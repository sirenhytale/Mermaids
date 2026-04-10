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
        topSideFinsMap.put(TextureColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Orange_Texture.png");
        topSideFinsMap.put(TextureColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Pink_Texture.png");
        topSideFinsMap.put(TextureColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Rose_Texture.png");
        topSideFinsMap.put(TextureColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Purple_Texture.png");
        topSideFinsMap.put(TextureColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Aqua_Texture.png");
        topSideFinsMap.put(TextureColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Lime_Texture.png");
        topSideFinsMap.put(TextureColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Blue_Texture.png");
        topSideFinsMap.put(TextureColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Cyan_Texture.png");
        textureMap.put(MermaidCosmetic.Top_Side_Fins,topSideFinsMap);

        // Ear_Fins
        TreeMap<TextureColor, String> earFinsMap = new TreeMap<>();
        earFinsMap.put(TextureColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Orange.png");
        earFinsMap.put(TextureColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Pink.png");
        earFinsMap.put(TextureColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Rose.png");
        earFinsMap.put(TextureColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Purple.png");
        earFinsMap.put(TextureColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Aqua.png");
        earFinsMap.put(TextureColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Lime.png");
        earFinsMap.put(TextureColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Blue.png");
        earFinsMap.put(TextureColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Cyan.png");
        textureMap.put(MermaidCosmetic.Ear_Fins,earFinsMap);

        // Spiked_Ear_Fins
        TreeMap<TextureColor, String> spikedEarFinsMap = new TreeMap<>();
        spikedEarFinsMap.put(TextureColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Orange.png");
        spikedEarFinsMap.put(TextureColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Pink.png");
        spikedEarFinsMap.put(TextureColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Rose.png");
        spikedEarFinsMap.put(TextureColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Purple.png");
        spikedEarFinsMap.put(TextureColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Aqua.png");
        spikedEarFinsMap.put(TextureColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Lime.png");
        spikedEarFinsMap.put(TextureColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Blue.png");
        spikedEarFinsMap.put(TextureColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Cyan.png");
        textureMap.put(MermaidCosmetic.Spiked_Ear_Fins,spikedEarFinsMap);

        // Flippers
        TreeMap<TextureColor, String> flippersMap = new TreeMap<>();
        flippersMap.put(TextureColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Orange_Texture.png");
        flippersMap.put(TextureColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Pink_Texture.png");
        flippersMap.put(TextureColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Rose_Texture.png");
        flippersMap.put(TextureColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Purple_Texture.png");
        flippersMap.put(TextureColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Aqua_Texture.png");
        flippersMap.put(TextureColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Lime_Texture.png");
        flippersMap.put(TextureColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Blue_Texture.png");
        flippersMap.put(TextureColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Cyan_Texture.png");
        textureMap.put(MermaidCosmetic.Flippers,flippersMap);

        // Flippers_Large
        TreeMap<TextureColor, String> flippersLargeMap = new TreeMap<>();
        flippersLargeMap.put(TextureColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Orange_Texture.png");
        flippersLargeMap.put(TextureColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Pink_Texture.png");
        flippersLargeMap.put(TextureColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Rose_Texture.png");
        flippersLargeMap.put(TextureColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Purple_Texture.png");
        flippersLargeMap.put(TextureColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Aqua_Texture.png");
        flippersLargeMap.put(TextureColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Lime_Texture.png");
        flippersLargeMap.put(TextureColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Blue_Texture.png");
        flippersLargeMap.put(TextureColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Cyan_Texture.png");
        textureMap.put(MermaidCosmetic.Flippers_Large,flippersLargeMap);

        // Dorsal_Fin
        TreeMap<TextureColor, String> dorsalFinMap = new TreeMap<>();
        dorsalFinMap.put(TextureColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Orange_Texture.png");
        dorsalFinMap.put(TextureColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Pink_Texture.png");
        dorsalFinMap.put(TextureColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Rose_Texture.png");
        dorsalFinMap.put(TextureColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Purple_Texture.png");
        dorsalFinMap.put(TextureColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Aqua_Texture.png");
        dorsalFinMap.put(TextureColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Lime_Texture.png");
        dorsalFinMap.put(TextureColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Blue_Texture.png");
        dorsalFinMap.put(TextureColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Cyan_Texture.png");
        textureMap.put(MermaidCosmetic.Dorsal_Fin,dorsalFinMap);

        // Dorsal_Fin_Large
        textureMap.put(MermaidCosmetic.Dorsal_Fin_Large,dorsalFinMap);
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
