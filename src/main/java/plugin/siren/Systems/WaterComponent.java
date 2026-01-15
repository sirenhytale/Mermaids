package plugin.siren.Systems;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nullable;

public class WaterComponent implements Component<EntityStore> {
    private boolean underwater;
    private boolean swimming;

    private float elapsedTime;

    public WaterComponent(){
        this(false,false);
    }

    public WaterComponent(boolean underwater, boolean swimming){
        this.underwater = underwater;
        this.swimming = swimming;
        this.elapsedTime = 0f;
    }

    public WaterComponent(WaterComponent other){
        this.underwater = other.underwater;
        this.swimming = other.swimming;

    }

    @Nullable
    @Override
    public Component<EntityStore> clone() {
        return new WaterComponent(this);
    }

    public boolean isUnderwater(){
        return underwater;
    }

    public boolean isSwimming(){
        return swimming;
    }

    public float getElapsedTime(){
        return this.elapsedTime;
    }

    public void setUnderwater(boolean underwater){
        this.underwater = underwater;
    }

    public void setSwimming(boolean swimming){
        this.swimming = swimming;
    }

    public void incrementTick(){
        this.elapsedTime += 1f;
    }

    public void setElapsedTime(float dt){
        this.elapsedTime = dt;
    }
}
