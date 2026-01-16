package plugin.siren.Systems;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MermaidComponent implements Component<EntityStore> {

    private List<String> cosmeticList;
    private PlayerSkinComponent originalSkin;

    public MermaidComponent(){
        this(false,false);
    }

    public MermaidComponent(boolean underwater, boolean swimming){
        cosmeticList = new ArrayList<>();
    }

    public MermaidComponent(MermaidComponent other){
        this.cosmeticList = other.cosmeticList;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new MermaidComponent(this);
    }

    public List<String> getCosmeticList(){
        return cosmeticList;
    }

    public PlayerSkinComponent getOriginalSkin(){
        return originalSkin;
    }

    public void setCosmeticList(List<String> cosmeticList){
        this.cosmeticList = cosmeticList;
    }

    public void setOriginalSkin(PlayerSkinComponent playerSkin){
        originalSkin = playerSkin;
    }

    public PlayerSkin getCosmetics(){
        PlayerSkin skin = new PlayerSkin();

        skin.cape = "";//cosmeticList.get(0);
        skin.skinFeature = cosmeticList.get(1);
        skin.face = cosmeticList.get(2);
        skin.ears = cosmeticList.get(3);
        skin.earAccessory = cosmeticList.get(4);
        skin.bodyCharacteristic = cosmeticList.get(5);
        skin.eyebrows = cosmeticList.get(6);
        skin.eyes = cosmeticList.get(7);
        skin.faceAccessory = cosmeticList.get(8);
        skin.facialHair = cosmeticList.get(9);
        skin.gloves = cosmeticList.get(10);
        skin.haircut = cosmeticList.get(11);
        skin.headAccessory = cosmeticList.get(12);
        skin.mouth = cosmeticList.get(13);
        skin.overpants = cosmeticList.get(14);
        skin.overtop = cosmeticList.get(15);
        skin.pants = cosmeticList.get(16);
        skin.shoes = cosmeticList.get(17);
        skin.undertop = cosmeticList.get(18);
        skin.underwear = cosmeticList.get(19);

        Universe.get().sendMessage(Message.raw("Get Cosmetic"));

        return skin;
    }

    public void saveCosmetics(PlayerSkin skin){
        cosmeticList.clear();

        cosmeticList.add(skin.cape);
        cosmeticList.add(skin.skinFeature);
        cosmeticList.add(skin.face);
        cosmeticList.add(skin.ears);
        cosmeticList.add(skin.earAccessory);
        cosmeticList.add(skin.bodyCharacteristic);
        cosmeticList.add(skin.eyebrows);
        cosmeticList.add(skin.eyes);
        cosmeticList.add(skin.faceAccessory);
        cosmeticList.add(skin.facialHair);
        cosmeticList.add(skin.gloves);
        cosmeticList.add(skin.haircut);
        cosmeticList.add(skin.headAccessory);
        cosmeticList.add(skin.mouth);
        cosmeticList.add(skin.overpants);
        cosmeticList.add(skin.overtop);
        cosmeticList.add(skin.pants);
        cosmeticList.add(skin.shoes);
        cosmeticList.add(skin.undertop);
        cosmeticList.add(skin.underwear);
    }
}
