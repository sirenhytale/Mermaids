package plugin.siren.Contributions.starman.modelutils;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

/*
 *
 * Author: StarMan
 * Version: 1.0-pre
 * Date: 1/15/2026
 * Link: https://github.com/SyperAI/hytale-model-utils
 *
 * Modified: meFroggy
 * Date: 1/16/2026
 *
 */

public class ModelHelper {

    // Applies given skin on given model
    public static Model applySkin(Model model, PlayerSkin playerSkin) {
        var attachments = AttachmentsHelper.parseSkin(playerSkin, null, model.getGradientId());

        return new Model(
                model.getModelAssetId() + "_Skinned",
                model.getScale(),
                model.getRandomAttachmentIds(),
                attachments,
                model.getBoundingBox(),
                model.getModel(),
                model.getTexture(),
                model.getGradientSet(),
                model.getGradientId(),
                model.getEyeHeight(),
                model.getCrouchOffset(),
                model.getAnimationSetMap(),
                model.getCamera(),
                model.getLight(),
                model.getParticles(),
                model.getTrails(),
                model.getPhysicsValues(),
                model.getDetailBoxes(),
                model.getPhobia(),
                model.getPhobiaModelAssetId()
        );
    }

    // Applies given skin on given player reference
    public static void applySkin(Model model, PlayerSkin skin, Ref<EntityStore> ref, CommandBuffer commandBuffer) {
        var skinnedModel = applySkin(model, skin);

        commandBuffer.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(skinnedModel));
    }
}