using System;
using log4net;
using MusicFestivalMPP.model;
using MusicFestivalMPP.repository.interfaces;
using MusicFestivalMPP.validator;

namespace MusicFestivalMPP.service;

public class ServiceTicket
{
    private IRepositoryTicket Repository;
    private IValidator<Ticket> ValidatorTicket;
    private IValidator<Performance> ValidatorPerformance;
    private static readonly ILog Log = LogManager.GetLogger("ServiceTicket");

    public ServiceTicket(IRepositoryTicket repository, IValidator<Ticket> validatorTicket, IValidator<Performance> validatorPerformance)
    {
        Log.InfoFormat("Initializing ServiceTicket.");
        Repository = repository;
        ValidatorTicket = validatorTicket;
        ValidatorPerformance = validatorPerformance;
    }

    public Ticket Save(long performanceId, DateTime date, String place, int noOfAvailableSeats, int noOfSoldSeats, String artist, String owner, int noOfSeats) {
        Log.InfoFormat("Saving ticket at performance with ID {0}, bought by {1}.", performanceId, owner);
        Performance performance = new Performance(performanceId, date, place, noOfAvailableSeats, noOfSoldSeats, artist);
        ValidatorPerformance.Validate(performance);
        Ticket ticket = new Ticket(performance, owner, noOfSeats);
        ValidatorTicket.Validate(ticket);
        return Repository.Save(ticket);
    }
}