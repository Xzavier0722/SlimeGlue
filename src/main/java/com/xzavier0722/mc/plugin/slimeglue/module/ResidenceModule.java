package com.xzavier0722.mc.plugin.slimeglue.module;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.xzavier0722.mc.plugin.slimeglue.SlimeGlue;
import com.xzavier0722.mc.plugin.slimeglue.api.ACompatibilityModule;
import com.xzavier0722.mc.plugin.slimeglue.api.protection.IBlockProtectionHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ResidenceModule extends ACompatibilityModule {
    public ResidenceModule() {
        addProtectionHandler(new IBlockProtectionHandler() {
            @Override
            public boolean canPlaceBlock(OfflinePlayer player, Location location) {
                return checkResidence(player, location, Interaction.PLACE_BLOCK);
            }

            @Override
            public boolean canBreakBlock(OfflinePlayer player, Location location) {
                return checkResidence(player, location, Interaction.BREAK_BLOCK);
            }

            @Override
            public boolean canInteractBlock(OfflinePlayer player, Location location) {
                return checkResidence(player, location, Interaction.INTERACT_BLOCK);
            }

            @Override
            public boolean bypassCheck(OfflinePlayer player, Location location) {
                return canBypassCheck(player, location);
            }
        });
    }

    @Override
    public String getCompatibilityPluginName() {
        return "Residence";
    }

    @Override
    public void enable(Plugin plugin) {
    }

    @Override
    public void disable() {
    }

    private boolean checkResidence(@Nonnull OfflinePlayer p, @Nonnull Location location, Interaction action) {
        if (!p.isOnline()) {
            return false;
        }

        var onlinePlayer = p.getPlayer();

        if (onlinePlayer == null) {
            return false;
        }

        var perms = Residence.getInstance().getPermsByLocForPlayer(location, onlinePlayer);

        if (perms != null) {
            if (Bukkit.isPrimaryThread()) {
                return checkPerm(perms, onlinePlayer, action);
            }
            try {
                return Bukkit.getScheduler().callSyncMethod(SlimeGlue.instance(), () -> checkPerm(
                        perms,
                        onlinePlayer,
                        action)
                ).get(15, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                SlimeGlue.logger().w("A error occurred during query residence flag, skipped check");
                e.printStackTrace();
                return true;
            }
        }

        return true;
    }

    private boolean canBypassCheck(@Nonnull OfflinePlayer p, @Nonnull Location location) {
        if (p.isOp()) {
            return true;
        }

        var res = Residence.getInstance().getResidenceManager().getByLoc(location);

        if (res == null) {
            return true;
        }

        if (res.getOwnerUUID() == p.getUniqueId()) {
            return true;
        }

        var onlinePlayer = p.getPlayer();
        if (onlinePlayer == null) {
            return false;
        }

        if (onlinePlayer.hasPermission("residence.admin")) {
            return true;
        }

        var perms = res.getPermissions();
        if (perms == null) {
            return true;
        }

        if (Bukkit.isPrimaryThread()) {
            return perms.playerHas(onlinePlayer, Flags.admin, FlagPermissions.FlagCombo.OnlyTrue);
        }

        try {
            return Bukkit.getScheduler().callSyncMethod(SlimeGlue.instance(), () -> perms.playerHas(
                    onlinePlayer,
                    Flags.admin,
                    FlagPermissions.FlagCombo.OnlyTrue
            )).get();
        } catch (ExecutionException | InterruptedException e) {
            SlimeGlue.logger().w("A error occurred during query residence flag, skipped check");
            e.printStackTrace();
            return true;
        }
    }

    private boolean checkPerm(FlagPermissions perms, Player p, Interaction action) {
        switch (action) {
            case BREAK_BLOCK -> {
                return perms.playerHas(p, Flags.destroy, FlagPermissions.FlagCombo.OnlyTrue)
                        || perms.playerHas(p, Flags.build, FlagPermissions.FlagCombo.OnlyTrue);
            }
            case INTERACT_BLOCK -> {
                return perms.playerHas(p, Flags.container, FlagPermissions.FlagCombo.OnlyTrue);
            }
            case PLACE_BLOCK -> {
                // move 是为了机器人而检查的, 防止机器人跑进别人领地然后还出不来
                return perms.playerHas(p, Flags.place, FlagPermissions.FlagCombo.OnlyTrue)
                        || perms.playerHas(p, Flags.build, FlagPermissions.FlagCombo.OnlyTrue)
                        && perms.playerHas(p, Flags.move, FlagPermissions.FlagCombo.TrueOrNone);
            }
            default -> {
                return true;
            }
        }
    }
}
