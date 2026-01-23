package plugin.siren.Utils.Config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import plugin.siren.Mermaids;
import plugin.siren.Utils.UI.MermaidUIPage;

public class MermaidsConfig {

    public static final BuilderCodec<MermaidsConfig> CODEC = BuilderCodec.builder(MermaidsConfig.class, MermaidsConfig::new)
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
            .append(new KeyedCodec<Boolean>("DebugMode", Codec.BOOLEAN),
                    (merConfig, dmBool, extraInfo) -> merConfig.DebugMode = dmBool, // Setter
                    (merConfig, extraInfo) -> merConfig.DebugMode)                    // Getter
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
            .build();

    private int ConfigVersion = 3;
    private String PluginName = "Mermaids";
    private String Version = Mermaids.getVersion();
    private String Website = "https://www.curseforge.com/hytale/mods/mermaids";
    private boolean DebugMode = false;
    private boolean RequireTransPerm = false;
    private boolean RequireUIPerm = false;
    private boolean BlockTrans = true;

    public MermaidsConfig() {}

    public int getConfigVersion(){
        return ConfigVersion;
    }

    public boolean ifDebugMode(){
        return DebugMode;
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
}
