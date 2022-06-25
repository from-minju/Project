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

namespace ASyncGuISimpleServer
{
    public partial class MultiChat : Form
    {
        Socket server;
        IPAddress serverIPAddress;
        delegate void AppendTextDelegate(Control ctrl, string s);
        AppendTextDelegate _textAppend;
        List<AsyncObject> connectedClient;
        int clientNum; //접속한client수

        List<string> userList = new List<string>();

        public MultiChat() //생성자
        {
            InitializeComponent();
            server = new Socket(AddressFamily.InterNetwork,
                SocketType.Stream, ProtocolType.Tcp); //서버 소킷 초기화
            _textAppend = new AppendTextDelegate(AppendText); //기존의 내용에 덧붙일 때 delegate
            connectedClient = new List<AsyncObject>();

            clientNum = 0;

        }

        void AppendText(Control ctrl, string s)
        {
            if (ctrl.InvokeRequired)
                ctrl.Invoke(_textAppend, ctrl, s);
            else
            {
                string source = ctrl.Text;
                ctrl.Text = source + Environment.NewLine + s;
            }
        }

        private void btnStart_Click(object sender, EventArgs e)
        {
            int port;
            if (!int.TryParse(txtPort.Text, out port))
            {  // port 입력 안 함
                MsgBoxHelper.Warn("포트를 입력하세요");

                txtPort.Focus();
                txtPort.SelectAll();
                return;
            }

            if (string.IsNullOrEmpty(txtAddress.Text))
            {
                serverIPAddress = IPAddress.Loopback;
                txtAddress.Text = serverIPAddress.ToString();
            }
            else
            {
                serverIPAddress = IPAddress.Parse(txtAddress.Text);
            }
            IPEndPoint serverEP = new IPEndPoint(serverIPAddress, port);
            server.Bind(serverEP);
            server.Listen(10);
            AppendText(txtHistory, string.Format("서버시작: @{0}", serverEP));
            server.BeginAccept(AcceptCallback, null); //AcceptCallback은 OS가 호출
        }

        void AcceptCallback(IAsyncResult ar)
        {
            try
            {
                Socket client = server.EndAccept(ar); 
                AppendText(txtHistory, string.Format("클라이언트({0})가 연결되었습니다",
                    client.RemoteEndPoint));

                server.BeginAccept(AcceptCallback, null); //다른 클라이언트를 받을 준비를 함


                //receive ID
                AsyncObject obj = new AsyncObject(4096);
                obj.workingSocket = client;
                client.BeginReceive(obj.Buffer, 0, obj.BufferSize, 0,
                    DataReceived, obj); 
            }
            catch { }

        }

