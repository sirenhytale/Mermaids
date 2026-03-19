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

public class BlockTransformCmd extends AbstractPlayerCommand {
    public BlockTransformCmd() {
        super("blocktransform", "server.commands.mermaids.admin.blockTransform.desc");

        this.requirePermission("mermaids.admin.blocktransform");
    }

    RequiredArg<Boolean> blockTransformArg = this.withRequiredArg("Allow Block Transformation", "server.commands.mermaids.admin.blockTransform.arg0.desc", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean blockTransform = blockTransformArg.get(commandContext);

        Mermaids.getConfig().get().setBlockTransformation(blockTransform);
        Mermaids.getConfig().save();

        String playerTranslationId = "";
        String consoleTranslationId = "";
        if (blockTransform) {
            playerTranslationId = "server.commands.mermaids.admin.blockTransform.playerMsg.enabled";
            consoleTranslationId = "server.commands.mermaids.admin.blockTransform.consoleMsg.enabled";
        } else {
            playerTranslationId = "server.commands.mermaids.admin.blockTransform.playerMsg.disabled";
            consoleTranslationId = "server.commands.mermaids.admin.blockTransform.consoleMsg.disabled";
        }

        if(player != null) {
            player.sendMessage(Message.translation(playerTranslationId));

            String consoleMessage = Message.translation(consoleTranslationId).param("username", player.getDisplayName()).getAnsiMessage();
            Mermaids.LOGGER.atInfo().log(consoleMessage);
        }else{
            String consoleMessage = Message.translation(consoleTranslationId).param("username", "Unknown").getAnsiMessage();
            Mermaids.LOGGER.atInfo().log(consoleMessage);
        }
    }
}