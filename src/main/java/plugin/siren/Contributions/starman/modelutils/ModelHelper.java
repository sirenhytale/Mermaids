package plugin.siren.Contributions.starman.modelutils;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.ColorLight;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.MermaidSettings;

/*
 *
 * Author: StarMan
 * Version: 1.0-pre
 * Date: 2026/01/15
 * Link: https://github.com/SyperAI/hytale-model-utils
 *
 * Modified: meFroggy
 * Date: 2026/02/17
 *
 */

public class ModelHelper {

    // Applies given skin on given model
    public static Model applySkin(Model model, PlayerSkin playerSkin, MermaidComponent mermaid, MermaidSettings mermaidSettings) {
        ModelComponent modelComponent = (ModelComponent) mermaid.getModelComponent().clone();
        Model playerModel = modelComponent.getModel();

        var attachments = AttachmentsHelper.parseSkin(playerSkin, null, playerModel.getGradientId());

        String texturePath = model.getTexture();
        if(model.getModel().equals("Characters/MermaidPlayer.blockymodel") || model.getModel().equals("Characters/MermaidBigFinPlayer.blockymodel")){
            texturePath = "Characters/PlayerTextures/" + mermaidSettings.getTailColor() + ".png";
        }

        ColorLight modelLight = playerModel.getLight();
        if(Mermaids.getConfig().get().getMermaidLight()){
            model.getLight().radius = (byte) Mermaids.getConfig().get().getLightRadius();
            modelLight = model.getLight();
        }

        Model newMermaidModel = new Model(
                model.getModelAssetId() + "_Skinned",
                playerModel.getScale(),
                playerModel.getRandomAttachmentIds(),
                attachments,
                playerModel.getBoundingBox(),
                model.getModel(),
                texturePath,
                playerModel.getGradientSet(),
                playerModel.getGradientId(),
                model.getEyeHeight(),
                model.getCrouchOffset(),
                model.getSittingOffset(),
                model.getSleepingOffset(),
                model.getAnimationSetMap(),
                playerModel.getCamera(),
                modelLight,
                model.getParticles(),
                model.getTrails(),
                model.getPhysicsValues(),
                playerModel.getDetailBoxes(),
                playerModel.getPhobia(),
                playerModel.getPhobiaModelAssetId()
        );

        return newMermaidModel;
    }

    // Applies given skin on given player reference
    public static void applySkin(Model model, PlayerSkin skin, Ref<EntityStore> ref, CommandBuffer commandBuffer, Player player, MermaidComponent mermaid, MermaidSettings mermaidSettings) {
        var skinnedModel = applySkin(model, skin, mermaid, mermaidSettings);

        commandBuffer.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(skinnedModel));
    }

    public static void applySkin(Model model, PlayerSkin skin, Ref<EntityStore> ref, MermaidComponent mermaid, MermaidSettings mermaidSettings) {
        var skinnedModel = applySkin(model, skin, mermaid, mermaidSettings);

        var store = ref.getStore();
        store.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(skinnedModel));
    }
}