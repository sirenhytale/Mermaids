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

public class MermaidGlowRadiusCmd extends AbstractPlayerCommand {
    public MermaidGlowRadiusCmd() {
        super("mermaidglowradius", "server.commands.mermaids.admin.mermaidGlowRadius.desc");

        this.requirePermission("mermaids.admin.mermaidglow");
    }

    RequiredArg<Integer> mermaidGlowRadiusArg = this.withRequiredArg("Glow Radius", "server.commands.mermaids.admin.mermaidGlowRadius.arg0.desc", ArgTypes.INTEGER);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        int mermaidGlowRadius = mermaidGlowRadiusArg.get(commandContext);

        Mermaids.getConfig().get().setLightRadius(mermaidGlowRadius);
        Mermaids.getConfig().save();

        String playerTranslationId = "server.commands.mermaids.admin.mermaidGlowRadius.playerMsg.set";
        Message playerMessage = Message.translation(playerTranslationId).param("radius", mermaidGlowRadius);

        if(player != null) {
            player.sendMessage(playerMessage);

            String consoleTranslationId = "commands.mermaids.admin.mermaidGlowRadius.consoleMsg.set";
            String consoleMessage = Message.translation(consoleTranslationId).param("username", player.getDisplayName()).param("radius", mermaidGlowRadius).getAnsiMessage();
            Mermaids.LOGGER.atInfo().log(consoleMessage);
        }else{
            String consoleTranslationId = "commands.mermaids.admin.mermaidGlowRadius.consoleMsg.set";
            String consoleMessage = Message.translation(consoleTranslationId).param("username", "Unknown").param("radius", mermaidGlowRadius).getAnsiMessage();
            Mermaids.LOGGER.atInfo().log(consoleMessage);
        }
    }
}