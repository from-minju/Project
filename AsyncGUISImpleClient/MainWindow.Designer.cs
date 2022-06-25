namespace AsyncGUISImpleClient
{
    partial class MainWindow
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.txtNoticeBox = new System.Windows.Forms.TextBox();
            this.btnConnect = new System.Windows.Forms.Button();
            this.UserListView = new System.Windows.Forms.ListView();
            this.userList = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.Update_btn = new System.Windows.Forms.Button();
            this.txtHistory = new System.Windows.Forms.TextBox();
            this.txtSend = new System.Windows.Forms.TextBox();
            this.btnSend = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("나눔스퀘어_ac Bold", 28.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.label1.Location = new System.Drawing.Point(23, 19);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(225, 42);
            this.label1.TabIndex = 0;
            this.label1.Text = "채팅 프로그램";
            // 
            // txtNoticeBox
            // 
            this.txtNoticeBox.BackColor = System.Drawing.Color.White;
            this.txtNoticeBox.Font = new System.Drawing.Font("굴림", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtNoticeBox.Location = new System.Drawing.Point(275, 60);
            this.txtNoticeBox.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.txtNoticeBox.Multiline = true;
            this.txtNoticeBox.Name = "txtNoticeBox";
            this.txtNoticeBox.ReadOnly = true;
            this.txtNoticeBox.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.txtNoticeBox.Size = new System.Drawing.Size(359, 90);
            this.txtNoticeBox.TabIndex = 1;
            // 
            // btnConnect
            // 
            this.btnConnect.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.btnConnect.DialogResult = System.Windows.Forms.DialogResult.OK;
            this.btnConnect.Font = new System.Drawing.Font("나눔스퀘어_ac", 13.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.btnConnect.Location = new System.Drawing.Point(12, 63);
            this.btnConnect.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btnConnect.Name = "btnConnect";
            this.btnConnect.Size = new System.Drawing.Size(236, 31);
            this.btnConnect.TabIndex = 8;
            this.btnConnect.Text = "로그인";
            this.btnConnect.UseVisualStyleBackColor = false;
            this.btnConnect.Click += new System.EventHandler(this.btnConnect_Click);
            // 
            // UserListView
            // 
            this.UserListView.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.userList});
            this.UserListView.Font = new System.Drawing.Font("나눔스퀘어_ac", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.UserListView.FullRowSelect = true;
            this.UserListView.GridLines = true;
            this.UserListView.HideSelection = false;
            this.UserListView.Location = new System.Drawing.Point(12, 98);
            this.UserListView.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.UserListView.Name = "UserListView";
            this.UserListView.Size = new System.Drawing.Size(236, 403);
            this.UserListView.TabIndex = 9;
            this.UserListView.UseCompatibleStateImageBehavior = false;
            this.UserListView.View = System.Windows.Forms.View.Details;
            // 
            // userList
            // 
            this.userList.Text = "사용자";
            this.userList.Width = 219;
            // 
            // Update_btn
            // 
            this.Update_btn.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.Update_btn.Font = new System.Drawing.Font("나눔스퀘어_ac", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.Update_btn.Location = new System.Drawing.Point(12, 505);
            this.Update_btn.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Update_btn.Name = "Update_btn";
            this.Update_btn.Size = new System.Drawing.Size(236, 36);
            this.Update_btn.TabIndex = 10;
            this.Update_btn.Text = "새로고침";
            this.Update_btn.UseVisualStyleBackColor = false;
            this.Update_btn.Click += new System.EventHandler(this.Update_btn_Click);
            // 
            // txtHistory
            // 
            this.txtHistory.Location = new System.Drawing.Point(275, 163);
            this.txtHistory.Multiline = true;
            this.txtHistory.Name = "txtHistory";
            this.txtHistory.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.txtHistory.Size = new System.Drawing.Size(360, 301);
            this.txtHistory.TabIndex = 11;
            // 
            // txtSend
            // 
            this.txtSend.Location = new System.Drawing.Point(275, 471);
            this.txtSend.Multiline = true;
            this.txtSend.Name = "txtSend";
            this.txtSend.Size = new System.Drawing.Size(293, 70);
            this.txtSend.TabIndex = 12;
            // 
            // btnSend
            // 
            this.btnSend.Location = new System.Drawing.Point(574, 472);
            this.btnSend.Name = "btnSend";
            this.btnSend.Size = new System.Drawing.Size(61, 69);
            this.btnSend.TabIndex = 13;
            this.btnSend.Text = "보내기";
            this.btnSend.UseVisualStyleBackColor = true;
            this.btnSend.Click += new System.EventHandler(this.btnSend_Click);
            // 
            // MainWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(665, 571);
            this.Controls.Add(this.btnSend);
            this.Controls.Add(this.txtSend);
            this.Controls.Add(this.txtHistory);
            this.Controls.Add(this.Update_btn);
            this.Controls.Add(this.UserListView);
            this.Controls.Add(this.btnConnect);
            this.Controls.Add(this.txtNoticeBox);
            this.Controls.Add(this.label1);
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Name = "MainWindow";
            this.Text = "Chatting Program";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.MainWindow_FormClosing);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtNoticeBox;
        private System.Windows.Forms.Button btnConnect;
        private System.Windows.Forms.ListView UserListView;
        private System.Windows.Forms.ColumnHeader userList;
        private System.Windows.Forms.Button Update_btn;
        private System.Windows.Forms.TextBox txtHistory;
        private System.Windows.Forms.TextBox txtSend;
        private System.Windows.Forms.Button btnSend;
    }
}