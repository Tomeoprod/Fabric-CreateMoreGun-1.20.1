package net.tomeoprod.more_gun.entity.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.tomeoprod.more_gun.entity.animation.Level1SentryAnimations;
import net.tomeoprod.more_gun.entity.custom.BuildingBoxEntity;

public class Level1SentryModel<T extends BuildingBoxEntity> extends SinglePartEntityModel<T> {
	private final ModelPart sentry;
    private final ModelPart top;
    private final ModelPart head;

	public Level1SentryModel(ModelPart root) {
		this.sentry = root.getChild("sentry");
        this.top = sentry.getChild("top");
        this.head = top.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData sentry = modelPartData.addChild("sentry", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

        ModelPartData top = sentry.addChild("top", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -0.25F));

        ModelPartData armature = top.addChild("armature", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData shaft = armature.addChild("shaft", ModelPartBuilder.create().uv(26, 25).cuboid(-1.0F, 0.25F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F))
                .uv(29, 6).cuboid(-2.0F, -0.75F, -1.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.75F, 0.0F));

        ModelPartData right_arm = shaft.addChild("right_arm", ModelPartBuilder.create().uv(35, 25).cuboid(-0.5F, -3.5F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -0.25F, 0.0F));

        ModelPartData left_arm = shaft.addChild("left_arm", ModelPartBuilder.create().uv(35, 25).mirrored().cuboid(-0.5F, -3.5F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.5F, -0.25F, 0.0F));

        ModelPartData armature_joint = armature.addChild("armature_joint", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.6593F, 3.207F));

        ModelPartData cube_r1 = armature_joint.addChild("cube_r1", ModelPartBuilder.create().uv(31, 35).cuboid(-0.5F, -0.082F, -0.447F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.3734F, 0.644F, 2.5307F, 0.0F, 0.0F));

        ModelPartData cube_r2 = armature_joint.addChild("cube_r2", ModelPartBuilder.create().uv(26, 35).cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.4093F, -2.457F, 1.5708F, 0.0F, 0.0F));

        ModelPartData cube_r3 = armature_joint.addChild("cube_r3", ModelPartBuilder.create().uv(26, 35).mirrored().cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.0F, 0.4093F, -2.707F, 1.5708F, 0.0F, 0.0F));

        ModelPartData head = top.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -6.5F, 0.25F));

        ModelPartData magazine = head.addChild("magazine", ModelPartBuilder.create().uv(0, 0).cuboid(-5.5F, -3.5F, -3.0F, 7.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -0.5F, 3.75F));

        ModelPartData face = head.addChild("face", ModelPartBuilder.create().uv(0, 14).cuboid(-3.5F, -2.5F, -3.0F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -0.75F, -1.25F));

        ModelPartData barrel = head.addChild("barrel", ModelPartBuilder.create().uv(29, 0).cuboid(-1.0F, -1.5F, -2.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 0.3F, -5.25F));

        ModelPartData legs = sentry.addChild("legs", ModelPartBuilder.create(), ModelTransform.pivot(0.1429F, 1.7431F, 0.3925F));

        ModelPartData legs_joint = legs.addChild("legs_joint", ModelPartBuilder.create().uv(29, 10).cuboid(-2.0F, -0.5F, 1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.1429F, -0.2431F, -0.1425F));

        ModelPartData cube_r4 = legs_joint.addChild("cube_r4", ModelPartBuilder.create().uv(0, 25).cuboid(-1.5F, -3.0F, -3.5F, 2.0F, 5.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 25).mirrored().cuboid(4.5F, -3.0F, -3.5F, 2.0F, 5.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.5F, 1.25F, 0.0F, -0.7418F, 0.0F, 0.0F));

        ModelPartData RB = legs.addChild("RB", ModelPartBuilder.create(), ModelTransform.pivot(-2.3929F, 2.5859F, 1.3412F));

        ModelPartData cube_r5 = RB.addChild("cube_r5", ModelPartBuilder.create().uv(17, 25).cuboid(-0.25F, -3.0F, -2.5F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.25F, -0.3289F, 2.5163F, 1.1345F, 0.0F, 0.0F));

        ModelPartData LB = legs.addChild("LB", ModelPartBuilder.create(), ModelTransform.pivot(2.1071F, 2.5859F, 1.3412F));

        ModelPartData cube_r6 = LB.addChild("cube_r6", ModelPartBuilder.create().uv(17, 25).mirrored().cuboid(0.5F, -3.0F, -2.5F, 1.0F, 8.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.25F, -0.3289F, 2.5163F, 1.1345F, 0.0F, 0.0F));

        ModelPartData RF = legs.addChild("RF", ModelPartBuilder.create(), ModelTransform.pivot(-3.1932F, 0.2352F, -2.3467F));

        ModelPartData cube_r7 = RF.addChild("cube_r7", ModelPartBuilder.create().uv(21, 14).cuboid(-0.8901F, -1.1247F, -7.9269F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0503F, 0.3502F, -0.0458F, 0.6843F, 0.7022F, -0.1514F));

        ModelPartData LF = legs.addChild("LF", ModelPartBuilder.create(), ModelTransform.pivot(2.9074F, 0.2351F, -2.3467F));

        ModelPartData cube_r8 = LF.addChild("cube_r8", ModelPartBuilder.create().uv(21, 14).mirrored().cuboid(-1.0F, -1.0F, -3.5F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.6997F, 3.3502F, -2.7958F, 0.6843F, -0.7022F, 0.1514F));
        return TexturedModelData.of(modelData, 64, 64);
    }

	@Override
	public void setAngles(BuildingBoxEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        if (entity.getDeployed() > 0) {
            this.setHeadAngles(netHeadYaw, headPitch, entity.getBuildingRotation());
        }

        if (!(entity.getDeployed() > 0)) {
            this.updateAnimation(entity.deployAnimationState, Level1SentryAnimations.LEVEL_1_SENTRY_DEPLOY, ageInTicks, 1f);
        } else this.updateAnimation(entity.deployAnimationState, Level1SentryAnimations.LEVEL_1_SENTRY_DEPLOY, 12600, 1f);

        this.updateAnimation(entity.shootAnimationState, Level1SentryAnimations.LEVEL_1_SENTRY_SHOOT, ageInTicks, 1);
    }

    private void setHeadAngles(float headYaw, float headPitch, float rotationOffset) {
        this.top.yaw = (headYaw - rotationOffset) * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		sentry.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

    @Override
    public ModelPart getPart() {
        return this.sentry;
    }
}