package net.tomeoprod.more_gun.networking;

import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.entity.custom.BuildingBoxEntity;
import org.joml.Vector3f;

public class MGMessages {
    public static final Identifier SHOOT_ENTITY_PACKET_ID = Identifier.of(MoreGun.MOD_ID, "shoot");
    public static final Identifier SET_DATA_TRACKERS_PACKET_ID = Identifier.of(MoreGun.MOD_ID, "set_data_trackers");
    public static final Identifier TICK_PACKET_ID = Identifier.of(MoreGun.MOD_ID, "tick_packet");
    public static final Identifier SPAWN_PARTICLES_PACKET_ID = Identifier.of(MoreGun.MOD_ID, "spawn_particles");
    
    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SHOOT_ENTITY_PACKET_ID, ((minecraftServer, serverPlayerEntity, serverPlayNetworkHandler, packetByteBuf, packetSender) -> {
            World world = serverPlayerEntity.getWorld();
            Entity packetEntity = world.getEntityById(packetByteBuf.readInt());
            Vector3f vec3d = packetByteBuf.readVector3f();
            DamageSource damageSource = new DamageSource(
                    world.getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(MoreGun.SHOT_DAMAGE));

            if (packetEntity instanceof LivingEntity entity) {
                entity.damage(damageSource, 2f);
                double d = (double) 0.01F * ((double) 1.0F - entity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
                entity.addVelocity(vec3d.x * d, vec3d.y * d, vec3d.z * d);
            }
        }));

        ServerPlayNetworking.registerGlobalReceiver(SET_DATA_TRACKERS_PACKET_ID, ((minecraftServer, serverPlayerEntity, serverPlayNetworkHandler, packetByteBuf, packetSender) -> {
            ServerWorld world = serverPlayerEntity.getServerWorld();
            IntList list = packetByteBuf.readIntList();
            Entity packetEntity = world.getEntityById(list.getInt(0));
            int deploying = list.getInt(1);
            int deployed = list.getInt(2);
            int buildingMode = list.getInt(3);

            if (packetEntity instanceof BuildingBoxEntity entity) {
                entity.setDeploying(deploying);
                entity.setDeployed(deployed);
                entity.setBuildingMode(buildingMode);
            }
        }));

        ServerPlayNetworking.registerGlobalReceiver(TICK_PACKET_ID, ((minecraftServer, serverPlayerEntity, serverPlayNetworkHandler, packetByteBuf, packetSender) -> {
            ServerWorld world = serverPlayerEntity.getServerWorld();
            Entity packetEntity = world.getEntityById(packetByteBuf.readInt());

            if (packetEntity instanceof BuildingBoxEntity entity) {
                entity.setupAnimationStates();
            }
        }));

        ServerPlayNetworking.registerGlobalReceiver(SPAWN_PARTICLES_PACKET_ID, ((minecraftServer, serverPlayerEntity, serverPlayNetworkHandler, packetByteBuf, packetSender) -> {
            ServerWorld world = serverPlayerEntity.getServerWorld();
            Entity packetEntity = world.getEntityById(packetByteBuf.readInt());
            if (packetEntity != null){
                world.spawnParticles(ParticleTypes.SPIT, packetEntity.getX(), packetEntity.getY(), packetEntity.getZ(), 50, 0, 0, 0, 0.1);
            }
        }));
        
    }

    public static void registerS2CPackets() {
    }
}
