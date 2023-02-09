package com.xzavier0722.mc.plugin.slimeglue.listener;

import com.xzavier0722.mc.plugin.slimefuncomplib.event.CargoWithdrawEvent;
import com.xzavier0722.mc.plugin.slimeglue.SlimeGlue;
import com.xzavier0722.mc.plugin.slimeglue.api.listener.IListener;
import com.xzavier0722.mc.plugin.slimeglue.api.listener.ISlimefunAndroidListener;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IBlockProtectionHandler;
import io.github.thebusybiscuit.slimefun4.api.events.AndroidFarmEvent;
import io.github.thebusybiscuit.slimefun4.api.events.AndroidMineEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SlimefunListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onAndroidMine(AndroidMineEvent e) {
        for (IListener l : SlimeGlue.moduleManager().getListeners(e.getAndroid().getAndroid().getId())) {
            if (l instanceof ISlimefunAndroidListener) {
                ((ISlimefunAndroidListener) l).onMine(e);
                if (e.isCancelled()) {
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onAndroidFarm(AndroidFarmEvent e) {
        for (IListener l : SlimeGlue.moduleManager().getListeners(e.getAndroid().getAndroid().getId())) {
            if (l instanceof ISlimefunAndroidListener) {
                ((ISlimefunAndroidListener) l).onFarm(e);
                if (e.isCancelled()) {
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCargoWithdraw(CargoWithdrawEvent e) {
        for (var each : SlimeGlue.moduleManager().getProtectionHandlers()) {
            if (each instanceof IBlockProtectionHandler handler && !handler.canCargoAccessBlock(e.getTarget().getLocation())) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void afterAndroidMine(AndroidMineEvent e) {
        for (IListener l : SlimeGlue.moduleManager().getListeners(e.getAndroid().getAndroid().getId())) {
            if (l instanceof ISlimefunAndroidListener) {
                ((ISlimefunAndroidListener) l).afterMine(new AndroidMineEvent(e.getBlock(), e.getAndroid()));
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void afterAndroidFarm(AndroidFarmEvent e) {
        for (IListener l : SlimeGlue.moduleManager().getListeners(e.getAndroid().getAndroid().getId())) {
            if (l instanceof ISlimefunAndroidListener) {
                ((ISlimefunAndroidListener) l).afterFarm(new AndroidFarmEvent(e.getBlock(), e.getAndroid(), e.isAdvanced(), e.getDrop()));
            }
        }
    }

}
