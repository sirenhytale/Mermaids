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
        super("mermaidglowradius", "Set the radius of the Mermaid glow.");

        this.requirePermission("mermaids.admin.mermaidglow");
    }

    RequiredArg<Integer> msgMerGlowRadiusArg = this.withRequiredArg("interger value", "value to set as Mermaid glow (default 33).", ArgTypes.INTEGER);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        int merGlowRadius = msgMerGlowRadiusArg.get(commandContext);

        Mermaids.getConfig().get().setLightRadius(merGlowRadius);
        Mermaids.getConfig().save();

        player.sendMessage(Message.raw("You have changed the mermaid glow radius to " + String.valueOf(merGlowRadius) + "."));

        Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has changed the mermaid glow radius to " + String.valueOf(merGlowRadius) + ".");
    }
}