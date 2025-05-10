package com.bajookie.lost_geodes.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.function.BiConsumer;

public abstract class DelayedEffect extends StatusEffect implements IRemoveEffect {
    public DelayedEffect(StatusEffectCategory category) {
        super(category, 0x000000);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }

    public static DelayedEffect create(StatusEffectCategory category, BiConsumer<StatusEffectInstance, LivingEntity> onRemoveConsumer) {
        return new DelayedEffect(category) {
            @Override
            public void onRemoved(StatusEffectInstance effectInstance, LivingEntity entity) {
                onRemoveConsumer.accept(effectInstance, entity);
            }
        };
    }
    public static DelayedEffect create(StatusEffectCategory category, BiConsumer<StatusEffectInstance, LivingEntity> onRemoveConsumer, BiConsumer<LivingEntity,Integer> onApplyConsumer){
        return new DelayedEffect(category) {
            @Override
            public void onRemoved(StatusEffectInstance effectInstance, LivingEntity entity) {
                onRemoveConsumer.accept(effectInstance, entity);
            }

            @Override
            public void onApplied(LivingEntity entity, AttributeContainer attributeContainer, int amplifier) {
                onApplyConsumer.accept(entity,amplifier);
                super.onApplied(entity, attributeContainer , amplifier);
            }
        };
    }
}
