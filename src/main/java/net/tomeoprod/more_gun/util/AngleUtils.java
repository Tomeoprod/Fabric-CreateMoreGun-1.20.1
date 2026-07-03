package net.tomeoprod.more_gun.util;

public class AngleUtils {
    public static float getRangedAngle(float angle){
        while (angle < 0) {
            angle += 360;
        }

        while (angle > 360) {
            angle -= 360;
        }
        return angle;
    }
}
