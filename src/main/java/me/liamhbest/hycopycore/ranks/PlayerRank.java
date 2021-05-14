package me.liamhbest.hycopycore.ranks;

import lombok.Getter;
import me.liamhbest.hycopycore.utility.CC;
import org.bukkit.ChatColor;

public enum PlayerRank {

    ADMIN(ChatColor.RED.toString(), CC.translate("&c[ADMIN]"), CC.translate("&c[ADMIN] "), true, "ADMIN", CC.translate("&cADMIN")),
    GAME_MASTER(ChatColor.DARK_GREEN.toString(), CC.translate("&2[GM]"), CC.translate("&2[GM] "), true, "GAME MASTER", CC.translate("&2GAME MASTER")),
    MODERATOR(ChatColor.DARK_GREEN.toString(), CC.translate("&2[MOD]"), CC.translate("&2[MOD] "), true, "MOD", CC.translate("&2MOD")),
    HELPER(ChatColor.BLUE.toString(), CC.translate("&9[HELPER]"), CC.translate("&9[HELPER] "), true, "HELPER", CC.translate("&9HELPER")),
    YOUTUBE(ChatColor.RED.toString(), CC.translate("&c[&fYOUTUBE&c]"), CC.translate("&c[&fYOUTUBE&c] "), false, "YOUTUBE", CC.translate("&fYOUTUBE")),
    MVP_PLUS_PLUS(ChatColor.GOLD.toString(), CC.translate("&6[MVP%&6]"), CC.translate("&6[MVP%&6] "), false, "MVP++", CC.translate("&6MVP%")),
    MVP_PLUS(ChatColor.AQUA.toString(), CC.translate("&b[MVP%&b]"), CC.translate("&b[MVP%&b] "), false, "MVP+", CC.translate("&bMVP%")),
    MVP(ChatColor.AQUA.toString(), CC.translate("&b[MVP]"), CC.translate("&b[MVP] "), false, "MVP", CC.translate("&bMVP")),
    VIP_PLUS(ChatColor.GREEN.toString(), CC.translate("&a[VIP&e+&a]"), CC.translate("&a[VIP&e+&a] "), false, "VIP+", CC.translate("&aVIP&e+")),
    VIP(ChatColor.GREEN.toString(), CC.translate("&a[VIP]"), CC.translate("&a[VIP] "), false, "VIP", CC.translate("&aVIP")),
    DEFAULT(ChatColor.GRAY.toString(), CC.translate("&7"), ChatColor.GRAY.toString(), false, "Default", CC.translate("&7Default"));

    @Getter
    private final String color;

    @Getter
    private final String prefix;

    @Getter
    private final String prefixWithSpace;

    @Getter
    private final boolean isStaffRank;

    @Getter
    private final String displayName;

    @Getter
    private final String displayNameWithColor;

    PlayerRank(String color, String prefix, String prefixWithSpace, boolean isStaffRank, String displayName, String displayNameWithColor){
        this.color = color;
        this.prefix = prefix;
        this.isStaffRank = isStaffRank;
        this.displayName = displayName;
        this.displayNameWithColor = displayNameWithColor;
        this.prefixWithSpace = prefixWithSpace;
    }

}
