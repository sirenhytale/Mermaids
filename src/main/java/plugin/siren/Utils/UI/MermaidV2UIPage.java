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

public class MermaidV2UIPage extends InteractiveCustomUIPage<MermaidV2UIPage.MermaidSelectData> {

    public static class MermaidSelectData {

        public static final BuilderCodec<MermaidSelectData> CODEC = BuilderCodec.builder(MermaidSelectData.class, MermaidSelectData::new)
                .append(new KeyedCodec<>("@MermaidTail", Codec.STRING),
                        (MermaidSelectData merData, String mTail) -> merData.mermaidTail = mTail,
                        (MermaidSelectData merData) -> merData.mermaidTail)
                .add()
                .build();

        private String mermaidTail = "ModelTail";

        public String getMermaidTail(){
            return mermaidTail;
        }
    }

    public MermaidV2UIPage(@Nonnull PlayerRef playerRef){
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, MermaidSelectData.CODEC);
    }

    @Override
    public void build(@Nonnull Ref<EntityStore> ref, @Nonnull UICommandBuilder command, @Nonnull UIEventBuilder event, @Nonnull Store<EntityStore> store){
        command.append("Pages/MermaidV2UIPage.ui");

        event.addEventBinding(CustomUIEventBindingType.Activating, "#MermaidV2Button", new EventData().append("@MermaidTail", "#MermaidV2.Value"));
        event.addEventBinding(CustomUIEventBindingType.Activating, "#OriginalMermaidBigFinButton", new EventData().append("@MermaidTail", "#MermaidBigFinPlayer.Value"));
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull MermaidSelectData data){
        Player player = store.getComponent(ref, Player.getComponentType());

        String mermaidTail = data.getMermaidTail();

        MermaidComponent mermaid = store.getComponent(ref, Mermaids.get().getMermaidComponentType());
        MermaidSettings mermaidSettings = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());

        if(!mermaidTail.equals("ModelTail")) {
            String oldMermaidTail = mermaidSettings.getMermaidTail();

            mermaidSettings.setMermaidTail(mermaidTail);

            String msgMerTail = "ERROR GETTING TAIL";
            if (mermaidTail.equals("MermaidBigFinPlayer")) {
                msgMerTail = "Original Model";
            } else if (mermaidTail.equals("MermaidV2")) {
                msgMerTail = "ModelV2";
            }

            player.sendMessage(Message.raw("You have selected the " + msgMerTail + " mermaid tail."));
            Mermaids.LOGGER.atInfo().log(player.getDisplayName() + " has switched the Mermaid tail to " + msgMerTail + ".");

            if (!oldMermaidTail.equals(mermaidTail) && mermaid.isMermaid()) {
                ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset(mermaidTail);
                if (modelAsset == null) {
                    player.sendMessage(Message.raw("Mermaids: Error: MermaidV2UIPage: " + mermaidTail + " Model not found"));
                    Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidV2UIPage: " + mermaidTail + " Model not found.");
                } else {
                    ModelHelper.applySkin(Model.createUnitScaleModel(modelAsset), mermaid.getMermaidSkin().clone(), ref, mermaid, mermaidSettings);
                }
            }
        }else{
            player.sendMessage(Message.raw("Mermaids: Error: MermaidV2UIPage: Failed to get Mermaid Model"));
            Mermaids.LOGGER.atSevere().log(player.getDisplayName() + " had an error of getting the Mermaid Model. Error: MermaidV2UIPage: Failed to get Mermaid Model.");
        }

        player.getPageManager().setPage(ref, store, Page.None);
    }
}