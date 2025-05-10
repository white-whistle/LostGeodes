package com.bajookie.lost_geodes;

import com.bajookie.lost_geodes.item.ILeftClickAbility;
import com.bajookie.lost_geodes.item.ability.IHasSlotAbility;
import com.bajookie.lost_geodes.system.Raid.networking.c2s.C2SCastItemStack;
import com.bajookie.lost_geodes.system.Raid.networking.c2s.C2SSyncItemCooldown;
import com.bajookie.lost_geodes.system.Raid.networking.c2s.RequestCapabilitySync;
import com.bajookie.lost_geodes.system.Raid.networking.c2s.RequestLeftClickAbilitySync;
import com.bajookie.lost_geodes.system.Raid.networking.s2c.CapabilitySync;
import com.bajookie.lost_geodes.util.EntityUtil;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ServerNetworking {
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(RequestCapabilitySync.TYPE, (packet, player, responseSender) -> {
            var world = player.getWorld();
            var entity = EntityUtil.getEntityByUUID(world, packet.entityUuid());

            if (entity instanceof LivingEntity livingEntity) {
                CapabilitySync.send(player, livingEntity);
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(RequestLeftClickAbilitySync.TYPE, (packet, player, responseSender) -> {
            World world = player.getWorld();
            ItemStack stack = packet.stack();
            ((ILeftClickAbility) stack.getItem()).performLeftClickAbility(stack, world, player);
        });

        ServerPlayNetworking.registerGlobalReceiver(C2SSyncItemCooldown.TYPE, (packet, player, responseSender) -> {
            player.getItemCooldownManager().set(packet.item(), packet.duration());
        });

        ServerPlayNetworking.registerGlobalReceiver(C2SCastItemStack.TYPE, (packet, player, responseSender) -> {
            IHasSlotAbility.handleCast(player, packet.slot());
        });
    }
}
