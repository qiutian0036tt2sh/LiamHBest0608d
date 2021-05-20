package me.liamhbest.hycopycore.managers;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.ranks.PlayerRank;
import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.HycopyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class ChatManager implements Listener {

    private final HashMap<UUID, String> lastMessage = new HashMap<>();
    private final HashMap<UUID, Long> chatCooldown = new HashMap<>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        HycopyPlayer hycopyPlayer = new HycopyPlayer(player);
        PlayerRank rank = hycopyPlayer.getRankManager().getRank();
        event.setCancelled(true);

        if (event.getMessage().equalsIgnoreCase(lastMessage.get(player.getUniqueId()))) {
            if (!rank.isStaffRank()){
                player.sendMessage(CC.GOLD + CC.STRIKE_THROUGH + "------------------------------------------");
                player.sendMessage(CC.RED + "You cannot say the same message twice!");
                player.sendMessage(CC.GOLD + CC.STRIKE_THROUGH + "------------------------------------------");
                return;
            }
        }

        lastMessage.put(player.getUniqueId(), event.getMessage());
        int delay = 3;

        try {
            PreparedStatement ps = Core.instance.mysql.getConnection().prepareStatement("SELECT " + "CHAT_DELAY" + " FROM " + "serverdata" + " WHERE ID=?");
            ps.setString(1, "mainnetwork");
            ResultSet resultSet = ps.executeQuery();
            Object result;

            if (resultSet.next()){
                result = resultSet.getObject("CHAT_DELAY");
                delay = (int) result;
            }

        } catch (SQLException exception){
            exception.printStackTrace();
            return;
        }

        if (chatCooldown.containsKey(player.getUniqueId())) {
            if (chatCooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                //Still have time left on cooldown
                if (hycopyPlayer.getRankManager().getRank() == PlayerRank.DEFAULT){

                    player.sendMessage(CC.GOLD + CC.STRIKE_THROUGH + "------------------------------------------");
                    if (delay == 1){
                        player.sendMessage(CC.RED + "You can only chat once every " + delay + " second! Ranked users bypass this restriction!");
                    } else {
                        player.sendMessage(CC.RED + "You can only chat once every " + delay + " seconds! Ranked users bypass this restriction!");
                    }
                    player.sendMessage(CC.GOLD + CC.STRIKE_THROUGH + "------------------------------------------");
                    return;
                }
            }
        }

        chatCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (delay * 1000));
        for (Player target : Bukkit.getOnlinePlayers()){
            if (hycopyPlayer.getRankManager().getRank() == PlayerRank.DEFAULT){
                target.sendMessage(PlayerRank.DEFAULT.getPrefixWithSpace()
                        .replace("%", hycopyPlayer.getMetadataManager().getPlus())+
                        player.getName() +  ": " + event.getMessage());
                return;
            }

            target.sendMessage(hycopyPlayer.getRankManager().getRank().getPrefixWithSpace().replace("%",
                    hycopyPlayer.getMetadataManager().getPlus()) +
                    player.getName() + ChatColor.WHITE + ": " + event.getMessage());
        }

    }

}
















