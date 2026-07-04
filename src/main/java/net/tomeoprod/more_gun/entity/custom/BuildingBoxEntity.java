package net.tomeoprod.more_gun.entity.custom;

import com.mojang.datafixers.TypeRewriteRule;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.equipment.wrench.WrenchItem;
import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import com.simibubi.create.content.logistics.chute.ChuteBlock;
import com.simibubi.create.content.logistics.chute.ChuteBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.datafixer.fix.ChunkPalettedStorageFix;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.tomeoprod.more_gun.Item.MGItems;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.goggles.IHaveEntityGoggleInformation;
import net.tomeoprod.more_gun.networking.MGMessages;
import net.tomeoprod.more_gun.sound.MGSounds;
import net.tomeoprod.more_gun.sound.instances.DeployingSoundInstance;
import net.tomeoprod.more_gun.util.Image2d;
import net.tomeoprod.more_gun.util.TF2Utils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public abstract class BuildingBoxEntity extends MobEntity implements IHaveEntityGoggleInformation {
    public final AnimationState deployAnimationState = new AnimationState();
    public final AnimationState deployedAnimationState = new AnimationState();
    public final AnimationState suckAnimationState = new AnimationState();
    private int suckTimer = 0;

    private static final TrackedData<Integer> DEPLOYED = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> DEPLOYING = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<String> OWNER_UUID = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> OWNER_NAME = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<String> BUILDING_TYPE = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> BUILDING_LEVEL = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> BUILDING_ROTATION = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> BUILDING_MODE = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> UPGRADE_PROGRESS = DataTracker.registerData(BuildingBoxEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public BuildingBoxEntity(EntityType<? extends BuildingBoxEntity> buildingBoxEntityEntityType, World world) {
        super(buildingBoxEntityEntityType, world);
    }

    public static DefaultAttributeContainer.Builder createBuildingBoxAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 150)
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
        dataTracker.startTracking(DEPLOYING, 0);
        dataTracker.startTracking(OWNER_UUID, "");
        dataTracker.startTracking(OWNER_NAME, "None");
        dataTracker.startTracking(BUILDING_TYPE, "");
        dataTracker.startTracking(BUILDING_LEVEL, 0);
        dataTracker.startTracking(BUILDING_ROTATION, 0.0f);
        dataTracker.startTracking(BUILDING_MODE, 0);
        dataTracker.startTracking(UPGRADE_PROGRESS, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("more_gun.deployed", getDeployed());
        nbt.putInt("more_gun.deploying", getDeploying());
        nbt.putString("more_gun.ownerUUID", getOwnerId());
        nbt.putString("more_gun.ownerName", getOwnerName());
        nbt.putString("more_gun.buildingType", getBuildingType());
        nbt.putInt("more_gun.buildingLevel", getBuildingLevel());
        nbt.putFloat("more_gun.buildingRotation", getBuildingRotation());
        nbt.putInt("more_gun.buildingMode", getBuildingMode());
        nbt.putInt("more_gun.upgradeProgress", getUpgradeProgress());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("more_gun.deployed")) setDeployed(nbt.getInt("more_gun.deployed"));
        if (nbt.contains("more_gun.deploying")) setDeploying(nbt.getInt("more_gun.deploying"));
        if (nbt.contains("more_gun.ownerUUID")) setOwnerId(nbt.getString("more_gun.ownerUUID"));
        if (nbt.contains("more_gun.ownerName")) setOwnerName(nbt.getString("more_gun.ownerName"));
        if (nbt.contains("more_gun.buildingType")) setBuildingType(nbt.getString("more_gun.buildingType"));
        if (nbt.contains("more_gun.buildingLevel")) setBuildingLevel(nbt.getInt("more_gun.buildingLevel"));
        if (nbt.contains("more_gun.buildingRotation")) setBuildingRotation(nbt.getFloat("more_gun.buildingRotation"));
        if (nbt.contains("more_gun.buildingMode")) setBuildingMode(nbt.getInt("more_gun.buildingMode"));
        if (nbt.contains("more_gun.upgradeProgress")) setUpgradeProgress(nbt.getInt("more_gun.upgradeProgress"));
    }

    public void setDeployed(int deployed) {
        dataTracker.set(DEPLOYED, deployed);
    }

    public int getDeployed() {
        return dataTracker.get(DEPLOYED);
    }

    public void setDeploying(int deploying) {
        dataTracker.set(DEPLOYING, deploying);
    }

    public int getDeploying() {
        return dataTracker.get(DEPLOYING);
    }

    public void setOwnerId(String playerId) {
        dataTracker.set(OWNER_UUID, playerId);
    }

    public String getOwnerId() {
        return dataTracker.get(OWNER_UUID);
    }

    public void setOwnerName(String name) {
        dataTracker.set(OWNER_NAME, name);
    }

    public String getOwnerName() {
        return dataTracker.get(OWNER_NAME);
    }

    public void setBuildingType(String type) {
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

    public void setBuildingMode(int mode) {
        dataTracker.set(BUILDING_MODE, mode);
    }

    public int getBuildingMode() {
        return dataTracker.get(BUILDING_MODE);
    }

    public void setUpgradeProgress(int progress) {
        dataTracker.set(UPGRADE_PROGRESS, progress);
    }

    public int getUpgradeProgress() {
        return dataTracker.get(UPGRADE_PROGRESS);
    }

    public abstract Item getUpgradeItem();

    public abstract int getMaxUpgradeProgress();

    public abstract int getMaxBuildingModes();

    public abstract Text getBuildingModeMessage(int mode);

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
        if (this.getDeploying() > this.getDeployed()) {
            if (!this.deployAnimationState.isRunning()) {
                if (this.getWorld().isClient) {
                    MinecraftClient.getInstance().getSoundManager().play(new DeployingSoundInstance(this, this.getDeployingSound()));
                }

                if (this.getDeploying() == 1) {
                    for (int i = 0; i < 25; i++) {
                        this.getWorld().addImportantParticle(ParticleTypes.SPIT, true, this.getX(), this.getY(), this.getZ(), new Random().nextFloat(-0.1f, 0.1f), 0.2, new Random().nextFloat(-0.1f, 0.1f));
                    }
                }
            }
            this.deployAnimationState.startIfNotRunning(this.age);
        }

        if (this.deployAnimationState.getTimeRunning() >= this.getMaxAnimationTime() && this.getDeploying() != this.getDeployed()) {
            this.deployAnimationState.stop();

            this.setDeployed(this.getDeployed() + 1);

            if (this.getDeployed() != this.getBuildingLevel()) {
                this.setDeploying(this.getDeploying() + 1);
                this.deployAnimationState.startIfNotRunning(this.age);
                if (this.getWorld().isClient) {
                    MinecraftClient.getInstance().getSoundManager().play(new DeployingSoundInstance(this, this.getDeployingSound()));
                }
            } else {
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeInt(this.getId());
                ClientPlayNetworking.send(MGMessages.SPAWN_PARTICLES_PACKET_ID, passedData);
                MinecraftClient.getInstance().getSoundManager().play(new DeployingSoundInstance(this, MGSounds.BUILDING_DEPLOYING_END));
            }

            this.updateDataTrackers();
        }

        if (this.getDeployed() > 0) {
            this.deployedAnimationState.startIfNotRunning(this.age);
        } else this.deployedAnimationState.stop();
    }

    abstract public SoundEvent getDeployingSound();

    abstract public int getMaxAnimationTime();

    @Override
    public void tick() {
        super.tick();

        this.setupAnimationStates();
        this.calculateDimensions();


        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        passedData.writeInt(this.getId());

        if (this.getWorld().isClient) {
            ClientPlayNetworking.send(MGMessages.TICK_PACKET_ID, passedData);
        }

        if (this.getWorld().getBlockEntity(this.getSteppingPos()) instanceof ChuteBlockEntity chute) {
            BlockPos pos = this.getSteppingPos();
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.playSound(null, pos.getX(), pos.getY() - 1, pos.getZ(), AllSoundEvents.FWOOMP.getMainEvent(), SoundCategory.BLOCKS, 2f, 1f, 0);
            }
            ClientPlayNetworking.send(MGMessages.SPAWN_PARTICLES_PACKET_ID, passedData);
            chute.setItem(this.getAsItem());
            this.discard();
        }
    }

    protected void updateDataTrackers() {
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        IntList list = IntList.of(
                this.getId(),
                this.getDeploying(),
                this.getDeployed(),
                this.getBuildingMode()
        );
        passedData.writeIntList(list);

        ClientPlayNetworking.send(MGMessages.SET_DATA_TRACKERS_PACKET_ID, passedData);
    }

    public abstract ItemStack getAsItem();

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        World world = player.getWorld();

        if (player.getUuidAsString().equals(this.getOwnerId())) {
            if (player.isSneaking()) {
                this.putInBox(world);
                return ActionResult.SUCCESS;

            } else {
                int mode = this.getBuildingMode() + 1;

                if (mode > this.getMaxBuildingModes()) {
                    mode = 0;
                }

                player.sendMessage(this.getBuildingModeMessage(mode), true);

                this.setBuildingMode(mode);
                this.updateDataTrackers();
            }
        }

        return ActionResult.PASS;
    }

    public void putInBox(World world) {
        ItemEntity item = new ItemEntity(
                world,
                this.getX(),
                this.getY() + 0.5,
                this.getZ(),
                this.getAsItem()
        );
        world.spawnEntity(item);
        this.discard();
    }

    public void putInBox(World world, Vec3d velocity) {
        ItemEntity item = new ItemEntity(
                world,
                this.getX(),
                this.getY() + 0.5,
                this.getZ(),
                this.getAsItem(),
                velocity.getX(),
                velocity.getY(),
                velocity.getZ()
        );
        world.spawnEntity(item);
        this.discard();
    }

    @Override
    public boolean canTakeDamage() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        World world = this.getWorld();
        Entity attacker = source.getAttacker();

        if (attacker instanceof DeployerFakePlayer deployer) {
            boolean needsHealth = this.getHealth() != this.getMaxHealth();
            boolean deployerHasBrass = deployer.getMainHandStack().isOf(AllItems.BRASS_INGOT.asItem());

            if (this.getDeployed() == 0 && this.getDeploying() == 0 && deployer.getMainHandStack().isOf(AllItems.WRENCH.asItem())) {
                this.setDeploying(1);
                TF2Utils.playWrenchSound(world, this.getX(), this.getY(), this.getZ(), 0.1f);
                this.updateDataTrackers();
                return false;

            }

            if (this instanceof SentryEntity sentryEntity) {
                int maxAmmo = TF2Utils.getMaxAmmo(sentryEntity.getBuildingLevel());
                boolean needsAmmo = maxAmmo - sentryEntity.getAmmo() != 0;
                boolean deployerHasBullets = deployer.getMainHandStack().isOf(MGItems.BULLET);

                if (needsAmmo && deployerHasBullets) {
                    int bullet = MathHelper.clamp(
                            deployer.getMainHandStack().getCount(),
                            0,
                            MathHelper.clamp(maxAmmo - sentryEntity.getAmmo(), 0, 40));

                    sentryEntity.setAmmo(sentryEntity.getAmmo() + bullet);

                    deployer.getMainHandStack().decrement(bullet);

                    if (!deployerHasBrass || !needsHealth) {
                        TF2Utils.playWrenchSound(world, sentryEntity.getX(), sentryEntity.getY(), sentryEntity.getZ(), 0.1f);
                        return false;
                    }
                }
            }

            if (deployerHasBrass && needsHealth) {
                TF2Utils.playWrenchSound(world, this.getX(), this.getY(), this.getZ(), 0.1f);
                this.heal(105F);

                deployer.getMainHandStack().decrement(1);

                if (this.getWorld() instanceof ServerWorld serverWorld) {
                    ItemStackParticleEffect particleEffect = new ItemStackParticleEffect(ParticleTypes.ITEM, AllItems.BRASS_INGOT.asStack());
                    serverWorld.spawnParticles(particleEffect, this.getX(), this.getY() + 0.5, this.getZ(), 10, 0, 0, 0, 0.25);
                }

                return false;

            }

            if (this.getUpgradeItem() != null) {
                if (deployer.getMainHandStack().isOf(this.getUpgradeItem())) {
                    deployer.getMainHandStack().decrement(1);
                    TF2Utils.playWrenchSound(world, this.getX(), this.getY(), this.getZ(), 0.1f);
                    upgradeBuilding(world);
                    return false;
                }
            }

            TF2Utils.playWrenchFailSound(world, this.getX(), this.getY(), this.getZ(), 0.1f);
            return false;
        }

        if (attacker instanceof PlayerEntity player) {
            if (player.getMainHandStack().getItem() instanceof WrenchItem) {
                boolean needsHealth = this.getHealth() != this.getMaxHealth();
                boolean playerHasBrass = player.getInventory().contains(AllItems.BRASS_INGOT.asStack());

                if (this.getDeployed() == 0 && this.getDeploying() == 0) {
                    this.setDeploying(1);
                    TF2Utils.playWrenchSound(world, this.getX(), this.getY(), this.getZ(), 0.5f);
                    this.updateDataTrackers();
                    return false;

                }

                if (this instanceof SentryEntity sentryEntity) {
                    int maxAmmo = TF2Utils.getMaxAmmo(sentryEntity.getBuildingLevel());
                    boolean needsAmmo = maxAmmo - sentryEntity.getAmmo() != 0;
                    boolean playerHasBullets = player.getInventory().contains(MGItems.BULLET.getDefaultStack());

                    if (needsAmmo && playerHasBullets) {
                        int bullet = MathHelper.clamp(
                                player.getInventory().count(MGItems.BULLET),
                                0,
                                MathHelper.clamp(maxAmmo - sentryEntity.getAmmo(), 0, 40));

                        sentryEntity.setAmmo(sentryEntity.getAmmo() + bullet);
                        if (!player.isCreative()) {
                            player.getInventory().remove(stack -> stack.isOf(MGItems.BULLET), bullet, player.playerScreenHandler.getCraftingInput());
                        }

                        if (!playerHasBrass || !needsHealth) {
                            TF2Utils.playWrenchSound(world, sentryEntity.getX(), sentryEntity.getY(), sentryEntity.getZ(), 0.5f);
                            return false;
                        }
                    }
                }

                if (playerHasBrass && needsHealth) {
                    TF2Utils.playWrenchSound(world, this.getX(), this.getY(), this.getZ(), 0.5f);
                    this.heal(105F);

                    if (!player.isCreative()) {
                        player.getInventory().getStack(
                                player.getInventory().indexOf(AllItems.BRASS_INGOT.asStack())
                        ).decrement(1);
                    }

                    if (this.getWorld() instanceof ServerWorld serverWorld) {
                        ItemStackParticleEffect particleEffect = new ItemStackParticleEffect(ParticleTypes.ITEM, AllItems.BRASS_INGOT.asStack());
                        serverWorld.spawnParticles(particleEffect, this.getX(), this.getY() + 0.5, this.getZ(), 10, 0, 0, 0, 0.25);
                    }

                    return false;

                }

                if (this.getUpgradeItem() != null) {
                    if (player.getInventory().contains(this.getUpgradeItem().getDefaultStack())) {
                        if (!player.isCreative()) {
                            player.getInventory().getStack(
                                    player.getInventory().indexOf(this.getUpgradeItem().getDefaultStack())
                            ).decrement(1);
                        }
                        TF2Utils.playWrenchSound(world, this.getX(), this.getY(), this.getZ(), 0.5f);
                        upgradeBuilding(world);
                        return false;
                    }
                }

                TF2Utils.playWrenchFailSound(world, this.getX(), this.getY(), this.getZ(), 0.5f);
                return false;

            } else if (this.getDeployed() == 0) {
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

            if (this instanceof SentryEntity sentryEntity) {
                while (sentryEntity.getAmmo() > 0) {
                    world.spawnEntity(new ItemEntity(world, this.getX(), this.getY(), this.getZ(), MGItems.BULLET.getDefaultStack(), new Random().nextFloat(-0.25F, 0.25F), 0.5, new Random().nextFloat(-0.25F, 0.25F)));
                    sentryEntity.setAmmo(sentryEntity.getAmmo() - 1);
                }
            }

            ItemEntity item = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), MGItems.EMPTY_BUILDING_BOX.getDefaultStack(), new Random().nextFloat(-0.25F, 0.25F), 0.5, new Random().nextFloat(-0.25F, 0.25F));

            world.spawnEntity(item);

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            }
        }

        return super.damage(source, amount);
    }

    public void upgradeBuilding(World world) {
        if (this.getUpgradeProgress() == this.getMaxUpgradeProgress() - 1) {
            this.setUpgradeProgress(0);
            this.setBuildingLevel(this.getBuildingLevel() + 1);
            if (this.getDeploying() == this.getDeployed()) {
                this.setDeploying(this.getBuildingLevel());
            }
            TF2Utils.setMaxHealth(this, this.getBuildingLevel());
            this.setHealth(this.getMaxHealth());
            this.updateDataTrackers();

        } else this.setUpgradeProgress(this.getUpgradeProgress() + 1);
    }

    @Override
    public boolean hasNoGravity() {
        return false;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    protected boolean isImmobile() {
        return true;
    }

    @Override
    abstract protected @Nullable SoundEvent getHurtSound(DamageSource source);

    @Override
    abstract protected @Nullable SoundEvent getDeathSound();

    @Override
    public void onDeath(DamageSource damageSource) {
        this.discard();
    }

    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        MutableText infoText = Text.translatable(MoreGun.MOD_ID + ".goggles.building.info");
        MutableText ownerText = Text.translatable(MoreGun.MOD_ID + ".goggles.building.owner").append("§e" + this.getOwnerName());
        MutableText typeText = Text.translatable(MoreGun.MOD_ID + ".goggles.building.type").append("§e" + this.getBuildingType());
        MutableText levelText = Text.translatable(MoreGun.MOD_ID + ".goggles.building.level").append("§e" + this.getBuildingLevel());

        CreateLang.builder().add(infoText.copy()).forGoggles(tooltip);
        CreateLang.builder().add(ownerText.copy()).forGoggles(tooltip);
        CreateLang.builder().add(typeText.copy()).forGoggles(tooltip);
        CreateLang.builder().add(levelText.copy()).forGoggles(tooltip);

        return true;
    }

    @Override
    public boolean addToSecondaryGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        if (this.getBuildingLevel() == 3 || this.getDeployed() == 0 || this.getUpgradeItem() == null) {
            return false;
        }

        MutableText upgradeText = Text.translatable(MoreGun.MOD_ID + ".goggles.building.upgrade");

        CreateLang.builder().add(upgradeText.copy()).forGoggles(tooltip);
        CreateLang.builder().add(this.getUpgradeItem().getName()).forGoggles(tooltip,5);

        return true;
    }

    @Override
    public boolean addImageToSecondaryGoggleTooltip(List<Image2d<Item, Vec2f>> images) {
        if (this.getBuildingLevel() == 3 || this.getDeployed() == 0 || this.getUpgradeItem() == null) {
            return false;
        }

        images.add(new Image2d<>(this.getUpgradeItem(), new Vec2f(30f, 5)));

        return true;
    }
}