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
            sender.sendMessage("§c插件已被禁用，请检查配置文件");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§c用法: /pli <插件名>");
            return true;
        }

        String pluginName = args[0];
        Plugin targetPlugin = plugin.getServer().getPluginManager().getPlugin(pluginName);

        if (targetPlugin == null) {
            sender.sendMessage("§c未找到插件: " + pluginName);
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
        String website = targetPlugin.getDescription().getWebsite();
        String apiVersion = targetPlugin.getDescription().getAPIVersion();
        List<String> dependencies = targetPlugin.getDescription().getDepend();
        List<String> softDependencies = targetPlugin.getDescription().getSoftDepend();
        List<String> loadBefore = targetPlugin.getDescription().getLoadBefore();
        String status = targetPlugin.isEnabled() ? "§a已启用" : "§c已禁用";

        // 清屏效果
        for (int i = 0; i < 20; i++) {
            sender.sendMessage("");
        }

        // 创建消息
        ComponentBuilder message = new ComponentBuilder();
        
        // 添加顶部边框
        TextComponent topBorder = new TextComponent("§8┌─────────────────────────────────┐");
        topBorder.setClickEvent(null);
        topBorder.setHoverEvent(null);
        sender.spigot().sendMessage(topBorder);
        
        // 添加标题
        TextComponent title = new TextComponent("§6插件信息:");
        title.setClickEvent(null);
        title.setHoverEvent(null);
        message.append(title);
        message.append("\n");
        
        // 添加插件信息
        TextComponent nameComponent = new TextComponent("§7名称: §f" + name);
        nameComponent.setClickEvent(null);
        nameComponent.setHoverEvent(null);
        message.append(nameComponent);
        message.append("\n");
        
        TextComponent versionComponent = new TextComponent("§7版本: §f" + version);
        versionComponent.setClickEvent(null);
        versionComponent.setHoverEvent(null);
        message.append(versionComponent);
        message.append("\n");
        
        TextComponent authorComponent = new TextComponent("§7作者: §f" + authors);
        authorComponent.setClickEvent(null);
        authorComponent.setHoverEvent(null);
        message.append(authorComponent);
        message.append("\n");
        
        TextComponent descComponent = new TextComponent("§7描述: §f" + description);
        descComponent.setClickEvent(null);
        descComponent.setHoverEvent(null);
        message.append(descComponent);
        message.append("\n");
        
        TextComponent statusText = new TextComponent("§7状态: " + status);
        statusText.setClickEvent(null);
        statusText.setHoverEvent(null);
        message.append(statusText);
        message.append("\n");

        // 如果安装了PlugMan，添加管理按钮
        if (hasPlugMan && sender.hasPermission("plugman.use")) {
            // 添加管理标签
            TextComponent manageLabel = new TextComponent("§7管理: ");
            manageLabel.setClickEvent(null);
            manageLabel.setHoverEvent(null);
            message.append(manageLabel);

            // 添加隔离组件
            TextComponent separator = new TextComponent("");
            separator.setClickEvent(null);
            separator.setHoverEvent(null);
            message.append(separator);

            // 启用按钮
            TextComponent enable = new TextComponent("§a[启用] ");
            enable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plugman enable " + name));
            enable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§7点击启用插件").create()));
            message.append(enable);

            // 添加隔离组件
            separator = new TextComponent("");
            separator.setClickEvent(null);
            separator.setHoverEvent(null);
            message.append(separator);

            // 禁用按钮
            TextComponent disable = new TextComponent("§c[禁用] ");
            disable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plugman disable " + name));
            disable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§7点击禁用插件").create()));
            message.append(disable);

            // 添加隔离组件
            separator = new TextComponent("");
            separator.setClickEvent(null);
            separator.setHoverEvent(null);
            message.append(separator);

            // 重载按钮
            TextComponent reload = new TextComponent("§e[重载]");
            reload.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plugman reload " + name));
            reload.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§7点击重载插件").create()));
            message.append(reload);
            message.append("\n");
        }

        // 添加返回按钮
        TextComponent returnLabel = new TextComponent("§7返回: ");
        returnLabel.setClickEvent(null);
        returnLabel.setHoverEvent(null);
        message.append(returnLabel);

        // 添加隔离组件
        TextComponent separator = new TextComponent("");
        separator.setClickEvent(null);
        separator.setHoverEvent(null);
        message.append(separator);

        TextComponent back = new TextComponent("§e[插件列表]");
        back.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pl"));
        back.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
            new ComponentBuilder("§7点击返回插件列表").create()));
        message.append(back);
        message.append("\n");

        // 添加底部边框
        TextComponent bottomBorder = new TextComponent("§8└─────────────────────────────────┘");
        bottomBorder.setClickEvent(null);
        bottomBorder.setHoverEvent(null);
        message.append(bottomBorder);
        sender.spigot().sendMessage(message.create());
        return true;
    }
} 