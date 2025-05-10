package com.bajookie.lost_geodes.system.StackedItem;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

public class StackableItemSettings extends FabricItemSettings {
    @Override
    public FabricItemSettings maxDamageIfAbsent(int maxDamage) {
        return this;
    }
}
