package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.entity.custom.OrbOfAnnihilationEntity;
import com.bajookie.lost_geodes.item.ArtifactItemSettings;
import com.bajookie.lost_geodes.item.IHasCooldown;
import com.bajookie.lost_geodes.item.IHasToggledEffect;
import com.bajookie.lost_geodes.item.ability.Ability;
import com.bajookie.lost_geodes.item.reward.DropCondition;
import com.bajookie.lost_geodes.item.reward.IRaidReward;
import com.bajookie.lost_geodes.system.StackedItem.StackedItemStat;
import com.bajookie.lost_geodes.system.Text.TextArgs;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 25, max = 35)
public class OrbOfAnnihilation extends Item implements IArtifact, IStackPredicate, IHasToggledEffect, IRaidReward, IHasCooldown {
    public static final StackedItemStat.Float EXPLOSION_POWER = new StackedItemStat.Float(6f, 20f);
    public static final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 60, 20 * 10);

    public OrbOfAnnihilation() {
        super(new ArtifactItemSettings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        var cdm = user.getItemCooldownManager();
        if (cdm.isCoolingDown(this)) return TypedActionResult.fail(stack);
        if (!world.isClient) {
            var orbEntity = new OrbOfAnnihilationEntity(world, user.getX(), user.getY() + 3, user.getZ(), 0.7f, user);

            world.spawnEntity(orbEntity);
            user.startRiding(orbEntity);

            orbEntity.setStack(stack);
            cdm.set(this, COOLDOWN.get(stack));
        }
        return TypedActionResult.success(stack);
    }

    public static final Ability MOUNT_ABILITY = new Ability("mount", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
            section.line("info2", new TextArgs().putF("explosion_power", EXPLOSION_POWER.get(stack)));
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final Ability FIERY_EXPLOSION_ABILITY = new Ability("fiery_explosion", Ability.AbilityType.PASSIVE, Ability.AbilityTrigger.TOGGLED) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }
    };

    public static final List<Ability> ABILITIES = List.of(MOUNT_ABILITY, FIERY_EXPLOSION_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return COOLDOWN.get(itemStack);
    }
}
