package olivermakesco.de.bmc.testmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.Nullable;

public class TestMod implements ModInitializer {
    private static final String MODID = "bmc-testmod";

    public static final Item TEST_ITEM = new TestItem(new FabricItemSettings().group(CreativeModeTab.TAB_MISC).maxCount(6).rarity(Rarity.EPIC));
    public static final Block TEST_BLOCK = new TestBlock(FabricBlockSettings.of(Material.DIRT, MaterialColor.COLOR_BLUE));
    public static final Block TEST_BLOCK_GLOWING = new Block(FabricBlockSettings.of(Material.AMETHYST, MaterialColor.RAW_IRON).luminance(9));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, id("test_item"), TEST_ITEM);

        registerBlock(id("test_block"), TEST_BLOCK);
        registerBlock(id("test_block_glowing"), TEST_BLOCK_GLOWING);

        Registry.register(Registry.ENCHANTMENT, id("test_enchantment"), new TestEnchantment());
    }

    public static void debugSend(@Nullable Player playerEntity, String text) {
        if (playerEntity != null) playerEntity.displayClientMessage(new TextComponent(text), false);
    }

    private static void registerBlock(ResourceLocation id, Block block) {
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new FabricItemSettings()));
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}
