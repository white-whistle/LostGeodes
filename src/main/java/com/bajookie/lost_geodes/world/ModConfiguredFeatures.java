package com.bajookie.lost_geodes.world;

import com.bajookie.lost_geodes.EOTE;
import com.bajookie.lost_geodes.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> EXPLORERS_FRUIT_KEY = registerKey("explorer_fruit");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ARTIFACT_GEODE_ORE_KEY = registerKey("artifact_geode_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        makeVegetationConfigFeatures(context);
        makeOreConfigFeatures(context);
    }

    public static void makeVegetationConfigFeatures(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, EXPLORERS_FRUIT_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(1, 1, 1, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.EXPLORER_FRUIT_BLOCK)))));
    }
    public static void makeOreConfigFeatures(Registerable<ConfiguredFeature<?, ?>> context) {
        var stoneReplacables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);

        var overworldStoneGeodeOres = List.of(OreFeatureConfig.createTarget(stoneReplacables, ModBlocks.ARTIFACT_GEODE.getDefaultState()));

        register(context, ARTIFACT_GEODE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldStoneGeodeOres, 3 /* vein size - is really odd and stupid */));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(EOTE.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
