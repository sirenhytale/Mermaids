package plugin.siren.Contributions.starman.modelutils;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.ColorLight;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAttachment;
import com.hypixel.hytale.server.core.cosmetics.CosmeticType;
import com.hypixel.hytale.server.core.cosmetics.PlayerSkinPart;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import io.sentry.Attachment;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.MermaidSettings;

import java.lang.reflect.Field;
import java.util.List;

import static plugin.siren.Contributions.starman.modelutils.AttachmentsHelper.reg;

/*
 *
 * Author: StarMan
 * Version: 1.0-pre
 * Date: 2026/01/15
 * Link: https://github.com/SyperAI/hytale-model-utils
 *
 * Modified: Siren
 * Date: 2026/03/14
 *
 */

public class ModelHelper {

    // Applies given skin on given model
    public static Model applySkin(Model model, PlayerSkin playerSkin, MermaidComponent mermaid, MermaidSettings mermaidSettings) {
        ModelComponent modelComponent = (ModelComponent) mermaid.getModelComponent().clone();
        Model playerModel = modelComponent.getModel();

        String gradientId = playerModel.getGradientId();
        for (Field skinField : playerSkin.getClass().getDeclaredFields()) {
            // We only need string fields so skipping all other
            if (skinField.getType() != String.class) continue;

            // Collecting skin part data
            String skinPartValue;
            try {
                skinPartValue = (String) skinField.get(playerSkin);
            } catch (IllegalAccessException ignored) {
                continue;
            }
            if (skinPartValue == null) continue;

            // Converting field name to enum
            String upperSnakeName = skinField.getName().replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
            String checkCosmeticType = "";
            try {
                checkCosmeticType = upperSnakeName;
            } catch (IllegalArgumentException e) {
                String enumName = upperSnakeName + "S";
                checkCosmeticType = enumName;
            }
            Mermaids.LOGGER.atInfo().log(checkCosmeticType);

            // 0 - part id; 1 - gradient id; 2 - variant
            String[] cosmeticParts = skinPartValue.split("\\.");

            if(checkCosmeticType.equalsIgnoreCase("BODY_CHARACTERISTIC") || checkCosmeticType.equalsIgnoreCase("BODY_CHARACTERISTICS")){
                if (cosmeticParts.length > 1) {
                    gradientId = cosmeticParts[1];
                    Mermaids.LOGGER.atInfo().log("update default to " + gradientId);
                } else {
                    Mermaids.LOGGER.atInfo().log("failed to update" + gradientId);
                }
            }
        }

        var attachments = AttachmentsHelper.parseSkin(playerSkin, null, gradientId, mermaid);

        String texturePath = model.getTexture();
        if(model.getModel().equals("Characters/MermaidPlayer.blockymodel") || model.getModel().equals("Characters/MermaidBigFinPlayer.blockymodel")){
            texturePath = "Characters/PlayerTextures/" + mermaidSettings.getTailColor() + ".png";
        }
        if(model.getModel().equals("Characters/SirensMermaid/Mermaidv2.blockymodel")){
            texturePath = "Characters/SirensMermaid/MermaidTextures/" + mermaidSettings.getTailColorV2() + ".png";
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