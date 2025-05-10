package com.bajookie.lost_geodes.mixin;

import com.bajookie.lost_geodes.item.ModItems;
import com.bajookie.lost_geodes.item.custom.Cowplate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "setSneaking", at = @At("HEAD"), cancellable = true)
    private void handleSneakingStart(boolean sneaking, CallbackInfo ci) {
        if (!sneaking) return;

        var entity = (Entity) (Object) this;


        if (entity instanceof PlayerEntity player) {
            var stack = player.getInventory().getArmorStack(EquipmentSlot.FEET.getEntitySlotId());

            if (stack.isOf(ModItems.GUNHEELS)) {
                if (ModItems.GUNHEELS.doBulletjump(player, stack)) {
                    ci.cancel();
                }

            }
        }
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void onInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        var self = (Entity) (Object) this;

        if (self instanceof LivingEntity target) {
            var chestStack = target.getEquippedStack(EquipmentSlot.CHEST);
            if (chestStack.getItem() instanceof Cowplate cowplate) {
                if (cowplate.handleBucket(chestStack, player, hand)) {
                    cir.setReturnValue(ActionResult.success(target.getWorld().isClient));
                }
            }
        }
    }

}
