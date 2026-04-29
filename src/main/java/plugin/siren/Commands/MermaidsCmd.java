package plugin.siren.Commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.command.system.AbstractCommand;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Commands.Mermaids.*;
import plugin.siren.Mermaids;
import plugin.siren.Utils.UI.MermaidPremiumUIPage;
import plugin.siren.Utils.UI.MermaidUIPage;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class MermaidsCmd extends AbstractCommand {
    public MermaidsCmd(){
        super("mermaids","server.commands.mermaids.desc");

        this.addAliases("mer");
        this.addAliases("mermaid");

        if(Mermaids.ifDebug()){
            this.addSubCommand(new DebugCmd());
        }

        this.addSubCommand(new AdminCmd());
        this.addSubCommand(new BugCmd());
        this.addSubCommand(new InfoCmd());
        this.addSubCommand(new ToggleCmd());
        this.addSubCommand(new GlowCmd());
        this.addSubCommand(new AirCmd());
        this.addSubCommand(new PermPotionRemoveCmd());

        this.requirePermission("mermaids");
    }

    @Nonnull
    @Override
    protected CompletableFuture<Void> execute(@Nonnull CommandContext context) {
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
                        if(Mermaids.ifPremium()){
                            MermaidPremiumUIPage merPage = new MermaidPremiumUIPage(playerRef);
                            player.getPageManager().openCustomPage(ref, store, merPage);
                        }else {
                            MermaidUIPage merPage = new MermaidUIPage(playerRef);
                            player.getPageManager().openCustomPage(ref, store, merPage);
                        }

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
