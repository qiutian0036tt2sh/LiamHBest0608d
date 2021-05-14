package me.liamhbest.hycopycore.punishments;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.database.DatabaseManager;
import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PunishmentManager {

    private OfflinePlayer offlinePlayer;
    public PunishmentManager(OfflinePlayer offlinePlayer){
        this.offlinePlayer = offlinePlayer;
    }
    private final DatabaseManager databaseManager = Core.instance.databaseManager;

    public boolean isMuted(){
        return (boolean) databaseManager.getField(offlinePlayer, "playerpunishmentdata", "MUTED");
    }

    public boolean isBanned(){
        return (boolean) databaseManager.getField(offlinePlayer, "playerpunishmentdata", "BANNED");
    }

    public boolean isTempBanned(){
        return (boolean) databaseManager.getField(offlinePlayer, "playerpunishmentdata", "BANNED")
                && !(boolean) databaseManager.getField(offlinePlayer, "playerpunishmentdata", "PERM_BANNED");
    }

    public boolean isPermBanned(){
        return (boolean) databaseManager.getField(offlinePlayer, "playerpunishmentdata", "BANNED")
                && (boolean) databaseManager.getField(offlinePlayer, "playerpunishmentdata", "PERM_BANNED");
    }

    public void kick(String reason){
        try {
            Player player = Bukkit.getPlayer(offlinePlayer.getUniqueId());
            player.kickPlayer(CC.translate("&cThis is the kick text\n&bThis is a new line on the kick text"));
        } catch (Exception exception){
            Logger.log("The player you are trying to kick is not online!");
        }
    }

    public void warn(String reason){
        try {
            Player player = Bukkit.getPlayer(offlinePlayer.getUniqueId());
            player.kickPlayer(CC.translate("&cThis is the warn text\n&bThis is a new line on the warn text"));
        } catch (Exception exception){
            Logger.log("The player you are trying to warn is not online so the kicking could not happen!");
        }
    }



}
