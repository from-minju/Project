using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net;
using System.Net.Sockets;

namespace AsyncGUISImpleClient
{
    public partial class MainWindow : Form
    {

        Socket server;
        IPAddress serverIPAddress;

        delegate void AppendTextDelegate(Control ctrl, string s);
        AppendTextDelegate _textAppend;
        AsyncObject obj = new AsyncObject(4096);
        public static string u_id; //사용자id(이름)
        

        public MainWindow()
        {
            InitializeComponent();
            _textAppend = new AppendTextDelegate(AppendText);
        }

        void AppendText(Control ctrl, string s)
        {
            if (ctrl.InvokeRequired)
                ctrl.Invoke(_textAppend, ctrl, s);
            else
            {
                string source = ctrl.Text;
                ctrl.Text = source + s + Environment.NewLine;
            }
        }
        private void btnConnect_Click(object sender, EventArgs e)
        {
            if (server != null)
            {
                MessageBox.Show("이미 로그인되었습니다.", "Information", MessageBoxButtons.OK);
                return;
            }

            LogIn login = new LogIn();

            login.ShowDialog();

            server = new Socket(AddressFamily.InterNetwork,
                SocketType.Stream, ProtocolType.Tcp);

            string u_ip = login.userIP;
            int u_port;
            int.TryParse(login.userPort, out u_port);


            if (string.IsNullOrEmpty(u_ip)) //ip입력안했을 때
            {
                serverIPAddress = IPAddress.Loopback;
                u_id = serverIPAddress.ToString();
            }
            else
            {
                serverIPAddress = IPAddress.Parse(login.userIP);
            }
            IPEndPoint serverEP = new IPEndPoint(serverIPAddress, u_port);

            if (string.IsNullOrEmpty(login.userID))
            {
                MsgBoxHelper.Warn("ID를 입력하세요");
                return;
            }
            u_id = login.userID;

            try
            {
                server.Connect(serverEP);

            }
            catch
            {
                return;
            }

            SendID(); //서버로 id를 보냄

            AppendText(txtNoticeBox, "서버와 연결되었습니다");
            btnConnect.Text = string.Format("{0} 님 반갑습니다 ", u_id);
            
            obj = new AsyncObject(4096);
            obj.workingSocket = server;
            server.BeginReceive(obj.Buffer, 0, obj.BufferSize, 0,
                DataReceived, obj);
        }

        //서버로 ID를 보냄.(ID:강민주:)
        void SendID()
        {
            byte[] bDts = Encoding.UTF8.GetBytes("ID:" + u_id + ":");
            server.Send(bDts); //id를 서버에 전달

        }



