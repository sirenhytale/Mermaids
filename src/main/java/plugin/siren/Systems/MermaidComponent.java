package plugin.siren.Systems;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nullable;

public class MermaidComponent implements Component<EntityStore> {

    private boolean mermaid;
    private boolean toggleMermaid;
    private boolean updateStats; //not used

    private String mermaidTail;

    private PlayerSkin skin;
    private Component<EntityStore> movementManager;
    private Component<EntityStore> playerSkinComponent;
    private Component<EntityStore> modelComponent;

    public MermaidComponent(){
        this(new PlayerSkin());
    }

    public MermaidComponent(PlayerSkin skin){
        this.mermaid = false;
        this.toggleMermaid = true;
        this.updateStats = false;
        this.skin = skin;
        this.mermaidTail = "MermaidBigFinPlayer";
    }

    public MermaidComponent(MermaidComponent other){
        this.mermaid = other.mermaid;
        this.toggleMermaid = other.toggleMermaid;
        this.updateStats = other.updateStats;
        this.skin = other.skin;
        this.mermaidTail = other.mermaidTail;
        this.movementManager = other.movementManager;
        this.playerSkinComponent = other.playerSkinComponent;
        this.modelComponent = other.modelComponent;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new MermaidComponent(this);
    }

    public void setMermaid(boolean mermaid){
        this.mermaid = mermaid;
    }

    public void setToggleMermaid(boolean toggle){
        this.toggleMermaid = toggle;
    }

    public void setUpdateStats(boolean updateStats){
        this.updateStats = updateStats;
    }

    public void setMermaidTail(String mermaidTail){
        this.mermaidTail = mermaidTail;
    }

    public void setPlayerSkin(PlayerSkin skin){
        this.skin = skin;
    }

    public void setMovementManager(Component<EntityStore> movementManager){
        this.movementManager = movementManager;
    }

    public void setPlayerSkinComponent(Component<EntityStore> playerSkinComponent){
        this.playerSkinComponent = playerSkinComponent;
    }

    public void setModelComponent(Component<EntityStore> modelComponent){
        this.modelComponent = modelComponent;
    }

    public boolean isMermaid(){
        return mermaid;
    }

    public boolean getToggleMermaid(){
        return toggleMermaid;
    }

    public boolean getUpdateStats(){
        return updateStats;
    }

    public String getMermaidTail(){
        return mermaidTail;
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

    public Component<EntityStore> getMovementManager(){
        return movementManager;
    }

    public Component<EntityStore> getPlayerSkinComponent(){
        return playerSkinComponent;
    }

    public Component<EntityStore> getModelComponent(){
        return modelComponent;
    }

}
