// See https://aka.ms/new-console-template for more information

using System;
using System.Collections.Generic;
using System.Configuration;
using log4net.Config;
using MusicFestivalMPP.model;
using MusicFestivalMPP.repository;

namespace MusicFestivalMPP
{
    class Program
    {
        static void Main(string[] args)
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("log4j.xml"));
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings["festivalDB"];
            Console.WriteLine(settings.ConnectionString);
            IDictionary<string, string> properties = new SortedList<string, string>();
            properties.Add("ConnectionString", settings.ConnectionString);
            RepositoryUser repositoryUser = new RepositoryUser(properties);
            Console.WriteLine(repositoryUser.Find(1).Username);
            RepositoryPerformance repositoryPerformance = new RepositoryPerformance(properties);
          //  repositoryPerformance.Save(new Performance(new DateTime(2022, 6, 22), "Bontida", 2000, 0, "EC"));
            RepositoryTicket repositoryTicket = new RepositoryTicket(properties);
           // repositoryTicket.Save(new Ticket(new Performance(12, new DateTime(2022, 6, 22), "Bontida", 2000, 0, "EC"), "Mara", 2));
              Performance performance = repositoryPerformance.Find(14);
              Console.WriteLine(performance.NoOfAvailableSeats);
              Console.WriteLine(repositoryTicket.GetPerformanceOfTicket(15).Artist);
        }
    }
}