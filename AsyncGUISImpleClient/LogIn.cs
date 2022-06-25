using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;


namespace AsyncGUISImpleClient
{
    public partial class LogIn : Form
    {
        public LogIn()
        {
            InitializeComponent();
        }
        public string userID
        {
            get
            {
                return txtID.Text;
            }
            private set
            {
                txtID.Text = value;
            }
        }

        public string userIP
        {
            get
            {
                return txtIP.Text;
            }
            private set
            {
                txtIP.Text = value;
            }
        }

        public string userPort
        {
            get
            {
                return txtPort.Text;
            }
            private set
            {
                txtPort.Text = value;
            }
        }


        private void btnLogIn_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(txtID.Text))
            {
                MsgBoxHelper.Warn("ID를 입력하세요");
                return;
            }

            //this.DialogResult = true;
            this.DialogResult = DialogResult.OK;
            //this.Close();
        }
    }
}
