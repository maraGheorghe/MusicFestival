using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using model;
using persistence.interfaces;
using service;

namespace server
{
    public class ServiceImplementation: IService
    {
        private IRepositoryUser userRepository;
        private IRepositoryPerformance performanceRepository;
        private IRepositoryTicket ticketRepository;
        private readonly List<IObserver> loggedClients;


        public ServiceImplementation(IRepositoryUser userRepository, IRepositoryPerformance performanceRepository, IRepositoryTicket ticketRepository)
        {
            this.userRepository = userRepository;
            this.performanceRepository = performanceRepository;
            this.ticketRepository = ticketRepository;
            this.loggedClients = new List<IObserver>();
        }

        public void Login(string username, string password, IObserver client)
        {
            User user = userRepository.CheckConnection(username, password);
            if (user is null)
            {
                throw new ServiceException("Username and password do not match! Please try again.");
            }
            loggedClients.Add(client);
        }

        public List<Performance> FindAllPerformances()
        {
            IList<Performance> performances = performanceRepository.FindAll();
            return performances.ToList();
        }

        public List<Performance> FindAllPerformancesForADay(DateTime date)
        {
            IList<Performance> performances = performanceRepository.FindAllPerformancesForADay(date);
            return performances.ToList();
        }

        public void SaveTicket(long performanceId, DateTime date, string place, int noOfAvailableSeats, int noOfSoldSeats,
            string artist, string owner, int noOfSeats)
        {
            Performance performance = new Performance(performanceId, date, place, noOfAvailableSeats, noOfSoldSeats, artist);
            Ticket ticket = new Ticket(performance, owner, noOfSeats);
            TicketValidator validator = new TicketValidator();
            validator.Validate(ticket);
            Ticket savedTicket = ticketRepository.Save(ticket);
            if (savedTicket != null) {
                NotifyTicketBought(ticket);
            }
        }
        
        private void NotifyTicketBought(Ticket ticket) {
            foreach (var observer in loggedClients)
            {
                Task.Run(() => observer.TicketBought(ticket));
            }
        }

        public void Logout(User user, IObserver client)
        {
            bool localClient = loggedClients.Remove(client);
            if (!localClient)
            {
                throw new ServiceException("This user is not logged in.");
            }
        }
    }
}