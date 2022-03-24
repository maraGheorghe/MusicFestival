using System;
using System.Collections.Generic;
using System.Configuration;
using System.Windows.Forms;
using log4net.Config;
using MusicFestivalMPP.repository;
using MusicFestivalMPP.service;
using MusicFestivalMPP.validator;
using System.Data.SQLite;

namespace GUI
{
    internal static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("log4j.xml"));
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings["festivalDB"];
            IDictionary<string, string> properties = new SortedList<string, string>();
            properties.Add("ConnectionString", settings.ConnectionString);
            RepositoryUser repositoryUser = new RepositoryUser(properties);
            RepositoryPerformance repositoryPerformance = new RepositoryPerformance(properties);
            RepositoryTicket repositoryTicket = new RepositoryTicket(properties);
            ServiceUser serviceUser = new ServiceUser(repositoryUser, new UserValidator());
            ServicePerformance servicePerformance =
                new ServicePerformance(repositoryPerformance, new PerformanceValidator());
            ServiceTicket serviceTicket =
                new ServiceTicket(repositoryTicket, new TicketValidator(), new PerformanceValidator());
            SuperService superService = new SuperService(serviceUser, servicePerformance, serviceTicket, null);
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1(superService));
        }
    }
}
