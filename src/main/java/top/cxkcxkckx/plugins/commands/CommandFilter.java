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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AutoRegister
public class CommandFilter extends AbstractModule implements CommandExecutor {
    public CommandFilter(plugins plugin) {
        super(plugin);
        registerCommand("plf", this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // 检查插件是否启用
        if (!plugin.isPluginEnabled()) {
            plugin.getMessageHelper().sendPluginDisabled(sender);
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(plugin.getLanguageManager().getMessage("usage-plf"));
            return true;
        }

        String filter = args[0].toLowerCase();
        List<Plugin> plugins = Arrays.asList(plugin.getServer().getPluginManager().getPlugins());
        
        // 按首字母排序
        plugins.sort((p1, p2) -> {
            char c1 = p1.getName().toLowerCase().charAt(0);
            char c2 = p2.getName().toLowerCase().charAt(0);
            return Character.compare(c1, c2);
        });
        
        // 过滤插件
        List<Plugin> filteredPlugins = new ArrayList<>();
        for (Plugin p : plugins) {
            String name = p.getName();
            char firstChar = name.toLowerCase().charAt(0);
            
            if (filter.equalsIgnoreCase("other")) {
                // 处理非字母开头的插件
                if (!Character.isLetter(firstChar)) {
                    filteredPlugins.add(p);
                }
            } else {
                // 处理字母开头的插件
                if (Character.isLetter(firstChar) && 
                    Character.toLowerCase(firstChar) == Character.toLowerCase(filter.charAt(0))) {
                    filteredPlugins.add(p);
                }
            }
        }

        // 清屏效果
        for (int i = 0; i < 20; i++) {
            sender.sendMessage("");
        }
        
        sender.sendMessage(plugin.getLanguageManager().getMessage("plugin-filter-title"));
        for (Plugin p : filteredPlugins) {
            String status = plugin.getMessageHelper().getPluginStatus(p.isEnabled());

            TextComponent nameComponent = new TextComponent(p.getName());
            nameComponent.setColor(p.isEnabled() ? net.md_5.bungee.api.ChatColor.GREEN : net.md_5.bungee.api.ChatColor.RED);
            nameComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pli " + p.getName()));
            nameComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-view")).create()));

            ComponentBuilder message = new ComponentBuilder("§7- ");
            message.append(nameComponent);
            message.append(" §8v" + p.getDescription().getVersion() + " §8| " + status);

            sender.spigot().sendMessage(message.create());
        }

        // 添加插件数量统计
        int enabledCount = 0;
        int disabledCount = 0;
        for (Plugin p : filteredPlugins) {
            if (p.isEnabled()) {
                enabledCount++;
            } else {
                disabledCount++;
            }
        }
        
        // 创建插件数量统计消息
        sender.spigot().sendMessage(plugin.getMessageHelper().getStatsText(
            filteredPlugins.size(), enabledCount, disabledCount));

        // 创建字母索引
        ComponentBuilder indexBuilder = new ComponentBuilder();
        
        // 添加顶部边框
        plugin.getMessageHelper().sendTopBorder(sender);
        
        // 第一行：A-O
        indexBuilder.append(plugin.getMessageHelper().getIndexLabel());
        
        for (char c = 'A'; c <= 'O'; c++) {
            // 添加隔离组件
            indexBuilder.append(plugin.getMessageHelper().getSeparator());

            // 添加字母
            TextComponent letter = new TextComponent(String.valueOf(c));
            letter.setColor(net.md_5.bungee.api.ChatColor.GOLD);
            letter.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plf " + c));
            letter.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-show", c)).create()));
            indexBuilder.append(letter);

            // 添加隔离组件
            indexBuilder.append(plugin.getMessageHelper().getSeparator());

            // 添加分隔线（除了最后一个字母）
            if (c != 'O') {
                indexBuilder.append(plugin.getMessageHelper().getDivider());
            }
        }
        
        // 添加右侧空格
        for (int i = 0; i < 11; i++) {
            indexBuilder.append(plugin.getMessageHelper().getSpace());
        }
        
        // 添加右侧边框
        indexBuilder.append(plugin.getMessageHelper().getRightBorder());
        sender.spigot().sendMessage(indexBuilder.create());
        
        // 添加分隔线
        plugin.getMessageHelper().sendMiddleBorder(sender);
        
        // 第二行：P-Z 和其他选项
        indexBuilder = new ComponentBuilder();
        indexBuilder.append(plugin.getMessageHelper().getIndexLabel());
        
        for (char c = 'P'; c <= 'Z'; c++) {
            // 添加隔离组件
            indexBuilder.append(plugin.getMessageHelper().getSeparator());

            // 添加字母
            TextComponent letter = new TextComponent(String.valueOf(c));
            letter.setColor(net.md_5.bungee.api.ChatColor.GOLD);
            letter.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plf " + c));
            letter.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                new ComponentBuilder(plugin.getLanguageManager().getMessage("click-to-show", c)).create()));
            indexBuilder.append(letter);

            // 添加隔离组件
            indexBuilder.append(plugin.getMessageHelper().getSeparator());

            // 添加分隔线（除了最后一个字母）
            if (c != 'Z') {
                indexBuilder.append(plugin.getMessageHelper().getDivider());
            }
        }
        
        // 添加隔离组件
        indexBuilder.append(plugin.getMessageHelper().getSeparator());
        
        // 添加其他选项
        indexBuilder.append(plugin.getMessageHelper().getOtherButton());
        
        // 添加隔离组件
        indexBuilder.append(plugin.getMessageHelper().getSeparator());
        
        // 添加重置按钮
        indexBuilder.append(plugin.getMessageHelper().getResetButton());
        
        // 添加右侧空格
        for (int i = 0; i < 9; i++) {
            indexBuilder.append(plugin.getMessageHelper().getSpace());
        }
        
        // 添加右侧边框
        indexBuilder.append(plugin.getMessageHelper().getRightBorder());
        sender.spigot().sendMessage(indexBuilder.create());
        
        // 添加底部边框
        plugin.getMessageHelper().sendBottomBorder(sender);

        return true;
    }
} 