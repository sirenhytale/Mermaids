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
import java.util.UUID;

public class BlockTransformCmd extends AbstractPlayerCommand {
    public BlockTransformCmd() {
        super("blocktransform", "Toggles to allow users to transform from liquid blocks.");

        this.requirePermission("mermaids.admin.blocktransform");
    }

    RequiredArg<Boolean> msgMerBlockTransArg = this.withRequiredArg("Allow Block Transformation", "Boolean to toggle block transformations.", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());
        UUID a = playerRef.getUuid();
        String b = a.toString();

        boolean merBlockTrans = msgMerBlockTransArg.get(commandContext);

        Mermaids.getConfig().get().setBlockTransformation(merBlockTrans);
        Mermaids.getConfig().save();

        String toggledStr = "";
        if (merBlockTrans) {
            toggledStr = "Enabled";
        } else {
            toggledStr = "Disabled";
        }
        player.sendMessage(Message.raw("You have " + toggledStr + " block transformations."));

        Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has toggled block transformations: " + String.valueOf(merBlockTrans) + ".");
    }
}