package com.bajookie.lost_geodes;

import com.bajookie.lost_geodes.block.ModBlocks;
import com.bajookie.lost_geodes.block.custom.entity.ModBlockEntities;
import com.bajookie.lost_geodes.effects.ModEffects;
import com.bajookie.lost_geodes.entity.ModEntities;
import com.bajookie.lost_geodes.item.ModFuelItems;
import com.bajookie.lost_geodes.item.ModItemGroups;
import com.bajookie.lost_geodes.item.ModItems;
import com.bajookie.lost_geodes.item.reward.DropCondition;
import com.bajookie.lost_geodes.particles.ModParticles;
import com.bajookie.lost_geodes.screen.ModScreenHandlerTypes;
import com.bajookie.lost_geodes.sound.ModSounds;
import com.bajookie.lost_geodes.util.ModLootTablesModifiers;
import com.bajookie.lost_geodes.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EOTE implements ModInitializer {
    public static final String MOD_ID = "lost_geodes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModItemGroups.registerGroups();
        ModLootTablesModifiers.modifyLootTables();
        ModWorldGeneration.generateModWorldGen();
        ModEffects.registerEffects();

        ModEntities.registerMobAttributes();
        ModParticles.registerParticles();
        ModSounds.registerSounds();
        ModBlockEntities.registerBlockEntities();
        ModEntities.registerMobAttributes();
        ModParticles.registerParticles();
        ModSounds.registerSounds();
        ServerNetworking.init();
        ModScreenHandlerTypes.init();
        ModFuelItems.init();

        DropCondition.init();
    }


}