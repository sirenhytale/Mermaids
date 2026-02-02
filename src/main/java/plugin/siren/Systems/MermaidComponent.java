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
    private boolean underwater;
    private boolean dryingOff;

    private AtomicBoolean h2oBlock;
    private AtomicBoolean rainTransform;
    private AtomicBoolean potionEffect;

    private PlayerSkin skin;
    private MovementManager movementManager;
    private PlayerSkinComponent playerSkinComponent;
    private ModelComponent modelComponent;

    private float elapsedTime;
    private float armorElapsedTime;
    private float potionElapsedTime;
    private float dryingElapsedTime;

    public MermaidComponent(){
        this(new PlayerSkin());
    }

    public MermaidComponent(PlayerSkin skin){
        this.mermaid = false;
        this.underwater = false;
        this.dryingOff = false;

        this.h2oBlock = new AtomicBoolean(false);
        this.rainTransform = new AtomicBoolean(false);
        this.potionEffect = new AtomicBoolean(false);

        this.skin = skin;

        this.elapsedTime = 0f;
        this.armorElapsedTime = 0f;
        this.potionElapsedTime = 0f;
        this.dryingElapsedTime = 0f;
    }

    public MermaidComponent(MermaidComponent other){
        this.mermaid = other.mermaid;
        this.underwater = other.underwater;
        this.dryingOff = other.dryingOff;

        this.h2oBlock = other.h2oBlock;
        this.rainTransform = other.rainTransform;
        this.potionEffect = other.potionEffect;

        this.skin = other.skin;
        this.movementManager = other.movementManager;
        this.playerSkinComponent = other.playerSkinComponent;
        this.modelComponent = other.modelComponent;

        this.elapsedTime = other.elapsedTime;
        this.armorElapsedTime = other.armorElapsedTime;
        this.potionElapsedTime = other.potionElapsedTime;
        this.dryingElapsedTime = other.dryingElapsedTime;
    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new MermaidComponent(this);
    }

    public boolean isMermaid(){
        return mermaid;
    }

    public void setMermaid(boolean mermaid){
        this.mermaid = mermaid;
    }

    public boolean isUnderwater(){
        return underwater;
    }

    public void setUnderwater(boolean underwater){
        this.underwater = underwater;
    }

    public boolean isDrying(){
        return dryingOff;
    }

    public void setDrying(boolean drying){
        this.dryingOff = drying;
    }

    public AtomicBoolean getH2OBlock(){
        return h2oBlock;
    }

    public void setH2OBlock(boolean h2o){
        h2oBlock.set(h2o);
    }

    public AtomicBoolean getRainTransform(){
        return rainTransform;
    }

    public void setRainTransform(boolean raining){
        rainTransform.set(raining);
    }

    public boolean isPotionEffectTransformation(){
        return potionEffect.get();
    }

    public void setPotionEffect(boolean potion){
        this.potionEffect.set(potion);
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

    public PlayerSkin getPlayerSkin(){
        return skin;
    }

    public void setPlayerSkin(PlayerSkin skin){
        this.skin = skin;
    }

    public MovementManager getMovementManager(){
        return (MovementManager) movementManager.clone();
    }

    public void setMovementManager(MovementManager movementManager){
        this.movementManager = movementManager;
    }

    public PlayerSkinComponent getPlayerSkinComponent(){
        return (PlayerSkinComponent) playerSkinComponent.clone();
    }

    public void setPlayerSkinComponent(PlayerSkinComponent playerSkinComponent){
        this.playerSkinComponent = playerSkinComponent;
    }

    public ModelComponent getModelComponent(){
        return (ModelComponent) modelComponent.clone();
    }

    public void setModelComponent(ModelComponent modelComponent){
        this.modelComponent = modelComponent;
    }

    public float getElapsedTime(){
        return this.elapsedTime;
    }

    public void setElapsedTime(float time){
        this.elapsedTime = time;
    }

    public void incrementTick(){
        this.elapsedTime += 1f;
    }

    public float getArmorElapsedTime(){
        return this.armorElapsedTime;
    }

    public void setArmorElapsedTime(float time){
        this.armorElapsedTime = time;
    }

    public void decrementArmorTick(){
        this.armorElapsedTime -= 1f;
    }

    public float getPotionElapsedTime(){
        return this.potionElapsedTime;
    }

    public void setPotionElapsedTime(float time){
        this.potionElapsedTime = time;
    }

    public void addPotionElapsedTime(float time){
        this.potionElapsedTime += time;
    }

    public void decrementPotionTick(){
        this.potionElapsedTime -= 1f;
    }

    public float getDryingElapsedTime(){
        return this.dryingElapsedTime;
    }

    public void setDryingElapsedTime(float time){
        this.dryingElapsedTime = time;
    }

    public void incrementDryingTick(){
        this.dryingElapsedTime += 1f;
    }

}
