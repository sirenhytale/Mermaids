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
import plugin.siren.Systems.MermaidSettings;

import javax.annotation.Nonnull;

public class MermaidV1UIPage extends InteractiveCustomUIPage<MermaidV1UIPage.MermaidV1UIEventData> {
    private static final Value<String> CATEGORY_BUTTON_STYLE = Value.ref("Pages/UIGallery/CategoryButton.ui", "LabelStyle");
    private static final Value<String> CATEGORY_BUTTON_SELECTED_STYLE = Value.ref("Pages/UIGallery/CategoryButton.ui", "SelectedLabelStyle");
    private MermaidV1UIPage.Category selectedCategory = Category.MODEL;

    public MermaidV1UIPage(@Nonnull PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismiss, MermaidV1UIPage.MermaidV1UIEventData.CODEC);
    }

    @Override
    public void build(
            @Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder commandBuilder, @Nonnull UIEventBuilder eventBuilder, @Nonnull Store<EntityStore> store
    ) {
        commandBuilder.append("Pages/MermaidV1UI/MermaidUIPage.ui");
        this.buildCategoryList(commandBuilder, eventBuilder);
        this.displayCategory(this.selectedCategory, commandBuilder, eventBuilder);
    }

    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull MermaidV1UIPage.MermaidV1UIEventData data) {
        Player player = store.getComponent(ref, Player.getComponentType());

        MermaidComponent mermaid = store.getComponent(ref, Mermaids.get().getMermaidComponentType());
        MermaidSettings mermaidSettings = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());

        if(mermaid != null && mermaidSettings != null) {
            UICommandBuilder commandBuilder = new UICommandBuilder();
            UIEventBuilder eventBuilder = new UIEventBuilder();
            if (data.category != null) {
                MermaidV1UIPage.Category newCategory = MermaidV1UIPage.Category.fromId(data.category);
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
                String mermaidTailPath = "MermaidV2";
                if (data.tailModel.equalsIgnoreCase("0")) {
                    msgMerTail = "Big Fin";
                    mermaidTailPath = "MermaidBigFinPlayer";
                } else if (data.tailModel.equalsIgnoreCase("1")) {
                    msgMerTail = "Small Fin";
                    mermaidTailPath = "MermaidPlayer";
                }

                player.sendMessage(Message.raw("You have selected the " + msgMerTail + " mermaid tail."));
                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has switched the Mermaid tail to " + msgMerTail + ".");
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
                String mermaidTailColorPath = "MermaidPlayerGrayscale";
                if (data.tailColor.equalsIgnoreCase("0")) {
                    msgTailColor = "Orange";
                    mermaidTailColorPath = "MermaidPlayerGrayscale";
                } else if (data.tailColor.equalsIgnoreCase("1")) {
                    msgTailColor = "Yellow";
                    mermaidTailColorPath = "MermaidPlayerYellow";
                } else if (data.tailColor.equalsIgnoreCase("2")) {
                    msgTailColor = "Red";
                    mermaidTailColorPath = "MermaidPlayerRed";
                } else if (data.tailColor.equalsIgnoreCase("3")) {
                    msgTailColor = "Light Blue";
                    mermaidTailColorPath = "MermaidPlayerBlue";
                } else if (data.tailColor.equalsIgnoreCase("4")) {
                    msgTailColor = "Blue";
                    mermaidTailColorPath = "MermaidPlayerDBlue";
                } else if (data.tailColor.equalsIgnoreCase("5")) {
                    msgTailColor = "Light Purple";
                    mermaidTailColorPath = "MermaidPlayerPurple";
                } else if (data.tailColor.equalsIgnoreCase("6")) {
                    msgTailColor = "Purple";
                    mermaidTailColorPath = "MermaidPlayerDPurple";
                } else if (data.tailColor.equalsIgnoreCase("7")) {
                    msgTailColor = "Pink";
                    mermaidTailColorPath = "MermaidPlayerPink";
                } else if (data.tailColor.equalsIgnoreCase("8")) {
                    msgTailColor = "Green";
                    mermaidTailColorPath = "MermaidPlayerLime";
                }

                player.sendMessage(Message.raw("You have selected the " + msgTailColor + " tail color."));
                if(Mermaids.getConfig().get().ifConsoleLogs()) {
                    Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has switched the Mermaid tail color to " + msgTailColor + ".");
                }

                String oldTailColor = mermaidSettings.getTailColor();
                mermaidSettings.setTailColor(mermaidTailColorPath);

                String activeMermaidTail = mermaidSettings.getMermaidTail();
                if (!oldTailColor.equals(mermaidTailColorPath) && mermaid.isMermaid()) {
                    ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(activeMermaidTail);
                    if (modelAsset == null) {
                        player.sendMessage(Message.raw("Mermaids: Error: MermaidV1UIPage: " + activeMermaidTail + " Model not found"));
                        Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidV1UIPage: " + activeMermaidTail + " Model not found.");
                    } else {
                        ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), mermaid.getMermaidSkin().clone(), ref, mermaid, mermaidSettings);
                    }
                }

                this.sendUpdate(commandBuilder, eventBuilder, false);
            }
        }else{
            Mermaids.LOGGER.atFine().log("Mermaids: Error: MermaidV1UIPage: Failed to load Mermaid Component and/or Mermaid Settings Component : handleDataEvent");
        }
    }

    private void buildCategoryList(@Nonnull UICommandBuilder commandBuilder, @Nonnull UIEventBuilder eventBuilder) {
        commandBuilder.clear("#CategoryList");

        for (int i = 0; i < MermaidV1UIPage.Category.values().length; i++) {
            MermaidV1UIPage.Category category = MermaidV1UIPage.Category.values()[i];
            commandBuilder.append("#CategoryList", "Pages/UIGallery/CategoryButton.ui");
            commandBuilder.set("#CategoryList[" + i + "].TextSpans", Message.translation(category.getNameKey()));
            if (category == this.selectedCategory) {
                commandBuilder.set("#CategoryList[" + i + "].Style", CATEGORY_BUTTON_SELECTED_STYLE);
            }

            eventBuilder.addEventBinding(CustomUIEventBindingType.Activating, "#CategoryList[" + i + "]", EventData.of("Category", category.getId()));
        }
    }

    private void displayCategory(@Nonnull MermaidV1UIPage.Category category, @Nonnull UICommandBuilder commandBuilder, @Nonnull UIEventBuilder eventBuilder) {
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
        }
    }

    private static enum Category {
        MODEL(
                "model",
                "server.customUI.mermaid.category.model",
                "Pages/MermaidV1UI/Categories/TailModelContent.ui",
                2
        ),
        COLOR(
                "color",
                "server.customUI.mermaid.category.color",
                "Pages/MermaidV1UI/Categories/TailColorContent.ui",
                9
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

        public static MermaidV1UIPage.Category fromId(String id) {
            for (MermaidV1UIPage.Category category : values()) {
                if (category.id.equals(id)) {
                    return category;
                }
            }

            return MODEL;
        }
    }

    public static class MermaidV1UIEventData {
        static final String KEY_CATEGORY = "Category";
        static final String KEY_TAIL_MODEL = "TailModel";
        static final String KEY_TAIL_COLOR = "TailColor";
        public static final BuilderCodec<MermaidV1UIEventData> CODEC = BuilderCodec.builder(
                        MermaidV1UIPage.MermaidV1UIEventData.class, MermaidV1UIPage.MermaidV1UIEventData::new
                )
                .addField(new KeyedCodec<>("Category", Codec.STRING), (entry, s) -> entry.category = s, entry -> entry.category)
                .addField(new KeyedCodec<>("TailModel", Codec.STRING), (entry, s) -> entry.tailModel = s, entry -> entry.tailModel)
                .addField(new KeyedCodec<>("TailColor", Codec.STRING), (entry, s) -> entry.tailColor = s, entry -> entry.tailColor)
                .build();
        private String category;
        private String tailModel;
        private String tailColor;

        public MermaidV1UIEventData() {
        }
    }
}