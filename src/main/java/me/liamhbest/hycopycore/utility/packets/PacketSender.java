package me.liamhbest.hycopycore.utility.packets;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class PacketSender extends Reflection {

    public void sendTitlePacket(Player player, int fadeInTime, int showTime, int fadeOutTime, String title, String subTitle){
        try {
            Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + title + "\"}");

            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
            int.class, int.class, int.class);

            Object packet = titleConstructor.newInstance(
                    getNMSClass("PacketPlayerOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null),
                    chatTitle, fadeInTime, showTime, fadeOutTime);

            Object chatsTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
                    .invoke(null, "{\"text\": \"" + subTitle + "\"}");

            Constructor<?> stitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
                    int.class, int.class, int.class);

            Object spacket = stitleConstructor.newInstance(
                    getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
                    fadeInTime, showTime, fadeOutTime);

            sendPacket(player, packet);
            sendPacket(player, spacket);
        } catch (Exception ignored){

        }
    }

}
