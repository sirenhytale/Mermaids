package plugin.siren.Utils.Models;

import java.util.ArrayList;
import java.util.List;

public enum MermaidModel  {
    Mermaid(0, "Mermaids_Mermaid", MermaidModelType.MERMAID),
    Ocean_Fluke(1, "Mermaids_Mermaid_Ocean_Fluke", MermaidModelType.OCEAN_FLUKE);

    private final int value;
    private final String model;
    private final List<MermaidColor> colorList;
    private final List<String> textureColors;
    private final MermaidModelType type;
    private MermaidModel(int value, String model, MermaidModelType type){
        this.value = value;
        this.model = model;
        this.type = type;

        if(value == 0){
            List<MermaidColor> colors = new ArrayList<>();
            colors.add(MermaidColor.ORANGE);
            colors.add(MermaidColor.PINK);
            colors.add(MermaidColor.ROSE);
            colors.add(MermaidColor.PURPLE);
            colors.add(MermaidColor.AQUA);
            colors.add(MermaidColor.LIME);
            colors.add(MermaidColor.BLUE);
            colors.add(MermaidColor.CYAN);
            this.colorList = colors;

            List<String> textures = new ArrayList<>();
            textures.add("Mermaids_Mermaid_Orange_Texture");
            textures.add("Mermaids_Mermaid_Pink_Texture");
            textures.add("Mermaids_Mermaid_Rose_Texture");
            textures.add("Mermaids_Mermaid_Purple_Texture");
            textures.add("Mermaids_Mermaid_Aqua_Texture");
            textures.add("Mermaids_Mermaid_Lime_Texture");
            textures.add("Mermaids_Mermaid_Blue_Texture");
            textures.add("Mermaids_Mermaid_Cyan_Texture");
            this.textureColors = textures;
        }else if(value == 1){
            List<MermaidColor> colors = new ArrayList<>();

            colors.add(MermaidColor.BLACK);
            colors.add(MermaidColor.GRAY);

            this.colorList = colors;

            List<String> textures = new ArrayList<>();
            textures.add("Mermaids_Mermaid_Orca_Texture");
            textures.add("Mermaids_Mermaid_Dolphin_Texture");
            this.textureColors = textures;
        }else{
            this.colorList = new ArrayList<>();
            this.textureColors = new ArrayList<>();
        }
    }

    public static MermaidModel get(int value){
        MermaidModel mermaidModel = null;
        for(MermaidModel models : MermaidModel.values()){
            if(models.value == value){
                mermaidModel = models;
            }
        }

        return mermaidModel;
    }

    public static MermaidModel get(String model){
        MermaidModel mermaidModel = null;
        for(MermaidModel models : MermaidModel.values()){
            if(models.getModel().equals(model)){
                mermaidModel = models;
            }
        }

        return mermaidModel;
    }

    public int getValue(){
        return this.value;
    }

    public String getModel(){
        return this.model;
    }

    public List<MermaidColor> getColorList(){
        return this.colorList;
    }

    public List<String> getTextureList(){
        return this.textureColors;
    }

    public MermaidModelType getType(){
        return type;
    }
}
