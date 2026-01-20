package plugin.siren.Commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Utils.UI.MermaidUIPage;

import javax.annotation.Nonnull;

public class MermaidsUI extends AbstractPlayerCommand {
    public MermaidsUI() {
        super("mermaids", "Opens the Mermaids plugin UI");
        this.requirePermission("mermaids.ui");
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        MermaidUIPage merPage = new MermaidUIPage(playerRef);
        player.getPageManager().openCustomPage(ref, store, merPage);

        if(Mermaids.ifDebug()){
            Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " opened the Mermaids UI");
        }
    }
}