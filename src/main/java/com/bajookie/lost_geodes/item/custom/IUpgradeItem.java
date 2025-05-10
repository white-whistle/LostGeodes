package com.bajookie.lost_geodes.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

public interface IUpgradeItem {

    enum ClickResult {
        // upgrade successful
        SUCCESS,
        // failed to upgrade
        FAILURE,
        // forward to next handler
        FORWARD,
        // no click interaction
        PASS,
    }

    enum EmptyStackPolicy {
        FORWARD,
        INTERACT
    }

    void onUpgrade(PlayerEntity user, ItemStack self, ItemStack other, StackReference cursor);

    ClickResult canUpgrade(PlayerEntity user, ItemStack self, ItemStack other);

    default EmptyStackPolicy getEmptyStackPolicy() {
        return EmptyStackPolicy.FORWARD;
    }

    static ClickResult handleClick(ItemStack cursorStack, ItemStack other, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        var item = cursorStack.getItem();

        if (item instanceof IUpgradeItem iUpgradeItem) {

            if (clickType != ClickType.RIGHT) {
                return (ClickResult.FORWARD);
            }

            if (other.isEmpty() && iUpgradeItem.getEmptyStackPolicy() == EmptyStackPolicy.FORWARD) {
                return (ClickResult.FORWARD);
            }

            var canUpgrade = iUpgradeItem.canUpgrade(player, cursorStack, other);

            if (canUpgrade == ClickResult.SUCCESS) {
                iUpgradeItem.onUpgrade(player, cursorStack, other, cursorStackReference);
            }

            return canUpgrade;
        }

        return ClickResult.FORWARD;
    }
}
