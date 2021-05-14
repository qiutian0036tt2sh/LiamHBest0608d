package me.liamhbest.hycopycore.ranks;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.database.DatabaseManager;
import me.liamhbest.hycopycore.utility.Logger;
import org.bukkit.OfflinePlayer;

public class RankManager {

    private OfflinePlayer offlinePlayer;
    public RankManager(OfflinePlayer offlinePlayer){
        this.offlinePlayer = offlinePlayer;
    }

    private final DatabaseManager databaseManager = Core.instance.databaseManager;

    public PlayerRank getRank(){
        if (databaseManager.userExists(offlinePlayer, "rankdata")) {
            return PlayerRank.valueOf(databaseManager.getField(offlinePlayer, "rankdata", "RANK").toString());
        } else {
            Logger.log("The player you are trying to modify has never played on Hycopy before!");
            return null;
        }
    }

    public void setRank(PlayerRank playerRank){
        if (databaseManager.userExists(offlinePlayer, "rankdata")) {
            databaseManager.setField(offlinePlayer, "rankdata", "RANK", playerRank.name());
        } else {
            Logger.log("The player you are trying to modify has never played on Hycopy before!");
        }
    }

}









