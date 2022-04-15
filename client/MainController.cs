using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;
using model;

namespace client;

public partial class MainController : Form
{
    private readonly Controller _controller;
    private readonly LoginController _loginController;
    private List<Performance> _allShowsData;
    private List<Performance> _dateShowsData;
    public MainController(Controller controller, LoginController loginController)
    {
        InitializeComponent();
        _controller = controller;
        _loginController = loginController;
        _allShowsData = controller.GetAllPerformances();
        _dateShowsData = controller.GetAllPerformancesByDate(DateTime.Now);
        InitializeAllShowsList();
        dateShowsList.DataSource = _dateShowsData;
        dateShowsList.ClearSelected();
        _controller.updateEvent += UserUpdate;
    }
    
    private void InitializeAllShowsList()
    {
        allShowsList.View = View.List;
        allShowsList.Items.Clear();
        for (int i = 0; i < _allShowsData.Count; i++)
        {
            allShowsList.Items.Add(_allShowsData[i].ToString());
            if (_allShowsData[i].NoOfAvailableSeats == 0)
                allShowsList.Items[i].BackColor = Color.Firebrick;
        }
    }

    public void UserUpdate(object sender, Ticket ticket)
    {
        if (ticket is not null)
        {
            foreach (var show in _allShowsData)
                if (show == ticket.Performance)
                {
                    show.NoOfSoldSeats += ticket.NoOfSeats;
                    show.NoOfAvailableSeats -= ticket.NoOfSeats;
                }
            foreach (var show in _dateShowsData)
                if (show == ticket.Performance)
                {
                    show.NoOfSoldSeats += ticket.NoOfSeats;
                    show.NoOfAvailableSeats -= ticket.NoOfSeats;
                }
            allShowsList.Invoke((Action) InitializeAllShowsList);
            dateShowsList.Invoke((Action) delegate
            {
                dateShowsList.DataSource = null;
                dateShowsList.DataSource = _dateShowsData;
                dateShowsList.ClearSelected();
            });
        }
    }

    private void allShowsList_MouseDoubleClick(object sender, MouseEventArgs e)
    {
        int index = allShowsList.SelectedIndices[0];
        if (index >= 0)
        {
            Performance performance = _allShowsData[index];
            BuyTicketController buyTicketController = new BuyTicketController(_controller, performance);
            buyTicketController.Show();
        }
    }

    private void dateShowsList_MouseDoubleClick(object sender, MouseEventArgs e)
    {
        int index = dateShowsList.SelectedIndex;
        if (index >= 0)
        {
            Performance performance = _dateShowsData[index];
            BuyTicketController buyTicketController = new BuyTicketController(_controller, performance);
            buyTicketController.Show();
        }
        dateShowsList.ClearSelected();
    }

    private void logoutButton_Click(object sender, EventArgs e)
    {
        _controller.Logout();
        _loginController.Show();
        Hide();
    }
    
    private void MainController_FormClosing(object sender, FormClosingEventArgs e)
    {
        if (e.CloseReason == CloseReason.UserClosing)
        {
            _controller.Logout();
            Application.Exit();
        }
    }
    
    private void dateTimePicker_CloseUp(object sender, EventArgs e)
    {
        DateTime date = dateTimePicker.Value.Date;
        _dateShowsData = _controller.GetAllPerformancesByDate(date);
        dateShowsList.DataSource = _dateShowsData;
        dateShowsList.ClearSelected();
    }
}