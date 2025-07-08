package org.example;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class WG {
    static @NotNull String wg2(ExampleExpansion exampleExpansion, Player f2, String f1) {
        if (!exampleExpansion.f1(f2, "worldguard")) return "§cInstall worldguard...";
        try {
            String[] parts = f1.substring("countPlayersInRegion_".length()).split(",");
            if (parts.length != 2) return "§cError";

            String regionName = parts[0];
            String worldName = parts[1];

            World world = Bukkit.getWorld(worldName);
            if (world == null) return "-1";

            RegionManager manager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
            if (manager == null || !manager.hasRegion(regionName)) return "-1";

            ProtectedRegion region = manager.getRegion(regionName);
            if (region == null) return "-1";

            int count = 0;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getWorld().getName().equals(worldName)) continue;

                Location loc = player.getLocation();
                if (region.contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())) {
                    count++;
                }
            }

            return String.valueOf(count);
        } catch (Exception e) {
            return "§cError";
        }
    }

    static @Nullable String wg(ExampleExpansion exampleExpansion, Player f2, String f1) {
        if (!exampleExpansion.f1(f2, "WorldGuard")) return null;

        String params = f1.substring("nearestPlayerNotTeam2_".length());
        String[] parts = params.split(",");

        if (parts.length != 6) {
            return "?";
        }

        String teamName = parts[0];
        int radius;
        String worldName = parts[2];
        double x, y, z;

        try {
            radius = Integer.parseInt(parts[1]);
            x = Double.parseDouble(parts[3]);
            y = Double.parseDouble(parts[4]);
            z = Double.parseDouble(parts[5]);
        } catch (NumberFormatException e) {
            return "?";
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return "?";
        }

        Location origin = new Location(world, x, y, z);

        // Get the specified team from the scoreboard
        Team team = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard().getTeam(teamName);
        if (team == null) {
            return "?";
        }

        Player nearestPlayer = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Player target : world.getPlayers()) {
            // Skip players on the specified team
            if (team.hasEntry(target.getName())) {
                continue;
            }

            // Check if player is inside a region that includes "criminalbase" in its ID
            Location loc = target.getLocation();
            com.sk89q.worldedit.util.Location wgLoc = BukkitAdapter.adapt(loc);
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager regions = container.get(BukkitAdapter.adapt(world));

            if (regions == null) {
                continue;
            }

            ApplicableRegionSet set = regions.getApplicableRegions(wgLoc.toVector().toBlockPoint());
            boolean inCriminalBase = set.getRegions().stream()
                    .anyMatch(r -> r.getId().toLowerCase().contains("criminalbase"));

            if (!inCriminalBase) {
                continue;
            }

            // Calculate distance
            double distance = origin.distance(loc);
            if (distance <= radius && distance < nearestDistance) {
                nearestDistance = distance;
                nearestPlayer = target;
            }
        }

        if (nearestPlayer != null) {
            return nearestPlayer.getUniqueId().toString();
        }

        return "?";
    }
}
