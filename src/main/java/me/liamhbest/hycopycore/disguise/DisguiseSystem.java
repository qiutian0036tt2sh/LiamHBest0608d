package me.liamhbest.hycopycore.disguise;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.database.DatabaseManager;
import me.liamhbest.hycopycore.utility.HycopyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DisguiseSystem implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        DatabaseManager databaseManager = Core.instance.databaseManager;
        HycopyPlayer hycopyPlayer = new HycopyPlayer(player);
        if (!Bukkit.getServer().getName().contains("lobby")) return;

        databaseManager.createUser(player, "disguisedata");

        if (hycopyPlayer.getDisguiseManager().isVanished()) {
            for (Player targetPlayer : Bukkit.getOnlinePlayers()){
                targetPlayer.hidePlayer(player);
            }
        }

        for (Player targetPlayer : Bukkit.getOnlinePlayers()){
            HycopyPlayer targetHycopyPlayer = new HycopyPlayer(targetPlayer);
            if (targetHycopyPlayer.getDisguiseManager().isVanished()){
                player.hidePlayer(targetPlayer);
            }
        }

        if (hycopyPlayer.getDisguiseManager().isVanished()) {
            sendActionBar(player);
        }

    }


    public static void sendActionBar(Player player){

    }


}









