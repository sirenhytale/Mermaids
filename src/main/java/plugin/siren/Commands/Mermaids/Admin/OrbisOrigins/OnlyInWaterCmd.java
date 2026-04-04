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

public class OnlyInWaterCmd extends AbstractPlayerCommand {
    public OnlyInWaterCmd() {
        super("onlyinwater", "server.commands.mermaids.orbisOrigins.onlyInWater.onlyinwater.desc");

        this.requirePermission("mermaids.admin.orbisorigins.onlyinwater");
    }

    RequiredArg<Boolean> onlyInWaterArg = this.withRequiredArg("Mermaid only in water", "server.commands.mermaids.admin.orbisOrigins.onlyInWater.arg0.desc", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean onlyInWater = onlyInWaterArg.get(commandContext);

        Mermaids.getOrbisOriginsConfig().get().setMermaidOnlyInWater(onlyInWater);
        Mermaids.getOrbisOriginsConfig().save();

        OrbisOriginsRegistry.mermaidOnlyInWater();

        String playerTranslationId = "";
        String consoleTranslationId = "";
        if (onlyInWater) {
            playerTranslationId = "server.commands.mermaids.admin.orbisOrigins.onlyInWater.playerMsg.enabled";
            consoleTranslationId = "server.commands.mermaids.admin.orbisOrigins.onlyInWater.consoleMsg.enabled";
        } else {
            playerTranslationId = "server.commands.mermaids.admin.orbisOrigins.onlyInWater.playerMsg.disabled";
            consoleTranslationId = "server.commands.mermaids.admin.orbisOrigins.onlyInWater.consoleMsg.disabled";
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