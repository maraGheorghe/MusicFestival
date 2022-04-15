using System;
using System.Collections.Generic;
using System.Configuration;
using System.Net.Sockets;
using System.Threading;
using log4net.Config;
using networking;
using persistence;
using persistence.interfaces;
using service;

namespace server
{
    class StartServer
    {
        static void Main(string[] args)
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("log4j.xml"));
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings["festivalDB"];
            Console.WriteLine(settings.ConnectionString);
            IDictionary<string, string> properties = new SortedList<string, string>();
            properties.Add("ConnectionString", settings.ConnectionString);
            Console.WriteLine(properties["ConnectionString"]);
            IRepositoryUser userRepository = new RepositoryUser(properties);
            IRepositoryPerformance performanceRepository = new RepositoryPerformance(properties);
            RepositoryTicket ticketRepository = new RepositoryTicket(properties);
            IService serviceImplementation = new ServiceImplementation(userRepository, performanceRepository, ticketRepository);
            SerialServer server = new SerialServer("127.0.0.1", 22126, serviceImplementation);
            server.Start();
            Console.WriteLine("Server started ...");
            Console.ReadLine();
            
        }
    }
    public class SerialServer: ConcurrentServer 
    {
        private IService server;
        private ClientWorker worker;
        public SerialServer(string host, int port, IService server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialServer...");
        }
        
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ClientWorker(server, client);
            return new Thread(new ThreadStart(worker.Run));
        }
    }
}