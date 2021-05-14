package me.liamhbest.hycopycore.punishments.commands.punishgui;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.ranks.PlayerRank;
import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.HycopyPlayer;
import me.liamhbest.hycopycore.utility.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class PunishGUIListener implements Listener {

    public static ArrayList<UUID> inBanReasonEnter = new ArrayList<>();

    @EventHandler
    public void onPunishManagerClick(InventoryClickEvent event){
        if (event.getWhoClicked() instanceof Player){
            if (event.getView().getTitle().contains("Punish Manager (")) {
                if (event.getCurrentItem() == null) return;
                if (event.getCurrentItem().getItemMeta() == null) return;
                Player player = (Player) event.getWhoClicked();
                event.setCancelled(true);
                String displayName = event.getCurrentItem().getItemMeta().getDisplayName();

                if (displayName.equalsIgnoreCase("§cClose")) {
                    player.closeInventory();
                    player.updateInventory();
                }

                if (displayName.equalsIgnoreCase("§cBan Player")) {
                    HycopyPlayer hycopyPlayer = new HycopyPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
                    if (hycopyPlayer.getRankManager().getRank() != PlayerRank.ADMIN
                    && hycopyPlayer.getRankManager().getRank() != PlayerRank.GAME_MASTER
                    && hycopyPlayer.getRankManager().getRank() != PlayerRank.MODERATOR){
                        player.sendMessage(CC.RED + "You must be a moderator or higher to do this!");
                        return;
                    }

                    player.sendMessage(CC.GRAY + "Start by modifying the ban.");
                    player.openInventory(PunishGUI.getBanGUI(player, Bukkit.getOfflinePlayer(PunishGUI.currentPunishMenu.get(player.getUniqueId()))));
                }

                if (displayName.equalsIgnoreCase("§eMute Player")) {
                    player.sendMessage(CC.GRAY + "Start by modifying the mute.");
                    player.openInventory(PunishGUI.muteGUI(player, Bukkit.getOfflinePlayer(PunishGUI.currentPunishMenu.get(player.getUniqueId()))));
                }

                if (displayName.equalsIgnoreCase("§aPrevious Reports")) {
                    player.sendMessage(CC.GRAY + "Viewing all previous reports.");
                }

                if (displayName.equalsIgnoreCase("§bPrevious Punishments")) {
                    player.sendMessage(CC.GRAY + "Viewing all previous punishments.");
                }

            }
        }
    }


    @EventHandler
    public void onBanManagerClick(InventoryClickEvent event){
        if (event.getWhoClicked() instanceof Player){
            if (event.getView().getTitle().contains("Ban Manager (")) {
                if (event.getCurrentItem() == null) return;
                if (event.getCurrentItem().getItemMeta() == null) return;
                Player player = (Player) event.getWhoClicked();
                event.setCancelled(true);
                int slot = event.getSlot();
                Inventory banGUI = PunishGUI.banGUI.get(player.getUniqueId());

                if (slot == 49) {
                    player.closeInventory();
                    player.updateInventory();
                }

                if (slot == 50) {
                    player.openInventory(PunishGUI.punishGUI(player, Bukkit.getOfflinePlayer(PunishGUI.currentPunishMenu.get(player.getUniqueId()))));
                }

                if (slot == 29){
                    if (event.getCurrentItem().getItemMeta().getLore().get(4)
                            .equalsIgnoreCase(CC.translate("&dReason: &cNot Set"))) {
                        player.closeInventory();
                        player.sendMessage(CC.GREEN + "Please enter the reason in chat:");

                        Core.instance.getPacketSender().sendTitlePacket(player,
                                2, 20, 2, "Testing", "Testing Again");
                        player.sendMessage("After 1");
                        inBanReasonEnter.add(player.getUniqueId());
                        player.sendMessage("After 2");
                    }
                }

                if (slot == 33){
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate("&3Mode: &fTemporary"))) {
                        //Set to temporary
                        if (event.getClick().isRightClick()) {
                            player.sendMessage("Setting time...");
                            return;
                        }

                        ItemStack permanent = new ItemBuilder(Material.BEDROCK)
                                .setDisplayName("&3Mode: &fPermanent")
                                .setLore(
                                        CC.translate("&7The ban mode is currently"),
                                        CC.translate("&7toggled to permanent."),
                                        CC.translate(""),
                                        CC.translate("&7You can change between"),
                                        CC.translate("&7permanent and temporary mode."),
                                        CC.translate(""),
                                        CC.translate("&e-> Click to toggle")
                                )
                                .build();
                        banGUI.setItem(33, permanent);

                    } else {
                        //Set to permanent
                        ItemStack temporary = new ItemBuilder(Material.WATCH)
                                .setDisplayName("&3Mode: &fTemporary")
                                .setLore(
                                        CC.translate("&7The ban mode is currently"),
                                        CC.translate("&7toggled to temporary."),
                                        CC.translate(""),
                                        CC.translate("&7You can change between"),
                                        CC.translate("&7permanent and temporary mode."),
                                        CC.translate(""),
                                        CC.translate("&e-> Click to toggle"),
                                        CC.translate("&e-> Right Click to set time")
                                )
                                .build();
                        banGUI.setItem(33, temporary);
                    }
                }

            }
        }
    }

}


































