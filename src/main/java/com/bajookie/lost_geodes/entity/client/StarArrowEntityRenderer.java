package com.bajookie.lost_geodes.entity.client;

import com.bajookie.lost_geodes.entity.custom.StarArrowProjectile;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class StarArrowEntityRenderer extends ProjectileEntityRenderer<StarArrowProjectile> {
    public static final Identifier TEXTURE = new Identifier("textures/entity/projectiles/arrow.png");
    public static final Identifier TIPPED_TEXTURE = new Identifier("textures/entity/projectiles/tipped_arrow.png");

    public StarArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(StarArrowProjectile arrowEntity) {
        return arrowEntity.getColor() > 0 ? TIPPED_TEXTURE : TEXTURE;
    }
}
