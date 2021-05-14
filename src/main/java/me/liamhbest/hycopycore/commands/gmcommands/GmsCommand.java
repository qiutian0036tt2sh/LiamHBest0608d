package me.liamhbest.hycopycore.commands.gmcommands;

import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.Logger;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GmsCommand extends Command {

    public GmsCommand(){
        super("gms");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            Logger.log("Only a player can perform this!");
            return true;
        }
        Player player = (Player) commandSender;
        if (!player.hasPermission("hycopy.core.gamemode")) {
            player.sendMessage(CC.RED + "You are not allowed to do this!");
            return true;
        }

        if (args.length == 0){
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(CC.translate("&aYou set your own &agamemode to &e" + player.getGameMode().name() + "&a."));
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 3);
            return true;
        }

        try {
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer.getUniqueId() == player.getUniqueId()){
                player.sendMessage(CC.translate("&cPlease set your own rank without the extra arguments!"));
                return true;
            }

            targetPlayer.setGameMode(GameMode.SURVIVAL);
            targetPlayer.sendMessage(CC.translate("&6" + player.getName() + " &aset your gamemode to &e" + targetPlayer.getGameMode().name() + "&a."));
            player.sendMessage(CC.translate("&aYou set &6" + targetPlayer.getName() + "'s &agamemode to &e" + targetPlayer.getGameMode().name() + "&a."));
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 3);
            targetPlayer.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 3);

        } catch (Exception exception){
            player.sendMessage(CC.translate("&cThis player is not online!"));
        }
        return false;
    }

}
