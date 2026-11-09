package net.tomeoprod.more_gun.entity.client;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.MoreGunClient;
import net.tomeoprod.more_gun.entity.client.model.BuildingBoxModel;
import net.tomeoprod.more_gun.entity.client.model.Level1SentryModel;
import net.tomeoprod.more_gun.entity.client.model.Level2SentryModel;
import net.tomeoprod.more_gun.entity.custom.SentryEntity;
import net.tomeoprod.more_gun.util.RenderUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class SentryRenderer extends MobEntityRenderer<SentryEntity, BuildingBoxModel<SentryEntity>> {
    @SuppressWarnings("rawtypes")
    private final Level1SentryModel sentryLv1Model;

    @SuppressWarnings("rawtypes")
    private final Level2SentryModel sentryLv2Model;

    private static final Identifier Level1SentryTexture = Identifier.of(MoreGun.MOD_ID, "textures/entity/sentry_lv1.png");
    private static final Identifier Level1SentryEyeTexture = Identifier.of(MoreGun.MOD_ID, "textures/entity/sentry_lv1_eye.png");
    private static final Identifier Level2SentryTexture = Identifier.of(MoreGun.MOD_ID, "textures/entity/sentry_lv2.png");
    private static final Identifier Level2SentryEyeTexture = Identifier.of(MoreGun.MOD_ID, "textures/entity/sentry_lv2_eye.png");

    public SentryRenderer(EntityRendererFactory.Context context) {
        super(context, new BuildingBoxModel<>(context.getPart(MoreGunClient.MODEL_BUILDING_BOX_LAYER)), 0.5f);
        this.sentryLv1Model = new Level1SentryModel<>(context.getPart(MoreGunClient.MODEL_LEVEL_1_SENTRY_LAYER));
        this.sentryLv2Model = new Level2SentryModel<>(context.getPart(MoreGunClient.MODEL_LEVEL_2_SENTRY_LAYER));
    }

    @Override
    public void render(SentryEntity sentry, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        float l = this.getAnimationProgress(sentry, g);
        this.setupTransforms(sentry, matrices, l, 0, g);
        matrices.scale(-1.0F, -1.0F, 1.0F);
        this.scale(sentry, matrices, g);
        matrices.translate(0.0F, -1.501F, 0.0F);

        RenderLayer renderLayer = this.getRenderLayer(sentry, true, true, true);
        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
            int p = getOverlay(sentry, this.getAnimationCounter(sentry, g));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(sentry.getBuildingRotation()));

            this.model.animateModel(sentry, 0, 0, g);
            this.model.setAngles(sentry, 0, 0, l, 0, 0);
            this.model.render(matrices, vertexConsumer, light, p, 1.0F, 1.0F, 1.0F, 1.0F);

            if (sentry.getBuildingType().equals("Sentry")) {
                float j = MathHelper.lerpAngleDegrees(g, sentry.prevHeadYaw, sentry.headYaw);
                if (sentry.getBuildingLevel() >= 1 && sentry.getDeployed() <= 1 && sentry.getDeploying() == 1) {
                    VertexConsumer sentryBuffer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Level1SentryTexture));
                    this.sentryLv1Model.setAngles(sentry, 0, 0, l, j, sentry.getPitch());

                    if (!(sentry.getDeployed() > 0 || sentry.deployAnimationState.getTimeRunning() >= 10)) {
                        matrices.scale(0.2f, 0.2f, 0.2f);
                        matrices.translate(0f, 5.75f, 0f);
                    }
                    sentryLv1Model.render(matrices, sentryBuffer, light, p, 1f, 1f, 1f, 1f);

                    if (sentry.getBuildingMode() != 3) {
                        VertexConsumer sentryEyeBuffer = vertexConsumers.getBuffer(RenderLayer.getEyes(Level1SentryEyeTexture));
                        sentryLv1Model.render(matrices, sentryEyeBuffer, LightmapTextureManager.MAX_SKY_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

                        if (sentry.isWrangled && sentry.getDeployed() == 1) {
                            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(sentry.getBuildingRotation()));
                            this.renderWranglerLaser(sentry, matrices, vertexConsumers, 1/128f, sentry.aimedBlockPos, 0.25f, 0.075f, 0.6f);
                        }
                    }
                }

                if (sentry.getBuildingLevel() >= 2 && sentry.getDeploying() == 2 && sentry.getDeployed() >= 1) {
                    VertexConsumer sentryBuffer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Level2SentryTexture));

                    this.sentryLv2Model.setAngles(sentry, 0, 0, l, j, sentry.getPitch());

                    sentryLv2Model.render(matrices, sentryBuffer, light, p, 1f, 1f, 1f, 1f);

                    if (sentry.getBuildingMode() != 3) {
                        VertexConsumer sentryEyeBuffer = vertexConsumers.getBuffer(RenderLayer.getEyes(Level2SentryEyeTexture));
                        sentryLv2Model.render(matrices, sentryEyeBuffer, LightmapTextureManager.MAX_SKY_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

                        if (sentry.isWrangled && sentry.getDeployed() == 2) {
                            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(sentry.getBuildingRotation()));
                            this.renderWranglerLaser(sentry, matrices, vertexConsumers, 1/128f, sentry.aimedBlockPos, -0.1f, -0.025f, 0.325f);
                        }
                    }
                }
            }
        }

        matrices.pop();
    }

    @Override
    public boolean shouldRender(SentryEntity sentry, Frustum frustum, double d, double e, double f) {
        return super.shouldRender(sentry, frustum, d, e, f) || sentry.isWrangled;
    }

    private void renderWranglerLaser(SentryEntity sentry, MatrixStack matrices, VertexConsumerProvider vertexConsumers, float thickness, Vec3d target, float frontOffset, float sideOffset, float upOffset) {
        Vec3d front = sentry.getRotationVec(1).multiply(frontOffset);
        Vec3d side = new Vec3d(-front.getZ(), 0, front.getX()).normalize();
        Vec3d side2 = front.add(side.multiply(sideOffset));

        matrices.translate(0, upOffset, 0);
        matrices.translate(side2.getX(), -side2.getY(), -side2.getZ());

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(sentry.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(sentry.getPitch()));

        double dx = target.getX() - sentry.getX();
        double dy = target.getY() - sentry.getEyeY();
        double dz = target.getZ() - sentry.getZ();

        float length = (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
        Matrix4f pose = matrices.peek().getPositionMatrix();
        Matrix3f normal = matrices.peek().getNormalMatrix();
        VertexConsumer vcons = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentCull(Identifier.of(MoreGun.MOD_ID, "textures/entity/color.png")));
        RenderUtils.renderBox(vcons, pose, normal, 255, 50, 0, 150, length, thickness);
        RenderUtils.renderBoxInverted(vcons, pose, normal, 255, 10, 0, 150, length, thickness * 1.5f);

        vcons = vertexConsumers.getBuffer(RenderLayer.getEyes(Identifier.of(MoreGun.MOD_ID, "textures/entity/color.png")));
        RenderUtils.renderBox(vcons, pose, normal, 255, 50, 0, 150, length, thickness);
        RenderUtils.renderBoxInverted(vcons, pose, normal, 255, 10, 0, 150, length, thickness * 1.5f);
    }

    @Override
    public Identifier getTexture(SentryEntity entity) {
        return Identifier.of(MoreGun.MOD_ID, "textures/item/building_box.png");
    }
}
