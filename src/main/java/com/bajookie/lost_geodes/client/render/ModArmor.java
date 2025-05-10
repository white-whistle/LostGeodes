package com.bajookie.lost_geodes.client.render;

import com.bajookie.lost_geodes.entity.client.HaloModel;
import com.bajookie.lost_geodes.entity.client.ModModelLayers;
import com.bajookie.lost_geodes.item.ModItems;
import com.bajookie.lost_geodes.item.custom.IStackPredicate;
import com.bajookie.lost_geodes.system.ItemStack.StackLevel;
import com.bajookie.lost_geodes.util.ModIdentifier;
import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModArmor {

    private static final Function<LivingEntity, BipedEntityModel<LivingEntity>> getHatBrimModel = memo(() -> new HaloModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(ModModelLayers.HAT_BRIM)));
    private static final Function<LivingEntity, BipedEntityModel<LivingEntity>> getHaloModel = memo(() -> new HaloModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(ModModelLayers.HALO_LAYER)));

    public static void init() {

        ArmorRenderer.register(new CustomArmor((ctx) -> {
            ctx.parts.add(new CustomArmor.Part(ctx::getOuterArmor, getStackedLayer(ModArmor::armorCutout, "gunheel")));
            ctx.parts.add(new CustomArmor.Part(ctx::getOuterArmor, getStackedLayer(EffectLayer::getGlow, "gunheel_glow")));
        }), ModItems.GUNHEELS);

        ArmorRenderer.register(new CustomArmor((ctx) -> {
            ctx.parts.add(new CustomArmor.Part(ctx::getOuterArmor, getStackedLayer(ModArmor::armorCutout, "gangway")));
            ctx.parts.add(new CustomArmor.Part(ctx::getOuterArmor, getStackedLayer(EffectLayer::getGlow, "gangway_glow")));

            ctx.parts.add(new CustomArmor.Part(getHaloModel, getLayer(ModArmor::armorCutout, "gangway_horns_01")) {
                @Override
                public boolean shouldRender(CustomArmor customArmor, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
                    return StackLevel.isMaxed(stack);
                }
            });
        }), ModItems.MASK_OF_DAWN);

        ArmorRenderer.register(new CustomArmor((ctx) -> {
            ctx.parts.add(new CustomArmor.Part(ctx::getOuterArmor, getStackedLayer(ModArmor::armorCutout, "cowplate")));
        }), ModItems.COWPLATE);

        ArmorRenderer.register(new CustomArmor((ctx) -> {
            ctx.parts.add(new CustomArmor.Part(ctx::getOuterArmor, getStackedLayer(ModArmor::armorCutout, "hareleap_striders")));
        }), ModItems.HARELEAP_STRIDERS);

        ArmorRenderer.register(new CustomArmor((ctx) -> {
            ctx.parts.add(new CustomArmor.Part(ctx::getInnerArmor, getStackedLayer(ModArmor::armorCutout, "atlas_greaves")));
            ctx.parts.add(new CustomArmor.Part(ctx::getInnerArmor, getStackedLayer(EffectLayer::getGlow, "atlas_greaves_glow")) {
                @Override
                public boolean shouldRender(CustomArmor customArmor, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
                    return entity.isOnGround();
                }

                @Override
                public float getAlpha() {
                    return 0.5f;
                }
            });
        }), ModItems.ATLAS_GREAVES);
    }

    public static Identifier armorIdentifier(String name) {
        return new ModIdentifier("textures/models/armor/" + name + ".png");
    }

    public static Function<ItemStack, RenderLayer> getLayer(Function<Identifier, RenderLayer> renderLayerGetter, String name) {
        return (stack) -> renderLayerGetter.apply(armorIdentifier(name));
    }

    public static Function<ItemStack, RenderLayer> getStackedLayer(Function<Identifier, RenderLayer> renderLayerGetter, String name) {
        return (stack) -> renderLayerGetter.apply(armorIdentifier(name + IStackPredicate.stackAppendix(stack)));
    }

    public static RenderLayer armorCutout(Identifier identifier) {
        return RenderLayer.getArmorCutoutNoCull(identifier);
    }

    private static <T extends Model> Function<LivingEntity, T> memo(Supplier<T> getModel) {
        Supplier<T> memoSupplier = Suppliers.memoize(getModel::get);
        return (living) -> memoSupplier.get();
    }
}
