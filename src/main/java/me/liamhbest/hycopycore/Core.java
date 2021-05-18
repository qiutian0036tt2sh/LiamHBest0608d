package me.liamhbest.hycopycore;

import lombok.Getter;
import lombok.SneakyThrows;
import me.liamhbest.hycopycore.commands.ItemCommand;
import me.liamhbest.hycopycore.commands.gmcommands.GmaCommand;
import me.liamhbest.hycopycore.commands.gmcommands.GmcCommand;
import me.liamhbest.hycopycore.commands.gmcommands.GmsCommand;
import me.liamhbest.hycopycore.commands.gmcommands.GmspCommand;
import me.liamhbest.hycopycore.database.DatabaseManager;
import me.liamhbest.hycopycore.database.MySQLGetter;
import me.liamhbest.hycopycore.managers.ChatManager;
import me.liamhbest.hycopycore.punishments.commands.PunishCommand;
import me.liamhbest.hycopycore.punishments.commands.punishgui.PunishGUIListener;
import me.liamhbest.hycopycore.punishments.commands.punishgui.ReasonEnterListener;
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
        databaseManager.createTable("playerpunishmentdata", "BANNED BOOLEAN", "MUTED BOOLEAN", "PERM_BANNED BOOLEAN", "BAN_EXPIRE LONG", "MUTE_EXPIRE LONG", "REASON_BAN VARCHAR(300)", "REASON_MUTE VARCHAR(300)", "BAN_ID VARCHAR(10)", "MUTE_ID VARCHAR(10)");

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
        pluginManager.registerEvents(new PunishGUIListener(), this);
        pluginManager.registerEvents(new ReasonEnterListener(), this);
        pluginManager.registerEvents(new ChatManager(), this);
    }

    public void registerCommands(){
        commandMap.register(pluginName, new GmcCommand());
        commandMap.register(pluginName, new GmsCommand());
        commandMap.register(pluginName, new GmspCommand());
        commandMap.register(pluginName, new GmaCommand());
        commandMap.register(pluginName, new RankCommand());
        commandMap.register(pluginName, new ItemCommand());
        commandMap.register(pluginName, new PunishCommand());
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












