package com.bajookie.lost_geodes.item;

import net.fabricmc.fabric.api.registry.FuelRegistry;

public class ModFuelItems {
    public static void init() {
        var r = FuelRegistry.INSTANCE;

        r.add(ModItems.HEAT_STONE, 1);
    }
}
