package com.bajookie.lost_geodes.effects;

import com.bajookie.lost_geodes.particles.ModParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class ElectricStunEffect extends StatusEffect {
    public ElectricStunEffect() {
        super(StatusEffectCategory.HARMFUL, 0x000000);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributeContainer, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 2, 10, true, false), entity.getLastAttacker());
        super.onApplied(entity, attributeContainer, amplifier);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        World world = entity.getWorld();
        Vec3d entityPos = entity.getPos();
        Random r = new Random();
        if (!world.isClient) {
            ((ServerWorld) world).spawnParticles(ModParticles.ELECTRIC_SHOCK, entityPos.x, entityPos.y + 0.75, entityPos.z, 1, r.nextInt(-5, 5) * 0.1, r.nextInt(3, 5) * 0.1, r.nextInt(-5, 5) * 0.1, 0.5);

        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 50 >> 10;
        if (i > 0) {
            return duration % i == 0;
        }
        return true;
    }
}
