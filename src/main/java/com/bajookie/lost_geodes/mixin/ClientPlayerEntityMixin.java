package com.bajookie.lost_geodes.mixin;

import com.bajookie.lost_geodes.client.animation.AnimationUtil;
import com.bajookie.lost_geodes.item.ModItems;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void tick(CallbackInfo ci) {
        AnimationUtil.tick();
    }

    @Inject(method = "sendMovementPackets", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;lastSneaking:Z", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    public void onClientStartSneaking(CallbackInfo ci) {
        var player = (ClientPlayerEntity) (Object) this;

        var stack = player.getInventory().getArmorStack(EquipmentSlot.FEET.getEntitySlotId());

        if (stack.isOf(ModItems.GUNHEELS)) {
            ModItems.GUNHEELS.doBulletjump(player, stack);
        }
    }
}
