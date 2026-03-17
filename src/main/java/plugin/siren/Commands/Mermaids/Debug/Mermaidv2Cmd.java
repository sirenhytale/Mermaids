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
        super("mermaidv2", "server.commands.mermaids.debug.mermaidV2.desc");

        this.requirePermission("mermaids.debug.mermaidv2");
        this.setPermissionGroup(GameMode.Creative);
    }

    RequiredArg<Boolean> mermaidV2Arg = this.withRequiredArg("Use Model v2", "server.commands.mermaids.debug.mermaidV2.arg0.desc", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean mermaidV2 = mermaidV2Arg.get(commandContext);

        MermaidSettings mermaidSettings = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());
        mermaidSettings.setMermaidV2Model(mermaidV2);

        String playerTranslationId = "";
        String consoleTranslationId = "";
        if (mermaidV2) {
            playerTranslationId = "server.commands.mermaids.debug.mermaidV2.playerMsg.enabled";
            consoleTranslationId = "server.commands.mermaids.debug.mermaidV2.consoleMsg.enabled";
        } else {
            playerTranslationId = "server.commands.mermaids.debug.mermaidV2.playerMsg.disabled";
            consoleTranslationId = "server.commands.mermaids.debug.mermaidV2.consoleMsg.disabled";
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