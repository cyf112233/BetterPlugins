package top.cxkcxkckx.plugins.commands;

import net.md_5.bungee.api.chat.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import top.mrxiaom.pluginbase.func.AutoRegister;
import top.cxkcxkckx.plugins.plugins;
import top.cxkcxkckx.plugins.func.AbstractModule;

import java.util.List;

@AutoRegister
public class CommandInfo extends AbstractModule implements CommandExecutor {
    public CommandInfo(plugins plugin) {
        super(plugin);
        registerCommand("pli", this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // 检查插件是否启用
        if (!plugin.isPluginEnabled()) {
            sender.sendMessage(plugin.getLanguageManager().getMessage("plugin-disabled"));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(plugin.getLanguageManager().getMessage("usage-pli"));
            return true;
        }

        String pluginName = args[0];
        Plugin targetPlugin = plugin.getServer().getPluginManager().getPlugin(pluginName);

        if (targetPlugin == null) {
            sender.sendMessage(plugin.getLanguageManager().getMessage("plugin-not-found", pluginName));
            return true;
        }

        // 检查是否安装了PlugMan
        boolean hasPlugMan = plugin.getServer().getPluginManager().getPlugin("PlugMan") != null;

        // 获取插件信息
        String name = targetPlugin.getName();
        String version = targetPlugin.getDescription().getVersion();
        String description = targetPlugin.getDescription().getDescription();
        String mainClass = targetPlugin.getDescription().getMain();
        List<String> authors = targetPlugin.getDescription().getAuthors();
        String authorsStr = authors != null && !authors.isEmpty() ? String.join(", ", authors) : plugin.getLanguageManager().getMessage("unknown");
        String website = targetPlugin.getDescription().getWebsite();
        String apiVersion = targetPlugin.getDescription().getAPIVersion();
        List<String> dependencies = targetPlugin.getDescription().getDepend();
        List<String> softDependencies = targetPlugin.getDescription().getSoftDepend();
        List<String> loadBefore = targetPlugin.getDescription().getLoadBefore();
        String status = targetPlugin.isEnabled() ? 
            plugin.getLanguageManager().getMessage("plugin-info-status-enabled") : 
            plugin.getLanguageManager().getMessage("plugin-info-status-disabled");

        // 清屏效果
        for (int i = 0; i < 20; i++) {
            sender.sendMessage("");
        }

        // 创建消息
        ComponentBuilder message = new ComponentBuilder();
        
        // 添加顶部边框
        plugin.getMessageHelper().sendTopBorder(sender);
        
        // 添加标题
        TextComponent title = new TextComponent(plugin.getLanguageManager().getMessage("plugin-info-title"));
        title.setClickEvent(null);
        title.setHoverEvent(null);
        message.append(title);
        message.append("\n");
        
        // 添加插件信息
        TextComponent nameComponent = new TextComponent(plugin.getLanguageManager().getMessage("plugin-info-name", name != null ? name : plugin.getLanguageManager().getMessage("unknown")));
        nameComponent.setClickEvent(null);
        nameComponent.setHoverEvent(null);
        message.append(nameComponent);
        message.append("\n");
        
        TextComponent versionComponent = new TextComponent(plugin.getLanguageManager().getMessage("plugin-info-version", version != null ? version : plugin.getLanguageManager().getMessage("unknown")));
        versionComponent.setClickEvent(null);
        versionComponent.setHoverEvent(null);
        message.append(versionComponent);
        message.append("\n");
        
        TextComponent authorComponent = new TextComponent(plugin.getLanguageManager().getMessage("plugin-info-author", authorsStr));
        authorComponent.setClickEvent(null);
        authorComponent.setHoverEvent(null);
        message.append(authorComponent);
        message.append("\n");
        
        TextComponent descComponent = new TextComponent(plugin.getLanguageManager().getMessage("plugin-info-description", description != null ? description : plugin.getLanguageManager().getMessage("unknown")));
        descComponent.setClickEvent(null);
        descComponent.setHoverEvent(null);
        message.append(descComponent);
        message.append("\n");
        
        TextComponent statusText = new TextComponent(plugin.getLanguageManager().getMessage("plugin-info-status", status));
        statusText.setClickEvent(null);
        statusText.setHoverEvent(null);
        message.append(statusText);
        message.append("\n");

        // 如果安装了PlugMan，添加管理按钮
        if (hasPlugMan && sender.hasPermission("plugman.use")) {
            // 添加管理标签
            message.append(plugin.getMessageHelper().getManageLabel());

            // 添加隔离组件
            message.append(plugin.getMessageHelper().getSeparator());

            // 启用按钮
            TextComponent enable = new TextComponent(plugin.getLanguageManager().getMessage("button-enable"));
            enable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plugman enable " + name));
            enable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-enable")).create()));
            message.append(enable);

            // 添加隔离组件
            message.append(plugin.getMessageHelper().getSeparator());

            // 禁用按钮
            TextComponent disable = new TextComponent(plugin.getLanguageManager().getMessage("button-disable"));
            disable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plugman disable " + name));
            disable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-disable")).create()));
            message.append(disable);

            // 添加隔离组件
            message.append(plugin.getMessageHelper().getSeparator());

            // 重载按钮
            TextComponent reload = new TextComponent(plugin.getLanguageManager().getMessage("button-reload"));
            reload.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plugman reload " + name));
            reload.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-reload")).create()));
            message.append(reload);
            message.append("\n");
        }

        // 添加返回按钮
        message.append(plugin.getMessageHelper().getBackLabel());

        // 添加隔离组件
        message.append(plugin.getMessageHelper().getSeparator());

        // 添加返回按钮
        message.append(plugin.getMessageHelper().getBackButton());
        message.append("\n");

        // 发送消息内容
        sender.spigot().sendMessage(message.create());

        // 添加底部边框
        plugin.getMessageHelper().sendBottomBorder(sender);
        return true;
    }
} 