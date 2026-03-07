package plugin.siren.Commands.Mermaids;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.MermaidSettings;

import javax.annotation.Nonnull;

public class MermaidCompBugCmd extends AbstractPlayerCommand {
    public MermaidCompBugCmd() {
        super("bugfix", "Gives the user the Mermaids Component.");

        this.setPermissionGroup(GameMode.Adventure);
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        world.execute(() -> {
            Player player = store.getComponent(ref, Player.getComponentType());

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
                    player.sendMessage(Message.raw("Mermaids: Error: MermaidCompBugCmd: skincomp == null"));
                    Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " failed to get Mermaid Component. Error: MermaidCompBugCmd: skincomp == null");
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

                player.sendMessage(Message.raw("You now have the Mermaid Component!"));

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " now has the Mermaid Component.");
                }
            }else{
                player.sendMessage(Message.raw("You already have the Mermaid Component!"));

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " tried to receive Mermaid Component in Bug Fix Cmd but already has it.");
                }
            }

            MermaidSettings merSett = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());
            if (merSett == null) {
                MermaidSettings mermaidSettings = new MermaidSettings();

                store.putComponent(ref, Mermaids.get().getMermaidSetingsComponentType(), mermaidSettings);

                player.sendMessage(Message.raw("You now have the Mermaid Settings Component!"));

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " now has the Mermaid Settings Component.");
                }
            }else{
                player.sendMessage(Message.raw("You already have the Mermaid Settings Component!"));

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " tried to receive Mermaid Settings Component but already has it.");
                }
            }
        });
    }
}