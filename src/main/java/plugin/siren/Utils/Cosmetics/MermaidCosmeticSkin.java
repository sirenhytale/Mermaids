package plugin.siren.Utils.Cosmetics;

import plugin.siren.Utils.Models.MermaidColor;

import java.util.TreeMap;

public class MermaidCosmeticSkin {
    private static TreeMap<MermaidCosmetic, TreeMap<MermaidColor, String>> textureMap;

    public static void registerCosmeticSkins(){
        textureMap = new TreeMap<>();

        //Mermaid_Tail
        TreeMap<MermaidColor, String> mermaidTailMap = new TreeMap<>();
        mermaidTailMap.put(MermaidColor.ORANGE,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Orange_Texture.png");
        mermaidTailMap.put(MermaidColor.PINK,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Pink_Texture.png");
        mermaidTailMap.put(MermaidColor.ROSE,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Rose_Texture.png");
        mermaidTailMap.put(MermaidColor.PURPLE,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Purple_Texture.png");
        mermaidTailMap.put(MermaidColor.AQUA,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Aqua_Texture.png");
        mermaidTailMap.put(MermaidColor.LIME,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Lime_Texture.png");
        mermaidTailMap.put(MermaidColor.BLUE,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Blue_Texture.png");
        mermaidTailMap.put(MermaidColor.CYAN,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Cyan_Texture.png");
        textureMap.put(MermaidCosmetic.Mermaid_Tail,mermaidTailMap);

        /*//Mermaid_Fluke
        TreeMap<MermaidColor, String> mermaidFlukeMap = new TreeMap<>();
        mermaidFlukeMap.put(MermaidColor.ORANGE,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Orange_Texture.png");
        mermaidFlukeMap.put(MermaidColor.PINK,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Pink_Texture.png");
        mermaidFlukeMap.put(MermaidColor.ROSE,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Rose_Texture.png");
        mermaidFlukeMap.put(MermaidColor.PURPLE,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Purple_Texture.png");
        mermaidFlukeMap.put(MermaidColor.AQUA,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Aqua_Texture.png");
        mermaidFlukeMap.put(MermaidColor.LIME,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Lime_Texture.png");
        mermaidFlukeMap.put(MermaidColor.BLUE,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Blue_Texture.png");
        mermaidFlukeMap.put(MermaidColor.CYAN,"Characters/SirensMermaid/MermaidTextures/Mermaids_Mermaid_Cyan_Texture.png");
        textureMap.put(MermaidCosmetic.Mermaid_Fluke,mermaidFlukeMap);*/

        //Mammal_Tail
        TreeMap<MermaidColor, String> mammalTailMap = new TreeMap<>();
        mammalTailMap.put(MermaidColor.BLACK,"Characters/SirensMermaid/MermaidMammalTextures/Mermaids_Mermaid_Orca_Texture.png");
        mammalTailMap.put(MermaidColor.GRAY,"Characters/SirensMermaid/MermaidMammalTextures/Mermaids_Mermaid_Dolphin_Texture.png");
        textureMap.put(MermaidCosmetic.Mammal_Tail,mammalTailMap);

        /*//Mammal_Fluke
        TreeMap<MermaidColor, String> mammalFlukeMap = new TreeMap<>();
        mammalFlukeMap.put(MermaidColor.BLACK,"Characters/SirensMermaid/MermaidMammalTextures/Mermaids_Mermaid_Orca_Texture.png");
        mammalFlukeMap.put(MermaidColor.GRAY,"Characters/SirensMermaid/MermaidMammalTextures/Mermaids_Mermaid_Dolphin_Texture.png");
        textureMap.put(MermaidCosmetic.Mammal_Fluke,mammalFlukeMap);*/

        // Long_Back_Fins
        TreeMap<MermaidColor, String> longBackFinsMap = new TreeMap<>();
        longBackFinsMap.put(MermaidColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Orange_Texture.png");
        longBackFinsMap.put(MermaidColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Pink_Texture.png");
        longBackFinsMap.put(MermaidColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Rose_Texture.png");
        longBackFinsMap.put(MermaidColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Purple_Texture.png");
        longBackFinsMap.put(MermaidColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Aqua_Texture.png");
        longBackFinsMap.put(MermaidColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Lime_Texture.png");
        longBackFinsMap.put(MermaidColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Blue_Texture.png");
        longBackFinsMap.put(MermaidColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Cyan_Texture.png");
        longBackFinsMap.put(MermaidColor.BLACK,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Black_Texture.png");
        longBackFinsMap.put(MermaidColor.GRAY,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/LongDorsalFins/Mermaids_LongDorsalFins_Gray_Texture.png");
        textureMap.put(MermaidCosmetic.Long_Back_Fins,longBackFinsMap);

        // Top_Side_Fins
        TreeMap<MermaidColor, String> topSideFinsMap = new TreeMap<>();
        topSideFinsMap.put(MermaidColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Orange_Texture.png");
        topSideFinsMap.put(MermaidColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Pink_Texture.png");
        topSideFinsMap.put(MermaidColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Rose_Texture.png");
        topSideFinsMap.put(MermaidColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Purple_Texture.png");
        topSideFinsMap.put(MermaidColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Aqua_Texture.png");
        topSideFinsMap.put(MermaidColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Lime_Texture.png");
        topSideFinsMap.put(MermaidColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Blue_Texture.png");
        topSideFinsMap.put(MermaidColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Cyan_Texture.png");
        topSideFinsMap.put(MermaidColor.BLACK,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Black_Texture.png");
        topSideFinsMap.put(MermaidColor.GRAY,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/TopPectoralFins/Mermaids_TopPectoralFins_Gray_Texture.png");
        textureMap.put(MermaidCosmetic.Top_Side_Fins,topSideFinsMap);

        // Ear_Fins
        TreeMap<MermaidColor, String> earFinsMap = new TreeMap<>();
        earFinsMap.put(MermaidColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Orange.png");
        earFinsMap.put(MermaidColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Pink.png");
        earFinsMap.put(MermaidColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Rose.png");
        earFinsMap.put(MermaidColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Purple.png");
        earFinsMap.put(MermaidColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Aqua.png");
        earFinsMap.put(MermaidColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Lime.png");
        earFinsMap.put(MermaidColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Blue.png");
        earFinsMap.put(MermaidColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Cyan.png");
        earFinsMap.put(MermaidColor.BLACK,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Black.png");
        earFinsMap.put(MermaidColor.GRAY,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/EarFins/Mermaids_EarFins_Gray.png");
        textureMap.put(MermaidCosmetic.Ear_Fins,earFinsMap);

        // Spiked_Ear_Fins
        TreeMap<MermaidColor, String> spikedEarFinsMap = new TreeMap<>();
        spikedEarFinsMap.put(MermaidColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Orange.png");
        spikedEarFinsMap.put(MermaidColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Pink.png");
        spikedEarFinsMap.put(MermaidColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Rose.png");
        spikedEarFinsMap.put(MermaidColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Purple.png");
        spikedEarFinsMap.put(MermaidColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Aqua.png");
        spikedEarFinsMap.put(MermaidColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Lime.png");
        spikedEarFinsMap.put(MermaidColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Blue.png");
        spikedEarFinsMap.put(MermaidColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Cyan.png");
        spikedEarFinsMap.put(MermaidColor.BLACK,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Black.png");
        spikedEarFinsMap.put(MermaidColor.GRAY,"Characters/SirensMermaid/Cosmetics/Mermaid/Auricle_Fins/SpikedEarFins/Mermaids_SpikedEarFins_Gray.png");
        textureMap.put(MermaidCosmetic.Spiked_Ear_Fins,spikedEarFinsMap);

        // Flippers
        TreeMap<MermaidColor, String> flippersMap = new TreeMap<>();
        flippersMap.put(MermaidColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Orange_Texture.png");
        flippersMap.put(MermaidColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Pink_Texture.png");
        flippersMap.put(MermaidColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Rose_Texture.png");
        flippersMap.put(MermaidColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Purple_Texture.png");
        flippersMap.put(MermaidColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Aqua_Texture.png");
        flippersMap.put(MermaidColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Lime_Texture.png");
        flippersMap.put(MermaidColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Blue_Texture.png");
        flippersMap.put(MermaidColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Cyan_Texture.png");
        flippersMap.put(MermaidColor.BLACK,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Black_Texture.png");
        flippersMap.put(MermaidColor.GRAY,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/Flippers/Mermaids_Flippers_Gray_Texture.png");
        textureMap.put(MermaidCosmetic.Flippers,flippersMap);

        // Flippers_Large
        TreeMap<MermaidColor, String> flippersLargeMap = new TreeMap<>();
        flippersLargeMap.put(MermaidColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Orange_Texture.png");
        flippersLargeMap.put(MermaidColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Pink_Texture.png");
        flippersLargeMap.put(MermaidColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Rose_Texture.png");
        flippersLargeMap.put(MermaidColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Purple_Texture.png");
        flippersLargeMap.put(MermaidColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Aqua_Texture.png");
        flippersLargeMap.put(MermaidColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Lime_Texture.png");
        flippersLargeMap.put(MermaidColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Blue_Texture.png");
        flippersLargeMap.put(MermaidColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Cyan_Texture.png");
        flippersLargeMap.put(MermaidColor.BLACK,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Black_Texture.png");
        flippersLargeMap.put(MermaidColor.GRAY,"Characters/SirensMermaid/Cosmetics/Mermaid/Pectoral_Fins/FlippersLarge/Mermaids_Flippers_Gray_Texture.png");
        textureMap.put(MermaidCosmetic.Flippers_Large,flippersLargeMap);

        // Dorsal_Fin
        TreeMap<MermaidColor, String> dorsalFinMap = new TreeMap<>();
        dorsalFinMap.put(MermaidColor.ORANGE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Orange_Texture.png");
        dorsalFinMap.put(MermaidColor.PINK,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Pink_Texture.png");
        dorsalFinMap.put(MermaidColor.ROSE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Rose_Texture.png");
        dorsalFinMap.put(MermaidColor.PURPLE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Purple_Texture.png");
        dorsalFinMap.put(MermaidColor.AQUA,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Aqua_Texture.png");
        dorsalFinMap.put(MermaidColor.LIME,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Lime_Texture.png");
        dorsalFinMap.put(MermaidColor.BLUE,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Blue_Texture.png");
        dorsalFinMap.put(MermaidColor.CYAN,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Cyan_Texture.png");
        dorsalFinMap.put(MermaidColor.BLACK,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Black_Texture.png");
        dorsalFinMap.put(MermaidColor.GRAY,"Characters/SirensMermaid/Cosmetics/Mermaid/Dorsal_Fins/DorsalFin/Mermaids_Dorsal_Fin_Gray_Texture.png");
        textureMap.put(MermaidCosmetic.Dorsal_Fin,dorsalFinMap);

        // Dorsal_Fin_Large
        textureMap.put(MermaidCosmetic.Dorsal_Fin_Large,dorsalFinMap);
    }

    public static String getTexture(int cosmeticValue, MermaidColor textureColor){
        MermaidCosmetic mermaidCosmetic = MermaidCosmetic.get(cosmeticValue);

        String texture = mermaidCosmetic.getDefaultTexture();

        TreeMap<MermaidColor, String> colorTreeMap = textureMap.get(mermaidCosmetic);
        if(colorTreeMap != null && !colorTreeMap.isEmpty()) {
            String color = colorTreeMap.get(textureColor);
            if(color != null){
                texture = color;
            }
        }

        return texture;
    }
}
