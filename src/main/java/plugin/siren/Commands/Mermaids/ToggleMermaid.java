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
import java.awt.*;

public class ToggleMermaid extends AbstractPlayerCommand {
    public ToggleMermaid() {
        super("toggle", "server.commands.mermaids.toggle.desc");
        this.requirePermission("mermaids.toggle");
        this.setPermissionGroup(GameMode.Creative);
    }

    RequiredArg<Boolean> mermaidToggleArg = this.withRequiredArg("Allow Mermaid Transformations", "server.commands.mermaids.toggle.arg0.desc", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean mermaidToggle = mermaidToggleArg.get(commandContext);

        MermaidSettings mermaidSettings = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());
        if(mermaidSettings != null) {
            mermaidSettings.setToggleMermaid(mermaidToggle);

            String playerTranslationId = "server.commands.mermaids.toggle.playerMsg.removed";
            String consoleTranslationId = "server.commands.mermaids.toggle.playerMsg.removed";
            if(player != null) {
                player.sendMessage(Message.translation(playerTranslationId));

                String consoleMessage = Message.translation(consoleTranslationId).param("username", player.getDisplayName()).getAnsiMessage();
                Mermaids.LOGGER.atInfo().log(consoleMessage);
            }else{
                String consoleMessage = Message.translation(consoleTranslationId).param("username", "Unknown").getAnsiMessage();
                Mermaids.LOGGER.atInfo().log(consoleMessage);
            }
        }else{
            if(player != null){
                String playerTranslationId = "server.commands.mermaids.toggle.playerMsg.issue";
                player.sendMessage(Message.translation(playerTranslationId).color(Color.RED));
            }
        }
    }
}