package me.liamhbest.hycopycore.utility.bookapi;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.*;

public final class BookUtil {
    private static final boolean canTranslateDirectly;

    public BookUtil() {
    }

    public static void openPlayer(Player p, ItemStack book) {
        CustomBookOpenEvent event = new CustomBookOpenEvent(p, book, false);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            p.closeInventory();
            ItemStack hand = p.getItemInHand();
            p.setItemInHand(event.getBook());
            p.updateInventory();
            NmsBookHelper.openBook(p, event.getBook(), false);
            p.setItemInHand(hand);
            p.updateInventory();
        }
    }

    public static BookUtil.BookBuilder writtenBook() {
        return new BookUtil.BookBuilder(new ItemStack(Material.WRITTEN_BOOK));
    }

    static {
        boolean success = true;

        try {
            ChatColor.BLACK.asBungee();
        } catch (NoSuchMethodError var2) {
            success = false;
        }

        canTranslateDirectly = success;
    }

    public interface HoverAction {
        HoverEvent.Action action();

        BaseComponent[] value();

        static BookUtil.HoverAction showText(BaseComponent... text) {
            return new BookUtil.HoverAction.SimpleHoverAction(HoverEvent.Action.SHOW_TEXT, text);
        }

        static BookUtil.HoverAction showText(String text) {
            return new BookUtil.HoverAction.SimpleHoverAction(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(text)});
        }

        static BookUtil.HoverAction showItem(BaseComponent... item) {
            return new BookUtil.HoverAction.SimpleHoverAction(HoverEvent.Action.SHOW_ITEM, item);
        }

        static BookUtil.HoverAction showItem(ItemStack item) {
            return new BookUtil.HoverAction.SimpleHoverAction(HoverEvent.Action.SHOW_ITEM, NmsBookHelper.itemToComponents(item));
        }

        static BookUtil.HoverAction showAchievement(String achievementId) {
            return new BookUtil.HoverAction.SimpleHoverAction(HoverEvent.Action.SHOW_ACHIEVEMENT, new BaseComponent[]{new TextComponent("achievement." + achievementId)});
        }

        static BookUtil.HoverAction showStatistic(String statisticId) {
            return new BookUtil.HoverAction.SimpleHoverAction(HoverEvent.Action.SHOW_ACHIEVEMENT, new BaseComponent[]{new TextComponent("statistic." + statisticId)});
        }

        public static class SimpleHoverAction implements BookUtil.HoverAction {
            private final HoverEvent.Action action;
            private final BaseComponent[] value;

            public SimpleHoverAction(HoverEvent.Action action, BaseComponent... value) {
                this.action = action;
                this.value = value;
            }

            public HoverEvent.Action action() {
                return this.action;
            }

            public BaseComponent[] value() {
                return this.value;
            }
        }
    }

    public interface ClickAction {
        net.md_5.bungee.api.chat.ClickEvent.Action action();

        String value();

        static BookUtil.ClickAction runCommand(String command) {
            return new BookUtil.ClickAction.SimpleClickAction(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, command);
        }

        /** @deprecated */
        @Deprecated
        static BookUtil.ClickAction suggestCommand(String command) {
            return new BookUtil.ClickAction.SimpleClickAction(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, command);
        }

        static BookUtil.ClickAction openUrl(String url) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                throw new IllegalArgumentException("Invalid url: \"" + url + "\", it should start with http:// or https://");
            } else {
                return new BookUtil.ClickAction.SimpleClickAction(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, url);
            }
        }

        public static class SimpleClickAction implements BookUtil.ClickAction {
            private final net.md_5.bungee.api.chat.ClickEvent.Action action;
            private final String value;

            public net.md_5.bungee.api.chat.ClickEvent.Action action() {
                return this.action;
            }

            public String value() {
                return this.value;
            }

            public SimpleClickAction(net.md_5.bungee.api.chat.ClickEvent.Action action, String value) {
                this.action = action;
                this.value = value;
            }
        }
    }

    public static class TextBuilder {
        private String text = "";
        private BookUtil.ClickAction onClick = null;
        private BookUtil.HoverAction onHover = null;
        private ChatColor color;
        private ChatColor[] style;

        public TextBuilder() {
            this.color = ChatColor.BLACK;
        }

        public BookUtil.TextBuilder color(ChatColor color) {
            if (color != null && !color.isColor()) {
                throw new IllegalArgumentException("Argument isn't a color!");
            } else {
                this.color = color;
                return this;
            }
        }

        public BookUtil.TextBuilder style(ChatColor... style) {
            ChatColor[] var2 = style;
            int var3 = style.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                ChatColor c = var2[var4];
                if (!c.isFormat()) {
                    throw new IllegalArgumentException("Argument isn't a style!");
                }
            }

            this.style = style;
            return this;
        }

        public BaseComponent build() {
            TextComponent res = new TextComponent(this.text);
            if (this.onClick != null) {
                res.setClickEvent(new ClickEvent(this.onClick.action(), this.onClick.value()));
            }

            if (this.onHover != null) {
                res.setHoverEvent(new HoverEvent(this.onHover.action(), this.onHover.value()));
            }

            if (this.color != null) {
                if (BookUtil.canTranslateDirectly) {
                    res.setColor(this.color.asBungee());
                } else {
                    res.setColor(net.md_5.bungee.api.ChatColor.getByChar(this.color.getChar()));
                }
            }

            if (this.style != null) {
                ChatColor[] var2 = this.style;
                int var3 = var2.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    ChatColor c = var2[var4];
                    switch(c) {
                        case MAGIC:
                            res.setObfuscated(true);
                            break;
                        case BOLD:
                            res.setBold(true);
                            break;
                        case STRIKETHROUGH:
                            res.setStrikethrough(true);
                            break;
                        case UNDERLINE:
                            res.setUnderlined(true);
                            break;
                        case ITALIC:
                            res.setItalic(true);
                    }
                }
            }

            return res;
        }

        public static BookUtil.TextBuilder of(String text) {
            return (new BookUtil.TextBuilder()).text(text);
        }

        public BookUtil.TextBuilder text(String text) {
            this.text = text;
            return this;
        }

        public BookUtil.TextBuilder onClick(BookUtil.ClickAction onClick) {
            this.onClick = onClick;
            return this;
        }

        public BookUtil.TextBuilder onHover(BookUtil.HoverAction onHover) {
            this.onHover = onHover;
            return this;
        }

        public String text() {
            return this.text;
        }

        public BookUtil.ClickAction onClick() {
            return this.onClick;
        }

        public BookUtil.HoverAction onHover() {
            return this.onHover;
        }

        public ChatColor color() {
            return this.color;
        }
    }

    public static class PageBuilder {
        private List<BaseComponent> text = new ArrayList();

        public PageBuilder() {
        }

        public BookUtil.PageBuilder add(String text) {
            this.text.add(BookUtil.TextBuilder.of(text).build());
            return this;
        }

        public BookUtil.PageBuilder add(BaseComponent component) {
            this.text.add(component);
            return this;
        }

        public BookUtil.PageBuilder add(BaseComponent... components) {
            this.text.addAll(Arrays.asList(components));
            return this;
        }

        public BookUtil.PageBuilder add(Collection<BaseComponent> components) {
            this.text.addAll(components);
            return this;
        }

        public BookUtil.PageBuilder newLine() {
            this.text.add(new TextComponent("\n"));
            return this;
        }

        public BaseComponent[] build() {
            return (BaseComponent[])this.text.toArray(new BaseComponent[0]);
        }

        public static BookUtil.PageBuilder of(String text) {
            return (new BookUtil.PageBuilder()).add(text);
        }

        public static BookUtil.PageBuilder of(BaseComponent text) {
            return (new BookUtil.PageBuilder()).add(text);
        }

        public static BookUtil.PageBuilder of(BaseComponent... text) {
            BookUtil.PageBuilder res = new BookUtil.PageBuilder();
            BaseComponent[] var2 = text;
            int var3 = text.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                BaseComponent b = var2[var4];
                res.add(b);
            }

            return res;
        }
    }

    public static class BookBuilder {
        private final BookMeta meta;
        private final ItemStack book;

        public BookBuilder(ItemStack book) {
            this.book = book;
            this.meta = (BookMeta)book.getItemMeta();
        }

        public BookUtil.BookBuilder title(String title) {
            this.meta.setTitle(title);
            return this;
        }

        public BookUtil.BookBuilder author(String author) {
            this.meta.setAuthor(author);
            return this;
        }

        public BookUtil.BookBuilder pagesRaw(String... pages) {
            this.meta.setPages(pages);
            return this;
        }

        public BookUtil.BookBuilder pagesRaw(List<String> pages) {
            this.meta.setPages(pages);
            return this;
        }

        public BookUtil.BookBuilder pages(BaseComponent[]... pages) {
            NmsBookHelper.setPages(this.meta, pages);
            return this;
        }

        public BookUtil.BookBuilder pages(List<BaseComponent[]> pages) {
            NmsBookHelper.setPages(this.meta, (BaseComponent[][])pages.toArray(new BaseComponent[0][]));
            return this;
        }

        public ItemStack build() {
            this.book.setItemMeta(this.meta);
            return this.book;
        }
    }
}
