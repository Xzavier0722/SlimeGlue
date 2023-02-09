package com.xzavier0722.mc.plugin.slimeglue.api.protection;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public interface IBlockProtectionHandler extends IProtectionHandler {

    default boolean canPlaceBlock(OfflinePlayer player, Location location) {
        return true;
    }

    default boolean canBreakBlock(OfflinePlayer player, Location location) {
        return true;
    }

    default boolean canInteractBlock(OfflinePlayer player, Location location) {
        return true;
    }

    default boolean canCargoAccessBlock(Location location) {
        return true;
    }

}
