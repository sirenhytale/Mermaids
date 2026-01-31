package plugin.siren.Events.Interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidSettings;

import javax.annotation.Nonnull;

public class FreezeInteraction extends SimpleInstantInteraction {

    public static final BuilderCodec<FreezeInteraction> CODEC = BuilderCodec
            .builder(FreezeInteraction.class, FreezeInteraction::new, SimpleInstantInteraction.CODEC)
            .documentation("Adds the Mermaid Transformation Permanent potion for the player.")
            .build();

    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
        Ref<EntityStore> ref = context.getEntity();
        if (ref == null || !ref.isValid()) {
            Mermaids.get().getLogger().atFine().log("In FreezeInteraction, failed to get reference.");
            return;
        }

        CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
        if (commandBuffer == null) {
            Mermaids.get().getLogger().atFine().log("In FreezeInteraction, failed to get commandBuffer.");
            return;
        }

        Player player = commandBuffer.getComponent(ref, Player.getComponentType());
        if (player == null) {
            Mermaids.get().getLogger().atFine().log("In FreezeInteraction, failed to get player.");
            return;
        }

        World world = player.getWorld();
        if (world == null) {
            Mermaids.get().getLogger().atFine().log("In FreezeInteraction, failed to get world.");
            return;
        }

        world.execute(() -> {
            TransformComponent transform = commandBuffer.getComponent(ref, TransformComponent.getComponentType());
            if (transform == null) {
                Mermaids.get().getLogger().atFine().log("In FreezeInteraction, failed to get transform component.");
                return;
            }

            Vector3d pos = transform.getPosition();
            //Mermaids.get().getLogger().atInfo().log("Beginning Frezze interaction.");
            int x = (int) Math.floor(pos.getX());
            int y = (int) Math.floor(pos.getY());
            int z = (int) Math.floor(pos.getZ());
            for(int i = x - 4; i <= x+4; i++){
                for(int k = z - 3; k <= z+3; k++){
                    for(int j = y - 4; j <= y+4; j++){
                        int fluidId = world.getFluidId(i,j,k);

                        if(fluidId == 7){//WATER
                            world.setBlock(i,j,k,"Rock_Ice");
                            //Mermaids.get().getLogger().atInfo().log("Froze a Block.");
                        }
                    }
                }
            }
        });
    }
}