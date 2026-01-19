package plugin.siren.Utils.Config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import plugin.siren.Mermaids;

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
            .build();

    private int ConfigVersion = 1;
    private String PluginName = "Mermaids";
    private String Version = Mermaids.getVersion();
    private String Website = "https://www.curseforge.com/hytale/mods/mermaids";
    private boolean DebugMode = false;
    private boolean RequireTransPerm = false;

    public MermaidsConfig() {}

    public boolean ifDebugMode(){
        return DebugMode;
    }

    public boolean getRequireTransformationPermission(){
        return RequireTransPerm;
    }
}
