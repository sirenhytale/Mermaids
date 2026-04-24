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
import plugin.siren.Systems.MermaidSettingsComponent;

import javax.annotation.Nonnull;
import java.awt.*;

public class GlowCmd extends AbstractPlayerCommand {
    public GlowCmd() {
        super("glow", "server.commands.mermaids.glow.desc");

        this.addAliases("mermaidglow");

        this.requirePermission("mermaids.glow");
    }

    RequiredArg<Boolean> mermaidGlowArg = this.withRequiredArg("Allow Mermaid Glow", "server.commands.mermaids.glow.arg0.desc", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean mermaidGlow = mermaidGlowArg.get(commandContext);

        MermaidSettingsComponent mermaidSettings = store.getComponent(ref, MermaidSettingsComponent.getComponentType());
        if(mermaidSettings != null) {
            mermaidSettings.setMermaidGlow(mermaidGlow);

            if(Mermaids.getConfig().get().getMermaidLight()) {
                String playerTranslationId = "";
                String consoleTranslationId = "";
                if(mermaidGlow) {
                    playerTranslationId = "server.commands.mermaids.glow.playerMsg.enabled";
                    consoleTranslationId = "server.commands.mermaids.glow.playerMsg.enabled";
                }else{
                    playerTranslationId = "server.commands.mermaids.glow.playerMsg.disabled";
                    consoleTranslationId = "server.commands.mermaids.glow.playerMsg.disabled";
                }

                if (player != null) {
                    player.sendMessage(Message.translation(playerTranslationId));

                    String consoleMessage = Message.translation(consoleTranslationId).param("username", player.getDisplayName()).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                } else {
                    String consoleMessage = Message.translation(consoleTranslationId).param("username", "Unknown").getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }
            }else{
                if (player != null) {
                    String playerTranslationId = "server.commands.mermaids.glow.playerMsg.serverDisabled";
                    player.sendMessage(Message.translation(playerTranslationId));
                }
            }
        }else{
            if(player != null){
                String playerTranslationId = "server.commands.mermaids.glow.playerMsg.issue";
                player.sendMessage(Message.translation(playerTranslationId).color(Color.RED));
            }
        }
    }
}