package me.liamhbest.hycopycore;

import lombok.Getter;
import lombok.SneakyThrows;
import me.liamhbest.hycopycore.commands.ItemCommand;
import me.liamhbest.hycopycore.commands.SetChatDelayCommand;
import me.liamhbest.hycopycore.commands.gmcommands.GmaCommand;
import me.liamhbest.hycopycore.commands.gmcommands.GmcCommand;
import me.liamhbest.hycopycore.commands.gmcommands.GmsCommand;
import me.liamhbest.hycopycore.commands.gmcommands.GmspCommand;
import me.liamhbest.hycopycore.database.DatabaseManager;
import me.liamhbest.hycopycore.database.MySQLGetter;
import me.liamhbest.hycopycore.disguise.DisguiseSystem;
import me.liamhbest.hycopycore.disguise.nick.NickCommand;
import me.liamhbest.hycopycore.disguise.vanish.VanishCommand;
import me.liamhbest.hycopycore.managers.ChatManager;
import me.liamhbest.hycopycore.ranks.RankCommand;
import me.liamhbest.hycopycore.utility.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Core extends JavaPlugin {

    public static Core instance;
    public MySQLGetter mysql;
    public DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        instance = this;

        this.mysql = new MySQLGetter();
        this.databaseManager = new DatabaseManager(instance);
        try {
            mysql.connect();
            Bukkit.getLogger().info("[HYCOPY SUCCESSFUL] Database got connected!");
        } catch (Exception ignored){
            Bukkit.getLogger().info("[HYCOPY ERROR] Database could not be connected!");
        }

        registerCommands();
        registerListeners();

        DatabaseManager databaseManager = Core.instance.databaseManager;
        databaseManager.createTable("rankdata", "RANK VARCHAR(100)");
        databaseManager.createTable("disguisedata", "NICKED BOOLEAN", "VANISHED BOOLEAN", "NICKNAME VARCHAR(100)", "NICKRANK VARCHAR(100)", "NICKSKIN VARCHAR(100)");

        try {
            PreparedStatement ps = mysql.getConnection().prepareStatement("SELECT * FROM " + "serverdata" + " WHERE ID=?");
            ps.setString(1, "mainnetwork");

            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()){
                PreparedStatement ps1 = mysql.getConnection().prepareStatement("INSERT INTO " + "serverdata" + " "
                        + "(ID,CHAT_DELAY,SAME_MESSAGE_CHECK) VALUES (?,?,?)");
                ps1.setString(1, "mainnetwork");
                ps1.setInt(2, 3);
                ps1.setBoolean(3, true);
                ps1.executeUpdate();
            }

        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public void registerListeners(){
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new ChatManager(), this);
        pluginManager.registerEvents(new DisguiseSystem(), this);
    }

    public void registerCommands(){
        commandMap.register(pluginName, new GmcCommand());
        commandMap.register(pluginName, new GmsCommand());
        commandMap.register(pluginName, new GmspCommand());
        commandMap.register(pluginName, new GmaCommand());
        commandMap.register(pluginName, new RankCommand());
        commandMap.register(pluginName, new ItemCommand());
        commandMap.register(pluginName, new SetChatDelayCommand());
        commandMap.register(pluginName, new VanishCommand());
        commandMap.register(pluginName, new NickCommand());
    }

    @Getter
    private static CommandMap commandMap;
    private static final String pluginName = "HycopyCore";

    @SneakyThrows
    public Core(){
        final Server server = Bukkit.getServer();
        final Field bukkitCommandMap = server.getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        commandMap = (CommandMap) bukkitCommandMap.get(server);
    }

}












