package plugin.siren.Events;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.WaterComponent;

public class PlayerReadyEventM {
    public static void onPlayerReadyEvent(PlayerReadyEvent event){
        Player player = event.getPlayer();

        Ref<EntityStore> ref = event.getPlayerRef();
        Store<EntityStore> store = ref.getStore();

        //if(player.getWorld().getName().equals("default")) {
            MermaidComponent merComp = store.getComponent(ref, Mermaids.get().getMermaidComponentType());
            if (merComp == null) {
                MermaidComponent mermaid = new MermaidComponent();

                PlayerSkinComponent skinComp = store.getComponent(ref, PlayerSkinComponent.getComponentType());
                if (skinComp != null) {
                    PlayerSkin skin = skinComp.getPlayerSkin().clone();
                    mermaid.setPlayerSkin(skin);
                    mermaid.setPlayerSkinComponent(skinComp.clone());
                } else {
                    player.sendMessage(Message.raw("Mermaids: Error: PlayerReadyEventM: skincomp == null"));
                }

                Component<EntityStore> movementManager = store.getComponent(ref, MovementManager.getComponentType()).clone();
                mermaid.setMovementManager(movementManager);

                Component<EntityStore> modelComponent = store.getComponent(ref, ModelComponent.getComponentType()).clone();
                mermaid.setModelComponent(modelComponent);

                //player.sendMessage(Message.raw("Set model/movement comp"));

                store.addComponent(ref, Mermaids.get().getMermaidComponentType(), mermaid);
            }

            WaterComponent watComp = store.getComponent(ref, Mermaids.get().getWaterComponentType());
            if (watComp == null) {
                WaterComponent water = new WaterComponent();
                store.addComponent(ref, Mermaids.get().getWaterComponentType(), water);
            }

            if (Mermaids.ifDebug()) {
                player.sendMessage(Message.raw("You now have Water Compontent!"));
            }
        /*}else{
            if (Mermaids.ifDebug()) {
                player.sendMessage(Message.raw("You are aren't in the default world!"));

                MermaidComponent merComp = store.getComponent(ref, Mermaids.get().getMermaidComponentType());
                if(merComp != null) {
                    store.removeComponent(ref, Mermaids.get().getMermaidComponentType());
                    if (Mermaids.ifDebug()) {
                        player.sendMessage(Message.raw("Removed mermaidCompontent!"));
                    }
                }

                WaterComponent watComp = store.getComponent(ref, Mermaids.get().getWaterComponentType());
                if(watComp != null) {
                    store.removeComponent(ref, Mermaids.get().getWaterComponentType());
                    if (Mermaids.ifDebug()) {
                        player.sendMessage(Message.raw("Removed waterCompontent!"));
                    }
                }
            }
        }*/
    }
}
