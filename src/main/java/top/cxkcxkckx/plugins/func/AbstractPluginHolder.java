package top.cxkcxkckx.plugins.func;
        
import top.cxkcxkckx.plugins.plugins;

@SuppressWarnings({"unused"})
public abstract class AbstractPluginHolder extends top.mrxiaom.pluginbase.func.AbstractPluginHolder<plugins> {
    public AbstractPluginHolder(plugins plugin) {
        super(plugin);
    }

    public AbstractPluginHolder(plugins plugin, boolean register) {
        super(plugin, register);
    }
}
