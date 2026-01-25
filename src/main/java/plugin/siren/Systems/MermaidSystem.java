package plugin.siren.Systems;

import com.hypixel.hytale.builtin.weather.components.WeatherTracker;
import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.math.util.MathUtil;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.ComponentUpdate;
import com.hypixel.hytale.protocol.ComponentUpdateType;
import com.hypixel.hytale.protocol.EntityUpdate;
import com.hypixel.hytale.protocol.Equipment;
import com.hypixel.hytale.protocol.ItemArmorSlot;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.protocol.packets.entities.EntityUpdates;
import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
import com.hypixel.hytale.server.core.inventory.Inventory;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import plugin.siren.Contributions.starman.modelutils.ModelHelper;
import plugin.siren.Mermaids;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class MermaidSystem extends EntityTickingSystem<EntityStore> {
    private final ComponentType<EntityStore, MermaidComponent> mermaidComponentType;

    public MermaidSystem(ComponentType<EntityStore, MermaidComponent> mermaidComponentType){
        this.mermaidComponentType = mermaidComponentType;
    }

    @Override
    public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
                     @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer){

        MermaidComponent mermaid = archetypeChunk.getComponent(index, mermaidComponentType);

        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);

        if(ref == null){
            Mermaids.LOGGER.atSevere().log("Failed to get reference : MermaidSystem");
        }else {
            Player player = commandBuffer.getComponent(ref, Player.getComponentType());
            PlayerRef playerRef = commandBuffer.getComponent(ref, PlayerRef.getComponentType());

            //Check to see if Raining or on a transformation block
            if (Mermaids.getConfig().get().getBlockTransformation() || Mermaids.getConfig().get().getRainTransformation()) {
                World world = player.getWorld();
                if (world.getName().equals("default")) {
                    world.execute(() -> {
                        TransformComponent transform = commandBuffer.getComponent(ref, TransformComponent.getComponentType());
                        if (transform != null) {
                            Vector3d pos = transform.getPosition();

                            if (Mermaids.getConfig().get().getBlockTransformation()) {
                                BlockType footBlockType = world.getBlockType((int) Math.floor(pos.getX()), (int) Math.floor(pos.getY()), (int) Math.floor(pos.getZ()));
                                String footBlockId = footBlockType.getId();

                                Boolean h2o = false;
                                if (footBlockId.equals("Alchemy_Cauldron_Big")) {//footBlockId == 24){//Large Cauldron
                                    mermaid.setH2OBlock(true);
                                    h2o = true;
                                }
                                if (footBlockId.equals("Soil_Mud")) {//footBlockId == 563 || footBlockId == 564){//Soil Mud
                                    mermaid.setH2OBlock(true);
                                    h2o = true;
                                }
                                if (!h2o && mermaid.getH2OBlock().get()) {
                                    mermaid.setH2OBlock(false);
                                }
                            }

                            if (Mermaids.getConfig().get().getRainTransformation()) {
                                WorldChunk worldChunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(pos.getX(), pos.getZ()));
                                int yHeight = worldChunk.getHeight(MathUtil.floor(pos.getX()), MathUtil.floor(pos.getZ()));

                                boolean raining = false;

                                if (pos.getY() >= yHeight) {
                                    WeatherResource weatherResource = commandBuffer.getResource(WeatherResource.getResourceType());
                                    Weather weatherForcedAsset = Weather.getAssetMap().getAsset(weatherResource.getForcedWeatherIndex());

                                    WeatherTracker weatherTracker = commandBuffer.getComponent(ref, WeatherTracker.getComponentType());
                                    Weather weatherNaturalAsset = Weather.getAssetMap().getAsset(weatherResource.getWeatherIndexForEnvironment(weatherTracker.getEnvironmentId()));

                                    String weatherForcedId = weatherForcedAsset.getId();
                                    String weatherNaturalId = weatherNaturalAsset.getId();
                                    if (weatherNaturalId.equalsIgnoreCase("zone1_rain") || weatherNaturalId.equalsIgnoreCase("zone1_swamp_rain") || //Zone 1
                                            weatherNaturalId.equalsIgnoreCase("zone1_rain_light") || weatherNaturalId.equalsIgnoreCase("zone1_storm") ||
                                            weatherNaturalId.equalsIgnoreCase("zone2_thunder_storm") || //Zone2
                                            weatherNaturalId.equalsIgnoreCase("zone3_rain") || //Zone3
                                            weatherNaturalId.equalsIgnoreCase("zone4_wastes_rain") || weatherNaturalId.equalsIgnoreCase("zone4_wastes_rain_heavy") || //Zone4
                                            weatherNaturalId.equalsIgnoreCase("skylands_rapid_marsh_stormy")) /* Misc */ {
                                        mermaid.setRainTransform(true);
                                        raining = true;
                                    }
                                    if (weatherForcedId.equalsIgnoreCase("zone1_rain") || weatherForcedId.equalsIgnoreCase("zone1_swamp_rain") || //Zone 1
                                            weatherForcedId.equalsIgnoreCase("zone1_rain_light") || weatherForcedId.equalsIgnoreCase("zone1_storm") ||
                                            weatherForcedId.equalsIgnoreCase("zone2_thunder_storm") || //Zone2
                                            weatherForcedId.equalsIgnoreCase("zone3_rain") || //Zone3
                                            weatherForcedId.equalsIgnoreCase("zone4_wastes_rain") || weatherForcedId.equalsIgnoreCase("zone4_wastes_rain_heavy") || //Zone4
                                            weatherForcedId.equalsIgnoreCase("skylands_rapid_marsh_stormy")) /* Misc */ {
                                        mermaid.setRainTransform(true);
                                        raining = true;
                                    }

                                    if (weatherNaturalId.equalsIgnoreCase("zone3_snow") || weatherNaturalId.equalsIgnoreCase("zone3_snow_storm") ||//Zone3
                                            weatherNaturalId.equalsIgnoreCase("zone3_snow_heavy")) {
                                        mermaid.setRainTransform(true);
                                        raining = true;
                                    }
                                    if (weatherForcedId.equalsIgnoreCase("zone3_snow") || weatherForcedId.equalsIgnoreCase("zone3_snow_storm") ||//Zone3
                                            weatherForcedId.equalsIgnoreCase("zone3_snow_heavy")) {
                                        mermaid.setRainTransform(true);
                                        raining = true;
                                    }

                                    //player.sendMessage(Message.raw(weatherId));
                                }

                                if (!raining && mermaid.getRainTransform().get()) {
                                    mermaid.setRainTransform(false);
                                }
                            }
                        }
                    });
                }
            }

            boolean transformPermission = PermissionsModule.get().hasPermission(playerRef.getUuid(), "mermaids.transform") || !Mermaids.getConfig().get().getRequireTransformationPermission();

            MovementStatesComponent movementState = commandBuffer.getComponent(ref, MovementStatesComponent.getComponentType());
            //Checks to see if in water / other transformation methods
            if ((movementState.getMovementStates().swimming || movementState.getMovementStates().swimJumping || movementState.getMovementStates().inFluid ||
                    mermaid.getH2OBlock().get() || mermaid.getRainTransform().get()) && mermaid.getToggleMermaid() && transformPermission) {
                if (!mermaid.isUnderwater()) {
                    mermaid.setUnderwater(true);
                    mermaid.setElapsedTime(0f);

                    if (Mermaids.ifDebug()) {
                        player.sendMessage(Message.raw("You have entered a liquid."));
                    }
                }

                if (mermaid.getElapsedTime() < 500f) {
                    mermaid.incrementTick();
                }

                //Began transformation into Mermaid
                if (!mermaid.isMermaid() && mermaid.getElapsedTime() >= 35f) {
                    if (Mermaids.ifDebug()) {
                        player.sendMessage(Message.raw("Now Swimming (35 ticks)"));
                    }

                    if (mermaid != null) {
                        PlayerSkinComponent skinComp = commandBuffer.getComponent(ref, PlayerSkinComponent.getComponentType());
                        if (skinComp != null) {
                            PlayerSkin skin = skinComp.getPlayerSkin().clone();
                            mermaid.setPlayerSkin(skin);
                            mermaid.setPlayerSkinComponent((PlayerSkinComponent) skinComp.clone());

                            if (Mermaids.ifDebug()) {
                                player.sendMessage(Message.raw("Set the PlayerSkinComponent"));
                            }
                        } else {
                            player.sendMessage(Message.raw("Mermaids: Error: MermaidSystem: skincomp == null"));
                            Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " Failed to get Player Skin Component. Error: MermaidSystem: skincomp == null");
                        }

                        PlayerSkin skin = mermaid.getMermaidSkin();

                        if (Mermaids.ifDebug()) {
                            player.sendMessage(Message.raw("Replacing Skin"));
                        }

                        String mermaidTailModel = mermaid.getMermaidTail();

                        ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(mermaidTailModel);
                        if (modelAsset == null) {
                            player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: " + mermaidTailModel + " Model not found"));
                            Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: WaterSystem: " + mermaidTailModel + " Model not found.");
                        } else {
                            ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), skin.clone(), ref, commandBuffer, player, mermaid);
                        }
                    } else {
                        player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: Mermaid Component == null [fluid==7]"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Component. Error: WaterSystem: Mermaid Component == null [movementStates]");
                    }

                    if (Mermaids.ifDebug()) {
                        player.sendMessage(Message.raw("Updating oxygen stats for mermaid transformation"));
                    }

                    HudManager playerHud = player.getHudManager();
                    playerHud.hideHudComponents(playerRef, HudComponent.Oxygen);

                    mermaid.setMermaid(true);
                    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has transformed into a Mermaid.");
                }
            } else if (mermaid.isMermaid() || mermaid.isUnderwater()) {
                mermaid.setUnderwater(false);
                mermaid.setMermaid(false);
                mermaid.setElapsedTime(0f);
                mermaid.setDrying(true);
            } else if (mermaid.isDrying()) {
                mermaid.incrementTick();

                //Began transformation back into Human
                if (mermaid.getElapsedTime() >= 15f) {
                    mermaid.setDrying(false);
                    mermaid.setElapsedTime(0f);

                    if (Mermaids.ifDebug()) {
                        player.sendMessage(Message.raw("Updating stats for player transformation"));
                    }

                    commandBuffer.replaceComponent(ref, MovementManager.getComponentType(), (MovementManager) mermaid.getMovementManager().clone());

                    HudManager playerHud = player.getHudManager();
                    playerHud.showHudComponents(playerRef, HudComponent.Oxygen);

                    if (Mermaids.ifDebug()) {
                        player.sendMessage(Message.raw("Now Drying (15 ticks)"));
                    }

                    if (mermaid != null) {
                        if (Mermaids.ifDebug()) {
                            player.sendMessage(Message.raw("Setting Original Skin"));
                        }

                        ModelComponent modelComponent = (ModelComponent) mermaid.getModelComponent().clone();
                        commandBuffer.replaceComponent(ref, ModelComponent.getComponentType(), modelComponent);

                        PlayerSkinComponent skinComponent = (PlayerSkinComponent) mermaid.getPlayerSkinComponent().clone();
                        commandBuffer.replaceComponent(ref, PlayerSkinComponent.getComponentType(), skinComponent);

                        Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has transformed back into a Human.");
                    } else {
                        player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: Mermaid Component == null [water.isDrying]"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Component. Error: WaterSystem: Mermaid Component == null [water.isDrying]");
                    }

                    EntityTrackerSystems.EntityViewer entityViewer = commandBuffer.getComponent(ref, EntityTrackerSystems.EntityViewer.getComponentType());
                    if (entityViewer == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: EntityViewer Component == null [transformation stats]"));
                    } else {
                        NetworkId networkId = commandBuffer.getComponent(ref, NetworkId.getComponentType());
                        if (networkId == null) {
                            player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: NetworkID Component == null [transformation stats]"));
                        } else {
                            EntityUpdate entityUpdate = new EntityUpdate();
                            entityUpdate.networkId = networkId.getId();

                            ObjectArrayList<ComponentUpdate> updateList = new ObjectArrayList<>();

                            Inventory inventory = player.getInventory();
                            ComponentUpdate update = new ComponentUpdate();

                            update.type = ComponentUpdateType.Equipment;
                            update.equipment = new Equipment();

                            ItemContainer armor = inventory.getArmor();
                            update.equipment.armorIds = new String[armor.getCapacity()];
                            Arrays.fill(update.equipment.armorIds, "");
                            armor.forEachWithMeta((slot, itemStack, armorIds) -> armorIds[slot] = itemStack.getItemId(), update.equipment.armorIds);

                        /*PlayerSettings playerSettings = commandBuffer.getComponent(ref, PlayerSettings.getComponentType());
                        if (playerSettings != null) {
                            PlayerConfig.ArmorVisibilityOption armorVisibilityOption = commandBuffer.getExternalData()//store.getExternalData()
                                    .getWorld()
                                    .getGameplayConfig()
                                    .getPlayerConfig()
                                    .getArmorVisibilityOption();
                            if (playerSettings.hideHelmet()) {
                                update.equipment.armorIds[ItemArmorSlot.Head.ordinal()] = "";
                            }

                            if (armorVisibilityOption.canHideCuirass() && playerSettings.hideCuirass()) {
                                update.equipment.armorIds[ItemArmorSlot.Chest.ordinal()] = "";
                            }

                            if (armorVisibilityOption.canHideGauntlets() && playerSettings.hideGauntlets()) {
                                update.equipment.armorIds[ItemArmorSlot.Hands.ordinal()] = "";
                            }

                            if (armorVisibilityOption.canHidePants() && playerSettings.hidePants()) {
                                update.equipment.armorIds[ItemArmorSlot.Legs.ordinal()] = "";
                            }
                        }*/

                            ItemStack itemInHand = inventory.getItemInHand();
                            update.equipment.rightHandItemId = itemInHand != null ? itemInHand.getItemId() : "Empty";
                            ItemStack utilityItem = inventory.getUtilityItem();
                            update.equipment.leftHandItemId = utilityItem != null ? utilityItem.getItemId() : "Empty";

                            updateList.add(update);

                            entityUpdate.updates = updateList.toArray(ComponentUpdate[]::new);
                            entityViewer.packetReceiver.writeNoCache(new EntityUpdates(null, new EntityUpdate[]{entityUpdate}));
                        }
                    }

                    mermaid.setMermaid(false);
                }
            }

            if (mermaid.isMermaid() && mermaid.getToggleMermaid() && transformPermission) {
                MovementManager movement = commandBuffer.getComponent(ref, MovementManager.getComponentType());
                if (movementState.getMovementStates().swimming || movementState.getMovementStates().swimJumping || movementState.getMovementStates().inFluid) {
                    movement.getSettings().swimJumpForce = 14.5f;
                    movement.getSettings().baseSpeed = 11.5f;
                    movement.getSettings().forwardCrouchSpeedMultiplier = 1f;
                    movement.getSettings().backwardCrouchSpeedMultiplier = 0.8f;
                    movement.getSettings().forwardSprintSpeedMultiplier = 1.85f;
                    movement.update(playerRef.getPacketHandler());
                } else {
                    movement.getSettings().jumpForce = 8f;
                    movement.getSettings().baseSpeed = 3f;
                    movement.getSettings().forwardCrouchSpeedMultiplier = 0.35f;
                    movement.getSettings().backwardCrouchSpeedMultiplier = 0.25f;
                    movement.getSettings().forwardSprintSpeedMultiplier = 1.35f;
                    movement.update(playerRef.getPacketHandler());
                }

                EntityStatMap statMapComponent = commandBuffer.getComponent(ref, EntityStatMap.getComponentType());
                //statMapComponent.setStatValue(DefaultEntityStatTypes.getOxygen(), 100f);
                statMapComponent.maximizeStatValue(DefaultEntityStatTypes.getOxygen());

                EntityTrackerSystems.EntityViewer entityViewer = commandBuffer.getComponent(ref, EntityTrackerSystems.EntityViewer.getComponentType());
                if (entityViewer == null) {
                    player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: EntityViewer Component == null [transformation stats]"));
                } else {
                    NetworkId networkId = commandBuffer.getComponent(ref, NetworkId.getComponentType());
                    if (networkId == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: NetworkID Component == null [transformation stats]"));
                    } else {
                        EntityUpdate entityUpdate = new EntityUpdate();
                        entityUpdate.networkId = networkId.getId();

                        ObjectArrayList<ComponentUpdate> updateList = new ObjectArrayList<>();

                        Inventory inventory = player.getInventory();
                        ComponentUpdate update = new ComponentUpdate();

                        update.type = ComponentUpdateType.Equipment;
                        update.equipment = new Equipment();

                        ItemContainer armor = inventory.getArmor();
                        update.equipment.armorIds = new String[armor.getCapacity()];
                        Arrays.fill(update.equipment.armorIds, "");
                        armor.forEachWithMeta((slot, itemStack, armorIds) -> armorIds[slot] = itemStack.getItemId(), update.equipment.armorIds);

                        update.equipment.armorIds[ItemArmorSlot.Legs.ordinal()] = "";

                        ItemStack itemInHand = inventory.getItemInHand();
                        update.equipment.rightHandItemId = itemInHand != null ? itemInHand.getItemId() : "Empty";
                        ItemStack utilityItem = inventory.getUtilityItem();
                        update.equipment.leftHandItemId = utilityItem != null ? utilityItem.getItemId() : "Empty";

                        updateList.add(update);

                        entityUpdate.updates = updateList.toArray(ComponentUpdate[]::new);
                        entityViewer.packetReceiver.writeNoCache(new EntityUpdates(null, new EntityUpdate[]{entityUpdate}));
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery(){
        return Query.and(this.mermaidComponentType);
    }
}
