package me.liamhbest.hycopycore.disguise.nick;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.database.DatabaseManager;
import me.liamhbest.hycopycore.ranks.PlayerRank;
import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.HycopyPlayer;
import me.liamhbest.hycopycore.utility.anivilgui.AnvilGUI;
import me.liamhbest.hycopycore.utility.bookapi.BookUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NickCommand extends Command {

    public NickCommand(){
        super("nick");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.RED + "Only a player can do this!");
            return true;
        }
        Player player = (Player) sender;
        HycopyPlayer hycopyPlayer = new HycopyPlayer(player);
        DatabaseManager databaseManager = Core.instance.databaseManager;
        if (hycopyPlayer.getRankManager().getRank() != PlayerRank.YOUTUBE
        && !hycopyPlayer.getRankManager().getRank().isStaffRank()) {
            hycopyPlayer.sendMessage("&cYou are not allowed to do this!");
            return true;
        }

        if (args.length == 0){
            BookUtil.openPlayer(player, NickSystem.getNickWelcomeBook());
            return true;
        }

        if (args[0].equalsIgnoreCase("24hj234hjk243h2h3f2huy03r0hsfdasfi")) {
            BookUtil.openPlayer(player, NickSystem.getSetRankBook());
        }



        else if (args[0].equalsIgnoreCase("89345089534890sfdjlksdfjlsdffsdDEFAULT")) {
            BookUtil.openPlayer(player, NickSystem.getSetSkinBook(player));
            databaseManager.setField(player, "disguisedata", "NICKRANK", "VIP");
        }

        else if (args[0].equalsIgnoreCase("89345089534890sfdjlksdfjlsdffsdVIP")) {
            BookUtil.openPlayer(player, NickSystem.getSetSkinBook(player));
            databaseManager.setField(player, "disguisedata", "NICKRANK", "VIP");
        }

        else if (args[0].equalsIgnoreCase("89345089534890sfdjlksdfjlsdffsdVIP+")) {
            BookUtil.openPlayer(player, NickSystem.getSetSkinBook(player));
            databaseManager.setField(player, "disguisedata", "NICKRANK", "VIP+");
        }

        else if (args[0].equalsIgnoreCase("89345089534890sfdjlksdfjlsdffsdMVP")) {
            BookUtil.openPlayer(player, NickSystem.getSetSkinBook(player));
            databaseManager.setField(player, "disguisedata", "NICKRANK", "MVP");
        }

        else if (args[0].equalsIgnoreCase("89345089534890sfdjlksdfjlsdffsdMVP+")) {
            BookUtil.openPlayer(player, NickSystem.getSetSkinBook(player));
            databaseManager.setField(player, "disguisedata", "NICKRANK", "MVP+");
        }



        else if (args[0].equalsIgnoreCase("999912381238123812387fdasafsdNORMAL")) {
            BookUtil.openPlayer(player, NickSystem.getSetNameYOUTUBEBook(player));
            databaseManager.setField(player, "disguisedata", "NICKSKIN", player.getName());
        }

        else if (args[0].equalsIgnoreCase("999912381238123812387fdasafsdSTEVE")) {
            BookUtil.openPlayer(player, NickSystem.getSetNameYOUTUBEBook(player));

        }

        else if (args[0].equalsIgnoreCase("999912381238123812387fdasafsdRANDOM")) {
            BookUtil.openPlayer(player, NickSystem.getSetNameYOUTUBEBook(player));

        }

        else if (args[0].equalsIgnoreCase("999912381238123812387fdasafsdREUSE")) {
            BookUtil.openPlayer(player, NickSystem.getSetNameYOUTUBEBook(player));

        }



        else if (args[0].equalsIgnoreCase("89345089534890sfdjlksdfjlsdffsdREUSE")) {
            BookUtil.openPlayer(player, NickSystem.getSetSkinBook(player));

        }

        else if (args[0].equalsIgnoreCase("999912381238123812387fdasafsdCUSTOM")) {
            BookUtil.openPlayer(player, NickSystem.getSetNameYOUTUBEBook(player));
        }

        else if (args[0].equalsIgnoreCase("999912381238123812387fdasafsdRANDOM")) {
            BookUtil.openPlayer(player, NickSystem.getSetNameYOUTUBEBook(player));
        }

        else if (args[0].equalsIgnoreCase("999912381238123812387fdasafsdREUSE")) {
            BookUtil.openPlayer(player, NickSystem.getSetNameYOUTUBEBook(player));
        }


        else if (args[0].equalsIgnoreCase("bhksfdhklsdfghasfdiafsdyuiasfdghklasfdhlkCUSTOM")) {
            AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler() {
                @Override
                public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
                    if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
                        event.setWillClose(true);
                        event.setWillDestroy(true);
                        player.sendMessage(event.getName().trim());
                        BookUtil.openPlayer(player, NickSystem.getNickCompleteYOUTUBEBook());

                    } else {
                        event.setWillClose(false);
                        event.setWillDestroy(false);
                    }
                }
            });
            ItemStack item = new ItemStack(Material.NAME_TAG);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(" ");
            item.setItemMeta(meta);
            gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, item);
            gui.open();
        }

        return false;
    }
}



































