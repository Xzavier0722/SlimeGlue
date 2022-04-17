package com.xzavier0722.mc.plugin.slimeglue;

import com.xzavier0722.mc.plugin.slimeglue.listener.BlockListener;
import com.xzavier0722.mc.plugin.slimeglue.listener.PluginListener;
import com.xzavier0722.mc.plugin.slimeglue.manager.CompatibilityModuleManager;
import com.xzavier0722.mc.plugin.slimeglue.module.KingdomsXModule;
import com.xzavier0722.mc.plugin.slimeglue.slimefun.GlueProtectionModule;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public final class SlimeGlue extends JavaPlugin implements SlimefunAddon {

    private static SlimeGlue instance;
    private static GlueLogger logger;
    private static CompatibilityModuleManager moduleManager;

    @Override
    public void onEnable() {
        instance = this;
        logger = new GlueLogger(getLogger());
        logger.i("====SlimeGlue Start====");
        moduleManager = new CompatibilityModuleManager();

        logger.i("- Loading modules...");
        registerModules();
        moduleManager().load();

        logger.i("- Registering listeners...");
        getServer().getPluginManager().registerEvents(new PluginListener(), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);

        logger.i("- Registering protection module...");
        Slimefun.getProtectionManager().registerModule(getServer().getPluginManager(), "SlimeGlue", (p) -> new GlueProtectionModule());

        logger.i("- SlimeGlue Started!");
        logger.i("=======================");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/Xzavier0722/SlimeGlue/issues";
    }

    public static SlimeGlue instance() {
        return instance;
    }

    public static GlueLogger logger() {
        return logger;
    }

    public static CompatibilityModuleManager moduleManager() {
        return moduleManager;
    }

    private void registerModules() {
        moduleManager().register(new KingdomsXModule());
    }

}
