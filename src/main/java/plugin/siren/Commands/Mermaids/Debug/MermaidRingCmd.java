package plugin.siren.Commands.Mermaids.Debug;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.InventoryComponent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.transaction.ItemStackTransaction;
import com.hypixel.hytale.server.core.inventory.transaction.Transaction;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;

import javax.annotation.Nonnull;

public class MermaidRingCmd extends AbstractPlayerCommand {
    public MermaidRingCmd() {
        super("givemermaidring", "server.commands.mermaids.debug.giveMermaidRing.desc");

        this.requirePermission("mermaids.debug.mermaidring");
        this.setPermissionGroup(GameMode.Creative);
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        if(player != null) {
            InventoryComponent.Hotbar hotbarComponent = store.getComponent(ref, InventoryComponent.Hotbar.getComponentType());
            if(hotbarComponent != null) {
                ItemStackTransaction itemStackTransaction = hotbarComponent.getInventory().addItemStack(new ItemStack("Mermaids_Weapon_Spell_Ring", 1));

                if(itemStackTransaction.getRemainder() == null) {
                    String playerTranslationId = "server.commands.mermaids.debug.giveMermaidRing.playerMsg.received";
                    player.sendMessage(Message.translation(playerTranslationId));

                    String consoleTranslationId = "server.commands.mermaids.debug.giveMermaidRing.consoleMsg.received";
                    String consoleMessage = Message.translation(consoleTranslationId).param("username", player.getDisplayName()).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }else{
                    InventoryComponent.Storage storageComponent = store.getComponent(ref, InventoryComponent.Storage.getComponentType());
                    if(storageComponent != null){
                        ItemStackTransaction StorageItemStackTransaction = storageComponent.getInventory().addItemStack(new ItemStack("Mermaids_Weapon_Spell_Ring", 1));

                        if(StorageItemStackTransaction.getRemainder() == null) {
                            String playerTranslationId = "server.commands.mermaids.debug.giveMermaidRing.playerMsg.received";
                            player.sendMessage(Message.translation(playerTranslationId));

                            String consoleTranslationId = "server.commands.mermaids.debug.giveMermaidRing.consoleMsg.received";
                            String consoleMessage = Message.translation(consoleTranslationId).param("username", player.getDisplayName()).getAnsiMessage();
                            Mermaids.LOGGER.atInfo().log(consoleMessage);
                        }else{
                            String playerTranslationId = "server.commands.mermaids.debug.giveMermaidRing.playerMsg.inventoryFull";
                            player.sendMessage(Message.translation(playerTranslationId));
                        }
                    }
                }
            }
        }
    }
}