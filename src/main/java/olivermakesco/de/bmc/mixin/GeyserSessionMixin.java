package olivermakesco.de.bmc.mixin;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtMapBuilder;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.ExperimentData;
import com.nukkitx.protocol.bedrock.data.inventory.ComponentItemData;
import com.nukkitx.protocol.bedrock.packet.ItemComponentPacket;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import edu.umd.cs.findbugs.annotations.NonNull;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import olivermakesco.de.bmc.impl.BedrockModCompat;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.session.UpstreamSession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GeyserSession.class)
public class GeyserSessionMixin {
    @Redirect(
            method = "startGame()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/geysermc/geyser/session/UpstreamSession;sendPacket(Lcom/nukkitx/protocol/bedrock/BedrockPacket;)V"
            )
    )
    public void editPacket(UpstreamSession upstreamSession, @NonNull BedrockPacket packet) {
        if (!(packet instanceof StartGamePacket startGamePacket)) return;
        startGamePacket.getExperiments().add(new ExperimentData("data_driven_items", true));
        int startBlockSize = Registry.BLOCK.size() - BedrockModCompat.blockRegistry.size() + 20;

        upstreamSession.sendPacket(startGamePacket);

        int startItemSize = Registry.ITEM.size() - BedrockModCompat.itemRegistry.size() + 20;
        ItemComponentPacket itemComponentPacket = new ItemComponentPacket();
        for (ResourceLocation id : BedrockModCompat.itemRegistry.keySet()) {
            Item item = BedrockModCompat.itemRegistry.get(id);
            NbtMapBuilder propertiesBuilder = NbtMap.builder();
            propertiesBuilder.put("max_stack_size",item.getMaxStackSize());
            propertiesBuilder.put("allow_offhand",true);
            propertiesBuilder.put("hand_equipped",true);
            NbtMap properties = propertiesBuilder.build();

            NbtMapBuilder iconBuilder = NbtMap.builder();
            iconBuilder.put("texture", id.getPath());
            NbtMap icon = iconBuilder.build();

            NbtMapBuilder componentsBuilder = NbtMap.builder();
            componentsBuilder.put("item_properties",properties);
            componentsBuilder.put("minecraft:icon",icon);
            NbtMap components = componentsBuilder.build();

            NbtMapBuilder itemBuilder = NbtMap.builder();
            itemBuilder.put("components",components);
            itemBuilder.put("id",startItemSize++);
            itemBuilder.put("name","zzz"+id);

            itemComponentPacket.getItems().add(new ComponentItemData("zzz"+id,itemBuilder.build()));
        }
        upstreamSession.sendPacket(itemComponentPacket);
    }
}
