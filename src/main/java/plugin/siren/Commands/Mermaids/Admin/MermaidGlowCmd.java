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

public class MermaidGlowCmd extends AbstractPlayerCommand {
    public MermaidGlowCmd() {
        super("mermaidglow", "Toggles to allow the mermaid model to glow.");

        this.requirePermission("mermaids.admin.mermaidglow");
    }

    RequiredArg<Boolean> msgMerGlowArg = this.withRequiredArg("Allow Mermaid Glow", "Boolean to toggle mermaid glow.", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean merGlow = msgMerGlowArg.get(commandContext);

        Mermaids.getConfig().get().setMermaidLight(merGlow);
        Mermaids.getConfig().save();

        String toggledStr = "";
        if (merGlow) {
            toggledStr = "Enabled";
        } else {
            toggledStr = "Disabled";
        }
        player.sendMessage(Message.raw("You have " + toggledStr + " mermaid glow."));

        Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has toggled mermaid glow: " + String.valueOf(merGlow) + ".");
    }
}