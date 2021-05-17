package me.liamhbest.hycopycore.managers;

import me.liamhbest.hycopycore.punishments.commands.punishgui.PunishGUIListener;
import me.liamhbest.hycopycore.ranks.PlayerRank;
import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.HycopyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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

        if (PunishGUIListener.inBanReasonEnter.contains(player.getUniqueId())) {
            PunishGUIListener.inBanReasonEnter.remove(player.getUniqueId());
            return;
        }

        if (event.getMessage().equalsIgnoreCase(lastMessage.get(player.getUniqueId()))) {
            if (!rank.isStaffRank()){
                player.sendMessage(CC.GOLD + CC.STRIKE_THROUGH + "------------------------------------------");
                player.sendMessage(CC.RED + "You cannot say the same message twice!");
                player.sendMessage(CC.GOLD + CC.STRIKE_THROUGH + "------------------------------------------");
                return;
            }
        }

        if (chatCooldown.containsKey(player.getUniqueId())) {
            if (chatCooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                //Still have time left on cooldown
                if (hycopyPlayer.getRankManager().getRank() == PlayerRank.DEFAULT){
                    player.sendMessage(CC.GOLD + CC.STRIKE_THROUGH + "------------------------------------------");
                    player.sendMessage(CC.RED + "You can only chat once every 3 seconds! Ranked users bypass this restriction!");
                    player.sendMessage(CC.GOLD + CC.STRIKE_THROUGH + "------------------------------------------");
                    return;
                }
            }
        }

        lastMessage.put(player.getUniqueId(), event.getMessage());
        chatCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (3 * 1000));
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
















