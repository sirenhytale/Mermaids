package plugin.siren.Utils.Cosmetics;

import com.hypixel.hytale.server.core.asset.type.model.config.ModelAttachment;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidSettingsComponent;

import java.util.ArrayList;

public class MermaidCosmeticAttachments {
    public static ModelAttachment[] addAttachment(ModelAttachment[] baseAttachments, MermaidSettingsComponent mermaidSettings){
        ArrayList<ModelAttachment> attachments = new ArrayList<>();

        for(int i = 0; i < baseAttachments.length; i++){
            attachments.add(baseAttachments[i]);
        }

        if(Mermaids.ifDebug()){
            Mermaids.LOGGER.atInfo().log("Adding MermaidCosmetics");
        }

        if(mermaidSettings.hasMermaidCosmetic(MermaidCosmeticType.DORSAL_FIN)){
            MermaidCosmetic dorsalCosmetic = mermaidSettings.getMermaidCosmetic(MermaidCosmeticType.DORSAL_FIN);

            attachments.add(dorsalCosmetic.getAsModelAttachment(mermaidSettings));
        }

        if(mermaidSettings.hasMermaidCosmetic(MermaidCosmeticType.PELVIC_FIN)){
            MermaidCosmetic pelvicCosmetic = mermaidSettings.getMermaidCosmetic(MermaidCosmeticType.PELVIC_FIN);

            attachments.add(pelvicCosmetic.getAsModelAttachment(mermaidSettings));
        }

        if(mermaidSettings.hasMermaidCosmetic(MermaidCosmeticType.AURICLE_FIN)){
            MermaidCosmetic pelvicCosmetic = mermaidSettings.getMermaidCosmetic(MermaidCosmeticType.AURICLE_FIN);

            attachments.add(pelvicCosmetic.getAsModelAttachment(mermaidSettings));
        }

        if(Mermaids.ifDebug()){
            Mermaids.LOGGER.atInfo().log(attachments.toString());
        }

        return attachments.toArray(new ModelAttachment[0]);
    }
}
