package top.cxkcxkckx.plugins.func;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import java.util.logging.Logger;

public class LoggerManager {
    private final Plugin plugin;
    private final FileConfiguration config;
    private Logger logger;

    public LoggerManager(Plugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.logger = plugin.getLogger();
    }

    public void reload() {
        // 重新加载配置
        plugin.reloadConfig();
    }

    public void debug(String message) {
        if (config.getBoolean("debug.enabled", false)) {
            logger.info("[DEBUG] " + message);
        }
    }

    public void info(String message) {
        logger.info(message);
    }

    public void warning(String message) {
        logger.warning(message);
    }

    public void error(String message) {
        logger.severe(message);
    }
} 