package net.tomeoprod.more_gun;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.entity.MGEntities;
import net.tomeoprod.more_gun.entity.client.SentryRenderer;
import net.tomeoprod.more_gun.entity.client.model.BuildingBoxModel;
import net.tomeoprod.more_gun.entity.client.model.Level1SentryModel;
import net.tomeoprod.more_gun.particle.MGParticles;

public class MoreGunClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_BUILDING_BOX_LAYER = new EntityModelLayer(Identifier.of(MoreGun.MOD_ID, "building_box"), "main");
    public static final EntityModelLayer MODEL_LEVEL_1_SENTRY_LAYER = new EntityModelLayer(Identifier.of(MoreGun.MOD_ID, "level_1_sentry"), "main");

    @Override
    public void onInitializeClient() {
        MGParticles.clientInit();

        EntityModelLayerRegistry.registerModelLayer(MODEL_BUILDING_BOX_LAYER, BuildingBoxModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MODEL_LEVEL_1_SENTRY_LAYER, Level1SentryModel::getTexturedModelData);

        EntityRendererRegistry.register(MGEntities.SENTRY_BOX_ENTITY_TYPE, SentryRenderer::new);
    }
}
