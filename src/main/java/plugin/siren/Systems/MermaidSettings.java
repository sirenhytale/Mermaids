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
            .append(new KeyedCodec<String>("MermaidTailModelAlpha200", Codec.STRING),
                    (merSettings, mtmStr) -> merSettings.mermaidTail = mtmStr, // Setter
                    (merSettings) -> merSettings.mermaidTail)                    // Getter
            .add()
            .append(new KeyedCodec<String>("MermaidTailColorAlpha200", Codec.STRING),
                    (merSettings, mtcStr) -> merSettings.tailColor = mtcStr, // Setter
                    (merSettings) -> merSettings.tailColor)                    // Getter
            .add()
            .append(new KeyedCodec<String>("MermaidTailColorV2Alpha200", Codec.STRING),
                    (merSettings, mtcStr) -> merSettings.tailColor = mtcStr, // Setter
                    (merSettings) -> merSettings.tailColor)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("MermaidV2Model", Codec.BOOLEAN),
                    (merSettings, mv2mBool) -> merSettings.mermaidV2Model = mv2mBool, // Setter
                    (merSettings) -> merSettings.mermaidV2Model)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Alpha2.0.0", Codec.BOOLEAN),
                    (merSettings, a200Bool) -> merSettings.alpha200 = a200Bool, // Setter
                    (merSettings) -> merSettings.alpha200)                    // Getter
            .add()
            .build();

    private boolean toggleMermaid;
    private boolean permanentPotion;

    private String mermaidTail;
    private String tailColor;
    private String tailColorV2;

    private boolean mermaidV2Model;

    private boolean alpha200;

    public MermaidSettings(){
        this.toggleMermaid = true;
        this.permanentPotion = false;

        this.mermaidTail = "MermaidV2";
        this.tailColor = "MermaidPlayerGrayscale";
        this.tailColorV2 = "MermaidTextureV2";

        this.mermaidV2Model = false;

        this.alpha200 = false;
    }

    public MermaidSettings(MermaidSettings other){
        this.toggleMermaid = other.toggleMermaid;
        this.permanentPotion = other.permanentPotion;

        this.mermaidTail = other.mermaidTail;
        this.tailColor = other.tailColor;
        this.tailColorV2 = other.tailColorV2;

        this.mermaidV2Model = other.mermaidV2Model;

        this.alpha200 = other.alpha200;
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

    public String getTailColorV2() {
        return tailColorV2;
    }

    public void setTailColorV2(String tailColor) {
        this.tailColorV2 = tailColor;
    }

    public boolean ifUseMermaidV2(){
        return mermaidV2Model;
    }

    public void setMermaidV2Model(boolean useMermaidV2){
        this.mermaidV2Model = useMermaidV2;
    }

    public boolean getAlpha200(){
        return this.alpha200;
    }

    public void setAlpha200(boolean alpha){
        this.alpha200 = alpha;
    }
}
