package com.bajookie.lost_geodes.item;

import net.minecraft.item.ItemStack;

public interface ICooldownReduction {
    float getCooldownReductionPercentage(ItemStack stack);

    String cooldownInstanceId(ItemStack stack);
}
