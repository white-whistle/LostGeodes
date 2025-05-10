package com.bajookie.lost_geodes.particles.type;

import com.bajookie.lost_geodes.particles.ZapParticleEffect;
import com.mojang.serialization.Codec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class ZapParticleType extends ParticleType<ZapParticleEffect> {
    public ZapParticleType(boolean alwaysShow, ParticleEffect.Factory<ZapParticleEffect> parametersFactory) {
        super(alwaysShow, parametersFactory);
    }

    @Override
    public Codec<ZapParticleEffect> getCodec() {
        return ZapParticleEffect.CODEC;
    }
}
