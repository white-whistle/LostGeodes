package com.bajookie.lost_geodes.mixin;

import com.bajookie.lost_geodes.EOTE;
import com.bajookie.lost_geodes.item.IHasFlatOverlay;
import com.bajookie.lost_geodes.item.IHasUpscaledModel;
import com.bajookie.lost_geodes.item.ModItems;
import com.bajookie.lost_geodes.item.custom.IStackPredicate;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;


@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void addCustomModels(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
        // this.addModel(new ModelIdentifier(EOTE.MOD_ID, "arc_lightning_3d", "inventory"));
        // this.addModel(new ModelIdentifier(EOTE.MOD_ID, "scorchers_mitts_3d", "inventory"));
        this.addModel(new ModelIdentifier(EOTE.MOD_ID, "soulbound", "inventory"));
        this.addModel(new ModelIdentifier(EOTE.MOD_ID, "switch_off", "inventory"));
        this.addModel(new ModelIdentifier(EOTE.MOD_ID, "switch_on", "inventory"));
        this.addModel(new ModelIdentifier(EOTE.MOD_ID, "ancient_minigun_3d", "inventory"));

        ModItems.registeredModItems.forEach(item -> {
            if (item instanceof IHasUpscaledModel) {
                this.addModel(new ModelIdentifier(EOTE.MOD_ID, IHasUpscaledModel.getUpscaledItemModel(item).getPath(), "inventory"));

                if (item instanceof IHasFlatOverlay) {
                    IStackPredicate.interator(item).forEach(i -> {
                        this.addModel(new ModelIdentifier(EOTE.MOD_ID, IHasFlatOverlay.getFlatItemModel(item).withSuffixedPath(IStackPredicate.textureIndexToAppendix(i)).getPath(), "inventory"));
                    });
                }

            }
        });
    }
}