package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.EOTE;
import com.bajookie.lost_geodes.item.ArtifactItemSettings;
import com.bajookie.lost_geodes.item.IHasCooldown;
import com.bajookie.lost_geodes.item.ability.Ability;
import com.bajookie.lost_geodes.item.ability.IHasSlotAbility;
import com.bajookie.lost_geodes.item.reward.DropCondition;
import com.bajookie.lost_geodes.item.reward.IRaidReward;
import com.bajookie.lost_geodes.system.StackedItem.StackedItemStat;
import com.bajookie.lost_geodes.system.Text.TextArgs;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 35)
public class MaskOfDawn extends ArmorItem implements IArtifact, IHasSlotAbility, IHasCooldown, IStackPredicate, IRaidReward {

    private static final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 60, 20 * 5);

    public MaskOfDawn() {
        super(GANGWAY_ITEM_MATERIAL, Type.HELMET, new ArtifactItemSettings());
    }

    public static final Ability GANGWAY_ABILITY = new Ability("gangway", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.GEAR) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().putF("damage", DoomstickItem.ABILITY_DAMAGE.get(stack)));
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final List<Ability> ABILITIES = List.of(GANGWAY_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }

    private static final ArmorMaterial GANGWAY_ITEM_MATERIAL = new ArmorMaterial() {
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
    public @Nullable Ability getAbility(EquipmentSlot equipmentSlot) {
        if (equipmentSlot != EquipmentSlot.HEAD) return null;

        return DoomstickItem.DOOM_BEAM;
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return COOLDOWN.get(itemStack);
    }
}
