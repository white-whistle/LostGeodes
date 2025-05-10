package com.bajookie.lost_geodes.mixin;

import com.bajookie.lost_geodes.item.ModGenericTooltip;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ClientItemMixin {

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    public void appendGenericTooltips(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        var mc = MinecraftClient.getInstance();
        if (mc.world == null) return;

        var player = mc.player;
        if (player == null) return;

        var isShifting = Screen.hasShiftDown();

        ModGenericTooltip.appendGenericTooltips(stack, world, tooltip, context, isShifting);
    }
}
