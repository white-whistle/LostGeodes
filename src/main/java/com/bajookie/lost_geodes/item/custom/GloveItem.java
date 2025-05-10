package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.system.Text.TooltipSection;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface GloveItem {

    TooltipSection GLOVE_INFO = new TooltipSection.Info("glove") {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
            section.line("info2");
        }
    };

}
