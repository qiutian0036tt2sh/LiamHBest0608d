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

    }

}






