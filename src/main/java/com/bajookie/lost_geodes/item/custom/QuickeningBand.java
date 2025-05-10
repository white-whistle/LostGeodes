package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.item.ArtifactItemSettings;
import com.bajookie.lost_geodes.item.ICooldownReduction;
import com.bajookie.lost_geodes.item.IHasCooldown;
import com.bajookie.lost_geodes.item.ability.Ability;
import com.bajookie.lost_geodes.item.reward.DropCondition;
import com.bajookie.lost_geodes.item.reward.IRaidReward;
import com.bajookie.lost_geodes.system.StackedItem.StackedItemStat;
import com.bajookie.lost_geodes.system.Text.TextArgs;
import com.bajookie.lost_geodes.system.Text.TextUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(max = 20)
public class QuickeningBand extends Item implements IArtifact, ICooldownReduction, IHasCooldown, IStackPredicate, IRaidReward {

    public static final int EFFECT_DURATION = 20 * 15;
    public static final StackedItemStat.Int EFFECT_LEVEL = new StackedItemStat.Int(1, 8);

    public QuickeningBand() {
        super(new ArtifactItemSettings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        if (user.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.fail(stack);

        var eLevel = EFFECT_LEVEL.get(stack);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, EFFECT_DURATION, eLevel));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, EFFECT_DURATION, eLevel));

        user.getItemCooldownManager().set(this, this.getCooldown(stack));

        return TypedActionResult.success(stack, world.isClient());
    }

    public static final Ability SURGE_ABILITY = new Ability("surge", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            var eLevel = EFFECT_LEVEL.get(stack) + 1;

            section.line("info1", new TextArgs().putI("level", eLevel));
            section.line("info2", new TextArgs().put("duration", TextUtil.formatTime(EFFECT_DURATION)));
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final List<Ability> ABILITIES = List.of(SURGE_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }


    @Override
    public float getCooldownReductionPercentage(ItemStack stack) {
        return 0.1f;
    }

    @Override
    public String cooldownInstanceId(ItemStack stack) {
        return "quickening_band";
    }

    @Override
    public int getCooldown(ItemStack stack) {
        return 20 * 60;
    }
}
