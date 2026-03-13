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
import plugin.siren.Systems.MermaidSettings;
import plugin.siren.Utils.API.UpdateChecker;

import java.awt.*;

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

                store.putComponent(ref, Mermaids.get().getMermaidComponentType(), mermaid);

                if (Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("You now have the Mermaid Component!"));
                }

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " now has the Mermaid Component.");
                }
            }else{
                if (Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("You already have the Mermaid Component!"));

                    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " tried to receive Mermaid Component but already has it.");
                }
            }

            MermaidSettings merSett = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());
            if (merSett == null) {
                MermaidSettings mermaidSettings = new MermaidSettings();

                store.putComponent(ref, Mermaids.get().getMermaidSetingsComponentType(), mermaidSettings);

                if (Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("You now have the Mermaid Settings Component!"));

                    if(Mermaids.getConfig().get().ifConsoleLogs()) {
                        Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " now has the Mermaid Settings Component.");
                    }
                }

                MermaidSettings mermaidSett = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());
                if(mermaidSett != null){
                    if(!Mermaids.ifVersion1()) {
                        if (!mermaidSett.getAlpha200()) {
                            mermaidSett.setAlpha200(true);

                            mermaidSett.setMermaidTail("MermaidV2");
                            mermaidSett.setTailColor("MermaidPlayerGrayscale");
                            mermaidSett.setTailColorV2("MermaidTextureV2");

                            if(Mermaids.getConfig().get().ifConsoleLogs()) {
                                Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has updated saved Mermaid Tail to V2.");
                            }
                        }
                    }else{
                        if(mermaidSett.getMermaidTail().equalsIgnoreCase("MermaidV2")){
                            mermaidSett.setMermaidTail("MermaidBigFinPlayer");
                            mermaidSett.setTailColor("MermaidPlayerGrayscale");
                        }
                    }
                }else{
                    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " failed to get mermaidSett Mermaid Settings Component.");
                }
            }else{
                if (Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("You already have the Mermaid Settings Component!"));

                    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " tried to receive Mermaid Settings Component but already has it.");
                }

                //MermaidSettings mermaidSett = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());
                //if(mermaidSett != null){
                    if(!Mermaids.ifVersion1()) {
                        if (!merSett.getAlpha200()) {
                            merSett.setAlpha200(true);

                            merSett.setMermaidTail("MermaidV2");
                            merSett.setTailColor("MermaidPlayerGrayscale");
                            merSett.setTailColorV2("MermaidTextureV2");

                            if(Mermaids.getConfig().get().ifConsoleLogs()) {
                                Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has updated saved Mermaid Tail to V2.");
                            }
                        }
                    }else{
                        if(merSett.getMermaidTail().equalsIgnoreCase("MermaidV2")){
                            merSett.setMermaidTail("MermaidBigFinPlayer");
                            merSett.setTailColor("MermaidPlayerGrayscale");
                        }
                    }
                //}else{
                //    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " failed to get mermaidSett Mermaid Settings Component.");
                //}

                if(!merSett.getTailColorV2().equalsIgnoreCase("MermaidTextureV2") && !merSett.getTailColorV2().equalsIgnoreCase("MermaidV2Texture_PINK") && !merSett.getTailColorV2().equalsIgnoreCase("MermaidV2Texture_ColorCoded")){
                    merSett.setTailColorV2("MermaidTextureV2");
                }
            }

            String recentVersion = UpdateChecker.checkForUpdate();
            if(!Mermaids.getVersion().equalsIgnoreCase(recentVersion)){
                String versionMessage = "Your Mermaids version is outdated, Mermaids has released v" + recentVersion +".";
                Mermaids.LOGGER.atInfo().log(versionMessage);
                if(player.hasPermission("*") && Mermaids.getConfig().get().ifNewVersion()){
                    player.sendMessage(Message.raw(versionMessage).color(Color.CYAN));
                }
            }
        });
    }
}
