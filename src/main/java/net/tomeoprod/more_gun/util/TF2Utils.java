package net.tomeoprod.more_gun.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Formatting;
import net.tomeoprod.more_gun.entity.custom.BuildingBoxEntity;
import net.tomeoprod.more_gun.sound.MGSounds;

import java.util.Random;

public class TF2Utils {
    public static float getMaxHealth(int level) {
        float maxHealth;

        switch (level) {
            case 1 -> maxHealth = 150;
            case 2 -> maxHealth = 180;
            case 3 -> maxHealth = 216;
            default -> maxHealth = 0;
        }

        return maxHealth;
    }

    public static void setBuildingEntityProperties(BuildingBoxEntity entity, ItemStack stack, PlayerEntity player, double x, double y, double z) {
        entity.setPos(x, y, z);

        if (player != null) {
            entity.setOwnerId(player.getId());
        }
        entity.setBuildingType(getBuildingType(stack));
        entity.setBuildingLevel(getBuildingLevel(stack));
        entity.setBuildingRotation(player.getHeadYaw());
    }

    public static void setBuildingItemProperties(ItemStack stack, String type, int level, float health) {
        NbtCompound nbtData = stack.getOrCreateNbt();
        nbtData.putString("more_gun.building_type", type);
        nbtData.putInt("more_gun.building_level", level);
        nbtData.putFloat("more_gun.health", health);

        stack.setNbt(nbtData);
    }

    public static void setBuildingItemProperties(ItemStack stack, String type, int level, float health, int ammo, int rocket) {
        NbtCompound nbtData = stack.getOrCreateNbt();
        nbtData.putString("more_gun.building_type", type);
        nbtData.putInt("more_gun.building_level", level);
        nbtData.putFloat("more_gun.health", health);
        nbtData.putInt("more_gun.ammo", ammo);
        nbtData.putInt("more_gun.rocket", rocket);

        stack.setNbt(nbtData);
    }

    public static float getHealth(ItemStack stack) {
        if (stack.getNbt() != null) {
            return stack.getNbt().getFloat("more_gun.health");
        }
        return 0;
    }

    public static int getRocket(ItemStack stack) {
        if (stack.getNbt() != null) {
            return stack.getNbt().getInt("more_gun.rocket");
        }
        return 0;
    }

    public static int getAmmo(ItemStack stack) {
        if (stack.getNbt() != null) {
            return stack.getNbt().getInt("more_gun.ammo");
        }
        return 0;
    }

    public static String getBuildingType(ItemStack stack) {
        if (stack.getNbt() != null) {
            return stack.getNbt().getString("more_gun.building_type");
        }
        return "";
    }

    public static int getBuildingLevel(ItemStack stack) {
        if (stack.getNbt() != null) {
            return stack.getNbt().getInt("more_gun.building_level");
        }
        return 1;
    }

    public static SoundEvent getRandomWrenchSound() {
        int random = new Random().nextInt(2);

        if (random == 0) {
            return MGSounds.WRENCH_HIT_1;

        } else return MGSounds.WRENCH_HIT_2;
    }

    public static Formatting getStatColor(float stat, float maxStat) {
        if (stat == maxStat) {
            return Formatting.DARK_GREEN;
        } else if (stat > maxStat/2) {
            return Formatting.GREEN;
        } else if (stat > maxStat/4) {
            return Formatting.GOLD;
        }
        return Formatting.RED;
    }

    public static Formatting getStatColor(int stat, int maxStat) {
        if (stat == maxStat) {
            return Formatting.DARK_GREEN;
        } else if (stat > maxStat/2) {
            return Formatting.GREEN;
        } else if (stat > maxStat/4) {
            return Formatting.GOLD;
        }
        return Formatting.RED;
    }
}
