package plugin.siren.Commands.Mermaids.Debug;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;

import javax.annotation.Nonnull;

public class MermaidRingCmd extends AbstractPlayerCommand {
    public MermaidRingCmd() {
        super("givemermaidring", "Gives the player the Mermaid Ring.");

        this.requirePermission("mermaids.debug.mermaidring");
        this.setPermissionGroup(GameMode.Creative);
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        Player playerComponent = store.getComponent(ref, Player.getComponentType());
        playerComponent.getInventory().getCombinedHotbarFirst().addItemStack(new ItemStack("Mermaids_Weapon_Spell_Ring", 1));

        player.sendMessage(Message.raw("You have been given the Mermaid Ring (Currently in Development, not final product)."));

        Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has been given the Mermaid Ring.");
    }
}