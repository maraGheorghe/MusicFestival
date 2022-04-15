using System;
using model;

namespace persistence.interfaces;

public interface IRepositoryUser: IRepository<User> 
{
    User FindUserByUsername(String username);

    User CheckConnection(String username, String password);
}