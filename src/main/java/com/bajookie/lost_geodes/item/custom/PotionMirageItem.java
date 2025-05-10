package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.item.ArtifactItemSettings;
import com.bajookie.lost_geodes.item.IHasCooldown;
import com.bajookie.lost_geodes.item.ability.Ability;
import com.bajookie.lost_geodes.item.reward.DropCondition;
import com.bajookie.lost_geodes.item.reward.IRaidReward;
import com.bajookie.lost_geodes.system.StackedItem.StackedItemStat;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 15, max = 100)
public class PotionMirageItem extends Item implements IArtifact, IHasCooldown, IStackPredicate, IRaidReward {
    public static final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 20, 10 * 20);

    public PotionMirageItem() {
        super(new ArtifactItemSettings());
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return COOLDOWN.get(itemStack);
    }

    public static Ability MIRROR_IMAGE_ABILITY = new Ability("mirror_image", Ability.AbilityType.PASSIVE) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final List<Ability> ABILITIES = List.of(MIRROR_IMAGE_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }
}
