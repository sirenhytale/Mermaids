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
        super("transformmode", "server.commands.mermaids.admin.transformMode.desc");

        this.addAliases("mode");

        this.requirePermission("mermaids.admin.mode");
    }

    RequiredArg<Integer> transformModeArg = this.withRequiredArg("Transformation Mode [0 or 1]", "server.commands.mermaids.admin.transformMode.arg0.desc", ArgTypes.INTEGER);

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        int transformMode = transformModeArg.get(commandContext);

        if(transformMode == 0 || transformMode == 1){
            Mermaids.getConfig().get().setTransformationMode(transformMode);
            Mermaids.getConfig().save();

            if(transformMode == 0){
                if(player != null) {
                    String playerTranslationId = "server.commands.mermaids.admin.transformMode.playerMsg.set0";
                    player.sendMessage(Message.translation(playerTranslationId));

                    String consoleTranslationId = "server.commands.mermaids.admin.transformMode.consoleMsg.set0";
                    String consoleMessage = Message.translation(consoleTranslationId).param("username", player.getDisplayName()).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }else{
                    String playerTranslationId = "server.commands.mermaids.admin.transformMode.playerMsg.set0";
                    player.sendMessage(Message.translation(playerTranslationId));

                    String consoleTranslationId = "server.commands.mermaids.admin.transformMode.consoleMsg.set0";
                    String consoleMessage = Message.translation(consoleTranslationId).param("username", "Unknown").getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }
            }

            if(transformMode == 1){
                if(player != null) {
                    String playerTranslationId = "server.commands.mermaids.admin.transformMode.playerMsg.set1";
                    player.sendMessage(Message.translation(playerTranslationId));

                    String consoleTranslationId = "server.commands.mermaids.admin.transformMode.consoleMsg.set1";
                    String consoleMessage = Message.translation(consoleTranslationId).param("username", player.getDisplayName()).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }else{
                    String playerTranslationId = "server.commands.mermaids.admin.transformMode.playerMsg.set1";
                    player.sendMessage(Message.translation(playerTranslationId));

                    String consoleTranslationId = "server.commands.mermaids.admin.transformMode.consoleMsg.set1";
                    String consoleMessage = Message.translation(consoleTranslationId).param("username", "Unknown").getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }
            }
        }else{
            if(player != null){
                String playerTranslationId = "server.commands.mermaids.admin.transformMode.playerMsg.invalid";
                player.sendMessage(Message.translation(playerTranslationId));
            }
        }
    }
}