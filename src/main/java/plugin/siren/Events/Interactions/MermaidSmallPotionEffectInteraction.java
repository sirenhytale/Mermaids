package plugin.siren.Events.Interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.simple.ApplyEffectInteraction;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class MermaidSmallPotionEffectInteraction extends ApplyEffectInteraction {

    public static final BuilderCodec<MermaidSmallPotionEffectInteraction> CODEC = BuilderCodec
            .builder(MermaidSmallPotionEffectInteraction.class, MermaidSmallPotionEffectInteraction::new, ApplyEffectInteraction.CODEC)
            .documentation("Adds the Mermaid Transformation small potion for the player.")
            .build();

    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
        super.firstRun(interactionType, context, cooldownHandler);

        Ref<EntityStore> ref = context.getEntity();
        if (ref == null || !ref.isValid()) {
            Mermaids.get().getLogger().atFine().log("In MermaidSmallPotionEffectInteraction, failed to get reference.");
            return;
        }

        CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
        if (commandBuffer == null) {
            Mermaids.get().getLogger().atFine().log("In MermaidSmallPotionEffectInteraction, failed to get buffer.");
            return;
        }

        Player player = commandBuffer.getComponent(ref, Player.getComponentType());
        if (player == null) {
            Mermaids.get().getLogger().atFine().log("In MermaidSmallPotionEffectInteraction, failed to get player.");
            return;
        }

        MermaidComponent mermaid = commandBuffer.getComponent(ref, Mermaids.get().getMermaidComponentType());
        if (mermaid == null) {
            Mermaids.get().getLogger().atFine().log("In MermaidSmallPotionEffectInteraction, failed to get mermaidComponent.");
            return;
        }

        mermaid.setPotionEffect(true);

        if(Mermaids.ifDebug()){
            player.sendMessage(Message.raw("You have drank the small potion of Mermaid Transformation."));
        }

        float elapsedTime = mermaid.getPotionElapsedTime() / 20f;
        mermaid.addPotionElapsedTime(20f * 180f);

        long scheduleDelay = Math.round(elapsedTime) + 180;

        World world = player.getWorld();
        HytaleServer.SCHEDULED_EXECUTOR.schedule(() -> world.execute(() -> {
            if(ref == null || !ref.isValid()){
                Mermaids.get().getLogger().atFine().log("In MermaidSmallPotionEffectInteraction, failed to get reference in world execute.");
            }else{
                if(mermaid.getPotionElapsedTime() <= 0){
                    mermaid.setPotionEffect(false);
                    mermaid.setPotionElapsedTime(0f);

                    if(Mermaids.ifDebug()){
                        player.sendMessage(Message.raw("The potion of Mermaid Transformation has expired."));
                    }
                }
            }
        }),scheduleDelay, TimeUnit.SECONDS);
    }
}