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
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;

import javax.annotation.Nonnull;

public class MermaidUIPage extends InteractiveCustomUIPage<MermaidUIPage.MermaidSelectData> {

    public static class MermaidSelectData {
        //public String mermaidTail;// = "MermaidPlayer";

        public static final BuilderCodec<MermaidSelectData> CODEC = BuilderCodec.builder(MermaidSelectData.class, MermaidSelectData::new)
                .append(new KeyedCodec<>("@MermaidTail", Codec.STRING),
                        (MermaidSelectData merData, String mTail) -> merData.mermaidTail = mTail,
                        (MermaidSelectData merData) -> merData.mermaidTail)
                .add()
                .build();

        private String mermaidTail = "MermaidBigFinPlayer";

        public String getMermaidTail(){
            return mermaidTail;
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
    }

    @Override
    public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull MermaidSelectData data){
        Player player = store.getComponent(ref, Player.getComponentType());

        String mermaidTail = data.getMermaidTail();//data.mermaidTail != null && !data.mermaidTail.isEmpty() ? data.mermaidTail : "MermaidPlayer";

        MermaidComponent mermaid = store.getComponent(ref, Mermaids.get().getMermaidComponentType());
        mermaid.setMermaidTail(mermaidTail);

        String msgMerTail = "ERROR GETTING TAIL";
        if(mermaidTail.equals("MermaidPlayer")){
            msgMerTail = "Small Fin";
        }else if(mermaidTail.equals("MermaidBigFinPlayer")){
            msgMerTail = "Big Fin";
        }

        player.sendMessage(Message.raw("You have selected the " + msgMerTail + " mermaid tail."));

        player.getPageManager().setPage(ref, store, Page.None);
    }
}