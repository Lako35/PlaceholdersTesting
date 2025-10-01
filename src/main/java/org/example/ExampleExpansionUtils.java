package org.example;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Set;
import java.util.UUID;

public class ExampleExpansionUtils {

    /**
     * Checks whether the entity matches the targetType filter.
     */
    public static boolean isValidTargetType(Entity e, String targetType, Set<?> HOSTILE_TYPES, String uid) {
        try {
            if( e.getUniqueId().equals(UUID.fromString(uid))) return false;
        } catch (Exception ignored) {}
        if (targetType.equalsIgnoreCase("Players")) {
            return e instanceof Player;
        }
        if (targetType.equalsIgnoreCase("Hostiles")) {
            return HOSTILE_TYPES.contains(e.getType());
        }
        // Both
        return e instanceof LivingEntity;
    }

    /**
     * Checks if any scoreboard tag of the entity matches any tag in turretTags.
     */
    public static boolean hasMatchingTag(Entity e, Set<String> turretTags) {
        for (String tag : e.getScoreboardTags()) {
            if (turretTags.contains(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Simple line-of-sight check between two locations.
     */
    public static boolean canSee(Location from, Location to) {
        return from.getWorld().rayTraceBlocks(from, to.toVector().subtract(from.toVector()).normalize(), from.distance(to)) == null;
    }
}
