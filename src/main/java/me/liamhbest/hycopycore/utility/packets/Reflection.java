package me.liamhbest.hycopycore.utility.packets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Reflection {

    public Class<?> getNMSClass(String name){
        try {
            return Class.forName("net.minecraft.server"
            + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch (ClassNotFoundException ignored){

        }
        return null;
    }

    public void sendPacket(Player player, Object packet){
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(player);
        }catch (Exception ignored){

        }
    }

}
