package me.liamhbest.hycopycore.utility.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Reflect {

    public static void sendPacket(Player player, Object packet){
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = player.getClass().getField("playerConnection").get(handle);

            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(player);
        } catch (Exception ignored){

        }
    }

    public static Class<?> getNMSClass(String name){
        try {
            return Class.forName("net.minecraft.server"
            + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch (ClassNotFoundException ignored){

        }
        return null;
    }

}
