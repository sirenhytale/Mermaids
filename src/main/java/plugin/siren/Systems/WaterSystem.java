package plugin.siren.Systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.Animation;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.entity.entities.BlockEntity;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.teleport.TeleportSystems;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.worldgen.container.WaterContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WaterSystem extends EntityTickingSystem<EntityStore> {
    private final ComponentType<EntityStore, WaterComponent> waterComponentType;

    public WaterSystem(ComponentType<EntityStore, WaterComponent> waterComponentType){
        this.waterComponentType = waterComponentType;
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
            player.sendMessage(Message.raw("Underwater"));

            if(!water.isUnderwater()) {
                water.setUnderwater(true);
            }

            //MAKES SURE ELAPSED TIME WON'T GO TO INFINITE AND ONLY TO WHAT WILL BE NEEDED WHICH IS 100f FOR NOW
            if(water.getElapsedTime() < 1000f) {
                water.incrementTick();
            }

            if(!water.isSwimming() && water.getElapsedTime() > 100f){
                water.setSwimming(true);
                player.sendMessage(Message.raw("Now Swimming (100 ticks)"));
            }
        }else if(water.isSwimming() || water.isUnderwater()){
            water.setUnderwater(false);
            water.setSwimming(false);
            water.setElapsedTime(0f);
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
        return Query.and(this.waterComponentType);
    }
}
