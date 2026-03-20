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

import javax.annotation.Nullable;

public class MermaidSettingsComponent implements Component<EntityStore> {
    public static final BuilderCodec<MermaidSettingsComponent> CODEC = BuilderCodec.builder(MermaidSettingsComponent.class, MermaidSettingsComponent::new)
            .append(new KeyedCodec<Boolean>("Mermaid-Toggle_ALPHA", Codec.BOOLEAN),
                    (merSettings, mtBool) -> merSettings.toggleMermaid = mtBool, // Setter
                    (merSettings) -> merSettings.toggleMermaid)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Mermaid-Transformation-PermPotion_ALPHA", Codec.BOOLEAN),
                    (merSettings, mtppBool) -> merSettings.permanentPotion = mtppBool, // Setter
                    (merSettings) -> merSettings.permanentPotion)                    // Getter
            .add()
            .append(new KeyedCodec<String>("Mermaid-Tail-Model_ALPHA", Codec.STRING),
                    (merSettings, mtmStr) -> merSettings.mermaidTail = mtmStr, // Setter
                    (merSettings) -> merSettings.mermaidTail)                    // Getter
            .add()
            .append(new KeyedCodec<String>("Mermaid-Tail-Color_ALPHA", Codec.STRING),
                    (merSettings, mtcStr) -> merSettings.tailColor = mtcStr, // Setter
                    (merSettings) -> merSettings.tailColor)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Force-Mermaid_ALPHA", Codec.BOOLEAN),
                    (merSettings, fmBool) -> merSettings.forceMermaid = fmBool, // Setter
                    (merSettings) -> merSettings.forceMermaid)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Force-Mermaid-OrbisOrigin_ALPHA", Codec.BOOLEAN),
                    (merSettings, fmooBool) -> merSettings.forceMermaidOrbisOrigin = fmooBool, // Setter
                    (merSettings) -> merSettings.forceMermaidOrbisOrigin)                    // Getter
            .add()
            .build();

    private boolean toggleMermaid;
    private boolean permanentPotion;

    private String mermaidTail;
    private String tailColor;

    private boolean forceMermaid;
    private boolean forceMermaidOrbisOrigin;

    private final String defaultMermaidTail = "Mermaids_Mermaid";
    private final String defaultTailColor = "Mermaids_Mermaid_Orange_Texture";

    public static ComponentType<EntityStore, MermaidSettingsComponent> getComponentType(){
        return Mermaids.get().getMermaidSettingsComponentType();
    }

    public MermaidSettingsComponent(){
        this.toggleMermaid = true;
        this.permanentPotion = false;

        this.mermaidTail = defaultMermaidTail;
        this.tailColor = defaultTailColor;

        this.forceMermaid = false;
        this.forceMermaidOrbisOrigin = false;
    }

    public MermaidSettingsComponent(MermaidSettingsComponent other){
        this.toggleMermaid = other.toggleMermaid;
        this.permanentPotion = other.permanentPotion;

        this.mermaidTail = other.mermaidTail;
        this.tailColor = other.tailColor;

        this.forceMermaid = other.forceMermaid;
        this.forceMermaidOrbisOrigin = other.forceMermaidOrbisOrigin;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new MermaidSettingsComponent(this);
    }

    public boolean isForcedMermaid(){
        boolean forcedMermaid = this.forceMermaid;

        if (HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("hexvane:OrbisOrigins")) != null) {
            forcedMermaid = forceMermaidOrbisOrigin || this.forceMermaid;
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
}
