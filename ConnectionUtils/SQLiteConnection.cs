using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;

namespace ConnectionUtils;

public class SqLiteConnection: ConnectionFactory
{
    public override IDbConnection CreateConnection(IDictionary<string, string> properties)
    {
        String connectionString = properties["ConnectionString"];
        return new SQLiteConnection(connectionString);
    }
}