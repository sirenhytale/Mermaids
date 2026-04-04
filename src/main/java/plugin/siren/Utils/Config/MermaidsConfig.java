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
            .append(new KeyedCodec<String>("Download-Site", Codec.STRING),
                    (merConfig, dsStr, extraInfo) -> merConfig.DownloadSite = dsStr, // Setter
                    (merConfig, extraInfo) -> merConfig.DownloadSite)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("Enable-Generic-Console-Logs", Codec.BOOLEAN),
                    (merConfig, egclBool, extraInfo) -> merConfig.ConsoleLogs = egclBool, // Setter
                    (merConfig, extraInfo) -> merConfig.ConsoleLogs)                    // Getter
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
            .append(new KeyedCodec<Boolean>("Items-Can-Increase-Swim-Speed", Codec.BOOLEAN),
                    (merConfig, icissBool, extraInfo) -> merConfig.ItemSwimSpeed = icissBool, // Setter
                    (merConfig, extraInfo) -> merConfig.ItemSwimSpeed)                    // Getter
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
            .append(new KeyedCodec<Boolean>("EasyHunger-By:Haasapenas-Compatibility", Codec.BOOLEAN),
                    (merConfig, ehbhcBool, extraInfo) -> merConfig.EasyHungerCompat = ehbhcBool, // Setter
                    (merConfig, extraInfo) -> merConfig.EasyHungerCompat)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("AquaThirst&Hunger-By:Jume-Compatibility", Codec.BOOLEAN),
                    (merConfig, athbjcBool, extraInfo) -> merConfig.AquaThirstHungerCompat = athbjcBool, // Setter
                    (merConfig, extraInfo) -> merConfig.AquaThirstHungerCompat)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("DivingTale-By:xnrdev-Compatibility", Codec.BOOLEAN),
                    (merConfig, dtbxcBool, extraInfo) -> merConfig.DivingTaleCompat = dtbxcBool, // Setter
                    (merConfig, extraInfo) -> merConfig.DivingTaleCompat)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("MoreNPC-By:BlueEyesWhiteMen-Compatibility", Codec.BOOLEAN),
                    (merConfig, mnbbcBool, extraInfo) -> merConfig.MoreNPCCompat = mnbbcBool, // Setter
                    (merConfig, extraInfo) -> merConfig.MoreNPCCompat)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("KeybladesReimagined-By:TaHie-Compatibility", Codec.BOOLEAN),
                    (merConfig, kbrbthcBool, extraInfo) -> merConfig.KeybladeReimagCompat = kbrbthcBool, // Setter
                    (merConfig, extraInfo) -> merConfig.KeybladeReimagCompat)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("New-Version-Message", Codec.BOOLEAN),
                    (merConfig, nvmBool, extraInfo) -> merConfig.NewVersion = nvmBool, // Setter
                    (merConfig, extraInfo) -> merConfig.NewVersion)                    // Getter
            .add()
            .append(new KeyedCodec<Boolean>("DebugMode", Codec.BOOLEAN),
                    (merConfig, dmBool, extraInfo) -> merConfig.DebugMode = dmBool, // Setter
                    (merConfig, extraInfo) -> merConfig.DebugMode)                    // Getter
            .add()
            .build();

    private String InformationDefault = "Confused about what one of these statement do? Go to https://www.mermaids.dev/mermaids/config/ or check out the Mermaids page on the Curseforge website and scroll down to Config Extra Info.";
    private String Information = InformationDefault;
    private final int ConfigVersionDefault = 13;
    private int ConfigVersion = ConfigVersionDefault;
    private String PluginName = "Mermaids";
    private String Version = Mermaids.getVersion();
    private String WebsiteDefault = "https://www.mermaids.dev/mermaids/";
    private String Website = WebsiteDefault;
    private String DownloadSiteDefault = "https://www.curseforge.com/hytale/mods/mermaids";
    private String DownloadSite = DownloadSiteDefault;
    private boolean ConsoleLogs = false;
    private int TransformationMode = 0;
    private String TransModeDesc = "TransformationMode = 0 : Transform when entering water, TransformationMode = 1 : Requires user to drink Mermaid Potion to Transform";
    private boolean MermaidOnLand = false;
    private boolean RequireTransPerm = false;
    private boolean RequireUIPerm = false;
    private boolean ItemSwimSpeed = true;
    private boolean BlockTrans = true;
    private boolean RainTrans = false;
    private boolean MermaidLight = true;
    private int LightRadius = 33;
    private boolean EasyHungerCompat = true;
    private boolean AquaThirstHungerCompat = true;
    private boolean DivingTaleCompat = true;
    private boolean MoreNPCCompat = true;
    private boolean KeybladeReimagCompat = true;
    private boolean NewVersion = true;
    private boolean DebugMode = false;

    private boolean RequireForceMermaid = false;
    private boolean ForceOnlyInWater = false;

    public MermaidsConfig() {}

    public boolean ifConfigUpdate(){
        boolean configUpdated = false;

        if(ConfigVersionDefault > ConfigVersion){
            configUpdated = true;
            ConfigVersion = ConfigVersionDefault;
        }
        if(!Version.equalsIgnoreCase(Mermaids.getVersion())){
            configUpdated = true;
            Version = Mermaids.getVersion();
        }
        if(!Information.equalsIgnoreCase(InformationDefault)){
            configUpdated = true;
            Information = InformationDefault;
        }
        if(!Website.equalsIgnoreCase(WebsiteDefault)){
            configUpdated = true;
            Website = WebsiteDefault;
        }
        if(!DownloadSite.equalsIgnoreCase(DownloadSiteDefault)){
            configUpdated = true;
            DownloadSite = DownloadSiteDefault;
        }

        return configUpdated;
    }

    public int getConfigVersionDefault(){
        return ConfigVersionDefault;
    }

    public int getConfigVersion(){
        return ConfigVersion;
    }

    public void setConfigVersion(int version){
        this.ConfigVersion = version;
    }

    public String getPluginVersion(){
        return Version;
    }

    public void setPluginVersion(String version){
        this.Version = version;
    }

    public boolean ifConsoleLogs(){
        return this.ConsoleLogs;
    }

    public int getTransformationMode(){
        return TransformationMode;
    }

    public void setTransformationMode(int mode){
        this.TransformationMode = mode;
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

    public boolean getItemIncreaseSwimSpeed(){
        return this.ItemSwimSpeed;
    }

    public void setItemIncreaseSwimSpeed(boolean increaseSwimSpeed){
        this.ItemSwimSpeed = increaseSwimSpeed;
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

    public boolean getEasyHungerCompat(){
        return this.EasyHungerCompat;
    }

    public boolean getAquaThirstHungerCompat(){
        return this.AquaThirstHungerCompat;
    }

    public boolean getDivingTaleCompat(){
        return this.DivingTaleCompat;
    }

    public boolean getMoreNPCCompat(){
        return this.MoreNPCCompat;
    }

    public boolean getKeybladeReimagCompat(){
        return this.KeybladeReimagCompat;
    }

    public boolean ifNewVersion(){
        return NewVersion;
    }

    public boolean ifDebugMode(){
        return DebugMode;
    }

    public boolean ifRequireForceMermaid(){
        return this.RequireForceMermaid;
    }

    public void setRequireForceMermaid(boolean requireForceMermaid){
        this.RequireForceMermaid = requireForceMermaid;
    }

    public boolean ifForceMermaidOnlyInWater(){
        return this.ForceOnlyInWater;
    }

    public void setForceMermaidOnlyInWater(boolean forceOnlyInWater){
        this.ForceOnlyInWater = forceOnlyInWater;
    }
}
