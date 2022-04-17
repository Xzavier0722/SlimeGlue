package com.xzavier0722.mc.plugin.slimeglue.api.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface ISlimefunBlockListener extends IListener{
    /**
     * 
     * Fired when a slimefun block is placing, the cancelled event will be ignored.
     * 
     * @param sfId: the placing slimefun item id
     * @param player: the player who placed this block
     * @param location: the location of this placing
     * @return if allowed placing
     */
    default boolean onPlaceEvent(String sfId, Player player, Location location) {
        return true;
    }

    /**
     * 
     * Fired when a slimefun block is breaking, the cancelled event will be ignored.
     * 
     * @param sfId: the breaking slimefun item id
     * @param player: the player who broke this block
     * @param location: the location of this breaking
     * @return if allowed breaking
     */
    default boolean onBreakEvent(String sfId, Player player, Location location) {
        return true;
    }

    /**
     * 
     * Fired after place event, you can monitor the result of the event.
     * 
     * @see #onPlaceEvent 
     * @param isCancelled: if the event cancelled
     */
    default void afterPlaceEvent(String sfId, Player player, Location location, boolean isCancelled) { }

    /**
     * 
     * Fired after break event, you can monitor the result of the event.
     * 
     * @see #onBreakEvent
     * @param isCancelled: fi the event cancelled
     */
    default void afterBreakEvent(String sfId, Player player, Location location, boolean isCancelled) { }

}
