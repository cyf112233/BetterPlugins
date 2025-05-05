package top.cxkcxkckx.plugins.func;

import org.bukkit.configuration.file.YamlConfiguration;
import top.cxkcxkckx.plugins.plugins;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager {
    private final plugins plugin;
    private final Map<String, YamlConfiguration> languages;
    private String currentLanguage;
    private YamlConfiguration currentLang;

    public LanguageManager(plugins plugin) {
        this.plugin = plugin;
        this.languages = new HashMap<>();
    }

    public void loadLanguages() {
        plugin.getLogger().info("开始加载语言文件...");
        
        // 清空现有语言文件
        languages.clear();
        plugin.getLogger().info("已清空语言缓存");

        // 从jar中复制默认语言文件
        plugin.saveResource("lang/zh_CN.yml", false);
        plugin.saveResource("lang/en_US.yml", false);
        plugin.getLogger().info("已复制默认语言文件");

        // 加载所有语言文件
        File langFolder = new File(plugin.getDataFolder(), "lang");
        File[] langFiles = langFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (langFiles != null) {
            for (File file : langFiles) {
                String langName = file.getName().replace(".yml", "");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                languages.put(langName, config);
                plugin.getLogger().info("已加载语言文件: " + langName);
            }
        }

        // 从配置文件读取语言设置
        this.currentLanguage = plugin.getConfig().getString("language", "zh_CN");
        plugin.getLogger().info("从配置文件读取到语言设置: " + currentLanguage);
        
        setLanguage(currentLanguage);
    }

    public void setLanguage(String lang) {
        this.currentLanguage = lang;
        this.currentLang = languages.getOrDefault(lang, languages.get("zh_CN"));
        plugin.getLogger().info("当前语言已设置为: " + currentLanguage);
        plugin.getLogger().info("可用的语言文件: " + String.join(", ", languages.keySet()));
    }

    public String getMessage(String path) {
        String message = currentLang.getString(path, "§cMissing message: " + path);
        plugin.getLogger().info("获取消息: " + path + " -> " + message);
        return message;
    }

    public String getMessage(String path, Object... args) {
        String message = getMessage(path);
        for (int i = 0; i < args.length; i++) {
            message = message.replace("%s", args[i].toString());
        }
        return message;
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public Map<String, YamlConfiguration> getLanguages() {
        return languages;
    }
} 