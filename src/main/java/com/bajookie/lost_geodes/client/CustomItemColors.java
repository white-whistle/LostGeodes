package com.bajookie.lost_geodes.client;

import com.bajookie.lost_geodes.client.animation.AnimationUtil;
import com.bajookie.lost_geodes.item.ModItems;
import com.bajookie.lost_geodes.util.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class CustomItemColors {

    public static Color rainbow(float l, float s) {
        var mc = MinecraftClient.getInstance();

        var progress = AnimationUtil.HUE_SHIFT_ANIMATION.getProgress(mc.getTickDelta());

        return Color.fromHSL(360 * progress, l, s);
    }

    public static Color rainbow() {
        return rainbow(0.5f, 0.7f);
    }

    public static Color flaming() {
        var mc = MinecraftClient.getInstance();

        var progress = AnimationUtil.HUE_SHIFT_ANIMATION.getProgress(mc.getTickDelta());

        return Color.fromHSL(35, AnimationUtil.tween(0.9f, 1f, progress), AnimationUtil.tween(0.6f, 0.56f, progress));
    }

    public static void init() {
        ColorProviderRegistry.ITEM.register((stack, index) -> {
            if (index == 0) return 0xFFFFFF;

            return rainbow().getRGB();
        }, ModItems.REALITY_PICK);

        ColorProviderRegistry.ITEM.register((stack, index) -> {
            if (index == 0) return 0xFFFFFF;

            if (stack.getCount() == stack.getMaxCount()) {
                return 0x23D370;
            }

            return 0x6DD307;
        }, ModItems.TIME_GLYPH);

        ColorProviderRegistry.ITEM.register((stack, index) -> {
            if (index == 0) return 0xFFFFFF;

            return rainbow().getRGB();
        }, ModItems.WTF_TOKEN);
    }
}
