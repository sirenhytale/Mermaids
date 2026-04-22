package plugin.siren.Utils.Cosmetics;

import com.hypixel.hytale.server.core.asset.type.model.config.ModelAttachment;
import plugin.siren.Systems.MermaidSettingsComponent;
import plugin.siren.Utils.Models.MermaidColor;

public enum MermaidCosmetic {
    Long_Back_Fins(0, "Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/Mermaids_LongDorsalFins.blockymodel", "Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Orange_Texture.png", MermaidCosmeticType.DORSAL_FIN),
    Top_Side_Fins(1, "Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Mermaids_TopPectoralFins.blockymodel", "Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Orange_Texture.png", MermaidCosmeticType.PECTORAL_FIN),
    Ear_Fins(2, "Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/Mermaids_EarFins.blockymodel", "Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Orange.png", MermaidCosmeticType.AURICLE_FIN),
    Spiked_Ear_Fins(3, "Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/Mermaids_SpikedEarFins.blockymodel", "Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Orange.png", MermaidCosmeticType.AURICLE_FIN),
    Flippers(4, "Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Mermaids_Flippers.blockymodel", "Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Orange_Texture.png", MermaidCosmeticType.PECTORAL_FIN),
    Flippers_Large(5, "Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Mermaids_Flippers_Large.blockymodel", "Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Orange_Texture.png", MermaidCosmeticType.PECTORAL_FIN),
    Dorsal_Fin(6, "Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/Mermaids_Dorsal_Fin.blockymodel", "Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Orange_Texture.png", MermaidCosmeticType.DORSAL_FIN),
    Dorsal_Fin_Large(7, "Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/Mermaids_Dorsal_Fin_Large.blockymodel", "Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Orange_Texture.png", MermaidCosmeticType.DORSAL_FIN),
    Mermaid_Tail(8, "Characters/SirensMermaid/Mermaids_Mermaid_Tail.blockymodel", "Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Orange_Texture.png", MermaidCosmeticType.TAIL),
    Mammal_Tail(9, "Characters/SirensMermaid/Mermaids_Mammal_Tail.blockymodel", "Characters/SirensMermaid/MermaidMammalTextures/Mermaids_Mermaid_Orca_Texture.png", MermaidCosmeticType.TAIL);
    /*Mermaid_Fluke(10, "Characters/SirensMermaid/Mermaids_Mermaid_Fluke.blockymodel", "Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Orange_Texture.png", MermaidCosmeticType.FLUKE),
    Mammal_Fluke(11, "Characters/SirensMermaid/Mermaids_Mammal_Fluke.blockymodel", "Characters/SirensMermaid/MermaidMammalTextures/Mermaids_Mermaid_Orca_Texture.png", MermaidCosmeticType.FLUKE);*/

    private final int value;
    private final String model;
    private final String deafultTexture;
    private final MermaidCosmeticType type;
    private MermaidCosmetic(int value, String model, String deafultTexture, MermaidCosmeticType type){
        this.value = value;
        this.model = model;
        this.deafultTexture = deafultTexture;
        this.type = type;
    }

    public static MermaidCosmetic get(int value){
        MermaidCosmetic mermaidCosmetic = null;
        for(MermaidCosmetic cosmetics : MermaidCosmetic.values()){
            if(cosmetics.value == value){
                mermaidCosmetic = cosmetics;
            }
        }

        return mermaidCosmetic;
    }

    public int getValue(){
        return this.value;
    }

    public String getModel(){
        return this.model;
    }

    public String getDefaultTexture(){
        return this.deafultTexture;
    }

    public String getTexture(MermaidSettingsComponent mermaidSettings){
        MermaidColor textureColor = mermaidSettings.getCosmeticColor();
        String textureString = MermaidCosmeticSkin.getTexture(value, textureColor);

        return textureString;
    }

    public MermaidCosmeticType getType(){
        return type;
    }

    public ModelAttachment getAsModelAttachment(MermaidSettingsComponent mermaidSettings){
        String textureString = getTexture(mermaidSettings);

        return new ModelAttachment(model, textureString, null, null, 1.0);
    }
}
