package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.item.*;
import com.bajookie.lost_geodes.item.ability.Ability;
import com.bajookie.lost_geodes.item.reward.DropCondition;
import com.bajookie.lost_geodes.item.reward.IRaidReward;
import com.bajookie.lost_geodes.system.ItemStack.StackLevel;
import com.bajookie.lost_geodes.system.StackedItem.StackedAttributeModifiers;
import com.bajookie.lost_geodes.system.StackedItem.StackedItemStat;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.bajookie.lost_geodes.system.ItemStack.CustomItemNbt.EFFECT_ENABLED;

@DropCondition.RaidLevelBetween(min = 35, max = 75)
public class SpiralSword extends SwordItem implements IArtifact, IStackPredicate, IHasToggledEffect, IHasUpscaledModel, IHasFlatOverlay, IRaidReward {
    private static final StackedItemStat.Float ATTACK_DAMAGE = new StackedItemStat.Float(4f, 12f);
    private static final StackedItemStat.Float ATTACK_SPEED = new StackedItemStat.Float(-3.5f, -2f);
    private final TargetPredicate targetPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(5).setPredicate(l -> true);

    private final StackedAttributeModifiers stackedAttributeModifiers = new StackedAttributeModifiers(index -> {
        var progress = index / (float) (SpiralSword.this.getArtifactMaxStack() - 1);

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double) ATTACK_DAMAGE.get(progress), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double) ATTACK_SPEED.get(progress), EntityAttributeModifier.Operation.ADDITION));

        return builder.build();
    });

    public SpiralSword() {
        super(ModItems.ARTIFACT_BASE_MATERIAL, 0, 0, new ArtifactItemSettings());
    }

    public static final Ability AUTO_ATTACK = new Ability("auto_attack", Ability.AbilityType.PASSIVE, Ability.AbilityTrigger.TOGGLED) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }
    };

    public static final List<Ability> ABILITIES = List.of(AUTO_ATTACK);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return stackedAttributeModifiers.get(StackLevel.get(stack) - 1);
        }
        return super.getAttributeModifiers(stack, slot);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!EFFECT_ENABLED.get(stack)) return;
        if (!selected) return;
        if (!(entity instanceof PlayerEntity player)) return;

        var isOffhand = player.getOffHandStack() == stack;
        if (isOffhand) return;

        var attackCooldownProgress = player.getAttackCooldownProgress(0f);
        if (attackCooldownProgress < 1) return;

        var closestTarget = world.getClosestEntity(HostileEntity.class, targetPredicate, null, 0, 0, 0, player.getBoundingBox().expand(5, 3, 5));
        if (closestTarget == null) return;

        player.attack(closestTarget);
        player.swingHand(Hand.MAIN_HAND, !world.isClient);

    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public Model getBaseModel() {
        return Models.HANDHELD;
    }

    @Override
    public boolean shouldUseUpscaledModel(ItemStack itemStack) {
        return StackLevel.isMaxed(itemStack);
    }

    @Override
    public boolean showFlatOverlay(ItemStack itemStack) {
        return StackLevel.isMaxed(itemStack);
    }
}
