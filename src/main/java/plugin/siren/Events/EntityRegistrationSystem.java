package plugin.siren.Events;

import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefSystem;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.npc.role.Role;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import plugin.siren.Mermaids;
import plugin.siren.Systems.NPCs.MermaidNPCComponent;

import javax.annotation.Nonnull;

/*
 *
 * Author: Dan / Major76
 * Date: 2026/04/26
 * Link: https://bin.danmizu.dev/code/dZ9HZp.java
 *
 * Modified: Siren
 * Date: 2026/04/26
 *
 */

public class EntityRegistrationSystem extends RefSystem<EntityStore> {

    private final ComponentType<EntityStore, MermaidNPCComponent> mermaidNPCComponentType = MermaidNPCComponent.getComponentType();

    @Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        // keep broad query since npcentity component type may be null depending on init order
        return Query.any();
    }

    @Override
    public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason addReason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        // get npc component type safely
        ComponentType<EntityStore, NPCEntity> npcType = NPCEntity.getComponentType();
        if (npcType == null) return;

        NPCEntity npcEntity = store.getComponent(ref, npcType);
        if (npcEntity == null) return;

        String npcTypeId = npcEntity.getNPCTypeId();
        if (npcTypeId == null) return;

        World world = npcEntity.getWorld();
        if(world != null){
            world.execute(() -> {
                boolean isZombie = false;
                /*NPCGroup zombieGroup = NPCGroup.getAssetMap().getAsset("Zombie");
                if(zombieGroup != null) {
                    String[] zombieGroupArray = zombieGroup.getIncludedTags();

                    if (zombieGroupArray != null && zombieGroupArray.length > 0) {
                        for (String group : zombieGroupArray) {

                            Mermaids.LOGGER.atInfo().log(group);
                            if (group.equalsIgnoreCase(npcTypeId)) {
                                isZombie = true;
                                Mermaids.LOGGER.atSevere().log("ZOMBIBE");
                                Mermaids.LOGGER.atInfo().log("ZOMBITE CONFIMRED");
                            }
                        }
                    }

                    if(npcTypeId.length() >= 6){
                        if(npcTypeId.toLowerCase().startsWith("zombie")){
                            isZombie = true;
                            Mermaids.LOGGER.atSevere().log("ZOMBIBE-other");
                        }
                    }
                }*/
                if(npcTypeId.equalsIgnoreCase("Zombie") || npcTypeId.equalsIgnoreCase("Zombie_Aberrant_Small") ||
                        npcTypeId.equalsIgnoreCase("Zombie_Burnt") || npcTypeId.equalsIgnoreCase("Zombie_Frost") || npcTypeId.equalsIgnoreCase("Zombie_Sand")){
                    isZombie = true;
                }

                if(isZombie){
                    if (store.getComponent(ref, mermaidNPCComponentType) != null) return;

                    commandBuffer.putComponent(ref, mermaidNPCComponentType, new MermaidNPCComponent());
                }
                Mermaids.LOGGER.atInfo().log("Entity: " + npcTypeId);
            });
        }
    }

    @Override
    public void onEntityRemove(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl RemoveReason removeReason, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
    }
}