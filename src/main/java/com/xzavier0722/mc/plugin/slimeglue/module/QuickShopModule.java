package com.xzavier0722.mc.plugin.slimeglue.module;

import com.xzavier0722.mc.plugin.slimeglue.SlimeGlue;
import com.xzavier0722.mc.plugin.slimeglue.api.ACompatibilityModule;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IBlockProtectionHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.maxgamer.quickshop.api.QuickShopAPI;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;

public class QuickShopModule extends ACompatibilityModule {
    private static Object shopAPI = null;
    private static Method qsMethod = null;

    public QuickShopModule() {
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
                return getQuickshopOwner(location) == player.getUniqueId();
            }
        });
    }

    private UUID getQuickshopOwner(@Nonnull Location l) {
        var qsPlugin = Bukkit.getPluginManager().getPlugin("QuickShop");

        if (qsPlugin == null) {
            return null;
        }

        if (qsMethod != null) {
            // no support for old version
            return null;
        }

        if (qsPlugin instanceof QuickShopAPI qsAPI) {
            return qsAPI.getShopManager().getShop(l).getOwner();
        } else {
            SlimeGlue.logger().w("检查 QuickShop 商店失败，请避免使用热重载更换插件版本。如频繁出现该报错请反馈。");
            return null;
        }
    }

    private boolean isQuickshop(@Nonnull Location l) {
        var qsPlugin = Bukkit.getPluginManager().getPlugin("QuickShop");

        if (qsPlugin == null) {
            return false;
        }

        if (qsMethod != null) {
            try {
                if (shopAPI == null) {
                    return false;
                }

                var result = qsMethod.invoke(shopAPI, l);

                if (result instanceof Optional optional) {
                    return optional.isPresent();
                } else {
                    return false;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                SlimeGlue.logger().w("在获取箱子商店时出现了问题");
                e.printStackTrace();
                return false;
            }
        }

        if (qsPlugin instanceof QuickShopAPI qsAPI) {
            return qsAPI.getShopManager().getShop(l) != null;
        } else {
            SlimeGlue.logger().w("检查 QuickShop 商店失败，请避免使用热重载更换插件版本。如频繁出现该报错请反馈。");
            return false;
        }
    }

    @Override
    public String getCompatibilityPluginName() {
        return "Quickshop";
    }

    @Override
    public void enable(Plugin plugin) throws Exception {
        var version = plugin.getDescription().getVersion();
        var splitVersion = version.split("-")[0].split("\\.");

        try {
            var major = Integer.parseInt(splitVersion[0]);
            var sub = Integer.parseInt(splitVersion[2]);
            var last = Integer.parseInt(splitVersion[3]);

            if (major < 5) {
                SlimeGlue.logger().w("QuickShop 版本过低, 建议你更新到 5.0.0+!");

                try {
                    var shopAPIMethod = Class.forName("org.maxgamer.quickshop.QuickShopAPI").getDeclaredMethod("getShopAPI");
                    shopAPIMethod.setAccessible(true);
                    shopAPI = shopAPIMethod.invoke(null);
                } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                         InvocationTargetException e) {
                    SlimeGlue.logger().w("无法接入 Quickshop-Reremake " + version + " , 请更新到最新版, 相关功能将自动关闭");
                    throw e;
                }

                if (sub >= 8 && last >= 2) {
                    // For 5.0.0-
                    try {
                        qsMethod = Class.forName("org.maxgamer.quickshop.api.ShopAPI").getDeclaredMethod("getShop", Location.class);
                        qsMethod.setAccessible(true);
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        SlimeGlue.logger().w("无法接入 Quickshop-Reremake " + version + " , 请更新到最新版, 相关功能将自动关闭");
                        throw e;
                    }
                } else {
                    // For 4.0.8-
                    try {
                        qsMethod = Class.forName("org.maxgamer.quickshop.api.ShopAPI").getDeclaredMethod("getShopWithCaching", Location.class);
                        qsMethod.setAccessible(true);
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        SlimeGlue.logger().w("无法接入 Quickshop-Reremake " + version + " , 请更新到最新版, 相关功能将自动关闭");
                        throw e;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            SlimeGlue.logger().w("解析 Quickshop-Reremake 版本失败, 实际为 " + version + ".");
            throw e;
        }
    }

    @Override
    public void disable() {
        qsMethod = null;
        shopAPI = null;
    }
}
