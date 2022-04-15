using System;
using System.Windows.Forms;
using model;
using service;

namespace client;

public partial class BuyTicketController : Form
{
    
    private readonly Controller _controller;
    private readonly Performance _performance;
    
    public BuyTicketController(Controller controller, Performance performance)
    {
        InitializeComponent();
        _controller = controller;
        _performance = performance;
    }

    private void buyButton_Click(object sender, EventArgs e)
    {
        try
        {
            _controller.BuyTicket(_performance, ownerTextBox.Text, Int32.Parse(numberTextBox.Text));
            errorLabel.Text = "";
            Close();
        }
        catch (ServiceException exception)
        {
            errorLabel.Text = exception.Message;
        }
        catch (FormatException exception)
        {
            errorLabel.Text = "The second field must represent a number!";
        }
    }
}