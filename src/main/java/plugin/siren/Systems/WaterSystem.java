package plugin.siren.Systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.cosmetics.CosmeticsModule;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
import com.hypixel.hytale.server.core.modules.entity.component.ActiveAnimationComponent;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
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
        MermaidComponent mermaid = archetypeChunk.getComponent(index, mermaidComponentType);

        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);

        Player player = commandBuffer.getComponent(ref, Player.getComponentType());
        PlayerRef playerRef = commandBuffer.getComponent(ref, PlayerRef.getComponentType());

        boolean transformPermission = PermissionsModule.get().hasPermission(playerRef.getUuid(), "mermaids.transform") || !Mermaids.getConfig().get().getRequireTransformationPermission();

        MovementStatesComponent movementState = commandBuffer.getComponent(ref, MovementStatesComponent.getComponentType());
        if((movementState.getMovementStates().swimming || movementState.getMovementStates().swimJumping || movementState.getMovementStates().inFluid) && mermaid.getToggleMermaid() && transformPermission){
            if(!water.isUnderwater()) {
                water.setUnderwater(true);
                water.setElapsedTime(0f);

                if(Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("You have entered the water"));
                }
            }

            if(water.getElapsedTime() < 500f) {
                water.incrementTick();
            }

            if(!water.isSwimming() && water.getElapsedTime() >= 35f){
                water.setSwimming(true);

                if(Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("Now Swimming (35 ticks)"));
                }

                if(mermaid != null) {
                    PlayerSkin skin = mermaid.getMermaidSkin();

                    if(Mermaids.ifDebug()) {
                        player.sendMessage(Message.raw("Replacing Skin"));
                    }

                    String mermaidTailModel = mermaid.getMermaidTail();

                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(mermaidTailModel);
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: " + mermaidTailModel + " Model not found"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: WaterSystem: " + mermaidTailModel + " Model not found.");
                    }else{
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), skin.clone(), ref, commandBuffer, mermaid);
                    }
                }else{
                    player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: Mermaid Component == null [fluid==7]"));
                    Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Component. Error: WaterSystem: Mermaid Component == null [movementStates]");
                }

                if(Mermaids.ifDebug()){
                    player.sendMessage(Message.raw("Updating oxygen stats for mermaid transformation"));
                }

                HudManager playerHud = player.getHudManager();
                playerHud.hideHudComponents(playerRef, HudComponent.Oxygen);

                mermaid.setMermaid(true);
                Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has transformed into a Mermaid.");
            }
        }else if(water.isSwimming() || water.isUnderwater()){
            water.setUnderwater(false);
            water.setSwimming(false);
            water.setElapsedTime(0f);
            water.setDrying(true);
        }else if(water.isDrying()){
            water.incrementTick();

            if(water.getElapsedTime() >= 15f){
                water.setDrying(false);
                water.setElapsedTime(0f);

                if(Mermaids.ifDebug()){
                    player.sendMessage(Message.raw("Updating stats for player transformation"));
                }

                commandBuffer.replaceComponent(ref, MovementManager.getComponentType(), (MovementManager) mermaid.getMovementManager().clone());

                HudManager playerHud = player.getHudManager();
                playerHud.showHudComponents(playerRef, HudComponent.Oxygen);

                if(Mermaids.ifDebug()) {
                    player.sendMessage(Message.raw("Now Drying (15 ticks)"));
                }

                if(mermaid != null) {
                    if(Mermaids.ifDebug()) {
                        player.sendMessage(Message.raw("Setting Original Skin"));
                    }

                    ModelComponent modelComponent = (ModelComponent) mermaid.getModelComponent().clone();
                    commandBuffer.replaceComponent(ref, ModelComponent.getComponentType(), modelComponent);

                    PlayerSkinComponent skinComponent = (PlayerSkinComponent) mermaid.getPlayerSkinComponent().clone();
                    commandBuffer.replaceComponent(ref, PlayerSkinComponent.getComponentType(), skinComponent);

                    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has transformed back into a Human.");
                }else{
                    player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: Mermaid Component == null [water.isDrying]"));
                    Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Component. Error: WaterSystem: Mermaid Component == null [water.isDrying]");
                }

                mermaid.setMermaid(false);
            }
        }

        if(water.isSwimming() && mermaid.getToggleMermaid() && transformPermission){
            MovementManager movement = commandBuffer.getComponent(ref, MovementManager.getComponentType());
            movement.getSettings().baseSpeed = 11.5f;
            movement.getSettings().forwardCrouchSpeedMultiplier = 1f;
            movement.getSettings().backwardCrouchSpeedMultiplier = 0.8f;
            movement.getSettings().forwardSprintSpeedMultiplier = 1.85f;
            movement.update(playerRef.getPacketHandler());


            EntityStatMap statMapComponent = commandBuffer.getComponent(ref, EntityStatMap.getComponentType());
            statMapComponent.setStatValue(DefaultEntityStatTypes.getOxygen(), 100f);
        }

        ActiveAnimationComponent actAnimation = commandBuffer.getComponent(ref, ActiveAnimationComponent.getComponentType());
        if(actAnimation != null)
            player.sendMessage(Message.raw(actAnimation.getActiveAnimations()[0]));
    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery(){
        return Query.and(this.waterComponentType,this.mermaidComponentType);
    }
}
