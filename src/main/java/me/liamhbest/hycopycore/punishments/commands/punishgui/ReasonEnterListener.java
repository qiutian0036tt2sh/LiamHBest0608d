package me.liamhbest.hycopycore.punishments.commands.punishgui;

import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;

public class ReasonEnterListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();

        if (PunishGUIListener.inBanReasonEnter.contains(player.getUniqueId())) {
            event.setCancelled(true);
            String reason = event.getMessage();
            PunishGUI.banReason.put(player.getUniqueId(), reason);

            player.openInventory(PunishGUI.getBanGUI(player, Bukkit.getOfflinePlayer(PunishGUI.currentPunishMenu.get(player.getUniqueId()))));

            ItemStack item = new ItemStack(Material.NAME_TAG);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(CC.translate("&bBan Reason"));
            ArrayList<String> lore = new ArrayList<>();
            lore.add(CC.translate("&7Enter a reason for the ban. The"));
            lore.add(CC.translate("&7reason will be displayed for"));
            lore.add(CC.translate("&7the rule breaker and the staff."));
            lore.add(" ");
            lore.add(ChatColor.LIGHT_PURPLE + "Reason:");
            for (String s : ChatPaginator.wordWrap(reason, 37)){
                lore.add(ChatColor.WHITE + s);
            }
            lore.add(" ");
            lore.add(CC.translate("&e-> Click to change"));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            PunishGUI.banGUI.get(player.getUniqueId()).setItem(29, item);

            if (PunishGUI.permanentToggleMode.contains(player.getUniqueId())) {
                //Reason set and permanent mode toggle
                ItemStack confirm = new ItemBuilder(Material.SLIME_BALL)
                        .setDisplayName("&aConfirm Ban")
                        .setLore(
                                CC.translate("&7Confirm and issue the ban"),
                                CC.translate("&7you created. This action"),
                                CC.translate("&7can be revoked with /unban."),
                                CC.translate(""),
                                CC.translate("&eClick to confirm")
                        )
                        .build();
                PunishGUI.banGUI.get(player.getUniqueId()).setItem(31, confirm);
            }

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











