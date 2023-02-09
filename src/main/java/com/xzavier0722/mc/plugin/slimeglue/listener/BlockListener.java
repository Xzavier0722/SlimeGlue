package com.xzavier0722.mc.plugin.slimeglue.listener;

import com.xzavier0722.mc.plugin.slimeglue.SlimeGlue;
import com.xzavier0722.mc.plugin.slimeglue.api.listener.IListener;
import com.xzavier0722.mc.plugin.slimeglue.api.listener.ISlimefunBlockListener;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Location loc = e.getBlock().getLocation();
        SlimefunItem sfItem = BlockStorage.check(loc);
        if (sfItem == null) {
            return;
        }

        String sfId = sfItem.getId();
        for (IListener l : SlimeGlue.moduleManager().getListeners(sfId)) {
            if (l instanceof ISlimefunBlockListener) {
                if (!((ISlimefunBlockListener) l).onBreakEvent(sfId, e.getPlayer(), loc)) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void afterBlockBreak(BlockBreakEvent e) {
        Location loc = e.getBlock().getLocation();
        SlimefunItem sfItem = BlockStorage.check(loc);
        if (sfItem == null) {
            return;
        }

        String sfId = sfItem.getId();
        for (IListener l : SlimeGlue.moduleManager().getListeners(sfId)) {
            if (l instanceof ISlimefunBlockListener) {
                ((ISlimefunBlockListener) l).afterBreakEvent(sfId, e.getPlayer(), loc, e.isCancelled());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        SlimefunItem sfItem = SlimefunItem.getByItem(e.getItemInHand());
        if (sfItem == null || sfItem instanceof NotPlaceable) {
            return;
        }

        String sfId = sfItem.getId();
        for (IListener l : SlimeGlue.moduleManager().getListeners(sfId)) {
            if (l instanceof ISlimefunBlockListener) {
                if (!((ISlimefunBlockListener) l).onPlaceEvent(sfId, e.getPlayer(), e.getBlock().getLocation())) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void afterBlockPlace(BlockPlaceEvent e) {
        SlimefunItem sfItem = SlimefunItem.getByItem(e.getItemInHand());
        if (sfItem == null || sfItem instanceof NotPlaceable) {
            return;
        }

        String sfId = sfItem.getId();
        for (IListener l : SlimeGlue.moduleManager().getListeners(sfId)) {
            if (l instanceof ISlimefunBlockListener) {
                ((ISlimefunBlockListener) l).afterPlaceEvent(sfId, e.getPlayer(), e.getBlock().getLocation(), e.isCancelled());
            }
        }
    }

}
