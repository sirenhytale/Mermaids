package plugin.siren.Contributions.starman.modelutils;

import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAttachment;
import com.hypixel.hytale.server.core.cosmetics.CosmeticRegistry;
import com.hypixel.hytale.server.core.cosmetics.CosmeticType;
import com.hypixel.hytale.server.core.cosmetics.CosmeticsModule;
import com.hypixel.hytale.server.core.cosmetics.PlayerSkinPart;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/*
 *
 * Author: StarMan
 * Version: 1.0-pre
 * Date: 1/15/2026
 * Link: https://github.com/SyperAI/hytale-model-utils
 *
 * Modified: Siren
 * Date: 2026/03/19
 *
 */

public class AttachmentsHelper {
    static CosmeticRegistry reg = CosmeticsModule.get().getRegistry();

    public static void addAttachment(ArrayList<ModelAttachment> attachments, PlayerSkinPart part, @Nullable String gradientId, @Nullable PlayerSkinPart.Variant variant) {
        if(variant != null) {
            String model = variant.getModel();
            String greyscaleTexture = variant.getGreyscaleTexture();
            /*if(model.equalsIgnoreCase("Characters/Player.blockymodel")){
                if(greyscaleTexture.equalsIgnoreCase("Characters/Player_Textures/Player_Greyscale.png")){
                    greyscaleTexture = "Characters/SirensMermaid/MermaidTextures//MermaidPlayer_Greyscale.png";
                }else if(greyscaleTexture.equalsIgnoreCase("Characters/Player_Textures/Player_Muscular_Greyscale.png")){
                    greyscaleTexture = "Characters/SirensMermaid/MermaidTextures/MermaidPlayer_Muscular_Greyscale.png";
                }
            }*/

            attachments.add(
                    new ModelAttachment(
                            model,
                            greyscaleTexture,
                            part.getGradientSet(),
                            gradientId,
                            1.0
                    )
            );
        }else{
            if(part.getGreyscaleTexture() == null) {
                String partToString = part.getTextures().toString();
                if(partToString != null && !partToString.isEmpty()) {
                    String partWithoutBrace = partToString.substring(1);
                    if(partWithoutBrace != null) {
                        String[] partToStringParts = partWithoutBrace.split("=>");
                        if(partToStringParts.length >= 1) {
                            String key = partToStringParts[0];
                            if(key != null) {
                                String attTexture = part.getTextures().get(key).getTexture();
                                String attGradientSet = part.getTextures().get(key).getBaseColor()[0];
                                if(attTexture != null && attGradientSet != null) {
                                    String model = part.getModel();
                                    String greyscaleTexture = attTexture;
                                    /*if(model.equalsIgnoreCase("Characters/Player.blockymodel")){
                                        if(greyscaleTexture.equalsIgnoreCase("Characters/Player_Textures/Player_Greyscale.png")){
                                            greyscaleTexture = "Characters/SirensMermaid/MermaidTextures/MermaidPlayer_Greyscale.png";
                                        }else if(greyscaleTexture.equalsIgnoreCase("Characters/Player_Textures/Player_Muscular_Greyscale.png")){
                                            greyscaleTexture = "Characters/SirensMermaid/MermaidTextures/MermaidPlayer_Muscular_Greyscale.png";
                                        }
                                    }*/
                                    attachments.add(
                                            new ModelAttachment(
                                                    model,
                                                    greyscaleTexture,
                                                    attGradientSet,
                                                    gradientId,
                                                    1.0
                                            )
                                    );
                                }
                            }
                        }
                    }
                }
            }else{
                String model = part.getModel();
                String greyscaleTexture = part.getGreyscaleTexture();
                /*if(model.equalsIgnoreCase("Characters/Player.blockymodel")){
                    if(greyscaleTexture.equalsIgnoreCase("Characters/Player_Textures/Player_Greyscale.png")){
                        greyscaleTexture = "Characters/SirensMermaid/MermaidTextures/MermaidPlayer_Greyscale.png";
                    }else if(greyscaleTexture.equalsIgnoreCase("Characters/Player_Textures/Player_Muscular_Greyscale.png")){
                        greyscaleTexture = "Characters/SirensMermaid/MermaidTextures/MermaidPlayer_Muscular_Greyscale.png";
                    }
                }*/
                attachments.add(
                        new ModelAttachment(
                                model,
                                greyscaleTexture,
                                part.getGradientSet(),
                                gradientId,
                        1.0
                    )
                );
            }
        }
    }

