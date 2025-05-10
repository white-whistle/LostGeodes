package com.bajookie.lost_geodes.system.Capability;

import net.minecraft.nbt.NbtCompound;

public abstract class Capability<T> {

    public T self;

    public Capability(T self) {
        this.self = self;
    }

    public abstract void writeToNbt(NbtCompound nbt);

    public abstract void readFromNbt(NbtCompound nbt);
}
