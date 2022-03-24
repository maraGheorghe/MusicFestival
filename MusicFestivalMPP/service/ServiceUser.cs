using System;
using log4net;
using MusicFestivalMPP.model;
using MusicFestivalMPP.repository.interfaces;
using MusicFestivalMPP.validator;

namespace MusicFestivalMPP.service;

public class ServiceUser
{
    private IRepositoryUser Repository;
    private IValidator<User> Validator;
    private static readonly ILog Log = LogManager.GetLogger("ServiceUser");

    public ServiceUser(IRepositoryUser repositoryUser, IValidator<User> validator)
    {
        Log.InfoFormat("Initializing ServiceUser.");
        Repository = repositoryUser;
        Validator = validator;
    }
    
    public User Save(String username, String password) {
        Log.InfoFormat("Saving user with username {0} and password {1}.", username, password);
        User user = new User(username, password);
        Validator.Validate(user);
        return Repository.Save(user);
    }

    public User Login(String username, String password) {
        Log.InfoFormat("Login user with username {0} and password {1}.", username, password);
        return Repository.CheckConnection(username, password);
    }
}