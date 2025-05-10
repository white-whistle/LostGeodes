package com.bajookie.lost_geodes.item.reward;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Random;

public interface IRaidReward {

    Random r = new Random();

    record RaidRewardDropContext(World world,PlayerEntity player,
                                 int level) {
    }
}
