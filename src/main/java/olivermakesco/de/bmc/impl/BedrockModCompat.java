package olivermakesco.de.bmc.impl;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;;
import net.minecraft.util.Identifier;
import olivermakesco.de.bmc.items.FabricItemPopulator;
import olivermakesco.de.bmc.resourcepack.ResourcePackGeneration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class BedrockModCompat implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("bmc");
	public static Map<Identifier, Block> blockRegistry = new HashMap<>();

	@Override
	public void onInitialize() {
		FabricItemPopulator.populateModdedItems();

		ResourcePackGeneration.generateResourcePack();
	}
}
