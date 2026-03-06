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
import java.util.UUID;

public class ItemIncreaseSpeedCmd extends AbstractPlayerCommand {
    public ItemIncreaseSpeedCmd() {
        super("itemincreasespeed", "Toggles to allow some items to increase the swim movement speed.");

        this.requirePermission("mermaids.admin.itemincreasespeed");
    }

    RequiredArg<Boolean> msgMerItemIncSpeedArg = this.withRequiredArg("Allow Items to Increase Speed", "Boolean to toggle items increasing speed.", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean merItemIncSpeed = msgMerItemIncSpeedArg.get(commandContext);

        Mermaids.getConfig().get().setItemIncreaseSwimSpeed(merItemIncSpeed);
        Mermaids.getConfig().save();

        String toggledStr = "";
        if (merItemIncSpeed) {
            toggledStr = "Enabled";
        } else {
            toggledStr = "Disabled";
        }
        player.sendMessage(Message.raw("You have " + toggledStr + " that some items can increase swim movement speed."));

        Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has toggled how some items can increase swim movement speed: " + String.valueOf(merItemIncSpeed) + ".");
    }
}