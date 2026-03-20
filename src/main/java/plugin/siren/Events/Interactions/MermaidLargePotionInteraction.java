package plugin.siren.Events.Interactions;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidSettingsComponent;

import javax.annotation.Nonnull;

public class MermaidLargePotionInteraction extends SimpleInstantInteraction {

    public static final BuilderCodec<MermaidLargePotionInteraction> CODEC = BuilderCodec
            .builder(MermaidLargePotionInteraction.class, MermaidLargePotionInteraction::new, SimpleInstantInteraction.CODEC)
            .documentation("Adds the Mermaid Transformation Permanent potion for the player.")
            .build();

    @Override
    protected void firstRun(@Nonnull InteractionType interactionType, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
        Ref<EntityStore> ref = context.getEntity();
        if (ref == null || !ref.isValid()) {
            Mermaids.get().getLogger().atFine().log("In MermaidLargePotionInteraction, failed to get reference.");
            return;
        }

        CommandBuffer<EntityStore> buffer = context.getCommandBuffer();
        if (buffer == null) {
            Mermaids.get().getLogger().atFine().log("In MermaidLargePotionInteraction, failed to get buffer.");
            return;
        }

        Player player = buffer.getComponent(ref, Player.getComponentType());
        if (player == null) {
            Mermaids.get().getLogger().atFine().log("In MermaidLargePotionInteraction, failed to get player.");
            return;
        }

        MermaidSettingsComponent mermaidSettings = buffer.getComponent(ref, MermaidSettingsComponent.getComponentType());
        if (mermaidSettings == null) {
            Mermaids.get().getLogger().atFine().log("In MermaidLargePotionInteraction, failed to get mermaidSettings Component.");
            return;
        }

        mermaidSettings.setPermanentPotion(true);
    }
}