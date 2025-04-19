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
            sender.sendMessage("§c插件已被禁用，请检查配置文件");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§c用法: /plf <字母|other>");
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
        
        sender.sendMessage("§6筛选结果:");
        for (Plugin p : filteredPlugins) {
            String status = p.isEnabled() ? "§a已启用" : "§c已禁用";

            TextComponent nameComponent = new TextComponent(p.getName());
            nameComponent.setColor(p.isEnabled() ? net.md_5.bungee.api.ChatColor.GREEN : net.md_5.bungee.api.ChatColor.RED);
            nameComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pli " + p.getName()));
            nameComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§7点击查看插件信息").create()));

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
        TextComponent statsText = new TextComponent("§7插件总数: §f" + filteredPlugins.size() + " §8| §a已启用: §f" + enabledCount + " §8| §c已禁用: §f" + disabledCount);
        statsText.setClickEvent(null);
        statsText.setHoverEvent(null);
        sender.spigot().sendMessage(statsText);

        // 创建字母索引
        ComponentBuilder indexBuilder = new ComponentBuilder();
        
        // 添加顶部边框
        TextComponent topBorder = new TextComponent("§8┌─────────────────────────────────┐");
        topBorder.setClickEvent(null);
        topBorder.setHoverEvent(null);
        sender.spigot().sendMessage(topBorder);
        
        // 第一行：A-O
        TextComponent indexLabel = new TextComponent("§8│ §a索引§a: ");
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
            letter.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("点击显示 " + c + " 开头的插件").create()));
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
        TextComponent rightBorder = new TextComponent("§8│");
        rightBorder.setClickEvent(null);
        rightBorder.setHoverEvent(null);
        indexBuilder.append(rightBorder);
        sender.spigot().sendMessage(indexBuilder.create());
        
        // 添加分隔线
        TextComponent divider = new TextComponent("§8├─────────────────────────────────┤");
        divider.setClickEvent(null);
        divider.setHoverEvent(null);
        sender.spigot().sendMessage(divider);
        
        // 第二行：P-Z 和其他选项
        indexBuilder = new ComponentBuilder();
        indexLabel = new TextComponent("§8│ §a索引§a: ");
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
            letter.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("点击显示 " + c + " 开头的插件").create()));
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
        TextComponent other = new TextComponent(" §8| §e[其他]");
        other.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plf other"));
        other.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("点击显示非字母开头的插件").create()));
        indexBuilder.append(other);
        
        // 添加隔离组件
        separator = new TextComponent("");
        separator.setClickEvent(null);
        separator.setHoverEvent(null);
        indexBuilder.append(separator);
        
        // 添加重置按钮
        TextComponent reset = new TextComponent(" §8| §c[重置]");
        reset.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pl"));
        reset.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("点击显示所有插件").create()));
        indexBuilder.append(reset);
        
        // 添加右侧空格
        for (int i = 0; i < 9; i++) {
            TextComponent space = new TextComponent(" ");
            space.setClickEvent(null);
            space.setHoverEvent(null);
            indexBuilder.append(space);
        }
        
        // 添加右侧边框
        rightBorder = new TextComponent("§8│");
        rightBorder.setClickEvent(null);
        rightBorder.setHoverEvent(null);
        indexBuilder.append(rightBorder);
        sender.spigot().sendMessage(indexBuilder.create());
        
        // 添加底部边框
        TextComponent bottomBorder = new TextComponent("§8└─────────────────────────────────┘");
        bottomBorder.setClickEvent(null);
        bottomBorder.setHoverEvent(null);
        sender.spigot().sendMessage(bottomBorder);

        return true;
    }
} 