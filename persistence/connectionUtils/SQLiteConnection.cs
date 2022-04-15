using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;

namespace persistence.connectionUtils;

public class SqLiteConnection: ConnectionFactory
{
    public override IDbConnection CreateConnection(IDictionary<string, string> properties)
    {
        String connectionString = properties["ConnectionString"];
        Console.WriteLine(connectionString);
        return new SQLiteConnection(connectionString);
    }
}