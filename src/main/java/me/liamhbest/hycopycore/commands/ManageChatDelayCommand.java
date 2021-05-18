package me.liamhbest.hycopycore.commands;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.ranks.PlayerRank;
import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.HycopyPlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManageChatDelayCommand extends Command {

    public ManageChatDelayCommand(){
        super("/setchatdelay");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        HycopyPlayer hycopyPlayer = new HycopyPlayer(player);

        if (hycopyPlayer.getRankManager().getRank() != PlayerRank.ADMIN){
            player.sendMessage(CC.RED + "You must be an admin or higher to perform this!");
            return true;
        }

        if (args.length == 0){
            player.sendMessage(CC.translate("&aPlease specify a chat delay. You can also use 'off' to turn it off."));
            return true;
        }

        if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("0")) {
            try {
                PreparedStatement ps = Core.instance.mysql.getConnection().prepareStatement("UPDATE " + "serverdata" + " SET " + "CHAT_DELAY" + "=? WHERE ID=?");
                ps.setObject(1, 0);
                ps.setString(2, "mainnetwork");
                ps.executeUpdate();

            } catch (SQLException exception){
                exception.printStackTrace();
                player.sendMessage(CC.RED + "Something went wrong with the database connection and the delay could not be changed!");
            }

            player.sendMessage(CC.translate("&6You turned off the chat delay."));
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 4);
            return true;
        }

        try {
            PreparedStatement ps = Core.instance.mysql.getConnection().prepareStatement("UPDATE " + "serverdata" + " SET " + "CHAT_DELAY" + "=? WHERE ID=?");
            ps.setObject(1, Integer.parseInt(args[0]));
            ps.setString(2, "mainnetwork");
            ps.executeUpdate();

        } catch (SQLException exception){
            exception.printStackTrace();
            player.sendMessage(CC.RED + "Something went wrong with the database connection and the delay could not be changed!");
        }

        player.sendMessage(CC.translate("&bYou changed the chat delay from &e" + "3" + "s &bto &e" + "5" + "s&b."));
        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 4);

        return false;
    }
}
























