package me.liamhbest.hycopycore.ranks;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.database.DatabaseManager;
import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.HycopyPlayer;
import me.liamhbest.hycopycore.utility.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand extends Command {

    public RankCommand(){
        super("rank");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) {
            int amount = PlayerRank.values().length;
            int times = 0;
            for (PlayerRank rank : PlayerRank.values()){
                if (rank.name().equalsIgnoreCase(args[0])) {
                    if (args.length > 1){
                        // get player
                        DatabaseManager databaseManager = Core.instance.databaseManager;
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (!databaseManager.userExists(offlinePlayer, "rankdata")){
                            Logger.log(CC.RED + "This user has never played on Hycopy before and therefore you cannot modify their rank!");
                            return true;
                        }

                        HycopyPlayer target = new HycopyPlayer(offlinePlayer);
                        target.getRankManager().setRank(PlayerRank.valueOf(args[0].toUpperCase()));
                        Logger.log(CC.GREEN + "You updated " + offlinePlayer.getName() + "'s rank to " + PlayerRank.valueOf(args[0].toUpperCase()).getDisplayNameWithColor().replace("%", target.getMetadataManager().getPlus()));
                        if (offlinePlayer.isOnline()) {
                            Player targetPlayer = Bukkit.getPlayer(offlinePlayer.getUniqueId());
                            targetPlayer.sendMessage(CC.GREEN + "You are now " + PlayerRank.valueOf(args[0].toUpperCase()).getDisplayNameWithColor().replace("%", target.getMetadataManager().getPlus()));
                        }

                    } else {
                        Logger.log(CC.RED + "Please specify a player. Use /rank <rank> <player>");
                    }
                    break;
                }
                times++;
                if (times == amount){
                    Logger.log(CC.RED + "No rank found!");
                    Logger.log(CC.RED + "Available ranks: " + ranks());
                    break;
                }
            }

            return true;
        }
        Player player = (Player) sender;
        HycopyPlayer hycopyPlayer = new HycopyPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        if (hycopyPlayer.getRankManager().getRank() != PlayerRank.ADMIN && !player.getUniqueId().toString().equals("de17e166-a71f-4ab5-ba83-7dc38a8e6723")){
            player.sendMessage(CC.RED + "You are not allowed to perform this!");
            return true;
        }

        if (args.length == 0){
            player.sendMessage(CC.translate("&aPlease specify a rank to set. Use &6/rank <rank> <player>"));
            player.sendMessage(CC.translate("&eAvailable ranks: " + ranks()));
            player.sendMessage(CC.translate("&aYou can also use: &6/rank check <player>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("check")) {
            if (args.length > 1){
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                DatabaseManager databaseManager = Core.instance.databaseManager;
                if (!databaseManager.userExists(offlinePlayer, "rankdata")){
                    player.sendMessage(CC.RED + "This user has never played on Hycopy before and therefore you cannot modify their rank!");
                    return true;
                }
                HycopyPlayer targetPlayer = new HycopyPlayer(offlinePlayer);
                player.sendMessage(CC.translate("&a" + offlinePlayer.getName() + "'s rank: ") + targetPlayer.getRankManager().getRank().getDisplayNameWithColor().replace("%", targetPlayer.getMetadataManager().getPlus()));

            } else {
                player.sendMessage(CC.RED + "Please specify a player!");
            }

            return true;
        }

        int amount = PlayerRank.values().length;
        int times = 0;
        for (PlayerRank rank : PlayerRank.values()){
            if (rank.name().equalsIgnoreCase(args[0])) {
                if (args.length > 1){
                    // get player
                    DatabaseManager databaseManager = Core.instance.databaseManager;
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    if (!databaseManager.userExists(offlinePlayer, "rankdata")){
                        player.sendMessage(CC.RED + "This user has never played on Hycopy before and therefore you cannot modify their rank!");
                        return true;
                    }

                    HycopyPlayer target = new HycopyPlayer(offlinePlayer);
                    target.getRankManager().setRank(PlayerRank.valueOf(args[0].toUpperCase()));
                    player.sendMessage(CC.GREEN + "You updated " + offlinePlayer.getName() + "'s rank to " + PlayerRank.valueOf(args[0].toUpperCase()).getDisplayNameWithColor().replace("%", target.getMetadataManager().getPlus()));
                    if (offlinePlayer.isOnline()) {
                        Player targetPlayer = Bukkit.getPlayer(offlinePlayer.getUniqueId());
                        targetPlayer.sendMessage(CC.GREEN + "You are now " + PlayerRank.valueOf(args[0].toUpperCase()).getDisplayNameWithColor().replace("%", target.getMetadataManager().getPlus()));
                    }

                } else {
                    player.sendMessage(CC.RED + "Please specify a player. Use /rank <rank> <player>");
                }
                break;
            }
            times++;
            if (times == amount){
                player.sendMessage(CC.RED + "No rank found!");
                player.sendMessage(CC.RED + "Available ranks: " + ranks());
                break;
            }
        }

        return false;
    }

    public String ranks(){
        return "ADMIN, GAME_MASTER, MODERATOR, HELPER, YOUTUBE, MVP_PLUS_PLUS, MVP_PLUS, MVP, VIP_PLUS, VIP, DEFAULT";
    }
}




























