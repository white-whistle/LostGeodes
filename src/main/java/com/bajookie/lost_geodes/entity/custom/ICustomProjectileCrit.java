package com.bajookie.lost_geodes.entity.custom;

import net.minecraft.world.World;

public interface ICustomProjectileCrit {

    void spawnCritParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ);
}
