package com.bajookie.lost_geodes.item.custom;

import com.bajookie.lost_geodes.datagen.WebView;
import com.bajookie.lost_geodes.item.ICooldownReduction;
import com.bajookie.lost_geodes.item.ability.Ability;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

@WebView.OverrideTexture("time_token")
public class WTFGlyph extends Item implements IArtifact, ICooldownReduction {
    public WTFGlyph() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        var cdm = user.getItemCooldownManager();

        TimeGlyph.clearCooldowns(cdm);

        if (world.isClient) {
            MinecraftClient.getInstance().gameRenderer.showFloatingItem(stack);
        }

        return TypedActionResult.success(stack);
    }

    public static final List<Ability> ABILITIES = List.of(TimeGlyph.REFRESH_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }

    @Override
    public float getCooldownReductionPercentage(ItemStack stack) {
        return 1;
    }

    @Override
    public String cooldownInstanceId(ItemStack stack) {
        return "WTF?";
    }
}
