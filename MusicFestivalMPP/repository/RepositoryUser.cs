using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using log4net;
using log4net.Util;
using MusicFestivalMPP.model;
using MusicFestivalMPP.repository.interfaces;

namespace MusicFestivalMPP.repository;

public class RepositoryUser: IRepositoryUser
{

    private static readonly ILog Log = LogManager.GetLogger("RepositoryUser");

    private IDictionary<String, string> properties;

    public RepositoryUser(IDictionary<string, string> properties)
    {
        Log.Info("Creating RepositoryUser.");
        this.properties = properties;
    }

    public User Save(User entity)
    {
        Log.InfoFormat("Saving user: {0}.", entity);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "insert into users (username, password) values (@username, @password)";
                IDbDataParameter parameterUsername = command.CreateParameter();
                parameterUsername.ParameterName = "@username";
                parameterUsername.Value = entity.Username;
                command.Parameters.Add(parameterUsername);
                IDbDataParameter parameterPassword = command.CreateParameter();
                parameterPassword.ParameterName = "@password";
                parameterPassword.Value = entity.Password;
                command.Parameters.Add(parameterPassword);
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

    public User Find(long id)
    {
        Log.InfoFormat("Finding user with ID: {0}.", id);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select username, password from users where user_id=@id";
                IDbDataParameter paramId = command.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                command.Parameters.Add(paramId);
                using (var result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        String username = result.GetString(0);
                        String password = result.GetString(1);
                        User user = new User(id, username, password);
                        Log.InfoFormat("Found user: {0}.", user);
                        return user;
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

    public User Delete(User entity)
    {
        Log.InfoFormat("Deleting user: {0}.", entity);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "delete from users where user_id = @id";
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

    public User Update(User entity)
    {
        Log.InfoFormat("Updating user: {0}.", entity);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "update users set username = @username, password = @password where user_id = @id";
                IDbDataParameter parameterUsername = command.CreateParameter();
                parameterUsername.ParameterName = "@username";
                parameterUsername.Value = entity.Username;
                command.Parameters.Add(parameterUsername);
                IDbDataParameter parameterPassword = command.CreateParameter();
                parameterPassword.ParameterName = "@password";
                parameterPassword.Value = entity.Password;
                command.Parameters.Add(parameterPassword);
                IDbDataParameter parameterId= command.CreateParameter();
                parameterId.ParameterName = "@id";
                parameterId.Value = entity.Id;
                command.Parameters.Add(parameterId);
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

    public IList<User> FindAll()
    {
        Log.Info("Finding all users");
        IDbConnection connection = DbUtils.GetConnection(properties);
        IList<User> users = new List<User>();
        try
        {
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "select user_id, username, password from users";
                using (var result = command.ExecuteReader())
                {
                    while (result.Read())
                    {
                        int userId = result.GetInt32(0);
                        String username = result.GetString(1);
                        String password = result.GetString(2);
                        User user = new User(userId, username, password);
                        users.Add(user);
                    }
                }
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        Log.InfoExt("Exit FindAll.");
        return users;
    }

    public User FindUserByUsername(string username)
    {
        Log.InfoFormat("Finding user with username: {0}.", username);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select user_id, password from users where username=@username";
                IDbDataParameter parameterUsername = command.CreateParameter();
                parameterUsername.ParameterName = "@username";
                parameterUsername.Value = username;
                command.Parameters.Add(parameterUsername);
                using (var result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        int userId = result.GetInt32(0);
                        String password = result.GetString(1);
                        User user = new User(userId, username, password);
                        Log.InfoFormat("Found user: {0}.", user);
                        return user;
                    }
                }
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        Log.InfoFormat("Didn't find the user with username: {0}", username);
        return null;
    }

    public User CheckConnection(string username, string password)
    {
        Log.InfoFormat("Check connection for user with username: {0}.", username);
        IDbConnection connection = DbUtils.GetConnection(properties);
        try
        {
            using (IDbCommand command = connection.CreateCommand())
            {
                command.CommandText = "select user_id from users where username=@username and password = @password";
                IDbDataParameter parameterUsername = command.CreateParameter();
                parameterUsername.ParameterName = "@username";
                parameterUsername.Value = username;
                command.Parameters.Add(parameterUsername);
                IDbDataParameter parameterPassword = command.CreateParameter();
                parameterPassword.ParameterName = "@password";
                parameterPassword.Value = password;
                command.Parameters.Add(parameterPassword);
                using (var result = command.ExecuteReader())
                {
                    if (result.Read())
                    {
                        int userId = result.GetInt32(0);
                        User user = new User(userId, username, password);
                        Log.InfoFormat("Found user: {0}.", user);
                        return user;
                    }
                }
            }
        }
        catch (SQLiteException e)
        {
            Log.Error(e);
        }
        Log.InfoFormat("Didn't connect user with username: {0}", username);
        return null;
    }
}