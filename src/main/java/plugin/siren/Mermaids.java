package plugin.siren;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import plugin.siren.Commands.*;
import plugin.siren.Events.PlayerReadyEventM;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.WaterComponent;
import plugin.siren.Systems.WaterSystem;
import plugin.siren.Utils.Config.MermaidsConfig;

import javax.annotation.Nonnull;

public class Mermaids extends JavaPlugin {
    private static final String VERSION = "1.2.2";
    private static final boolean DEBUG = false;

    private static Mermaids plugin;
    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private final Config<MermaidsConfig> config;

    private ComponentType<EntityStore, WaterComponent> waterComponent;
    private ComponentType<EntityStore, MermaidComponent> mermaidComponent;

    public Mermaids(@Nonnull JavaPluginInit init){
        super(init);

        plugin = this;
        this.config = this.withConfig("Mermaids", MermaidsConfig.CODEC);
    }

    @Override
    protected void setup(){
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, PlayerReadyEventM::onPlayerReadyEvent);
        this.getCommandRegistry().registerCommand(new MermaidsUI());
        this.getCommandRegistry().registerCommand(new ToggleMermaid());

        this.waterComponent = this.getEntityStoreRegistry().registerComponent(WaterComponent.class, WaterComponent::new);
        this.mermaidComponent = this.getEntityStoreRegistry().registerComponent(MermaidComponent.class, MermaidComponent::new);
        this.getEntityStoreRegistry().registerSystem(new WaterSystem(this.waterComponent, this.mermaidComponent));

        config.load();
        config.save();

        LOGGER.atInfo().log("Version " + VERSION + " of Mermaids has loaded.");

        if(ifDebug()){
            LOGGER.atInfo().log("Loaded in Debug mode.");
        }
    }

    public ComponentType<EntityStore, WaterComponent> getWaterComponentType(){
        return waterComponent;
    }

    public ComponentType<EntityStore, MermaidComponent> getMermaidComponentType(){
        return mermaidComponent;
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
