using System;
using System.Collections.Generic;
using log4net;
using MusicFestivalMPP.model;
using MusicFestivalMPP.repository.interfaces;
using MusicFestivalMPP.validator;

namespace MusicFestivalMPP.service;

public class ServicePerformance
{
    private IRepositoryPerformance Repository;
    private IValidator<Performance> Validator;
    private static readonly ILog Log = LogManager.GetLogger("ServicePerformance");

    public ServicePerformance(IRepositoryPerformance repository, IValidator<Performance> validator) {
        Log.InfoFormat("Initializing ServicePerformance.");
        Repository = repository;
        Validator = validator;
    }

    public Performance Save(DateTime date, String place, int noOfAvailableSeats, String artist) {
        Log.InfoFormat("Saving performance.");
        Performance performance = new Performance(date, place, noOfAvailableSeats, 0, artist);
        Validator.Validate(performance);
        return Repository.Save(performance);
    }

    public IList<Performance> FindAll() {
        Log.InfoFormat("Finding all the performances.");
        return Repository.FindAll();
    }

    public IList<Performance> FindAllForADay(DateTime date) {
        Log.InfoFormat("Finding all the performances for the day {0}.", date);
        return Repository.FindAllPerformancesForADay(date);
    }
}