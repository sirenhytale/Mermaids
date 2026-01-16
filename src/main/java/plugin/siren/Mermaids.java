package plugin.siren;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Commands.CheckWaterComponent;
import plugin.siren.Events.PlayerReadyEventM;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.WaterComponent;
import plugin.siren.Systems.WaterSystem;

import javax.annotation.Nonnull;

public class Mermaids extends JavaPlugin {
    private static Mermaids plugin;
    private ComponentType<EntityStore, WaterComponent> waterComponent;
    private ComponentType<EntityStore, MermaidComponent> mermaidComponent;

    public Mermaids(@Nonnull JavaPluginInit init){
        super(init);
        plugin = this;
    }

    @Override
    protected void setup(){
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, PlayerReadyEventM::onPlayerReadyEvent);
        //this.getCommandRegistry().registerCommand(new CheckWaterComponent());

        this.waterComponent = this.getEntityStoreRegistry().registerComponent(WaterComponent.class, WaterComponent::new);
        this.mermaidComponent = this.getEntityStoreRegistry().registerComponent(MermaidComponent.class, MermaidComponent::new);
        this.getEntityStoreRegistry().registerSystem(new WaterSystem(this.waterComponent, this.mermaidComponent));

        System.out.println("Mermaids Plugin Version 0.1.0 has started!");
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
}
