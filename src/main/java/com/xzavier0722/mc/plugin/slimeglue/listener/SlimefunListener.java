package com.xzavier0722.mc.plugin.slimeglue.listener;

import com.xzavier0722.mc.plugin.slimeglue.SlimeGlue;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IBlockProtectionHandler;
import io.github.thebusybiscuit.slimefun4.api.events.BlockPlacerPlaceEvent;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.UUID;

public class SlimefunListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPlacerPlace(BlockPlacerPlaceEvent e) {
        var l = e.getBlockPlacer().getLocation();
        var ownerUuid = BlockStorage.getLocationInfo(l, "owner");
        if (ownerUuid == null) {
            return;
        }

        var owner = Bukkit.getOfflinePlayer(UUID.fromString(ownerUuid));
        for (var each : SlimeGlue.moduleManager().getProtectionHandlers()) {
            if (each instanceof IBlockProtectionHandler handler) {
                if (!handler.canPlaceBlock(owner, e.getBlock().getLocation())) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
