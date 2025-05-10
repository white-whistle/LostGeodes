package com.bajookie.lost_geodes.client;

import com.bajookie.lost_geodes.item.ModItems;
import com.bajookie.lost_geodes.util.Color;
import net.minecraft.client.MinecraftClient;

public class CustomOutlineColor {
    public static Color getOutlineColor() {

        var mc = MinecraftClient.getInstance();

        if (mc == null || mc.player == null) return null;

        var stack = mc.player.getMainHandStack();
        var item = stack.getItem();

        if (item == ModItems.REALITY_PICK) {
            return CustomItemColors.rainbow();
        }

        return null;
    }
}
