package plugin.siren;

import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.event.EventRegistration;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.command.system.CommandRegistration;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import plugin.siren.Commands.MermaidCmd;
import plugin.siren.Commands.MermaidsCmd;
import plugin.siren.Compatibility.EndlessLeveling.EndlessLevelingRegistry;
import plugin.siren.Compatibility.OrbisOrigins.OrbisOriginsRegistry;
import plugin.siren.Compatibility.PlaceholderAPI.PlaceholderAPICompat;
import plugin.siren.Contributions.al3x.HStats;
import plugin.siren.Events.Interactions.*;
import plugin.siren.Events.PlayerReadyEventM;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.MermaidSettingsComponent;
import plugin.siren.Systems.MermaidSystem;
import plugin.siren.Utils.API.MermaidsUpdateChecker;
import plugin.siren.Utils.Config.EndlessLevelingConfig;
import plugin.siren.Utils.Config.MermaidsConfig;
import plugin.siren.Utils.Config.OrbisOriginsConfig;
import plugin.siren.Utils.Cosmetics.MermaidCosmeticSkin;
import plugin.siren.Utils.Github.GithubIgnore;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class Mermaids extends JavaPlugin {
    private static final String VERSION = "2.4.1";
    private static final boolean DEBUG = false;

    private static Mermaids plugin;
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private final Config<MermaidsConfig> config;
    private final Config<OrbisOriginsConfig> orbisOriginsConfig;
    private final Config<EndlessLevelingConfig> endlessLevelingConfig;

    private ComponentType<EntityStore, MermaidComponent> mermaidComponent;
    private ComponentType<EntityStore, MermaidSettingsComponent> mermaidSettingsComponent;

    private boolean orbisOriginsCompat;
    private boolean endlessLevelingCompat;

    public Mermaids(@Nonnull JavaPluginInit init){
        super(init);

        plugin = this;
        this.config = this.withConfig("Config", MermaidsConfig.CODEC);
        this.orbisOriginsConfig = this.withConfig("Compatibility/OrbisOrigins", OrbisOriginsConfig.CODEC);
        this.endlessLevelingConfig = this.withConfig("Compatibility/EndlessLeveling", EndlessLevelingConfig.CODEC);

        new HStats(GithubIgnore.getHStatsModUUID(), VERSION);

        orbisOriginsCompat = false;
        endlessLevelingCompat = false;
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

        this.mermaidComponent = this.getEntityStoreRegistry().registerComponent(MermaidComponent.class, MermaidComponent::new);
        if(this.mermaidComponent != null) {
            LOGGER.atInfo().log("Registered Mermaid Component.");
        }else{
            LOGGER.atInfo().log("Failed to register Mermaid Component.");
        }

        this.mermaidSettingsComponent = this.getEntityStoreRegistry().registerComponent(MermaidSettingsComponent.class, "MermaidSettings", MermaidSettingsComponent.CODEC);
        if (this.mermaidSettingsComponent != null) {
            LOGGER.atInfo().log("Registered Mermaid Settings Component.");
        } else {
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
        orbisOriginsConfig.save();
        endlessLevelingConfig.save();
        LOGGER.atInfo().log("Loaded all configs settings.");

        boolean configUpdated = config.get().ifConfigUpdate();
        if(configUpdated){
            config.save();
            LOGGER.atInfo().log("Updated config to latest version.");
        }

        boolean orbisOriginsConfigUpdated = orbisOriginsConfig.get().ifConfigUpdate();
        if(orbisOriginsConfigUpdated){
            orbisOriginsConfig.save();
            LOGGER.atInfo().log("Updated Orbis Origins config to latest version.");
        }

        boolean endlessLevelingConfigUpdated = endlessLevelingConfig.get().ifConfigUpdate();
        if(endlessLevelingConfigUpdated){
            endlessLevelingConfig.save();
            LOGGER.atInfo().log("Updated Orbis Origins config to latest version.");
        }

        if (HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("HelpChat:PlaceholderAPI")) != null) {
            new PlaceholderAPICompat().register();
        }

        if(orbisOriginsConfig.get().isEnabledOrbisOrigins()){
            if (HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("hexvane:OrbisOrigins")) != null) {
                LOGGER.atInfo().log("Compatibility with Orbis Origins was successful.");

                orbisOriginsCompat = true;

                Runnable registerOrbisOrigins = new Runnable() {
                    @Override
                    public void run() {
                        OrbisOriginsRegistry.register();
                    }
                };

                HytaleServer.SCHEDULED_EXECUTOR.schedule(registerOrbisOrigins,3, TimeUnit.SECONDS);
            }else{
                LOGGER.atWarning().log("The Orbis Origins mod is not installed, inside the Orbis Origins Leveling config. You declared to enable Orbis Origins Compatibility.");
            }
        }

        if(endlessLevelingConfig.get().isEnabledEndlessLeveling()) {
            if (HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("com.airijko:EndlessLeveling")) != null) {
                LOGGER.atInfo().log("Compatibility with Endless Leveling was successful.");

                endlessLevelingCompat = true;

                EndlessLevelingRegistry.register();
            }else{
                LOGGER.atWarning().log("The Endless Leveling mod is not installed, inside the Mermaids Endless Leveling config. You declared to enable Endless Leveling Compatibility.");
            }
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

        MermaidCosmeticSkin.registerCosmeticSkins();

        LOGGER.atInfo().log("Version " + VERSION + " of Mermaids has successfully loaded.");

        if(ifDebug()){
            LOGGER.atInfo().log("= =- -=- -=- -=- -=- -=- -=- -=- -= =");
            LOGGER.atInfo().log("Loaded Mermaids in Debug mode.");
        }

        MermaidsUpdateChecker.sendUpdateMessage(MermaidsUpdateChecker.Type.StartUp);

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

    public ComponentType<EntityStore, MermaidSettingsComponent> getMermaidSettingsComponentType(){
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

    public static Config<OrbisOriginsConfig> getOrbisOriginsConfig(){
        return plugin.orbisOriginsConfig;
    }

    public static Config<EndlessLevelingConfig> getEndlessLeveingConfig(){
        return plugin.endlessLevelingConfig;
    }

    public static boolean ifOrbisOrigins(){
        return plugin.orbisOriginsCompat;
    }

    public static boolean ifEndlessLeveling(){
        return plugin.endlessLevelingCompat;
    }

    public static boolean ifDebug(){
        return plugin.DEBUG || plugin.config.get().ifDebugMode();
    }
}
