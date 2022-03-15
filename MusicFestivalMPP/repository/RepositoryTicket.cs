using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using log4net;
using log4net.Util;
using MusicFestivalMPP.model;
using MusicFestivalMPP.repository.interfaces;

namespace MusicFestivalMPP.repository;

public class RepositoryTicket: IRepositoryTicket
{
    private static readonly ILog Log = LogManager.GetLogger("RepositoryUser");

    private IDictionary<String, string> properties;

    public RepositoryTicket(IDictionary<string, string> properties)
    {
        Log.Info("Creating RepositoryTicket.");
        this.properties = properties;
    }
    
    public Ticket Save(Ticket entity)
    {
        Log.InfoFormat("Saving ticket: {0}.", entity);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "insert into tickets (performance_id, owner, seats) values (@id, @owner, @seats)";
                IDbDataParameter parameterId = command.CreateParameter();
                parameterId.ParameterName = "@id";
                parameterId.Value = entity.Performance.Id;
                command.Parameters.Add(parameterId);
                IDbDataParameter parameterOwner = command.CreateParameter();
                parameterOwner.ParameterName = "@owner";
                parameterOwner.Value = entity.OwnerName;
                command.Parameters.Add(parameterOwner);
                IDbDataParameter parameterSeats = command.CreateParameter();
                parameterSeats.ParameterName = "@seats";
                parameterSeats.Value = entity.NoOfSeats;
                command.Parameters.Add(parameterSeats);
                int result = command.ExecuteNonQuery();
                Log.InfoFormat("Saved {0} instances.", result);
                return entity;
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        return null;
    }

    public Ticket Find(long id)
    {
        Log.InfoFormat("Finding ticket with ID: {0}.", id);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select * from tickets where ticket_id = @id";
                IDbDataParameter paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);
                using (var result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        Performance performance = GetPerformanceOfTicket(id);
                        String owner = result.GetString(2);
                        int seats = result.GetInt32(3);
                        Ticket ticket = new Ticket(id, performance, owner, seats);
                        Log.InfoFormat("Found ticket: {0}.", ticket);
                        return ticket;
                    }
                }
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        Log.InfoFormat("Didn't find the ticket with ID: {0}", id);
        return null;
    }

    public Ticket Delete(Ticket entity)
    {
        Log.InfoFormat("Deleting ticket: {0}.", entity);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "delete from tickets where ticket_id = @id";
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
    
    public Ticket Update(Ticket entity)
    {
        Log.InfoFormat("Updating ticket: {0}.", entity);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "update tickets set performance_id = @performance_id, owner = @owner, seats = @seats where ticket_id = @id)";
                IDbDataParameter parameterPerformance = command.CreateParameter();
                parameterPerformance.ParameterName = "@performance_id";
                parameterPerformance.Value = entity.Performance.Id;
                command.Parameters.Add(parameterPerformance);
                IDbDataParameter parameterOwner = command.CreateParameter();
                parameterOwner.ParameterName = "@owner";
                parameterOwner.Value = entity.OwnerName;
                command.Parameters.Add(parameterOwner);
                IDbDataParameter parameterSeats = command.CreateParameter();
                parameterSeats.ParameterName = "@seats";
                parameterSeats.Value = entity.NoOfSeats;
                command.Parameters.Add(parameterSeats);
                IDbDataParameter parameterId = command.CreateParameter();
                parameterId.ParameterName = "@id";
                parameterId.Value = entity.Id;
                command.Parameters.Add(parameterId);
                int result = command.ExecuteNonQuery();
                Log.InfoFormat("Saved {0} instances.", result);
                return entity;
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        return null;
    }

    public IList<Ticket> FindAll()
    {
        Log.Info("Finding all users");
        IDbConnection connection = DbUtils.GetConnection(properties);
        IList<Ticket> tickets = new List<Ticket>();
        try
        {
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select * from tickets";
                using (var result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        long id = result.GetInt32(0);
                        Performance performance = GetPerformanceOfTicket(id);
                        String owner = result.GetString(2);
                        int seats = result.GetInt32(3);
                        Ticket ticket = new Ticket(id, performance, owner, seats);
                        tickets.Add(ticket);
                    }
                }
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        Log.InfoExt("Exit FindAll.");
        return tickets;
    }

    public Performance GetPerformanceOfTicket(long ticketId)
    {
        Log.InfoFormat("Finding performance for the ticket with ID: {0}.", ticketId);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select p.performance_id, p.performance_date, p.place, p.no_of_seats, p.artist from performances p inner join tickets t on p.performance_id = t.performance_id where ticket_id = @id";
                IDbDataParameter paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = ticketId;
                command.Parameters.Add(paramId);
                using (var result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        DateTime date = DateTime.Parse(result.GetString(1));
                        String place = result.GetString(2);
                        int tickets = result.GetInt32(3);
                        int sold = GetNumberOfSoldTickets(ticketId);
                        int available = tickets - sold;
                        String artist = result.GetString(4);
                        Performance performance = new Performance(ticketId, date, place, available, sold, artist);
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
        Log.InfoFormat("Didn't find the performance of the ticket with ID: {0}", ticketId);
        return null;
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
}