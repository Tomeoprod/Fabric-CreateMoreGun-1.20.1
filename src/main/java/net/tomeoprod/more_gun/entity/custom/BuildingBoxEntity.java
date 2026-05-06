package net.tomeoprod.more_gun.entity.custom;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.wrench.WrenchItem;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tomeoprod.more_gun.Item.MGItems;
import net.tomeoprod.more_gun.Item.custom.BuildingBoxItem;
import net.tomeoprod.more_gun.networking.MGMessages;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public abstract class BuildingBoxEntity extends MobEntity {
    public final AnimationState deployAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();

    private static final TrackedData<Integer> DEPLOYED = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> DEPLOYING = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> OWNER_ID = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<String> BUILDING_TYPE = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> BUILDING_LEVEL = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> BUILDING_ROTATION = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public BuildingBoxEntity(EntityType<? extends BuildingBoxEntity> buildingBoxEntityEntityType, World world) {
        super(buildingBoxEntityEntityType, world);
    }

    public static DefaultAttributeContainer.Builder createBuildingBoxAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 500);
    }

    @Override
    protected Box calculateBoundingBox() {
        return new Box(this.getX() + 0.5, this.getY(), this.getZ() + 0.5, this.getX() - 0.5, this.getY() + 0.6, this.getZ() - 0.5);

    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        dataTracker.startTracking(DEPLOYED, 0);
        dataTracker.startTracking(DEPLOYING, false);
        dataTracker.startTracking(OWNER_ID, 0);
        dataTracker.startTracking(BUILDING_TYPE, "");
        dataTracker.startTracking(BUILDING_LEVEL, 0);
        dataTracker.startTracking(BUILDING_ROTATION, 0.0f);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("more_gun.deployed", getDeployed());
        nbt.putBoolean("more_gun.deploying", getDeploying());
        nbt.putInt("more_gun.ownerId", getOwnerId());
        nbt.putString("more_gun.buildingType", getBuildingType());
        nbt.putInt("more_gun.buildingLevel", getBuildingLevel());
        nbt.putFloat("more_gun.buildingRotation", getBuildingRotation());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("more_gun.deployed")) setDeployed(nbt.getInt("more_gun.deployed"));
        if (nbt.contains("more_gun.deploying")) setDeploying(nbt.getBoolean("more_gun.deploying"));
        if (nbt.contains("more_gun.ownerId")) setOwnerId(nbt.getInt("more_gun.ownerId"));
        if (nbt.contains("more_gun.buildingType")) setBuildingType(nbt.getString("more_gun.buildingType"));
        if (nbt.contains("more_gun.buildingLevel")) setBuildingLevel(nbt.getInt("more_gun.buildingLevel"));
        if (nbt.contains("more_gun.buildingRotation")) setBuildingRotation(nbt.getFloat("more_gun.buildingRotation"));
    }

    public void setDeployed(int deployed) {
        dataTracker.set(DEPLOYED, deployed);
    }
    public int getDeployed() {
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
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    public void setupAnimationStates() {
        if (this.getDeploying()) {
            if (!this.deployAnimationState.isRunning()) {
                this.getWorld().playSoundAtBlockCenter(this.getBlockPos(), this.getDeployingSound(), SoundCategory.NEUTRAL, 0.5f, 1f, true);
                for (int i = 0; i < 25; i++) {
                    this.getWorld().addImportantParticle(ParticleTypes.SPIT, true, this.getX(), this.getY(), this.getZ(), new Random().nextFloat(-0.1f, 0.1f), 0.2, new Random().nextFloat(-0.1f, 0.1f));
                }
            }
            this.deployAnimationState.startIfNotRunning(this.age);
        }

        if (this.deployAnimationState.getTimeRunning() >= 6300 && this.getBuildingLevel() != this.getDeployed()) {
            this.setDeployed(this.getDeployed() + 1);
            this.setDeploying(false);
        }

    }

    abstract public SoundEvent getDeployingSound();

    @Override
    public void tick() {
        super.tick();

        this.setupAnimationStates();
        this.calculateDimensions();

        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        IntList list = IntList.of(
                this.getId(),
                this.getDeploying() ? 1 : 0,
                this.getDeployed()
        );
        passedData.writeIntList(list);

        if (this.getWorld().isClient && MGMessages.SET_DATA_TRACKERS_PACKET_ID != null) {
            ClientPlayNetworking.send(MGMessages.SET_DATA_TRACKERS_PACKET_ID, passedData);
        }
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
    public boolean damage(DamageSource source, float amount) {
        World world = this.getWorld();
        Entity attacker = source.getAttacker();

        if (attacker instanceof PlayerEntity player) {
            if (player.getMainHandStack().getItem() instanceof WrenchItem) {
                if (!(this.getDeployed() > 0 || this.getDeploying())) {
                    this.setDeploying(true);
                    this.getBuildingLevel();
                    return false;
                } else if (player.getInventory().contains(AllItems.BRASS_INGOT.asStack()) && this.getHealth() != this.getMaxHealth()) {
                    this.getWorld().playSoundAtBlockCenter(this.getBlockPos(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS, 0.5F, 1F, true);
                    int healAmount = 5;

                    if (player.isSneaking()) {
                        if (player.getInventory().count(AllItems.BRASS_INGOT.asItem()) > 5) {
                            healAmount = (int) MathHelper.clamp(this.getMaxHealth() - this.getHealth(), 5, 25);
                        } else healAmount = player.getInventory().count(AllItems.BRASS_INGOT.asItem());
                    }

                    this.heal(healAmount);
                    if (!player.isCreative()) {
                        player.getInventory().getStack(
                                player.getInventory().indexOf(AllItems.BRASS_INGOT.asStack())
                        ).decrement(healAmount / 5);
                    }
                    if (this.getWorld() instanceof ServerWorld serverWorld) {
                        ItemStackParticleEffect particleEffect = new ItemStackParticleEffect(ParticleTypes.ITEM, AllItems.BRASS_INGOT.asStack());
                        serverWorld.spawnParticles(particleEffect, this.getX(), this.getY() + 0.5, this.getZ(), 10, 0, 0, 0, 0.25);
                    }
                    return false;
                }
                return false;
            }
        }


        if ((this.getHealth() - amount) <= 0.0F) {
            for (int i = 0; i < 9; i++) {
                ItemEntity item = switch (new Random().nextInt(0, 3)) {
                    case 0 ->
                            new ItemEntity(world, this.getX(), this.getY(), this.getZ(), AllBlocks.LARGE_COGWHEEL.asStack(), new Random().nextFloat(-0.25F, 0.25F), 0.5, new Random().nextFloat(-0.25F, 0.25F));
                    case 1 ->
                            new ItemEntity(world, this.getX(), this.getY(), this.getZ(), AllBlocks.COGWHEEL.asStack(), new Random().nextFloat(-0.25F, 0.25F), 0.5, new Random().nextFloat(-0.25F, 0.25F));
                    default ->
                            new ItemEntity(world, this.getX(), this.getY(), this.getZ(), AllItems.BRASS_NUGGET.asStack(), new Random().nextFloat(-0.25F, 0.25F), 0.5, new Random().nextFloat(-0.25F, 0.25F));
                };

                world.spawnEntity(item);
            }

            ItemEntity item = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), MGItems.BUILDING_BOX.asStack(), new Random().nextFloat(-0.25F, 0.25F), 0.5, new Random().nextFloat(-0.25F, 0.25F));

            world.spawnEntity(item);

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            }
        }

        return super.damage(source, amount);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    abstract protected @Nullable SoundEvent getHurtSound(DamageSource source);

    @Override
    abstract protected @Nullable SoundEvent getDeathSound();

    @Override
    public void onDeath(DamageSource damageSource) {
        this.discard();
    }
}
