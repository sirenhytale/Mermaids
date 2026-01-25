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
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;

public class PlayerReadyEventM {
    public static void onPlayerReadyEvent(PlayerReadyEvent event){
        World world = event.getPlayer().getWorld();
        world.execute(() -> {
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

                    if (Mermaids.ifDebug()) {
                        player.sendMessage(Message.raw("Set the PlayerSkinComponent"));
                    }
                } else {
                    player.sendMessage(Message.raw("Mermaids: Error: PlayerReadyEventM: skincomp == null"));
                    Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " failed to get Player Skin Component. Error: PlayerReadyEventM: skincomp == null");
                }

                Component<EntityStore> movementManager = store.getComponent(ref, MovementManager.getComponentType()).clone();
                mermaid.setMovementManager((MovementManager) movementManager);
                if (Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("Set the MovementManager"));
                }

                Component<EntityStore> modelComponent = store.getComponent(ref, ModelComponent.getComponentType()).clone();
                mermaid.setModelComponent((ModelComponent) modelComponent);
                if (Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("Set the ModelComponent"));
                }

                store.addComponent(ref, Mermaids.get().getMermaidComponentType(), mermaid);

                if (Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("You now have the Mermaid Component!"));
                }

                Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " now has the Mermaid Component.");
            }else{
                if (Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("You already have the Mermaid Component!"));
                }

                Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " tried to receive Mermaid Component but already has it.");
            }
        });
    }
}
