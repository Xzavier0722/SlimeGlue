package com.xzavier0722.mc.plugin.slimeglue.module;

import com.xzavier0722.mc.plugin.slimeglue.api.ACompatibilityModule;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IBlockProtectionHandler;
import me.crafter.mc.lockettepro.LocketteProAPI;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public class LocketteProModule extends ACompatibilityModule {

    public LocketteProModule() {
        addProtectionHandler(new IBlockProtectionHandler() {
            @Override
            public boolean canCargoAccessBlock(Location location) {
                return verbose("canCargoAccessBlock: ", !LocketteProAPI.isProtected(location.getBlock()));
            }

            @Override
            public boolean canBreakBlock(OfflinePlayer player, Location location) {
                verbose("canBreakBlock: p=" + player.getName() + ", l=" + location);
                return false;
            }

            @Override
            public boolean canInteractBlock(OfflinePlayer player, Location location) {
                verbose("canInteractBlock: p=" + player.getName() + ", l=" + location);
                var p = player.getPlayer();
                return verbose("canInteractBlock: ", p != null && LocketteProAPI.isUser(location.getBlock(), p));
            }

            @Override
            public boolean bypassCheck(OfflinePlayer player, Location location) {
                var b = location.getBlock();
                if (player.isOp()) {
                    return true;
                }

                if (!LocketteProAPI.isProtected(b)) {
                    return true;
                }

                var p = player.getPlayer();
                return p != null && LocketteProAPI.isOwner(b, p);
            }
        });
    }

    @Override
    public String getCompatibilityPluginName() {
        return "LockettePro";
    }

    @Override
    public void enable(Plugin plugin) throws Exception {

    }

    @Override
    public void disable() {

    }
}
