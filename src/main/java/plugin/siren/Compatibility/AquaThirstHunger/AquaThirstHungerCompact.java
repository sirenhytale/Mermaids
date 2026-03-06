package plugin.siren.Compatibility.AquaThirstHunger;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import mx.jume.aquahunger.HHMUtils;
import mx.jume.aquahunger.components.ThirstComponent;

public class AquaThirstHungerCompact {

    private AquaThirstHungerCompact() {}

    public static void setMaxThirst(Store<EntityStore> store, Ref<EntityStore> ref){
        HHMUtils.setPlayerThirstLevel(ref, store, ThirstComponent.maxThirstLevel);
    }
}
