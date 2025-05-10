package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.item.ArtifactItemSettings;
import com.bajookie.lost_geodes.item.ability.Ability;
import com.bajookie.lost_geodes.item.reward.DropCondition;
import com.bajookie.lost_geodes.item.reward.IRaidReward;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;


@DropCondition.RaidLevelBetween(max = 10)
public class PortalRingItem extends BiDimensionToggleItem implements IArtifact, IRaidReward {

    public PortalRingItem() {
        super(new ArtifactItemSettings(), new Pair<>(World.OVERWORLD, World.NETHER));
    }

    public static final Ability DIMENSIONAL_SHIFT_ABILITY = new Ability("dimensional_shift", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            if (world != null) {
                var worldKey = world.getRegistryKey();

                if (worldKey == World.NETHER) {
                    section.line("to_overworld");
                } else if (worldKey == World.OVERWORLD) {
                    section.line("to_nether");
                } else {
                    section.line("other");
                }
            }
        }
    };

    public static final List<Ability> ABILITIES = List.of(DIMENSIONAL_SHIFT_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }
}
