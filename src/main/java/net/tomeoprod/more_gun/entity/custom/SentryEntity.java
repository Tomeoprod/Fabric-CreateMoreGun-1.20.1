package net.tomeoprod.more_gun.entity.custom;

import com.simibubi.create.content.equipment.wrench.WrenchItem;
import com.simibubi.create.foundation.utility.CreateLang;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.tomeoprod.more_gun.Item.MGItems;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.entity.MGEntities;
import net.tomeoprod.more_gun.networking.MGMessages;
import net.tomeoprod.more_gun.particle.MGParticles;
import net.tomeoprod.more_gun.sound.MGSounds;
import net.tomeoprod.more_gun.util.TF2Utils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class SentryEntity extends BuildingBoxEntity {
    public Entity target;
    private boolean hadTarget = false;
    private boolean canShoot = false;
    private boolean lookingRight = true;

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

            case 2 -> new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 1.5, this.getZ() - 0.5);

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
    public SoundEvent getDeployingSound() {
        return MGSounds.SENTRY_DEPLOYING;
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

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        World world = player.getWorld();

        if (player.isSneaking()) {
            ItemStack stack = new ItemStack(MGItems.BUILDING_BOX);
            TF2Utils.setBuildingItemProperties(stack, this.getBuildingType(), this.getBuildingLevel(), this.getHealth(), this.getAmmo(), this.getRockets());

            ItemEntity item = new ItemEntity(
                    world,
                    this.getX(),
                    this.getY() + 0.5,
                    this.getZ(),
                    stack
            );

            world.spawnEntity(item);
            this.discard();
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        World world = this.getWorld();
        Entity attacker = source.getAttacker();

        boolean isDeployed = this.getDeployed() > 0;

        if (attacker instanceof PlayerEntity player && isDeployed) {
            boolean needsAmmo = 150 - this.getAmmo() != 0;
            boolean hitWithWrench = player.getMainHandStack().getItem() instanceof WrenchItem;
            boolean playerHasBullets = player.getInventory().contains(MGItems.BULLET.getDefaultStack());

            if (needsAmmo && hitWithWrench && playerHasBullets) {
                int bullet = MathHelper.clamp(
                        player.getInventory().count(MGItems.BULLET),
                        0,
                        MathHelper.clamp(150 - this.getAmmo(), 0, 40));

                world.playSoundAtBlockCenter(this.getBlockPos(), TF2Utils.getRandomWrenchSound(), SoundCategory.PLAYERS, 0.5F, 1F, true);

                this.setAmmo(this.getAmmo() + bullet);
                if (!player.isCreative()) {
                    player.getInventory().remove(stack -> stack.isOf(MGItems.BULLET), bullet, player.playerScreenHandler.getCraftingInput());
                }
            }
        }

        return super.damage(source, amount);
    }

    @Override
    public void tick() {
        super.tick();

        canShoot = this.age % 5 == 0;

        this.calculateRotation();

        if (this.getDeployed() > 0) {
            this.shootTarget();
            this.searchTarget();
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

        if (this.target != null) {
            if (!this.hadTarget) {
                world.playSoundAtBlockCenter(this.getBlockPos(), MGSounds.SENTRY_SPOT_1, SoundCategory.NEUTRAL, 0.1f, 1f, true);
            }
            this.hadTarget = true;
        } else this.hadTarget = false;
    }

    public void calculateRotation() {
        World world = this.getWorld();

        if (this.getDeployed() <= 0) {
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

            float newYaw = MathHelper.stepTowards(
                    this.getYaw(),
                    targetYaw,
                    11.25F
            );

            this.setYaw(newYaw);
            this.setHeadYaw(newYaw);

            this.setPitch(MathHelper.stepTowards(
                    this.getPitch(),
                    targetPitch,
                    4.0F
            ));

        } else {
            float centerYaw = this.getBuildingRotation();

            float targetYaw = lookingRight
                    ? centerYaw + 45F
                    : centerYaw - 45F;

            float newYaw = MathHelper.stepTowards(
                    this.getYaw(),
                    targetYaw,
                    2F
            );

            this.setYaw(newYaw);
            this.setHeadYaw(newYaw);

            this.setPitch(MathHelper.stepTowards(
                    this.getPitch(),
                    0,
                    1.0F
            ));

            if (Math.abs(MathHelper.wrapDegrees(targetYaw - newYaw)) < 0.5F) {
                world.playSoundAtBlockCenter(this.getBlockPos(), MGSounds.SENTRY_SEARCH_1, SoundCategory.NEUTRAL, 0.25f, 1f, true);
                lookingRight = !lookingRight;
            }
        }
    }

    public void shootTarget() {
        World world = this.getWorld();

        if (target != null && getDeployed() > 0 && canShoot) {
            float xzAngle = (float) Math.toRadians(this.getYaw() + 90);
            float yAngle = (float) Math.toRadians(-this.getPitch());
            float d = 20;

            Vec3d start = this.getEyePos();
            Vec3d end = this.getEyePos().add(d * Math.cos(xzAngle), d * Math.sin(yAngle), d * Math.sin(xzAngle));

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
                                    && !(entity instanceof BuildingBoxEntity),
                    start.squaredDistanceTo(end)
            );

            if (this.getAmmo() > 0) {
                if (entityHit != null && entityHit.getType() != HitResult.Type.MISS) {
                    if (entityHit.getEntity() instanceof LivingEntity entity) {
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

                        if (entity.canTakeDamage()) {
                            PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                            passedData.writeInt(entity.getId());
                            passedData.writeVector3f(this.getRotationVec(1.0F).toVector3f());

                            if (this.getWorld().isClient && MGMessages.SHOOT_ENTITY_PACKET_ID != null) {
                                ClientPlayNetworking.send(MGMessages.SHOOT_ENTITY_PACKET_ID, passedData);
                            }
                        }
                    }
                }
                this.setAmmo(this.getAmmo() - 1);

            } else {
                shootAnimationState.startIfNotRunning(this.age);
                world.playSoundAtBlockCenter(this.getBlockPos(), MGSounds.SENTRY_SHOOT_EMPTY, SoundCategory.NEUTRAL, 0.25f, 1f, true);
            }
        } else shootAnimationState.stop();

    }

    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        float health = this.getHealth();
        float maxHealth = this.getMaxHealth();

        int ammo = this.getAmmo();
        int maxAmmo = 150;

        int rockets = this.getRockets();
        int maxRockets = 0;

        Formatting healthColor = TF2Utils.getStatColor(health, maxHealth);
        Formatting ammoColor = TF2Utils.getStatColor(ammo, maxAmmo);
        Formatting rocketsColor = TF2Utils.getStatColor(rockets, maxRockets);

        switch (this.getBuildingLevel()) {
            case 2 -> maxAmmo = 200;
            case 3 -> {
                maxAmmo = 200;
                maxRockets = 20 ;
            }
        }

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