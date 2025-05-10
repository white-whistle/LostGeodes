package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.EOTE;
import com.bajookie.lost_geodes.item.ArtifactItemSettings;
import com.bajookie.lost_geodes.item.IHasCooldown;
import com.bajookie.lost_geodes.item.ability.Ability;
import com.bajookie.lost_geodes.item.reward.DropCondition;
import com.bajookie.lost_geodes.item.reward.IRaidReward;
import com.bajookie.lost_geodes.mixin.LivingEntityAccessor;
import com.bajookie.lost_geodes.system.ItemStack.StackLevel;
import com.bajookie.lost_geodes.system.StackedItem.StackedItemStat;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 35, max = 45)
public class Cowplate extends ArmorItem implements IArtifact, IHasCooldown, IStackPredicate, IRaidReward {

    private static final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 60, 20 * 2);

    public Cowplate() {
        super(COWPLATE_ITEM_MATERIAL, Type.CHESTPLATE, new ArtifactItemSettings());
    }

    public static final Ability SHRUG_ABILITY = new Ability("shrug", Ability.AbilityType.PASSIVE) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final Ability MILKABLE_ABILITY = new Ability("milkable", Ability.AbilityType.PASSIVE) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }
    };

    public static final List<Ability> ABILITIES = List.of(SHRUG_ABILITY, MILKABLE_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }

    private static final ArmorMaterial COWPLATE_ITEM_MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurability(Type type) {
            return 0;
        }

        @Override
        public int getProtection(Type type) {
            return 1;
        }

        @Override
        public int getEnchantability() {
            return 1;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return null;
        }

        @Override
        public String getName() {
            return new Identifier(EOTE.MOD_ID, "gangway").toString();
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };

    @Override
    public int getCooldown(ItemStack itemStack) {
        return COOLDOWN.get(itemStack);
    }

    public boolean removeRandomNegativeEffect(PlayerEntity player) {
        var statusEffects = player.getStatusEffects();
        var harmfulStatuses = statusEffects.stream().filter(instance -> instance.getEffectType().getCategory() == StatusEffectCategory.HARMFUL).toList();
        var size = harmfulStatuses.size();
        if (size == 0) return false;

        var randomStatus = harmfulStatuses.get(player.getRandom().nextBetween(0, size - 1));

        ((LivingEntityAccessor) player).invokeOnStatusEffectRemoved(randomStatus);
        statusEffects.remove(randomStatus);

        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            var cdm = player.getItemCooldownManager();

            if (slot != EquipmentSlot.CHEST.getEntitySlotId()) return;
            if (cdm.isCoolingDown(this)) return;

            if (removeRandomNegativeEffect(player)) {
                player.playSound(SoundEvents.ENTITY_COW_HURT, 1f, 0.5f);
                cdm.set(this, getCooldown(stack));
            }

        }
    }

    public boolean handleBucket(ItemStack stack, PlayerEntity user, Hand hand) {
        var handStack = user.getStackInHand(hand);

        var isMaxed = StackLevel.isMaxed(stack);

        if (handStack.isOf(Items.BUCKET)) {
            user.playSound(SoundEvents.ENTITY_COW_MILK, 1.0f, 1.0f);
            ItemStack itemStack2 = ItemUsage.exchangeStack(handStack, user, Items.MILK_BUCKET.getDefaultStack());
            user.setStackInHand(hand, itemStack2);

            return true;
        }

        if (handStack.isOf(Items.BOWL) && isMaxed) {
            user.playSound(SoundEvents.ENTITY_MOOSHROOM_MILK, 1.0f, 1.0f);
            ItemStack itemStack2 = ItemUsage.exchangeStack(handStack, user, new ItemStack(Items.MUSHROOM_STEW));
            user.setStackInHand(hand, itemStack2);

            return true;
        }

        return false;
    }
}
