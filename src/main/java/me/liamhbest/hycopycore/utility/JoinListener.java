package me.liamhbest.hycopycore.utility;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.database.DatabaseManager;
import me.liamhbest.hycopycore.ranks.PlayerRank;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        DatabaseManager databaseManager = Core.instance.databaseManager;
        Player player = event.getPlayer();

        if (!databaseManager.userExists(player, "rankdata")) {
            databaseManager.createUser(player, "rankdata");
            databaseManager.setField(player, "rankdata", "RANK", PlayerRank.DEFAULT.name());
        }

        if (!databaseManager.userExists(player, "playerpunishmentdata")) {
            databaseManager.createUser(player, "playerpunishmentdata");
            databaseManager.setField(player, "playerpunishmentdata", "BANNED", false);
            databaseManager.setField(player, "playerpunishmentdata", "MUTED", false);
            databaseManager.setField(player, "playerpunishmentdata", "PERM_BANNED", false);
            databaseManager.setField(player, "playerpunishmentdata", "BAN_EXPIRE", 0L);
            databaseManager.setField(player, "playerpunishmentdata", "MUTE_EXPIRE", 0L);
            databaseManager.setField(player, "playerpunishmentdata", "REASON_BAN", null);
            databaseManager.setField(player, "playerpunishmentdata", "REASON_MUTE", null);
            databaseManager.setField(player, "playerpunishmentdata", "BAN_ID", null);
            databaseManager.setField(player, "playerpunishmentdata", "MUTE_ID", null);
        }

    }

}






