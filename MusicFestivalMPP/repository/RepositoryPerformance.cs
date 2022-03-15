using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using MusicFestivalMPP.model;
using log4net;
using log4net.Util;
using MusicFestivalMPP.repository.interfaces;

namespace MusicFestivalMPP.repository;

public class RepositoryPerformance: IRepositoryPerformance
{
    private static readonly ILog Log = LogManager.GetLogger("RepositoryPerformance");

    private IDictionary<String, string> properties;

    public RepositoryPerformance(IDictionary<string, string> properties)
    {
        Log.Info("Creating RepositoryPerformance.");
        this.properties = properties;
    }
    
    public Performance Save(Performance entity)
    {
        Log.InfoFormat("Saving performance: {0}.", entity);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText =
                    "insert into performances (performance_date, place, no_of_seats, artist) values (@date, @place, @tickets, @artist)";
                IDbDataParameter parameterDate = command.CreateParameter();
                parameterDate.ParameterName = "@date";
                parameterDate.Value = entity.Date.ToString("yyyy-MM-dd");
                command.Parameters.Add(parameterDate);
                IDbDataParameter parameterPlace = command.CreateParameter();
                parameterPlace.ParameterName = "@place";
                parameterPlace.Value = entity.Place;
                command.Parameters.Add(parameterPlace);
                IDbDataParameter parameterTickets = command.CreateParameter();
                parameterTickets.ParameterName = "@tickets";
                parameterTickets.Value = entity.NoOfAvailableSeats + entity.NoOfSoldSeats;
                command.Parameters.Add(parameterTickets);
                IDbDataParameter parameterArtist = command.CreateParameter();
                parameterArtist.ParameterName = "@artist";
                parameterArtist.Value = entity.Artist;
                command.Parameters.Add(parameterArtist);
                int result = command.ExecuteNonQuery();
                Log.InfoFormat("Saved {0} instances.", result);
                return entity;
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
            Console.WriteLine(e);
        }
        return null;
    }

    public Performance Find(long id)
    {
        Log.InfoFormat("Finding performance with ID: {0}.", id);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select * from performances where performance_id = @id";
                IDbDataParameter paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);
                using (var result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        DateTime date = DateTime.Parse(result.GetString(1));
                        String place = result.GetString(2);
                        int tickets = result.GetInt32(3);
                        int sold = GetNumberOfSoldTickets(id);
                        int available = tickets - sold;
                        String artist = result.GetString(4);
                        Performance performance = new Performance(id, date, place, available, sold, artist);
                        Log.InfoFormat("Found performance: {0}.", performance);
                        return performance;
                    }
                }
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        Log.InfoFormat("Didn't find the user with ID: {0}", id);
        return null;
    }

    public Performance Delete(Performance entity)
    {
        Log.InfoFormat("Deleting performance: {0}.", entity);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "delete from performances where performance_id = @id";
                IDbDataParameter parameterId = command.CreateParameter();
                parameterId.ParameterName = "@id";
                parameterId.Value = entity.Id;
                command.Parameters.Add(parameterId);
                int result = command.ExecuteNonQuery();
                Log.InfoFormat("Deleted {0} instances.", result);
                return entity;
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        return null;
    }

    public Performance Update(Performance entity)
    {
        Log.InfoFormat("Updating performance: {0}.", entity);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText =
                    "update performances set performance_date = @date, place = @place, no_of_seats = @tickets, artist = @artist where performance_id = @id";
                IDbDataParameter parameterDate = command.CreateParameter();
                parameterDate.ParameterName = "@date";
                parameterDate.Value = entity.Date.ToString("yyyy-MM-dd");
                command.Parameters.Add(parameterDate);
                IDbDataParameter parameterPlace = command.CreateParameter();
                parameterPlace.ParameterName = "@place";
                parameterPlace.Value = entity.Place;
                command.Parameters.Add(parameterPlace);
                IDbDataParameter parameterTickets = command.CreateParameter();
                parameterTickets.ParameterName = "@tickets";
                parameterTickets.Value = entity.NoOfAvailableSeats + entity.NoOfSoldSeats;
                command.Parameters.Add(parameterTickets);
                IDbDataParameter parameterArtist = command.CreateParameter();
                parameterArtist.ParameterName = "@artist";
                parameterArtist.Value = entity.Artist;
                command.Parameters.Add(parameterArtist);
                IDbDataParameter parameterId = command.CreateParameter();
                parameterId.ParameterName = "@id";
                parameterId.Value = entity.Id;
                int result = command.ExecuteNonQuery();
                Log.InfoFormat("Updated {0} instances.", result);
                return entity;
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        return null;
    }

    public IList<Performance> FindAll()
    {
        Log.Info("Finding all performances");
        IDbConnection connection = DbUtils.GetConnection(properties);
        IList<Performance> performances = new List<Performance>();
        try
        {
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from performances";
                using (var result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        int performanceId = result.GetInt32(0);
                        DateTime date = DateTime.Parse(result.GetString(1));
                        String place = result.GetString(2);
                        int tickets = result.GetInt32(3);
                        int sold = GetNumberOfSoldTickets(performanceId);
                        int available = tickets - sold;
                        String artist = result.GetString(4);
                        Performance performance = new Performance(performanceId, date, place, available,sold, artist);
                        performances.Add(performance);
                    }
                }
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        Log.InfoExt("Exit FindAll.");
        return performances;
    }
    
    private int GetNumberOfSoldTickets(long id) {
        Log.Info("Sold tickets.");
        IDbConnection connection = DbUtils.GetConnection(properties);
        int count = 0;
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select sum(seats) as summ from tickets where performance_id = @id";
                IDbDataParameter parameterId = command.CreateParameter();
                parameterId.ParameterName = "@id";
                parameterId.Value = id;
                command.Parameters.Add(parameterId);
                using (var result = command.ExecuteReader())
                {
                    if(result.Read() && !result.IsDBNull(0))
                       count = result.GetInt32(0);
                }
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        Log.InfoExt(count);
        return count;
    }

    public IList<Performance> FindAllPerformancesForADay(DateTime date)
    {
        Log.InfoFormat("Finding all performances for the day: {0}.", date);
        IDbConnection connection = DbUtils.GetConnection(properties);
        IList<Performance> performances = new List<Performance>();
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select * from performances where performance_date = @date";
                IDbDataParameter parameterDate = command.CreateParameter();
                parameterDate.ParameterName = "@date";
                parameterDate.Value = date;
                command.Parameters.Add(parameterDate); 
                using (var result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        int performanceId = result.GetInt32(0);
                        String place = result.GetString(2);
                        int tickets = result.GetInt32(3);
                        int sold = GetNumberOfSoldTickets(performanceId);
                        int available = tickets - sold;
                        String artist = result.GetString(4);
                        Performance performance = new Performance(performanceId, date, place, available,sold, artist);
                        performances.Add(performance);
                    }
                }
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        Log.InfoExt("Exit FindAll for a day.");
        return performances;
    }
}