package com.bajookie.lost_geodes.system.ItemStack;

import com.bajookie.lost_geodes.util.ModIdentifier;

public class CustomItemNbt {

    public static CustomItemNbtImpl.Int STACK_LEVEL = new CustomItemNbtImpl.Int(ModIdentifier.string("stack_level"), 1);
    public static CustomItemNbtImpl.Boolean EFFECT_ENABLED = new CustomItemNbtImpl.Boolean(ModIdentifier.string("effect_enabled"), true);
}
