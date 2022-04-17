package com.xzavier0722.mc.plugin.slimeglue.slimefun;

import com.xzavier0722.mc.plugin.slimeglue.SlimeGlue;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IBlockProtectionHandler;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IPlayerProtectionHandler;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IProtectionHandler;
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
                for (IProtectionHandler each : SlimeGlue.moduleManager().getProtectionHandlers()) {
                    if (each.bypassCheck(p, l)) {
                        continue;
                    }
                    if (each instanceof IBlockProtectionHandler) {
                        if (!((IBlockProtectionHandler) each).canBreakBlock(p, l)) {
                            return false;
                        }
                    }
                }
                break;
            case PLACE_BLOCK:
                for (IProtectionHandler each : SlimeGlue.moduleManager().getProtectionHandlers()) {
                    if (each.bypassCheck(p, l)) {
                        continue;
                    }
                    if (each instanceof IBlockProtectionHandler) {
                        if (!((IBlockProtectionHandler) each).canPlaceBlock(p, l)) {
                            return false;
                        }
                    }
                }
                break;
            case ATTACK_ENTITY:
                for (IProtectionHandler each : SlimeGlue.moduleManager().getProtectionHandlers()) {
                    if (each.bypassCheck(p, l)) {
                        continue;
                    }
                    if (each instanceof IPlayerProtectionHandler) {
                        if (!((IPlayerProtectionHandler) each).canAttackEntity(p, l)) {
                            return false;
                        }
                    }
                }
                break;
            case ATTACK_PLAYER:
                for (IProtectionHandler each : SlimeGlue.moduleManager().getProtectionHandlers()) {
                    if (each.bypassCheck(p, l)) {
                        continue;
                    }
                    if (each instanceof IPlayerProtectionHandler) {
                        if (!((IPlayerProtectionHandler) each).canAttackPlayer(p, l)) {
                            return false;
                        }
                    }
                }
                break;
            case INTERACT_BLOCK:
                for (IProtectionHandler each : SlimeGlue.moduleManager().getProtectionHandlers()) {
                    if (each.bypassCheck(p, l)) {
                        continue;
                    }
                    if (each instanceof IBlockProtectionHandler) {
                        if (!((IBlockProtectionHandler) each).canInteractBlock(p, l)) {
                            return false;
                        }
                    }
                }
                break;
            case INTERACT_ENTITY:
                for (IProtectionHandler each : SlimeGlue.moduleManager().getProtectionHandlers()) {
                    if (each.bypassCheck(p, l)) {
                        continue;
                    }
                    if (each instanceof IPlayerProtectionHandler) {
                        if (!((IPlayerProtectionHandler) each).canInteractEntity(p, l)) {
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
