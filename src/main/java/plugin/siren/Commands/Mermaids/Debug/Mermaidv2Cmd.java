package plugin.siren.Commands.Mermaids.Debug;

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

public class Mermaidv2Cmd extends AbstractPlayerCommand {
    public Mermaidv2Cmd() {
        super("mermaidv2", "Toggles to test the Mermaid v2 Model.");

        this.requirePermission("mermaids.debug.mermaidv2");
        this.setPermissionGroup(GameMode.Creative);
    }

    RequiredArg<Boolean> msgMerV2Arg = this.withRequiredArg("Use Model v2", "Boolean to toggle Mermaid v2 Model.", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean merV2 = msgMerV2Arg.get(commandContext);

        MermaidSettings mermaidSettings = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());
        mermaidSettings.setMermaidV2Model(merV2);

        String toggledStr = "";
        if (merV2) {
            toggledStr = "Enabled";
        } else {
            toggledStr = "Disabled";
        }
        player.sendMessage(Message.raw("You have " + toggledStr + " using the Mermaid V2 Tail model (Currently in Development, not final product)."));

        Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has toggled to use the Mermaid Tail Model v2: " + String.valueOf(merV2) + ".");
    }
}