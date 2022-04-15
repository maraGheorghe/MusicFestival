using System.Collections.Generic;
using System.Data;

namespace persistence.connectionUtils;

public class DbUtils
{
    private static IDbConnection instance = null;

    public static IDbConnection GetConnection(IDictionary<string, string> properties)
    {
        if (instance == null || instance.State == System.Data.ConnectionState.Closed)
        {
            instance = GetNewConnection(properties);
            instance.Open();
        }

        return instance;
    }

    private static IDbConnection GetNewConnection(IDictionary<string, string> properties)
    {
        return ConnectionFactory.GetInstance().CreateConnection(properties);
    }
}