package com.bajookie.lost_geodes.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public interface IRemoveEffect {
    void onRemoved(StatusEffectInstance effectInstance, LivingEntity entity);
}
