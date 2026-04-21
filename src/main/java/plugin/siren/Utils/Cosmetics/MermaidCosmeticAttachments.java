package plugin.siren.Utils.Cosmetics;

import com.hypixel.hytale.server.core.asset.type.model.config.ModelAttachment;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidSettingsComponent;
import plugin.siren.Utils.Models.MermaidModel;

import java.util.ArrayList;
import java.util.Arrays;

public class MermaidCosmeticAttachments {
    public static ModelAttachment[] addAttachment(ModelAttachment[] baseAttachments, MermaidSettingsComponent mermaidSettings){
        ArrayList<ModelAttachment> attachments = new ArrayList<>(Arrays.asList(baseAttachments));

        if(Mermaids.ifDebug()){
            Mermaids.LOGGER.atInfo().log("Adding MermaidCosmetics");
        }

        MermaidModel mermaidModel = mermaidSettings.getMermaidTailId();
        if(mermaidModel.getValue() == MermaidModel.Mermaid.getValue()){
            MermaidCosmetic mermaidTail = MermaidCosmetic.Mermaid_Tail;
            attachments.add(mermaidTail.getAsModelAttachment(mermaidSettings));

            MermaidCosmetic mermaidFluke = MermaidCosmetic.Mermaid_Fluke;
            attachments.add(mermaidFluke.getAsModelAttachment(mermaidSettings));
        }else{
            MermaidCosmetic mammalTail = MermaidCosmetic.Mammal_Tail;
            attachments.add(mammalTail.getAsModelAttachment(mermaidSettings));

            MermaidCosmetic mammalFluke = MermaidCosmetic.Mammal_Fluke;
            attachments.add(mammalFluke.getAsModelAttachment(mermaidSettings));
        }

        if(mermaidSettings.hasMermaidCosmetic(MermaidCosmeticType.DORSAL_FIN)){
            MermaidCosmetic dorsalCosmetic = mermaidSettings.getMermaidCosmetic(MermaidCosmeticType.DORSAL_FIN);

            attachments.add(dorsalCosmetic.getAsModelAttachment(mermaidSettings));
        }

        if(mermaidSettings.hasMermaidCosmetic(MermaidCosmeticType.PECTORAL_FIN)){
            MermaidCosmetic pelvicCosmetic = mermaidSettings.getMermaidCosmetic(MermaidCosmeticType.PECTORAL_FIN);

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
