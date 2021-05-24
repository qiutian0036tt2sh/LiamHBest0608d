package me.liamhbest.hycopycore.disguise.vanish;

import me.liamhbest.hycopycore.disguise.DisguiseSystem;
import me.liamhbest.hycopycore.ranks.PlayerRank;
import me.liamhbest.hycopycore.utility.HycopyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class VanishCommand extends BukkitCommand {

    public VanishCommand(){
        super("vanish");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        HycopyPlayer hycopyPlayer = new HycopyPlayer(player);

        if (!hycopyPlayer.getRankManager().getRank().isStaffRank()
                && hycopyPlayer.getRankManager().getRank() != PlayerRank.YOUTUBE) {
            hycopyPlayer.sendMessage("&cYou do not have permission to use this command!");
            return true;
        }

        if (!Bukkit.getServerName().contains("lobby")) {
            hycopyPlayer.sendMessage("&cThis cannot be performed alive in-game!");
            return true;
        }

        if (hycopyPlayer.getDisguiseManager().isVanished()) {
            // Unvanish player
            hycopyPlayer.getDisguiseManager().setVanished(false);
            hycopyPlayer.sendMessage("&aYou reappeared!");

            for (Player targetPlayer : Bukkit.getOnlinePlayers()){
                targetPlayer.showPlayer(player);
            }

        } else {
            // Vanish Player
            hycopyPlayer.getDisguiseManager().setVanished(true);
            hycopyPlayer.sendMessage("&aYou vanished!");

            for (Player targetPlayer : Bukkit.getOnlinePlayers()){
                HycopyPlayer targetHycopyPlayer = new HycopyPlayer(targetPlayer);

                if (!targetHycopyPlayer.getRankManager().getRank().isStaffRank()) {
                    targetPlayer.hidePlayer(player);
                }

            }

            DisguiseSystem.sendActionBar(player);
        }



        return false;
    }
}





























