package net.tomeoprod.more_gun.entity.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.tomeoprod.more_gun.entity.animation.Level2SentryAnimations;
import net.tomeoprod.more_gun.entity.custom.SentryEntity;

@SuppressWarnings("unused")
public class Level2SentryModel<T extends SentryEntity> extends SinglePartEntityModel<T> {
	private final ModelPart sentry;
	private final ModelPart top;
	private final ModelPart head;

	public Level2SentryModel(ModelPart root) {
		this.sentry = root.getChild("sentry");
		this.top = sentry.getChild("top");
		this.head = top.getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData sentry = modelPartData.addChild("sentry", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 15.0F, 0.0F));

		ModelPartData top = sentry.addChild("top", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -0.25F));

		ModelPartData armature = top.addChild("armature", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData shaft = armature.addChild("shaft", ModelPartBuilder.create().uv(53, 0).cuboid(-1.0F, 0.25F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.75F, 0.0F));

		ModelPartData right_arm = shaft.addChild("right_arm", ModelPartBuilder.create().uv(48, 0).cuboid(-3.5F, -2.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -0.25F, 0.0F));

		ModelPartData left_arm = shaft.addChild("left_arm", ModelPartBuilder.create().uv(48, 0).mirrored().cuboid(2.5F, -2.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.5F, -0.25F, 0.0F));

		ModelPartData horizontal_shaft = shaft.addChild("horizontal_shaft", ModelPartBuilder.create().uv(29, 10).cuboid(-7.0F, -0.5F, -1.0F, 14.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.25F, 0.0F));

		ModelPartData armature_joint = armature.addChild("armature_joint", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.6593F, 3.207F));

		ModelPartData cube_r1 = armature_joint.addChild("cube_r1", ModelPartBuilder.create().uv(48, 0).cuboid(-0.5F, -0.082F, -0.447F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.6266F, 1.644F, 2.5307F, 0.0F, 0.0F));

		ModelPartData cube_r2 = armature_joint.addChild("cube_r2", ModelPartBuilder.create().uv(48, 0).cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.4093F, -2.457F, 1.8326F, 0.0F, 0.0F));

		ModelPartData cube_r3 = armature_joint.addChild("cube_r3", ModelPartBuilder.create().uv(48, 0).mirrored().cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.0F, 0.4093F, -2.707F, 1.8326F, 0.0F, 0.0F));

		ModelPartData head = top.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -6.5F, 0.25F));

		ModelPartData magazine = head.addChild("magazine", ModelPartBuilder.create().uv(0, 0).cuboid(-5.5F, -4.5F, -2.0F, 7.0F, 6.0F, 7.0F, new Dilation(0.0F))
		.uv(33, 0).cuboid(-5.0F, -4.0F, -2.75F, 6.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -0.5F, 3.75F));

		ModelPartData face = head.addChild("face", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, -0.75F, -1.25F));

		ModelPartData R_face = face.addChild("R_face", ModelPartBuilder.create(), ModelTransform.pivot(-6.5F, 0.0F, -0.5F));

		ModelPartData cube_r4 = R_face.addChild("cube_r4", ModelPartBuilder.create().uv(21, 14).cuboid(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		ModelPartData L_face = face.addChild("L_face", ModelPartBuilder.create(), ModelTransform.pivot(4.5F, 0.0F, -0.5F));

		ModelPartData cube_r5 = L_face.addChild("cube_r5", ModelPartBuilder.create().uv(21, 14).mirrored().cuboid(-1.0F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		ModelPartData L_gatling = head.addChild("L_gatling", ModelPartBuilder.create().uv(37, 14).cuboid(-1.0F, -0.8812F, 4.4472F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 14).cuboid(-2.0F, -2.0594F, -1.3486F, 4.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, -2.6406F, -3.4014F));

		ModelPartData L_barrel = L_gatling.addChild("L_barrel", ModelPartBuilder.create().uv(40, 14).cuboid(-1.25F, 0.75F, -5.25F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F))
		.uv(40, 14).cuboid(0.25F, 0.75F, -5.25F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F))
		.uv(40, 14).cuboid(0.75F, -0.5F, -5.25F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F))
		.uv(40, 14).cuboid(0.25F, -1.75F, -5.25F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F))
		.uv(40, 14).cuboid(-1.25F, -1.75F, -5.25F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F))
		.uv(40, 14).cuboid(-1.75F, -0.5F, -5.25F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F))
		.uv(22, 0).cuboid(-2.0F, -2.0F, 3.25F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(22, 0).cuboid(-2.0F, -2.0F, 1.25F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(22, 0).cuboid(-2.0F, -2.0F, -3.25F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(22, 0).cuboid(-2.0F, -2.0F, -4.75F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.0594F, -7.0986F));

		ModelPartData R_gatling = head.addChild("R_gatling", ModelPartBuilder.create().uv(37, 14).cuboid(-1.0F, -0.8812F, 4.4305F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 14).cuboid(-2.0F, -2.0594F, -1.3653F, 4.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.5F, -2.6406F, -3.3847F));

		ModelPartData R_barrel = R_gatling.addChild("R_barrel", ModelPartBuilder.create().uv(40, 14).cuboid(-1.25F, 0.75F, -5.3F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F))
		.uv(40, 14).cuboid(0.25F, 0.75F, -5.3F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F))
		.uv(40, 14).cuboid(0.75F, -0.5F, -5.3F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F))
		.uv(40, 14).cuboid(0.25F, -1.75F, -5.3F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F))
		.uv(40, 14).cuboid(-1.25F, -1.75F, -5.3F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F))
		.uv(40, 14).cuboid(-1.75F, -0.5F, -5.3F, 1.0F, 1.0F, 11.0F, new Dilation(0.0F))
		.uv(22, 0).cuboid(-2.0F, -2.0F, 3.2F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(22, 0).cuboid(-2.0F, -2.0F, 1.2F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(22, 0).cuboid(-2.0F, -2.0F, -3.05F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(22, 0).cuboid(-2.0F, -2.0F, -4.55F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.0594F, -7.0653F));

		ModelPartData R_belt = top.addChild("R_belt", ModelPartBuilder.create(), ModelTransform.pivot(-10.1428F, 5.6178F, -2.0F));

		ModelPartData cube_r6 = R_belt.addChild("cube_r6", ModelPartBuilder.create().uv(36, 19).cuboid(3.4569F, -1.7634F, 0.1388F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.2146F, -0.1443F, 0.5317F));

		ModelPartData cube_r7 = R_belt.addChild("cube_r7", ModelPartBuilder.create().uv(36, 19).cuboid(3.3609F, -3.0156F, 0.0761F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.1522F, -0.1317F, 0.6775F));

		ModelPartData cube_r8 = R_belt.addChild("cube_r8", ModelPartBuilder.create().uv(36, 19).cuboid(3.5773F, -3.1276F, 0.3097F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.1189F, -0.0748F, 0.4486F));

		ModelPartData cube_r9 = R_belt.addChild("cube_r9", ModelPartBuilder.create().uv(36, 19).cuboid(3.8005F, -3.1889F, 0.6916F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.1278F, 0.0283F, 0.2164F));

		ModelPartData cube_r10 = R_belt.addChild("cube_r10", ModelPartBuilder.create().uv(36, 19).cuboid(4.3674F, -3.1154F, 0.5611F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.1309F, 0.0F, 0.0F));

		ModelPartData cube_r11 = R_belt.addChild("cube_r11", ModelPartBuilder.create().uv(36, 19).cuboid(-2.0029F, -7.0688F, -4.9194F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, 0.1309F));

		ModelPartData cube_r12 = R_belt.addChild("cube_r12", ModelPartBuilder.create().uv(36, 19).cuboid(-0.3131F, -4.5577F, 0.0771F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.1657F, 0.2139F, 1.9273F));

		ModelPartData cube_r13 = R_belt.addChild("cube_r13", ModelPartBuilder.create().uv(36, 19).cuboid(0.8665F, -4.3824F, -0.2493F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.2166F, 0.2038F, 1.8905F));

		ModelPartData cube_r14 = R_belt.addChild("cube_r14", ModelPartBuilder.create().uv(36, 19).cuboid(-2.2283F, 3.6093F, -0.2683F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.1759F, -0.2549F, -1.122F));

		ModelPartData cube_r15 = R_belt.addChild("cube_r15", ModelPartBuilder.create().uv(36, 19).cuboid(1.947F, -4.8261F, -0.5533F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.2012F, 0.3249F, 2.0542F));

		ModelPartData cube_r16 = R_belt.addChild("cube_r16", ModelPartBuilder.create().uv(36, 19).cuboid(2.6977F, -4.8013F, -0.757F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.2634F, 0.4025F, 1.9892F));

		ModelPartData cube_r17 = R_belt.addChild("cube_r17", ModelPartBuilder.create().uv(36, 19).cuboid(3.7888F, -4.5576F, -0.9085F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.2849F, 0.42F, 1.9349F));

		ModelPartData cube_r18 = R_belt.addChild("cube_r18", ModelPartBuilder.create().uv(36, 19).cuboid(0.1272F, -7.0691F, -1.0931F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.1886F, 0.4688F, 2.9574F));

		ModelPartData cube_r19 = R_belt.addChild("cube_r19", ModelPartBuilder.create().uv(36, 19).cuboid(2.8362F, -6.0103F, -1.0931F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.0922F, 0.4949F, 2.3654F));

		ModelPartData cube_r20 = R_belt.addChild("cube_r20", ModelPartBuilder.create().uv(36, 19).cuboid(-0.1616F, -7.1662F, -0.9052F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.2679F, 0.3268F, 3.0728F));

		ModelPartData cube_r21 = R_belt.addChild("cube_r21", ModelPartBuilder.create().uv(36, 19).cuboid(-0.9787F, -7.0902F, -1.2856F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.2972F, 0.2261F, -2.9507F));

		ModelPartData cube_r22 = R_belt.addChild("cube_r22", ModelPartBuilder.create().uv(36, 19).cuboid(-1.7082F, -6.8052F, -1.7908F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.2578F, 0.0862F, -2.7187F));

		ModelPartData cube_r23 = R_belt.addChild("cube_r23", ModelPartBuilder.create().uv(36, 19).cuboid(-2.3586F, -6.4288F, -2.1556F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.2184F, 0.0405F, -2.4632F));

		ModelPartData cube_r24 = R_belt.addChild("cube_r24", ModelPartBuilder.create().uv(36, 19).cuboid(-2.7112F, -6.1494F, -2.4824F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.1764F, -0.002F, -2.2679F));

		ModelPartData cube_r25 = R_belt.addChild("cube_r25", ModelPartBuilder.create().uv(36, 19).cuboid(-2.5166F, -5.8189F, -3.3091F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.091F, 0.0089F, -2.1365F));

		ModelPartData cube_r26 = R_belt.addChild("cube_r26", ModelPartBuilder.create().uv(36, 19).cuboid(-2.1575F, -5.4169F, -3.9536F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.0049F, 0.0084F, -2.0056F));

		ModelPartData cube_r27 = R_belt.addChild("cube_r27", ModelPartBuilder.create().uv(36, 19).cuboid(-1.3679F, -5.2963F, -4.1795F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.0017F, -0.0008F, -1.9635F));

		ModelPartData cube_r28 = R_belt.addChild("cube_r28", ModelPartBuilder.create().uv(36, 19).cuboid(-0.7477F, -5.2565F, -4.3794F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.0017F, -0.0008F, -1.8762F));

		ModelPartData cube_r29 = R_belt.addChild("cube_r29", ModelPartBuilder.create().uv(36, 19).cuboid(-0.0579F, -5.2286F, -4.4794F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.0017F, -0.0008F, -1.8326F));

		ModelPartData cube_r30 = R_belt.addChild("cube_r30", ModelPartBuilder.create().uv(36, 19).cuboid(0.1021F, -5.3186F, -4.5788F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.0017F, -0.0008F, -1.658F));

		ModelPartData cube_r31 = R_belt.addChild("cube_r31", ModelPartBuilder.create().uv(36, 19).cuboid(0.4618F, -5.4091F, -4.6796F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.0018F, -0.0005F, -1.5271F));

		ModelPartData cube_r32 = R_belt.addChild("cube_r32", ModelPartBuilder.create().uv(36, 19).cuboid(1.0164F, -5.4715F, -4.7801F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, -0.0019F, -0.0003F, -1.4399F));

		ModelPartData cube_r33 = R_belt.addChild("cube_r33", ModelPartBuilder.create().uv(36, 19).cuboid(1.0859F, -5.6858F, -4.8694F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, -1.2654F));

		ModelPartData cube_r34 = R_belt.addChild("cube_r34", ModelPartBuilder.create().uv(36, 19).cuboid(0.6081F, -6.0559F, -4.9194F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, -1.0036F));

		ModelPartData cube_r35 = R_belt.addChild("cube_r35", ModelPartBuilder.create().uv(36, 19).cuboid(1.3808F, -6.1221F, -4.9194F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, -0.9599F));

		ModelPartData cube_r36 = R_belt.addChild("cube_r36", ModelPartBuilder.create().uv(36, 19).cuboid(1.8721F, -6.3095F, -4.9194F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, -0.8727F));

		ModelPartData cube_r37 = R_belt.addChild("cube_r37", ModelPartBuilder.create().uv(36, 19).cuboid(1.2179F, -6.8063F, -4.9194F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, -0.6109F));

		ModelPartData cube_r38 = R_belt.addChild("cube_r38", ModelPartBuilder.create().uv(36, 19).cuboid(1.0556F, -7.0702F, -4.9194F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, -0.4363F));

		ModelPartData L_belt = top.addChild("L_belt", ModelPartBuilder.create(), ModelTransform.pivot(10.1428F, 5.6178F, -2.0F));

		ModelPartData cube_r39 = L_belt.addChild("cube_r39", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-4.4569F, -1.7634F, 0.1388F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.2146F, 0.1443F, -0.5317F));

		ModelPartData cube_r40 = L_belt.addChild("cube_r40", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-4.3609F, -3.0156F, 0.0761F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.1522F, 0.1317F, -0.6775F));

		ModelPartData cube_r41 = L_belt.addChild("cube_r41", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-4.5773F, -3.1276F, 0.3097F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.1189F, 0.0748F, -0.4486F));

		ModelPartData cube_r42 = L_belt.addChild("cube_r42", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-4.8005F, -3.1889F, 0.6916F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.1278F, -0.0283F, -0.2164F));

		ModelPartData cube_r43 = L_belt.addChild("cube_r43", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-5.3674F, -3.1154F, 0.5611F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.1309F, 0.0F, 0.0F));

		ModelPartData cube_r44 = L_belt.addChild("cube_r44", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(1.0029F, -7.0688F, -4.9194F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, -0.1309F));

		ModelPartData cube_r45 = L_belt.addChild("cube_r45", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-0.6869F, -4.5577F, 0.0771F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.1657F, -0.2139F, -1.9273F));

		ModelPartData cube_r46 = L_belt.addChild("cube_r46", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-1.8665F, -4.3824F, -0.2493F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.2166F, -0.2038F, -1.8905F));

		ModelPartData cube_r47 = L_belt.addChild("cube_r47", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(1.2283F, 3.6093F, -0.2683F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.1759F, 0.2549F, 1.122F));

		ModelPartData cube_r48 = L_belt.addChild("cube_r48", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-2.947F, -4.8261F, -0.5533F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.2012F, -0.3249F, -2.0542F));

		ModelPartData cube_r49 = L_belt.addChild("cube_r49", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-3.6977F, -4.8013F, -0.757F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.2634F, -0.4025F, -1.9892F));

		ModelPartData cube_r50 = L_belt.addChild("cube_r50", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-4.7888F, -4.5576F, -0.9085F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.2849F, -0.42F, -1.9349F));

		ModelPartData cube_r51 = L_belt.addChild("cube_r51", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-1.1272F, -7.0691F, -1.0931F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.1886F, -0.4688F, -2.9574F));

		ModelPartData cube_r52 = L_belt.addChild("cube_r52", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-3.8362F, -6.0103F, -1.0931F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.0922F, -0.4949F, -2.3654F));

		ModelPartData cube_r53 = L_belt.addChild("cube_r53", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-0.8384F, -7.1662F, -0.9052F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.2679F, -0.3268F, -3.0728F));

		ModelPartData cube_r54 = L_belt.addChild("cube_r54", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-0.0213F, -7.0902F, -1.2856F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.2972F, -0.2261F, 2.9507F));

		ModelPartData cube_r55 = L_belt.addChild("cube_r55", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(0.7082F, -6.8052F, -1.7908F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.2578F, -0.0862F, 2.7187F));

		ModelPartData cube_r56 = L_belt.addChild("cube_r56", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(1.3586F, -6.4288F, -2.1556F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.2184F, -0.0405F, 2.4632F));

		ModelPartData cube_r57 = L_belt.addChild("cube_r57", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(1.7112F, -6.1494F, -2.4824F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.1764F, 0.002F, 2.2679F));

		ModelPartData cube_r58 = L_belt.addChild("cube_r58", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(1.5166F, -5.8189F, -3.3091F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.091F, -0.0089F, 2.1365F));

		ModelPartData cube_r59 = L_belt.addChild("cube_r59", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(1.1575F, -5.4169F, -3.9536F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.0049F, -0.0084F, 2.0056F));

		ModelPartData cube_r60 = L_belt.addChild("cube_r60", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(0.3679F, -5.2963F, -4.1795F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.0017F, 0.0008F, 1.9635F));

		ModelPartData cube_r61 = L_belt.addChild("cube_r61", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-0.2523F, -5.2565F, -4.3794F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.0017F, 0.0008F, 1.8762F));

		ModelPartData cube_r62 = L_belt.addChild("cube_r62", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-0.9421F, -5.2286F, -4.4794F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.0017F, 0.0008F, 1.8326F));

		ModelPartData cube_r63 = L_belt.addChild("cube_r63", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-1.1021F, -5.3186F, -4.5788F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.0017F, 0.0008F, 1.658F));

		ModelPartData cube_r64 = L_belt.addChild("cube_r64", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-1.4618F, -5.4091F, -4.6796F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.0018F, 0.0005F, 1.5271F));

		ModelPartData cube_r65 = L_belt.addChild("cube_r65", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-2.0164F, -5.4715F, -4.7801F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, -0.0019F, 0.0003F, 1.4399F));

		ModelPartData cube_r66 = L_belt.addChild("cube_r66", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-2.0859F, -5.6858F, -4.8694F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, 1.2654F));

		ModelPartData cube_r67 = L_belt.addChild("cube_r67", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-1.6081F, -6.0559F, -4.9194F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, 1.0036F));

		ModelPartData cube_r68 = L_belt.addChild("cube_r68", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-2.3808F, -6.1221F, -4.9194F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, 0.9599F));

		ModelPartData cube_r69 = L_belt.addChild("cube_r69", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-2.8721F, -6.3095F, -4.9194F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, 0.8727F));

		ModelPartData cube_r70 = L_belt.addChild("cube_r70", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-2.2179F, -6.8063F, -4.9194F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, 0.6109F));

		ModelPartData cube_r71 = L_belt.addChild("cube_r71", ModelPartBuilder.create().uv(36, 19).mirrored().cuboid(-2.0556F, -7.0702F, -4.9194F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.2754F, -8.609F, 3.9194F, 0.0F, 0.0F, 0.4363F));

		ModelPartData legs = sentry.addChild("legs", ModelPartBuilder.create(), ModelTransform.pivot(0.1429F, 1.7431F, 0.3925F));

		ModelPartData legs_joint = legs.addChild("legs_joint", ModelPartBuilder.create().uv(29, 7).cuboid(-2.0F, 0.5F, 1.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.1429F, -0.2431F, -0.1425F));

		ModelPartData cube_r72 = legs_joint.addChild("cube_r72", ModelPartBuilder.create().uv(10, 25).cuboid(-1.5F, -3.0F, -3.5F, 2.0F, 5.0F, 6.0F, new Dilation(0.0F))
		.uv(10, 25).mirrored().cuboid(4.5F, -3.0F, -3.5F, 2.0F, 5.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.5F, 2.25F, 0.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData RB = legs.addChild("RB", ModelPartBuilder.create(), ModelTransform.pivot(-2.3929F, 2.5859F, 1.3412F));

		ModelPartData cube_r73 = RB.addChild("cube_r73", ModelPartBuilder.create().uv(1, 25).cuboid(-0.25F, -7.0F, -2.5F, 1.0F, 13.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.25F, 0.6711F, 6.5163F, 1.1781F, 0.0F, 0.0F));

		ModelPartData LB = legs.addChild("LB", ModelPartBuilder.create(), ModelTransform.pivot(2.1071F, 2.5859F, 1.3412F));

		ModelPartData cube_r74 = LB.addChild("cube_r74", ModelPartBuilder.create().uv(1, 25).mirrored().cuboid(0.5F, -7.0F, -2.5F, 1.0F, 13.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.25F, 0.6711F, 6.5163F, 1.1781F, 0.0F, 0.0F));

		ModelPartData RF = legs.addChild("RF", ModelPartBuilder.create(), ModelTransform.pivot(-3.1932F, 0.2352F, -2.3467F));

		ModelPartData cube_r75 = RF.addChild("cube_r75", ModelPartBuilder.create().uv(27, 25).cuboid(-0.8901F, -1.1247F, -7.9269F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0503F, 1.3502F, -0.0458F, 0.6843F, 0.7022F, -0.1514F));

		ModelPartData LF = legs.addChild("LF", ModelPartBuilder.create(), ModelTransform.pivot(2.9074F, 0.2351F, -2.3467F));

		ModelPartData cube_r76 = LF.addChild("cube_r76", ModelPartBuilder.create().uv(27, 25).mirrored().cuboid(-1.0F, -1.0F, -3.5F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(1.6997F, 4.3502F, -2.7958F, 0.6843F, -0.7022F, 0.1514F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(SentryEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		if (entity.getDeployed() > 1) {
			this.setHeadAngles(netHeadYaw, headPitch, entity.getBuildingRotation());
		}

		this.updateAnimation(entity.deployAnimationState, Level2SentryAnimations.LEVEL_2_SENTRY_DEPLOY, ageInTicks, 1f);
		this.updateAnimation(entity.shootAnimationState, Level2SentryAnimations.LEVEL_2_SENTRY_SHOOT, ageInTicks, 1f);
		this.updateAnimation(entity.suckAnimationState, Level2SentryAnimations.LEVEL_2_SENTRY_SUCK, ageInTicks, 0.25f);
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