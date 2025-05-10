package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.entity.custom.OrbOfLightningProjectileEntity;
import com.bajookie.lost_geodes.item.ArtifactItemSettings;
import com.bajookie.lost_geodes.item.IHasCooldown;
import com.bajookie.lost_geodes.item.ability.Ability;
import com.bajookie.lost_geodes.item.reward.DropCondition;
import com.bajookie.lost_geodes.item.reward.IRaidReward;
import com.bajookie.lost_geodes.system.StackedItem.StackedItemStat;
import com.bajookie.lost_geodes.system.Text.TextArgs;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 15, max = 75)
public class OrbOfLightning extends Item implements IArtifact, IStackPredicate, IHasCooldown, IRaidReward {
    protected final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 20, 20 * 5);
    public static final StackedItemStat.Int LIGHTNING_STRIKES = new StackedItemStat.Int(1, 3);

    public OrbOfLightning() {
        super(new ArtifactItemSettings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var itemStack = user.getStackInHand(hand);

        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            user.getItemCooldownManager().set(this, this.getCooldown(itemStack));

            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
            if (!world.isClient) {
                OrbOfLightningProjectileEntity orbOfLightningProjectileEntity = new OrbOfLightningProjectileEntity(world, user);
                orbOfLightningProjectileEntity.setOwner(user);
                orbOfLightningProjectileEntity.setItem(itemStack);
                orbOfLightningProjectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
                world.spawnEntity(orbOfLightningProjectileEntity);
            }

            user.incrementStat(Stats.USED.getOrCreateStat(this));

            return TypedActionResult.success(itemStack, world.isClient());
        }

        return super.use(world, user, hand);
    }

    public static final Ability LIGHTNING_BARRAGE_ABILITY = new Ability("lightning_barrage", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().putI("strikes", LIGHTNING_STRIKES.get(stack)));
            section.line("info2");
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final List<Ability> ABILITIES = List.of(LIGHTNING_BARRAGE_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return COOLDOWN.get(itemStack);
    }
}
