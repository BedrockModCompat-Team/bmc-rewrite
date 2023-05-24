package olivermakesco.de.bmc.impl;

import com.google.common.eventbus.Subscribe;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import olivermakesco.de.bmc.items.FabricItemPopulator;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPreInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.api.item.custom.NonVanillaCustomItemData;

public class BedrockModCompatExtension implements Extension {
    @Subscribe
    public void onPreInitialize(GeyserPreInitializeEvent event) {

    }
    @Subscribe
    public static void onGeyserPreInitializeEvent(GeyserDefineCustomItemsEvent event)  {
        int ip = 1;

        if (FabricItemPopulator.itemRegistry.size() > 0) {
            for (int i = 0; i < FabricItemPopulator.itemRegistry.size(); i++) {
                Item item = FabricItemPopulator.itemRegistry.get(i);
                Identifier id = FabricItemPopulator.itemIdRegistry.get(i);

                NonVanillaCustomItemData itemData = NonVanillaCustomItemData.builder()
                        .name(id.getPath())
                        .identifier(id.toString())
                        .javaId(ip)
                        .allowOffhand(true)
                        .stackSize(item.getMaxCount())
                        .maxDamage(item.getMaxDamage()).build();

                FabricItemPopulator.geyserItemData.add(itemData);

                //System.out.println(id.getPath());
                //System.out.println(id.toString());

                ip++;
            }
        }

        if (FabricItemPopulator.geyserItemData.size() > 0) {
            for (int i = 0; i >= FabricItemPopulator.geyserItemData.size() - 1; i++) {
                event.register(FabricItemPopulator.geyserItemData.get(i));
            }
        }

        System.out.println(FabricItemPopulator.geyserItemData.size());
    }
}
