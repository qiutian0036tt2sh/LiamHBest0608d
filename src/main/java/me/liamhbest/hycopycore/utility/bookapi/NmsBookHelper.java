package me.liamhbest.hycopycore.utility.bookapi;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NmsBookHelper {
    private static final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private static final boolean doubleHands;
    private static final Class<?> craftMetaBookClass;
    private static final Field craftMetaBookField;
    private static final Method chatSerializerA;
    private static final Method craftMetaBookInternalAddPageMethod;
    private static final Method craftPlayerGetHandle;
    private static final Method entityPlayerOpenBook;
    private static final Object[] hands;
    private static final Method nmsItemStackSave;
    private static final Constructor<?> nbtTagCompoundConstructor;
    private static final Method craftItemStackAsNMSCopy;

    public NmsBookHelper() {
    }

    public static void setPages(BookMeta meta, BaseComponent[][] components) {
        try {
            List<Object> pages = (List) craftMetaBookField.get(meta);
            if (pages != null) {
                pages.clear();
            }

            BaseComponent[][] var3 = components;
            int var4 = components.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                BaseComponent[] c = var3[var5];
                String json;
                if (craftMetaBookInternalAddPageMethod != null) {
                    json = c != null ? ComponentSerializer.toString(c) : "";
                    craftMetaBookInternalAddPageMethod.invoke(meta, json);
                } else {
                    BaseComponent[] nonNullC = c != null ? c : jsonToComponents("");
                    json = ComponentSerializer.toString(nonNullC);
                    pages.add(chatSerializerA.invoke((Object) null, json));
                }
            }

        } catch (Exception var9) {
            throw new NmsBookHelper.UnsupportedVersionException(var9);
        }
    }

    public static void openBook(Player player, ItemStack book, boolean offHand) {
        try {
            if (doubleHands) {
                entityPlayerOpenBook.invoke(toNms(player), nmsCopy(book), hands[offHand ? 1 : 0]);
            } else {
                entityPlayerOpenBook.invoke(toNms(player), nmsCopy(book));
            }

        } catch (Exception var4) {
            throw new NmsBookHelper.UnsupportedVersionException(var4);
        }
    }

    public static BaseComponent[] itemToComponents(ItemStack item) {
        return jsonToComponents(itemToJson(item));
    }

    public static BaseComponent[] jsonToComponents(String json) {
        return new BaseComponent[]{new TextComponent(json)};
    }

    private static String itemToJson(ItemStack item) {
        try {
            Object nmsItemStack = nmsCopy(item);
            Object emptyTag = nbtTagCompoundConstructor.newInstance();
            Object json = nmsItemStackSave.invoke(nmsItemStack, emptyTag);
            return json.toString();
        } catch (Exception var4) {
            throw new NmsBookHelper.UnsupportedVersionException(var4);
        }
    }

    public static Object toNms(Player player) throws InvocationTargetException, IllegalAccessException {
        return craftPlayerGetHandle.invoke(player);
    }

    public static Object nmsCopy(ItemStack item) throws InvocationTargetException, IllegalAccessException {
        return craftItemStackAsNMSCopy.invoke((Object) null, item);
    }

    public static Class<?> getNmsClass(String className, boolean required) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + className);
        } catch (ClassNotFoundException var3) {
            if (required) {
                throw new RuntimeException("Cannot find NMS class " + className, var3);
            } else {
                return null;
            }
        }
    }

    public static Class<?> getNmsClass(String className) {
        return getNmsClass(className, false);
    }

    private static Class<?> getCraftClass(String path) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + path);
        } catch (ClassNotFoundException var2) {
            throw new RuntimeException("Cannot find CraftBukkit class at path: " + path, var2);
        }
    }

    static {
        Pattern pattern = Pattern.compile("v([0-9]+)_([0-9]+)");
        Matcher m = pattern.matcher(version);
        if (!m.find()) {
            throw new IllegalStateException("Cannot parse version \"" + version + "\", make sure it follows \"v<major>_<minor>...\"");
        } else {
            int major = Integer.parseInt(m.group(1));
            int minor = Integer.parseInt(m.group(2));
            doubleHands = major <= 1 && minor >= 9;

            try {
                craftMetaBookClass = getCraftClass("inventory.CraftMetaBook");
                craftMetaBookField = craftMetaBookClass.getDeclaredField("pages");
                craftMetaBookField.setAccessible(true);
                Method cmbInternalAddMethod = null;

                try {
                    cmbInternalAddMethod = craftMetaBookClass.getDeclaredMethod("internalAddPage", String.class);
                    cmbInternalAddMethod.setAccessible(true);
                } catch (NoSuchMethodException var13) {
                }

                craftMetaBookInternalAddPageMethod = cmbInternalAddMethod;
                Class<?> chatSerializer = getNmsClass("IChatBaseComponent$ChatSerializer", false);
                if (chatSerializer == null) {
                    chatSerializer = getNmsClass("ChatSerializer");
                }

                chatSerializerA = chatSerializer.getDeclaredMethod("a", String.class);
                Class<?> craftPlayerClass = getCraftClass("entity.CraftPlayer");
                craftPlayerGetHandle = craftPlayerClass.getMethod("getHandle");
                Class<?> entityPlayerClass = getNmsClass("EntityPlayer");
                Class<?> itemStackClass = getNmsClass("ItemStack");
                Class enumHandClass;
                if (doubleHands) {
                    enumHandClass = getNmsClass("EnumHand");

                    Method openBookMethod;
                    try {
                        openBookMethod = entityPlayerClass.getMethod("a", itemStackClass, enumHandClass);
                    } catch (NoSuchMethodException var12) {
                        openBookMethod = entityPlayerClass.getMethod("openBook", itemStackClass, enumHandClass);
                    }

                    entityPlayerOpenBook = openBookMethod;
                    hands = enumHandClass.getEnumConstants();
                } else {
                    entityPlayerOpenBook = entityPlayerClass.getMethod("openBook", itemStackClass);
                    hands = null;
                }

                enumHandClass = getCraftClass("inventory.CraftItemStack");
                craftItemStackAsNMSCopy = enumHandClass.getMethod("asNMSCopy", ItemStack.class);
                Class<?> nmsItemStackClazz = getNmsClass("ItemStack");
                Class<?> nbtTagCompoundClazz = getNmsClass("NBTTagCompound");
                nmsItemStackSave = nmsItemStackClazz.getMethod("save", nbtTagCompoundClazz);
                nbtTagCompoundConstructor = nbtTagCompoundClazz.getConstructor();
            } catch (Exception var14) {
                throw new IllegalStateException("Cannot initiate reflections for " + version, var14);
            }
        }
    }

    public static class UnsupportedVersionException extends RuntimeException {
        private final String version;

        public UnsupportedVersionException(Exception e) {
            super("Error while executing reflections, submit to developers the following log (version: " + NmsBookHelper.version + ")", e);
            this.version = NmsBookHelper.version;
        }

        public String getVersion() {
            return this.version;
        }
    }
}
