package com.bajookie.lost_geodes;

import com.bajookie.lost_geodes.system.Capability.Capabilities;
import com.bajookie.lost_geodes.system.Capability.IHasCapability;
import com.bajookie.lost_geodes.system.Raid.networking.c2s.RequestCapabilitySync;
import com.bajookie.lost_geodes.system.Raid.networking.s2c.CapabilitySync;
import com.bajookie.lost_geodes.system.Raid.networking.s2c.SyncSingleCapability;
import com.bajookie.lost_geodes.util.EntityUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.LivingEntity;

public class ClientNetworking {
    public static void init() {
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof LivingEntity livingEntity) {
                RequestCapabilitySync.send(livingEntity);
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(CapabilitySync.TYPE, ((packet, player, responseSender) -> {
            var world = player.getWorld();
            var entityToSync = EntityUtil.getEntityByUUID(world, packet.entityUuid());
            if (entityToSync instanceof IHasCapability iHasCapability && packet.capabilities() instanceof Capabilities.UnboundCapabilities uc) {
                iHasCapability.echoesOfTheElders$setCapabilities(uc.bind(entityToSync));
            }
        }));

        ClientPlayNetworking.registerGlobalReceiver(SyncSingleCapability.TYPE, ((packet, player, responseSender) -> {
            var world = player.getWorld();
            var entityToSync = EntityUtil.getEntityByUUID(world, packet.entityUuid());
            if (entityToSync instanceof IHasCapability iHasCapability) {
                iHasCapability.echoesOfTheElders$getCapabilities().put(packet.name(), packet.capability());
            }
        }));
    }
}
