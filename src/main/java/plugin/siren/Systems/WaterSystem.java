package plugin.siren.Systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Contributions.starman.modelutils.ModelHelper;
import plugin.siren.Mermaids;

import javax.annotation.Nonnull;

public class WaterSystem extends EntityTickingSystem<EntityStore> {
    private final ComponentType<EntityStore, WaterComponent> waterComponentType;
    private final ComponentType<EntityStore, MermaidComponent> mermaidComponentType;

    public WaterSystem(ComponentType<EntityStore, WaterComponent> waterComponentType, ComponentType<EntityStore, MermaidComponent> mermaidComponentType){
        this.waterComponentType = waterComponentType;
        this.mermaidComponentType = mermaidComponentType;
    }

    @Override
    public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
                     @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer){

        WaterComponent water = archetypeChunk.getComponent(index, waterComponentType);
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);

        TransformComponent transform = store.getComponent(ref, TransformComponent.getComponentType());
        Vector3d pos = transform.getPosition();

        Store<EntityStore> storeRef = ref.getStore();
        Player player = storeRef.getComponent(ref, Player.getComponentType());

        World world = player.getWorld();
        int fluidId = world.getFluidId((int)Math.floor(pos.getX()), (int)Math.floor(pos.getY()), (int)Math.floor(pos.getZ()));

        if(fluidId == 7) {
            if(!water.isUnderwater()) {
                water.setUnderwater(true);
                water.setElapsedTime(0f);

                if(Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("You have entered the water"));
                }
            }

            //MAKES SURE ELAPSED TIME WON'T GO TO INFINITE AND ONLY TO WHAT WILL BE NEEDED WHICH IS 100f FOR NOW
            if(water.getElapsedTime() < 1000f) {
                water.incrementTick();
            }

            if(!water.isSwimming() && water.getElapsedTime() > 100f){
                water.setSwimming(true);

                if(Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("Now Swimming (100 ticks)"));
                }

                MermaidComponent mermaid = archetypeChunk.getComponent(index, mermaidComponentType);
                if(mermaid != null) {
                    PlayerSkin skin = mermaid.getMermaidSkin();

                    if(Mermaids.ifDebug()) {
                        player.sendMessage(Message.raw("Replacing Skin"));
                    }

                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset("MermaidPlayer");
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: MermaidPlayer Model not found"));
                    }else{
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), skin, ref, commandBuffer);
                    }
                }else{
                    player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: Mermaid Component == null [fluid==7]"));
                }
            }
        }else if(water.isSwimming() || water.isUnderwater()){
            water.setUnderwater(false);
            water.setSwimming(false);
            water.setElapsedTime(0f);
            water.setDrying(true);
        }else if(water.isDrying()){
            water.incrementTick();

            if(water.getElapsedTime() > 100f){
                water.setDrying(false);
                water.setElapsedTime(0f);

                if(Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("Now Drying (100 ticks)"));
                }

                MermaidComponent mermaid = archetypeChunk.getComponent(index, mermaidComponentType);
                if(mermaid != null) {
                    if(Mermaids.ifDebug()) {
                        player.sendMessage(Message.raw("Setting Original Skin"));
                    }

                    PlayerSkin skin = mermaid.getPlayerSkin();

                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset("Player");
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: Player Model not found"));
                    }else{
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), skin, ref, commandBuffer);
                    }
                }else{
                    player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: Mermaid Component == null [water.isDrying]"));
                }
            }
        }
    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery(){
        return Query.and(this.waterComponentType,this.mermaidComponentType);
    }
}
