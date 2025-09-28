package net.tomeoprod.more_gun.particle.custom;

import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

import java.util.Random;

public class MuzzleFlashParticle extends AnimatedParticle {
    MuzzleFlashParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0125F);
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
        this.angle = new Random().nextFloat(-45f, 45f);
        this.scale *= 1.15F;
        this.maxAge = 2 + this.random.nextInt(6);
        this.setTargetColor(15916745);
        this.setSpriteForAge(spriteProvider);
    }

    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new MuzzleFlashParticle(clientWorld, d, e, f, this.spriteProvider);
        }
    }
}
