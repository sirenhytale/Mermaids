package plugin.siren.Events;

import com.hypixel.hytale.component.Archetype;
import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.event.events.ecs.UseBlockEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;

import javax.annotation.Nonnull;

public class UseBlockEventM extends EntityEventSystem<EntityStore, UseBlockEvent.Pre> {
    public UseBlockEventM() {
        super(UseBlockEvent.Pre.class);
    }
    @Override
    public void handle(int index,
                       @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
                       @Nonnull Store<EntityStore> store,
                       @Nonnull CommandBuffer<EntityStore> commandBuffer,
                       @Nonnull UseBlockEvent.Pre useBlockEvent) {
        Mermaids.LOGGER.atFine().log("To String: " + useBlockEvent.toString());
        Mermaids.LOGGER.atFine().log("Blocktype ID: " + useBlockEvent.getBlockType().getId());
        Mermaids.LOGGER.atFine().log("InteractionType Name: " + useBlockEvent.getInteractionType().name());
    }
    @Override
    public Query<EntityStore> getQuery() {
        return Archetype.empty();
    }
}