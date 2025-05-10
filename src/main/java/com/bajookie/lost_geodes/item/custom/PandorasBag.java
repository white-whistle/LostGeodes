package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.screen.PandorasBagScreenHandler;
import com.bajookie.lost_geodes.system.ItemStack.RaidReward;
import com.bajookie.lost_geodes.system.ItemStack.Soulbound;
import com.bajookie.lost_geodes.system.Text.TextArgs;
import com.bajookie.lost_geodes.system.Text.TextUtil;
import com.bajookie.lost_geodes.util.ModIdentifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PandorasBag extends Item implements IArtifact {
    public static final String BAG_INVENTORY = ModIdentifier.string("inventory");

    public PandorasBag() {
        super(new FabricItemSettings().rarity(Rarity.RARE).maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        var rewards = RaidReward.get(stack);
        var size = rewards.size();
        var nbt = stack.getNbt();
        if (nbt != null) {
            var nbtList = nbt.getList(BAG_INVENTORY, NbtElement.COMPOUND_TYPE);
            if (nbtList != null) {
                var inv = new SimpleInventory(27);
                inv.readNbtList(nbtList);
                for (int i = 0; i < 27; i++) {
                    if (!inv.getStack(i).isEmpty()) {
                        size++;
                    }
                }
            }
        }
        if (size > 0) {
            tooltip.add((TextUtil.translatable("tooltip.lost_geodes.raid_rewards", new TextArgs().put("items", size))));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);

        if (Soulbound.notOwner(stack, user)) return TypedActionResult.fail(stack);

        if (world.isClient) return TypedActionResult.success(stack);

        var nbt = stack.getNbt();
        if (nbt == null) return TypedActionResult.fail(stack);

        var invNbt = nbt.get(BAG_INVENTORY);
        var rewards = RaidReward.get(stack);

        if (invNbt == null && rewards.size() < 1) return TypedActionResult.fail(stack);

        // open inventory
        user.openHandledScreen(new NamedScreenHandlerFactory() {
            @Override
            public Text getDisplayName() {
                return TextUtil.translatable("screen.lost_geodes.pandoras_bag.title");
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                return new PandorasBagScreenHandler(syncId, playerInventory, stack);
            }
        });

        return TypedActionResult.success(stack);
    }

    public static void setBagInventory(ItemStack bag, SimpleInventory inventory) {
        var nbt = bag.getOrCreateNbt();
        nbt.put(BAG_INVENTORY, inventory.toNbtList());
    }

    @Override
    public boolean canArtifactMerge() {
        return false;
    }
}
