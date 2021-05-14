package me.liamhbest.hycopycore.utility;

import org.bukkit.Bukkit;

public class Logger {

    private static final String prefix = CC.translate("&6&l[HYCOPY CORE]&r ");

    public static void log(String message){
        Bukkit.getLogger().info(prefix + " " + message);
    }

}
