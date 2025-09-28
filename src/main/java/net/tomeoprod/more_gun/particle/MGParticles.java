package net.tomeoprod.more_gun.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.particle.custom.MuzzleFlashParticle;

public class MGParticles {
    public static final ParticleType<DefaultParticleType> MUZZLE_FLASH_PARTICLE = FabricParticleTypes.simple();

    public static void MainInit() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MoreGun.MOD_ID, "muzzle_flash"), MUZZLE_FLASH_PARTICLE);
    }

    public static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(MUZZLE_FLASH_PARTICLE, MuzzleFlashParticle.Factory::new);
    }
}
