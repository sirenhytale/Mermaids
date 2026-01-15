package plugin.siren.Events;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.WaterComponent;

public class PlayerReadyEventM {
    public static void onPlayerReadyEvent(PlayerReadyEvent event){
        Player player = event.getPlayer();
        player.sendMessage(Message.raw("PlayerReadyEvent"));

        Ref<EntityStore> ref = event.getPlayerRef();
        Store<EntityStore> store = ref.getStore();

        WaterComponent water = new WaterComponent();
        store.addComponent(ref, Mermaids.get().getWaterComponentType(), water);
        player.sendMessage(Message.raw("You now have Water Compontent!"));
    }
}
