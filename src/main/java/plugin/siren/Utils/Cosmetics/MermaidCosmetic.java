package plugin.siren.Utils.Cosmetics;

import com.hypixel.hytale.server.core.asset.type.model.config.ModelAttachment;

public enum MermaidCosmetic {
    Long_Side_Fins(0, "Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins.blockymodel", "Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFinTexture.png", MermaidCosmeticType.DORSAL_FIN);

    private final int value;
    private final String model;
    private final String texture;
    private final MermaidCosmeticType type;
    private MermaidCosmetic(int value, String model, String texture, MermaidCosmeticType type){
        this.value = value;
        this.model = model;
        this.texture = texture;
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

    public String getTexture(){
        return this.texture;
    }

    public MermaidCosmeticType getType(){
        return type;
    }

    public ModelAttachment getAsModelAttachment(String mermaidModelTexture){
        if(mermaidModelTexture.equalsIgnoreCase("Mermaids_Mermaid_Orange_Texture")){
            return new ModelAttachment(model, texture, null, null, 1.0);
        }else{
            return new ModelAttachment(model, "Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFinPinkTexture.png", null, null, 1.0);
        }
        //return new ModelAttachment(model, texture, null, null, 1.0);
    }
}
