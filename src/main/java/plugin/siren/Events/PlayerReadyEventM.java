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

        MermaidComponent merComp = store.getComponent(ref, Mermaids.get().getMermaidComponentType());
        if (merComp == null) {
            MermaidComponent mermaid = new MermaidComponent();

            PlayerSkinComponent skinComp = store.getComponent(ref, PlayerSkinComponent.getComponentType());
            if (skinComp != null) {
                PlayerSkin skin = skinComp.getPlayerSkin().clone();
                mermaid.setPlayerSkin(skin);
                mermaid.setPlayerSkinComponent((PlayerSkinComponent) skinComp.clone());
            } else {
                player.sendMessage(Message.raw("Mermaids: Error: PlayerReadyEventM: skincomp == null"));
                Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " failed to get Mermaid Component. Error: PlayerReadyEventM: skincomp == null");
            }

            Component<EntityStore> movementManager = store.getComponent(ref, MovementManager.getComponentType()).clone();
            mermaid.setMovementManager((MovementManager) movementManager);

            Component<EntityStore> modelComponent = store.getComponent(ref, ModelComponent.getComponentType()).clone();
            mermaid.setModelComponent((ModelComponent) modelComponent);

            store.addComponent(ref, Mermaids.get().getMermaidComponentType(), mermaid);
        }

        WaterComponent watComp = store.getComponent(ref, Mermaids.get().getWaterComponentType());
        if (watComp == null) {
            WaterComponent water = new WaterComponent();
            store.addComponent(ref, Mermaids.get().getWaterComponentType(), water);
        }

        if (Mermaids.ifDebug()) {
            player.sendMessage(Message.raw("You now have the Mermaid and Water Compontents!"));
        }

        Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has received Mermaid and Water Components");
    }
}
