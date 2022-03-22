package service;

import model.User;

public class SuperService {
    public ServiceUser serviceUser;
    public ServicePerformance servicePerformance;
    public ServiceTicket serviceTicket;
    private User user;

    public SuperService(ServiceUser serviceUser, ServicePerformance servicePerformance, ServiceTicket serviceTicket, User user) {
        this.serviceUser = serviceUser;
        this.servicePerformance = servicePerformance;
        this.serviceTicket = serviceTicket;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
