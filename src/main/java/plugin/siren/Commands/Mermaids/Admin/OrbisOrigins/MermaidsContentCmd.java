package plugin.siren.Commands.Mermaids.Admin.OrbisOrigins;

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
import plugin.siren.Compatibility.OrbisOrigins.OrbisOriginsRegistry;
import plugin.siren.Mermaids;

import javax.annotation.Nonnull;

public class MermaidsContentCmd extends AbstractPlayerCommand {
    public MermaidsContentCmd() {
        super("mermaidscontent", "server.commands.mermaids.admin.orbisOrigins.mermaidsContent.desc");

        this.requirePermission("mermaids.admin.orbisorigins.mermaidscontent");
    }

    RequiredArg<Boolean> mermaidsContentArg = this.withRequiredArg("Mermaids Content", "server.commands.mermaids.admin.orbisOrigins.mermaidsContent.arg0.desc", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean mermaidsContent = mermaidsContentArg.get(commandContext);

        Mermaids.getOrbisOriginsConfig().get().setMermaidsContent(mermaidsContent);
        Mermaids.getOrbisOriginsConfig().save();

        OrbisOriginsRegistry.requireForcedMermaids();

        String playerTranslationId = "";
        String consoleTranslationId = "";
        if (mermaidsContent) {
            playerTranslationId = "server.commands.mermaids.admin.orbisOrigins.mermaidsContent.playerMsg.enabled";
            consoleTranslationId = "server.commands.mermaids.admin.orbisOrigins.mermaidsContent.consoleMsg.enabled";
        } else {
            playerTranslationId = "server.commands.mermaids.admin.orbisOrigins.mermaidsContent.playerMsg.disabled";
            consoleTranslationId = "server.commands.mermaids.admin.orbisOrigins.mermaidsContent.consoleMsg.disabled";
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