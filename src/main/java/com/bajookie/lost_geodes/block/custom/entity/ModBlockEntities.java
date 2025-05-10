package com.bajookie.lost_geodes.block.custom.entity;

import com.bajookie.lost_geodes.EOTE;
import com.bajookie.lost_geodes.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.bajookie.lost_geodes.EOTE.MOD_ID;

public class ModBlockEntities {
    public static void registerBlockEntities(){
        EOTE.LOGGER.info("creating block entities for --->"+MOD_ID);
    }
}
