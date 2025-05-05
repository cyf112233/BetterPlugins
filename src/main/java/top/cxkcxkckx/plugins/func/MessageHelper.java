package top.cxkcxkckx.plugins.func;

import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;
import top.cxkcxkckx.plugins.plugins;

public class MessageHelper {
    private final plugins plugin;

    public MessageHelper(plugins plugin) {
        this.plugin = plugin;
    }

    // 边框相关
    public void sendTopBorder(CommandSender sender) {
        TextComponent border = new TextComponent(plugin.getLanguageManager().getMessage("border-top"));
        border.setClickEvent(null);
        border.setHoverEvent(null);
        sender.spigot().sendMessage(border);
    }

    public void sendMiddleBorder(CommandSender sender) {
        TextComponent border = new TextComponent(plugin.getLanguageManager().getMessage("border-middle"));
        border.setClickEvent(null);
        border.setHoverEvent(null);
        sender.spigot().sendMessage(border);
    }

    public void sendBottomBorder(CommandSender sender) {
        TextComponent border = new TextComponent(plugin.getLanguageManager().getMessage("border-bottom"));
        border.setClickEvent(null);
        border.setHoverEvent(null);
        sender.spigot().sendMessage(border);
    }

    public TextComponent getRightBorder() {
        TextComponent border = new TextComponent(plugin.getLanguageManager().getMessage("border-right"));
        border.setClickEvent(null);
        border.setHoverEvent(null);
        return border;
    }

    // 标签相关
    public TextComponent getIndexLabel() {
        TextComponent label = new TextComponent(plugin.getLanguageManager().getMessage("label-index"));
        label.setClickEvent(null);
        label.setHoverEvent(null);
        return label;
    }

    public TextComponent getManageLabel() {
        TextComponent label = new TextComponent(plugin.getLanguageManager().getMessage("label-manage"));
        label.setClickEvent(null);
        label.setHoverEvent(null);
        return label;
    }

    public TextComponent getBackLabel() {
        TextComponent label = new TextComponent(plugin.getLanguageManager().getMessage("label-back"));
        label.setClickEvent(null);
        label.setHoverEvent(null);
        return label;
    }

    // 按钮相关
    public TextComponent getOtherButton() {
        TextComponent button = new TextComponent(" §8| " + plugin.getLanguageManager().getMessage("button-other"));
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plf other"));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
            new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-show-other")).create()));
        return button;
    }

    public TextComponent getResetButton() {
        TextComponent button = new TextComponent(" §8| " + plugin.getLanguageManager().getMessage("button-reset"));
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pl"));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
            new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-show-all")).create()));
        return button;
    }

    public TextComponent getBackButton() {
        TextComponent button = new TextComponent(plugin.getLanguageManager().getMessage("button-back"));
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pl"));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
            new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-back")).create()));
        return button;
    }

    // 插件状态相关
    public String getPluginStatus(boolean enabled) {
        return enabled ? 
            plugin.getLanguageManager().getMessage("plugin-info-status-enabled") : 
            plugin.getLanguageManager().getMessage("plugin-info-status-disabled");
    }

    // 统计信息相关
    public TextComponent getStatsText(int total, int enabled, int disabled) {
        String message = plugin.getLanguageManager().getMessage("plugin-stats");
        message = String.format(message, total, enabled, disabled);
        TextComponent stats = new TextComponent(message);
        stats.setClickEvent(null);
        stats.setHoverEvent(null);
        return stats;
    }

    // 错误消息相关
    public void sendPluginDisabled(CommandSender sender) {
        sender.sendMessage(plugin.getLanguageManager().getMessage("plugin-disabled"));
    }

    public void sendPluginNotFound(CommandSender sender, String pluginName) {
        sender.sendMessage(plugin.getLanguageManager().getMessage("plugin-not-found", pluginName));
    }

    // 通用组件
    public TextComponent getSeparator() {
        TextComponent separator = new TextComponent("");
        separator.setClickEvent(null);
        separator.setHoverEvent(null);
        return separator;
    }

    public TextComponent getSpace() {
        TextComponent space = new TextComponent(" ");
        space.setClickEvent(null);
        space.setHoverEvent(null);
        return space;
    }

    public TextComponent getDivider() {
        TextComponent divider = new TextComponent(" §8| ");
        divider.setClickEvent(null);
        divider.setHoverEvent(null);
        return divider;
    }
} 