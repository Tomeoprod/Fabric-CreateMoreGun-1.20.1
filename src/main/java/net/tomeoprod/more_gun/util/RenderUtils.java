package net.tomeoprod.more_gun.util;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RenderUtils {
    private static void vertex(VertexConsumer builder, Matrix4f pose, Matrix3f normal, int r, int g, int b, int a, float x, float y, float z) {
        builder.vertex(pose, x, y, z)
                .color(r, g, b, a)
                .texture(0, 0)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normal, 0, 1, 0)
                .next();
    }

    public static void renderBox(VertexConsumer builder, Matrix4f pose, Matrix3f normal, int r, int g, int b, int a, float length, float thickness) {
        float x1 = -thickness;
        float y1 = -thickness;
        float z1 = -thickness - length;
        float x2 = thickness;
        float y2 = thickness;
        float z2 = thickness;

        // Front
        vertex(builder, pose, normal, r, g, b, a, x1, y1, z1);
        vertex(builder, pose, normal, r, g, b, a, x1, y2, z1);
        vertex(builder, pose, normal, r, g, b, a, x2, y2, z1);
        vertex(builder, pose, normal, r, g, b, a, x2, y1, z1);

        // Right
        vertex(builder, pose, normal, r, g, b, a, x1, y1, z1);
        vertex(builder, pose, normal, r, g, b, a, x1, y1, z2);
        vertex(builder, pose, normal, r, g, b, a, x1, y2, z2);
        vertex(builder, pose, normal, r, g, b, a, x1, y2, z1);

        // Back
        vertex(builder, pose, normal, r, g, b, a, x1, y1, z2);
        vertex(builder, pose, normal, r, g, b, a, x2, y1, z2);
        vertex(builder, pose, normal, r, g, b, a, x2, y2, z2);
        vertex(builder, pose, normal, r, g, b, a, x1, y2, z2);

        // Left
        vertex(builder, pose, normal, r, g, b, a, x2, y1, z1);
        vertex(builder, pose, normal, r, g, b, a, x2, y2, z1);
        vertex(builder, pose, normal, r, g, b, a, x2, y2, z2);
        vertex(builder, pose, normal, r, g, b, a, x2, y1, z2);

        // Down
        vertex(builder, pose, normal, r, g, b, a, x2, y1, z2);
        vertex(builder, pose, normal, r, g, b, a, x1, y1, z2);
        vertex(builder, pose, normal, r, g, b, a, x1, y1, z1);
        vertex(builder, pose, normal, r, g, b, a, x2, y1, z1);

        // Up
        vertex(builder, pose, normal, r, g, b, a, x1, y2, z1);
        vertex(builder, pose, normal, r, g, b, a, x1, y2, z2);
        vertex(builder, pose, normal, r, g, b, a, x2, y2, z2);
        vertex(builder, pose, normal, r, g, b, a, x2, y2, z1);
    }

    public static void renderBoxInverted(VertexConsumer builder, Matrix4f pose, Matrix3f normal, int r, int g, int b, int a, float length, float thickness) {
        float x1 = -thickness;
        float y1 = -thickness;
        float z1 = -thickness - length;
        float x2 = thickness;
        float y2 = thickness;
        float z2 = thickness;

        // Front
        vertex(builder, pose, normal, r, g, b, a, x1, y1, z1);
        vertex(builder, pose, normal, r, g, b, a, x2, y1, z1);
        vertex(builder, pose, normal, r, g, b, a, x2, y2, z1);
        vertex(builder, pose, normal, r, g, b, a, x1, y2, z1);

        // Right
        vertex(builder, pose, normal, r, g, b, a, x1, y1, z1);
        vertex(builder, pose, normal, r, g, b, a, x1, y2, z1);
        vertex(builder, pose, normal, r, g, b, a, x1, y2, z2);
        vertex(builder, pose, normal, r, g, b, a, x1, y1, z2);

        // Back
        vertex(builder, pose, normal, r, g, b, a, x1, y1, z2);
        vertex(builder, pose, normal, r, g, b, a, x1, y2, z2);
        vertex(builder, pose, normal, r, g, b, a, x2, y2, z2);
        vertex(builder, pose, normal, r, g, b, a, x2, y1, z2);

        // Left
        vertex(builder, pose, normal, r, g, b, a, x2, y1, z1);
        vertex(builder, pose, normal, r, g, b, a, x2, y1, z2);
        vertex(builder, pose, normal, r, g, b, a, x2, y2, z2);
        vertex(builder, pose, normal, r, g, b, a, x2, y2, z1);

        // Down
        vertex(builder, pose, normal, r, g, b, a, x2, y1, z2);
        vertex(builder, pose, normal, r, g, b, a, x2, y1, z1);
        vertex(builder, pose, normal, r, g, b, a, x1, y1, z1);
        vertex(builder, pose, normal, r, g, b, a, x1, y1, z2);

        // Up
        vertex(builder, pose, normal, r, g, b, a, x1, y2, z1);
        vertex(builder, pose, normal, r, g, b, a, x2, y2, z1);
        vertex(builder, pose, normal, r, g, b, a, x2, y2, z2);
        vertex(builder, pose, normal, r, g, b, a, x1, y2, z2);
    }
}
