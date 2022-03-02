using MusicFestivalMPP.model;

namespace MusicFestivalMPP.repository;

public interface IRepository<E>
{
    E Save(E entity);

    E Find(long id);

    E Delete(long id);

    E Update(E entity);

    List<E> FindAll();
}