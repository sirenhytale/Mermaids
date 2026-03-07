package plugin.siren.Commands.Mermaids;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidSettings;

import javax.annotation.Nonnull;

public class ToggleMermaid extends AbstractPlayerCommand {
    public ToggleMermaid() {
        super("toggle", "Toggles if you can be mermaid or not.");
        this.requirePermission("mermaids.toggle");
        this.setPermissionGroup(GameMode.Creative);
    }

    RequiredArg<Boolean> msgMerToggleArg = this.withRequiredArg("Allow Mermaid Transformations", "Boolean to allow for the mermaid transformations to happen.", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean merToggle = msgMerToggleArg.get(commandContext);

        MermaidSettings mermaidSettings = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());
        mermaidSettings.setToggleMermaid(merToggle);

        String toggledStr = "";
        if (merToggle) {
            toggledStr = "Enabled";
        } else {
            toggledStr = "Disabled";
        }
        player.sendMessage(Message.raw("You have " + toggledStr + " transforming into a mermaid."));

        if(Mermaids.getConfig().get().ifConsoleLogs()) {
            Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has toggled transforming into a Mermaid: " + String.valueOf(merToggle) + ".");
        }
    }
}