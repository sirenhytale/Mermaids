package plugin.siren.Systems;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.protocol.PlayerSkin;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSkinComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class MermaidComponent implements Component<EntityStore> {

    private boolean mermaid;
    private boolean toggleMermaid;
    private AtomicBoolean h2oBlock;

    private String mermaidTail;
    private String tailColor;

    private PlayerSkin skin;
    private MovementManager movementManager;
    private PlayerSkinComponent playerSkinComponent;
    private ModelComponent modelComponent;

    public MermaidComponent(){
        this(new PlayerSkin());
    }

    public MermaidComponent(PlayerSkin skin){
        this.mermaid = false;
        this.toggleMermaid = true;
        this.skin = skin;
        this.mermaidTail = "MermaidBigFinPlayer";
        this.tailColor = "MermaidPlayerGrayscale";
        h2oBlock = new AtomicBoolean(false);
    }

    public MermaidComponent(MermaidComponent other){
        this.mermaid = other.mermaid;
        this.toggleMermaid = other.toggleMermaid;
        this.skin = other.skin;
        this.mermaidTail = other.mermaidTail;
        this.tailColor = other.tailColor;
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

    public void setH2OBlock(boolean h2o){
        h2oBlock.set(h2o);
    }

    public void setMermaidTail(String mermaidTail){
        this.mermaidTail = mermaidTail;
    }

    public void setTailColor(String tailColor) {
        this.tailColor = tailColor;
    }

    public void setPlayerSkin(PlayerSkin skin){
        this.skin = skin;
    }

    public void setMovementManager(MovementManager movementManager){
        this.movementManager = movementManager;
    }

    public void setPlayerSkinComponent(PlayerSkinComponent playerSkinComponent){
        this.playerSkinComponent = playerSkinComponent;
    }

    public void setModelComponent(ModelComponent modelComponent){
        this.modelComponent = modelComponent;
    }

    public boolean isMermaid(){
        return mermaid;
    }

    public boolean getToggleMermaid(){
        return toggleMermaid;
    }

    public AtomicBoolean getH2OBlock(){
        return h2oBlock;
    }

    public String getMermaidTail(){
        return mermaidTail;
    }

    public String getTailColor() {
        return tailColor;
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

    public MovementManager getMovementManager(){
        return (MovementManager) movementManager.clone();
    }

    public PlayerSkinComponent getPlayerSkinComponent(){
        return (PlayerSkinComponent) playerSkinComponent.clone();
    }

    public ModelComponent getModelComponent(){
        return (ModelComponent) modelComponent.clone();
    }

}
