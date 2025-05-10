package com.bajookie.lost_geodes.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.function.BiConsumer;

public interface ILeftClickAbility {
    public void performLeftClickAbility(ItemStack stack,World world,PlayerEntity user);
}
