package olivermakesco.de.bmc.items;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.geysermc.event.Event;
import org.geysermc.event.subscribe.OwnedSubscriber;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.EventRegistrar;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import org.geysermc.geyser.api.item.custom.NonVanillaCustomItemData;

import java.util.ArrayList;
import java.util.List;

public class FabricItemPopulator {

    public static List<Item> itemRegistry = new ArrayList<>();
    public static List<Identifier> itemIdRegistry = new ArrayList<>();

    public static List<NonVanillaCustomItemData> geyserItemData = new ArrayList<>();

    public static void populateModdedItems() {
        System.out.println("Registering Items....");

        for (int i = 0; i < Registries.ITEM.size(); i++) {
            Item item = Registries.ITEM.get(i);
            Identifier id = Registries.ITEM.getId(item);
            if (!id.getNamespace().equals("minecraft")) {
                itemRegistry.add(item);
                itemIdRegistry.add(id);
            }
        }

        System.out.println(itemRegistry.size());
        System.out.println(itemIdRegistry.size());

    }
}
