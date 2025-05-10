package com.bajookie.lost_geodes.datagen;

import com.bajookie.lost_geodes.EOTE;
import com.bajookie.lost_geodes.block.ModBlocks;
import com.bajookie.lost_geodes.item.IHasFlatOverlay;
import com.bajookie.lost_geodes.item.IHasUpscaledModel;
import com.bajookie.lost_geodes.item.ModItems;
import com.bajookie.lost_geodes.item.custom.IArtifact;
import com.bajookie.lost_geodes.item.custom.IStackPredicate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // blockStateModelGenerator.registerTintableCross(ModBlocks.NETHER_FRUIT_BLOCK, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.EXPLORER_FRUIT_BLOCK, ModBlocks.POTTED_EXPLORER_FRUIT_BLOCK, BlockStateModelGenerator.TintType.NOT_TINTED);
        // blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.MINERS_FRUIT_BLOCK, ModBlocks.POTTED_MINERS_FRUIT_BLOCK, BlockStateModelGenerator.TintType.NOT_TINTED);
        // blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.ELDER_LILY_FLOWER, ModBlocks.POTTED_ELDER_LILY_FLOWER, BlockStateModelGenerator.TintType.NOT_TINTED);
        // blockStateModelGenerator.registerTintableCross(ModBlocks.SPIRITAL_GRASS, BlockStateModelGenerator.TintType.TINTED);
        // blockStateModelGenerator.registerFlowerbed(ModBlocks.SPIRIT_PETALS);
        // blockStateModelGenerator.registerPlantPart();
        // blockStateModelGenerator.registerSimpleState(ModBlocks.BEAR_TRAP_BLOCK);
        // blockStateModelGenerator.registerSimpleState(ModBlocks.ARTIFACT_VAULT);
        // blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SUN_RUNE_BLOCK);
        // blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CHISELED_MOSSY_STONE);
        // Wood
        // blockStateModelGenerator.registerTintableCross(ModBlocks.ANCIENT_TREE_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ARTIFACT_GEODE);
        // blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ANCIENT_TREE_LEAVES);
        // blockStateModelGenerator.registerLog(ModBlocks.ANCIENT_TREE_LOG).log(ModBlocks.ANCIENT_TREE_LOG).wood(ModBlocks.ANCIENT_TREE_WOOD);
        // blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_ANCIENT_TREE_LOG).log(ModBlocks.STRIPPED_ANCIENT_TREE_LOG).wood(ModBlocks.STRIPPED_ANCIENT_TREE_WOOD);
    }

    static class ItemModelConfig {
        protected static final List<Item> HANDHELD = List.of(
                ModItems.MIDAS_HAMMER,
                ModItems.WTF_HAMMER
        );
        protected static final List<Item> SKIP = List.of(
                ModItems.WITHERS_BULWARK,
                ModItems.ORB_OF_LIGHTNING,
                ModItems.REALITY_PICK,
                ModItems.SCORCHERS_MITTS,
                // ModItems.STASIS_STOPWATCH,
                ModItems.TIME_GLYPH,
                ModItems.WTF_TOKEN,
                ModItems.POCKET_GALAXY,
                ModItems.STARFALL_BOW
        );
    }

    public static final Model HANDHELD_X32 = new Model(Optional.of(new Identifier(EOTE.MOD_ID, "item/handheld_32")), Optional.empty(), TextureKey.LAYER0);
    public static final Model HANDHELD_X32_FLAT = new Model(Optional.of(new Identifier(EOTE.MOD_ID, "item/handheld_32_flat")), Optional.empty(), TextureKey.LAYER0);
    public static final Model GUN = new Model(Optional.of(new Identifier(EOTE.MOD_ID, "item/gun")), Optional.empty(), TextureKey.LAYER0);

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        ModItems.registeredModItems.forEach(item -> {

            // skip model gen (items with hand-made jsons)
            if (ItemModelConfig.SKIP.contains(item)) {
                return;
            }

            if (item instanceof SpawnEggItem) {
                itemModelGenerator.register(item, new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty()));
                return;
            }

            // handheld models
            if (ItemModelConfig.HANDHELD.contains(item)) {
                itemModelGenerator.register(item, Models.HANDHELD);

                return;
            }

            if (item instanceof IStackPredicate iStackPredicate && item instanceof IArtifact iArtifact) {
                var levels = iStackPredicate.getTextureIndex(iArtifact.getArtifactMaxStack()) + 1;
                var modelId = ModelIds.getItemModelId(item);
                var baseModel = iStackPredicate.getBaseModel();

                addStackedVariants(itemModelGenerator, modelId, levels, baseModel);

                if (item instanceof IHasUpscaledModel) {
                    if (baseModel == Models.HANDHELD) {
                        addStackedVariants(itemModelGenerator, modelId.withSuffixedPath("_x32"), levels, HANDHELD_X32);
                    }
                    if (item instanceof IHasFlatOverlay) {
                        if (baseModel == Models.HANDHELD) {
                            addStackedVariants(itemModelGenerator, modelId.withSuffixedPath("_x32_flat"), levels, HANDHELD_X32_FLAT);
                        }
                    }
                }


                return;
            }

            // by default register as generated
            itemModelGenerator.register(item, Models.GENERATED);
        });
    }

    public static void addStackedVariants(ItemModelGenerator itemModelGenerator, Identifier modelId, int levels, Model baseModel) {
        baseModel.upload(modelId, TextureMap.layer0(modelId), itemModelGenerator.writer, (id, textures) -> {
            var json = baseModel.createJson(id, textures);

            JsonArray jsonArray = new JsonArray();

            for (int i = 1; i < levels; i++) {
                var entry = new JsonObject();
                var predicate = new JsonObject();

                var pModelId = modelId.withSuffixedPath(String.format("_%02d", i));

                predicate.addProperty(new Identifier(EOTE.MOD_ID, "stack_level").toString(), i / (float) (levels - 1));

                entry.add("predicate", predicate);
                entry.addProperty("model", pModelId.toString());

                jsonArray.add(entry);

            }

            json.add("overrides", jsonArray);

            return json;
        });

        for (int i = 1; i < levels; i++) {
            var pModelId = modelId.withSuffixedPath(String.format("_%02d", i));

            baseModel.upload(pModelId, TextureMap.layer0(pModelId), itemModelGenerator.writer);
        }
    }
}