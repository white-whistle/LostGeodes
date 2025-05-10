package com.bajookie.lost_geodes.util;

import com.bajookie.lost_geodes.item.ICooldownReduction;
import com.bajookie.lost_geodes.item.IHasCooldown;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.HashMap;

public class CooldownUtil {
    public static float getCooldownReduction(PlayerEntity player) {
        var inv = player.getInventory();
        HashMap<String, Float> cooldowns = new HashMap<>();

        InventoryUtil.forEach(inv, stack -> {
            var i = stack.getItem();

            if (i instanceof ICooldownReduction iCooldownReduction) {
                var key = iCooldownReduction.cooldownInstanceId(stack);
                var v = iCooldownReduction.getCooldownReductionPercentage(stack);

                if (cooldowns.containsKey(key)) {
                    var oldV = cooldowns.get(key);
                    cooldowns.put(key, Math.max(oldV, v));
                } else {
                    cooldowns.put(key, v);
                }
            }
        });

        return cooldowns.values().stream().reduce(0f, Float::sum);
    }

    public static int getReducedCooldown(PlayerEntity player, Item item, int duration) {

        var minCd = 1;
        var affectCooldown = true;

        if (item instanceof IHasCooldown iHasCooldown) {
            minCd = iHasCooldown.getMinCooldown();
            affectCooldown = iHasCooldown.canReduceCooldown();
        }

        if (!affectCooldown) return duration;

        var cdr = CooldownUtil.getCooldownReduction(player);

        return Math.max(minCd, Math.round(duration * (1 - cdr)));
    }
}
