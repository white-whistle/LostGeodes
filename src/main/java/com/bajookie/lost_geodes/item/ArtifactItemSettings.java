package com.bajookie.lost_geodes.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.util.Rarity;

public class ArtifactItemSettings extends FabricItemSettings {

    public ArtifactItemSettings() {
        super();

        this.maxCount(1);
        this.rarity(Rarity.EPIC);
    }
}
