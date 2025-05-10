package com.bajookie.lost_geodes.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;

public interface IEmptyClick {
    boolean onEmptyClick(PlayerEntity user, ItemStack self, StackReference cursor);
}
