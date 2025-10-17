package net.tomeoprod.more_gun;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.Item.MGItemGroups;
import net.tomeoprod.more_gun.Item.MGItems;
import net.tomeoprod.more_gun.block.MGBlocks;
import net.tomeoprod.more_gun.block.entity.MGBlockEntities;
import net.tomeoprod.more_gun.block.entity.renderer.SonarBlockEntityRenderer;
import net.tomeoprod.more_gun.entity.MGEntities;
import net.tomeoprod.more_gun.networking.MGMessages;
import net.tomeoprod.more_gun.particle.MGParticles;
import net.tomeoprod.more_gun.ponder.MGPonderIndex;
import net.tomeoprod.more_gun.sound.MGSounds;
import net.tomeoprod.more_gun.world.MGOreGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreGun implements ModInitializer {
	public static final String MOD_ID = "more_gun";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

    public static final RegistryKey<DamageType> SHOT_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(MOD_ID, "shot"));

	@Override
	public void onInitialize() {
        MGItems.initialize();
        MGBlocks.initialize();
        MGBlockEntities.initialize();
        MGItemGroups.registerModItemGroups();
        MGEntities.registerModEntities();
        MGParticles.MainInit();
        MGMessages.registerC2SPackets();
        MGSounds.initialize();
        MGPonderIndex.registerPonders();
        MGOreGeneration.generateOres();
    }
}