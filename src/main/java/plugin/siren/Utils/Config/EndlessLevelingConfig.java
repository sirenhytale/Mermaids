package plugin.siren.Utils.Config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class EndlessLevelingConfig {
    public static final BuilderCodec<EndlessLevelingConfig> CODEC = BuilderCodec.builder(EndlessLevelingConfig.class, EndlessLevelingConfig::new)
            .append(new KeyedCodec<String>("Config-Name", Codec.STRING),
                    (ooConfig, cnStr) -> ooConfig.Name = cnStr,
                    (ooConfig) -> ooConfig.Name)
            .add()
            .append(new KeyedCodec<String>("Config-Description", Codec.STRING),
                    (ooConfig, cdStr) -> ooConfig.Description = cdStr,
                    (ooConfig) -> ooConfig.Description)
            .add()
            .append(new KeyedCodec<String>("Config-Information", Codec.STRING),
                    (ooConfig, ciStr) -> ooConfig.Information = ciStr,
                    (ooConfig) -> ooConfig.Information)
            .add()
            .append(new KeyedCodec<Integer>("ConfigVersion", Codec.INTEGER),
                    (ooConfig, cvInt) -> ooConfig.ConfigVersion = cvInt,
                    (ooConfig) -> ooConfig.ConfigVersion)
            .add()
            .append(new KeyedCodec<Boolean>("Enable-Endless-Leveling-Compatibility", Codec.BOOLEAN),
                    (ooConfig, eoocBool) -> ooConfig.Enabled = eoocBool,
                    (ooConfig) -> ooConfig.Enabled)
            .add()
            .append(new KeyedCodec<String>("Description-Enable-Endless-Leveling-Compatibility", Codec.STRING),
                    (ooConfig, deoocBool) -> ooConfig.EnabledDescription = deoocBool,
                    (ooConfig) -> ooConfig.EnabledDescription)
            .add()
            .append(new KeyedCodec<Boolean>("Default-Mermaids-Content", Codec.BOOLEAN),
                    (ooConfig, ddmcBool) -> ooConfig.MermaidsContent = ddmcBool,
                    (ooConfig) -> ooConfig.MermaidsContent)
            .add()
            .append(new KeyedCodec<String>("Description-Default-Mermaids-Content", Codec.STRING),
                    (ooConfig, cddmcStr) -> ooConfig.MermaidsContentDescription = cddmcStr,
                    (ooConfig) -> ooConfig.MermaidsContentDescription)
            .add()
            .append(new KeyedCodec<Boolean>("Mermaid-Only-In-Water", Codec.BOOLEAN),
                    (ooConfig, moiwBool) -> ooConfig.MermaidOnlyInWater = moiwBool,
                    (ooConfig) -> ooConfig.MermaidOnlyInWater)
            .add()
            .build();

    private final String NameDefault = "Endless Leveling Compatibility";
    private String Name = NameDefault;
    private final String DescriptionDefault = "This config file allows the user to modify compatibility issues with the Endless Leveling mod.";
    private String Description = DescriptionDefault;
    private final String InformationDefault = "Confused about what one of these statement do? Go to https://mermaids.dev/mermaids/config/ and scroll down to the Endless Leveling Config or reach out to me for more guidance.";
    private String Information = InformationDefault;
    private final int ConfigVersionDefault = 1;
    private int ConfigVersion = ConfigVersionDefault;
    private boolean Enabled = false;
    private String EnabledDescription = "By enabling Enable-Endless-Leveling-Compatibility, the Mermaids mod will add a brand new mermaid race to Endless Leveling.";
    private boolean MermaidsContent = false;
    private String MermaidsContentDescription = "By Disabling Default-Mermaids-Content, all Mermaid transformations will depend on the player being one of the mermaid races.";
    private boolean MermaidOnlyInWater = true;

    public EndlessLevelingConfig() {}

    public boolean ifConfigUpdate(){
        boolean configUpdated = false;

        if(ConfigVersionDefault > ConfigVersion){
            configUpdated = true;
            ConfigVersion = ConfigVersionDefault;
        }
        if(!Name.equalsIgnoreCase(NameDefault)){
            configUpdated = true;
            Name = NameDefault;
        }
        if(!Description.equalsIgnoreCase(DescriptionDefault)){
            configUpdated = true;
            Description = DescriptionDefault;
        }
        if(!Information.equalsIgnoreCase(InformationDefault)){
            configUpdated = true;
            Information = InformationDefault;
        }

        return configUpdated;
    }

    public boolean isEnabledEndlessLeveling(){
        return this.Enabled;
    }

    public boolean getMermaidContent(){
        return this.MermaidsContent;
    }

    public void setMermaidsContent(boolean disableContent){
        this.MermaidsContent = disableContent;
    }

    public boolean ifMermaidOnlyInWater(){
        return this.MermaidOnlyInWater;
    }

    public void setMermaidOnlyInWater(boolean onlyInWater){
        this.MermaidOnlyInWater = onlyInWater;
    }
}
