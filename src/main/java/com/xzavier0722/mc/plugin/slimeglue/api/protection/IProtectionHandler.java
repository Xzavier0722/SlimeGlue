package com.xzavier0722.mc.plugin.slimeglue.api.protection;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public interface IProtectionHandler {

    default boolean bypassCheck(OfflinePlayer player, Location location) {
        return false;
    }

}
