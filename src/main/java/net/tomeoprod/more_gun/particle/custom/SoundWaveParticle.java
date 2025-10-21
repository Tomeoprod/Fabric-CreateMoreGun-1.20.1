package net.tomeoprod.more_gun.particle.custom;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.tomeoprod.more_gun.MoreGun;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.awt.*;
import java.util.Random;

public class SoundWaveParticle extends SpriteBillboardParticle {
    private float maxSize;

    SoundWaveParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.velocityX = 0F;
        this.velocityY = 0F;
        this.velocityZ = 0F;
        this.red = 1F;
        this.green = 1F;
        this.blue = 0F;
        this.scale *= 3F;
        this.maxSize = 64F;
        this.maxAge = 40;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d vec3d = camera.getPos();
        float f = (float)(MathHelper.lerp(tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float)(MathHelper.lerp(tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float)(MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
        Quaternionf quaternionf;
        if (this.angle == 0.0F) {
            quaternionf = new Quaternionf(eulerToQuaternion(90, 0, 0));;
        } else {
            quaternionf = new Quaternionf(eulerToQuaternion(90, 0, 0));
            quaternionf.rotateZ(MathHelper.lerp(tickDelta, this.prevAngle, this.angle));
        }

        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float i = this.getSize(tickDelta);

        for(int j = 0; j < 4; ++j) {
            Vector3f vector3f = vector3fs[j];
            vector3f.rotate(quaternionf);
            vector3f.mul(i);
            vector3f.add(f, g, h);
        }

        float k = this.getMinU();
        float l = this.getMaxU();
        float m = this.getMinV();
        float n = this.getMaxV();
        int o = 255;
        vertexConsumer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(l, n).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(l, m).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(k, m).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(k, n).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(l, n).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(k, n).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(k, m).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(l, m).color(this.red, this.green, this.blue, this.alpha).light(o).next();
    }

    public Quaternionf eulerToQuaternion(float x, float y, float z) {
        x *= ((float) Math.PI / 180F);
        y *= ((float) Math.PI / 180F);
        z *= ((float) Math.PI / 180F);

        float f = MathHelper.sin(0.5F * x);
        float g = MathHelper.cos(0.5F * x);
        float h = MathHelper.sin(0.5F * y);
        float i = MathHelper.cos(0.5F * y);
        float j = MathHelper.sin(0.5F * z);
        float k = MathHelper.cos(0.5F * z);
        x = f * i * k + g * h * j;
        y = g * h * k - f * i * j;
        z = f * h * k + g * i * j;
        float w = g * i * k - f * h * j;

        return new Quaternionf(x, y, z, w);
    }

    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    @Override
    public void tick() {
        super.tick();

        //code form of the equation f(x,y,z) = -((z/(y/2))/(y/2)) * (x-y/2)² + z (without lerp and realMaxSize)
        float realMaxSize = this.maxSize + 17.75f;
        int halfAge = this.maxAge / 2;
        float a = -((realMaxSize/halfAge)/halfAge);
        float xAlphaSqr = (this.age - halfAge) * (this.age - halfAge);
        float beta = realMaxSize;
        float delta = 0.4F;

        this.scale = MathHelper.lerp(delta, this.scale, a * xAlphaSqr + beta);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SoundWaveParticle(clientWorld, d, e, f, this.spriteProvider);
        }
    }
}
