package com.xzavier0722.mc.plugin.slimeglue.slimefun;

import com.xzavier0722.mc.plugin.slimeglue.SlimeGlue;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IBlockProtectionHandler;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IPlayerProtectionHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.ProtectionModule;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public class GlueProtectionModule implements ProtectionModule {

    @Override
    public void load() {

    }

    @Override
    public Plugin getPlugin() {
        return SlimeGlue.instance();
    }

    @Override
    public boolean hasPermission(OfflinePlayer p, Location l, Interaction interaction) {
        switch (interaction) {
            case BREAK_BLOCK:
                for (var each : SlimeGlue.moduleManager().getProtectionHandlers()) {
                    if (each instanceof IBlockProtectionHandler handler) {
                        if (handler.bypassCheck(p, l)) {
                            continue;
                        }
                        if (!handler.canBreakBlock(p, l)) {
                            return false;
                        }
                    }
                }
                break;
            case PLACE_BLOCK:
                for (var each : SlimeGlue.moduleManager().getProtectionHandlers()) {
                    if (each instanceof IBlockProtectionHandler handler) {
                        if (handler.bypassCheck(p, l)) {
                            continue;
                        }
                        if (!handler.canPlaceBlock(p, l)) {
                            return false;
                        }
                    }
                }
                break;
            case ATTACK_ENTITY:
                for (var each : SlimeGlue.moduleManager().getProtectionHandlers()) {
                    if (each instanceof IPlayerProtectionHandler handler) {
                        if (handler.bypassCheck(p, l)) {
                            continue;
                        }
                        if (!handler.canAttackEntity(p, l)) {
                            return false;
                        }
                    }
                }
                break;
            case ATTACK_PLAYER:
                for (var each : SlimeGlue.moduleManager().getProtectionHandlers()) {
                    if (each instanceof IPlayerProtectionHandler handler) {
                        if (handler.bypassCheck(p, l)) {
                            continue;
                        }
                        if (!handler.canAttackPlayer(p, l)) {
                            return false;
                        }
                    }
                }
                break;
            case INTERACT_BLOCK:
                for (var each : SlimeGlue.moduleManager().getProtectionHandlers()) {
                    if (each instanceof IBlockProtectionHandler handler) {
                        if (handler.bypassCheck(p, l)) {
                            continue;
                        }
                        if (!handler.canInteractBlock(p, l)) {
                            return false;
                        }
                    }
                }
                break;
            case INTERACT_ENTITY:
                for (var each : SlimeGlue.moduleManager().getProtectionHandlers()) {
                    if (each instanceof IPlayerProtectionHandler handler) {
                        if (handler.bypassCheck(p, l)) {
                            continue;
                        }
                        if (!handler.canInteractEntity(p, l)) {
                            return false;
                        }
                    }
                }
                break;
            default:
                return true;
        }
        return true;
    }
}
