package com.bajookie.lost_geodes.item.custom;


import com.bajookie.lost_geodes.item.ability.Ability;
import com.bajookie.lost_geodes.screen.client.particles.ScreenParticleManager;
import com.bajookie.lost_geodes.screen.client.particles.imps.StarParticle;
import com.bajookie.lost_geodes.system.ItemStack.StackLevel;
import com.bajookie.lost_geodes.system.Text.TooltipSection;
import com.bajookie.lost_geodes.util.Interator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;

import java.util.List;

public interface IArtifact {

    List<Tag> NO_TAGS = List.of();
    List<Ability> NO_ABILITIES = List.of();
    List<TooltipSection> NO_INFO = List.of();

    enum Tag {
        THROWN,
        CHARGED,

        WEAPON,
        GUN,
        SWORD,

        GEAR,

        TOOL,
        CATALYST,

        ODDITY,
        UNIQUE,

        SUPPORT,


        FIRE,
        ICE,
        LIGHTNING,
        DIVINE,
        VOID,
        CONSTRUCT,
    }

    default List<Tag> getTags() {
        return NO_TAGS;
    }

    default int getArtifactMaxStack() {
        return 16;
    }

    default boolean canArtifactMerge() {
        return true;
    }

    default boolean onArtifactClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType != ClickType.RIGHT) return false;
        if (!canArtifactMerge()) return false;


        if (stack.isOf(otherStack.getItem())) {
            var mainStack = StackLevel.get(stack) > StackLevel.get(otherStack) ? stack : otherStack;
            var secondaryStack = mainStack == stack ? otherStack : stack;

            if (!StackLevel.isMaxed(mainStack)) {
                StackLevel.raise(mainStack, 1);
                StackLevel.decrement(secondaryStack, 1);
                player.playSound(SoundEvents.BLOCK_SMITHING_TABLE_USE, 0.8f, 0.8f + player.getWorld().getRandom().nextFloat() * 0.4f);

                Interator.of(20).forEach(i -> {
                    ScreenParticleManager.addParticle(
                            new StarParticle(ScreenParticleManager.getMousePos())
                                    .randomizeVelocity(5f)
                    );
                });

                return true;
            }
        }

        return false;
    }

    default List<Ability> getAbilities(ItemStack itemStack) {
        return NO_ABILITIES;
    }

    default List<TooltipSection> getAdditionalInfo(ItemStack itemStack) {
        return NO_INFO;
    }
}
