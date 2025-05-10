package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.item.ArtifactItemSettings;
import com.bajookie.lost_geodes.item.IHasUpscaledModel;
import com.bajookie.lost_geodes.item.ModItems;
import com.bajookie.lost_geodes.item.ability.Ability;
import com.bajookie.lost_geodes.item.reward.DropCondition;
import com.bajookie.lost_geodes.item.reward.IRaidReward;
import com.bajookie.lost_geodes.system.ItemStack.StackLevel;
import com.bajookie.lost_geodes.system.StackedItem.StackedAttributeModifiers;
import com.bajookie.lost_geodes.system.StackedItem.StackedItemStat;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 50)
public class RealityPick extends PickaxeItem implements IArtifact, IHasUpscaledModel, IStackPredicate, IRaidReward {
    protected static final int MAX_COUNT = 16;
    private final StackedItemStat.Float stackedAttackDamage = new StackedItemStat.Float(4f, 16f);
    private final StackedItemStat.Float miningSpeedMultiplier = new StackedItemStat.Float(0.1f, 4f);

    protected final StackedAttributeModifiers stackedAttributeModifiers = new StackedAttributeModifiers((index) -> {
        var progress = index / (float) (MAX_COUNT - 1);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double) stackedAttackDamage.get(progress), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2, EntityAttributeModifier.Operation.ADDITION));

        return builder.build();
    });

    @Override
    public int getArtifactMaxStack() {
        return MAX_COUNT;
    }

    public RealityPick() {
        super(ModItems.ARTIFACT_BASE_MATERIAL, 0, 0, new ArtifactItemSettings());
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return stackedAttributeModifiers.get(StackLevel.get(stack) - 1);
        }
        return super.getAttributeModifiers(stack, slot);
    }

    public static final Ability COSMIC_PICK_ABILITY = new Ability("cosmic_pick", Ability.AbilityType.ON_MINE, Ability.AbilityTrigger.LEFT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }
    };

    public static final List<Ability> ABILITIES = List.of(COSMIC_PICK_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return super.getMiningSpeedMultiplier(stack, state) + miningSpeedMultiplier.get(stack);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return true;
    }

    @Override
    public boolean shouldUseUpscaledModel(ItemStack itemStack) {
        return StackLevel.isMaxed(itemStack);
    }
}
