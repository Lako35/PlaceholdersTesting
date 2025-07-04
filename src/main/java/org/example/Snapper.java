package org.example;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

class Snapper extends BukkitRunnable {
    private final ExampleExpansion exampleExpansion;
    protected final ArmorStand as;
    protected final Player owner;
    protected final String args;
    protected final EulerAngle startPose, targetPose;
    protected final int faceNumber;
    protected int step = 0;

    Snapper(ExampleExpansion exampleExpansion, ArmorStand stand, Player p, String args) {
        this.exampleExpansion = exampleExpansion;
        this.as = stand;
        this.owner = p;
        this.args = args;
        this.startPose = stand.getHeadPose();
        int idx = ExampleExpansion2.pickNearestFaceIndex(startPose);
        this.targetPose = ExampleExpansion.DICE_FACES[idx];
        this.faceNumber = idx + 1;
    }

    @Override
    public void run() {
        if (step < 5) {
            double t = step / 5.0;
            as.setHeadPose(new EulerAngle(
                    ExampleExpansion2.lerp(startPose.getX(), targetPose.getX(), t),
                    ExampleExpansion2.lerp(startPose.getY(), targetPose.getY(), t),
                    ExampleExpansion2.lerp(startPose.getZ(), targetPose.getZ(), t)
            ));
        } else if (step == 5) {
            as.setHeadPose(targetPose);
        } else if (step == 45) {
            as.remove();
            owner.performCommand(args + " " + faceNumber);
            exampleExpansion.activeDice.remove(owner.getUniqueId());
            cancel();
        }
        step++;
    }
}
