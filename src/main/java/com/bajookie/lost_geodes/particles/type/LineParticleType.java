package com.bajookie.lost_geodes.particles.type;

import com.bajookie.lost_geodes.particles.LineParticleEffect;
import com.mojang.serialization.Codec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class LineParticleType extends ParticleType<LineParticleEffect> {

    public LineParticleType(boolean alwaysShow, ParticleEffect.Factory<LineParticleEffect> parametersFactory) {
        super(alwaysShow, parametersFactory);
    }

    @Override
    public Codec<LineParticleEffect> getCodec() {
        return LineParticleEffect.CODEC;
    }
}
