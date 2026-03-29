package plugin.siren.Utils.Cosmetics;

import java.util.TreeMap;

public class MermaidCosmeticSkin {
    public enum TextureColor{
        ORANGE(0),
        PINK(1),
        ROSE(2),
        PURPLE(3),
        AQUA(4),
        LIME(5),
        BLUE(6),
        CYAN(7);

        private final int value;
        private TextureColor(int value){
            this.value = value;
        }

        public static TextureColor get(int value){
            TextureColor textureColor = null;
            for(TextureColor color : TextureColor.values()){
                if(color.value == value){
                    textureColor = color;
                }
            }

            return textureColor;
        }

        public int getValue() {
            return this.value;
        }
    }

    private static TreeMap<MermaidCosmetic, TreeMap<TextureColor, String>> textureMap;

    public static void registerCosmeticSkins(){
        textureMap = new TreeMap<>();

        // Long_Back_Fins
        TreeMap<TextureColor, String> longBackFinsMap = new TreeMap<>();
        longBackFinsMap.put(TextureColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Orange_Texture.png");
        longBackFinsMap.put(TextureColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Pink_Texture.png");
        longBackFinsMap.put(TextureColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Rose_Texture.png");
        longBackFinsMap.put(TextureColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Purple_Texture.png");
        longBackFinsMap.put(TextureColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Aqua_Texture.png");
        longBackFinsMap.put(TextureColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Lime_Texture.png");
        longBackFinsMap.put(TextureColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Blue_Texture.png");
        longBackFinsMap.put(TextureColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Cyan_Texture.png");
        textureMap.put(MermaidCosmetic.Long_Back_Fins,longBackFinsMap);

        // Top_Side_Fins
        TreeMap<TextureColor, String> topSideFinsMap = new TreeMap<>();
        topSideFinsMap.put(TextureColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pelvic_Fins/TopPelvicFins/Mermaids_TopPelvicFins_Orange_Texture.png");
        topSideFinsMap.put(TextureColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Pelvic_Fins/TopPelvicFins/Mermaids_TopPelvicFins_Pink_Texture.png");
        topSideFinsMap.put(TextureColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pelvic_Fins/TopPelvicFins/Mermaids_TopPelvicFins_Rose_Texture.png");
        topSideFinsMap.put(TextureColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pelvic_Fins/TopPelvicFins/Mermaids_TopPelvicFins_Purple_Texture.png");
        topSideFinsMap.put(TextureColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Pelvic_Fins/TopPelvicFins/Mermaids_TopPelvicFins_Aqua_Texture.png");
        topSideFinsMap.put(TextureColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Pelvic_Fins/TopPelvicFins/Mermaids_TopPelvicFins_Lime_Texture.png");
        topSideFinsMap.put(TextureColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pelvic_Fins/TopPelvicFins/Mermaids_TopPelvicFins_Blue_Texture.png");
        topSideFinsMap.put(TextureColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Pelvic_Fins/TopPelvicFins/Mermaids_TopPelvicFins_Cyan_Texture.png");
        textureMap.put(MermaidCosmetic.Top_Side_Fins,topSideFinsMap);

        // Ear_Fins
        TreeMap<TextureColor, String> earFinsMap = new TreeMap<>();
        earFinsMap.put(TextureColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Orange.png");
        earFinsMap.put(TextureColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Orange.png");
        earFinsMap.put(TextureColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Orange.png");
        earFinsMap.put(TextureColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Orange.png");
        earFinsMap.put(TextureColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Orange.png");
        earFinsMap.put(TextureColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Orange.png");
        earFinsMap.put(TextureColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Orange.png");
        earFinsMap.put(TextureColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Orange.png");
        textureMap.put(MermaidCosmetic.Ear_Fins,earFinsMap);
    }

    public static String getTexture(int cosmeticValue, TextureColor textureColor){
        MermaidCosmetic mermaidCosmetic = MermaidCosmetic.get(cosmeticValue);

        String texture = mermaidCosmetic.getDefaultTexture();

        TreeMap<TextureColor, String> colorTreeMap = textureMap.get(mermaidCosmetic);
        if(colorTreeMap != null && !colorTreeMap.isEmpty()) {
            String color = colorTreeMap.get(textureColor);
            if(color != null){
                texture = color;
            }
        }

        return texture;
    }
}
