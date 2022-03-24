using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MusicFestivalMPP.model;
using MusicFestivalMPP.service;
using MusicFestivalMPP.validator;

namespace GUI
{
    public partial class Form3 : Form
    {
        private SuperService SuperService;
        private Performance Performance;
        public Form3(SuperService superService, Performance performance)
        {
            SuperService = superService;
            Performance = performance;
            InitializeComponent();
        }

        private void buyButton_Click(object sender, EventArgs e)
        {
            try
            {
                Ticket ticket = SuperService.ServiceTicket.Save(Performance.Id, Performance.Date, Performance.Place,
                    Performance.NoOfAvailableSeats, Performance.NoOfSoldSeats, Performance.Artist, nameField.Text,
                    Int32.Parse(numberField.Text));
                if (ticket == null)
                    errorsLabel.Text = "Invalid name or number of tickets!\n Please try again!";
                else
                    Close();
            }
            catch (ValidationException exception)
            {
                errorsLabel.Text = exception.Message;
            }
            catch (FormatException exception)
            {
                errorsLabel.Text = "The second field must represent a number!";
            }
        }
    }
}
