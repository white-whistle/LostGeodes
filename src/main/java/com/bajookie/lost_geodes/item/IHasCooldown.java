package com.bajookie.lost_geodes.item;

import com.bajookie.lost_geodes.mixin.ItemCooldownManagerAccessor;
import com.bajookie.lost_geodes.mixin.ItemCooldownManagerEntryAccessor;
import com.bajookie.lost_geodes.system.Text.TextArgs;
import com.bajookie.lost_geodes.system.Text.TextUtil;
import com.bajookie.lost_geodes.util.CooldownUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface IHasCooldown {
    int getCooldown(ItemStack itemStack);

    default int getMinCooldown() {
        return 1;
    }

    default boolean canReduceCooldown() {
        return true;
    }

    @Nullable
    static private MutableText getCooldownMessageBase(ItemStack stack) {
        var item = stack.getItem();
        if (!(item instanceof IHasCooldown iHasCooldown)) return null;

        var cd = iHasCooldown.getCooldown(stack);

        return TextUtil.translatable("tooltip.lost_geodes.cooldown", new TextArgs().put("cd", TextUtil.formatTime(cd).styled(s -> s.withColor(Formatting.BLUE))));
    }

    @Environment(EnvType.CLIENT)
    @Nullable
    static private MutableText getClientCooldownMessage(ItemStack stack) {
        var item = stack.getItem();
        if (!(item instanceof IHasCooldown iHasCooldown)) return null;

        var mc = MinecraftClient.getInstance();
        if (mc.world == null) return null;

        var player = mc.player;
        if (player == null) return null;


        var cd = CooldownUtil.getReducedCooldown(player, stack.getItem(), iHasCooldown.getCooldown(stack));
        var cdm = player.getItemCooldownManager();

        if (cdm.isCoolingDown(item) && cdm instanceof ItemCooldownManagerAccessor a) {
            var cdEntry = a.getEntries().get(item);
            if (cdEntry instanceof ItemCooldownManagerEntryAccessor e) {
                var totalTicks = e.getEndTick() - e.getStartTick();
                var remainingTime = (int) (cdm.getCooldownProgress(item, mc.getTickDelta()) * totalTicks);

                return TextUtil.translatable("tooltip.lost_geodes.cooldown.active", new TextArgs().put("cd", TextUtil.formatTime(cd).styled(s -> s.withColor(Formatting.BLUE))).put("remaining", TextUtil.formatTime(remainingTime)));

            }
        } else {
            return TextUtil.translatable("tooltip.lost_geodes.cooldown", new TextArgs().put("cd", TextUtil.formatTime(cd).styled(s -> s.withColor(Formatting.BLUE))));
        }

        return null;
    }

    @Nullable
    static MutableText getCooldownMessage(ItemStack stack, @Nullable World world) {
        var isServer = world == null || !world.isClient;

        if (isServer) {
            return getCooldownMessageBase(stack);
        }

        return getClientCooldownMessage(stack);
    }
}
