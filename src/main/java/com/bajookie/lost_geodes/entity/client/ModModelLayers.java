package com.bajookie.lost_geodes.entity.client;

import com.bajookie.lost_geodes.entity.ModEntities;
import com.bajookie.lost_geodes.item.models.MinigunModel;
import com.bajookie.lost_geodes.util.ModIdentifier;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import static com.bajookie.lost_geodes.EOTE.MOD_ID;

public class ModModelLayers {

    public static final EntityModelLayer MAGMA_BULLET_LAYER = new EntityModelLayer(new ModIdentifier("magma_bullet_entity"), "main");
    public static final EntityModelLayer HAT_BRIM = new EntityModelLayer(new ModIdentifier("hat_brim"), "main");
    public static final EntityModelLayer HALO_LAYER = new EntityModelLayer(new ModIdentifier("halo"), "main");
    public static final EntityModelLayer PELLET_PROJECTILE_LAYER = new EntityModelLayer(new ModIdentifier("pellet_entity"), "main");
    public static final EntityModelLayer MINIGUN_LAYER = new EntityModelLayer(new ModIdentifier("minigun_layer"), "main");
    public static final EntityModelLayer MONOLOOK_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "monolook_entity"), "main");

    static class FarFlyingItemRenderer extends FlyingItemEntityRenderer {
        public FarFlyingItemRenderer(EntityRendererFactory.Context ctx, float scale, boolean lit) {
            super(ctx, scale, lit);
        }

        public FarFlyingItemRenderer(EntityRendererFactory.Context context) {
            super(context);
        }

        @Override
        public boolean shouldRender(Entity entity, Frustum frustum, double x, double y, double z) {
            return true;
        }
    }

    /**
     * Register Model Layers here:
     */
    public static void registerModMobLayers() {
        EntityModelLayerRegistry.registerModelLayer(MAGMA_BULLET_LAYER, MagmaBulletModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(PELLET_PROJECTILE_LAYER, PelletProjectileModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(MONOLOOK_LAYER, MonolookEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MONOLOOK_ENTITY_TYPE, MonolookEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(MINIGUN_LAYER, MinigunModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HAT_BRIM, HaloModel.getTexturedModelData(0, 0, 0));
        EntityModelLayerRegistry.registerModelLayer(HALO_LAYER, HaloModel.getTexturedModelData((float) (Math.PI / 2f), 0, 0));

    }
}
