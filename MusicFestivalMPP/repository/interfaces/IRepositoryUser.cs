using System;
using MusicFestivalMPP.model;

namespace MusicFestivalMPP.repository.interfaces;

public interface IRepositoryUser: IRepository<User> 
{
    User FindUserByUsername(String username);

    User CheckConnection(String username, String password);
}