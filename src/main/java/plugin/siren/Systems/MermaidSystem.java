package plugin.siren.Systems;

import com.hypixel.hytale.builtin.weather.components.WeatherTracker;
import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.math.util.MathUtil;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.*;
import com.hypixel.hytale.protocol.packets.entities.EntityUpdates;
import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.asset.type.gameplay.PlayerConfig;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.asset.type.weather.config.Weather;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
import com.hypixel.hytale.server.core.inventory.Inventory;
//import com.hypixel.hytale.server.core.inventory.InventoryComponent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSettings;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.plugin.PluginManager;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.hardaway.wardrobe.impl.player.PlayerWardrobeComponent;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import plugin.siren.Compatibility.AquaThirstHunger.AquaThirstHungerCompact;
import plugin.siren.Compatibility.EasyHunger.EasyHungerCompat;
import plugin.siren.Compatibility.PlaceholderAPI.PlaceholderAPICompat;
import plugin.siren.Contributions.starman.modelutils.ModelHelper;
import plugin.siren.Mermaids;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class MermaidSystem extends EntityTickingSystem<EntityStore> {
    private final ComponentType<EntityStore, MermaidComponent> mermaidComponentType;
    private final ComponentType<EntityStore, MermaidSettings> mermaidSettingsComponentType;

    public MermaidSystem(ComponentType<EntityStore, MermaidComponent> mermaidComponentType, ComponentType<EntityStore, MermaidSettings> mermaidSettingsComponentType){
        this.mermaidComponentType = mermaidComponentType;
        this.mermaidSettingsComponentType = mermaidSettingsComponentType;
    }

    @Override
    public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk,
                     @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer){
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);

        if(ref == null || !ref.isValid()){
            Mermaids.LOGGER.atFine().log("Failed to get reference : MermaidSystem");
        }else {
            MermaidComponent mermaid = archetypeChunk.getComponent(index, mermaidComponentType);
            MermaidSettings mermaidSettings = archetypeChunk.getComponent(index, mermaidSettingsComponentType);

            Player player = commandBuffer.getComponent(ref, Player.getComponentType());
            PlayerRef playerRef = commandBuffer.getComponent(ref, PlayerRef.getComponentType());

            //Check to see if Raining or on a transformation block
            if (Mermaids.getConfig().get().getBlockTransformation() || Mermaids.getConfig().get().getRainTransformation()) {
                World world = player.getWorld();
                if (world != null){//world.getName().equals("default")) {
                    world.execute(() -> {
                        if(ref == null || !ref.isValid()){
                            Mermaids.LOGGER.atFine().log("Failed to get reference : MermaidSystem - World execution");
                        }else {
                            TransformComponent transform = commandBuffer.getComponent(ref, TransformComponent.getComponentType());
                            if (transform != null) {
                                Vector3d pos = transform.getPosition();

                                if (Mermaids.getConfig().get().getBlockTransformation()) {
                                    /*Ref<ChunkStore> chunkRef = world.getChunkStore().getChunkReference(ChunkUtil.indexChunkFromBlock(pos.getX(),pos.getZ()));

                                    Store<ChunkStore> chunkStore = chunkRef.getStore();

                                    BlockComponentChunk blocksComponent = chunkStore.getComponent(chunkRef, BlockComponentChunk.getComponentType());
                                    if(blocksComponent != null){
                                        int blockIndex = ChunkUtil.indexBlockInColumn((int) Math.floor(pos.getX()), (int) Math.floor(pos.getY()), (int) Math.floor(pos.getZ()));

                                        Ref<ChunkStore> blockRef = blocksComponent.getEntityReference(blockIndex);
                                        if(blockRef != null){

                                        }
                                    }*/

                                    BlockType footBlockType = world.getBlockType((int) Math.floor(pos.getX()), (int) Math.floor(pos.getY()), (int) Math.floor(pos.getZ()));
                                    String footBlockId = footBlockType.getId();

                                    Boolean h2o = false;
                                    if (footBlockId.equals("Alchemy_Cauldron_Big")) {//footBlockId == 24){//Large Cauldron
                                        if(!mermaid.getH2OBlock().get()) {
                                            mermaid.setH2OBlock(true);
                                        }

                                        h2o = true;
                                    }
                                    if (footBlockId.equals("Alchemy_Cauldron")) {//footBlockId == 24){//Large Cauldron
                                        if(!mermaid.getH2OBlock().get()) {
                                            mermaid.setH2OBlock(true);
                                        }

                                        h2o = true;
                                    }
                                    if (footBlockId.equals("Soil_Mud")) {//footBlockId == 563 || footBlockId == 564){//Soil Mud
                                        if(!mermaid.getH2OBlock().get()) {
                                            mermaid.setH2OBlock(true);
                                        }

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
                                        if(weatherResource == null){
                                            Mermaids.LOGGER.atFine().log("Failed to get WeatherResource : MermaidSystem - World execution");
                                        }else {
                                            Weather weatherForcedAsset = Weather.getAssetMap().getAsset(weatherResource.getForcedWeatherIndex());

                                            WeatherTracker weatherTracker = commandBuffer.getComponent(ref, WeatherTracker.getComponentType());
                                            if(weatherTracker == null){
                                                Mermaids.LOGGER.atFine().log("Failed to get weatherTracker : MermaidSystem - World execution");
                                            }else {
                                                Weather weatherNaturalAsset = Weather.getAssetMap().getAsset(weatherResource.getWeatherIndexForEnvironment(weatherTracker.getEnvironmentId()));

                                                if( weatherNaturalAsset != null){
                                                    String weatherNaturalId = weatherNaturalAsset.getId();

                                                    if (weatherNaturalId.equalsIgnoreCase("zone1_rain") || weatherNaturalId.equalsIgnoreCase("zone1_swamp_rain") || //Zone 1
                                                            weatherNaturalId.equalsIgnoreCase("zone1_rain_light") || weatherNaturalId.equalsIgnoreCase("zone1_storm") ||
                                                            weatherNaturalId.equalsIgnoreCase("zone2_thunder_storm") || //Zone2
                                                            weatherNaturalId.equalsIgnoreCase("zone3_rain") || //Zone3
                                                            weatherNaturalId.equalsIgnoreCase("zone4_wastes_rain") || weatherNaturalId.equalsIgnoreCase("zone4_wastes_rain_heavy") || //Zone4
                                                            weatherNaturalId.equalsIgnoreCase("skylands_rapid_marsh_stormy")) /* Misc */ {
                                                        if(!mermaid.getRainTransform().get()){
                                                            mermaid.setRainTransform(true);
                                                        }
                                                        raining = true;
                                                    }

                                                    if (weatherNaturalId.equalsIgnoreCase("zone3_snow") || weatherNaturalId.equalsIgnoreCase("zone3_snow_storm") ||//Zone3
                                                            weatherNaturalId.equalsIgnoreCase("zone3_snow_heavy")) {
                                                        if(!mermaid.getRainTransform().get()){
                                                            mermaid.setRainTransform(true);
                                                        }
                                                        raining = true;
                                                    }
                                                }

                                                if (weatherForcedAsset != null) {
                                                    String weatherForcedId = weatherForcedAsset.getId();

                                                    if (weatherForcedId.equalsIgnoreCase("zone1_rain") || weatherForcedId.equalsIgnoreCase("zone1_swamp_rain") || //Zone 1
                                                            weatherForcedId.equalsIgnoreCase("zone1_rain_light") || weatherForcedId.equalsIgnoreCase("zone1_storm") ||
                                                            weatherForcedId.equalsIgnoreCase("zone2_thunder_storm") || //Zone2
                                                            weatherForcedId.equalsIgnoreCase("zone3_rain") || //Zone3
                                                            weatherForcedId.equalsIgnoreCase("zone4_wastes_rain") || weatherForcedId.equalsIgnoreCase("zone4_wastes_rain_heavy") || //Zone4
                                                            weatherForcedId.equalsIgnoreCase("skylands_rapid_marsh_stormy")) /* Misc */ {
                                                        if(!mermaid.getRainTransform().get()){
                                                            mermaid.setRainTransform(true);
                                                        }
                                                        raining = true;
                                                    }

                                                    if (weatherForcedId.equalsIgnoreCase("zone3_snow") || weatherForcedId.equalsIgnoreCase("zone3_snow_storm") ||//Zone3
                                                            weatherForcedId.equalsIgnoreCase("zone3_snow_heavy")) {
                                                        if(!mermaid.getRainTransform().get()){
                                                            mermaid.setRainTransform(true);
                                                        }
                                                        raining = true;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if (!raining && mermaid.getRainTransform().get()) {
                                        mermaid.setRainTransform(false);
                                    }
                                }

                                int footFluidId = world.getFluidId((int)Math.floor(pos.getX()), (int)Math.floor(pos.getY()), (int)Math.floor(pos.getZ()));
                                int bodyFluidID = world.getFluidId((int)Math.floor(pos.getX()), (int)Math.floor(pos.getY())+1, (int)Math.floor(pos.getZ()));
                                ///int belowFluidID = 0;//world.getFluidId((int)Math.floor(pos.getX()), (int)Math.floor(pos.getY())-1, (int)Math.floor(pos.getZ()));
                                if(footFluidId > 0 || bodyFluidID > 0){
                                    mermaid.setInFluidBlock(true);
                                }

                                if(footFluidId == 0 && bodyFluidID == 0 && mermaid.isInFluidBlock()){
                                    mermaid.setInFluidBlock(false);
                                }
                            }
                        }
                    });
                }else{
                    Mermaids.LOGGER.atFine().log("Failed to get world : MermaidSystem - Pre-World execution");
                }
            }

            boolean transformPermission = PermissionsModule.get().hasPermission(playerRef.getUuid(), "mermaids.transform") || !Mermaids.getConfig().get().getRequireTransformationPermission();
            boolean toggleMermaid = mermaidSettings.getToggleMermaid();
            int transformationMode = Mermaids.getConfig().get().getTransformationMode();
            boolean transModeZero = transformationMode == 0;
            boolean transModeOne = transformationMode == 1;

            MovementStatesComponent movementState = commandBuffer.getComponent(ref, MovementStatesComponent.getComponentType());

            boolean movementStatesTransform = movementState.getMovementStates().swimming || movementState.getMovementStates().swimJumping || movementState.getMovementStates().inFluid;
            boolean h2OorRain = mermaid.getH2OBlock().get() || mermaid.getRainTransform().get();
            boolean inFluidBlock = mermaid.isInFluidBlock();

            boolean permMerPotion = (transformationMode == 0 || transformationMode == 1) && mermaidSettings.ifPermanentPotion();
            boolean mermaidPotionEffect = mermaid.isPotionEffectTransformation();
            boolean mermaidOnLand = Mermaids.getConfig().get().getMermaidOnLand();

            boolean requireForcedMermaid = Mermaids.getConfig().get().ifRequireForceMermaid();
            boolean forcedMermaid = requireForcedMermaid && mermaidSettings.isForcedMermaid() && !Mermaids.getConfig().get().ifForceMermaidOnlyInWater();
            boolean forcedMermaidWater = requireForcedMermaid && mermaidSettings.isForcedMermaid() && Mermaids.getConfig().get().ifForceMermaidOnlyInWater();

            //Checks to see if in water / other transformation methods
            if (((((movementStatesTransform || h2OorRain || inFluidBlock) && (transModeZero || (permMerPotion))) || mermaidPotionEffect || (mermaidOnLand && (transModeZero || permMerPotion))) && toggleMermaid && transformPermission && (!requireForcedMermaid || forcedMermaidWater)) || forcedMermaid) {

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

                if(mermaid.getPotionElapsedTime() > 0){
                    mermaid.decrementPotionTick();
                }

                if(!mermaid.isMermaid() && mermaid.getElapsedTime() < 35f){
                    EntityStatMap statMapComponent = commandBuffer.getComponent(ref, EntityStatMap.getComponentType());
                    float stamina = statMapComponent.get(DefaultEntityStatTypes.getStamina()).get();
                    if(!(stamina <= statMapComponent.get(DefaultEntityStatTypes.getStamina()).getMax() + 0.5 || stamina >= statMapComponent.get(DefaultEntityStatTypes.getStamina()).getMax() - 0.5)){
                        mermaid.setPreviousStamina(stamina);
                    }
                }

                //Began transformation into Mermaid
                if ((!mermaid.isMermaid() && mermaid.getElapsedTime() >= 35f) || (!mermaid.isMermaid() && (forcedMermaid || forcedMermaidWater))) {
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

                        String mermaidTailModel = mermaidSettings.getMermaidTail();
                        if(Mermaids.ifDebug() && mermaidSettings.ifUseMermaidV2()){
                            mermaidTailModel = "MermaidV2";
                        }

                        ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(mermaidTailModel);
                        if (modelAsset == null) {
                            player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: " + mermaidTailModel + " Model not found"));
                            Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: WaterSystem: " + mermaidTailModel + " Model not found.");
                        } else {
                            ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), skin.clone(), ref, commandBuffer, player, mermaid, mermaidSettings);
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

                    mermaid.setArmorElapsedTime(0f);
                    mermaid.setMermaid(true);

                    if(Mermaids.getConfig().get().ifConsoleLogs()) {
                        Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has transformed into a Mermaid.");
                    }
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
                    mermaid.setPreviousStamina(100f);

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

                        if(Mermaids.getConfig().get().ifConsoleLogs()) {
                            Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has transformed back into a Human.");
                        }
                    } else {
                        player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: Mermaid Component == null [water.isDrying]"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Component. Error: WaterSystem: Mermaid Component == null [water.isDrying]");
                    }

                    List<PlayerRef> playersList = Universe.get().getPlayers();
                    for(int i = 0; i < playersList.size(); i++) {
                        PlayerRef tempPlayerRef = playersList.get(i);

                        if(tempPlayerRef == null || !tempPlayerRef.isValid()){
                            Mermaids.LOGGER.atFine().log("Failed to get temp player reference : MermaidSystem [hide armor]");
                        }else {
                            if(playerRef.getWorldUuid() == tempPlayerRef.getWorldUuid()) {
                                Ref<EntityStore> tempRef = tempPlayerRef.getReference();

                                if (tempRef == null || !tempRef.isValid()) {
                                    Mermaids.LOGGER.atFine().log("Failed to get temp reference : MermaidSystem [hide armor]");
                                } else {
                                    EntityTrackerSystems.EntityViewer entityViewer = commandBuffer.getComponent(tempRef, EntityTrackerSystems.EntityViewer.getComponentType());
                                    if (entityViewer == null) {
                                        player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: EntityViewer Component[tempRef] == null [hide armor]"));
                                    } else {
                                        NetworkId networkId = commandBuffer.getComponent(ref, NetworkId.getComponentType());
                                        if (networkId == null) {
                                            player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: NetworkID Component == null [hide armor]"));
                                        } else {
                                            EntityUpdate entityUpdate = new EntityUpdate();
                                            entityUpdate.networkId = networkId.getId();

                                            ObjectArrayList<ComponentUpdate> updateList = new ObjectArrayList<>();

                                            EquipmentUpdate update = new EquipmentUpdate();

                                            /* UPDATE 4
                                            ItemContainer armor = null;
                                            InventoryComponent.Hotbar armorComponent = commandBuffer.getComponent(ref, InventoryComponent.Hotbar.getComponentType());
                                            if(armorComponent != null) {
                                                armor = armorComponent.getInventory();
                                            }
                                            */
                                            Inventory inventory = player.getInventory();
                                            ItemContainer armor = inventory.getArmor();

                                            update.armorIds = new String[armor.getCapacity()];
                                            Arrays.fill(update.armorIds, "");
                                            armor.forEachWithMeta((slot, itemStack, armorIds) -> armorIds[slot] = itemStack.getItemId(), update.armorIds);

                                            PlayerSettings playerSettings = commandBuffer.getComponent(ref, PlayerSettings.getComponentType());
                                            if (playerSettings != null) {
                                                PlayerConfig.ArmorVisibilityOption armorVisibilityOption = commandBuffer.getExternalData()//store.getExternalData()
                                                        .getWorld()
                                                        .getGameplayConfig()
                                                        .getPlayerConfig()
                                                        .getArmorVisibilityOption();
                                                if (playerSettings.hideHelmet()) {
                                                    update.armorIds[ItemArmorSlot.Head.ordinal()] = "";
                                                }

                                                if (armorVisibilityOption.canHideCuirass() && playerSettings.hideCuirass()) {
                                                    update.armorIds[ItemArmorSlot.Chest.ordinal()] = "";
                                                }

                                                if (armorVisibilityOption.canHideGauntlets() && playerSettings.hideGauntlets()) {
                                                    update.armorIds[ItemArmorSlot.Hands.ordinal()] = "";
                                                }

                                                if (armorVisibilityOption.canHidePants() && playerSettings.hidePants()) {
                                                    update.armorIds[ItemArmorSlot.Legs.ordinal()] = "";
                                                }
                                            }

                                            /* UPDATE 4
                                            InventoryComponent.Hotbar hotbarComponent = commandBuffer.getComponent(ref, InventoryComponent.Hotbar.getComponentType());

                                            if(hotbarComponent != null) {
                                                ItemStack itemInHand = hotbarComponent.getActiveItem();
                                                update.rightHandItemId = itemInHand != null ? itemInHand.getItemId() : "Empty";
                                                ItemStack utilityItem = hotbarComponent.getActiveItem();
                                                update.leftHandItemId = utilityItem != null ? utilityItem.getItemId() : "Empty";
                                            }
                                             */
                                            ItemStack itemInHand = inventory.getItemInHand();
                                            update.rightHandItemId = itemInHand != null ? itemInHand.getItemId() : "Empty";
                                            ItemStack utilityItem = inventory.getUtilityItem();
                                            update.leftHandItemId = utilityItem != null ? utilityItem.getItemId() : "Empty";

                                            updateList.add(update);

                                            entityUpdate.updates = updateList.toArray(ComponentUpdate[]::new);
                                            entityViewer.packetReceiver.writeNoCache(new EntityUpdates(null, new EntityUpdate[]{entityUpdate}));
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (HytaleServer.get().getPluginManager().getPlugin(PluginIdentifier.fromString("Hardaway:Wardrobe")) != null) {
                        PlayerWardrobeComponent wardrobe = commandBuffer.getComponent(ref, PlayerWardrobeComponent.getComponentType());
                        if(wardrobe != null){
                            wardrobe.rebuild();
                        }
                    }

                    mermaid.setMermaid(false);
                }
            }else{
                EntityStatMap statMapComponent = commandBuffer.getComponent(ref, EntityStatMap.getComponentType());
                float stamina = statMapComponent.get(DefaultEntityStatTypes.getStamina()).get();
                if(!(stamina <= statMapComponent.get(DefaultEntityStatTypes.getStamina()).getMax() + 0.5 || stamina >= statMapComponent.get(DefaultEntityStatTypes.getStamina()).getMax() - 0.5)){
                    mermaid.setPreviousStamina(stamina);
                }
            }

            if ((mermaid.isMermaid() && mermaidSettings.getToggleMermaid() && transformPermission) || ((forcedMermaid || forcedMermaidWater) && mermaid.isMermaid())) {
                boolean onLand = false;
                MovementManager movement = commandBuffer.getComponent(ref, MovementManager.getComponentType());
                if(movement != null) {
                    if (movementState.getMovementStates().swimming || movementState.getMovementStates().swimJumping || movementState.getMovementStates().inFluid ||
                            (mermaid.isInFluidBlock() && (movementState.getMovementStates().sitting || movementState.getMovementStates().sleeping))) {
                        if(Mermaids.getConfig().get().getItemIncreaseSwimSpeed()) {
                            /* UPDATE 4
                            ItemStack itemInHand = null;
                            InventoryComponent.Hotbar hotbarComponent = commandBuffer.getComponent(ref, InventoryComponent.Hotbar.getComponentType());
                            if(hotbarComponent != null) {
                                itemInHand = hotbarComponent.getActiveItem();
                            }
                             */
                            ItemStack itemInHand = player.getInventory().getItemInHand();

                            if (itemInHand != null && itemInHand.getItemId().equalsIgnoreCase("weapon_spear_fishbone")) {
                                movement.getSettings().swimJumpForce = 16f;
                                movement.getSettings().baseSpeed = 15f;
                                movement.getSettings().forwardCrouchSpeedMultiplier = 1f;
                                movement.getSettings().backwardCrouchSpeedMultiplier = 0.9f;
                                movement.getSettings().forwardSprintSpeedMultiplier = 2.05f;
                            } else {
                                movement.getSettings().swimJumpForce = 14.5f;
                                movement.getSettings().baseSpeed = 11.5f;
                                movement.getSettings().forwardCrouchSpeedMultiplier = 1f;
                                movement.getSettings().backwardCrouchSpeedMultiplier = 0.8f;
                                movement.getSettings().forwardSprintSpeedMultiplier = 1.85f;
                            }

                            // DIVING TALE
                            if (Mermaids.getConfig().get().getDivingTaleCompat()) {
                                if (itemInHand != null &&
                                        (itemInHand.getItemId().equalsIgnoreCase("Harpoon_Copper") || itemInHand.getItemId().equalsIgnoreCase("Harpoon_Iron"))) {
                                    movement.getSettings().swimJumpForce = 15.25f;
                                    movement.getSettings().baseSpeed = 13f;
                                    movement.getSettings().forwardCrouchSpeedMultiplier = 1f;
                                    movement.getSettings().backwardCrouchSpeedMultiplier = 0.85f;
                                    movement.getSettings().forwardSprintSpeedMultiplier = 1.9f;
                                } else if (itemInHand != null &&
                                        (itemInHand.getItemId().equalsIgnoreCase("Harpoon_Thorium") || itemInHand.getItemId().equalsIgnoreCase("Harpoon_Cobalt")
                                                || itemInHand.getItemId().equalsIgnoreCase("Harpoon_Adamantite"))) {
                                    movement.getSettings().swimJumpForce = 16f;
                                    movement.getSettings().baseSpeed = 15f;
                                    movement.getSettings().forwardCrouchSpeedMultiplier = 1f;
                                    movement.getSettings().backwardCrouchSpeedMultiplier = 0.9f;
                                    movement.getSettings().forwardSprintSpeedMultiplier = 2.05f;
                                } else if (itemInHand != null &&
                                        (itemInHand.getItemId().equalsIgnoreCase("Harpoon_Mithril"))) {
                                    movement.getSettings().swimJumpForce = 17f;
                                    movement.getSettings().baseSpeed = 16.5f;
                                    movement.getSettings().forwardCrouchSpeedMultiplier = 1f;
                                    movement.getSettings().backwardCrouchSpeedMultiplier = 0.95f;
                                    movement.getSettings().forwardSprintSpeedMultiplier = 2.15f;
                                }
                            }

                            // MORENPC
                            if(Mermaids.getConfig().get().getMoreNPCCompat()) {
                                if (itemInHand != null &&
                                        (itemInHand.getItemId().equalsIgnoreCase("Weapon_Trident_Icy"))) {
                                    movement.getSettings().swimJumpForce = 17f;
                                    movement.getSettings().baseSpeed = 16.5f;
                                    movement.getSettings().forwardCrouchSpeedMultiplier = 1f;
                                    movement.getSettings().backwardCrouchSpeedMultiplier = 0.95f;
                                    movement.getSettings().forwardSprintSpeedMultiplier = 2.15f;
                                }
                            }
                        }else{
                            movement.getSettings().swimJumpForce = 14.5f;
                            movement.getSettings().baseSpeed = 11.5f;
                            movement.getSettings().forwardCrouchSpeedMultiplier = 1f;
                            movement.getSettings().backwardCrouchSpeedMultiplier = 0.8f;
                            movement.getSettings().forwardSprintSpeedMultiplier = 1.85f;
                        }
                    } else {
                        movement.getSettings().jumpForce = 8f;
                        movement.getSettings().baseSpeed = 3f;
                        movement.getSettings().forwardCrouchSpeedMultiplier = 0.35f;
                        movement.getSettings().backwardCrouchSpeedMultiplier = 0.25f;
                        movement.getSettings().forwardSprintSpeedMultiplier = 1.35f;
                        onLand = true;
                    }
                    movement.update(playerRef.getPacketHandler());
                }else{
                    player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: Movement Component == null [transformation stats]"));
                }

                if(onLand){
                    /*HudManager playerHud = player.getHudManager();
                    playerHud.showHudComponents(playerRef, HudComponent.Oxygen);

                    EntityStatMap statMapComponent = commandBuffer.getComponent(ref, EntityStatMap.getComponentType());
                    float currOxygen = statMapComponent.get(DefaultEntityStatTypes.getOxygen()).get();
                    float newOxygen = currOxygen;
                    if(mermaid.getDryingElapsedTime() >= 18f){
                        newOxygen -= 1f;
                        mermaid.setDryingElapsedTime(0f);
                    }else{
                        mermaid.incrementDryingTick();
                    }

                    if(newOxygen <= 0f) {
                        statMapComponent.minimizeStatValue(DefaultEntityStatTypes.getOxygen());
                    }else {
                        statMapComponent.setStatValue(DefaultEntityStatTypes.getOxygen(), newOxygen);
                    }*/
                }else {
                    EntityStatMap statMapComponent = commandBuffer.getComponent(ref, EntityStatMap.getComponentType());
                    statMapComponent.maximizeStatValue(DefaultEntityStatTypes.getOxygen());

                    if(Mermaids.getConfig().get().getEasyHungerCompat()) {
                        if (PluginManager.get().getPlugin(new PluginIdentifier("Haas", "EasyHunger")) != null) {
                            EasyHungerCompat.setMaxThirst(store, ref);
                        }
                    }

                    if(Mermaids.getConfig().get().getAquaThirstHungerCompat()) {
                        if (PluginManager.get().getPlugin(new PluginIdentifier("mx.jume.aquahunger", "Aqua-Thirst-hunger")) != null) {
                            AquaThirstHungerCompact.setMaxThirst(store, ref);
                        }
                    }

                    //HudManager playerHud = player.getHudManager();
                    //playerHud.hideHudComponents(playerRef, HudComponent.Oxygen);

                    if(!Mermaids.ifVersion1()) {
                        float stamina = statMapComponent.get(DefaultEntityStatTypes.getStamina()).get();
                        if (movementState.getMovementStates().sprinting) {
                            float newStamina = ((stamina + mermaid.getPreviousStamina()) / 2f);

                            statMapComponent.setStatValue(DefaultEntityStatTypes.getStamina(), newStamina);
                            mermaid.setPreviousStamina(stamina);
                        } else {
                            if (stamina != mermaid.getPreviousStamina()) {
                                mermaid.setPreviousStamina(stamina);
                            }
                        }
                    }
                }

                mermaid.decrementArmorTick();
                if(mermaid.getArmorElapsedTime() < 0){
                    mermaid.setArmorElapsedTime(35f);

                    List<PlayerRef> playersList = Universe.get().getPlayers();
                    for(int i = 0; i < playersList.size(); i++) {
                        PlayerRef tempPlayerRef = playersList.get(i);

                        if(tempPlayerRef == null || !tempPlayerRef.isValid()){
                            Mermaids.LOGGER.atFine().log("Failed to get temp player reference : MermaidSystem [hide armor]");
                        }else {
                            if(playerRef.getWorldUuid() == tempPlayerRef.getWorldUuid()) {
                                Ref<EntityStore> tempRef = tempPlayerRef.getReference();

                                if (tempRef == null || !tempRef.isValid()) {
                                    Mermaids.LOGGER.atFine().log("Failed to get temp reference : MermaidSystem [hide armor]");
                                } else {
                                    EntityTrackerSystems.EntityViewer entityViewer = commandBuffer.getComponent(tempRef, EntityTrackerSystems.EntityViewer.getComponentType());
                                    if (entityViewer == null) {
                                        player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: EntityViewer Component[tempRef] == null [hide armor]"));
                                    } else {
                                        NetworkId networkId = commandBuffer.getComponent(ref, NetworkId.getComponentType());
                                        if (networkId == null) {
                                            player.sendMessage(Message.raw("Mermaids: Error: WaterSystem: NetworkID Component == null [hide armor]"));
                                        } else {
                                            EntityUpdate entityUpdate = new EntityUpdate();
                                            entityUpdate.networkId = networkId.getId();

                                            ObjectArrayList<ComponentUpdate> updateList = new ObjectArrayList<>();

                                            EquipmentUpdate update = new EquipmentUpdate();

                                            /* UPDATE 4
                                            ItemContainer armor = null;
                                            InventoryComponent.Hotbar armorComponent = commandBuffer.getComponent(ref, InventoryComponent.Hotbar.getComponentType());
                                            if(armorComponent != null) {
                                                armor = armorComponent.getInventory();
                                            }
                                             */
                                            Inventory inventory = player.getInventory();
                                            ItemContainer armor = inventory.getArmor();

                                            update.armorIds = new String[armor.getCapacity()];
                                            Arrays.fill(update.armorIds, "");
                                            armor.forEachWithMeta((slot, itemStack, armorIds) -> armorIds[slot] = itemStack.getItemId(), update.armorIds);

                                            PlayerSettings playerSettings = commandBuffer.getComponent(ref, PlayerSettings.getComponentType());
                                            if (playerSettings != null) {
                                                PlayerConfig.ArmorVisibilityOption armorVisibilityOption = store.getExternalData()
                                                        .getWorld()
                                                        .getGameplayConfig()
                                                        .getPlayerConfig()
                                                        .getArmorVisibilityOption();
                                                if (armorVisibilityOption.canHideHelmet() && playerSettings.hideHelmet()) {
                                                    update.armorIds[ItemArmorSlot.Head.ordinal()] = "";
                                                }

                                                if (armorVisibilityOption.canHideCuirass() && playerSettings.hideCuirass()) {
                                                    update.armorIds[ItemArmorSlot.Chest.ordinal()] = "";
                                                }

                                                if (armorVisibilityOption.canHideGauntlets() && playerSettings.hideGauntlets()) {
                                                    update.armorIds[ItemArmorSlot.Hands.ordinal()] = "";
                                                }

                                                update.armorIds[ItemArmorSlot.Legs.ordinal()] = "";
                                            }

                                            /* UPDATE 4
                                            InventoryComponent.Hotbar hotbarComponent = commandBuffer.getComponent(ref, InventoryComponent.Hotbar.getComponentType());
                                            if(hotbarComponent != null) {
                                                ItemStack itemInHand = hotbarComponent.getActiveItem();
                                                update.rightHandItemId = itemInHand != null ? itemInHand.getItemId() : "Empty";
                                                ItemStack utilityItem = hotbarComponent.getActiveItem();
                                                update.leftHandItemId = utilityItem != null ? utilityItem.getItemId() : "Empty";
                                            }
                                             */
                                            ItemStack itemInHand = inventory.getItemInHand();
                                            update.rightHandItemId = itemInHand != null ? itemInHand.getItemId() : "Empty";
                                            ItemStack utilityItem = inventory.getUtilityItem();
                                            update.leftHandItemId = utilityItem != null ? utilityItem.getItemId() : "Empty";

                                            updateList.add(update);

                                            entityUpdate.updates = updateList.toArray(ComponentUpdate[]::new);
                                            entityViewer.packetReceiver.writeNoCache(new EntityUpdates(null, new EntityUpdate[]{entityUpdate}));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery(){
        return Query.and(this.mermaidComponentType, this.mermaidSettingsComponentType);
    }
}
