package com.xzavier0722.mc.plugin.slimeglue.api.protection;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public interface IPlayerProtectionHandler extends IProtectionHandler {

    default boolean canAttackPlayer(OfflinePlayer player, Location location) {
        return true;
    }

    default boolean canAttackEntity(OfflinePlayer player, Location location) {
        return true;
    }

    default boolean canInteractEntity(OfflinePlayer player, Location location) {
        return true;
    }

}
