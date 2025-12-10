package org.example;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public class ExampleExpansionUtils {



    // Helper: safe UUID parse
    private static UUID safeUuid(String s) {
        if (s == null || s.isBlank()) return null;
        try { return UUID.fromString(s.trim()); } catch (IllegalArgumentException ignored) { return null; }
    }

    
    
    /**
     * Target filter semantics (consistent with your outer loop that already restricts to LivingEntity):
     * - "Players"            => players only
     * - "Entities"           => all living non-player entities (passives, hostiles, etc.), NOT players
     * - "Hostiles"           => only entities whose EntityType is in HOSTILE_TYPES
     * - "Players+Hostiles"   => players OR hostiles (aka "Both")
     * - "All"                => all living entities (players + every living non-player)
     *
     * If ownerUUID parses to a valid UUID, that exact UUID is NEVER targeted.
     */
    public static boolean iearnsvoienrositwf(Entity e,
                                             String targetType,
                                             Set<EntityType> HOSTILE_TYPES,
                                             String ownerUUID) {
        // Absolute: never target the owner if UUID is valid
        UUID owner = safeUuid(ownerUUID);
        if (owner != null && e.getUniqueId().equals(owner)) {
            return false;
        }

        // Normalize the mode (accept a few common variants like "players_hostiles", "both")
        String mode = (targetType == null ? "" : targetType)
                .toLowerCase(Locale.ROOT)
                .replace("_", "")
                .replace(" ", "");

        // Map common synonyms
        if (mode.equals("both")) mode = "players+hostiles";

        switch (mode) {
            case "players":
                return e instanceof Player;

            case "entities":
                // all living non-players (your outer loop already filters non-living)
                return (e instanceof LivingEntity) && !(e instanceof Player);

            case "hostiles":
                return HOSTILE_TYPES.contains(e.getType());

            case "players+hostiles":
            case "playershostiles":
                return (e instanceof Player) || HOSTILE_TYPES.contains(e.getType());

            case "all":
                // all living entities, including players and passives
                return (e instanceof LivingEntity);

            default:
                // Sensible fallback: behave like Players+Hostiles
                return (e instanceof Player) || HOSTILE_TYPES.contains(e.getType());
        }
    }


    /**
     * Checks if any scoreboard tag of the entity matches any tag in turretTags.
     */
    public static boolean ytwunfoyutn4(Entity e, Set<String> turretTags) {
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
    public static boolean wodyuwnfdyuwfn(Location from, Location to) {
        return from.getWorld().rayTraceBlocks(from, to.toVector().subtract(from.toVector()).normalize(), from.distance(to)) == null;
    }
}
