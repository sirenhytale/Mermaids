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

public class MermaidUIPage extends InteractiveCustomUIPage<MermaidUIPage.MermaidSelectData> {

    public static class MermaidSelectData {

        public static final BuilderCodec<MermaidSelectData> CODEC = BuilderCodec.builder(MermaidSelectData.class, MermaidSelectData::new)
                .append(new KeyedCodec<>("@MermaidTail", Codec.STRING),
                        (MermaidSelectData merData, String mTail) -> merData.mermaidTail = mTail,
                        (MermaidSelectData merData) -> merData.mermaidTail)
                .add()
                .append(new KeyedCodec<>("@TailColor", Codec.STRING),
                        (MermaidSelectData merData, String tColor) -> merData.tailColor = tColor,
                        (MermaidSelectData merData) -> merData.tailColor)
                .add()
                .build();

        private String mermaidTail = "ModelTail";
        private String tailColor = "TailColor";

        public String getMermaidTail(){
            return mermaidTail;
        }

        public String getTailColor() {
            return tailColor;
        }
    }

    public MermaidUIPage(@Nonnull PlayerRef playerRef){
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, MermaidSelectData.CODEC);
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder command, @Nonnull UIEventBuilder event, @Nonnull Store<EntityStore> store){
        command.append("Pages/MermaidUIPage.ui");

        event.addEventBinding(CustomUIEventBindingType.Activating, "#OriginalMermaidSmallFinButton", new EventData().append("@MermaidTail", "#MermaidPlayer.Value"));
        event.addEventBinding(CustomUIEventBindingType.Activating, "#OriginalMermaidBigFinButton", new EventData().append("@MermaidTail", "#MermaidBigFinPlayer.Value"));
        event.addEventBinding(CustomUIEventBindingType.Activating, "#MermaidV2Button", new EventData().append("@MermaidTail", "#MermaidV2.Value"));

        event.addEventBinding(CustomUIEventBindingType.Activating, "#OrangeTailColorButton", new EventData().append("@TailColor", "#OrangeTailColor.Value"));
        event.addEventBinding(CustomUIEventBindingType.Activating, "#YellowTailColorButton", new EventData().append("@TailColor", "#YellowTailColor.Value"));
        event.addEventBinding(CustomUIEventBindingType.Activating, "#RedTailColorButton", new EventData().append("@TailColor", "#RedTailColor.Value"));
        event.addEventBinding(CustomUIEventBindingType.Activating, "#BlueTailColorButton", new EventData().append("@TailColor", "#BlueTailColor.Value"));
        event.addEventBinding(CustomUIEventBindingType.Activating, "#DBlueTailColorButton", new EventData().append("@TailColor", "#DBlueTailColor.Value"));
        event.addEventBinding(CustomUIEventBindingType.Activating, "#PurpleTailColorButton", new EventData().append("@TailColor", "#PurpleTailColor.Value"));
        event.addEventBinding(CustomUIEventBindingType.Activating, "#DPurpleTailColorButton", new EventData().append("@TailColor", "#DPurpleTailColor.Value"));
        event.addEventBinding(CustomUIEventBindingType.Activating, "#PinkTailColorButton", new EventData().append("@TailColor", "#PinkTailColor.Value"));
        event.addEventBinding(CustomUIEventBindingType.Activating, "#LimeTailColorButton", new EventData().append("@TailColor", "#LimeTailColor.Value"));
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull MermaidSelectData data){
        Player player = store.getComponent(ref, Player.getComponentType());

        String mermaidTail = data.getMermaidTail();
        String tailColor = data.getTailColor();

        MermaidComponent mermaid = store.getComponent(ref, Mermaids.get().getMermaidComponentType());
        MermaidSettings mermaidSettings = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());

        if(!mermaidTail.equals("ModelTail")) {
            String oldMermaidTail = mermaidSettings.getMermaidTail();

            mermaidSettings.setMermaidTail(mermaidTail);

            String msgMerTail = "ERROR GETTING TAIL";
            if (mermaidTail.equals("MermaidPlayer")) {
                msgMerTail = "Small Fin";
            } else if (mermaidTail.equals("MermaidBigFinPlayer")) {
                msgMerTail = "Big Fin";
            } else if (mermaidTail.equals("MermaidV2")) {
                msgMerTail = "ModelV2";
            }

            player.sendMessage(Message.raw("You have selected the " + msgMerTail + " mermaid tail."));
            Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has switched the Mermaid tail to " + msgMerTail + ".");

            if (!oldMermaidTail.equals(mermaidTail) && mermaid.isMermaid()) {
                ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(mermaidTail);
                if (modelAsset == null) {
                    player.sendMessage(Message.raw("Mermaids: Error: MermaidUIPage: " + mermaidTail + " Model not found"));
                    Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidUIPage: " + mermaidTail + " Model not found.");
                } else {
                    ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), mermaid.getMermaidSkin().clone(), ref, mermaid, mermaidSettings);
                }
            }
        }else if(!tailColor.equals("TailColor")){
            String oldTailColor = mermaidSettings.getTailColor();

            mermaidSettings.setTailColor(tailColor);

            String msgTailColor = "ERROR GETTING COLOR";
            if (tailColor.equals("MermaidPlayerGrayscale")) {
                msgTailColor = "Orange";
            } else if (tailColor.equals("MermaidPlayerYellow")) {
                msgTailColor = "Yellow";
            } else if (tailColor.equals("MermaidPlayerRed")) {
                msgTailColor = "Red";
            } else if (tailColor.equals("MermaidPlayerBlue")) {
                msgTailColor = "Light Blue";
            } else if (tailColor.equals("MermaidPlayerDBlue")) {
                msgTailColor = "Blue";
            } else if (tailColor.equals("MermaidPlayerPurple")) {
                msgTailColor = "Light Purple";
            } else if (tailColor.equals("MermaidPlayerDPurple")) {
                msgTailColor = "Purple";
            } else if (tailColor.equals("MermaidPlayerPink")) {
                msgTailColor = "Pink";
            } else if (tailColor.equals("MermaidPlayerLime")) {
                msgTailColor = "Green";
            }

            player.sendMessage(Message.raw("You have selected the " + msgTailColor + " tail color."));
            Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has switched the Mermaid tail color to " + msgTailColor + ".");

            String activeMermaidTail = mermaidSettings.getMermaidTail();

            if (!oldTailColor.equals(tailColor) && mermaid.isMermaid()) {
                ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(activeMermaidTail);
                if (modelAsset == null) {
                    player.sendMessage(Message.raw("Mermaids: Error: MermaidUIPage: " + activeMermaidTail + " Model not found"));
                    Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidUIPage: " + activeMermaidTail + " Model not found.");
                } else {
                    ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), mermaid.getMermaidSkin().clone(), ref, mermaid, mermaidSettings);
                }
            }
        }else{
            player.sendMessage(Message.raw("Mermaids: Error: MermaidUIPage: Failed to get Mermaid Model and/or Tail color"));
            Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidUIPage: Failed to get Mermaid Model and/or Tail color.");
        }

        player.getPageManager().setPage(ref, store, Page.None);
    }
}