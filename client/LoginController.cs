using System;
using System.Windows.Forms;
namespace client;
public partial class LoginController : Form
{
    private Controller _controller;
    public LoginController(Controller controller)
    {
        InitializeComponent();
        _controller = controller;
    }
    private void loginButton_Click(object sender, EventArgs e)
    {
        try
        {
            _controller.Login(usernameField.Text, passwordField.Text);
            MainController mainController = new MainController(_controller, this);
            mainController.Show();
            usernameField.Text = "";
            passwordField.Text = "";
            errorLabel.Text = "";
            Hide();
        }
        catch (Exception exception)
        {
            errorLabel.Text = exception.Message;
        }
    }
}