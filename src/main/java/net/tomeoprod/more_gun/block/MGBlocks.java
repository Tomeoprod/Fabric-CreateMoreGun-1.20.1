package net.tomeoprod.more_gun.block;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.block.custom.SonarBlock;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class MGBlocks {
    public static final Block AUSTRALIUM_ORE = register(new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).luminance(12).strength(2f), UniformIntProvider.create(2, 5)), "australium_ore", true);
    public static final Block AUSTRALIUM_BLOCK = register(new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).luminance(12)), "australium_block", true);

    public static final BlockEntry<SonarBlock> SONAR = MoreGun.REGISTRATE.block("sonar", SonarBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.nonOpaque().mapColor(MapColor.SPRUCE_BROWN))
            .addLayer(() -> RenderLayer::getCutoutMipped)
            .transform(BlockStressDefaults.setImpact(4.0))
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
            .item()
            .transform(customItemModel())
            .register();

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        Identifier id = new Identifier(MoreGun.MOD_ID, name);
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void initialize() {}
}
