package plugin.siren.Events;

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
        player.sendMessage(Message.raw("PlayerReadyEvent"));

        Ref<EntityStore> ref = event.getPlayerRef();
        Store<EntityStore> store = ref.getStore();

        MermaidComponent mermaid = new MermaidComponent();
        PlayerSkinComponent skinComp = store.getComponent(ref, PlayerSkinComponent.getComponentType());
        if(skinComp != null) {
            PlayerSkin skin = skinComp.getPlayerSkin();
            mermaid.saveCosmetics(skin);

            mermaid.setOriginalSkin(skinComp);
        }else{
            player.sendMessage(Message.raw("skincomp == null"));
        }
        store.addComponent(ref, Mermaids.get().getMermaidComponentType(), mermaid);

        WaterComponent water = new WaterComponent();
        store.addComponent(ref, Mermaids.get().getWaterComponentType(), water);

        player.sendMessage(Message.raw("You now have Water Compontent!"));
    }
}
