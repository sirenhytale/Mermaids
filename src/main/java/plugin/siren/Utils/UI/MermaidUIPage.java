package plugin.siren.Utils.UI;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUICommand;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.Value;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Contributions.starman.modelutils.ModelHelper;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.MermaidSettingsComponent;
import plugin.siren.Utils.Cosmetics.MermaidCosmetic;
import plugin.siren.Utils.Cosmetics.MermaidCosmeticType;
import plugin.siren.Utils.Models.MermaidColor;
import plugin.siren.Utils.Models.MermaidModel;

import javax.annotation.Nonnull;

public class MermaidUIPage extends InteractiveCustomUIPage<MermaidUIPage.MermaidUIEventData> {
    private static final Value<String> CATEGORY_BUTTON_STYLE = Value.ref("Pages/UIGallery/CategoryButton.ui", "LabelStyle");
    private static final Value<String> CATEGORY_BUTTON_SELECTED_STYLE = Value.ref("Pages/UIGallery/CategoryButton.ui", "SelectedLabelStyle");
    private MermaidUIPage.Category selectedCategory = Category.PRESETS;

    public MermaidUIPage(@Nonnull PlayerRef playerRef, MermaidUIPage.Category category) {
        this(playerRef);
        this.selectedCategory = category;
    }

    public MermaidUIPage(@Nonnull PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismiss, MermaidUIPage.MermaidUIEventData.CODEC);
    }

    @Override
    public void build(
            @Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder commandBuilder, @Nonnull UIEventBuilder eventBuilder, @Nonnull Store<EntityStore> store
    ) {
        MermaidSettingsComponent mermaidSettings = store.getComponent(ref, MermaidSettingsComponent.getComponentType());

        commandBuilder.append("Pages/MermaidUI/MermaidUIPage.ui");

        if(mermaidSettings != null) {
            Player player = store.getComponent(ref, Player.getComponentType());

            this.buildCategoryList(commandBuilder, eventBuilder, mermaidSettings);
            this.displayCategory(this.selectedCategory, commandBuilder, eventBuilder, mermaidSettings, player);
        }else{
            Mermaids.LOGGER.atWarning().log("ERROR: MermaidUIPage build mermaidSettings is null!");
        }
    }

    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull MermaidUIPage.MermaidUIEventData data) {
        Player player = store.getComponent(ref, Player.getComponentType());

        MermaidComponent mermaid = store.getComponent(ref, MermaidComponent.getComponentType());
        MermaidSettingsComponent mermaidSettings = store.getComponent(ref, MermaidSettingsComponent.getComponentType());

        if(mermaid != null && mermaidSettings != null) {
            UICommandBuilder commandBuilder = new UICommandBuilder();
            UIEventBuilder eventBuilder = new UIEventBuilder();
            if (data.category != null) {
                MermaidUIPage.Category newCategory = MermaidUIPage.Category.fromId(data.category);
                if (newCategory != this.selectedCategory) {
                    int oldIndex = this.selectedCategory.ordinal();
                    if(oldIndex >= 3){
                        oldIndex--;
                    }
                    commandBuilder.set("#CategoryList[" + oldIndex + "].Style", CATEGORY_BUTTON_STYLE);
                    int newIndex = newCategory.ordinal();
                    if(newIndex >= 3){
                        newIndex--;
                    }
                    commandBuilder.set("#CategoryList[" + newIndex + "].Style", CATEGORY_BUTTON_SELECTED_STYLE);
                    this.selectedCategory = newCategory;
                    this.displayCategory(this.selectedCategory, commandBuilder, eventBuilder, mermaidSettings, player);
                }

                this.sendUpdate(commandBuilder, eventBuilder, false);
            }else if (data.tailPresets != null) {
                String msgMerPreset = "ERROR GETTING PRESET";

                String mermaidTailPath = mermaidSettings.getDefaultMermaidTail();
                String mermaidTailColorPath = mermaidSettings.getTailColor();
                MermaidColor mermaidTailColor = mermaidSettings.getCosmeticColor();

                MermaidModel modelId = mermaidSettings.getMermaidTailId();

                int dorsalFin = -1;
                int pectoralFin = -1;
                int auricleFin = -1;
                if (data.tailPresets.equalsIgnoreCase("0")) {
                    msgMerPreset = "default";

                    mermaidTailPath = MermaidModel.Mermaid.getModel();

                    boolean allowedColor = false;
                    for(MermaidColor color : MermaidModel.Mermaid.getColorList()){
                        if(mermaidTailColor == color){
                            allowedColor = true;
                        }
                    }
                    if(!allowedColor){
                        mermaidTailColor = MermaidModel.Mermaid.getColorList().getFirst();
                        mermaidTailColorPath = "Mermaids_Mermaid_Orange_Texture";
                    }

                    modelId = MermaidModel.Mermaid;

                    dorsalFin = -1;
                    pectoralFin = -1;
                    auricleFin = -1;
                } else if (data.tailPresets.equalsIgnoreCase("1")) {
                    msgMerPreset = "Full Fins";

                    mermaidTailPath = MermaidModel.Mermaid.getModel();

                    boolean allowedColor = false;
                    for(MermaidColor color : MermaidModel.Mermaid.getColorList()){
                        if(mermaidTailColor == color){
                            allowedColor = true;
                        }
                    }
                    if(!allowedColor){
                        mermaidTailColor = MermaidModel.Mermaid.getColorList().getFirst();
                        mermaidTailColorPath = "Mermaids_Mermaid_Orange_Texture";
                    }

                    modelId = MermaidModel.Mermaid;

                    dorsalFin = MermaidCosmetic.Long_Back_Fins.getValue();
                    pectoralFin = MermaidCosmetic.Top_Side_Fins.getValue();
                    auricleFin = MermaidCosmetic.Ear_Fins.getValue();
                } else if (data.tailPresets.equalsIgnoreCase("2")) {
                    msgMerPreset = "Orca";

                    mermaidTailPath = MermaidModel.Mammal.getModel();
                    mermaidTailColor = MermaidColor.BLACK;
                    mermaidTailColorPath = "Mermaids_Mermaid_Orca_Texture";

                    modelId = MermaidModel.Mammal;

                    dorsalFin = MermaidCosmetic.Dorsal_Fin_Large.getValue();
                    pectoralFin = MermaidCosmetic.Flippers_Large.getValue();
                    auricleFin = -1;
                } else if (data.tailPresets.equalsIgnoreCase("3")) {
                    msgMerPreset = "Dolphin";

                    mermaidTailPath = MermaidModel.Mammal.getModel();
                    mermaidTailColor = MermaidColor.GRAY;
                    mermaidTailColorPath = "Mermaids_Mermaid_Dolphin_Texture";

                    modelId = MermaidModel.Mammal;

                    dorsalFin = MermaidCosmetic.Dorsal_Fin.getValue();
                    pectoralFin = MermaidCosmetic.Flippers.getValue();
                    auricleFin = -1;
                }

                Message playerMessage = Message.translation("server.customUI.mermaids.mermaidui.category.presets.playerMsg.modify").param("preset", msgMerPreset);
                player.sendMessage(playerMessage);

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    String consoleMessage = Message.translation("server.customUI.mermaids.mermaidui.category.presets.consoleMsg.modify").param("username", player.getDisplayName()).param("preset", msgMerPreset).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }

                String oldMermaidTail = mermaidSettings.getMermaidTail();
                mermaidSettings.setMermaidTail(mermaidTailPath);

                String oldTailColorPath = mermaidSettings.getTailColor();
                mermaidSettings.setTailColor(mermaidTailColorPath);

                MermaidColor oldTailColor = mermaidSettings.getCosmeticColor();
                mermaidSettings.setCosmeticColor(mermaidTailColor);

                MermaidModel oldMermaidTailId = mermaidSettings.getMermaidTailId();
                mermaidSettings.setMermaidTailId(modelId);

                int oldCosmeticDorsalValue = mermaidSettings.getMermaidCosmeticValue(MermaidCosmeticType.DORSAL_FIN);
                mermaidSettings.setMermaidCosmetic(MermaidCosmeticType.DORSAL_FIN, dorsalFin);

                int oldCosmeticPectoralValue = mermaidSettings.getMermaidCosmeticValue(MermaidCosmeticType.PECTORAL_FIN);
                mermaidSettings.setMermaidCosmetic(MermaidCosmeticType.PECTORAL_FIN, pectoralFin);

                int oldCosmeticAuricleValue = mermaidSettings.getMermaidCosmeticValue(MermaidCosmeticType.AURICLE_FIN);
                mermaidSettings.setMermaidCosmetic(MermaidCosmeticType.AURICLE_FIN, auricleFin);

                boolean differentFins = dorsalFin != oldCosmeticDorsalValue || pectoralFin != oldCosmeticPectoralValue ||  auricleFin != oldCosmeticAuricleValue;
                if ((!oldMermaidTail.equals(mermaidTailPath) || (!oldTailColorPath.equals(mermaidTailColorPath)) || (oldTailColor != mermaidTailColor) || (oldMermaidTailId != modelId) || differentFins) && mermaid.isMermaid()) {
                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(mermaidTailPath);
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: MermaidV2UIPage: " + mermaidTailPath + " Model not found"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidV2UIPage: " + mermaidTailPath + " Model not found.");
                    } else {
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), mermaid.getMermaidSkin().clone(), ref, mermaid, mermaidSettings);
                    }
                }

                MermaidUIPage merPage = new MermaidUIPage(playerRef, selectedCategory);
                player.getPageManager().openCustomPage(ref, store, merPage);

                this.sendUpdate(commandBuilder, eventBuilder, false);
            } else if (data.tailModel != null) {
                String msgMerTail = "ERROR GETTING TAIL";
                String mermaidTailPath = mermaidSettings.getDefaultMermaidTail();
                String mermaidTailColorPath = mermaidSettings.getTailColor();
                MermaidColor mermaidTailColor = mermaidSettings.getCosmeticColor();
                MermaidModel modelId = mermaidSettings.getMermaidTailId();
                if (data.tailModel.equalsIgnoreCase("0")) {
                    msgMerTail = "Mermaid Model";
                    mermaidTailPath = MermaidModel.Mermaid.getModel();

                    boolean allowedColor = false;
                    for(MermaidColor color : MermaidModel.Mermaid.getColorList()){
                        if(mermaidTailColor == color){
                            allowedColor = true;
                        }
                    }
                    if(!allowedColor){
                        mermaidTailColor = MermaidModel.Mermaid.getColorList().getFirst();
                        mermaidTailColorPath = "Mermaids_Mermaid_Orange_Texture";
                    }

                    modelId = MermaidModel.Mermaid;
                } else if (data.tailModel.equalsIgnoreCase("1")) {
                    msgMerTail = "Mammal Model";
                    mermaidTailPath = MermaidModel.Mammal.getModel();

                    boolean allowedColor = false;
                    for(MermaidColor color : MermaidModel.Mammal.getColorList()){
                        if(mermaidTailColor == color){
                            allowedColor = true;
                        }
                    }
                    if(!allowedColor){
                        mermaidTailColor = MermaidModel.Mammal.getColorList().getFirst();
                        mermaidTailColorPath = "Mermaids_Mermaid_Orca_Texture";
                    }

                    modelId = MermaidModel.Mammal;
                }

                Message playerMessage = Message.translation("server.customUI.mermaids.mermaidui.category.model.playerMsg.modify").param("model", msgMerTail);
                player.sendMessage(playerMessage);

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    String consoleMessage = Message.translation("server.customUI.mermaids.mermaidui.category.model.consoleMsg.modify").param("username", player.getDisplayName()).param("model", msgMerTail).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }

                String oldMermaidTail = mermaidSettings.getMermaidTail();
                mermaidSettings.setMermaidTail(mermaidTailPath);

                String oldTailColorPath = mermaidSettings.getTailColor();
                mermaidSettings.setTailColor(mermaidTailColorPath);

                MermaidColor oldTailColor = mermaidSettings.getCosmeticColor();
                mermaidSettings.setCosmeticColor(mermaidTailColor);

                MermaidModel oldMermaidTailId = mermaidSettings.getMermaidTailId();
                mermaidSettings.setMermaidTailId(modelId);

                if ((!oldMermaidTail.equals(mermaidTailPath) || !oldTailColorPath.equals(mermaidTailColorPath) || oldTailColor != mermaidTailColor || oldMermaidTailId != modelId) && mermaid.isMermaid()) {
                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(mermaidTailPath);
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: MermaidV2UIPage: " + mermaidTailPath + " Model not found"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidV2UIPage: " + mermaidTailPath + " Model not found.");
                    } else {
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), mermaid.getMermaidSkin().clone(), ref, mermaid, mermaidSettings);
                    }
                }

                MermaidUIPage merPage = new MermaidUIPage(playerRef, selectedCategory);
                player.getPageManager().openCustomPage(ref, store, merPage);

                this.sendUpdate(commandBuilder, eventBuilder, false);
            } else if (data.tailColor != null) {
                String msgTailColor = "ERROR GETTING COLOR";
                String mermaidTailColorPath = mermaidSettings.getDefaultTailColor();
                if (data.tailColor.equalsIgnoreCase("0")) {
                    msgTailColor = "Orange";
                    mermaidTailColorPath = "Mermaids_Mermaid_Orange_Texture";

                    mermaidSettings.setCosmeticColor(MermaidColor.ORANGE);
                } else if (data.tailColor.equalsIgnoreCase("1")) {
                    msgTailColor = "Pink";
                    mermaidTailColorPath = "Mermaids_Mermaid_Pink_Texture";

                    mermaidSettings.setCosmeticColor(MermaidColor.PINK);
                } else if (data.tailColor.equalsIgnoreCase("2")) {
                    msgTailColor = "Purple";
                    mermaidTailColorPath = "Mermaids_Mermaid_Purple_Texture";

                    mermaidSettings.setCosmeticColor(MermaidColor.PURPLE);
                } else if (data.tailColor.equalsIgnoreCase("3")) {
                    msgTailColor = "Rose";
                    mermaidTailColorPath = "Mermaids_Mermaid_Rose_Texture";

                    mermaidSettings.setCosmeticColor(MermaidColor.ROSE);
                } else if (data.tailColor.equalsIgnoreCase("4")) {
                    msgTailColor = "Lime";
                    mermaidTailColorPath = "Mermaids_Mermaid_Lime_Texture";

                    mermaidSettings.setCosmeticColor(MermaidColor.LIME);
                } else if (data.tailColor.equalsIgnoreCase("5")) {
                    msgTailColor = "Blue";
                    mermaidTailColorPath = "Mermaids_Mermaid_Blue_Texture";

                    mermaidSettings.setCosmeticColor(MermaidColor.BLUE);
                } else if (data.tailColor.equalsIgnoreCase("6")) {
                    msgTailColor = "Aqua";
                    mermaidTailColorPath = "Mermaids_Mermaid_Aqua_Texture";

                    mermaidSettings.setCosmeticColor(MermaidColor.AQUA);
                } else if (data.tailColor.equalsIgnoreCase("7")) {
                    msgTailColor = "Cyan";
                    mermaidTailColorPath = "Mermaids_Mermaid_Cyan_Texture";

                    mermaidSettings.setCosmeticColor(MermaidColor.CYAN);
                }

                Message playerMessage = Message.translation("server.customUI.mermaids.mermaidui.category.color.playerMsg.modify").param("color", msgTailColor);
                player.sendMessage(playerMessage);

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    String consoleMessage = Message.translation("server.customUI.mermaids.mermaidui.category.color.consoleMsg.modify").param("username", player.getDisplayName()).param("color", msgTailColor).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }

                String oldTailColor = mermaidSettings.getTailColor();
                mermaidSettings.setTailColor(mermaidTailColorPath);

                if (!oldTailColor.equals(mermaidTailColorPath) && mermaid.isMermaid()) {
                    String activeMermaidTail = mermaidSettings.getMermaidTail();
                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(activeMermaidTail);
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: MermaidUIPage: " + activeMermaidTail + " Model not found"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidUIPage: " + activeMermaidTail + " Model not found.");
                    } else {
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), mermaid.getMermaidSkin().clone(), ref, mermaid, mermaidSettings);
                    }
                }

                this.sendUpdate(commandBuilder, eventBuilder, false);
            } else if (data.oceanFlukeColor != null) {
                String msgTailColor = "ERROR GETTING COLOR";
                String mermaidTailColorPath = mermaidSettings.getDefaultTailColor();
                if (data.oceanFlukeColor.equalsIgnoreCase("0")) {
                    msgTailColor = "Black";
                    mermaidTailColorPath = "Mermaids_Mermaid_Orca_Texture";

                    mermaidSettings.setCosmeticColor(MermaidColor.BLACK);
                } else if (data.oceanFlukeColor.equalsIgnoreCase("1")) {
                    msgTailColor = "Gray";
                    mermaidTailColorPath = "Mermaids_Mermaid_Dolphin_Texture";

                    mermaidSettings.setCosmeticColor(MermaidColor.GRAY);
                }

                Message playerMessage = Message.translation("server.customUI.mermaids.mermaidui.category.color.playerMsg.modify").param("color", msgTailColor);
                player.sendMessage(playerMessage);

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    String consoleMessage = Message.translation("server.customUI.mermaids.mermaidui.category.color.consoleMsg.modify").param("username", player.getDisplayName()).param("color", msgTailColor).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }

                String oldTailColor = mermaidSettings.getTailColor();
                mermaidSettings.setTailColor(mermaidTailColorPath);

                if (!oldTailColor.equals(mermaidTailColorPath) && mermaid.isMermaid()) {
                    String activeMermaidTail = mermaidSettings.getMermaidTail();
                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(activeMermaidTail);
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: MermaidUIPage: " + activeMermaidTail + " Model not found"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidUIPage: " + activeMermaidTail + " Model not found.");
                    } else {
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), mermaid.getMermaidSkin().clone(), ref, mermaid, mermaidSettings);
                    }
                }

                this.sendUpdate(commandBuilder, eventBuilder, false);
            } else if (data.dorsalFin != null) {
                String dorsalFinMsg = "ERROR DORSAL FIN";
                int dorsalFin = -1;
                if (data.dorsalFin.equalsIgnoreCase("0")) {
                    dorsalFinMsg = "none";
                    dorsalFin = -1;
                } else if (data.dorsalFin.equalsIgnoreCase("1")) {
                    dorsalFinMsg = "all along the back";
                    dorsalFin = MermaidCosmetic.Long_Back_Fins.getValue();
                }else if (data.dorsalFin.equalsIgnoreCase("2")) {
                    dorsalFinMsg = "fin";
                    dorsalFin = MermaidCosmetic.Dorsal_Fin.getValue();
                }else if (data.dorsalFin.equalsIgnoreCase("3")) {
                    dorsalFinMsg = "large fin";
                    dorsalFin = MermaidCosmetic.Dorsal_Fin_Large.getValue();
                }

                Message playerMessage = Message.translation("server.customUI.mermaids.mermaidui.category.dorsalFin.playerMsg.modify").param("fin", dorsalFinMsg);
                player.sendMessage(playerMessage);

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    String consoleMessage = Message.translation("server.customUI.mermaids.mermaidui.category.dorsalFin.consoleMsg.modify").param("username", player.getDisplayName()).param("fin", dorsalFinMsg).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }

                int oldCosmeticValue = mermaidSettings.getMermaidCosmeticValue(MermaidCosmeticType.DORSAL_FIN);

                mermaidSettings.setMermaidCosmetic(MermaidCosmeticType.DORSAL_FIN, dorsalFin);

                if (dorsalFin != oldCosmeticValue && mermaid.isMermaid()) {
                    String activeMermaidTail = mermaidSettings.getMermaidTail();
                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(activeMermaidTail);
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: MermaidV2UIPage: " + activeMermaidTail + " Model not found"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidV2UIPage: " + activeMermaidTail + " Model not found.");
                    } else {
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), mermaid.getMermaidSkin().clone(), ref, mermaid, mermaidSettings);
                    }
                }

                this.sendUpdate(commandBuilder, eventBuilder, false);
            } else if (data.pectoralFin != null) {
                String pectoralFinMsg = "ERROR PELVIC FIN";
                int pectoralFin = -1;
                if (data.pectoralFin.equalsIgnoreCase("0")) {
                    pectoralFinMsg = "none";
                    pectoralFin = -1;
                } else if (data.pectoralFin.equalsIgnoreCase("1")) {
                    pectoralFinMsg = "top fins";
                    pectoralFin = MermaidCosmetic.Top_Side_Fins.getValue(); //NEED TO SET
                } else if (data.pectoralFin.equalsIgnoreCase("2")) {
                    pectoralFinMsg = "flippers";
                    pectoralFin = MermaidCosmetic.Flippers.getValue(); //NEED TO SET
                } else if (data.pectoralFin.equalsIgnoreCase("3")) {
                    pectoralFinMsg = "large flippers";
                    pectoralFin = MermaidCosmetic.Flippers_Large.getValue(); //NEED TO SET
                }

                Message playerMessage = Message.translation("server.customUI.mermaids.mermaidui.category.pectoralFin.playerMsg.modify").param("fin", pectoralFinMsg);
                player.sendMessage(playerMessage);

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    String consoleMessage = Message.translation("server.customUI.mermaids.mermaidui.category.pectoralFin.consoleMsg.modify").param("username", player.getDisplayName()).param("fin", pectoralFinMsg).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }

                int oldCosmeticValue = mermaidSettings.getMermaidCosmeticValue(MermaidCosmeticType.PECTORAL_FIN);

                mermaidSettings.setMermaidCosmetic(MermaidCosmeticType.PECTORAL_FIN, pectoralFin);

                if (oldCosmeticValue != pectoralFin && mermaid.isMermaid()) {
                    String activeMermaidTail = mermaidSettings.getMermaidTail();
                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(activeMermaidTail);
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: MermaidV2UIPage: " + activeMermaidTail + " Model not found"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidV2UIPage: " + activeMermaidTail + " Model not found.");
                    } else {
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), mermaid.getMermaidSkin().clone(), ref, mermaid, mermaidSettings);
                    }
                }

                this.sendUpdate(commandBuilder, eventBuilder, false);
            } else if (data.auricleFin != null) {
                String auricleFinMsg = "ERROR AURICLE FIN";
                int auricleFin = -1;
                if (data.auricleFin.equalsIgnoreCase("0")) {
                    auricleFinMsg = "none";
                    auricleFin = -1;
                } else if (data.auricleFin.equalsIgnoreCase("1")) {
                    auricleFinMsg = "default fin ears";
                    auricleFin = MermaidCosmetic.Ear_Fins.getValue();
                } else if (data.auricleFin.equalsIgnoreCase("2")) {
                    auricleFinMsg = "spiked fin ears";
                    auricleFin = MermaidCosmetic.Spiked_Ear_Fins.getValue();
                }

                Message playerMessage = Message.translation("server.customUI.mermaids.mermaidui.category.auricleFin.playerMsg.modify").param("fin", auricleFinMsg);
                player.sendMessage(playerMessage);

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    String consoleMessage = Message.translation("server.customUI.mermaids.mermaidui.category.auricleFin.consoleMsg.modify").param("username", player.getDisplayName()).param("fin", auricleFinMsg).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }

                int oldCosmeticValue = mermaidSettings.getMermaidCosmeticValue(MermaidCosmeticType.AURICLE_FIN);

                mermaidSettings.setMermaidCosmetic(MermaidCosmeticType.AURICLE_FIN, auricleFin);

                if (oldCosmeticValue != auricleFin && mermaid.isMermaid()) {
                    String activeMermaidTail = mermaidSettings.getMermaidTail();
                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(activeMermaidTail);
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: MermaidV2UIPage: " + activeMermaidTail + " Model not found"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidV2UIPage: " + activeMermaidTail + " Model not found.");
                    } else {
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), mermaid.getMermaidSkin().clone(), ref, mermaid, mermaidSettings);
                    }
                }

                this.sendUpdate(commandBuilder, eventBuilder, false);
            } else if (data.settings != null) {
                PlayerSettings setting = PlayerSettings.get(data.settings);

                if(setting != null){
                    String playerTranslationId = "";
                    String consoleTranslationId = "";
                    if(setting == PlayerSettings.TOGGLE){
                        boolean newValue = !mermaidSettings.getToggleMermaid();

                        if(newValue){
                            playerTranslationId = "server.customUI.mermaids.mermaidui.category.settings.playerMsg.toggle.true";
                            consoleTranslationId = "server.customUI.mermaids.mermaidui.category.settings.consoleMsg.toggle.true";
                        }else {
                            playerTranslationId = "server.customUI.mermaids.mermaidui.category.settings.playerMsg.toggle.false";
                            consoleTranslationId = "server.customUI.mermaids.mermaidui.category.settings.consoleMsg.toggle.false";
                        }

                        if(player.hasPermission("mermaids.toggle")) {
                            mermaidSettings.setToggleMermaid(newValue);
                        }else{
                            playerTranslationId = "server.customUI.mermaids.mermaidui.category.settings.playerMsg.toggle.missingPerms";
                            consoleTranslationId = "server.customUI.mermaids.mermaidui.category.settings.consoleMsg.toggle.missingPerms";
                        }
                    }else if(setting == PlayerSettings.GLOW){
                        boolean newValue = !mermaidSettings.ifMermaidGlow();

                        if(newValue){
                            playerTranslationId = "server.customUI.mermaids.mermaidui.category.settings.playerMsg.glow.true";
                            consoleTranslationId = "server.customUI.mermaids.mermaidui.category.settings.consoleMsg.glow.true";
                        }else {
                            playerTranslationId = "server.customUI.mermaids.mermaidui.category.settings.playerMsg.glow.false";
                            consoleTranslationId = "server.customUI.mermaids.mermaidui.category.settings.consoleMsg.glow.false";
                        }

                        if(player.hasPermission("mermaids.glow")) {
                            mermaidSettings.setMermaidGlow(newValue);
                        }else{
                            playerTranslationId = "server.customUI.mermaids.mermaidui.category.settings.playerMsg.glow.missingPerms";
                            consoleTranslationId = "server.customUI.mermaids.mermaidui.category.settings.consoleMsg.glow.missingPerms";
                        }
                    }else if(setting == PlayerSettings.REMOVEPOTION){
                        mermaidSettings.setPermanentPotion(false);

                        playerTranslationId = "server.customUI.mermaids.mermaidui.category.settings.playerMsg.removePotion";
                        consoleTranslationId = "server.customUI.mermaids.mermaidui.category.settings.consoleMsg.removePotion";
                    }

                    Message playerMessage = Message.translation(playerTranslationId);
                    player.sendMessage(playerMessage);

                    String consoleMessage = Message.translation(consoleTranslationId).param("username", player.getDisplayName()).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }else{
                    Message playerMessage = Message.translation("server.customUI.mermaids.mermaidui.category.settings.playerMsg.error");
                    player.sendMessage(playerMessage);
                }

                this.sendUpdate(commandBuilder, eventBuilder, false);
            }
        }else{
            Mermaids.LOGGER.atFine().log("Mermaids: Error: MermaidUIPage: Failed to load Mermaid Component and/or Mermaid Settings Component : handleDataEvent");
        }
    }

    private void buildCategoryList(@Nonnull UICommandBuilder commandBuilder, @Nonnull UIEventBuilder eventBuilder, MermaidSettingsComponent mermaidSettings) {
        commandBuilder.clear("#CategoryList");

        int j = 0;
        for (int i = 0; i < MermaidUIPage.Category.values().length; i++) {
            MermaidUIPage.Category category = MermaidUIPage.Category.values()[i];

            if(mermaidSettings.getMermaidTailId().getValue() == MermaidModel.Mermaid.getValue() && category == Category.OCEAN_FLUKE_COLOR){
                j++;
                continue;
            }
            if(mermaidSettings.getMermaidTailId().getValue() == MermaidModel.Mammal.getValue() && category == Category.COLOR){
                j++;
                continue;
            }

            commandBuilder.append("#CategoryList", "Pages/UIGallery/CategoryButton.ui");
            commandBuilder.set("#CategoryList[" + String.valueOf(i-j) + "].TextSpans", Message.translation(category.getNameKey()));
            if (category == this.selectedCategory) {
                commandBuilder.set("#CategoryList[" + String.valueOf(i-j) + "].Style", CATEGORY_BUTTON_SELECTED_STYLE);
            }

            eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#CategoryList[" + String.valueOf(i-j) + "]", EventData.of("Category", category.getId()));
        }
    }

    private void displayCategory(@Nonnull MermaidUIPage.Category category, @Nonnull UICommandBuilder commandBuilder, @Nonnull UIEventBuilder eventBuilder, MermaidSettingsComponent mermaidSettings, Player player) {
        commandBuilder.set("#CategoryTitle.TextSpans", Message.translation(category.getNameKey()));
        commandBuilder.clear("#CategoryContent");
        commandBuilder.append("#CategoryContent", category.getContentPath());

        if (category.id.equalsIgnoreCase("presets")) {
            for (int i = 0; i < category.getTailSelectionCount(); i++) {
                String selector = "#CategoryContent #TailPreset" + i + " #Selector";
                eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, selector, EventData.of("TailPresets", String.valueOf(i)));
            }
        } else if(category.id.equalsIgnoreCase("model")) {
            for (int i = 0; i < category.getTailSelectionCount(); i++) {
                String selector = "#CategoryContent #TailModel" + i + " #Selector";
                eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, selector, EventData.of("TailModel", String.valueOf(i)));
            }
        } else if (category.id.equalsIgnoreCase("color")) {
            for (int i = 0; i < category.getTailSelectionCount(); i++) {
                String selector = "#CategoryContent #TailColor" + i + " #Selector";
                eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, selector, EventData.of("TailColor", String.valueOf(i)));
            }
        } else if (category.id.equalsIgnoreCase("ocean_fluke_color")) {
            for (int i = 0; i < category.getTailSelectionCount(); i++) {
                String selector = "#CategoryContent #OceanFlukeColor" + i + " #Selector";
                eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, selector, EventData.of("OceanFlukeColor", String.valueOf(i)));
            }
        } else if (category.id.equalsIgnoreCase("dorsal_fin")) {
            for (int i = 0; i < category.getTailSelectionCount(); i++) {
                String selector = "#CategoryContent #DorsalFin" + i + " #Selector";
                eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, selector, EventData.of("DorsalFin", String.valueOf(i)));
            }
        } else if (category.id.equalsIgnoreCase("pectoral_fin")) {
            for (int i = 0; i < category.getTailSelectionCount(); i++) {
                String selector = "#CategoryContent #PectoralFin" + i + " #Selector";
                eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, selector, EventData.of("PectoralFin", String.valueOf(i)));
            }
        } else if (category.id.equalsIgnoreCase("auricle_fin")) {
            for (int i = 0; i < category.getTailSelectionCount(); i++) {
                String selector = "#CategoryContent #AuricleFin" + i + " #Selector";
                eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, selector, EventData.of("AuricleFin", String.valueOf(i)));
            }
        } else if (category.id.equalsIgnoreCase("settings")) {
            for (int i = 0; i < category.getTailSelectionCount(); i++) {
                PlayerSettings setting = PlayerSettings.get(i);

                if(setting.option == SettingOption.BUTTON){
                    String selector = "#CategoryContent #Settings" + i + " #Selector";
                    eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, selector, EventData.of("Settings", String.valueOf(setting.getValue())));
                }else if(setting.option == SettingOption.CHECKBOX){
                    String selector = "#CategoryContent #Settings" + i + " #Selector #CheckBox";
                    String selectorValue = "#CategoryContent #Settings" + i + " #Selector #CheckBox.Value";
                    String selectorText = "#CategoryContent #Settings" + i + " #Selector #TextLabel.Text";
                    boolean value = false;

                    if(setting == PlayerSettings.TOGGLE){
                        value = mermaidSettings.getToggleMermaid();

                        if(!player.hasPermission("mermaids.toggle")){
                            commandBuilder.set(selectorText, "Mermaid Transformation - Missing Perm: mermaids.toggle");
                        }
                    }else if(setting == PlayerSettings.GLOW){
                        value = mermaidSettings.ifMermaidGlow();

                        if(!player.hasPermission("mermaids.glow")){
                            commandBuilder.set(selectorText, "Mermaid Glow - Missing Perm: mermaids.glow");
                        }
                    }

                    commandBuilder.set(selectorValue, value);
                    eventBuilder.addEventBinding(CustomUIEventBindingType.ValueChanged, selector, EventData.of("Settings", String.valueOf(setting.getValue())));
                }
            }
        }
    }

    private static enum Category {
        PRESETS(
                "presets",
                "server.customUI.mermaids.mermaidui.category.presets",
                "Pages/MermaidUI/Categories/TailPresetsContent.ui",
                4
        ),
        MODEL(
                "model",
                "server.customUI.mermaids.mermaidui.category.model",
                "Pages/MermaidUI/Categories/TailModelContent.ui",
                2
        ),
        COLOR(
                "color",
                "server.customUI.mermaids.mermaidui.category.color",
                "Pages/MermaidUI/Categories/TailColorContent.ui",
                8
        ),
        OCEAN_FLUKE_COLOR(
                "ocean_fluke_color",
                "server.customUI.mermaids.mermaidui.category.color",
                "Pages/MermaidUI/Categories/OceanFlukeColorContent.ui",
                2
        ),
        DORSAL_FIN(
                "dorsal_fin",
                "server.customUI.mermaids.mermaidui.category.dorsalFin",
                "Pages/MermaidUI/Categories/DorsalFinContent.ui",
                4
        ),
        PECTORAL_FIN(
                "pectoral_fin",
                "server.customUI.mermaids.mermaidui.category.pectoralFin",
                "Pages/MermaidUI/Categories/PectoralFinContent.ui",
                4
        ),
        AURICLE_FIN(
                "auricle_fin",
                "server.customUI.mermaids.mermaidui.category.auricleFin",
                "Pages/MermaidUI/Categories/AuricleFinContent.ui",
                3
        ),
        SETTINGS(
                "settings",
                "server.customUI.mermaids.mermaidui.category.settings",
                "Pages/MermaidUI/Categories/SettingsContent.ui",
                3
        );

        private final String id;
        private final String nameKey;
        private final String contentPath;
        private final int tailSelectionCount;

        private Category(String id, String nameKey, String contentPath, int tailSelectionCount) {
            this.id = id;
            this.nameKey = nameKey;
            this.contentPath = contentPath;
            this.tailSelectionCount = tailSelectionCount;
        }

        public String getId() {
            return this.id;
        }

        public String getNameKey() {
            return this.nameKey;
        }

        public String getContentPath() {
            return this.contentPath;
        }

        public int getTailSelectionCount(){
            return this.tailSelectionCount;
        }

        public static MermaidUIPage.Category fromId(String id) {
            for (MermaidUIPage.Category category : values()) {
                if (category.id.equals(id)) {
                    return category;
                }
            }

            return PRESETS;
        }
    }

    public static class MermaidUIEventData {
        static final String KEY_CATEGORY = "Category";
        static final String KEY_TAIL_PRESETS = "TailPresets";
        static final String KEY_TAIL_MODEL = "TailModel";
        static final String KEY_TAIL_COLOR = "TailColor";
        static final String KEY_OCEAN_FLUKE_COLOR = "OceanFlukeColor";
        static final String KEY_DORSAL_FIN = "DorsalFin";
        static final String KEY_PECTORAL_FIN = "PectoralFin";
        static final String KEY_AURICLE_FIN = "AuricleFin";
        static final String KEY_SETTINGS = "Settings";
        public static final BuilderCodec<MermaidUIEventData> CODEC = BuilderCodec.builder(
                        MermaidUIPage.MermaidUIEventData.class, MermaidUIPage.MermaidUIEventData::new
                )
                .addField(new KeyedCodec<>(KEY_CATEGORY, Codec.STRING), (entry, s) -> entry.category = s, entry -> entry.category)
                .addField(new KeyedCodec<>(KEY_TAIL_PRESETS, Codec.STRING), (entry, s) -> entry.tailPresets = s, entry -> entry.tailPresets)
                .addField(new KeyedCodec<>(KEY_TAIL_MODEL, Codec.STRING), (entry, s) -> entry.tailModel = s, entry -> entry.tailModel)
                .addField(new KeyedCodec<>(KEY_TAIL_COLOR, Codec.STRING), (entry, s) -> entry.tailColor = s, entry -> entry.tailColor)
                .addField(new KeyedCodec<>(KEY_OCEAN_FLUKE_COLOR, Codec.STRING), (entry, s) -> entry.oceanFlukeColor = s, entry -> entry.oceanFlukeColor)
                .addField(new KeyedCodec<>(KEY_DORSAL_FIN, Codec.STRING), (entry, s) -> entry.dorsalFin = s, entry -> entry.dorsalFin)
                .addField(new KeyedCodec<>(KEY_PECTORAL_FIN, Codec.STRING), (entry, s) -> entry.pectoralFin = s, entry -> entry.pectoralFin)
                .addField(new KeyedCodec<>(KEY_AURICLE_FIN, Codec.STRING), (entry, s) -> entry.auricleFin = s, entry -> entry.auricleFin)
                .addField(new KeyedCodec<>(KEY_SETTINGS, Codec.STRING), (entry, s) -> entry.settings = s, entry -> entry.settings)
                .build();
        private String category;
        private String tailPresets;
        private String tailModel;
        private String tailColor;
        private String oceanFlukeColor;
        private String dorsalFin;
        private String pectoralFin;
        private String auricleFin;
        private String settings;

        public MermaidUIEventData() {
        }
    }

    private static enum PlayerSettings{
        TOGGLE(0, SettingOption.CHECKBOX),
        GLOW(1, SettingOption.CHECKBOX),
        REMOVEPOTION(2, SettingOption.BUTTON);

        private final int value;
        private final String valueStr;
        private final SettingOption option;
        private PlayerSettings(int value, SettingOption option){
            this.value = value;
            this.valueStr = String.valueOf(value);
            this.option = option;
        }

        public static PlayerSettings get(int value){
            PlayerSettings settings = null;
            for(PlayerSettings setting : PlayerSettings.values()){
                if(setting.value == value){
                    settings = setting;
                }
            }

            return settings;
        }

        public static PlayerSettings get(String value){
            PlayerSettings settings = null;
            for(PlayerSettings setting : PlayerSettings.values()){
                if(setting.valueStr.equalsIgnoreCase(value)){
                    settings = setting;
                }
            }

            return settings;
        }

        public int getValue(){
            return this.value;
        }

        public SettingOption getOption(){
            return this.option;
        }
    }

    private static enum SettingOption{
        CHECKBOX,
        BUTTON;
    }
}