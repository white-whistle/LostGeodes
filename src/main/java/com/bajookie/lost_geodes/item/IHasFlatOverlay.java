package com.bajookie.lost_geodes.item;

import com.bajookie.lost_geodes.client.render.EffectLayer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public interface IHasFlatOverlay {

    boolean showFlatOverlay(ItemStack itemStack);

    static Identifier getFlatItemModel(Item item) {
        return IHasUpscaledModel.getUpscaledItemModel(item).withSuffixedPath("_flat");
    }

    static void translateMatrix(MatrixStack matrix) {
        matrix.translate(0, 0, 8 / 16f);
        matrix.scale(1, 1, 0);
    }

    default RenderLayer getFlatRenderLayer() {
        return EffectLayer.getItemEntityWithCulling(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
    }

}
