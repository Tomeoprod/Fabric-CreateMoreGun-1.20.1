package net.tomeoprod.more_gun.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.MoreGun;

public class MGTags {
    public static class Blocks {
        //public static final TagKey<Block> <TAG NAME> = createTag("<TAG ID>");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(MoreGun.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> AUSTRALIUM = createTag("australium");

        private static TagKey<Item> createTag(@SuppressWarnings("SameParameterValue") String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(MoreGun.MOD_ID, name));
        }
    }
}
