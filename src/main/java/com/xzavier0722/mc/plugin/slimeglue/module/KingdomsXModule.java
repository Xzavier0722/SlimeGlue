package com.xzavier0722.mc.plugin.slimeglue.module;

import com.xzavier0722.mc.plugin.slimeglue.api.ACompatibilityModule;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IBlockProtectionHandler;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.kingdoms.constants.group.Kingdom;
import org.kingdoms.constants.player.KingdomPlayer;

public class KingdomsXModule extends ACompatibilityModule {

    public KingdomsXModule() {
        addProtectionHandler(new IBlockProtectionHandler() {
            @Override
            public boolean canPlaceBlock(OfflinePlayer player, Location location) {
                return isClaimed(player, location);
            }

            @Override
            public boolean canBreakBlock(OfflinePlayer player, Location location) {
                return isClaimed(player, location);
            }

            @Override
            public boolean canInteractBlock(OfflinePlayer player, Location location) {
                return isClaimed(player, location);
            }

            private boolean isClaimed(OfflinePlayer p, Location l) {
                Kingdom kingdom = KingdomPlayer.getKingdomPlayer(p).getKingdom();
                if (kingdom == null) {
                    return true;
                }
                return kingdom.isClaimed(l);
            }
        });
    }

    @Override
    public String getCompatibilityPluginName() {
        return "Kingdoms";
    }

    @Override
    public void enable(Plugin plugin) {

    }

    @Override
    public void disable() {

    }
}
