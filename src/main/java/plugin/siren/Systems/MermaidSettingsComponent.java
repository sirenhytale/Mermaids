package plugin.siren.Systems;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Utils.Cosmetics.MermaidCosmetic;
import plugin.siren.Utils.Cosmetics.MermaidCosmeticSkin;
import plugin.siren.Utils.Cosmetics.MermaidCosmeticType;
import plugin.siren.Utils.Models.MermaidColor;
import plugin.siren.Utils.Models.MermaidModel;

import javax.annotation.Nullable;

public class MermaidSettingsComponent implements Component<EntityStore> {
    public static final BuilderCodec<MermaidSettingsComponent> CODEC = BuilderCodec.builder(MermaidSettingsComponent.class, MermaidSettingsComponent::new)
            .append(new KeyedCodec<Boolean>("Mermaid-Toggle", Codec.BOOLEAN),
                    (merSettings, mtBool) -> merSettings.toggleMermaid = mtBool, // Setter
                    (merSettings) -> merSettings.toggleMermaid)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Mermaid-Transformation-PermPotion", Codec.BOOLEAN),
                    (merSettings, mtppBool) -> merSettings.permanentPotion = mtppBool, // Setter
                    (merSettings) -> merSettings.permanentPotion)                    // Getter
            .add()
            .append(new KeyedCodec<String>("Mermaid-Tail-Model", Codec.STRING),
                    (merSettings, mtmStr) -> merSettings.mermaidTail = mtmStr, // Setter
                    (merSettings) -> merSettings.mermaidTail)                    // Getter
            .add()
            .append(new KeyedCodec<Integer>("Mermaid-Tail-Model-ID", Codec.INTEGER),
                    (merSettings, mtmiInt) -> merSettings.modelId = mtmiInt, // Setter
                    (merSettings) -> merSettings.modelId)                    // Getter
            .add()
            .append(new KeyedCodec<String>("Mermaid-Tail-Color", Codec.STRING),
                    (merSettings, mtcStr) -> merSettings.tailColor = mtcStr, // Setter
                    (merSettings) -> merSettings.tailColor)                    // Getter
            .add()
            .append(new KeyedCodec<Integer>("Mermaid-Cosmetic-Tail-Color", Codec.INTEGER),
                    (merSettings, mctcInt) -> merSettings.cosmeticColor = mctcInt, // Setter
                    (merSettings) -> merSettings.cosmeticColor)                    // Getter
            .add()
            .append(new KeyedCodec<Integer>("Mermaid-Cosmetic-Dorsal-Fin", Codec.INTEGER),
                    (merSettings, mcdfInt) -> merSettings.dorsalFin = mcdfInt, // Setter
                    (merSettings) -> merSettings.dorsalFin)                    // Getter
            .add()
            .append(new KeyedCodec<Integer>("Mermaid-Cosmetic-Pectoral-Fin", Codec.INTEGER),
                    (merSettings, mcpfInt) -> merSettings.pectoralFin = mcpfInt, // Setter
                    (merSettings) -> merSettings.pectoralFin)                    // Getter
            .add()
            .append(new KeyedCodec<Integer>("Mermaid-Cosmetic-Auricle-Fin", Codec.INTEGER),
                    (merSettings, mcafInt) -> merSettings.auricleFin = mcafInt, // Setter
                    (merSettings) -> merSettings.auricleFin)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Force-Mermaid", Codec.BOOLEAN),
                    (merSettings, fmBool) -> merSettings.forceMermaid = fmBool, // Setter
                    (merSettings) -> merSettings.forceMermaid)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Force-Mermaid-OrbisOrigin", Codec.BOOLEAN),
                    (merSettings, fmooBool) -> merSettings.forceMermaidOrbisOrigin = fmooBool, // Setter
                    (merSettings) -> merSettings.forceMermaidOrbisOrigin)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Force-Mermaid-EndlessLeveling", Codec.BOOLEAN),
                    (merSettings, fmelBool) -> merSettings.forceMermaidEndlessLeveling = fmelBool, // Setter
                    (merSettings) -> merSettings.forceMermaidEndlessLeveling)                    // Getter
            .add()
            .build();

    private boolean toggleMermaid;
    private boolean permanentPotion;

    private String mermaidTail;
    private int modelId;
    private String tailColor;
    private int cosmeticColor;
    private int dorsalFin;
    private int pectoralFin;
    private int auricleFin;

    private boolean forceMermaid;
    private boolean forceMermaidOrbisOrigin;
    private boolean forceMermaidEndlessLeveling;

    private final String defaultMermaidTail = MermaidModel.Mermaid.getModel();
    private final String defaultTailColor = "Mermaids_Mermaid_Orange_Texture";

    public static ComponentType<EntityStore, MermaidSettingsComponent> getComponentType(){
        return Mermaids.get().getMermaidSettingsComponentType();
    }

    public MermaidSettingsComponent(){
        this.toggleMermaid = true;
        this.permanentPotion = false;

        this.mermaidTail = defaultMermaidTail;
        this.modelId = MermaidModel.Mermaid.getValue();
        this.tailColor = defaultTailColor;
        this.cosmeticColor = 0;
        this.dorsalFin = -1;
        this.pectoralFin = -1;
        this.auricleFin = -1;

        this.forceMermaid = false;
        this.forceMermaidOrbisOrigin = false;
        this.forceMermaidEndlessLeveling = false;
    }

    public MermaidSettingsComponent(MermaidSettingsComponent other){
        this.toggleMermaid = other.toggleMermaid;
        this.permanentPotion = other.permanentPotion;

        this.mermaidTail = other.mermaidTail;
        this.modelId = other.modelId;
        this.tailColor = other.tailColor;
        this.cosmeticColor = other.cosmeticColor;
        this.dorsalFin = other.dorsalFin;
        this.pectoralFin = other.pectoralFin;
        this.auricleFin = other.auricleFin;

        this.forceMermaid = other.forceMermaid;
        this.forceMermaidOrbisOrigin = other.forceMermaidOrbisOrigin;
        this.forceMermaidEndlessLeveling = other.forceMermaidEndlessLeveling;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new MermaidSettingsComponent(this);
    }

    public boolean isForcedMermaid(){
        boolean forcedMermaid = this.forceMermaid;

        if (HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("hexvane:OrbisOrigins")) != null) {
            forcedMermaid = forceMermaidOrbisOrigin || forcedMermaid;
        }

        if (HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("com.airijko:EndlessLeveling")) != null) {
            forcedMermaid = forceMermaidEndlessLeveling || forcedMermaid;
        }

        return forcedMermaid;
    }

    public void setForcedMermaid(boolean forcedMermaid){
        this.forceMermaid = forcedMermaid;
    }

    public boolean isForcedMermaidOrbisOrigin(){
        boolean forcedMermaid = false;

        if (HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("hexvane:OrbisOrigins")) != null) {
            forcedMermaid = forceMermaidOrbisOrigin;
        }

        return forcedMermaid;
    }

    public void setForcedMermaidOrbisOrigin(boolean forcedMermaid){
        this.forceMermaidOrbisOrigin = forcedMermaid;
    }

    public boolean isForcedMermaidEndlessLeveling(){
        boolean forcedMermaid = false;

        if (HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("com.airijko:EndlessLeveling")) != null) {
            forcedMermaid = forceMermaidEndlessLeveling;
        }

        return forcedMermaid;
    }

    public void setForcedMermaidEndlessLeveling(boolean forcedMermaid){
        this.forceMermaidEndlessLeveling = forcedMermaid;
    }

    public boolean getToggleMermaid(){
        return toggleMermaid;
    }

    public void setToggleMermaid(boolean toggle){
        this.toggleMermaid = toggle;
    }

    public boolean ifPermanentPotion(){
        return permanentPotion;
    }

    public void setPermanentPotion(boolean permPotion){
        this.permanentPotion = permPotion;
    }

    public String getMermaidTail(){
        return mermaidTail;
    }

    public void setMermaidTail(String mermaidTail){
        this.mermaidTail = mermaidTail;
    }

    public MermaidModel getMermaidTailId(){
        return MermaidModel.get(modelId);
    }

    public void setMermaidTailId(MermaidModel model){
        modelId = model.getValue();
    }

    public MermaidColor getCosmeticColor(){
        return MermaidColor.get(cosmeticColor);
    }

    public void setCosmeticColor(MermaidColor textureColor){
        cosmeticColor = textureColor.getValue();
    }

    public String getTailColor() {
        return tailColor;
    }

    public void setTailColor(String tailColor) {
        this.tailColor = tailColor;
    }

    public String getDefaultMermaidTail(){
        return this.defaultMermaidTail;
    }

    public String getDefaultTailColor(){
        return this.defaultTailColor;
    }

    public boolean hasMermaidCosmetic(MermaidCosmeticType cosmeticType){
        if(cosmeticType.equals(MermaidCosmeticType.DORSAL_FIN)){
            if(dorsalFin == -1){
                return false;
            }
            return true;
        }
        if(cosmeticType.equals(MermaidCosmeticType.PECTORAL_FIN)) {
            if (pectoralFin == -1) {
                return false;
            }
            return true;
        }
        if(cosmeticType.equals(MermaidCosmeticType.AURICLE_FIN)){
            if(auricleFin == -1){
                return false;
            }
            return true;
        }

        return false;
    }

    public MermaidCosmetic getMermaidCosmetic(MermaidCosmeticType cosmeticType) {
        if(cosmeticType.equals(MermaidCosmeticType.DORSAL_FIN)){
            if(hasMermaidCosmetic(cosmeticType)) {
                return MermaidCosmetic.get(dorsalFin);
            }

            return null;
        }
        if(cosmeticType.equals(MermaidCosmeticType.PECTORAL_FIN)){
            if(hasMermaidCosmetic(cosmeticType)) {
                return MermaidCosmetic.get(pectoralFin);
            }

            return null;
        }
        if(cosmeticType.equals(MermaidCosmeticType.AURICLE_FIN)){
            if(hasMermaidCosmetic(cosmeticType)) {
                return MermaidCosmetic.get(auricleFin);
            }

            return null;
        }

        return null;
    }

    public int getMermaidCosmeticValue(MermaidCosmeticType cosmeticType) {
        if(cosmeticType.equals(MermaidCosmeticType.DORSAL_FIN)){
            if(hasMermaidCosmetic(cosmeticType)) {
                return dorsalFin;
            }

            return -1;
        }
        if(cosmeticType.equals(MermaidCosmeticType.PECTORAL_FIN)){
            if(hasMermaidCosmetic(cosmeticType)) {
                return pectoralFin;
            }

            return -1;
        }
        if(cosmeticType.equals(MermaidCosmeticType.AURICLE_FIN)){
            if(hasMermaidCosmetic(cosmeticType)) {
                return auricleFin;
            }

            return -1;
        }

        return -1;
    }

    public void setMermaidCosmetic(MermaidCosmeticType cosmeticType, int cosmeticValue) {
        if(cosmeticType.equals(MermaidCosmeticType.DORSAL_FIN)){
            dorsalFin = cosmeticValue;
        }
        if(cosmeticType.equals(MermaidCosmeticType.PECTORAL_FIN)){
            pectoralFin = cosmeticValue;
        }
        if(cosmeticType.equals(MermaidCosmeticType.AURICLE_FIN)){
            auricleFin = cosmeticValue;
        }
    }
}
