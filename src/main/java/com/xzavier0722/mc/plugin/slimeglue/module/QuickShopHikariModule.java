package com.xzavier0722.mc.plugin.slimeglue.module;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;
import com.xzavier0722.mc.plugin.slimeglue.api.ACompatibilityModule;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IBlockProtectionHandler;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.UUID;

public class QuickShopHikariModule extends ACompatibilityModule {

    public QuickShopHikariModule() {
        addProtectionHandler(new IBlockProtectionHandler() {
            @Override
            public boolean canBreakBlock(OfflinePlayer player, Location location) {
                return !isQuickshop(location);
            }

            @Override
            public boolean canInteractBlock(OfflinePlayer player, Location location) {
                return !isQuickshop(location);
            }

            @Override
            public boolean bypassCheck(OfflinePlayer player, Location location) {
                return player.getUniqueId().equals(getQuickshopOwner(location));
            }

            @Override
            public boolean canCargoAccessBlock(Location location) {
                return !isQuickshop(location);
            }
        });
    }

    private UUID getQuickshopOwner(@Nonnull Location l) {
        Shop shop = QuickShopAPI.getInstance().getShopManager().getShop(l);
        if (shop != null) {
            return shop.getOwner().getUniqueId();
        }
        return null;

    }

    private boolean isQuickshop(@Nonnull Location l) {
        return QuickShopAPI.getInstance().getShopManager().getShop(l) != null;

    }

    @Override
    public String getCompatibilityPluginName() {
        return "QuickShop-Hikari";
    }

    @Override
    public void enable(Plugin plugin) throws Exception {

    }

    @Override
    public void disable() {

    }
}
