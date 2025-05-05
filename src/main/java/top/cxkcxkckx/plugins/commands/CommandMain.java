package top.cxkcxkckx.plugins.commands;
        
import com.google.common.collect.Lists;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.mrxiaom.pluginbase.func.AutoRegister;
import top.cxkcxkckx.plugins.plugins;
import top.cxkcxkckx.plugins.func.AbstractModule;

import java.util.*;

@AutoRegister
public class CommandMain extends AbstractModule implements CommandExecutor, TabCompleter, Listener {
    public CommandMain(plugins plugin) {
        super(plugin);
        registerCommand("pl", this);
        registerCommand("plugins", this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // 检查插件是否启用
        if (!plugin.isPluginEnabled()) {
            sender.sendMessage(plugin.getLanguageManager().getMessage("plugin-disabled"));
            return true;
        }
        
        if (args.length == 0) {
            return showPluginList(sender);
        }
        
        // 处理插件搜索
        if (args.length >= 1) {
            String search = String.join(" ", args).toLowerCase();
            List<Plugin> plugins = Arrays.asList(plugin.getServer().getPluginManager().getPlugins());
            List<Plugin> matches = new ArrayList<>();
            
            for (Plugin p : plugins) {
                if (p.getName().toLowerCase().contains(search)) {
                    matches.add(p);
                }
            }
            
            if (matches.isEmpty()) {
                sender.sendMessage(plugin.getLanguageManager().getMessage("plugin-not-found", search));
                return true;
            }
            
            // 清屏效果
            for (int i = 0; i < 20; i++) {
                sender.sendMessage("");
            }
            
            // 按首字母排序
            matches.sort((p1, p2) -> {
                char c1 = p1.getName().toLowerCase().charAt(0);
                char c2 = p2.getName().toLowerCase().charAt(0);
                return Character.compare(c1, c2);
            });
            
            // 添加插件数量统计
            int enabledCount = 0;
            int disabledCount = 0;
            for (Plugin p : matches) {
                if (p.isEnabled()) {
                    enabledCount++;
                } else {
                    disabledCount++;
                }
            }
            
            // 显示搜索结果标题
            sender.sendMessage(plugin.getLanguageManager().getMessage("plugin-search-title", matches.size()));
            
            // 创建插件数量统计消息
            sender.spigot().sendMessage(plugin.getMessageHelper().getStatsText(
                matches.size(), enabledCount, disabledCount));
            
            // 显示插件列表
            for (Plugin p : matches) {
                String status = p.isEnabled() ? 
                    plugin.getLanguageManager().getMessage("plugin-info-status-enabled") : 
                    plugin.getLanguageManager().getMessage("plugin-info-status-disabled");
                
                // 创建插件名称的点击组件
                TextComponent nameComponent = new TextComponent(p.getName());
                nameComponent.setColor(p.isEnabled() ? net.md_5.bungee.api.ChatColor.GREEN : net.md_5.bungee.api.ChatColor.RED);
                nameComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pli " + p.getName()));
                nameComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                    new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-view")).create()));
                
                // 创建完整消息
                ComponentBuilder message = new ComponentBuilder("§7- ");
                message.append(nameComponent);
                message.append(" §8v" + p.getDescription().getVersion() + " §8| " + status);
                
                sender.spigot().sendMessage(message.create());
            }
            return true;
        }
        
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("plugins.reload")) {
                    return t(sender, plugin.getLanguageManager().getMessage("no-permission"));
                }
                plugin.reloadConfig();
                return t(sender, plugin.getLanguageManager().getMessage("config-reloaded"));
            }
            if (args[0].equalsIgnoreCase("list")) {
                return showPluginList(sender);
            }
        }
        
        return t(sender, plugin.getLanguageManager().getMessage("usage-pl"));
    }

    private static final List<String> emptyList = Lists.newArrayList();
    private static final List<String> listArg0 = Lists.newArrayList(
            "hello");
    private static final List<String> listOpArg0 = Lists.newArrayList(
            "hello", "reload");
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }

    public List<String> startsWith(Collection<String> list, String s) {
        return startsWith(null, list, s);
    }
    public List<String> startsWith(String[] addition, Collection<String> list, String s) {
        String s1 = s.toLowerCase();
        List<String> stringList = new ArrayList<>(list);
        if (addition != null) stringList.addAll(0, Lists.newArrayList(addition));
        stringList.removeIf(it -> !it.toLowerCase().startsWith(s1));
        return stringList;
    }

    private boolean showPluginList(CommandSender sender) {
        // 清屏效果
        for (int i = 0; i < 20; i++) {
            sender.sendMessage("");
        }
        
        sender.sendMessage(plugin.getLanguageManager().getMessage("plugin-list-title"));
        List<Plugin> plugins = Arrays.asList(plugin.getServer().getPluginManager().getPlugins());
        
        // 按首字母排序
        plugins.sort((p1, p2) -> {
            char c1 = p1.getName().toLowerCase().charAt(0);
            char c2 = p2.getName().toLowerCase().charAt(0);
            return Character.compare(c1, c2);
        });

        // 添加插件数量统计
        int enabledCount = 0;
        int disabledCount = 0;
        for (Plugin p : plugins) {
            if (p.isEnabled()) {
                enabledCount++;
            } else {
                disabledCount++;
            }
        }
        
        // 显示所有插件
        for (Plugin p : plugins) {
            String status = p.isEnabled() ? 
                plugin.getLanguageManager().getMessage("plugin-info-status-enabled") : 
                plugin.getLanguageManager().getMessage("plugin-info-status-disabled");
            
            // 创建插件名称的点击组件
            TextComponent nameComponent = new TextComponent(p.getName());
            nameComponent.setColor(p.isEnabled() ? net.md_5.bungee.api.ChatColor.GREEN : net.md_5.bungee.api.ChatColor.RED);
            nameComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pli " + p.getName()));
            nameComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-view")).create()));
            
            // 创建完整消息
            ComponentBuilder message = new ComponentBuilder("§7- ");
            message.append(nameComponent);
            message.append(" §8v" + p.getDescription().getVersion() + " §8| " + status);
            
            sender.spigot().sendMessage(message.create());
        }

        // 创建插件数量统计消息
        sender.spigot().sendMessage(plugin.getMessageHelper().getStatsText(
            plugins.size(), enabledCount, disabledCount));

        // 创建字母索引
        ComponentBuilder indexBuilder = new ComponentBuilder();
        
        // 添加顶部边框
        plugin.getMessageHelper().sendTopBorder(sender);
        
        // 第一行：A-O
        TextComponent indexLabel = new TextComponent(plugin.getLanguageManager().getMessage("label-index"));
        indexLabel.setClickEvent(null);
        indexLabel.setHoverEvent(null);
        indexBuilder.append(indexLabel);
        
        for (char c = 'A'; c <= 'O'; c++) {
            // 添加隔离组件
            TextComponent separator = new TextComponent("");
            separator.setClickEvent(null);
            separator.setHoverEvent(null);
            indexBuilder.append(separator);

            // 添加字母
            TextComponent letter = new TextComponent(String.valueOf(c));
            letter.setColor(net.md_5.bungee.api.ChatColor.GOLD);
            letter.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plf " + c));
            letter.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-show", c)).create()));
            indexBuilder.append(letter);

            // 添加隔离组件
            separator = new TextComponent("");
            separator.setClickEvent(null);
            separator.setHoverEvent(null);
            indexBuilder.append(separator);

            // 添加分隔线（除了最后一个字母）
            if (c != 'O') {
                TextComponent divider = new TextComponent(" §8| ");
                divider.setClickEvent(null);
                divider.setHoverEvent(null);
                indexBuilder.append(divider);
            }
        }
        
        // 添加右侧空格
        for (int i = 0; i < 11; i++) {
            TextComponent space = new TextComponent(" ");
            space.setClickEvent(null);
            space.setHoverEvent(null);
            indexBuilder.append(space);
        }
        
        // 添加右侧边框
        TextComponent rightBorder = new TextComponent(plugin.getLanguageManager().getMessage("border-right"));
        rightBorder.setClickEvent(null);
        rightBorder.setHoverEvent(null);
        indexBuilder.append(rightBorder);
        sender.spigot().sendMessage(indexBuilder.create());
        
        // 添加分隔线
        TextComponent divider = new TextComponent(plugin.getLanguageManager().getMessage("border-middle"));
        divider.setClickEvent(null);
        divider.setHoverEvent(null);
        sender.spigot().sendMessage(divider);
        
        // 第二行：P-Z 和其他选项
        indexBuilder = new ComponentBuilder();
        indexLabel = new TextComponent(plugin.getLanguageManager().getMessage("label-index"));
        indexLabel.setClickEvent(null);
        indexLabel.setHoverEvent(null);
        indexBuilder.append(indexLabel);
        
        for (char c = 'P'; c <= 'Z'; c++) {
            // 添加隔离组件
            TextComponent separator = new TextComponent("");
            separator.setClickEvent(null);
            separator.setHoverEvent(null);
            indexBuilder.append(separator);

            // 添加字母
            TextComponent letter = new TextComponent(String.valueOf(c));
            letter.setColor(net.md_5.bungee.api.ChatColor.GOLD);
            letter.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plf " + c));
            letter.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-show", c)).create()));
            indexBuilder.append(letter);

            // 添加隔离组件
            separator = new TextComponent("");
            separator.setClickEvent(null);
            separator.setHoverEvent(null);
            indexBuilder.append(separator);

            // 添加分隔线（除了最后一个字母）
            if (c != 'Z') {
                TextComponent divider2 = new TextComponent(" §8| ");
                divider2.setClickEvent(null);
                divider2.setHoverEvent(null);
                indexBuilder.append(divider2);
            }
        }
        
        // 添加隔离组件
        TextComponent separator = new TextComponent("");
        separator.setClickEvent(null);
        separator.setHoverEvent(null);
        indexBuilder.append(separator);
        
        // 添加其他选项
        TextComponent other = new TextComponent(" §8| " + plugin.getLanguageManager().getMessage("button-other"));
        other.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plf other"));
        other.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
            new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-show-other")).create()));
        indexBuilder.append(other);
        
        // 添加隔离组件
        separator = new TextComponent("");
        separator.setClickEvent(null);
        separator.setHoverEvent(null);
        indexBuilder.append(separator);
        
        // 添加重置按钮
        TextComponent reset = new TextComponent(" §8| " + plugin.getLanguageManager().getMessage("button-reset"));
        reset.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pl"));
        reset.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
            new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-show-all")).create()));
        indexBuilder.append(reset);
        
        // 添加右侧空格
        for (int i = 0; i < 9; i++) {
            TextComponent space = new TextComponent(" ");
            space.setClickEvent(null);
            space.setHoverEvent(null);
            indexBuilder.append(space);
        }
        
        // 添加右侧边框
        rightBorder = new TextComponent(plugin.getLanguageManager().getMessage("border-right"));
        rightBorder.setClickEvent(null);
        rightBorder.setHoverEvent(null);
        indexBuilder.append(rightBorder);
        sender.spigot().sendMessage(indexBuilder.create());
        
        // 添加底部边框
        TextComponent bottomBorder = new TextComponent(plugin.getLanguageManager().getMessage("border-bottom"));
        bottomBorder.setClickEvent(null);
        bottomBorder.setHoverEvent(null);
        sender.spigot().sendMessage(bottomBorder);

        return true;
    }
}
