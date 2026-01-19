package plugin.siren.Commands;

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
import plugin.siren.Systems.MermaidComponent;

import javax.annotation.Nonnull;

public class ToggleMermaid extends AbstractPlayerCommand {
    public ToggleMermaid() {
        super("mermaid", "Toggles if you can be mermaid or not");
        //this.requirePermission(HytalePermissions.fromCommand("mermaids.toggle"));
        this.requirePermission("mermaids.toggle");
    }

    RequiredArg<Boolean> msgMerToggleArg = this.withRequiredArg("toggle boolean", "Boolean to allow for the mermaid transformations to happen.", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        //boolean merTogglePerm = PermissionsModule.get().hasPermission(playerRef.getUuid(), "mermaids.toggle");
        //if(merTogglePerm) {
            boolean merToggle = msgMerToggleArg.get(commandContext);

            MermaidComponent mermaid = store.getComponent(ref, Mermaids.get().getMermaidComponentType());
            mermaid.setToggleMermaid(merToggle);

            String toggledStr = "";
            if (merToggle) {
                toggledStr = "Enabled";
            } else {
                toggledStr = "Disabled";
            }
            player.sendMessage(Message.raw("You have " + toggledStr + " transforming into a mermaid."));
        //}else{
        //    player.sendMessage(Message.raw("You are missing the permission 'mermaids.toggle'."));
        //}
    }
}