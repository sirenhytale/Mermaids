package plugin.siren.Systems;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nullable;

public class MermaidSettings implements Component<EntityStore> {

    public static final BuilderCodec<MermaidSettings> CODEC = BuilderCodec.builder(MermaidSettings.class, MermaidSettings::new)
            .append(new KeyedCodec<Boolean>("MermaidToggle", Codec.BOOLEAN),
                    (merSettings, mtBool) -> merSettings.toggleMermaid = mtBool, // Setter
                    (merSettings) -> merSettings.toggleMermaid)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("MermaidTransPermPotion", Codec.BOOLEAN),
                    (merSettings, mtppBool) -> merSettings.permanentPotion = mtppBool, // Setter
                    (merSettings) -> merSettings.permanentPotion)                    // Getter
            .add()
            .append(new KeyedCodec<String>("MermaidTailModel", Codec.STRING),
                    (merSettings, mtmStr) -> merSettings.mermaidTail = mtmStr, // Setter
                    (merSettings) -> merSettings.mermaidTail)                    // Getter
            .add()
            .append(new KeyedCodec<String>("MermaidTailColor", Codec.STRING),
                    (merSettings, mtcStr) -> merSettings.tailColor = mtcStr, // Setter
                    (merSettings) -> merSettings.tailColor)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("MermaidV2Model", Codec.BOOLEAN),
                    (merSettings, mv2mBool) -> merSettings.mermaidV2Model = mv2mBool, // Setter
                    (merSettings) -> merSettings.mermaidV2Model)                    // Getter
            .add()
            .build();

    private boolean toggleMermaid;
    private boolean permanentPotion;

    private String mermaidTail;
    private String tailColor;

    private boolean mermaidV2Model;

    public MermaidSettings(){
        this.toggleMermaid = true;
        this.permanentPotion = false;

        this.mermaidTail = "MermaidBigFinPlayer";
        this.tailColor = "MermaidPlayerGrayscale";

        this.mermaidV2Model = false;
    }

    public MermaidSettings(MermaidSettings other){
        this.toggleMermaid = other.toggleMermaid;
        this.permanentPotion = other.permanentPotion;

        this.mermaidTail = other.mermaidTail;
        this.tailColor = other.tailColor;

        this.mermaidV2Model = other.mermaidV2Model;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new MermaidSettings(this);
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

    public boolean ifUseMermaidV2(){
        return mermaidV2Model;
    }

    public void setMermaidV2Model(boolean useMermaidV2){
        this.mermaidV2Model = useMermaidV2;
    }
}