    public static ModelAttachment[] parseSkin(PlayerSkin skin, @Nullable ArrayList<String> ignore, @Nullable String defaultGradientId, @Nullable MermaidComponent mermaid) {
        ArrayList<ModelAttachment> attachments = new ArrayList<>();

        // We go through all the fields of the class PlayerSkin
        for (Field skinField : skin.getClass().getDeclaredFields()) {
            // We only need string fields so skipping all other
            if (skinField.getType() != String.class) continue;

            // Skip ignored fields
            if (ignore != null && ignore.contains(skinField.getName())) continue;

            // Collecting skin part data
            String skinPartValue;
            try {
                skinPartValue = (String) skinField.get(skin);
            } catch (IllegalAccessException ignored) {
                continue;
            }
            if (skinPartValue == null) continue;

            // Converting field name to enum
            String upperSnakeName = skinField.getName().replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
            String checkCosmeticType = "";
            CosmeticType cosmeticType;
            try {
                cosmeticType = CosmeticType.valueOf(upperSnakeName);
                checkCosmeticType = upperSnakeName;
            } catch (IllegalArgumentException e) {
                String enumName = upperSnakeName + "S";
                cosmeticType = CosmeticType.valueOf(enumName);
                checkCosmeticType = enumName;
            }

            if(checkCosmeticType.equalsIgnoreCase("BODY_CHARACTERISTIC") || checkCosmeticType.equalsIgnoreCase("BODY_CHARACTERISTICS")){
                continue;
            }

            List<Integer> cosmeticsToHide = mermaid.getCosmeticsToHide();
            boolean hideAttachment = false;
            if(cosmeticsToHide != null && !cosmeticsToHide.isEmpty()){
                for(int cosmetic : cosmeticsToHide){
                    if(checkCosmeticType.equalsIgnoreCase("HAIRCUTS") && cosmetic == 0){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding HAIRCUT");
                        }
                        hideAttachment = true;
                    }else if(checkCosmeticType.equalsIgnoreCase("FACIAL_HAIR") && cosmetic == 1){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding FACIAL_HAIR");
                        }
                        hideAttachment = true;
                    }else if(checkCosmeticType.equalsIgnoreCase("UNDERTOPS") && cosmetic == 2){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding UNDERTOPS");
                        }
                        hideAttachment = true;
                    }else if(checkCosmeticType.equalsIgnoreCase("OVERTOPS") && cosmetic == 3){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding OVERTOPS");
                        }
                        hideAttachment = true;
                    }else if(checkCosmeticType.equalsIgnoreCase("PANTS") && cosmetic == 4){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding PANTS");
                        }
                        hideAttachment = true;
                    }else if(checkCosmeticType.equalsIgnoreCase("OVERPANTS") && cosmetic == 5){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding OVERPANTS");
                        }
                        hideAttachment = true;
                    }else if(checkCosmeticType.equalsIgnoreCase("SHOES") && cosmetic == 6){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding SHOES");
                        }
                        hideAttachment = true;
                    }else if(checkCosmeticType.equalsIgnoreCase("GLOVES") && cosmetic == 7){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding GLOVES");
                        }
                        hideAttachment = true;
                    }else if(checkCosmeticType.equalsIgnoreCase("CAPES") && cosmetic == 8){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding CAPES");
                        }
                        hideAttachment = true;
                    }else if(checkCosmeticType.equalsIgnoreCase("HEAD_ACCESSORY") && cosmetic == 9){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding HEAD_ACCESSORY");
                        }
                        hideAttachment = true;
                    }else if(checkCosmeticType.equalsIgnoreCase("FACE_ACCESSORY") && cosmetic == 10){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding FACE_ACCESSORY");
                        }
                        hideAttachment = true;
                    }else if(checkCosmeticType.equalsIgnoreCase("EAR_ACCESSORY") && cosmetic == 11){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding EAR_ACCESSORY");
                        }
                        hideAttachment = true;
                    }else if(checkCosmeticType.equalsIgnoreCase("EARS") && cosmetic == 12){
                        if(Mermaids.ifDebug()){
                            Mermaids.LOGGER.atInfo().log("Hiding EARS");
                        }
                        hideAttachment = true;
                    }
                }
            }

            // 0 - part id; 1 - gradient id; 2 - variant
            String[] cosmeticParts = skinPartValue.split("\\.");
            PlayerSkinPart skinPart = (PlayerSkinPart) reg.getByType(cosmeticType).get(cosmeticParts[0]);

            // Collecting gradient id from parsed cosmetic data
            String gradientId;
            if (cosmeticParts.length > 1) {
                gradientId = cosmeticParts[1];
            } else {
                gradientId = defaultGradientId;
            }

            PlayerSkinPart.Variant skinPartVariant = null;
            if (cosmeticParts.length > 2) {
                skinPartVariant = skinPart.getVariants().get(cosmeticParts[2]);
            }

            if(!hideAttachment) {
                addAttachment(attachments, skinPart, gradientId, skinPartVariant);
            }
        }

        if(Mermaids.ifDebug()){
            Mermaids.LOGGER.atInfo().log(attachments.toString());
        }

        return attachments.toArray(new ModelAttachment[0]);
    }
}