package com.bajookie.lost_geodes.system.screen_switch;

import com.bajookie.lost_geodes.system.Capability.Capability;
import com.bajookie.lost_geodes.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ScreenSwitchCapability extends Capability<LivingEntity> {
    private UUID screenUser;
    public ScreenSwitchCapability(LivingEntity self) {
        super(self);
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        if (screenUser != null) {
            nbt.putUuid("screen_user", screenUser);
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        if (nbt.contains("screen_user")) {
            screenUser = nbt.getUuid("screen_user");
        }
    }
    @Nullable
    public Entity getTargetScreen(World world) {
        if (screenUser == null) return null;
        return EntityUtil.getEntityByUUID(world, screenUser);
    }
    public void setTargetScreen(UUID uuid){
        this.screenUser = uuid;
    }

    @Override
    public String toString() {
        NbtCompound compound = new NbtCompound();
        writeToNbt(compound);
        return compound.toString();
    }
}
