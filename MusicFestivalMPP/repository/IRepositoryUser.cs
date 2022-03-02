using MusicFestivalMPP.model;

namespace MusicFestivalMPP.repository;

public interface IRepositoryUser: IRepository<User>
{
    User FindUserByUsername(String username);

    User CheckConnection(String username, String password);
}