package me.liamhbest.hycopycore.punishments.commands.punishgui;

import me.liamhbest.hycopycore.ranks.PlayerRank;
import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.HycopyPlayer;
import me.liamhbest.hycopycore.utility.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PunishGUI {

    //                     KEY     VALUE
    public static HashMap<UUID, UUID> currentPunishMenu = new HashMap<>();
    public static HashMap<UUID, String> banReason = new HashMap<>();

    public static Inventory punishGUI(Player player, OfflinePlayer targetPlayer) {
        Inventory punishGUI = Bukkit.createInventory(null, 54, "Punish Manager (" + targetPlayer.getName() + ")");
        currentPunishMenu.remove(player.getUniqueId(), targetPlayer.getUniqueId());
        currentPunishMenu.put(player.getUniqueId(), targetPlayer.getUniqueId());
        HycopyPlayer targetHycopyPlayer = new HycopyPlayer(targetPlayer);

        ItemStack profile = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta profileMeta = (SkullMeta) profile.getItemMeta();

        profileMeta.setDisplayName(targetHycopyPlayer.getRankManager().getRank().getPrefixWithSpace()
                .replace("%", targetHycopyPlayer.getMetadataManager().getPlus()) +
                targetPlayer.getName());

        profileMeta.setOwner(targetPlayer.getName());
        profile.setItemMeta(profileMeta);
        punishGUI.setItem(13, profile);

        HycopyPlayer hycopyPlayer = new HycopyPlayer(player);
        if (hycopyPlayer.getRankManager().getRank() == PlayerRank.HELPER){
            ItemStack banItem = new ItemBuilder(Material.WOOL, 1, (byte)14)
                    .setDisplayName("&cBan Player")
                    .setLore(
                            CC.translate("&7Ban this player from entering"),
                            CC.translate("&7the Hycopy Network."),
                            CC.translate(""),
                            CC.translate("&cOnly for Moderator and above!")
                    )
                    .build();
            punishGUI.setItem(28, banItem);
        } else {
            ItemStack banItem = new ItemBuilder(Material.WOOL, 1, (byte)14)
                    .setDisplayName("&cBan Player")
                    .setLore(
                            CC.translate("&7Ban this player from entering"),
                            CC.translate("&7the Hycopy Network."),
                            CC.translate(""),
                            CC.translate("&cClick to ban")
                    )
                    .build();
            punishGUI.setItem(28, banItem);
        }

        ItemStack muteItem = new ItemBuilder(Material.WOOL, 1, (byte)4)
                .setDisplayName("&eMute Player")
                .setLore(
                        CC.translate("&7Mute this player from chatting"),
                        CC.translate("&7on the Hycopy Network."),
                        CC.translate(""),
                        CC.translate("&eClick to mute")
                )
                .build();
        punishGUI.setItem(30, muteItem);

        ItemStack previousReports = new ItemBuilder(Material.BOOK)
                .setDisplayName("&aPrevious Reports")
                .setLore(
                        CC.translate("&7View all previous reports that"),
                        CC.translate("&7has been issued for this player."),
                        CC.translate(""),
                        CC.translate("&eClick to view")
                )
                .build();
        punishGUI.setItem(32, previousReports);

        ItemStack previousPunishments = new ItemBuilder(Material.PAPER)
                .setDisplayName("&bPrevious Punishments")
                .setLore(
                        CC.translate("&7View all previous punishments"),
                        CC.translate("&7that has been issued for this player."),
                        CC.translate("&7Current punishments as well as expired."),
                        CC.translate(""),
                        CC.translate("&eClick to view")
                )
                .build();
        punishGUI.setItem(34, previousPunishments);

        ItemStack close = new ItemBuilder(Material.BARRIER)
                .setDisplayName("&cClose")
                .build();
        punishGUI.setItem(49, close);

        return punishGUI;
    }

    public static Map<UUID, Inventory> banGUI = new HashMap<>();
    public static Inventory getBanGUI(Player player, OfflinePlayer targetPlayer){
        Inventory inventory = Bukkit.createInventory(null, 54, "Ban Manager (" + targetPlayer.getName() + ")");

        HycopyPlayer targetHycopyPlayer = new HycopyPlayer(targetPlayer);
        ItemStack profile = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta profileMeta = (SkullMeta) profile.getItemMeta();
        profileMeta.setDisplayName(targetHycopyPlayer.getRankManager().getRank().getPrefixWithSpace()
                .replace("%", targetHycopyPlayer.getMetadataManager().getPlus()) +
                targetPlayer.getName());
        profileMeta.setOwner(targetPlayer.getName());
        profile.setItemMeta(profileMeta);
        inventory.setItem(13, profile);

        ItemStack reason = new ItemBuilder(Material.NAME_TAG)
                .setDisplayName("&bBan Reason")
                .setLore(
                        CC.translate("&7Enter a reason for the ban. The"),
                        CC.translate("&7reason will be displayed for"),
                        CC.translate("&7the rule breaker and the staff."),
                        CC.translate(""),
                        CC.translate("&dReason: &cNot Set"),
                        CC.translate(""),
                        CC.translate("&e-> Click to set")
                )
                .build();
        inventory.setItem(29, reason);

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
        //inventory.setItem(31, confirm);

        ItemStack cannotConfirm = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short)14)
                .setDisplayName("&cConfirm Ban")
                .setLore(
                        CC.translate("&7Confirm and issue the ban"),
                        CC.translate("&7you created. This action"),
                        CC.translate("&7can be revoked with /unban."),
                        CC.translate(""),
                        CC.translate("&cModify the ban before confirming!")
                )
                .build();
        inventory.setItem(31, cannotConfirm);

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
        inventory.setItem(33, permanent);

        ItemStack close = new ItemBuilder(Material.BARRIER)
                .setDisplayName("&cClose")
                .build();
        inventory.setItem(49, close);

        ItemStack goBack = new ItemBuilder(Material.ARROW)
                .setDisplayName("&aGo Back")
                .setLore(CC.translate("&7To Punishment Manager"))
                .build();
        inventory.setItem(50, goBack);
        banGUI.put(player.getUniqueId(), inventory);

        return inventory;
    }


    public static Inventory muteGUI(Player player, OfflinePlayer targetPlayer){
        Inventory inventory = Bukkit.createInventory(null, 54, "Mute Manager (" + targetPlayer.getName() + ")");

        HycopyPlayer targetHycopyPlayer = new HycopyPlayer(targetPlayer);
        ItemStack profile = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta profileMeta = (SkullMeta) profile.getItemMeta();
        profileMeta.setDisplayName(targetHycopyPlayer.getRankManager().getRank().getPrefixWithSpace()
                .replace("%", targetHycopyPlayer.getMetadataManager().getPlus()) +
                targetPlayer.getName());
        profileMeta.setOwner(targetPlayer.getName());
        profile.setItemMeta(profileMeta);
        inventory.setItem(13, profile);

        return inventory;
    }




}

































