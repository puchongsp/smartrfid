package sushil.luc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Establishes remote db connection
 */
public class RemoteDatabaseConnection implements DatabaseConnection{

    private static Connection connection;

    /**
     *
     * Download jdbc mssql driver from here:
     * http://msdn.microsoft.com/en-us/sqlserver/aa937724.aspx
     * @return SQL Connection, returns existing connection if active, otherwise creates new connection
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     */
    public static Connection getConnection() throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        if(connection == null || connection.isClosed()){
            final String dburl = "192.168.2.4";
            final String port = "3306";
            final String dbname = "trakquip";
            final String username = "root";
            final String password = "password";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            connection = DriverManager.getConnection("jdbc:sqlserver://"+dburl+"\\"+port+";database="+dbname,username,password);
        }
        return connection;
    }

    @Override
    public void disconnect() {
        // TODO Auto-generated method stub

    }

}
