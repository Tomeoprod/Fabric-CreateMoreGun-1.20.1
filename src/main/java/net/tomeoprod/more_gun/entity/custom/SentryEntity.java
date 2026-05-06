package net.tomeoprod.more_gun.entity.custom;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.tomeoprod.more_gun.entity.MGEntities;
import net.tomeoprod.more_gun.networking.MGMessages;
import net.tomeoprod.more_gun.particle.MGParticles;
import net.tomeoprod.more_gun.sound.MGSounds;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SentryEntity extends BuildingBoxEntity{
    public Entity target;
    private boolean canShoot = false;

    public SentryEntity(EntityType<? extends BuildingBoxEntity> buildingBoxEntityEntityType, World world) {
        super(buildingBoxEntityEntityType, world);
    }

    public SentryEntity(World world) {
        super(MGEntities.SENTRY_BOX_ENTITY_TYPE, world);
    }

    @Override
    protected Box calculateBoundingBox() {
        return switch (this.getDeployed()) {
            case 1 -> new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 1.2, this.getZ() - 0.5);

            case 2 -> new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 1.5, this.getZ() - 0.5);

            case 3 -> new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 1.8, this.getZ() - 0.5);

            default -> new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 0.6, this.getZ() - 0.5);
        };

    }

    @Override
    public SoundEvent getDeployingSound() {
        return MGSounds.SENTRY_DEPLOYING;
    }

    public void searchTarget() {
        World world = this.getWorld();

        Box box =  new Box(
                this.getX() - 20,
                this.getY() - 20,
                this.getZ() - 20,
                this.getX() + 20,
                this.getY() + 20,
                this.getZ() + 20
        );

        List<HostileEntity> potentialTargets = world.getEntitiesByClass(HostileEntity.class, box, LivingEntity::isAlive);
        HostileEntity closest = null;

        for (HostileEntity target : potentialTargets) {
            BlockHitResult hitResult = world.raycast(new RaycastContext(this.getEyePos(), target.getBoundingBox().getCenter(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
            HitResult.Type hitType = hitResult.getType();
            BlockPos hitPos= hitResult.getBlockPos();

            if ((hitType == HitResult.Type.BLOCK && world.getBlockState(hitPos).isTransparent(world, hitPos)) || hitType == HitResult.Type.MISS) {
                Vec3d d1 = target.getPos().subtract(this.getPos());
                if (closest == null) {
                    closest = target;
                } else {
                    Vec3d d2 = closest.getPos().subtract(this.getPos());
                    if (d1.length() < d2.length()) {
                        closest = target;
                    }
                }
            }

        }

        this.target = closest;
    }

    public void calculateRotation() {
        if (this.getDeployed() > 0) {
            if (target != null) {
                double dx = this.target.getX() - this.getX();
                double dy = this.target.getBoundingBox().getCenter().y - this.getEyeY();
                double dz = this.target.getZ() - this.getZ();
                double horizontalDist = Math.sqrt(dx * dx + dz * dz);

                float targetYaw = (float) (MathHelper.atan2(dz, dx) * (180.0 / Math.PI) - 90);
                float targetPitch = MathHelper.clamp((float) -(MathHelper.atan2(dy, horizontalDist) * (180.0 / Math.PI)), -35, 35);

                this.setYaw(MathHelper.lerpAngleDegrees(0.2F, this.getYaw(), targetYaw));
                this.setHeadYaw(MathHelper.lerpAngleDegrees(0.2F, this.getYaw(), targetYaw));
                this.setPitch(MathHelper.lerp(0.2F, this.getPitch(), targetPitch));
            } else {
                boolean b1 = (Math.sin(this.age * 0.025)) >= 0;
                double d1 = Math.toRadians(this.getBuildingRotation() + (45 * (b1 ? 1 : -1)));

                float targetYaw = (float) (d1 * (180.0 / Math.PI));

                this.setYaw(MathHelper.lerpAngleDegrees(0.025F, this.getYaw(), targetYaw));
                this.setHeadYaw(MathHelper.lerpAngleDegrees(0.025F, this.getYaw(), targetYaw));
                this.setPitch(MathHelper.lerp(0.025F, this.getPitch(), 0));
            }
        } else {
            double d1 = Math.toRadians(this.getBuildingRotation());
            float targetYaw = (float) (d1 * (180.0 / Math.PI));

            this.setYaw(targetYaw);
            this.setHeadYaw(targetYaw);
            this.setPitch(0);
        }
    }

    public void shootTarget() {
        World world = this.getWorld();

        if (target != null && getDeployed() > 0 && canShoot) {
            Vec3d vec3d2 = target.getBoundingBox().getCenter().subtract(this.getEyePos());
            Vec3d vec3d3 = vec3d2.normalize();
            List<LivingEntity> potentialTargets = new ArrayList<>();

            for (int i = 1; i <= vec3d2.length() + 1; i++) {
                Vec3d vec3d4 = this.getPos().add(vec3d3.multiply(i));
                Box box = new Box(
                        vec3d4.x + 0.5,
                        vec3d4.y + 0.5,
                        vec3d4.z + 0.5,
                        vec3d4.x - 0.5,
                        vec3d4.y - 0.5,
                        vec3d4.z - 0.5
                );
                List<LivingEntity> temp = world.getEntitiesByClass(LivingEntity.class, box, entity -> !(entity instanceof BuildingBoxEntity));

                potentialTargets.addAll(temp);
            }

            if (!potentialTargets.isEmpty()) {
                shootAnimationState.startIfNotRunning(this.age);
                Vec3d particleSpawnPos = this.getEyePos().add(this.getRotationVec(1.0F).multiply(0.65));
                world.addImportantParticle(
                        MGParticles.MUZZLE_FLASH_PARTICLE,
                        particleSpawnPos.x,
                        particleSpawnPos.y + 0.1,
                        particleSpawnPos.z,
                        0,
                        0,
                        0
                );
                world.playSoundAtBlockCenter(this.getBlockPos(), MGSounds.SENTRY_SHOOT_1, SoundCategory.NEUTRAL, 0.1f, 1f, true);
                for (LivingEntity entity : potentialTargets) {
                    if (entity.canTakeDamage()) {
                        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                        passedData.writeInt(entity.getId());
                        passedData.writeVector3f(vec3d3.toVector3f());

                        if (this.getWorld().isClient && MGMessages.SHOOT_ENTITY_PACKET_ID != null) {
                            ClientPlayNetworking.send(MGMessages.SHOOT_ENTITY_PACKET_ID, passedData);
                        }
                    }
                }
            }
        } else shootAnimationState.stop();
    }

    @Override
    public void tick() {
        super.tick();

        canShoot = this.age % 5 == 0;

        this.calculateRotation();
        this.shootTarget();
        this.searchTarget();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return switch (new Random().nextInt(4)) {
            case 1 -> MGSounds.SENTRY_HURT_1;

            case 2 -> MGSounds.SENTRY_HURT_2;

            case 3 -> MGSounds.SENTRY_HURT_3;

            default -> MGSounds.SENTRY_HURT_4;
        };
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return MGSounds.SENTRY_EXPLODE;
    }
}
