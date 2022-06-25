namespace 가위바위보게임
{
    partial class Form1
    {
        /// <summary>
        /// 필수 디자이너 변수입니다.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 사용 중인 모든 리소스를 정리합니다.
        /// </summary>
        /// <param name="disposing">관리되는 리소스를 삭제해야 하면 true이고, 그렇지 않으면 false입니다.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form 디자이너에서 생성한 코드

        /// <summary>
        /// 디자이너 지원에 필요한 메서드입니다. 
        /// 이 메서드의 내용을 코드 편집기로 수정하지 마세요.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.label1 = new System.Windows.Forms.Label();
            this.btnRock = new System.Windows.Forms.Button();
            this.countDownTimer = new System.Windows.Forms.Timer(this.components);
            this.label2 = new System.Windows.Forms.Label();
            this.btnPaper = new System.Windows.Forms.Button();
            this.btnScissor = new System.Windows.Forms.Button();
            this.playerScoreLabel = new System.Windows.Forms.Label();
            this.AIScoreLabel = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.txtCountDown = new System.Windows.Forms.Label();
            this.txtRounds = new System.Windows.Forms.Label();
            this.btnRestart = new System.Windows.Forms.Button();
            this.btnHelp = new System.Windows.Forms.Button();
            this.picAI = new System.Windows.Forms.PictureBox();
            this.picPlayer = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.picAI)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.picPlayer)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("굴림", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.label1.Location = new System.Drawing.Point(239, 98);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(59, 19);
            this.label1.TabIndex = 2;
            this.label1.Text = "Player";
            // 
            // btnRock
            // 
            this.btnRock.AutoSize = true;
            this.btnRock.Font = new System.Drawing.Font("굴림", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.btnRock.Location = new System.Drawing.Point(84, 156);
            this.btnRock.Name = "btnRock";
            this.btnRock.Size = new System.Drawing.Size(82, 60);
            this.btnRock.TabIndex = 3;
            this.btnRock.Text = "바위";
            this.btnRock.UseVisualStyleBackColor = true;
            this.btnRock.Click += new System.EventHandler(this.btnRock_Click);
            // 
            // countDownTimer
            // 
            this.countDownTimer.Interval = 1000;
            this.countDownTimer.Tick += new System.EventHandler(this.countDownTimerEvent);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("굴림", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.label2.Location = new System.Drawing.Point(603, 98);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(24, 19);
            this.label2.TabIndex = 4;
            this.label2.Text = "AI";
            // 
            // btnPaper
            // 
            this.btnPaper.AutoSize = true;
            this.btnPaper.Font = new System.Drawing.Font("굴림", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.btnPaper.Location = new System.Drawing.Point(84, 225);
            this.btnPaper.Name = "btnPaper";
            this.btnPaper.Size = new System.Drawing.Size(82, 62);
            this.btnPaper.TabIndex = 5;
            this.btnPaper.Text = "보";
            this.btnPaper.UseVisualStyleBackColor = true;
            this.btnPaper.Click += new System.EventHandler(this.btnPaper_Click);
            // 
            // btnScissor
            // 
            this.btnScissor.AutoSize = true;
            this.btnScissor.Font = new System.Drawing.Font("굴림", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.btnScissor.Location = new System.Drawing.Point(84, 88);
            this.btnScissor.Name = "btnScissor";
            this.btnScissor.Size = new System.Drawing.Size(82, 62);
            this.btnScissor.TabIndex = 6;
            this.btnScissor.Text = "가위";
            this.btnScissor.UseVisualStyleBackColor = true;
            this.btnScissor.Click += new System.EventHandler(this.btnScissor_Click);
            // 
            // playerScoreLabel
            // 
            this.playerScoreLabel.BackColor = System.Drawing.SystemColors.ControlLight;
            this.playerScoreLabel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.playerScoreLabel.Enabled = false;
            this.playerScoreLabel.Font = new System.Drawing.Font("굴림", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.playerScoreLabel.Location = new System.Drawing.Point(373, 158);
            this.playerScoreLabel.Name = "playerScoreLabel";
            this.playerScoreLabel.Size = new System.Drawing.Size(54, 51);
            this.playerScoreLabel.TabIndex = 8;
            this.playerScoreLabel.Text = "0";
            this.playerScoreLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // AIScoreLabel
            // 
            this.AIScoreLabel.BackColor = System.Drawing.SystemColors.ControlLight;
            this.AIScoreLabel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.AIScoreLabel.Enabled = false;
            this.AIScoreLabel.Font = new System.Drawing.Font("굴림", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.AIScoreLabel.Location = new System.Drawing.Point(463, 158);
            this.AIScoreLabel.Name = "AIScoreLabel";
            this.AIScoreLabel.Size = new System.Drawing.Size(54, 51);
            this.AIScoreLabel.TabIndex = 9;
            this.AIScoreLabel.Text = "0";
            this.AIScoreLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("굴림", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.label3.Location = new System.Drawing.Point(368, 128);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(65, 19);
            this.label3.TabIndex = 10;
            this.label3.Text = "Player";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("굴림", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.label4.Location = new System.Drawing.Point(481, 128);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(26, 19);
            this.label4.TabIndex = 11;
            this.label4.Text = "AI";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("굴림", 21.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.label5.Location = new System.Drawing.Point(433, 165);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(24, 29);
            this.label5.TabIndex = 12;
            this.label5.Text = ":";
            // 
            // txtCountDown
            // 
            this.txtCountDown.AutoSize = true;
            this.txtCountDown.Font = new System.Drawing.Font("굴림", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.txtCountDown.Location = new System.Drawing.Point(434, 235);
            this.txtCountDown.Name = "txtCountDown";
            this.txtCountDown.Size = new System.Drawing.Size(20, 19);
            this.txtCountDown.TabIndex = 13;
            this.txtCountDown.Text = "5";
            // 
            // txtRounds
            // 
            this.txtRounds.AutoSize = true;
            this.txtRounds.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtRounds.Location = new System.Drawing.Point(400, 65);
            this.txtRounds.Name = "txtRounds";
            this.txtRounds.Size = new System.Drawing.Size(95, 24);
            this.txtRounds.TabIndex = 14;
            this.txtRounds.Text = "Round: 1";
            this.txtRounds.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // btnRestart
            // 
            this.btnRestart.AutoSize = true;
            this.btnRestart.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.btnRestart.Font = new System.Drawing.Font("굴림", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.btnRestart.Location = new System.Drawing.Point(404, 290);
            this.btnRestart.Name = "btnRestart";
            this.btnRestart.Size = new System.Drawing.Size(91, 73);
            this.btnRestart.TabIndex = 15;
            this.btnRestart.Text = "Start/\r\n\r\nRestart";
            this.btnRestart.UseVisualStyleBackColor = false;
            this.btnRestart.Click += new System.EventHandler(this.btnRestart_Click);
            // 
            // btnHelp
            // 
            this.btnHelp.BackColor = System.Drawing.SystemColors.GradientInactiveCaption;
            this.btnHelp.Location = new System.Drawing.Point(404, 369);
            this.btnHelp.Name = "btnHelp";
            this.btnHelp.Size = new System.Drawing.Size(91, 30);
            this.btnHelp.TabIndex = 16;
            this.btnHelp.Text = "게임방법";
            this.btnHelp.UseVisualStyleBackColor = false;
            this.btnHelp.Click += new System.EventHandler(this.btnHelp_Click);
            // 
            // picAI
            // 
            this.picAI.Image = global::가위바위보게임.Properties.Resources.qq;
            this.picAI.Location = new System.Drawing.Point(557, 133);
            this.picAI.Name = "picAI";
            this.picAI.Size = new System.Drawing.Size(120, 121);
            this.picAI.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picAI.TabIndex = 1;
            this.picAI.TabStop = false;
            // 
            // picPlayer
            // 
            this.picPlayer.Image = global::가위바위보게임.Properties.Resources.qq;
            this.picPlayer.Location = new System.Drawing.Point(210, 133);
            this.picPlayer.Name = "picPlayer";
            this.picPlayer.Size = new System.Drawing.Size(120, 121);
            this.picPlayer.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picPlayer.TabIndex = 0;
            this.picPlayer.TabStop = false;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.btnHelp);
            this.Controls.Add(this.btnRestart);
            this.Controls.Add(this.txtRounds);
            this.Controls.Add(this.txtCountDown);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.AIScoreLabel);
            this.Controls.Add(this.playerScoreLabel);
            this.Controls.Add(this.btnScissor);
            this.Controls.Add(this.btnPaper);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.btnRock);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.picAI);
            this.Controls.Add(this.picPlayer);
            this.Name = "Form1";
            this.Text = "RockPaperScissors";
            ((System.ComponentModel.ISupportInitialize)(this.picAI)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.picPlayer)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.PictureBox picPlayer;
        private System.Windows.Forms.PictureBox picAI;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button btnRock;
        private System.Windows.Forms.Timer countDownTimer;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button btnPaper;
        private System.Windows.Forms.Button btnScissor;
        private System.Windows.Forms.Label playerScoreLabel;
        private System.Windows.Forms.Label AIScoreLabel;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label txtCountDown;
        private System.Windows.Forms.Label txtRounds;
        private System.Windows.Forms.Button btnRestart;
        private System.Windows.Forms.Button btnHelp;
    }
}

