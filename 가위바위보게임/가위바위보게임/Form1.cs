using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace 가위바위보게임
{
    public partial class Form1 : Form
    {
        int rounds = 1;
        int timerPerRound = 6;
        bool gameOver = false;

        string[] AIchoiceList = { "rock", "paper", "scissor", "paper", "scissor", "rock" };

        int randomNumber = 0;
        Random rnd = new Random();

        string AIchoice;
        string playerChoice;

        int playerScore;
        int AIScore;

        public Form1()
        {
            InitializeComponent();
            countDownTimer.Enabled = false;
            playerChoice = "none";
            txtCountDown.Text = "5";
        }

        private void btnRock_Click(object sender, EventArgs e)
        {
            picPlayer.Image = Properties.Resources.rock;
            playerChoice = "rock";
        }

        private void btnPaper_Click(object sender, EventArgs e)
        {
            picPlayer.Image = Properties.Resources.paper;
            playerChoice = "paper";
        }

        private void btnScissor_Click(object sender, EventArgs e)
        {
            picPlayer.Image = Properties.Resources.scissors;
            playerChoice = "scissor";
        }

        private void btnRestart_Click(object sender, EventArgs e)
        {
            playerScore = 0;
            AIScore = 0;

            playerScoreLabel.Text = "0";
            AIScoreLabel.Text = "0";

            rounds = 1;
            playerChoice = "none";
            picPlayer.Image = Properties.Resources.qq;
            picAI.Image = Properties.Resources.qq;
            gameOver = false;

            countDownTimer.Enabled = true;

        }

        private void countDownTimerEvent(object sender, EventArgs e)
        {
            timerPerRound -= 1;
            txtCountDown.Text = timerPerRound.ToString();
            txtRounds.Text = "Round: " + rounds;

            Decision();
        }

        private void Decision()
        {
            if (timerPerRound < 1)//5초가 다 지나면 AI의 가위바위보 값 계산
            {
                countDownTimer.Enabled = false;
                timerPerRound = 6;
                randomNumber = rnd.Next(0, AIchoiceList.Length);
                AIchoice = AIchoiceList[randomNumber];

                switch (AIchoice)
                {
                    case "rock":
                        picAI.Image = Properties.Resources.rock;
                        break;
                    case "paper":
                        picAI.Image = Properties.Resources.paper;
                        break;
                    case "scissor":
                        picAI.Image = Properties.Resources.scissors;
                        break;
                }
            }

            if (rounds > 0&&rounds<4)//한 판 더 해야할 경우
            {
                if (txtCountDown.Text == "0")
                {
                    checkGame();
                    StartNextRound();
                }

            }
            else if(rounds>=4)//라운드가 안남아있을 경우(끝났을 경우)
            {
                countDownTimer.Enabled = false;
                if (txtCountDown.Text == "5")
                {
                    if (playerScore > AIScore)
                    {
                        MessageBox.Show("<최종결과>\nPlayer 승!!");
                    }
                    else
                    {
                        MessageBox.Show("<최종결과>\nAI 승!!");
                    }
                }
                gameOver = true;
            }
        }

        private void checkGame()
        {
            if (playerChoice == "rock" && AIchoice == "paper")
            {
                AIScore += 1;
                rounds += 1;
                MessageBox.Show("AI Wins");

            }
            else if (playerChoice == "scissor" && AIchoice == "rock")
            {
                AIScore += 1;
                rounds += 1;
                MessageBox.Show("AI Wins");

            }
            else if (playerChoice == "paper" && AIchoice == "scissor")
            {
                AIScore += 1;
                rounds += 1;
                MessageBox.Show("AI Wins");

            }
            else if (playerChoice == "rock" && AIchoice == "scissor")
            {
                playerScore += 1;
                rounds += 1;
                MessageBox.Show("Player Wins");

            }
            else if (playerChoice == "paper" && AIchoice == "rock")
            {
                playerScore += 1;
                rounds += 1;
                MessageBox.Show("Player Wins");

            }
            else if (playerChoice == "scissor" && AIchoice == "paper")
            {
                playerScore += 1;
                rounds += 1;
                MessageBox.Show("Player Wins");

            }
            else if (playerChoice == "none")
            {

                MessageBox.Show("선택하세요");

            }
            else
            {
                MessageBox.Show("비겼어요! 한 판 더~!");
            }
        }

        private void StartNextRound()
        {
            if (gameOver == true)
            {
                return;
            }

            playerScoreLabel.Text = playerScore.ToString();
            AIScoreLabel.Text = AIScore.ToString();

            playerChoice = "none";
            countDownTimer.Enabled = true;

            picPlayer.Image = Properties.Resources.qq;
            picAI.Image = Properties.Resources.qq;



        }

        private void btnHelp_Click(object sender, EventArgs e)
        {
            helpScreen help = new helpScreen();
            help.Show();
        }
    }
}
