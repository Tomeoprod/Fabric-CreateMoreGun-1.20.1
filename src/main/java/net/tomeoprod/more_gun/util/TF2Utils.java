package net.tomeoprod.more_gun.util;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.entity.custom.BuildingBoxEntity;
import net.tomeoprod.more_gun.entity.custom.SentryEntity;
import net.tomeoprod.more_gun.particle.MGParticles;
import net.tomeoprod.more_gun.sound.MGSounds;

public class TF2Utils {
    public static BuildingBoxEntity getBuildingEntity(World world, ItemStack stack, PlayerEntity player, double x, double y, double z) {
        BuildingBoxEntity entity;
        switch (getBuildingType(stack)) {
            case "Sentry" -> {
                entity = new SentryEntity(world);

                ((SentryEntity) entity).setAmmo(getAmmo(stack));
                ((SentryEntity) entity).setRockets(getRocket(stack));

            }

            case "Dispenser" -> {
                //Dispenser Coming Soon
                return null;
            }

            case "Teleporter" -> {
                //Teleporter Coming Soon
                return null;
            }

            default -> {
                return null;
            }
        }

        if (player != null) {
            entity.setOwnerId(player.getUuidAsString());
            entity.setOwnerName(player.getName().getString());
            if (player.isSneaking()) {
                entity.setBuildingRotation(player.getHeadYaw() + 180);
            } else entity.setBuildingRotation(player.getHeadYaw());
        } else entity.setOwnerName("None");

        setMaxHealth(entity, getBuildingLevel(stack));

        entity.setPos(x, y, z);
        entity.setHealth(getHealth(stack));
        entity.setBuildingType(getBuildingType(stack));
        entity.setBuildingLevel(getBuildingLevel(stack));

        return entity;
    }

    public static void setMaxHealth(BuildingBoxEntity entity, int level) {
        if (level > 1) {
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(
                    new EntityAttributeModifier(
                            Identifier.of(MoreGun.MOD_ID, "health_upgrade_1").toString(),
                            30,
                            EntityAttributeModifier.Operation.ADDITION
                    ));

            if (level > 2) {
                entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(
                        new EntityAttributeModifier(
                                Identifier.of(MoreGun.MOD_ID, "health_upgrade_2").toString(),
                                36,
                                EntityAttributeModifier.Operation.ADDITION
                        ));
            }
        }
    }

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

    public static void setBuildingItemProperties(ItemStack stack, String type, int level) {
        NbtCompound nbtData = stack.getOrCreateNbt();
        nbtData.putString("more_gun.building_type", type);
        nbtData.putInt("more_gun.building_level", level);
        nbtData.putFloat("more_gun.health", getMaxHealth(level));

        stack.setNbt(nbtData);
    }

    public static void setBuildingItemProperties(ItemStack stack, String type, int level, int ammo, int rocket) {
        NbtCompound nbtData = stack.getOrCreateNbt();
        nbtData.putString("more_gun.building_type", type);
        nbtData.putInt("more_gun.building_level", level);
        nbtData.putFloat("more_gun.health", getMaxHealth(level));
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

    public static int getMaxAmmo(int level) {
        int maxAmmo;
        switch (level) {
            case 2 -> maxAmmo = 200;
            case 3 -> {
                maxAmmo = 200;
            }
            default -> maxAmmo = 150;
        }

        return maxAmmo;
    }

    public static int getMaxRockets(int level) {
        int maxRockets;
        switch (level) {
            case 3 -> {
                maxRockets = 20;
            }
            default -> maxRockets = 0;
        }

        return maxRockets;
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

    public static void playWrenchSound(World world, double x, double y, double z) {
        world.playSound(null, x, y, z, MGSounds.WRENCH_HIT, SoundCategory.PLAYERS, 0.5f, 1f);
    }

    public static void playWrenchFailSound(World world, double x, double y, double z) {
        world.playSound(null, x, y, z, MGSounds.WRENCH_HIT_FAIL, SoundCategory.PLAYERS, 0.5f, 1f);
    }

    public static void PlayBoxSound(World world, double x, double y, double z) {
        world.playSound(null, x, y, z, MGSounds.BOX, SoundCategory.PLAYERS, 0.5f, 1f);
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

    public static void generateMuzzleFlash(World world, SentryEntity entity) {
        double sideDistance = 0.1;
        double frontDistance = 0.6;

        if (entity.getBuildingLevel() >= 2) {
            sideDistance = 0.4;
            frontDistance = 1.1;
        }

        Vec3d front = entity.getRotationVec(1).multiply(frontDistance);
        Vec3d side = new Vec3d(-front.getZ(),0,front.getX()).normalize();
        Vec3d up = front.crossProduct(side).normalize();
        front = front.add(up.multiply(-0.05));
        Vec3d leftSide = front.add(side.multiply(-sideDistance));
        Vec3d rightSide = front.add(side.multiply(sideDistance));

        Vec3d leftParticleSpawnPos = entity.getEyePos().add(leftSide);
        Vec3d rightParticleSpawnPos = entity.getEyePos().add(rightSide);

        if (entity.getBuildingLevel() == 1) {
            world.addImportantParticle(
                    MGParticles.MUZZLE_FLASH_PARTICLE,
                    leftParticleSpawnPos.x,
                    leftParticleSpawnPos.y,
                    leftParticleSpawnPos.z,
                    0,
                    0,
                    0
            );

        } else {
            world.addImportantParticle(
                    MGParticles.MUZZLE_FLASH_PARTICLE,
                    leftParticleSpawnPos.x,
                    leftParticleSpawnPos.y,
                    leftParticleSpawnPos.z,
                    0,
                    0,
                    0
            );

            world.addImportantParticle(
                    MGParticles.MUZZLE_FLASH_PARTICLE,
                    rightParticleSpawnPos.x,
                    rightParticleSpawnPos.y,
                    rightParticleSpawnPos.z,
                    0,
                    0,
                    0
            );

        }
    }

}
