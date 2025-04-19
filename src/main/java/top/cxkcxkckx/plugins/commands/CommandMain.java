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
            sender.sendMessage("§c插件已被禁用，请检查配置文件");
            return true;
        }
        
        if (args.length == 0) {
            sender.sendMessage("§6已安装的插件:");
            List<Plugin> plugins = Arrays.asList(plugin.getServer().getPluginManager().getPlugins());
            
            // 按首字母排序
            plugins.sort((p1, p2) -> {
                char c1 = p1.getName().toLowerCase().charAt(0);
                char c2 = p2.getName().toLowerCase().charAt(0);
                return Character.compare(c1, c2);
            });
            
            // 显示所有插件
            for (Plugin p : plugins) {
                String status = p.isEnabled() ? "§a已启用" : "§c已禁用";
                
                // 创建插件名称的点击组件
                TextComponent nameComponent = new TextComponent(p.getName());
                nameComponent.setColor(p.isEnabled() ? net.md_5.bungee.api.ChatColor.GREEN : net.md_5.bungee.api.ChatColor.RED);
                nameComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pli " + p.getName()));
                nameComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                    new ComponentBuilder("§7点击查看插件信息").create()));
                
                // 创建完整消息
                ComponentBuilder message = new ComponentBuilder("§7- ");
                message.append(nameComponent);
                message.append(" §8v" + p.getDescription().getVersion() + " §8| " + status);
                
                sender.spigot().sendMessage(message.create());
            }

            // 创建字母索引
            ComponentBuilder indexBuilder = new ComponentBuilder("§8┌─────────────────────────────────┐\n");
            
            // 第一行：A-O
            indexBuilder.append("§8│ §a索引§a: ");
            for (char c = 'A'; c <= 'O'; c++) {
                // 添加字母
                TextComponent letter = new TextComponent(String.valueOf(c));
                letter.setColor(net.md_5.bungee.api.ChatColor.GOLD);
                letter.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plf " + c));
                letter.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("点击显示 " + c + " 开头的插件").create()));
                indexBuilder.append(letter);

                // 添加分隔线（除了最后一个字母）
                if (c != 'O') {
                    TextComponent separator = new TextComponent(" §8| ");
                    indexBuilder.append(separator);
                }
            }
            // 使用循环来重复空格
            for (int i = 0; i < 11; i++) {
                indexBuilder.append(" ");
            }
            indexBuilder.append("§8│\n");
            
            // 添加分隔线
            indexBuilder.append("§8├─────────────────────────────────┤\n");
            
            // 第二行：P-Z 和其他选项
            indexBuilder.append("§8│ §a索引§a: ");
            for (char c = 'P'; c <= 'Z'; c++) {
                // 添加字母
                TextComponent letter = new TextComponent(String.valueOf(c));
                letter.setColor(net.md_5.bungee.api.ChatColor.GOLD);
                letter.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plf " + c));
                letter.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("点击显示 " + c + " 开头的插件").create()));
                indexBuilder.append(letter);

                // 添加分隔线（除了最后一个字母）
                if (c != 'Z') {
                    TextComponent separator = new TextComponent(" §8| ");
                    indexBuilder.append(separator);
                }
            }
            
            // 添加其他选项
            TextComponent other = new TextComponent(" §8| §e[其他]");
            other.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plf other"));
            other.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("点击显示非字母开头的插件").create()));
            indexBuilder.append(other);
            
            // 添加重置按钮
            TextComponent reset = new TextComponent(" §8| §c[重置]");
            reset.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pl"));
            reset.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("点击显示所有插件").create()));
            indexBuilder.append(reset);
            
            // 使用循环来重复空格
            for (int i = 0; i < 9; i++) {
                indexBuilder.append(" ");
            }
            indexBuilder.append("§8│\n");
            indexBuilder.append("§8└─────────────────────────────────┘");

            sender.spigot().sendMessage(indexBuilder.create());
            return true;
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
                sender.sendMessage("§c未找到匹配的插件");
                return true;
            }
            
            // 按首字母排序
            matches.sort((p1, p2) -> {
                char c1 = p1.getName().toLowerCase().charAt(0);
                char c2 = p2.getName().toLowerCase().charAt(0);
                return Character.compare(c1, c2);
            });
            
            sender.sendMessage("§6搜索结果 (" + matches.size() + "):");
            for (Plugin p : matches) {
                String status = p.isEnabled() ? "§a已启用" : "§c已禁用";
                
                // 创建插件名称的点击组件
                TextComponent nameComponent = new TextComponent(p.getName());
                nameComponent.setColor(p.isEnabled() ? net.md_5.bungee.api.ChatColor.GREEN : net.md_5.bungee.api.ChatColor.RED);
                nameComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pli " + p.getName()));
                nameComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
                    new ComponentBuilder("§7点击查看插件信息").create()));
                
                // 创建完整消息
                ComponentBuilder message = new ComponentBuilder("§7- ");
                message.append(nameComponent);
                message.append(" §8v" + p.getDescription().getVersion() + " §8| " + status);
                
                sender.spigot().sendMessage(message.create());
            }
            return true;
        }
        
        if (args.length == 1 && "hello".equalsIgnoreCase(args[0])) {
            return t(sender, "Hello World!");
        }
        if (args.length == 1 && "reload".equalsIgnoreCase(args[0]) && sender.isOp()) {
            plugin.reloadConfig();
            return t(sender, "&a配置文件已重载");
        }
        return true;
    }

    private static final List<String> emptyList = Lists.newArrayList();
    private static final List<String> listArg0 = Lists.newArrayList(
            "hello");
    private static final List<String> listOpArg0 = Lists.newArrayList(
            "hello", "reload");
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return startsWith(sender.isOp() ? listOpArg0 : listArg0, args[0]);
        }
        return emptyList;
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
}
