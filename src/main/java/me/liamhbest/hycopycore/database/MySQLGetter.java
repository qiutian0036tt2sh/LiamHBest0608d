package me.liamhbest.hycopycore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLGetter {

    private Connection connection;

    public boolean isConnected(){
        return (connection != null);
    }

    public void connect() throws ClassNotFoundException, SQLException{
        final String database = "customer_110229_hycopycore";
        final String host = "eu02-sql.pebblehost.com";
        final String port = "3306";
        final String username = "customer_110229_hycopycore";
        final String password = "hYcopycore600%";

        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" + port + "/" + database + "?useSSL=false",
                    username, password);
        }
    }

    public void disconnect(){
        if (!isConnected()) {
            try {
                connection.close();
            } catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }

    public Connection getConnection(){
        return connection;
    }

}
