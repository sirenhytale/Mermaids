package plugin.siren.Systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.Animation;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAttachment;
import com.hypixel.hytale.server.core.entity.entities.BlockEntity;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
import com.hypixel.hytale.server.core.modules.entity.teleport.TeleportSystems;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.worldgen.container.WaterContainer;
import plugin.siren.Mermaids;
import plugin.siren.other.ModelHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

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

        if(fluidId == 7){
            //player.sendMessage(Message.raw("Underwater"));

            if(!water.isUnderwater()) {
                water.setUnderwater(true);
                player.sendMessage(Message.raw("You have entered the water"));
            }

            //MAKES SURE ELAPSED TIME WON'T GO TO INFINITE AND ONLY TO WHAT WILL BE NEEDED WHICH IS 100f FOR NOW
            if(water.getElapsedTime() < 1000f) {
                water.incrementTick();
            }

            if(!water.isSwimming() && water.getElapsedTime() > 100f){
                water.setSwimming(true);
                player.sendMessage(Message.raw("Now Swimming (100 ticks)"));

                //ModelComponent modelComp = store.getComponent(ref, ModelComponent.getComponentType());

                //assert modelComp != null;
                //Model modelAsset = modelComp.getModel();

                //player.sendMessage(Message.raw(modelAsset.getModel()));


                //PlayerSkinComponent skinComp = store.getComponent(ref, PlayerSkinComponent.getComponentType());

                MermaidComponent mermaid = archetypeChunk.getComponent(index, mermaidComponentType);
                if(mermaid != null) {
                    PlayerSkin skin = mermaid.getCosmetics();
                    PlayerSkinComponent skinComp = new PlayerSkinComponent(skin);

                    //store.replaceComponent(ref, PlayerSkinComponent.getComponentType(), skinComp);
                    //storeRef.replaceComponent(ref, PlayerSkinComponent.getComponentType(), skinComp);

                    player.sendMessage(Message.raw("Replacing Skin"));
                    //commandBuffer.replaceComponent(ref, PlayerSkinComponent.getComponentType(), skinComp);
                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset("Mermaid");
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Error: Model not found"));
                    }else{
                        player.sendMessage(Message.raw("Updating model"));
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), skin, ref, commandBuffer);
                    }

                    List<String> cosList = mermaid.getCosmeticList();
                    for (String cosStr : cosList) {
                        player.sendMessage(Message.raw(cosStr));
                    }
                }else{
                    player.sendMessage(Message.raw("mermaid skincomp replace == null (fluid==7)"));
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

                player.sendMessage(Message.raw("Now Drying (100 ticks)"));

                MermaidComponent mermaid = archetypeChunk.getComponent(index, mermaidComponentType);
                if(mermaid != null) {
                    player.sendMessage(Message.raw("Setting Original Skin"));
                    PlayerSkinComponent skinComp = mermaid.getOriginalSkin();

                    commandBuffer.replaceComponent(ref, PlayerSkinComponent.getComponentType(), skinComp);
                }else{
                    player.sendMessage(Message.raw("mermaid skincomp replace == null (water dring)"));
                }
            }
        }
        //int block = world.getBlock((int)Math.floor(pos.getX()), (int)Math.floor(pos.getY()), (int)Math.floor(pos.getZ()));
        //BlockType blockType = world.getBlockType((int)Math.floor(pos.getX()), (int)Math.floor(pos.getY()), (int)Math.floor(pos.getZ()));
        //player.sendMessage(Message.raw(String.valueOf(world.getFluidId((int)Math.floor(pos.getX()), (int)Math.floor(pos.getY()), (int)Math.floor(pos.getZ())))));


        /*//System.out.println("Running null check");
        player.sendMessage(Message.raw("Running null check"));
        //Universe.get().sendMessage(Message.raw("Running null check"));
        assert blockType != null;
        //System.out.println("Running if");
        player.sendMessage(Message.raw("Running if"));
        //Universe.get().sendMessage(Message.raw("Running if"));
        player.sendMessage(Message.raw(blockType.getId()));
        if(blockType.getId().equals("Fluid")){
            //Universe.get().sendMessage(Message.raw("WATER"));
            player.sendMessage(Message.raw("worked water"));
            //System.out.println("worked water");
        }*/
    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery(){
        return Query.and(this.waterComponentType,this.mermaidComponentType);
    }
}
