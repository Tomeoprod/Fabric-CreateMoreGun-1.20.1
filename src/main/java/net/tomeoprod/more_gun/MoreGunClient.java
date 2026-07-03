package net.tomeoprod.more_gun;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.entity.MGEntities;
import net.tomeoprod.more_gun.entity.client.SentryRenderer;
import net.tomeoprod.more_gun.entity.client.model.BuildingBoxModel;
import net.tomeoprod.more_gun.entity.client.model.Level1SentryModel;
import net.tomeoprod.more_gun.entity.client.model.Level2SentryModel;
import net.tomeoprod.more_gun.goggles.EntityGoggleOverlayRenderer;
import net.tomeoprod.more_gun.networking.MGMessages;
import net.tomeoprod.more_gun.particle.MGParticles;

import java.util.function.BiConsumer;

public class MoreGunClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_BUILDING_BOX_LAYER = new EntityModelLayer(Identifier.of(MoreGun.MOD_ID, "building_box"), "main");
    public static final EntityModelLayer MODEL_LEVEL_1_SENTRY_LAYER = new EntityModelLayer(Identifier.of(MoreGun.MOD_ID, "level_1_sentry"), "main");
    public static final EntityModelLayer MODEL_LEVEL_2_SENTRY_LAYER = new EntityModelLayer(Identifier.of(MoreGun.MOD_ID, "level_2_sentry"), "main");

    @Override
    public void onInitializeClient() {
        MGParticles.clientInit();

        EntityModelLayerRegistry.registerModelLayer(MODEL_BUILDING_BOX_LAYER, BuildingBoxModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MODEL_LEVEL_1_SENTRY_LAYER, Level1SentryModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MODEL_LEVEL_2_SENTRY_LAYER, Level2SentryModel::getTexturedModelData);

        EntityRendererRegistry.register(MGEntities.SENTRY_BOX_ENTITY_TYPE, SentryRenderer::new);

        registerOverlays("hotbar", MoreGunClient::wrapOverlay);

        MGMessages.registerS2CPackets();
    }

    @FunctionalInterface
    public interface MGGuiOverlay {
        void renderOverlay(DrawContext graphics, float partialTicks, int windowWidth, int windowHeight);
    }

    public static void registerOverlays(String type, BiConsumer<String, MGGuiOverlay> cons) {
        if (type.equals("hotbar")) {
            cons.accept("entity_goggles_overlay", EntityGoggleOverlayRenderer::renderOverlay);
        }
    }

    private static void wrapOverlay(String id, MGGuiOverlay overlay) {
        HudRenderCallback.EVENT.register((stack, partialTicks) -> {
            Window window = MinecraftClient.getInstance().getWindow();
            overlay.renderOverlay(stack, partialTicks, window.getScaledWidth(), window.getScaledHeight());
        });
    }

}
