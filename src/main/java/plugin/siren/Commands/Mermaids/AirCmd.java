package plugin.siren.Commands.Mermaids;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.logger.backend.HytaleConsole;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Utils.UI.MermaidUIPage;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AirCmd extends AbstractAsyncCommand {
    public AirCmd() {
        super("air", "server.commands.mermaids.air.desc");

        this.addAliases("shareair");
        this.addAliases("kiss");

        this.setPermissionGroup(GameMode.Adventure);
    }

    @Nonnull
    @Override
    protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
        if (!context.isPlayer()) {
            return CompletableFuture.completedFuture(null);
        } else {
            Ref<EntityStore> ref = context.senderAsPlayerRef();
            if (ref != null && ref.isValid()) {
                Store<EntityStore> store = ref.getStore();
                World world = store.getExternalData().getWorld();
                return CompletableFuture.runAsync(() -> {
                    //Player player = store.getComponent(ref, Player.getComponentType());
                    PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
                    MermaidComponent mermaid = store.getComponent(ref, MermaidComponent.getComponentType());
                    if (/*player != null && */playerRef != null && mermaid != null) {
                        if(mermaid.isMermaid()) {
                            TransformComponent transform = store.getComponent(ref, TransformComponent.getComponentType());
                            if (transform != null) {
                                double x = transform.getPosition().getX();
                                double y = transform.getPosition().getY();
                                double z = transform.getPosition().getZ();

                                boolean sharedAir = false;

                                List<PlayerRef> onlinePlayers = Universe.get().getPlayers();
                                for (PlayerRef plyRef : onlinePlayers) {
                                    if(!playerRef.equals(plyRef)) {
                                        Ref<EntityStore> pRef = plyRef.getReference();
                                        if (pRef != null && pRef.isValid()) {
                                            Store<EntityStore> pStore = pRef.getStore();

                                            if (playerRef.getWorldUuid().equals(plyRef.getWorldUuid())) {
                                                TransformComponent pTransform = pStore.getComponent(pRef, TransformComponent.getComponentType());

                                                double pX = pTransform.getPosition().getX();
                                                double pY = pTransform.getPosition().getY();
                                                double pZ = pTransform.getPosition().getZ();

                                                double diffX = Math.abs(x - pX);
                                                double diffY = Math.abs(y - pY);
                                                double diffZ = Math.abs(z - pZ);

                                                boolean rangeDiff = diffX <= 1.5 && diffY <= 2 && diffZ <= 1.5;
                                                if (rangeDiff) {
                                                    MermaidComponent pMermaid = pStore.getComponent(pRef, MermaidComponent.getComponentType());
                                                    if(pMermaid == null) {
                                                        EntityStatMap pStatMap = pStore.getComponent(pRef, EntityStatMap.getComponentType());
                                                        if (pStatMap != null) {
                                                            pStatMap.maximizeStatValue(DefaultEntityStatTypes.getOxygen());
                                                        }

                                                        plyRef.sendMessage(Message.translation("server.commands.mermaids.air.playerMsg.receivedAir").param("username", playerRef.getUsername()).color(Color.CYAN));
                                                        playerRef.sendMessage(Message.translation("server.commands.mermaids.air.playerMsg.sharedAir").param("username", plyRef.getUsername()).color(Color.CYAN));

                                                        sharedAir = true;
                                                    }else{
                                                        if(!pMermaid.isMermaid()){
                                                            EntityStatMap pStatMap = pStore.getComponent(pRef, EntityStatMap.getComponentType());
                                                            if (pStatMap != null) {
                                                                pStatMap.maximizeStatValue(DefaultEntityStatTypes.getOxygen());
                                                            }

                                                            plyRef.sendMessage(Message.translation("server.commands.mermaids.air.playerMsg.receivedAir").param("username", playerRef.getUsername()).color(Color.CYAN));
                                                            playerRef.sendMessage(Message.translation("server.commands.mermaids.air.playerMsg.sharedAir").param("username", plyRef.getUsername()).color(Color.CYAN));

                                                            sharedAir = true;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                if(!sharedAir){
                                    playerRef.sendMessage(Message.translation("server.commands.mermaids.air.playerMsg.neverSharedAir"));
                                }
                            }
                        }else{
                            playerRef.sendMessage(Message.translation("server.commands.mermaids.air.playerMsg.notAMermaid"));
                        }
                    }
                }, world);
            } else {
                return CompletableFuture.completedFuture(null);
            }
        }
    }
}