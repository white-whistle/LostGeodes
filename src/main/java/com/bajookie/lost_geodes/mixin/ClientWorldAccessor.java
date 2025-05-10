package com.bajookie.lost_geodes.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.world.entity.EntityLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClientWorld.class)
public interface ClientWorldAccessor {

    @Invoker("getEntityLookup")
    EntityLookup<Entity> invokeGetEntityLookup();
}
