package me.liamhbest.hycopycore.database;

import me.liamhbest.hycopycore.Core;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    private Core plugin;
    public DatabaseManager(Core plugin){
        this.plugin = plugin;
    }

    public void createTable(String tableName, String... input){
        PreparedStatement ps;
        try {
            StringBuilder statement = new StringBuilder();
            statement.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" ");
            statement.append("(NAME VARCHAR(100),UUID VARCHAR(100)");
            statement.append(",");

            for (String s : input){
                statement.append(s).append(",");
            }

            statement.append("PRIMARY KEY(NAME))");

            ps = plugin.mysql.getConnection().prepareStatement(statement.toString());
            ps.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public void createUser(OfflinePlayer player, String tableName){
        try {
            if (!userExists(player, tableName)) {
                PreparedStatement ps = plugin.mysql.getConnection().prepareStatement("INSERT INTO " + tableName + " "
                        + "(NAME, UUID) VALUES (?, ?)");
                ps.setString(1, player.getName());
                ps.setString(2, player.getUniqueId().toString());
                ps.executeUpdate();
            }
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public boolean userExists(OfflinePlayer player, String tableName){
        try {
            PreparedStatement ps = plugin.mysql.getConnection().prepareStatement("SELECT * FROM " + tableName + " WHERE UUID=?");
            ps.setString(1, player.getUniqueId().toString());

            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return false;
    }

    public void setField(OfflinePlayer player, String tableName, Object findValue, Object setValue){
        try {
            PreparedStatement ps = plugin.mysql.getConnection().prepareStatement("UPDATE " + tableName + " SET " + findValue + "=? WHERE UUID=?");
            ps.setObject(1, setValue);
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();

        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public Object getField(OfflinePlayer player, String tableName, String value){
        try {
            PreparedStatement ps = plugin.mysql.getConnection().prepareStatement("SELECT " + value + " FROM " + tableName + " WHERE UUID=?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = ps.executeQuery();
            Object result;

            if (resultSet.next()){
                result = resultSet.getObject(value);
                return result;
            }

        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }

    public void deleteTableData(String tableName){
        try {
            PreparedStatement ps = plugin.mysql.getConnection().prepareStatement("TRUNCATE " + tableName);
            ps.executeQuery();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public void removeUser(OfflinePlayer player, String tableName){
        try {
            PreparedStatement ps = plugin.mysql.getConnection().prepareStatement("DELETE FROM " + tableName + " WHERE UUID=?");
            ps.setString(1, player.getUniqueId().toString());
            ps.executeQuery();

        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

}
