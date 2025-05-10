package com.bajookie.echoes_of_the_elders.item.reward;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Random;

public interface IRaidReward {

    Random r = new Random();

    record RaidRewardDropContext(World world,PlayerEntity player,
                                 int level) {
    }
}
