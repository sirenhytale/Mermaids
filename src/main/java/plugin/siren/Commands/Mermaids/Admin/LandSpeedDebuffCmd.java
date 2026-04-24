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

public class LandSpeedDebuffCmd extends AbstractPlayerCommand {
    public LandSpeedDebuffCmd() {
        super("landdebuff", "server.commands.mermaids.admin.landspeeddebuff.desc");

        this.addAliases("mermaidonlandspeeddebuff");
        this.addAliases("onlandspeeddebuff");
        this.addAliases("landspeeddebuff");

        this.requirePermission("mermaids.admin.mermaidonland");
    }

    RequiredArg<Boolean> landSpeedDebuffArg = this.withRequiredArg("Toggle Mermaid Land Speed Debuff", "server.commands.mermaids.admin.landspeeddebuff.arg0.desc", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean landSpeedDebuff = landSpeedDebuffArg.get(commandContext);

        Mermaids.getConfig().get().setMermaidOnLandSpeedDebuff(landSpeedDebuff);
        Mermaids.getConfig().save();

        String playerTranslationId = "";
        String consoleTranslationId = "";
        if (landSpeedDebuff) {
            playerTranslationId = "server.commands.mermaids.admin.landspeeddebuff.playerMsg.enabled";
            consoleTranslationId = "server.commands.mermaids.admin.landspeeddebuff.consoleMsg.enabled";
        } else {
            playerTranslationId = "server.commands.mermaids.admin.landspeeddebuff.playerMsg.disabled";
            consoleTranslationId = "server.commands.mermaids.admin.landspeeddebuff.consoleMsg.disabled";
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