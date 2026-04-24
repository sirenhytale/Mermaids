package plugin.siren.Commands.Mermaids;

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
import plugin.siren.Systems.MermaidSettingsComponent;
import plugin.siren.Utils.API.MermaidsUpdateChecker;

import javax.annotation.Nonnull;
import java.awt.*;

public class InfoCmd extends AbstractPlayerCommand {
    public InfoCmd() {
        super("info", "server.commands.mermaids.info.desc");

        this.addAliases("version");
        this.addAliases("v");

        this.setPermissionGroup(GameMode.Adventure);
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        String version = Mermaids.getVersion();
        String latestVersion = MermaidsUpdateChecker.sendUpdateMessage(MermaidsUpdateChecker.Type.InfoCmd);

        if(player != null){
            Message mermaidTitle = Message.raw("Mermaids by Siren").bold(true).color(Color.BLUE).link("https://www.mermaids.dev/mermaids/");
            Message versionMessage = Message.translation("server.commands.mermaids.info.playerMsg.version").param("version", version).color(Color.CYAN);

            player.sendMessage(mermaidTitle);
            player.sendMessage(versionMessage);

            if(!version.equalsIgnoreCase(latestVersion)){
                Message updateMessage = Message.translation("server.commands.mermaids.info.playerMsg.update").param("version", latestVersion).color(Color.CYAN).link("https://www.curseforge.com/hytale/mods/mermaids");
                player.sendMessage(updateMessage);
            }
        }
    }
}