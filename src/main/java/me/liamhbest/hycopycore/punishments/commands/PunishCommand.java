package me.liamhbest.hycopycore.punishments.commands;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.database.DatabaseManager;
import me.liamhbest.hycopycore.punishments.commands.punishgui.PunishGUI;
import me.liamhbest.hycopycore.ranks.PlayerRank;
import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.HycopyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PunishCommand extends Command {

    public PunishCommand(){
        super("punish");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) {
            CC.sendMessage(sender, "&cThis can only be performed by a player! Use /ban, /mute, /kick and /warn instead");
            return true;
        }
        Player player = (Player) sender;
        HycopyPlayer hycopyPlayer = new HycopyPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        if (!hycopyPlayer.getRankManager().getRank().isStaffRank()){
            player.sendMessage(CC.RED + "You are not allowed to do this!");
            return true;
        }

        if (args.length == 0){
            player.sendMessage(CC.translate("&cMissing arguments! Please enter a player to punish!"));
            return true;
        }

        if (args[0].equalsIgnoreCase("ban")) {

            if (args.length > 1){
                DatabaseManager databaseManager = Core.instance.databaseManager;
                if (databaseManager.userExists(Bukkit.getOfflinePlayer(args[1]), "playerpunishmentdata")) {
                    OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[1]);

                    player.sendMessage(CC.GREEN + "Currently banning " + targetPlayer.getName() + ".");
                    player.openInventory(PunishGUI.getBanGUI(player, targetPlayer));
                    PunishGUI.currentPunishMenu.put(player.getUniqueId(), targetPlayer.getUniqueId());

                } else {
                    player.sendMessage(CC.translate("&cAre you sure you spelled their name right? The player you entered does not exist in our database."));
                }
            } else {
                player.sendMessage(CC.RED + "Missing arguments! Please specify a player to ban them!");
            }

            return true;
        }

        DatabaseManager databaseManager = Core.instance.databaseManager;
        if (databaseManager.userExists(Bukkit.getOfflinePlayer(args[0]), "playerpunishmentdata")) {
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[0]);

            player.sendMessage(CC.GREEN + "Currently punishing " + targetPlayer.getName() + ".");
            player.openInventory(PunishGUI.punishGUI(player, targetPlayer));

        } else {
            player.sendMessage(CC.translate("&cAre you sure you spelled their name right? The player you entered does not exist in our database."));
        }

        return false;
    }
}
















