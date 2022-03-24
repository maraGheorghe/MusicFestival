using MusicFestivalMPP.model;

namespace MusicFestivalMPP.service;

public class SuperService
{
    public ServiceUser ServiceUser;
    public ServicePerformance ServicePerformance;
    public ServiceTicket ServiceTicket;
    public User User { get; set; }

    public SuperService(ServiceUser serviceUser, ServicePerformance servicePerformance, ServiceTicket serviceTicket, User user) {
        this.ServiceUser = serviceUser;
        this.ServicePerformance = servicePerformance;
        this.ServiceTicket = serviceTicket;
        this.User = user;
    }
}