        void DataReceived(IAsyncResult ar) //id나 데이터 받을 때마다 호출됨
        {
            AsyncObject obj = ar.AsyncState as AsyncObject;

            try
            {
                int received = obj.workingSocket.EndReceive(ar);

                if (received <= 0)//상대방이 종료하는 경우
                {

                    foreach (AsyncObject clients in connectedClient)
                    {
                        //비교해서 맞으면 삭제
                        if (obj.workingSocket == clients.workingSocket)
                        {
                            //string key = clients.Key;
                            try
                            {
                                connectedClient.Remove(clients);
                                AppendText(txtHistory, string.Format("접속해제 완료:{0}", clients.ID));
                            }
                            catch
                            {
                                break;
                            }
                        }
                    }

                    obj.workingSocket.Disconnect(false); //종료시키는부분
                    obj.workingSocket.Close();
                    clientNum--;
                    return;

                }
            }
            catch { }


            string text = Encoding.UTF8.GetString(obj.Buffer);
            AppendText(txtHistory, text);

            //프로토콜 :   ID:minju:
            string[] tokens = text.Split(':'); //token[0]:ID, token[1]:nameID
            string fromID = null; 
            string toID = null;
            string code = tokens[0];
            if (code.Equals("ID")) //clients에서 sendID할 때 ID:nameID:
            {
                clientNum++;
                fromID = tokens[1].Trim();
                obj.ID = fromID;
                AppendText(txtHistory, string.Format("[접속{0}]ID:{1},{2}",
                    clientNum, fromID, obj.workingSocket.RemoteEndPoint.ToString()));

                connectedClient.Add(obj);
                sendAll(obj.workingSocket, obj.Buffer); 
                obj.workingSocket.Send(Encoding.UTF8.GetBytes("ID_REG_Success:"));
            }

            else if (code.Equals("BR"))
            {
                //BR:나:메시지:
                fromID = tokens[1].Trim();
                string msg = tokens[2];
                AppendText(txtHistory, string.Format("[전체]{0}:{1}", fromID, msg));
                sendAll(obj.workingSocket, obj.Buffer);
                obj.workingSocket.Send(Encoding.UTF8.GetBytes("BR_Success:"));

            }
            else if (code.Equals("TO"))
            {
                //TO:나:상대방:메시지:
                fromID = tokens[1].Trim();
                toID = tokens[2].Trim();
                string msg = tokens[3];
                string rMsg = "[From:" + fromID + "][To:" + toID + "]" + msg;
                AppendText(txtHistory, rMsg);
                sendTo(toID, obj.Buffer);
                obj.workingSocket.Send(Encoding.UTF8.GetBytes("To_Success:"));

            }
            else if (code.Equals("GiveMeUserList"))
            {
                string userListStringData = "GetUserList:";
                foreach (var el in connectedClient)
                {
                    userListStringData += string.Format("|{0}", el.ID);
                }
                userListStringData += ":";
                byte[] userListByteData = new byte[userListStringData.Length];

                userListByteData = Encoding.UTF8.GetBytes(userListStringData);
                AppendText(txtHistory, userListStringData);
                sendTo(obj.ID, userListByteData);
                obj.workingSocket.Send(Encoding.UTF8.GetBytes("GetUserList_Success:"));
  
            }
            else if (code.Equals("GROUP"))
            {
                fromID = tokens[1];
                toID = tokens[2];
                string msg = tokens[3];
                
                string[] toList = toID.Split('$');
                foreach(var el in toList)
                {
                    string sendData = "TO:" + fromID + ":" + el + ":" + msg;
                    byte[] sendByteData = new byte[sendData.Length];

                    sendByteData = Encoding.UTF8.GetBytes(sendData);
                    AppendText(txtHistory, sendData);
                    sendTo(el, sendByteData);
                    obj.workingSocket.Send(Encoding.UTF8.GetBytes("To_Success:"));
                }
            }
            else { }


            obj.clearBuffer();
            obj.workingSocket.BeginReceive(obj.Buffer, 0, obj.BufferSize,
                0, DataReceived, obj);//sendID는 완료된 상태이고, textbox에 입력하고 전송했을때 

        }

        void sendTo(String toID, byte[] buffer)
        {
            foreach (AsyncObject obj in connectedClient)
            {
                if (string.Equals(obj.ID, toID))
                {
                    try
                    {
                        obj.workingSocket.Send(buffer);
                    }
                    catch
                    {
                        obj.workingSocket.Dispose();
                        
                    }
                }
            }

        }

        void sendAll(Socket except, byte[] buffer) //브로드캐스트하는 함수
        {
            foreach (AsyncObject clients in connectedClient)
            {
                if (except != clients.workingSocket)
                {
                    try { clients.workingSocket.Send(buffer); }
                    catch
                    {
                        try { clients.workingSocket.Dispose(); } catch { }
                    }
                }

            }


        }

        private void btnSend_Click(object sender, EventArgs e)
        {
            if (!server.IsBound)
            {
                MsgBoxHelper.Warn("서버를 실행하세요");
                return;
            }
            string text = txtSend.Text.Trim();
            if (string.IsNullOrEmpty(text))
            {
                MsgBoxHelper.Warn("텍스트를 입력하세요");
                return;
            }

            byte[] bDts = Encoding.UTF8.GetBytes("Server:" + text); //서버가 보내는 메시지
            AppendText(txtHistory, "Server:" + text);


            sendAll(null, bDts);

            txtSend.Clear();
        }

        private void MultiChat_FormClosing(object sender, FormClosingEventArgs e)
        {
            try
            {
                server.Close();
            }
            catch
            { }
        }

        private void MultiChat_Load(object sender, EventArgs e) //multichat이 올라올 때(Form이 로드될 때)
        {
            IPHostEntry he = Dns.GetHostEntry(Dns.GetHostName());
            foreach (IPAddress addr in he.AddressList)
            {
                if (addr.AddressFamily == AddressFamily.InterNetwork)
                {
                    string s = "server address:" + addr.ToString();
                    AppendText(txtHistory, s);
                }
            }

        }

      
    }
}
