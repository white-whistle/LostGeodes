package com.bajookie.lost_geodes.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;

public class SecondSunParticle extends SpriteBillboardParticle {
    public SecondSunParticle(ClientWorld clientWorld, double xCoord, double yCoord, double zCoord,
                             SpriteProvider spriteProvider, double xd, double yd, double zd) {
        super(clientWorld, xCoord, yCoord, zCoord, xd, yd, zd);
        this.maxAge = 60;
        this.scale(3);
        this.setSpriteForAge(spriteProvider);

    }

    @Override
    public void tick() {
        super.tick();
        this.velocityY =0.2;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType>{
        private final SpriteProvider spriteProvider;
        public Factory(SpriteProvider spriteProvider){
            this.spriteProvider = spriteProvider;
        }
        public Particle createParticle(DefaultParticleType particleType,ClientWorld world,
                                       double x, double y,double z,double xd, double yd ,double zd){
            return new SecondSunParticle(world,x,y,z,this.spriteProvider,xd,yd,zd);
        }
    }
}
