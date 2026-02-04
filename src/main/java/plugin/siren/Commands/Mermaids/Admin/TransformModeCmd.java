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

public class TransformModeCmd extends AbstractPlayerCommand {
    public TransformModeCmd() {
        super("transformmode", "Set the Transformation mode.");

        this.requirePermission("mermaids.admin.mode");
    }

    RequiredArg<Integer> msgTransModeArg = this.withRequiredArg("Transformation Mode [0 or 1]", "value to set as transform mode (0 or 1).", ArgTypes.INTEGER);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        int transMode = msgTransModeArg.get(commandContext);

        if(transMode != 0 && transMode != 1){
            player.sendMessage(Message.raw("Invalid Transformation Mode.\nMode 0 : Transform when entering water.\nMode 1 : Requires user to drink Mermaid Potion to Transform."));
        }

        Mermaids.getConfig().get().setTransformationMode(transMode);
        Mermaids.getConfig().save();

        if(transMode == 0){
            player.sendMessage(Message.raw("You have modified the transformation mode to 0.\nTransforming into a Mermaid when enter water is active."));
            Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has modified the transformation mode to 0 : Transforming into a Mermaid when enter water is active.");
        }

        if(transMode == 1){
            player.sendMessage(Message.raw("You have modified the transformation mode to 1.\nTransforming into a Mermaid requires the player to drink the Mermaid Potion."));
            Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has modified the transformation mode to 1 : Transforming into a Mermaid requires the player to drink the Mermaid Potion.");
        }
    }
}