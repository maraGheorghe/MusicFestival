using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MusicFestivalMPP.service;

namespace GUI
{
    public partial class Form2 : Form
    {
        private Form3 Form3;
        private SuperService SuperService;
        
        public Form2(SuperService superService)
        {
            SuperService = superService;
            InitializeComponent();
            InitializeAllShowsList();
            ColorSoldOutShows();
        }

        private void InitializeAllShowsList()
        {
            allShowsList.View = View.List;
            allShowsList.Items.Clear();
            foreach (var item in SuperService.ServicePerformance.FindAll())
                allShowsList.Items.Add(item.ToString());
        }

        private void ColorSoldOutShows()
        {
            var shows = SuperService.ServicePerformance.FindAll();
            for(int i = 0; i < shows.Count; i++)
                if (shows[i].NoOfAvailableSeats == 0)
                    allShowsList.Items[i].BackColor = Color.Firebrick;
        }

        private void logoutButton_Click(object sender, EventArgs e)
        {
            SuperService.User = null;
            Owner.Show();
            Hide();
        }

        private void dateTimePicker_ValueChanged(object sender, EventArgs e)
        {
            DateTime date = dateTimePicker.Value.Date;
            dateShowsList.Items.Clear();
            foreach (var item in SuperService.ServicePerformance.FindAllForADay(date))
                dateShowsList.Items.Add(item);
        }

        private void allShowsList_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            var index = allShowsList.SelectedIndices[0];
            if (Form3 == null)
            {
                Form3 = new Form3(SuperService, SuperService.ServicePerformance.FindAll()[index]);  
                Form3.FormClosed += Form3_FormClosed;  
            }
            Form3.Show(this);
            Hide();
        }
        
        void Form3_FormClosed(object sender, FormClosedEventArgs e)
        {
            Form3 = null;
            Show();
            dateTimePicker_ValueChanged(sender, e);
            InitializeAllShowsList();
            ColorSoldOutShows();
        }
        
        private void dateShowsList_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            var index = dateShowsList.SelectedIndices[0];
            if (Form3 == null)
            {
                Form3 = new Form3(SuperService, SuperService.ServicePerformance.FindAllForADay(dateTimePicker.Value.Date)[index]);  
                Form3.FormClosed += Form3_FormClosed;  
            }
            Form3.Show(this);
            Hide();
        }
    }
}
