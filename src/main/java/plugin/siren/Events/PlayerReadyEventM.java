package plugin.siren.Events;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.WaterComponent;

import java.util.ArrayList;
import java.util.List;

public class PlayerReadyEventM {
    public static void onPlayerReadyEvent(PlayerReadyEvent event){
        Player player = event.getPlayer();

        Ref<EntityStore> ref = event.getPlayerRef();
        Store<EntityStore> store = ref.getStore();

        MermaidComponent merComp = store.getComponent(ref, Mermaids.get().getMermaidComponentType());
        if(merComp == null) {
            MermaidComponent mermaid = new MermaidComponent();
            PlayerSkinComponent skinComp = store.getComponent(ref, PlayerSkinComponent.getComponentType());
            if (skinComp != null) {
                PlayerSkin skin = skinComp.getPlayerSkin().clone();
                mermaid.setPlayerSkin(skin);
            } else {
                player.sendMessage(Message.raw("Mermaids: Error: PlayerReadyEventM: skincomp == null"));
            }
            store.addComponent(ref, Mermaids.get().getMermaidComponentType(), mermaid);
        }

        WaterComponent watComp = store.getComponent(ref, Mermaids.get().getWaterComponentType());
        if(watComp == null) {
            WaterComponent water = new WaterComponent();
            store.addComponent(ref, Mermaids.get().getWaterComponentType(), water);
        }

        if(Mermaids.ifDebug()) {
            player.sendMessage(Message.raw("You now have Water Compontent!"));
        }
    }
}
