using System;
using System.Windows.Forms;
using MusicFestivalMPP.model;
using MusicFestivalMPP.service;

namespace GUI
{
    public partial class Form1 : Form
    {
        private Form2 Form2;
        private SuperService SuperService;
        public Form1(SuperService superService)
        {
            SuperService = superService;
            InitializeComponent();
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            try
            {
                User user = SuperService.ServiceUser.Login(usernameField.Text, passwordField.Text);
                if (user != null)
                {
                    SuperService.User = user;
                    if (Form2 == null)
                    {
                        Form2 = new Form2(SuperService);  
                        Form2.FormClosed += Form2_FormClosed;  
                    }
                    Form2.Show(this);
                    usernameField.Text = "";
                    passwordField.Text = "";
                    Hide();
                }
                else
                    errorsLabel.Text = "Wrong username or password!\n Please try again!";
            }
            catch (Exception exception)
            {
                errorsLabel.Text = "Wrong username or password!\n Please try again!";
            }
        }
        
        void Form2_FormClosed(object sender, FormClosedEventArgs e)
        {
            Form2 = null; 
            Show();
        }
    }
}
