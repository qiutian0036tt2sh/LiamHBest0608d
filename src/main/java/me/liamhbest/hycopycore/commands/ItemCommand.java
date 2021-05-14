package me.liamhbest.hycopycore.commands;

import me.liamhbest.hycopycore.ranks.PlayerRank;
import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.HycopyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCommand extends Command {

    public ItemCommand(){
        super("item");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            CC.sendMessage(commandSender, "&CThis can only be performed by a player!");
            return true;
        }
        Player player = (Player) commandSender;
        HycopyPlayer hycopyPlayer = new HycopyPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        if (hycopyPlayer.getRankManager().getRank() != PlayerRank.ADMIN) {
            player.sendMessage(CC.RED + "You must be an admin or higher to use this command!");
            return true;
        }

        if (args.length == 0){
            player.sendMessage(CC.GREEN + "Please insert an item");
            return true;
        }

        try {
            Material material = Material.valueOf(args[0].toUpperCase());

            if (args.length > 1){
                //Add with amount
                try {
                    int amount = Integer.parseInt(args[1]);
                    ItemStack giveItem = new ItemStack(material, amount);

                    if (player.getInventory().firstEmpty() != -1){
                        //Inventory not full
                        player.sendMessage(CC.translate("&aAdded " + amount + " &e" + material.name() + " &ato your inventory."));
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 3);
                        player.getInventory().addItem(giveItem);

                    } else {
                        player.sendMessage(CC.RED + "Your inventory is full!");
                    }

                } catch (Exception exception){
                    player.sendMessage(CC.RED + "That is not a valid number!");
                }

            } else {
                if (player.getInventory().firstEmpty() != -1){
                    //Inventory not full
                    player.sendMessage(CC.translate("&aAdded 64 &e" + material.name().replace("_", "") + " &ato your inventory."));
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 3);
                    ItemStack giveItem = new ItemStack(material, 64);
                    player.getInventory().addItem(giveItem);

                } else {
                    player.sendMessage(CC.RED + "Your inventory is full!");
                }
            }

        } catch (Exception exception){
            player.sendMessage(CC.RED + "The material you are looking for does not exist!");
        }

        return false;
    }
}















