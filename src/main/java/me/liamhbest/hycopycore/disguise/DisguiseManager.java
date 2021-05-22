package me.liamhbest.hycopycore.disguise;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.database.DatabaseManager;
import org.bukkit.OfflinePlayer;

public class DisguiseManager {

    private final OfflinePlayer offlinePlayer;
    public DisguiseManager(OfflinePlayer offlinePlayer){
        this.offlinePlayer = offlinePlayer;
    }
    private final DatabaseManager databaseManager = Core.instance.databaseManager;

    public boolean isNicked(){
        return (boolean) databaseManager.getField(offlinePlayer, "disguisedata", "NICKED");
    }

    public boolean isVanished(){
        return (boolean) databaseManager.getField(offlinePlayer, "disguisedata", "VANISHED");
    }

    public void setVanished(boolean value){
        databaseManager.setField(offlinePlayer, "disguisedata", "VANISHED", value);
    }












}
