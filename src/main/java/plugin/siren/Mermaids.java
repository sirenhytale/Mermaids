package plugin.siren;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import plugin.siren.Commands.MermaidsCmd;
import plugin.siren.Events.Interactions.*;
import plugin.siren.Events.PlayerReadyEventM;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.MermaidSettings;
import plugin.siren.Systems.MermaidSystem;
import plugin.siren.Utils.Config.MermaidsConfig;

import javax.annotation.Nonnull;

public class Mermaids extends JavaPlugin {
    private static final String VERSION = "1.3.0";
    private static final boolean DEBUG = false;

    private static Mermaids plugin;
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private final Config<MermaidsConfig> config;

    private ComponentType<EntityStore, MermaidComponent> mermaidComponent;
    private ComponentType<EntityStore, MermaidSettings> mermaidSettingsComponent;

    public Mermaids(@Nonnull JavaPluginInit init){
        super(init);

        plugin = this;
        this.config = this.withConfig("Mermaids", MermaidsConfig.CODEC);
    }

    @Override
    protected void setup(){
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, PlayerReadyEventM::onPlayerReadyEvent);
        /*this.getCommandRegistry().registerCommand(new MermaidsUI());
        this.getCommandRegistry().registerCommand(new ToggleMermaid());
        this.getCommandRegistry().registerCommand(new MermaidCompBugCmd());*/
        this.getCommandRegistry().registerCommand(new MermaidsCmd());

        this.mermaidComponent = this.getEntityStoreRegistry().registerComponent(MermaidComponent.class, MermaidComponent::new);
        this.mermaidSettingsComponent = this.getEntityStoreRegistry().registerComponent(MermaidSettings.class, "MermaidSettings", MermaidSettings.CODEC);
        this.getEntityStoreRegistry().registerSystem(new MermaidSystem(this.mermaidComponent, this.mermaidSettingsComponent));

        this.getCodecRegistry(Interaction.CODEC).register("MermaidPotionSmallEffect", MermaidSmallPotionEffectInteraction.class, MermaidSmallPotionEffectInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("MermaidPotionMediumEffect", MermaidMediumPotionEffectInteraction.class, MermaidMediumPotionEffectInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("MermaidPotionLarge", MermaidLargePotionInteraction.class, MermaidLargePotionInteraction.CODEC);

        //config.load();
        config.save();

        LOGGER.atInfo().log("Version " + VERSION + " of Mermaids has loaded.");

        if(ifDebug()){
            LOGGER.atInfo().log("Loaded in Debug mode.");
        }
    }

    public ComponentType<EntityStore, MermaidComponent> getMermaidComponentType(){
        return mermaidComponent;
    }

    public ComponentType<EntityStore, MermaidSettings> getMermaidSetingsComponentType(){
        return mermaidSettingsComponent;
    }

    public static Mermaids get(){
        return plugin;
    }

    public static String getVersion(){
        return VERSION;
    }

    public static Config<MermaidsConfig> getConfig(){
        return plugin.config;
    }

    public static boolean ifDebug(){
        boolean showDebug = plugin.DEBUG || plugin.config.get().ifDebugMode();
        return showDebug;
    }
}
