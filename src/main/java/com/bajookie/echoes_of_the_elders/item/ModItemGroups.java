package com.bajookie.echoes_of_the_elders.item;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import com.bajookie.echoes_of_the_elders.item.custom.IStackPredicate;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

@SuppressWarnings("unused")
public class ModItemGroups {
    public static final ItemGroup MOD_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "mod_item_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.echoes_of_the_elders")).icon(() -> new ItemStack(ModBlocks.ARTIFACT_GEODE)).entries((displayContext, entries) -> {
                entries.add(ModItems.EXPLORER_FRUIT);
                entries.add(ModBlocks.EXPLORER_FRUIT_BLOCK);
                entries.add(ModItems.GALE_ARROW);
            }).build());

    public static void addStackedVariants(ItemGroup.Entries entries, Item item) {
        entries.add(item);

        if (item instanceof IStackPredicate iStackPredicate && item instanceof IArtifact iArtifact) {
            var textures = iStackPredicate.getTextureIndex(iArtifact.getArtifactMaxStack());
            for (int i = 1; i < (textures + 1); i++) {
                int level = (int) ((i / (float) textures) * iArtifact.getArtifactMaxStack());
                entries.add(StackLevel.set(new ItemStack(item), level), ItemGroup.StackVisibility.SEARCH_TAB_ONLY);
            }
        }
    }

    public static final ItemGroup MOD_ARTIFACT_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "mod_artifact_group"),
            FabricItemGroup.builder().displayName(Text.translatable("artifact-group.echoes_of_the_elders")).icon(() -> new ItemStack(ModItems.VITALITY_PUMP)).entries((displayContext, entries) -> {
                addStackedVariants(entries, ModItems.RADIANT_LOTUS);
                addStackedVariants(entries, ModItems.VITALITY_PUMP);
                addStackedVariants(entries, ModItems.PORTAL_RING);
                addStackedVariants(entries, ModItems.MIDAS_HAMMER);
                addStackedVariants(entries, ModItems.GALE_QUIVER);
                addStackedVariants(entries, ModItems.SCORCHERS_MITTS);
                addStackedVariants(entries, ModItems.DOOMSTICK_ITEM);
                addStackedVariants(entries, ModItems.POTION_MIRAGE);
                addStackedVariants(entries, ModItems.WITHERS_BULWARK);
                addStackedVariants(entries, ModItems.QUICKENING_BAND);
                addStackedVariants(entries, ModItems.ORB_OF_LIGHTNING);
                addStackedVariants(entries, ModItems.REALITY_PICK);
                addStackedVariants(entries, ModItems.GODSLAYER);
                addStackedVariants(entries, ModItems.GUNHEELS);
                // addStackedVariants(entries, ModItems.STAT_FRUIT_HP);
                addStackedVariants(entries, ModItems.TIME_GLYPH);
                addStackedVariants(entries, ModItems.WTF_TOKEN);
                addStackedVariants(entries, ModItems.SPARKING_MITTS);
                addStackedVariants(entries, ModItems.SKY_WARD);
                addStackedVariants(entries, ModItems.POCKET_GALAXY);
                addStackedVariants(entries, ModItems.MOLTEN_CHAMBER);
                addStackedVariants(entries, ModItems.ECHOING_SWORD);
                addStackedVariants(entries, ModItems.ORB_OF_ANNIHILATION);
                addStackedVariants(entries, ModItems.STARFALL_BOW);
                // addStackedVariants(entries, ModItems.EARTH_SPIKE_RELIC);
                addStackedVariants(entries, ModItems.ICICLE_RELIC);
                entries.add(ModItems.MAGIC_HAMMER);
                entries.add(ModItems.HEAT_STONE);
                entries.add(ModItems.STEPPING_STONE);
                addStackedVariants(entries, ModItems.ARTIFACT_HAMMER);
                addStackedVariants(entries, ModItems.WTF_HAMMER);
                // addStackedVariants(entries, ModItems.ANCIENT_MINIGUN);
                addStackedVariants(entries, ModItems.VOID_RAY);
                addStackedVariants(entries, ModItems.MASK_OF_DAWN);
                addStackedVariants(entries, ModItems.COWPLATE);
                addStackedVariants(entries, ModItems.HARELEAP_STRIDERS);
                addStackedVariants(entries, ModItems.ATLAS_GREAVES);
                addStackedVariants(entries, ModItems.SPIRAL_SWORD);
                // addStackedVariants(entries, ModItems.MONOLOOK_SPAWN);
            }).build());

    public static void registerGroups() {
        EOTE.LOGGER.info("Registering Item Groups for ---> " + MOD_ID);
    }
}
