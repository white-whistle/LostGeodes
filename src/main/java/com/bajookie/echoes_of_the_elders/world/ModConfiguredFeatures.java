package com.bajookie.echoes_of_the_elders.world;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> EXPLORERS_FRUIT_KEY = registerKey("explorer_fruit");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        makeVegetationConfigFeatures(context);

    }

    public static void makeVegetationConfigFeatures(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, EXPLORERS_FRUIT_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(1, 1, 1, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.EXPLORER_FRUIT_BLOCK)))));


    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(EOTE.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
