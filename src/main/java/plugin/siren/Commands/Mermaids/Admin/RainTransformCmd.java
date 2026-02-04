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

public class RainTransformCmd extends AbstractPlayerCommand {
    public RainTransformCmd() {
        super("raintransform", "Toggles to allow users to transform from rain/snow.");

        this.requirePermission("mermaids.admin.raintransform");
    }

    RequiredArg<Boolean> msgMerRainTransArg = this.withRequiredArg("Allow Rain Transformation", "Boolean to toggle rain transformations.", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean merRainTrans = msgMerRainTransArg.get(commandContext);

        Mermaids.getConfig().get().setRainTransformation(merRainTrans);
        Mermaids.getConfig().save();

        String toggledStr = "";
        if (merRainTrans) {
            toggledStr = "Enabled";
        } else {
            toggledStr = "Disabled";
        }
        player.sendMessage(Message.raw("You have " + toggledStr + " rain transformations."));

        Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has toggled rain transformations: " + String.valueOf(merRainTrans) + ".");
    }
}