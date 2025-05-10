package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.item.ArtifactItemSettings;
import com.bajookie.lost_geodes.item.ModItems;
import com.bajookie.lost_geodes.item.ability.Ability;
import com.bajookie.lost_geodes.item.reward.DropCondition;
import com.bajookie.lost_geodes.item.reward.IRaidReward;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 8, max = 12)
public class HeatStone extends Item implements IArtifact, IRaidReward {
    @Override
    public int getArtifactMaxStack() {
        return 1;
    }

    public HeatStone() {
        super(new ArtifactItemSettings());
    }

    @Override
    public boolean hasRecipeRemainder() {
        return true;
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        return new ItemStack(ModItems.HEAT_STONE);
    }

    public static final Ability INFINIBURN_ABILITY = new Ability("infiniburn", Ability.AbilityType.SPECIAL) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }
    };

    public static final List<Ability> ABILITIES = List.of(INFINIBURN_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }
}
