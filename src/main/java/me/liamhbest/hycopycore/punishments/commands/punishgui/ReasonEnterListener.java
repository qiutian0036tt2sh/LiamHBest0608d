package me.liamhbest.hycopycore.punishments.commands.punishgui;

import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ReasonEnterListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();

        if (PunishGUIListener.inBanReasonEnter.contains(player.getUniqueId())) {
            event.setCancelled(true);
            PunishGUIListener.inBanReasonEnter.remove(player.getUniqueId());

            player.openInventory(PunishGUI.getBanGUI(player, Bukkit.getOfflinePlayer(PunishGUI.currentPunishMenu.get(player.getUniqueId()))));

            ItemStack reason = new ItemBuilder(Material.NAME_TAG)
                    .setDisplayName("&bBan Reason")
                    .setLore(
                            CC.translate("&7Enter a reason for the ban. The"),
                            CC.translate("&7reason will be displayed for"),
                            CC.translate("&7the rule breaker and the staff."),
                            CC.translate(""),
                            CC.translate("&dReason: &f" + event.getMessage()),
                            CC.translate(""),
                            CC.translate("&e-> Click to change")
                    )
                    .build();
            PunishGUI.banGUI.get(player.getUniqueId()).setItem(29, reason);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        PunishGUIListener.inBanReasonEnter.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        PunishGUIListener.inBanReasonEnter.remove(event.getPlayer().getUniqueId());
    }
}











