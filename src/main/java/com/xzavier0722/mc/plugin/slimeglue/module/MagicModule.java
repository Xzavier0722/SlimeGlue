package com.xzavier0722.mc.plugin.slimeglue.module;

import com.elmakers.mine.bukkit.api.block.BlockData;
import com.xzavier0722.mc.plugin.slimeglue.SlimeGlue;
import com.xzavier0722.mc.plugin.slimeglue.api.ACompatibilityModule;
import com.xzavier0722.mc.plugin.slimeglue.api.listener.ISlimefunAndroidListener;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IBlockProtectionHandler;
import io.github.thebusybiscuit.slimefun4.api.events.AndroidFarmEvent;
import io.github.thebusybiscuit.slimefun4.api.events.AndroidMineEvent;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Objects;

public class MagicModule extends ACompatibilityModule {
    private Method magicBlockDataMethod = null;

    public MagicModule() {
        addProtectionHandler(new IBlockProtectionHandler() {
            @Override
            public boolean canBreakBlock(OfflinePlayer player, Location location) {
                verbose("canBreakBlock: player=" + player.getName() + ", loc=" + location);
                return !isMagicBlock(location);
            }
        });

        addListener(new ISlimefunAndroidListener() {
            @Override
            public void onFarm(AndroidFarmEvent e) {
                var l = e.getBlock().getLocation();
                verbose("onFarm: " + l);
                if (isMagicBlock(l)) {
                    e.setCancelled(true);
                }
            }

            @Override
            public void onMine(AndroidMineEvent e) {
                var l = e.getBlock().getLocation();
                verbose("onFarm: " + l);
                if (isMagicBlock(l)) {
                    e.setCancelled(true);
                }
            }
        });
    }

    @Override
    public String getCompatibilityPluginName() {
        return "Magic";
    }

    @Override
    public void enable(Plugin plugin) throws Exception {
        if (plugin != null && magicBlockDataMethod == null) {
            magicBlockDataMethod = Class.forName("com.elmakers.mine.bukkit.block.UndoList")
                    .getDeclaredMethod("getBlockData", Location.class);

            Objects.requireNonNull(magicBlockDataMethod, "Unable to get method from Magic");

            magicBlockDataMethod.setAccessible(true);
        }
    }

    private boolean isMagicBlock(@Nonnull Location location) {
        try {
            var blockData = magicBlockDataMethod.invoke(null, location);
            verbose("isMagicBlock: " + blockData);
            if (blockData instanceof BlockData) {
                return true;
            }
        } catch (Exception e) {
            SlimeGlue.logger().w("Unable to check magic BlockData");
            e.printStackTrace();
            return true;
        }

        return false;
    }

    @Override
    public void disable() {
        magicBlockDataMethod = null;
    }
}
