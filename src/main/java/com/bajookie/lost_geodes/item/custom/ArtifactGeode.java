package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.item.ModItems;
import com.bajookie.lost_geodes.item.reward.IRaidReward;
import com.bajookie.lost_geodes.screen.client.particles.ScreenParticleManager;
import com.bajookie.lost_geodes.screen.client.particles.imps.PowerStar;
import com.bajookie.lost_geodes.system.Text.TooltipSection;
import com.bajookie.lost_geodes.util.Interator;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ArtifactGeode extends Item implements IArtifact {
    static Random r = new Random();

    public ArtifactGeode(Settings settings) {
        super(settings);
    }

    @Override
    public int getArtifactMaxStack() {
        return 1;
    }

    // @Override
    // public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
    //     if (!(otherStack.getItem() instanceof MiningToolItem)) {
    //         fail(player, stack, otherStack);
    //         return false;
    //     }
    //
    //     success(player, stack, otherStack);
    //
    //     stack.decrement(1);
    //
    //     var possibleRewards = ModItems.registeredModItems.stream().filter(item -> item instanceof IRaidReward).toList();
    //
    //     var resultStack = possibleRewards.get(r.nextInt(possibleRewards.size())).getDefaultStack();
    //
    //     player.giveItemStack(resultStack);
    //
    //     return true;
    // }


    @Override
    public boolean onArtifactClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (!(otherStack.getItem() instanceof MiningToolItem)) {
            fail(player, stack, otherStack);
            return false;
        }

        success(player, stack, otherStack);

        stack.decrement(1);

        var possibleRewards = ModItems.registeredModItems.stream().filter(item -> item instanceof IRaidReward).toList();

        var resultStack = possibleRewards.get(r.nextInt(possibleRewards.size())).getDefaultStack();

        if (stack.isEmpty()) {
            slot.setStack(resultStack);
        } else {
            player.giveItemStack(resultStack);
        }

        return true;
    }

    public void success(PlayerEntity user, ItemStack self, ItemStack other) {
        user.playSound(SoundEvents.BLOCK_SMITHING_TABLE_USE, 0.8f, 0.8f + user.getWorld().getRandom().nextFloat() * 0.4f);

        Interator.of(20).forEach(i -> {
            ScreenParticleManager.addParticle(
                    new PowerStar(ScreenParticleManager.getMousePos())
                            .randomizeVelocity(5f)
            );
        });

    }

    public void fail(PlayerEntity user, ItemStack self, ItemStack other) {
        user.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.2f, 0.8f + user.getWorld().getRandom().nextFloat() * 0.4f);
    }

    public static final TooltipSection.Info HIDDEN_TREASURE = new TooltipSection.Info("hidden_treasure") {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
            section.line("info2");
            section.line("info3");
        }
    };

    public static List<TooltipSection> INFO = List.of(HIDDEN_TREASURE);


    @Override
    public List<TooltipSection> getAdditionalInfo(ItemStack stack) {
        return INFO;
    }
}
