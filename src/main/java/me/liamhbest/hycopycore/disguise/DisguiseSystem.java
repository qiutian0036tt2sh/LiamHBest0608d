package me.liamhbest.hycopycore.disguise;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.database.DatabaseManager;
import me.liamhbest.hycopycore.utility.HycopyPlayer;
import net.md_5.bungee.BungeeServerInfo;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DisguiseSystem implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        DatabaseManager databaseManager = Core.instance.databaseManager;
        HycopyPlayer hycopyPlayer = new HycopyPlayer(player);
        if (!Bukkit.getServerName().contains("lobby")) return;

        databaseManager.createUser(player, "disguisedata");
        databaseManager.setField(player, "disguisedata", "NICKED", false);
        databaseManager.setField(player, "disguisedata", "VANISHED", false);

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
        String message = "&fYou are currently &cVANISHED";
        HycopyPlayer hycopyPlayer = new HycopyPlayer(player);

        while (true){
            if (hycopyPlayer.getDisguiseManager().isVanished()) {
                PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message.replace("&", "§") + "\"}"), (byte) 2);
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }


    }


}









