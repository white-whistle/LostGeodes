package com.bajookie.lost_geodes.mixin;

import com.bajookie.lost_geodes.system.ItemStack.Soulbound;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
        var itemEntity = (ItemEntity) (Object) this;
        var stack = itemEntity.getStack();

        var soulbound = Soulbound.getUuid(stack);
        if (soulbound == null) return;

        if (!soulbound.equals(player.getUuid())) {
            ci.cancel();
        }
    }

}
