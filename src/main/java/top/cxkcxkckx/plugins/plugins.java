package top.cxkcxkckx.plugins;
        
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import top.mrxiaom.pluginbase.BukkitPlugin;

public final class plugins extends BukkitPlugin {
    private static plugins instance;
    private boolean pluginEnabled;

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
        
        // 获取插件是否启用
        pluginEnabled = getConfig().getBoolean("enabled", true);
        
        if (!pluginEnabled) {
            getLogger().info("插件已禁用，如需启用请修改配置文件");
            return;
        }
        
        getLogger().info("BetterPlugins 加载完毕");
    }

    public static plugins getInstance() {
        return instance;
    }
    
    public boolean isPluginEnabled() {
        return pluginEnabled;
    }
}
