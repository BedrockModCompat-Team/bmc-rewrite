package olivermakesco.de.bmc.impl;

import com.google.common.eventbus.Subscribe;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import olivermakesco.de.bmc.items.FabricItemPopulator;
import org.geysermc.geyser.api.GeyserApi;
import org.geysermc.geyser.api.event.EventRegistrar;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import org.geysermc.geyser.api.item.custom.NonVanillaCustomItemData;

public class BedrockModCompatExtension implements EventRegistrar {

    public int vanillaItemAmnt = 0;

    public void registerEvents() {
        GeyserApi.api().eventBus().register(this, this);
        GeyserApi.api().eventBus().subscribe(this, GeyserDefineCustomItemsEvent.class, this::geyserDefineCustomItemsEvent);
        System.out.println("EEEE");
    }

    @Subscribe
    public void geyserDefineCustomItemsEvent(GeyserDefineCustomItemsEvent event)  {
        for (int i = 0; i < Registries.ITEM.size(); i++) {
            if (Registries.ITEM.getId(Registries.ITEM.get(i)).getNamespace().equals("minecraft")) {
               vanillaItemAmnt++;
            }
        }

        System.out.println("EEEE E2");
        System.out.println(vanillaItemAmnt);
        int ip = vanillaItemAmnt + 1;

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
                        .maxDamage(item.getMaxDamage())
                        .creativeCategory(1).build();

                FabricItemPopulator.geyserItemData.add(itemData);

                //System.out.println(id.getPath());
                //System.out.println(id.toString());

                ip++;
            }
        }

        if (FabricItemPopulator.geyserItemData.size() > 0) {
            for (int i = 0; i < FabricItemPopulator.geyserItemData.size(); i++) {
                event.register(FabricItemPopulator.geyserItemData.get(i));
            }
        }

        System.out.println(FabricItemPopulator.geyserItemData.size());
    }
}
