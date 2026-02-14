package plugin.siren;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.event.EventRegistration;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.command.system.CommandRegistration;
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
    private static final String VERSION = "Alpha 2.0.0-2026.2.13";
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
        LOGGER.atInfo().log("===---==---==---== MERMAIDS ==---==---==---===");
        LOGGER.atInfo().log("Mermaids has began to load.");

        EventRegistration<String, PlayerReadyEvent> playerReadyEventRegistration = this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, PlayerReadyEventM::onPlayerReadyEvent);
        if(playerReadyEventRegistration != null && playerReadyEventRegistration.isRegistered()) {
            LOGGER.atInfo().log("Registered Player Ready Event.");
        }else{
            LOGGER.atSevere().log("Failed to register Player Ready Event.");
        }

        CommandRegistration mermaidsCmdRegistration = this.getCommandRegistry().registerCommand(new MermaidsCmd());
        if(mermaidsCmdRegistration != null && mermaidsCmdRegistration.isRegistered()) {
            LOGGER.atInfo().log("Registered Mermaids Command.");
        }else{
            LOGGER.atSevere().log("Failed to register Mermaids Command.");
        }

        this.mermaidComponent = this.getEntityStoreRegistry().registerComponent(MermaidComponent.class, MermaidComponent::new);
        if(this.mermaidComponent != null) {
            LOGGER.atInfo().log("Registered Mermaid Component.");
        }else{
            LOGGER.atInfo().log("Failed to register Mermaid Component.");
        }

        this.mermaidSettingsComponent = this.getEntityStoreRegistry().registerComponent(MermaidSettings.class, "MermaidSettings", MermaidSettings.CODEC);
        if(this.mermaidSettingsComponent != null) {
            LOGGER.atInfo().log("Registered Mermaid Settings Component.");
        }else{
            LOGGER.atInfo().log("Failed to register Mermaid Settings Component.");
        }

        this.getEntityStoreRegistry().registerSystem(new MermaidSystem(this.mermaidComponent, this.mermaidSettingsComponent));
        LOGGER.atInfo().log("Registered Mermaid System.");

        this.getCodecRegistry(Interaction.CODEC).register("MermaidPotionSmallEffect", MermaidSmallPotionEffectInteraction.class, MermaidSmallPotionEffectInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("MermaidPotionMediumEffect", MermaidMediumPotionEffectInteraction.class, MermaidMediumPotionEffectInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("MermaidPotionLarge", MermaidLargePotionInteraction.class, MermaidLargePotionInteraction.CODEC);
        this.getCodecRegistry(Interaction.CODEC).register("MermaidFreeze", FreezeInteraction.class, FreezeInteraction.CODEC);
        LOGGER.atInfo().log("Registered Codec Interactions.");

        config.save();
        LOGGER.atInfo().log("Loaded config settings.");
        boolean configUpdated = false;
        if(config.get().getConfigVersionDefault() > config.get().getConfigVersion()){
            configUpdated = true;
            config.get().setConfigVersion(config.get().getConfigVersionDefault());
        }
        if(!config.get().getPluginVersion().equalsIgnoreCase(VERSION)){
            configUpdated = true;
            config.get().setPluginVersion(VERSION);
        }
        if(configUpdated){
            config.save();
            LOGGER.atInfo().log("Updated config to latest version.");
        }

        LOGGER.atInfo().log("Version " + VERSION + " of Mermaids has successfully loaded.");
        if(ifDebug()){
            LOGGER.atInfo().log("= =- -=- -=- -=- -=- -=- -=- -=- -= =");
            LOGGER.atInfo().log("Loaded Mermaids in Debug mode.");
        }
        LOGGER.atInfo().log("===---==---==---==---==---==---==---==---===");
    }

    @Override
    protected void shutdown(){
        LOGGER.atInfo().log("===---==---==---== MERMAIDS ==---==---==---===");
        LOGGER.atInfo().log("Mermaids has began to shutdown.");
        LOGGER.atInfo().log("Saving any necessary data.");
        LOGGER.atInfo().log("Version " + VERSION + " of Mermaids has successfully shutdown.");
        LOGGER.atInfo().log("===---==---==---==---==---==---==---==---===");
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
