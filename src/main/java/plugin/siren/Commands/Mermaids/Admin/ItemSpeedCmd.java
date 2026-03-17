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

public class ItemSpeedCmd extends AbstractPlayerCommand {
    public ItemSpeedCmd() {
        super("itemspeed", "server.commands.mermaids.admin.itemSpeed.desc");

        this.requirePermission("mermaids.admin.itemspeed");
    }

    RequiredArg<Boolean> itemSpeedArg = this.withRequiredArg("Allow Items to Increase Speed", "server.commands.mermaids.admin.itemSpeed.arg0.desc", ArgTypes.BOOLEAN);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        boolean itemSpeed = itemSpeedArg.get(commandContext);

        Mermaids.getConfig().get().setItemIncreaseSwimSpeed(itemSpeed);
        Mermaids.getConfig().save();

        String playerTranslationId = "";
        String consoleTranslationId = "";
        if (itemSpeed) {
            playerTranslationId = "server.commands.mermaids.admin.itemSpeed.playerMsg.enabled";
            consoleTranslationId = "server.commands.mermaids.admin.itemSpeed.consoleMsg.enabled";
        } else {
            playerTranslationId = "server.commands.mermaids.admin.itemSpeed.playerMsg.disabled";
            consoleTranslationId = "server.commands.mermaids.admin.itemSpeed.consoleMsg.disabled";
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