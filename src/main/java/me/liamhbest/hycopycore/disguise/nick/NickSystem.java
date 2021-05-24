package me.liamhbest.hycopycore.disguise.nick;

import me.liamhbest.hycopycore.Core;
import me.liamhbest.hycopycore.database.DatabaseManager;
import me.liamhbest.hycopycore.utility.CC;
import me.liamhbest.hycopycore.utility.bookapi.BookUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NickSystem {

    private static final DatabaseManager databaseManager = Core.instance.databaseManager;

    public static ItemStack getNickWelcomeBook(){
        ItemStack book;

        book = BookUtil.writtenBook()
                .author("Hycopy Network").title("Nick").pages(new BookUtil.PageBuilder()
                .add("Nicknames allow you to play with a different username to not " +
                        "get recognized.\n\nAll rules still apply. You can still" +
                        " be reported and all name history is stored.").newLine().newLine()
                        .add(BookUtil.TextBuilder.of("➤ I understand, set up my nickname").style(ChatColor.UNDERLINE)
                                .onClick(BookUtil.ClickAction.runCommand("/nick 24hj234hjk243h2h3f2huy03r0hsfdasfi")).build())
                        .build())
                .build();

        return book;
    }

    public static ItemStack getSetRankBook(){
        ItemStack book;

        book = BookUtil.writtenBook()
                .author("Hycopy Network").title("Nick").pages(new BookUtil.PageBuilder()
                        .add("Let's get you set up with your nickname! First you'll need to choose" +
                                " which ").add(BookUtil.TextBuilder.of("RANK").style(ChatColor.BOLD).build())
                        .add("\nyou would like to be shown as when nicked.")

                        .newLine().newLine()
                        .add(BookUtil.TextBuilder.of(CC.translate("➤ &7DEFAULT"))
                                .onClick(BookUtil.ClickAction.runCommand("/nick 89345089534890sfdjlksdfjlsdffsdDEFAULT"))
                                .build()).newLine()
                        .add(BookUtil.TextBuilder.of(CC.translate("➤ &aVIP"))
                                .onClick(BookUtil.ClickAction.runCommand("/nick 89345089534890sfdjlksdfjlsdffsdVIP"))
                                .build()).newLine()
                        .add(BookUtil.TextBuilder.of(CC.translate("➤ &aVIP&6+"))
                                .onClick(BookUtil.ClickAction.runCommand("/nick 89345089534890sfdjlksdfjlsdffsdVIP+"))
                                .build()).newLine()
                        .add(BookUtil.TextBuilder.of(CC.translate("➤ &bMVP"))
                                .onClick(BookUtil.ClickAction.runCommand("/nick 89345089534890sfdjlksdfjlsdffsdMVP"))
                                .build()).newLine()
                        .add(BookUtil.TextBuilder.of(CC.translate("➤ &bMVP&c+"))
                                .onClick(BookUtil.ClickAction.runCommand("/nick 89345089534890sfdjlksdfjlsdffsdMVP+"))
                                .build()).newLine()
                        .build())
                .build();

        return book;
    }

    public static ItemStack getSetSkinBook(Player player){
        ItemStack book;

        if (databaseManager.getField(player, "disguisedata", "NICKSKIN") != null) {
            book = BookUtil.writtenBook()
                    .author("Hycopy Network").title("Nick").pages(new BookUtil.PageBuilder()
                            .add(BookUtil.TextBuilder.of(CC.translate("Awesome! Now, which &lSKIN &rwould you like to have while nicked?")).build())
                            .add("\nyou would like to be shown as when nicked.")

                            .newLine().newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("➤ My normal skin"))
                                    .onClick(BookUtil.ClickAction.runCommand("/nick 999912381238123812387fdasafsdNORMAL"))
                                    .build()).newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("➤ Steve/Alex Skin"))
                                    .onClick(BookUtil.ClickAction.runCommand("/nick 999912381238123812387fdasafsdSTEVE"))
                                    .build()).newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("➤ Random skin"))
                                    .onClick(BookUtil.ClickAction.runCommand("/nick 999912381238123812387fdasafsdRANDOM"))
                                    .build()).newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("➤ Reuse FREERUNNER"))
                                    .onClick(BookUtil.ClickAction.runCommand("/nick 999912381238123812387fdasafsdREUSE"))
                                    .build()).newLine()
                            .build())
                    .build();
        } else {
            book = BookUtil.writtenBook()
                    .author("Hycopy Network").title("Nick").pages(new BookUtil.PageBuilder()
                            .add(BookUtil.TextBuilder.of(CC.translate("Awesome! Now, which &lSKIN &rwould you like to have while nicked?")).build())

                            .newLine().newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("➤ My normal skin"))
                                    .onClick(BookUtil.ClickAction.runCommand("/nick 999912381238123812387fdasafsdNORMAL"))
                                    .build()).newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("➤ Steve/Alex Skin"))
                                    .onClick(BookUtil.ClickAction.runCommand("/nick 999912381238123812387fdasafsdSTEVE"))
                                    .build()).newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("➤ Random skin"))
                                    .onClick(BookUtil.ClickAction.runCommand("/nick 999912381238123812387fdasafsdRANDOM"))
                                    .build()).newLine()
                            .build())
                    .build();
        }

        return book;
    }

    public static ItemStack getSetNameYOUTUBEBook(Player player){
        ItemStack book;

        if (databaseManager.getField(player, "disguisedata", "NICKNAME") != null) {
            book = BookUtil.writtenBook()
                    .author("Hycopy Network").title("Nick").pages(new BookUtil.PageBuilder()
                            .add(BookUtil.TextBuilder.of(CC.translate("Alright, now you'll need to choose the &lNAME&r to use!")).build())

                            .newLine().newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("➤ Enter a name"))
                                    .onClick(BookUtil.ClickAction.runCommand("/nick 89345089534890sfdjlksdfjlsdffsdCUSTOM"))
                                    .build()).newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("➤ Use a random name"))
                                    .onClick(BookUtil.ClickAction.runCommand("/nick 89345089534890sfdjlksdfjlsdffsdRANDOM"))
                                    .build()).newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("➤ Reuse 'thelostmoney'"))
                                    .onClick(BookUtil.ClickAction.runCommand("/nick 89345089534890sfdjlksdfjlsdffsdREUSE"))
                                    .build()).newLine().newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("To go back to being your usual self, type: &l/nick reset")).build())
                            .build())
                    .build();
        } else {
            book = BookUtil.writtenBook()
                    .author("Hycopy Network").title("Nick").pages(new BookUtil.PageBuilder()
                            .add(BookUtil.TextBuilder.of(CC.translate("Alright, now you'll need to choose the &lNAME&r to use!")).build())

                            .newLine().newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("➤ Enter a name"))
                                    .onClick(BookUtil.ClickAction.runCommand("/nick bhksfdhklsdfghasfdiafsdyuiasfdghklasfdhlkCUSTOM"))
                                    .build()).newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("➤ Use a random name"))
                                    .onClick(BookUtil.ClickAction.runCommand("/nick 89345089534890sfdjlksdfjlsdffsdRANDOM"))
                                    .build()).newLine().newLine()
                            .add(BookUtil.TextBuilder.of(CC.translate("To go back to being your usual self, type: &l/nick reset")).build())
                            .build())
                    .build();
        }



        return book;
    }

    public static ItemStack getNickCompleteYOUTUBEBook(){
        ItemStack book;

        book = BookUtil.writtenBook()
                .author("Hycopy Network").title("Nick").pages(new BookUtil.PageBuilder()
                        .add(BookUtil.TextBuilder.of(CC.translate("You have finished setting up your nickname!\n\n" +
                                "When you go into a game, you will be nicked as {nickname}.\n\n" +
                                "To go back to being your usual self, type:\n\n&l/nick reset")
                        .replace("{nickname}", "&7thebadmonkey5")
                        ).build())
                        .build())
                .build();

        return book;
    }

}























