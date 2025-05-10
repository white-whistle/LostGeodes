package com.bajookie.lost_geodes.client.tooltip;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;

public record ItemTooltipData(ItemStack stack) implements TooltipData {
}
