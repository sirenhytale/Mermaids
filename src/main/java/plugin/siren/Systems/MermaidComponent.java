package plugin.siren.Systems;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nullable;

public class MermaidComponent implements Component<EntityStore> {

    private PlayerSkin skin;

    public MermaidComponent(){
        this(new PlayerSkin());
    }

    public MermaidComponent(PlayerSkin skin){
        this.skin = skin;
    }

    public MermaidComponent(MermaidComponent other){
        this.skin = other.skin;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new MermaidComponent(this);
    }

    public void setPlayerSkin(PlayerSkin skin){
        this.skin = skin;
    }

    public PlayerSkin getPlayerSkin(){
        return skin;
    }

    public PlayerSkin getMermaidSkin(){
        PlayerSkin mermaidSkin = skin.clone();

        mermaidSkin.shoes = null;
        mermaidSkin.pants = null;
        mermaidSkin.underwear = null;
        mermaidSkin.overpants = null;
        mermaidSkin.bodyCharacteristic = null;

        return mermaidSkin;
    }

}
