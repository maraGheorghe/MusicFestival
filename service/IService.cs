using System;
using System.Collections.Generic;
using model;

namespace service
{
    public interface IService
    {
        void Login(String username, String password, IObserver client);
        void Logout(User user, IObserver client);
        List<Performance> FindAllPerformances();
        List<Performance> FindAllPerformancesForADay(DateTime date);
        void SaveTicket(long performanceId, DateTime date, String place,
            int noOfAvailableSeats, int noOfSoldSeats, String artist,
            String owner, int noOfSeats);
    }
}
