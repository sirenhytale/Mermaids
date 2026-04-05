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
        super("mermaidonland", "server.commands.mermaids.admin.mermaidOnLand.desc");

        this.addAliases("onland");

        this.requirePermission("mermaids.admin.mermaidonland");
    }

    RequiredArg<Boolean> mermaidOnLandArg = this.withRequiredArg("Toggle Always being a Mermaid", "server.commands.mermaids.admin.mermaidOnLand.arg0.desc", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean mermaidOnLand = mermaidOnLandArg.get(commandContext);

        Mermaids.getConfig().get().setMermaidOnLand(mermaidOnLand);
        Mermaids.getConfig().save();

        String playerTranslationId = "";
        String consoleTranslationId = "";
        if (mermaidOnLand) {
            playerTranslationId = "server.commands.mermaids.admin.mermaidOnLand.playerMsg.enabled";
            consoleTranslationId = "server.commands.mermaids.admin.mermaidOnLand.consoleMsg.enabled";
        } else {
            playerTranslationId = "server.commands.mermaids.admin.mermaidOnLand.playerMsg.disabled";
            consoleTranslationId = "server.commands.mermaids.admin.mermaidOnLand.consoleMsg.disabled";
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