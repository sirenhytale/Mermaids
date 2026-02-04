package plugin.siren.Utils.Config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import plugin.siren.Mermaids;

public class MermaidsConfig {

    public static final BuilderCodec<MermaidsConfig> CODEC = BuilderCodec.builder(MermaidsConfig.class, MermaidsConfig::new)
            .append(new KeyedCodec<String>("Config-Information", Codec.STRING),
                    (merConfig, ciStr, extraInfo) -> merConfig.Information = ciStr, // Setter
                    (merConfig, extraInfo) -> merConfig.Information)                    // Getter
            .add()
            .append(new KeyedCodec<Integer>("ConfigVersion", Codec.INTEGER),
                    (merConfig, cvInt, extraInfo) -> merConfig.ConfigVersion = cvInt, // Setter
                    (merConfig, extraInfo) -> merConfig.ConfigVersion)                    // Getter
            .add()
            .append(new KeyedCodec<String>("PluginName", Codec.STRING),
                    (merConfig, pnStr, extraInfo) -> merConfig.PluginName = pnStr, // Setter
                    (merConfig, extraInfo) -> merConfig.PluginName)                    // Getter
            .add()
            .append(new KeyedCodec<String>("Version", Codec.STRING),
                    (merConfig, vStr, extraInfo) -> merConfig.Version = vStr, // Setter
                    (merConfig, extraInfo) -> merConfig.Version)                    // Getter
            .add()
            .append(new KeyedCodec<String>("Website", Codec.STRING),
                    (merConfig, wStr, extraInfo) -> merConfig.Website = wStr, // Setter
                    (merConfig, extraInfo) -> merConfig.Website)                    // Getter
            .add()
            .append(new KeyedCodec<Integer>("Transformation-Mode", Codec.INTEGER),
                    (merConfig, tmInt, extraInfo) -> merConfig.TransformationMode = tmInt, // Setter
                    (merConfig, extraInfo) -> merConfig.TransformationMode)                    // Getter
            .add()
            .append(new KeyedCodec<String>("Description-Transformation-Mode", Codec.STRING),
                    (merConfig, dtmStr, extraInfo) -> merConfig.TransModeDesc = dtmStr, // Setter
                    (merConfig, extraInfo) -> merConfig.TransModeDesc)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Always-A-Mermaid-Even-On-Land", Codec.BOOLEAN),
                    (merConfig, aameolBool, extraInfo) -> merConfig.MermaidOnLand = aameolBool, // Setter
                    (merConfig, extraInfo) -> merConfig.MermaidOnLand)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Require-Transformation-Permission", Codec.BOOLEAN),
                    (merConfig, rtpBool, extraInfo) -> merConfig.RequireTransPerm = rtpBool, // Setter
                    (merConfig, extraInfo) -> merConfig.RequireTransPerm)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Require-Mermaids-UI-Permission", Codec.BOOLEAN),
                    (merConfig, rmupBool, extraInfo) -> merConfig.RequireUIPerm = rmupBool, // Setter
                    (merConfig, extraInfo) -> merConfig.RequireUIPerm)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Blocks-Can-Cause-Transformations", Codec.BOOLEAN),
                    (merConfig, bcctBool, extraInfo) -> merConfig.BlockTrans = bcctBool, // Setter
                    (merConfig, extraInfo) -> merConfig.BlockTrans)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Rain-Can-Cause-Transformations", Codec.BOOLEAN),
                    (merConfig, rcctBool, extraInfo) -> merConfig.RainTrans = rcctBool, // Setter
                    (merConfig, extraInfo) -> merConfig.RainTrans)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Mermaid-Have-A-Glow/Light", Codec.BOOLEAN),
                    (merConfig, mhaglBool, extraInfo) -> merConfig.MermaidLight = mhaglBool, // Setter
                    (merConfig, extraInfo) -> merConfig.MermaidLight)                    // Getter
            .add()
            .append(new KeyedCodec<Integer>("Mermaid-Glow/Light-Radius", Codec.INTEGER),
                    (merConfig, mglrInt, extraInfo) -> merConfig.LightRadius = mglrInt, // Setter
                    (merConfig, extraInfo) -> merConfig.LightRadius)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("DebugMode", Codec.BOOLEAN),
                    (merConfig, dmBool, extraInfo) -> merConfig.DebugMode = dmBool, // Setter
                    (merConfig, extraInfo) -> merConfig.DebugMode)                    // Getter
            .add()
            .build();

    private String Information = "Confused about what one of these statement do? Check out the Mermaids page on the Curseforge website and scroll down to Config Extra Info.";
    private int ConfigVersion = 6;
    private String PluginName = "Mermaids";
    private String Version = Mermaids.getVersion();
    private String Website = "https://www.curseforge.com/hytale/mods/mermaids";
    private int TransformationMode = 0;
    private String TransModeDesc = "TransformationMode = 0 : Transform when entering water, TransformationMode = 1 : Requires user to drink Mermaid Potion to Transform";
    private boolean MermaidOnLand = false;
    private boolean RequireTransPerm = false;
    private boolean RequireUIPerm = false;
    private boolean BlockTrans = true;
    private boolean RainTrans = false;
    private boolean MermaidLight = true;
    private int LightRadius = 33;
    private boolean DebugMode = false;

    public MermaidsConfig() {}

    public int getConfigVersion(){
        return ConfigVersion;
    }

    public int getTransformationMode(){
        return TransformationMode;
    }

    public boolean getMermaidOnLand(){
        return MermaidOnLand;
    }

    public void setMermaidOnLand(boolean onLand){
        this.MermaidOnLand = onLand;
    }

    public boolean getRequireTransformationPermission(){
        return RequireTransPerm;
    }

    public boolean getRequireUIPermission(){
        return RequireUIPerm;
    }

    public boolean getBlockTransformation(){
        return BlockTrans;
    }

    public void setBlockTransformation(boolean transform){
        this.BlockTrans = transform;
    }

    public boolean getRainTransformation(){
        return RainTrans;
    }

    public void setRainTransformation(boolean transform){
        this.RainTrans = transform;
    }

    public boolean getMermaidLight(){
        return MermaidLight;
    }

    public void setMermaidLight(boolean light){
        this.MermaidLight = light;
    }

    public int getLightRadius(){
        return LightRadius;
    }

    public void setLightRadius(int radius){
        this.LightRadius = radius;
    }

    public boolean ifDebugMode(){
        return DebugMode;
    }
}
