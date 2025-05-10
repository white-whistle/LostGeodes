package com.bajookie.echoes_of_the_elders;

import com.bajookie.echoes_of_the_elders.datagen.ModModelProvider;
import com.bajookie.echoes_of_the_elders.datagen.ModWorldGenerator;
import com.bajookie.echoes_of_the_elders.util.ModLootTableProvider;
import com.bajookie.echoes_of_the_elders.util.ModRecipeProvider;
import com.bajookie.echoes_of_the_elders.world.ModConfiguredFeatures;
import com.bajookie.echoes_of_the_elders.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class EOTEDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModWorldGenerator::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
    }
}
