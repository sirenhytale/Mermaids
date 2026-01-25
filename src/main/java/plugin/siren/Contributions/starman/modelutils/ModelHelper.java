package plugin.siren.Contributions.starman.modelutils;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.ComponentUpdate;
import com.hypixel.hytale.protocol.ComponentUpdateType;
import com.hypixel.hytale.protocol.EntityUpdate;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.protocol.packets.entities.EntityUpdates;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/*
 *
 * Author: StarMan
 * Version: 1.0-pre
 * Date: 1/15/2026
 * Link: https://github.com/SyperAI/hytale-model-utils
 *
 * Modified: meFroggy
 * Date: 1/22/2026
 *
 */

public class ModelHelper {

    // Applies given skin on given model
    public static Model applySkin(Model model, PlayerSkin playerSkin, MermaidComponent mermaid) {
        ModelComponent modelComponent = (ModelComponent) mermaid.getModelComponent().clone();
        Model playerModel = modelComponent.getModel();

        var attachments = AttachmentsHelper.parseSkin(playerSkin, null, playerModel.getGradientId());

        String texturePath = model.getTexture();
        if(model.getModel().equals("Characters/MermaidPlayer.blockymodel") || model.getModel().equals("Characters/MermaidBigFinPlayer.blockymodel")){
            texturePath = "Characters/PlayerTextures/" + mermaid.getTailColor() + ".png";
        }

        return new Model(
                model.getModelAssetId() + "_Skinned",
                playerModel.getScale(),
                playerModel.getRandomAttachmentIds(),
                attachments,
                playerModel.getBoundingBox(),
                model.getModel(),
                texturePath,//"Characters/PlayerTextures/" + mermaid.getTailColor() + ".png",//model.getTexture(),
                playerModel.getGradientSet(),
                playerModel.getGradientId(),
                model.getEyeHeight(),
                model.getCrouchOffset(),
                model.getAnimationSetMap(),
                playerModel.getCamera(),
                model.getLight(),
                model.getParticles(),
                model.getTrails(),
                model.getPhysicsValues(),
                playerModel.getDetailBoxes(),
                playerModel.getPhobia(),
                playerModel.getPhobiaModelAssetId()
        );
    }

    // Applies given skin on given player reference
    public static void applySkin(Model model, PlayerSkin skin, Ref<EntityStore> ref, CommandBuffer commandBuffer, Player player, MermaidComponent mermaid) {
        var skinnedModel = applySkin(model, skin, mermaid);

        commandBuffer.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(skinnedModel));
    }

    public static void applySkin(Model model, PlayerSkin skin, Ref<EntityStore> ref, MermaidComponent mermaid) {
        var skinnedModel = applySkin(model, skin, mermaid);

        var store = ref.getStore();
        store.replaceComponent(ref, ModelComponent.getComponentType(), new ModelComponent(skinnedModel));
    }
}