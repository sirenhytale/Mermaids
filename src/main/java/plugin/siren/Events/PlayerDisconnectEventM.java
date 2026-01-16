package plugin.siren.Events;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.WaterComponent;

public class PlayerDisconnectEventM {
    public static void onPlayerDisconnectEvent(PlayerDisconnectEvent event){
        Ref<EntityStore> ref = event.getPlayerRef().getReference();
        Store<EntityStore> store = ref.getStore();

        MermaidComponent merComp = store.getComponent(ref, Mermaids.get().getMermaidComponentType());
        if(merComp != null) {
            store.removeComponent(ref, Mermaids.get().getMermaidComponentType());
        }

        WaterComponent watComp = store.getComponent(ref, Mermaids.get().getWaterComponentType());
        if(watComp != null) {
            store.removeComponent(ref, Mermaids.get().getWaterComponentType());
        }

        if(Mermaids.ifDebug()) {
            System.out.println("Removed " + event.getPlayerRef().getUsername() + "'s Mermaid and Water components.");
        }
    }
}
