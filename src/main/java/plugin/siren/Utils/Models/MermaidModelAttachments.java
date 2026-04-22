package plugin.siren.Utils.Models;

import com.hypixel.hytale.server.core.asset.type.model.config.ModelAttachment;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidSettingsComponent;
import plugin.siren.Utils.Cosmetics.MermaidCosmetic;
import plugin.siren.Utils.Cosmetics.MermaidCosmeticType;

import java.util.ArrayList;
import java.util.Arrays;

public class MermaidModelAttachments {
    public static ModelAttachment[] addAttachments(MermaidSettingsComponent mermaidSettings){
        ArrayList<ModelAttachment> attachments = new ArrayList<>();

        if(Mermaids.ifDebug()){
            Mermaids.LOGGER.atInfo().log("Adding Mermaid Tail Model attachment");
        }

        MermaidModel mermaidModel = mermaidSettings.getMermaidTailId();
        if(mermaidModel.getValue() == MermaidModel.Mermaid.getValue()){
            MermaidCosmetic mermaidTail = MermaidCosmetic.Mermaid_Tail;
            attachments.add(mermaidTail.getAsModelAttachment(mermaidSettings));

            /*MermaidCosmetic mermaidFluke = MermaidCosmetic.Mermaid_Fluke;
            attachments.add(mermaidFluke.getAsModelAttachment(mermaidSettings));*/
        }else{
            MermaidCosmetic mammalTail = MermaidCosmetic.Mammal_Tail;
            attachments.add(mammalTail.getAsModelAttachment(mermaidSettings));

            /*MermaidCosmetic mammalFluke = MermaidCosmetic.Mammal_Fluke;
            attachments.add(mammalFluke.getAsModelAttachment(mermaidSettings));*/
        }

        if(Mermaids.ifDebug()){
            Mermaids.LOGGER.atInfo().log(attachments.toString());
        }

        return attachments.toArray(new ModelAttachment[0]);
    }
}
