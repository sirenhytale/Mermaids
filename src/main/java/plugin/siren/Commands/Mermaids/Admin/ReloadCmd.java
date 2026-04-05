package plugin.siren.Commands.Mermaids.Admin;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
import plugin.siren.Mermaids;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class ReloadCmd extends AbstractAsyncCommand {
    public ReloadCmd() {
        super("reload", "server.commands.mermaids.admin.reload.desc");

        this.requirePermission("mermaids.admin.reload");
    }

    @Nonnull
    @Override
    protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
        Mermaids.getConfig().load();
        Mermaids.getOrbisOriginsConfig().load();
        Mermaids.getEndlessLeveingConfig().load();

        Mermaids.LOGGER.atInfo().log("Reloaded all of the Mermaids configs.");

        return CompletableFuture.completedFuture(null);
    }
}