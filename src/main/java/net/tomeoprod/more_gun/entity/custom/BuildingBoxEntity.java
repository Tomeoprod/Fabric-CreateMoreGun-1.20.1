package net.tomeoprod.more_gun.entity.custom;

import com.simibubi.create.content.equipment.wrench.WrenchItem;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.tomeoprod.more_gun.Item.MGItems;
import net.tomeoprod.more_gun.Item.custom.BuildingBoxItem;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.entity.MGEntities;
import net.tomeoprod.more_gun.networking.MGMessages;
import net.tomeoprod.more_gun.particle.MGParticles;
import net.tomeoprod.more_gun.sound.MGSounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BuildingBoxEntity extends MobEntity {
    public boolean ponderEntity = false;
    public Entity target;
    private boolean oddTick = false;
    public final AnimationState deployAnimationState = new AnimationState();
    public final AnimationState searchAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();
    private static final TrackedData<Boolean> SEARCHING = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DEPLOYED = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DEPLOYING = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> OWNER_ID = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<String> BUILDING_TYPE = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> BUILDING_LEVEL = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> BUILDING_ROTATION = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public BuildingBoxEntity(World world) {
        super(MGEntities.BUILDING_BOX_ENTITY_TYPE, world);
    }

    public BuildingBoxEntity(EntityType<? extends BuildingBoxEntity> buildingBoxEntityEntityType, World world) {
        super(buildingBoxEntityEntityType, world);
    }

    public static DefaultAttributeContainer.Builder createBuildingBoxAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 500);
    }

    @Override
    protected Box calculateBoundingBox() {
        if (!getDeployed()) {
            return new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 0.6, this.getZ() - 0.5);
        }

        return switch (getBuildingLevel()) {
            case 2 -> new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 1.5, this.getZ() - 0.5);

            case 3 -> new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 1.8, this.getZ() - 0.5);

            default -> new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 1.2, this.getZ() - 0.5);
        };

    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        dataTracker.startTracking(SEARCHING, false);
        dataTracker.startTracking(DEPLOYED, false);
        dataTracker.startTracking(DEPLOYING, false);
        dataTracker.startTracking(OWNER_ID, 0);
        dataTracker.startTracking(BUILDING_TYPE, "");
        dataTracker.startTracking(BUILDING_LEVEL, 0);
        dataTracker.startTracking(BUILDING_ROTATION, 0.0f);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putBoolean("more_gun.searching", getSearching());
        nbt.putBoolean("more_gun.deployed", getDeployed());
        nbt.putBoolean("more_gun.deploying", getDeploying());
        nbt.putInt("more_gun.ownerId", getOwnerId());
        nbt.putString("more_gun.buildingType", getBuildingType());
        nbt.putInt("more_gun.buildingLevel", getBuildingLevel());
        nbt.putFloat("more_gun.buildingRotation", getBuildingRotation());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("more_gun.searching")) setSearching(nbt.getBoolean("more_gun.searching"));
        if (nbt.contains("more_gun.deployed")) setDeployed(nbt.getBoolean("more_gun.deployed"));
        if (nbt.contains("more_gun.deploying")) setDeploying(nbt.getBoolean("more_gun.deploying"));
        if (nbt.contains("more_gun.ownerId")) setOwnerId(nbt.getInt("more_gun.ownerId"));
        if (nbt.contains("more_gun.buildingType")) setBuildingType(nbt.getString("more_gun.buildingType"));
        if (nbt.contains("more_gun.buildingLevel")) setBuildingLevel(nbt.getInt("more_gun.buildingLevel"));
        if (nbt.contains("more_gun.buildingRotation")) setBuildingRotation(nbt.getFloat("more_gun.buildingRotation"));
    }

    public void setSearching(boolean searching) {
        dataTracker.set(SEARCHING, searching);
    }
    public boolean getSearching() {
        return dataTracker.get(SEARCHING);
    }

    public void setDeployed(boolean deployed) {
        dataTracker.set(DEPLOYED, deployed);
    }
    public boolean getDeployed() {
        return dataTracker.get(DEPLOYED);
    }

    public void setDeploying(boolean deploying) {
        dataTracker.set(DEPLOYING, deploying);
    }
    public boolean getDeploying() {
        return dataTracker.get(DEPLOYING);
    }

    public void setOwnerId(int playerId) {
        dataTracker.set(OWNER_ID, playerId);
    }
    public int getOwnerId() {
        return dataTracker.get(OWNER_ID);
    }

    public void setBuildingType (String type) {
        dataTracker.set(BUILDING_TYPE, type);
    }
    public String getBuildingType() {
        return dataTracker.get(BUILDING_TYPE);
    }

    public void setBuildingLevel(int level) {
        dataTracker.set(BUILDING_LEVEL, level);
    }
    public int getBuildingLevel() {
        return dataTracker.get(BUILDING_LEVEL);
    }

    public void setBuildingRotation(float rotation) {
        dataTracker.set(BUILDING_ROTATION, rotation);
    }
    public float getBuildingRotation() {
        return dataTracker.get(BUILDING_ROTATION);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    public void setupAnimationStates() {
        if (!this.ponderEntity) {
            if (this.getDeploying()) {
                if (!this.deployAnimationState.isRunning() && !getDeployed()) {
                    this.getWorld().playSoundAtBlockCenter(this.getBlockPos(), MGSounds.SENTRY_DEPLOYING, SoundCategory.NEUTRAL, 0.5f, 1f, true);
                    for (int i = 0; i < 25; i++) {
                        this.getWorld().addImportantParticle(ParticleTypes.SPIT, true, this.getX(), this.getY(), this.getZ(), new Random().nextFloat(-0.1f, 0.1f), 0.2, new Random().nextFloat(-0.1f, 0.1f));
                    }
                }
                this.deployAnimationState.startIfNotRunning(this.age);
            }

            if (this.deployAnimationState.getTimeRunning() >= 6300) {
                setDeployed(true);
                setSearching(this.getDeployed() && this.target == null);
                setDeploying(false);
            }

            if (this.getSearching()) {
                this.searchAnimationState.startIfNotRunning(this.age);
            } else this.searchAnimationState.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();
        oddTick = !oddTick;

        this.setupAnimationStates();
        this.calculateDimensions();
        this.calculateRotation();
        this.shootTarget();
        this.searchTarget();

        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        IntList list = IntList.of(
                this.getId(),
                this.getDeployed() ? 1 : 0,
                this.getDeployed() ? 1 : 0,
                this.getSearching() ? 1 : 0
        );
        passedData.writeIntList(list);

        if (this.getWorld().isClient) {
            ClientPlayNetworking.send(MGMessages.SET_DATA_TRACKERS_PACKET_ID, passedData);
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

        List<HostileEntity> potentialTargets = world.getEntitiesByClass(HostileEntity.class, box, entity -> entity.isAlive());
        HostileEntity closet = null;

        for (HostileEntity target : potentialTargets) {
            BlockHitResult hitResult = world.raycast(new RaycastContext(this.getEyePos(), target.getBoundingBox().getCenter(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
            HitResult.Type hitType = hitResult.getType();
            BlockPos hitPos= hitResult.getBlockPos();

            if ((hitType == HitResult.Type.BLOCK && world.getBlockState(hitPos).isTransparent(world, hitPos)) || hitType == HitResult.Type.MISS) {
                Vec3d d1 = target.getPos().subtract(this.getPos());
                if (closet == null) {
                    closet = target;
                } else {
                    Vec3d d2 = closet.getPos().subtract(this.getPos());
                    if (d1.length() < d2.length()) {
                        closet = target;
                    }
                }
            }

        }

        this.target = closet;
    }

    public void calculateRotation() {
        if (this.target != null) {
            double dx = this.target.getX() - this.getX();
            double dy = this.target.getBoundingBox().getCenter().y - this.getEyeY();
            double dz = this.target.getZ() - this.getZ();
            double horizontalDist = Math.sqrt(dx * dx + dz * dz);

            float targetYaw = (float) (MathHelper.atan2(dz, dx) * (180.0 / Math.PI) - 90);
            float targetPitch = Math.clamp((float) -(MathHelper.atan2(dy, horizontalDist) * (180.0 / Math.PI)), -35, 35);

            this.setYaw(MathHelper.lerpAngleDegrees(0.2F, this.getYaw(),targetYaw));
            this.setHeadYaw(MathHelper.lerpAngleDegrees(0.2F, this.getYaw(),targetYaw));
            this.setPitch(MathHelper.lerp(0.2F, this.getPitch(), targetPitch));
        }
    }

    public void shootTarget() {
        World world = this.getWorld();

        if (target != null && getDeployed() && oddTick) {
            Vec3d vec3d2 = this.getEyePos().add(this.getRotationVec(1.0F).multiply(20)).subtract(this.getPos());
            Vec3d vec3d3 = vec3d2.normalize();
            List<LivingEntity> potentialTargets = new ArrayList<>();

            for (int i = 1; i <= 16; i++) {
                Vec3d vec3d4 = this.getPos().add(vec3d3.multiply(i));
                Box box = new Box(
                        vec3d4.x + 0.5,
                        vec3d4.y + 0.5,
                        vec3d4.z + 0.5,
                        vec3d4.x - 0.5,
                        vec3d4.y - 0.5,
                        vec3d4.z - 0.5
                );
                List<LivingEntity> temp = world.getEntitiesByClass(LivingEntity.class, box, entity -> !(entity instanceof BuildingBoxEntity) && entity != world.getEntityById(getOwnerId()));

                potentialTargets.addAll(temp);
            }

            if (!potentialTargets.isEmpty()) {
                shootAnimationState.startIfNotRunning(this.age);
                Vec3d particleSpawnPos = this.getEyePos().add(this.getRotationVec(1.0F).multiply(0.65));
                world.addImportantParticle(
                        (ParticleEffect) MGParticles.MUZZLE_FLASH_PARTICLE,
                        particleSpawnPos.x + new Random().nextFloat(-0.05f, 0.05f),
                        particleSpawnPos.y + 0.1 + new Random().nextFloat(-0.05f, 0.05f),
                        particleSpawnPos.z + new Random().nextFloat(-0.05f, 0.05f),
                        0,
                        0,
                        0
                );
                world.playSoundAtBlockCenter(this.getBlockPos(), MGSounds.SENTRY_SHOOT_1, SoundCategory.NEUTRAL, 0.1f, 1f, true);
            }

            for (LivingEntity entity : potentialTargets) {
                if (entity.canTakeDamage()) {
                    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                    passedData.writeInt(entity.getId());
                    passedData.writeVector3f(vec3d3.toVector3f());

                    ClientPlayNetworking.send(MGMessages.SHOOT_ENTITY_PACKET_ID, passedData);
                }
            }
        } else if (!this.ponderEntity) shootAnimationState.stop();
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        if (player.isSneaking()) {
            ItemStack stack = new ItemStack(MGItems.BUILDING_BOX);
            BuildingBoxItem.setBuildingProperties(stack, this.getBuildingType(), this.getBuildingLevel());

            player.giveItemStack(stack);
            this.discard();
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public boolean canTakeDamage() {
        return true;
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        MoreGun.LOGGER.info("-> attacker : " + damageSource.getAttacker());
        if (damageSource.getAttacker() instanceof PlayerEntity player) {
            MoreGun.LOGGER.info("-> item : " + player.getMainHandStack().getItem());
            MoreGun.LOGGER.info("-> deploying : " + getDeploying());
            MoreGun.LOGGER.info("-> deployed : " + getDeployed());
            if (player.getMainHandStack().getItem() instanceof WrenchItem && !(this.getDeployed() || this.getDeploying())) {
                MoreGun.LOGGER.info("-> worked");
                this.setDeploying(true);
            }
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
