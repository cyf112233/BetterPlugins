package top.cxkcxkckx.plugins;
        
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import top.mrxiaom.pluginbase.BukkitPlugin;
import top.cxkcxkckx.plugins.func.LanguageManager;
import top.cxkcxkckx.plugins.func.MessageHelper;
import top.cxkcxkckx.plugins.func.LoggerManager;

public final class plugins extends BukkitPlugin {
    private static plugins instance;
    private boolean pluginEnabled;
    private LanguageManager languageManager;
    private MessageHelper messageHelper;
    private LoggerManager loggerManager;

    public plugins() {
        super(options()
                .bungee(false)
                .adventure(false)
                .database(false)
                .reconnectDatabaseWhenReloadConfig(false)
                .vaultEconomy(false)
                .scanIgnore("top.mrxiaom.example.libs")
        );
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    protected void afterEnable() {
        // 保存默认配置
        saveDefaultConfig();
        
        // 读取配置
        reloadConfig();
        
        // 初始化日志管理器
        loggerManager = new LoggerManager(this);
        
        // 初始化语言管理器
        languageManager = new LanguageManager(this);
        languageManager.loadLanguages();
        
        // 初始化消息助手
        messageHelper = new MessageHelper(this);
        
        // 获取插件是否启用
        pluginEnabled = getConfig().getBoolean("enabled", true);
        
        if (!pluginEnabled) {
            loggerManager.info(languageManager.getMessage("plugin-disabled"));
            return;
        }
        
        loggerManager.info(languageManager.getMessage("plugin-loaded"));
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        if (loggerManager != null) {
            loggerManager.reload();
        }
    }

    public static plugins getInstance() {
        return instance;
    }
    
    public boolean isPluginEnabled() {
        return pluginEnabled;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public MessageHelper getMessageHelper() {
        return messageHelper;
    }

    public LoggerManager getLoggerManager() {
        return loggerManager;
    }
}