        void DataReceived(IAsyncResult ar)
        {
            AsyncObject obj = ar.AsyncState as AsyncObject;
            try
            {
                int received = obj.workingSocket.EndReceive(ar);
                if (received <= 0)
                {
                    obj.workingSocket.Disconnect(false);
                    obj.workingSocket.Close();
                    return;
                }
            }
            catch { }



            string text = Encoding.UTF8.GetString(obj.Buffer);
            //AppendText(txtNoticeBox, text);
            string[] tokens = text.Split(':');


            
            if (tokens[0].Equals("ID"))
            {
                string id = tokens[1];
                AppendText(txtHistory, string.Format("<<{0}님이 입장하였습니다.>>", id));
            }
            else if (tokens[0].Equals("BR"))
            {
                string fromID = tokens[1];
                string msg = tokens[2];
                AppendText(txtHistory, string.Format("[전체] ID:{0}:{1}", fromID, msg));
            }
            else if (tokens[0].Equals("TO"))
            {
                //TO:나:상대방:메시지
                string fromID = tokens[1];
                string toID = tokens[2];
                string msg = tokens[3];
                AppendText(txtHistory, string.Format("[" + fromID + "] " + msg));
                
            }
            else if (tokens[0].Equals("Server"))
            {
                string msg = tokens[1];
                AppendText(txtHistory, string.Format("[공지]{0}", msg));
            }
            else if (tokens[0].Equals("GetUserList"))
            {

                string msg = tokens[1];
                //AppendText(txtNoticeBox, string.Format(msg));
                string[] splitedUser = msg.Split('|');
                List<AsyncObject> tempUserList = new List<AsyncObject>();
                foreach (var el in splitedUser)
                {
                    if (string.IsNullOrEmpty(el))
                        continue;
                    tempUserList.Add(new AsyncObject(el));
                }
                UserListView.Items.Clear();
                foreach (AsyncObject user in tempUserList)
                {
                    ListViewItem item = new ListViewItem();
                    item.Text = user.userName;
                    this.UserListView.Items.Add(item);
                }


            }
            else if (tokens[0].Equals("GROUP"))
            {
                //GROUP:나:상대방:메시지
                string fromID = tokens[1];
                string toID = tokens[2];
                string msg = tokens[3];
                AppendText(txtHistory, string.Format("[" + fromID + "] " + msg));
            }
            else if (tokens[0].Equals("ID_REG_Success"))
            {
                string msg = tokens[1];
                AppendText(txtNoticeBox, string.Format("ID가 등록되었습니다."));
            }
            else if (tokens[0].Equals("BR_Success"))
            {
                string msg = tokens[1];
                AppendText(txtNoticeBox, string.Format("브로드캐스트 성공"));
            }
            else if (tokens[0].Equals("To_Success"))
            {
                string msg = tokens[1];
                AppendText(txtNoticeBox, string.Format("채팅 보내기 성공"));
            }
            else if (tokens[0].Equals("GetUserList_Success"))
            {
                string msg = tokens[1];
                AppendText(txtNoticeBox, string.Format("사용자 목록 업데이트 완료"));
            }

            obj.clearBuffer();
            try
            {
                obj.workingSocket.BeginReceive(obj.Buffer, 0, obj.BufferSize, 0,
                DataReceived, obj);
            }
            catch { }

        }

        private void btnSend_Click(object sender, EventArgs e)
        {
            if (!server.IsBound) return; 
            string text = txtSend.Text.Trim();
            if (string.IsNullOrEmpty(text)) return;

            string[] tokens = text.Split(':');
            byte[] bDts = new byte[4096];

            if (tokens[0].Equals("BR"))
            {
                //BR:나:메시지:
                bDts = Encoding.UTF8.GetBytes("BR:" + u_id + ':' + tokens[1] + ':'); //tokens[1]: 보낼 내용
                AppendText(txtHistory, string.Format("[전체전송]{0}", tokens[1]));
            }
            else if (tokens[0].Equals("GROUP"))
            {
                //사용자가 보낼 때  GROUP:$user1$user2:msg
                //GROUP:나:user1$user2:msg
                bDts = Encoding.UTF8.GetBytes("GROUP:" + u_id + ":" + tokens[1] + ":" + tokens[2] + ":");
                AppendText(txtHistory, string.Format("[그룹전송]{0}에게: {1}", tokens[1], tokens[2]));
            }
            else //상대방이 정해진 경우(특정 id로 보냄)
            {
                //TO:나:상대방:메시지:
                bDts = Encoding.UTF8.GetBytes("TO:" + u_id + ':' + tokens[0] + ':' + tokens[1] + ':'); //tokens[0]: 상대방ID
                AppendText(txtHistory, string.Format("[{0}에게 전송]:{1}", tokens[0], tokens[1]));
            }
            try { server.Send(bDts); } catch { }

            txtSend.Clear();

        }

        private void Update_btn_Click(object sender, EventArgs e)
        {
            //서버에게 사용자 리스트를 달라고 요청 (프로토콜  GiveMeUserList:FROM:강민주:)
            string getUserProtocol = "GiveMeUserList:FROM:" + u_id + ":";
            byte[] bDts = new byte[getUserProtocol.Length];
            bDts = Encoding.UTF8.GetBytes(getUserProtocol);
            try
            {
                server.Send(bDts);
            }
            catch { }
        }


        private void MainWindow_FormClosing(object sender, FormClosingEventArgs e)
        {
            try
            {

                server.Disconnect(false);
                server.Shutdown(SocketShutdown.Both);

                server.Close();
            }
            catch
            { }
        }


    }
}
