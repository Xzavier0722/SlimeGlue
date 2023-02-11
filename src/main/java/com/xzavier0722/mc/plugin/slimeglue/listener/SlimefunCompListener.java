package com.xzavier0722.mc.plugin.slimeglue.listener;

import com.xzavier0722.mc.plugin.slimefuncomplib.event.cargo.CargoOperationEvent;
import com.xzavier0722.mc.plugin.slimeglue.SlimeGlue;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IBlockProtectionHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SlimefunCompListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCargoOperation(CargoOperationEvent e) {
        var l = e.getTarget().getLocation();
        v("onCargoOperation: " + l);
        for (var each : SlimeGlue.moduleManager().getProtectionHandlers()) {
            if (each instanceof IBlockProtectionHandler handler && !handler.canCargoAccessBlock(l)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    private void v(String msg) {
        SlimeGlue.logger().v("[SlimefunCompListener]: " + msg);
    }

}
