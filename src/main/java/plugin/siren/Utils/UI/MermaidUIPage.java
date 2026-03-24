package plugin.siren.Utils.UI;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.protocol.packets.interface_.Page;
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
import plugin.siren.Utils.Cosmetics.MermaidCosmeticSkin;
import plugin.siren.Utils.Cosmetics.MermaidCosmeticType;

import javax.annotation.Nonnull;

public class MermaidUIPage extends InteractiveCustomUIPage<MermaidUIPage.MermaidUIEventData> {
    private static final Value<String> CATEGORY_BUTTON_STYLE = Value.ref("Pages/UIGallery/CategoryButton.ui", "LabelStyle");
    private static final Value<String> CATEGORY_BUTTON_SELECTED_STYLE = Value.ref("Pages/UIGallery/CategoryButton.ui", "SelectedLabelStyle");
    private MermaidUIPage.Category selectedCategory = Category.MODEL;

    public MermaidUIPage(@Nonnull PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismiss, MermaidUIPage.MermaidUIEventData.CODEC);
    }

    @Override
    public void build(
            @Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder commandBuilder, @Nonnull UIEventBuilder eventBuilder, @Nonnull Store<EntityStore> store
    ) {
        commandBuilder.append("Pages/MermaidUI/MermaidUIPage.ui");
        this.buildCategoryList(commandBuilder, eventBuilder);
        this.displayCategory(this.selectedCategory, commandBuilder, eventBuilder);
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
                    commandBuilder.set("#CategoryList[" + oldIndex + "].Style", CATEGORY_BUTTON_STYLE);
                    int newIndex = newCategory.ordinal();
                    commandBuilder.set("#CategoryList[" + newIndex + "].Style", CATEGORY_BUTTON_SELECTED_STYLE);
                    this.selectedCategory = newCategory;
                    this.displayCategory(this.selectedCategory, commandBuilder, eventBuilder);
                }

                this.sendUpdate(commandBuilder, eventBuilder, false);
            } else if (data.tailModel != null) {
                boolean closeUI = false;

                String msgMerTail = "ERROR GETTING TAIL";
                String mermaidTailPath = mermaidSettings.getDefaultMermaidTail();
                if (data.tailModel.equalsIgnoreCase("0")) {
                    msgMerTail = "Mermaid Model";
                    mermaidTailPath = "Mermaids_Mermaid";
                }/* else if (data.tailModel.equalsIgnoreCase("1")) {
                    msgMerTail = "Old Mermaid Model";
                    mermaidTailPath = "MermaidBigFinPlayer";
                    closeUI = true;
                }*/

                Message playerMessage = Message.translation("server.customUI.mermaids.mermaidui.category.model.playerMsg.modify").param("model", msgMerTail);
                player.sendMessage(playerMessage);

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    String consoleMessage = Message.translation("customUI.mermaids.mermaidui.category.model.consoleMsg.modify").param("username", player.getDisplayName()).param("model", msgMerTail).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }

                String oldMermaidTail = mermaidSettings.getMermaidTail();
                mermaidSettings.setMermaidTail(mermaidTailPath);

                if (!oldMermaidTail.equals(mermaidTailPath) && mermaid.isMermaid()) {
                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(mermaidTailPath);
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: MermaidV2UIPage: " + mermaidTailPath + " Model not found"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidV2UIPage: " + mermaidTailPath + " Model not found.");
                    } else {
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), mermaid.getMermaidSkin().clone(), ref, mermaid, mermaidSettings);
                    }
                }

                this.sendUpdate(commandBuilder, eventBuilder, false);

                if(closeUI){
                    player.getPageManager().setPage(ref, store, Page.None);
                }
            } else if (data.tailColor != null) {
                String msgTailColor = "ERROR GETTING COLOR";
                String mermaidTailColorPath = mermaidSettings.getDefaultTailColor();
                if (data.tailColor.equalsIgnoreCase("0")) {
                    msgTailColor = "Orange";
                    mermaidTailColorPath = "Mermaids_Mermaid_Orange_Texture";

                    mermaidSettings.setCosmeticColor(MermaidCosmeticSkin.TextureColor.ORANGE);
                } else if (data.tailColor.equalsIgnoreCase("1")) {
                    msgTailColor = "Pink";
                    mermaidTailColorPath = "Mermaids_Mermaid_Pink_Texture";

                    mermaidSettings.setCosmeticColor(MermaidCosmeticSkin.TextureColor.PINK);
                } else if (data.tailColor.equalsIgnoreCase("2")) {
                    msgTailColor = "Purple";
                    mermaidTailColorPath = "Mermaids_Mermaid_Purple_Texture";

                    mermaidSettings.setCosmeticColor(MermaidCosmeticSkin.TextureColor.PURPLE);
                } else if (data.tailColor.equalsIgnoreCase("3")) {
                    msgTailColor = "Rose";
                    mermaidTailColorPath = "Mermaids_Mermaid_Rose_Texture";

                    mermaidSettings.setCosmeticColor(MermaidCosmeticSkin.TextureColor.ROSE);
                } else if (data.tailColor.equalsIgnoreCase("4")) {
                    msgTailColor = "Lime";
                    mermaidTailColorPath = "Mermaids_Mermaid_Lime_Texture";

                    mermaidSettings.setCosmeticColor(MermaidCosmeticSkin.TextureColor.LIME);
                } else if (data.tailColor.equalsIgnoreCase("5")) {
                    msgTailColor = "Blue";
                    mermaidTailColorPath = "Mermaids_Mermaid_Blue_Texture";

                    mermaidSettings.setCosmeticColor(MermaidCosmeticSkin.TextureColor.BLUE);
                } else if (data.tailColor.equalsIgnoreCase("6")) {
                    msgTailColor = "Aqua";
                    mermaidTailColorPath = "Mermaids_Mermaid_Aqua_Texture";

                    mermaidSettings.setCosmeticColor(MermaidCosmeticSkin.TextureColor.AQUA);
                } else if (data.tailColor.equalsIgnoreCase("7")) {
                    msgTailColor = "Cyan";
                    mermaidTailColorPath = "Mermaids_Mermaid_Cyan_Texture";

                    mermaidSettings.setCosmeticColor(MermaidCosmeticSkin.TextureColor.CYAN);
                }

                Message playerMessage = Message.translation("server.customUI.mermaids.mermaidui.category.color.playerMsg.modify").param("color", msgTailColor);
                player.sendMessage(playerMessage);

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    String consoleMessage = Message.translation("customUI.mermaids.mermaidui.category.color.consoleMsg.modify").param("username", player.getDisplayName()).param("color", msgTailColor).getAnsiMessage();
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
                    dorsalFin = 0;
                }

                Message playerMessage = Message.translation("server.customUI.mermaids.mermaidui.category.dorsal_fin.playerMsg.modify").param("fin", dorsalFinMsg);
                player.sendMessage(playerMessage);

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    String consoleMessage = Message.translation("customUI.mermaids.mermaidui.category.dorsal_fin.consoleMsg.modify").param("username", player.getDisplayName()).param("fin", dorsalFinMsg).getAnsiMessage();
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
            } else if (data.pelvicFin != null) {
                String pelvicFinMsg = "ERROR PELVIC FIN";
                int pelvicFin = -1;
                if (data.pelvicFin.equalsIgnoreCase("0")) {
                    pelvicFinMsg = "none";
                    pelvicFin = -1;
                } else if (data.pelvicFin.equalsIgnoreCase("1")) {
                    pelvicFinMsg = "top fins";
                    pelvicFin = 1; //NEED TO SET
                }

                Message playerMessage = Message.translation("server.customUI.mermaids.mermaidui.category.pelvic_fin.playerMsg.modify").param("fin", pelvicFinMsg);
                player.sendMessage(playerMessage);

                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    String consoleMessage = Message.translation("customUI.mermaids.mermaidui.category.pelvic_fin.consoleMsg.modify").param("username", player.getDisplayName()).param("fin", pelvicFinMsg).getAnsiMessage();
                    Mermaids.LOGGER.atInfo().log(consoleMessage);
                }

                int oldCosmeticValue = mermaidSettings.getMermaidCosmeticValue(MermaidCosmeticType.PELVIC_FIN);

                mermaidSettings.setMermaidCosmetic(MermaidCosmeticType.PELVIC_FIN, pelvicFin);

                if (oldCosmeticValue != pelvicFin && mermaid.isMermaid()) {
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
            }
        }else{
            Mermaids.LOGGER.atFine().log("Mermaids: Error: MermaidUIPage: Failed to load Mermaid Component and/or Mermaid Settings Component : handleDataEvent");
        }
    }

    private void buildCategoryList(@Nonnull UICommandBuilder commandBuilder, @Nonnull UIEventBuilder eventBuilder) {
        commandBuilder.clear("#CategoryList");

        for (int i = 0; i < MermaidUIPage.Category.values().length; i++) {
            MermaidUIPage.Category category = MermaidUIPage.Category.values()[i];
            commandBuilder.append("#CategoryList", "Pages/UIGallery/CategoryButton.ui");
            commandBuilder.set("#CategoryList[" + i + "].TextSpans", Message.translation(category.getNameKey()));
            if (category == this.selectedCategory) {
                commandBuilder.set("#CategoryList[" + i + "].Style", CATEGORY_BUTTON_SELECTED_STYLE);
            }

            eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#CategoryList[" + i + "]", EventData.of("Category", category.getId()));
        }
    }

    private void displayCategory(@Nonnull MermaidUIPage.Category category, @Nonnull UICommandBuilder commandBuilder, @Nonnull UIEventBuilder eventBuilder) {
        commandBuilder.set("#CategoryTitle.TextSpans", Message.translation(category.getNameKey()));
        commandBuilder.clear("#CategoryContent");
        commandBuilder.append("#CategoryContent", category.getContentPath());

        if(category.id.equalsIgnoreCase("model")) {
            for (int i = 0; i < category.getTailSelectionCount(); i++) {
                String selector = "#CategoryContent #TailModel" + i + " #Selector";
                eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, selector, EventData.of("TailModel", String.valueOf(i)));
            }
        } else if(category.id.equalsIgnoreCase("color")) {
            for (int i = 0; i < category.getTailSelectionCount(); i++) {
                String selector = "#CategoryContent #TailColor" + i + " #Selector";
                eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, selector, EventData.of("TailColor", String.valueOf(i)));
            }
        } else if(category.id.equalsIgnoreCase("dorsal_fin")) {
            for (int i = 0; i < category.getTailSelectionCount(); i++) {
                String selector = "#CategoryContent #DorsalFin" + i + " #Selector";
                eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, selector, EventData.of("DorsalFin", String.valueOf(i)));
            }
        } else if(category.id.equalsIgnoreCase("pelvic_fin")) {
            for (int i = 0; i < category.getTailSelectionCount(); i++) {
                String selector = "#CategoryContent #PelvicFin" + i + " #Selector";
                eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, selector, EventData.of("PelvicFin", String.valueOf(i)));
            }
        }
    }

    private static enum Category {
        MODEL(
                "model",
                "server.customUI.mermaids.mermaidui.category.model",
                "Pages/MermaidUI/Categories/TailModelContent.ui",
                1
        ),
        COLOR(
                "color",
                "server.customUI.mermaids.mermaidui.category.color",
                "Pages/MermaidUI/Categories/TailColorContent.ui",
                8
        ),
        DORSAL_FIN(
                "dorsal_fin",
                "server.customUI.mermaids.mermaidui.category.dorsal_fin",
                "Pages/MermaidUI/Categories/DorsalFinContent.ui",
                2
        ),
        PELVIC_FIN(
                "pelvic_fin",
                "server.customUI.mermaids.mermaidui.category.pelvic_fin",
                "Pages/MermaidUI/Categories/PelvicFinContent.ui",
                2
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

            return MODEL;
        }
    }

    public static class MermaidUIEventData {
        static final String KEY_CATEGORY = "Category";
        static final String KEY_TAIL_MODEL = "TailModel";
        static final String KEY_TAIL_COLOR = "TailColor";
        static final String KEY_DORSAL_FIN = "DorsalFin";
        static final String KEY_PELVIC_FIN = "PelvicFin";
        public static final BuilderCodec<MermaidUIEventData> CODEC = BuilderCodec.builder(
                        MermaidUIPage.MermaidUIEventData.class, MermaidUIPage.MermaidUIEventData::new
                )
                .addField(new KeyedCodec<>("Category", Codec.STRING), (entry, s) -> entry.category = s, entry -> entry.category)
                .addField(new KeyedCodec<>("TailModel", Codec.STRING), (entry, s) -> entry.tailModel = s, entry -> entry.tailModel)
                .addField(new KeyedCodec<>("TailColor", Codec.STRING), (entry, s) -> entry.tailColor = s, entry -> entry.tailColor)
                .addField(new KeyedCodec<>("DorsalFin", Codec.STRING), (entry, s) -> entry.dorsalFin = s, entry -> entry.dorsalFin)
                .addField(new KeyedCodec<>("PelvicFin", Codec.STRING), (entry, s) -> entry.pelvicFin = s, entry -> entry.pelvicFin)
                .build();
        private String category;
        private String tailModel;
        private String tailColor;
        private String dorsalFin;
        private String pelvicFin;

        public MermaidUIEventData() {
        }
    }
}