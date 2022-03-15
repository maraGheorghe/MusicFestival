using System.Collections.Generic;

namespace MusicFestivalMPP.repository.interfaces;

public interface IRepository<TE>
{
    TE Save(TE entity);

    TE Find(long id);

    TE Delete(TE entity);

    TE Update(TE entity);

    IList<TE> FindAll();
}