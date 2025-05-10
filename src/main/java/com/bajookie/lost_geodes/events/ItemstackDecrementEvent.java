package com.bajookie.lost_geodes.events;

import net.minecraft.item.ItemStack;

import java.util.function.Consumer;

public interface ItemstackDecrementEvent {
    interface ModItemstackExtension {
        void echoesOfTheElders$setDecrementHandler(Consumer<ItemStack> consumer);
    }

}
