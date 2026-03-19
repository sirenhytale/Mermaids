package plugin.siren.Commands.Mermaids;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Utils.UI.MermaidUIPage;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class MermaidsUI extends AbstractAsyncCommand {
    public MermaidsUI() {
        super("ui", "server.commands.mermaids.mermaid.desc");

        if (Mermaids.getConfig().get().getRequireUIPermission()) {
            this.requirePermission("mermaids.ui");
            this.setPermissionGroup(GameMode.Creative);
        } else {
            this.setPermissionGroup(GameMode.Adventure);
        }
    }

    @Nonnull
    @Override
    protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
        if (!context.isPlayer()) {
            return CompletableFuture.completedFuture(null);
        } else {
            Ref<EntityStore> ref = context.senderAsPlayerRef();
            if (ref != null && ref.isValid()) {
                Store<EntityStore> store = ref.getStore();
                World world = store.getExternalData().getWorld();
                return CompletableFuture.runAsync(() -> {
                    Player player = store.getComponent(ref, Player.getComponentType());
                    PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
                    if (player != null && playerRef != null) {
                        MermaidUIPage merPage = new MermaidUIPage(playerRef);
                        player.getPageManager().openCustomPage(ref, store, merPage);

                        if (Mermaids.ifDebug()) {
                            Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " opened the Mermaids UI.");
                        }
                    }
                }, world);
            } else {
                return CompletableFuture.completedFuture(null);
            }
        }
    }
}