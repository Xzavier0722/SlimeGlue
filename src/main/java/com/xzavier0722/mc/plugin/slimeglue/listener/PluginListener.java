package com.xzavier0722.mc.plugin.slimeglue.listener;

import com.xzavier0722.mc.plugin.slimeglue.SlimeGlue;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class PluginListener implements Listener {

    @EventHandler
    public void onPluginEnable(PluginEnableEvent e) {
        SlimeGlue.moduleManager().load(e.getPlugin().getName());
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent e) {
        SlimeGlue.moduleManager().unload(e.getPlugin().getName());
    }

}
