package net.tomeoprod.more_gun.entity.custom;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.utility.CreateLang;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.tomeoprod.more_gun.Item.MGItems;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.entity.MGEntities;
import net.tomeoprod.more_gun.networking.MGMessages;
import net.tomeoprod.more_gun.sound.MGSounds;
import net.tomeoprod.more_gun.sound.instances.SentrySearchSoundInstance;
import net.tomeoprod.more_gun.util.AngleUtils;
import net.tomeoprod.more_gun.util.TF2Utils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SentryEntity extends BuildingBoxEntity {
    public Entity target;
    private boolean hadTarget = false;
    private boolean canShoot = false;
    public boolean lookingRight = true;

    public final AnimationState shootAnimationState = new AnimationState();

    private static final TrackedData<Integer> AMMO = DataTracker.registerData(SentryEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> ROCKETS = DataTracker.registerData(SentryEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public SentryEntity(EntityType<? extends BuildingBoxEntity> buildingBoxEntityEntityType, World world) {
        super(buildingBoxEntityEntityType, world);
    }

    public SentryEntity(World world) {
        super(MGEntities.SENTRY_BOX_ENTITY_TYPE, world);
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(AMMO, 0);
        dataTracker.startTracking(ROCKETS, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("more_gun.ammo", getAmmo());
        nbt.putInt("more_gun.rockets", getRockets());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("more_gun.ammo")) setAmmo(nbt.getInt("more_gun.ammo"));
        if (nbt.contains("more_gun.rockets")) setRockets(nbt.getInt("more_gun.rockets"));
    }

    @Override
    protected Box calculateBoundingBox() {
        return switch (this.getDeployed()) {
            case 1 -> new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 1.2, this.getZ() - 0.5);

            case 2 -> new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 1.3, this.getZ() - 0.5);

            case 3 -> new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 1.8, this.getZ() - 0.5);

            default -> new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 0.6, this.getZ() - 0.5);
        };


    }

    public void setAmmo(int ammo) {
        dataTracker.set(AMMO, ammo);
    }
    public int getAmmo() {
        return dataTracker.get(AMMO);
    }

    public void setRockets(int rockets) {
        dataTracker.set(ROCKETS, rockets);
    }
    public int getRockets() {
        return dataTracker.get(ROCKETS);
    }

    @Override
    public Item getUpgradeItem() {
        if (this.getBuildingLevel() == 1) {
            return switch (this.getUpgradeProgress()) {
                case 1,3 -> AllBlocks.INDUSTRIAL_IRON_BLOCK.asItem();
                case 4 -> AllItems.PRECISION_MECHANISM.asItem();
                default -> AllBlocks.BRASS_BLOCK.asItem();
            };

        }

        return null;
    }

    @Override
    public int getMaxUpgradeProgress() {
        return switch (this.getBuildingLevel()) {
            case 1 -> 5;
            default -> 0;
        };
    }

    @Override
    public int getMaxBuildingModes() {
        return 3;
    }

    @Override
    public Text getBuildingModeMessage(int mode) {
        MutableText message;

        switch (mode) {
            case 1 -> message = Text.translatable("more_gun.building_mode.sentry.1");
            case 2 -> message = Text.translatable("more_gun.building_mode.sentry.2");
            case 3 -> message = Text.translatable("more_gun.building_mode.sentry.3");
            default -> message = Text.translatable("more_gun.building_mode.sentry.0");
        }

        return message;
    }

    @Override
    public SoundEvent getDeployingSound() {
        return switch (this.getDeploying()) {
            case 2 -> MGSounds.SENTRY_DEPLOYING_LV2;
            default -> MGSounds.SENTRY_DEPLOYING_LV1;
        };
    }

    @Override
    public int getMaxAnimationTime() {
        int time;

        switch (this.getDeployed()) {
            case 1 -> time = 1500;
            default -> time = 7000;
        }

        return time;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return MGSounds.SENTRY_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return MGSounds.SENTRY_EXPLODE;
    }

    @Override
    public ItemStack getAsItem() {
        ItemStack stack = new ItemStack(MGItems.BUILDING_BOX);
        TF2Utils.setBuildingItemProperties(stack, this.getBuildingType(), this.getBuildingLevel(), this.getAmmo(), this.getRockets());
        return stack;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getBuildingLevel() == 1) {
            canShoot = this.age % 5 == 0;
        } else canShoot = this.age  % 3 == 0;

        if (this.getBuildingMode() != 3) {
            this.calculateRotation();
            if (this.getDeployed() == this.getDeploying() && this.getDeployed() > 0) {
                this.shootTarget();
                this.searchTarget();
            }
        }
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
        List<? extends LivingEntity> potentialTargets;
        switch (this.getBuildingMode()) {
            case 1 -> potentialTargets = world.getEntitiesByClass(PlayerEntity.class, box, entity -> entity.isAlive() && !entity.getUuidAsString().equals(this.getOwnerId()) && !entity.isCreative());
            case 2 -> potentialTargets = world.getEntitiesByClass(LivingEntity.class, box, entity -> {
                boolean isCreative = false;
                boolean isOwner = false;
                if (entity instanceof PlayerEntity player) {
                    isCreative = player.isCreative();
                    isOwner = entity.getUuidAsString().equals(this.getOwnerId());
                }
                return !isOwner && !isCreative && entity.isAlive() && (entity instanceof PlayerEntity || entity instanceof HostileEntity);
            });
            default -> potentialTargets = world.getEntitiesByClass(HostileEntity.class, box, LivingEntity::isAlive);
        }
        LivingEntity closest = null;

        for (LivingEntity target : potentialTargets) {
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

        if (this.target != null) {
            if (!this.hadTarget) {
                world.playSoundAtBlockCenter(this.getBlockPos(), MGSounds.SENTRY_SPOT_1, SoundCategory.NEUTRAL, 0.1f, 1f, true);
            }
            this.hadTarget = true;
        } else this.hadTarget = false;
    }

    public void calculateRotation() {
        if (this.getDeployed() != this.getDeploying() || this.getDeployed() == 0) {
            float yaw = this.getBuildingRotation();

            this.setYaw(yaw);
            this.setHeadYaw(yaw);
            this.setPitch(0);
            return;
        }

        if (target != null) {
            double dx = this.target.getX() - this.getX();
            double dy = this.target.getBoundingBox().getCenter().y - this.getEyeY();
            double dz = this.target.getZ() - this.getZ();

            double horizontalDist = Math.sqrt(dx * dx + dz * dz);

            float targetYaw = (float) (MathHelper.atan2(dz, dx) * (180F / Math.PI)) - 90F;

            float targetPitch = MathHelper.clamp(
                    (float) -(MathHelper.atan2(dy, horizontalDist) * (180F / Math.PI)),
                    -35,
                    35
            );

            float newYaw = MathHelper.stepUnwrappedAngleTowards(
                    this.getYaw(),
                    targetYaw,
                    11.25F
            );

            float newPitch = MathHelper.stepUnwrappedAngleTowards(
                    this.getPitch(),
                    targetPitch,
                    4.0F
            );

            this.setPitch(newPitch);
            this.setYaw(newYaw);
            this.setHeadYaw(newYaw);



        } else {
            float centerYaw = AngleUtils.getRangedAngle(this.getBuildingRotation());

            float targetYaw = lookingRight
                    ? centerYaw + 45F
                    : centerYaw - 45F;

            float newYaw;
            float newPitch;
            if (this.getYaw() > centerYaw + 45F || this.getYaw() < centerYaw - 45F) {
                newYaw = MathHelper.stepUnwrappedAngleTowards(
                        AngleUtils.getRangedAngle(this.getYaw()),
                        targetYaw,
                        5.F
                );

                newPitch = MathHelper.stepUnwrappedAngleTowards(
                        this.getPitch(),
                        0,
                        4.0F
                );
            } else {
                newYaw = MathHelper.stepUnwrappedAngleTowards(
                        AngleUtils.getRangedAngle(this.getYaw()),
                        targetYaw,
                        2F
                );

                newPitch = MathHelper.stepUnwrappedAngleTowards(
                        this.getPitch(),
                        0,
                        1.0F
                );
            }

            this.setPitch(newPitch);
            this.setYaw(newYaw);
            this.setHeadYaw(newYaw);

            if (Math.abs(MathHelper.wrapDegrees(targetYaw - newYaw)) < 0.5F) {
                if (this.getWorld().isClient) {
                    MinecraftClient.getInstance().getSoundManager().play(new SentrySearchSoundInstance(this, this.lookingRight));
                }
                this.lookingRight = !this.lookingRight;
            }
        }
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        if (this.getBuildingLevel() >= 2) {
            return 1.1f;
        }

        return 0.85f;
    }

    public void shootTarget() {
        World world = this.getWorld();

        if (target != null && getDeployed() > 0) {
            if (canShoot) {
                float d = 20;

                Vec3d start = this.getEyePos();
                Vec3d end = this.getEyePos().add(this.getRotationVec(1).multiply(d));

                HitResult blockHit = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));

                if (blockHit.getType() == HitResult.Type.BLOCK) {
                    end = blockHit.getPos();
                }

                Box searchBox = this.getBoundingBox()
                        .stretch(this.getRotationVec(1.0F).multiply(d))
                        .expand(1.0D);

                EntityHitResult entityHit = ProjectileUtil.raycast(
                        this,
                        start,
                        end,
                        searchBox,
                        entity ->
                                !entity.isSpectator()
                                        && entity.canHit()
                                        && !(entity instanceof BuildingBoxEntity)
                                        && entity == this.target,
                        start.squaredDistanceTo(end)
                );

                if (this.getAmmo() > 0) {
                    if (entityHit != null && entityHit.getType() != HitResult.Type.MISS) {
                        if (entityHit.getEntity() instanceof LivingEntity entity) {
                            shootAnimationState.startIfNotRunning(this.age);
                            TF2Utils.generateMuzzleFlash(world, this);
                            world.playSoundAtBlockCenter(this.getBlockPos(), MGSounds.SENTRY_SHOOT_1, SoundCategory.NEUTRAL, 0.1f, 1f, true);

                            if (entity.canTakeDamage()) {
                                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                                passedData.writeInt(entity.getId());
                                passedData.writeVector3f(this.getRotationVec(1.0F).toVector3f());

                                if (this.getWorld().isClient && MGMessages.SHOOT_ENTITY_PACKET_ID != null) {
                                    ClientPlayNetworking.send(MGMessages.SHOOT_ENTITY_PACKET_ID, passedData);
                                }
                            }
                            this.setAmmo(this.getAmmo() - 1);
                        }
                    }


                } else {
                    shootAnimationState.startIfNotRunning(this.age);
                    world.playSoundAtBlockCenter(this.getBlockPos(), MGSounds.SENTRY_SHOOT_EMPTY, SoundCategory.NEUTRAL, 0.25f, 1f, true);
                }
            } else if(this.getBuildingLevel() >= 2) {
                shootAnimationState.startIfNotRunning(this.age);
            } else shootAnimationState.stop();
        } else shootAnimationState.stop();

    }

    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        float health = this.getHealth();
        float maxHealth = this.getMaxHealth();

        int ammo = this.getAmmo();
        int maxAmmo = TF2Utils.getMaxAmmo(this.getBuildingLevel());

        int rockets = this.getRockets();
        int maxRockets = TF2Utils.getMaxRockets(this.getBuildingLevel());

        Formatting healthColor = TF2Utils.getStatColor(health, maxHealth);
        Formatting ammoColor = TF2Utils.getStatColor(ammo, maxAmmo);
        Formatting rocketsColor = TF2Utils.getStatColor(rockets, maxRockets);

        MutableText healthText = Text
                .translatable(MoreGun.MOD_ID + ".goggles.building.health")
                .append(String.valueOf(health)).formatted(healthColor)
                .append("§7/" + maxHealth);

        MutableText ammoText = Text
                .translatable(MoreGun.MOD_ID + ".goggles.building.ammo")
                .append(String.valueOf(ammo)).formatted(ammoColor)
                .append("§7/" + maxAmmo);

        MutableText rocketsText = Text
                .translatable(MoreGun.MOD_ID + ".goggles.building.rockets")
                .append(String.valueOf(rockets)).formatted(rocketsColor)
                .append("§7/" + maxRockets);

        CreateLang.builder().add(healthText.copy()).forGoggles(tooltip);
        CreateLang.builder().add(ammoText.copy()).forGoggles(tooltip);

        if (this.getBuildingLevel() == 3) {
            CreateLang.builder().add(rocketsText.copy()).forGoggles(tooltip);
        }

        return true;
    }
}