package plugin.siren.Commands.Mermaids.Bug;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.MermaidSettingsComponent;
import plugin.siren.Utils.API.MermaidsUpdateChecker;

import javax.annotation.Nonnull;
import java.awt.*;

public class LogCmd extends AbstractPlayerCommand {
    public LogCmd() {
        super("log", "server.commands.mermaids.bug.log.desc");

        this.addAliases("info");

        this.setPermissionGroup(GameMode.Adventure);
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        String consoleLog = "Mermaids by Siren" + "\n";

        String version = Mermaids.getVersion();
        String latestVersion = MermaidsUpdateChecker.sendUpdateMessage(MermaidsUpdateChecker.Type.InfoCmd);

        if(player != null){
            Message mermaidTitle = Message.raw("Mermaids by Siren").bold(true).color(Color.BLUE).link("https://www.mermaids.dev/mermaids/");
            Message versionMessage = Message.translation("server.commands.mermaids.bug.log.msg.version").param("version", version).color(Color.CYAN);

            player.sendMessage(mermaidTitle);
            player.sendMessage(versionMessage);

            consoleLog += versionMessage.getAnsiMessage() + "\n";

            if(!version.equalsIgnoreCase(latestVersion)){
                Message updateMessage = Message.translation("server.commands.mermaids.bug.log.msg.update").param("version", version).color(Color.CYAN).link("https://www.curseforge.com/hytale/mods/mermaids");
                player.sendMessage(updateMessage);

                consoleLog += updateMessage.getAnsiMessage() + "\n";
            }

            MermaidComponent mermaidComponent = store.getComponent(ref, MermaidComponent.getComponentType());
            if(mermaidComponent != null){
                Message mermaidComponentMessage = Message.translation("server.commands.mermaids.bug.log.msg.mermaidComponent").param("boolean", "true").color(Color.CYAN);
                player.sendMessage(mermaidComponentMessage);

                consoleLog += mermaidComponentMessage.getAnsiMessage() + "\n";
            }else{
                Message mermaidComponentMessage = Message.translation("server.commands.mermaids.bug.log.msg.mermaidComponent").param("boolean", "false").color(Color.RED);
                player.sendMessage(mermaidComponentMessage);

                consoleLog += mermaidComponentMessage.getAnsiMessage() + "\n";
            }

            MermaidSettingsComponent mermaidSettingsComponent = store.getComponent(ref, MermaidSettingsComponent.getComponentType());
            if(mermaidSettingsComponent != null){
                Message mermaidSettingsComponentMessage = Message.translation("server.commands.mermaids.bug.log.msg.mermaidSettingsComponent").param("boolean", "true").color(Color.CYAN);
                player.sendMessage(mermaidSettingsComponentMessage);

                consoleLog += mermaidSettingsComponentMessage.getAnsiMessage() + "\n";
            }else{
                Message mermaidSettingsComponentMessage = Message.translation("server.commands.mermaids.bug.log.msg.mermaidSettingsComponent").param("boolean", "false").color(Color.RED);
                player.sendMessage(mermaidSettingsComponentMessage);

                consoleLog += mermaidSettingsComponentMessage.getAnsiMessage() + "\n";
            }

            Message requireForcedMermaidMessage = Message.translation("server.commands.mermaids.bug.log.msg.requireForcedMermaid").param("boolean", String.valueOf(Mermaids.getConfig().get().ifRequireForceMermaid())).color(Color.CYAN);
            player.sendMessage(requireForcedMermaidMessage);

            consoleLog += requireForcedMermaidMessage.getAnsiMessage();
        }else{
            consoleLog += "Player is NULL";
        }

        Mermaids.LOGGER.atInfo().log(consoleLog);
    }
}