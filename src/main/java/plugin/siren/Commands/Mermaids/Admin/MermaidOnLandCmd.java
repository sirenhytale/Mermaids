package plugin.siren.Commands.Mermaids.Admin;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
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

import javax.annotation.Nonnull;

public class MermaidOnLandCmd extends AbstractPlayerCommand {
    public MermaidOnLandCmd() {
        super("mermaidonland", "Toggles to allow users to be a Mermaid on land.");

        this.requirePermission("mermaids.admin.mermaidonland");
    }

    RequiredArg<Boolean> msgMerOnLandArg = this.withRequiredArg("Toggle Always being a Mermaid", "Boolean to toggle Mermaid on Land.", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean merOnLand = msgMerOnLandArg.get(commandContext);

        Mermaids.getConfig().get().setMermaidOnLand(merOnLand);
        Mermaids.getConfig().save();

        String toggledStr = "";
        if (merOnLand) {
            toggledStr = "Enabled";
        } else {
            toggledStr = "Disabled";
        }
        player.sendMessage(Message.raw("You have " + toggledStr + " mermaid transformation on land."));

        Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has toggled mermaid transformation on land: " + String.valueOf(merOnLand) + ".");
    }
}