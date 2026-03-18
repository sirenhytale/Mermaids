package plugin.siren;

import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.event.EventRegistration;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandRegistration;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import plugin.siren.Commands.MermaidCmd;
import plugin.siren.Commands.MermaidsCmd;
import plugin.siren.Compatibility.PlaceholderAPI.PlaceholderAPICompat;
import plugin.siren.Contributions.al3x.HStats;
import plugin.siren.Events.Interactions.*;
import plugin.siren.Events.PlayerReadyEventM;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.MermaidSettings;
import plugin.siren.Systems.MermaidSystem;
import plugin.siren.Utils.API.UpdateChecker;
import plugin.siren.Utils.Config.MermaidsConfig;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Mermaids extends JavaPlugin {
    private static final boolean v1 = false;
    private static final String VERSION = "Alpha 2.0.0-2026.03.17";//"Alpha 2.0.0-2026.03.01";
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

        new HStats("8aab16df-13b6-429e-9568-9b80effacdc8", VERSION);
    }

    @Override
    protected void setup(){
        LOGGER.atInfo().log("===---==---==---== MERMAIDS ==---==---==---===");
        LOGGER.atInfo().log("Mermaids has began to load.");

        if (HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("HelpChat:PlaceholderAPI")) != null) {
            new PlaceholderAPICompat().register();
        }

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

        CommandRegistration mermaidCmdRegistration = this.getCommandRegistry().registerCommand(new MermaidCmd());
        if(mermaidCmdRegistration != null && mermaidCmdRegistration.isRegistered()) {
            LOGGER.atInfo().log("Registered Mermaid Command.");
        }else{
            LOGGER.atSevere().log("Failed to register Mermaid Command.");
        }

        this.mermaidComponent = this.getEntityStoreRegistry().registerComponent(MermaidComponent.class, MermaidComponent::new);
        if(this.mermaidComponent != null) {
            LOGGER.atInfo().log("Registered Mermaid Component.");
        }else{
            LOGGER.atInfo().log("Failed to register Mermaid Component.");
        }

        if(ifVersion1()){
            this.mermaidSettingsComponent = this.getEntityStoreRegistry().registerComponent(MermaidSettings.class, "MermaidSettings", MermaidSettings.CODECV1);
            if(this.mermaidSettingsComponent != null) {
                LOGGER.atInfo().log("Registered Mermaid Settings Component.");
            }else{
                LOGGER.atInfo().log("Failed to register Mermaid Settings Component.");
            }
        }else {
            this.mermaidSettingsComponent = this.getEntityStoreRegistry().registerComponent(MermaidSettings.class, "MermaidSettings", MermaidSettings.CODEC);
            if (this.mermaidSettingsComponent != null) {
                LOGGER.atInfo().log("Registered Mermaid Settings Component.");
            } else {
                LOGGER.atInfo().log("Failed to register Mermaid Settings Component.");
            }
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
        boolean configUpdated = config.get().ifConfigUpdate();
        if(configUpdated){
            config.save();
            LOGGER.atInfo().log("Updated config to latest version.");
        }

        LOGGER.atInfo().log("Version " + VERSION + " of Mermaids has successfully loaded.");

        if(!ifVersion1()){
            LOGGER.atInfo().log("= =- -=- -=- -=- -=- -=- -=- -=- -= =");
            LOGGER.atInfo().log("Loaded Mermaids in Alpha state.");
        }

        if(ifDebug()){
            LOGGER.atInfo().log("= =- -=- -=- -=- -=- -=- -=- -=- -= =");
            LOGGER.atInfo().log("Loaded Mermaids in Debug mode.");
        }

        String recentVersion = UpdateChecker.checkForUpdate();
        if(!VERSION.equalsIgnoreCase(recentVersion)){
            LOGGER.atInfo().log("= =- -=- -=- -=- -=- -=- -=- -=- -= =");
            if(Mermaids.ifVersion1()) {
                String versionMessage = "The Mermaids mod version is outdated, Mermaids has released v" + recentVersion + ".";
                LOGGER.atInfo().log(versionMessage);

                Runnable updateCheckRunnable = new Runnable() {
                    @Override
                    public void run() {
                        LOGGER.atInfo().log(versionMessage);
                    }
                };
                HytaleServer.SCHEDULED_EXECUTOR.schedule(updateCheckRunnable,5, TimeUnit.SECONDS);
            }else{
                if(!recentVersion.equalsIgnoreCase("released")) {
                    String versionMessage = "The Alpha Mermaids mod version is outdated, Mermaids has released " + recentVersion + ".";
                    LOGGER.atInfo().log(versionMessage);

                    Runnable updateCheckRunnable = new Runnable() {
                        @Override
                        public void run() {
                            LOGGER.atInfo().log(versionMessage);
                        }
                    };
                    HytaleServer.SCHEDULED_EXECUTOR.schedule(updateCheckRunnable,5, TimeUnit.SECONDS);
                }else{
                    String versionMessage = "The Alpha Mermaids mod version 2.0.0 has been released, the server is currently on an outdated version of the Mermaids mod.";
                    LOGGER.atInfo().log(versionMessage);

                    Runnable updateCheckRunnable = new Runnable() {
                        @Override
                        public void run() {
                            LOGGER.atInfo().log(versionMessage);
                        }
                    };
                    HytaleServer.SCHEDULED_EXECUTOR.schedule(updateCheckRunnable,15, TimeUnit.SECONDS);
                }
            }
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
        return this.getMermaidSettingsComponentType();
    }

    public ComponentType<EntityStore, MermaidSettings> getMermaidSettingsComponentType(){
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
        return plugin.DEBUG || plugin.config.get().ifDebugMode();
    }

    public static boolean ifVersion1(){
        return v1;
    }
}
