package olivermakesco.de.bmc.impl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import olivermakesco.de.bmc.api.BedrockModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BedrockModCompat implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("bmc");
	public static Map<ResourceLocation, Item> itemRegistry = new HashMap<>();
	public static Map<ResourceLocation, Block> blockRegistry = new HashMap<>();
	public static Map<String, BedrockModInitializer> entrypoints = new HashMap<>();

	@Override
	public void onInitialize() {
		RegistryEntryAddedCallback.event(Registry.ITEM).register((rawId, id, object) -> {
			LOGGER.info("Registering Bedrock Item");
			if (!Objects.equals(id.getNamespace(), "minecraft"))
				BedrockModCompat.itemRegistry.put(id,object);
		});
		RegistryEntryAddedCallback.event(Registry.BLOCK).register((rawId, id, object) -> {
			LOGGER.info("Registering Bedrock Block");
			if (!Objects.equals(id.getNamespace(), "minecraft"))
				BedrockModCompat.blockRegistry.put(id,object);
		});

		List<BedrockModInitializer> initializers = FabricLoader.getInstance().getEntrypoints("bedrock", BedrockModInitializer.class);
		for (BedrockModInitializer initializer : initializers) {
			String id = initializer.init();
			entrypoints.put(id,initializer);
		}
	}
}
