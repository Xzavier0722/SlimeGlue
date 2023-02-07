package com.xzavier0722.mc.plugin.slimeglue.api;

import com.xzavier0722.mc.plugin.slimeglue.SlimeGlue;
import com.xzavier0722.mc.plugin.slimeglue.api.listener.IListener;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IProtectionHandler;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public abstract class ACompatibilityModule  {

    private final Set<IListener> listeners;
    private final Set<IProtectionHandler> protectionHandlers;

    public ACompatibilityModule() {
        listeners = new HashSet<>();
        protectionHandlers = new HashSet<>();
    }

    public Set<IListener> getListeners() {
        return listeners;
    }

    public Set<IProtectionHandler> getProtectionHandlers() {
        return protectionHandlers;
    }

    protected void addListener(IListener listener) {
        listeners.add(listener);
    }

    protected void removeListener(IListener listener) {
        listeners.remove(listener);
    }

    protected void addProtectionHandler(IProtectionHandler handler) {
        protectionHandlers.add(handler);
    }

    protected void removeProtectionHandler(IProtectionHandler handler) {
        protectionHandlers.remove(handler);
    }

    protected void verbose(String msg) {
        SlimeGlue.logger().v("[" + getClass().getSimpleName() + "]: " + msg);
    }

    protected <T> T verbose(String msg, T para) {
        verbose(msg + para);
        return para;
    }

    /**
     *
     * Define the compatible target plugin name
     *
     * @return name of the plugin
     */
    abstract public String getCompatibilityPluginName();

    /**
     *
     * Fired when the plugin specified in {@link #getCompatibilityPluginName()} is present
     *
     * @param plugin: the {@link Plugin} instance of the specific plugin
     */
    abstract public void enable(Plugin plugin) throws Exception;

    /**
     *
     * Fired when the plugin specified in {@link #getCompatibilityPluginName()} is disabled
     *
     */
    abstract public void disable();

}
