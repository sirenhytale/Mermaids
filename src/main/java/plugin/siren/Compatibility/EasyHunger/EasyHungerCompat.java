package plugin.siren.Compatibility.EasyHunger;

import com.haas.easyhunger.EasyHunger;
import com.haas.easyhunger.components.ThirstComponent;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class EasyHungerCompat {

    private EasyHungerCompat(){}

    public static void setMaxThirst(Store<EntityStore> store, Ref<EntityStore> ref){
        ThirstComponent thirst = store.getComponent(ref, ThirstComponent.getComponentType());

        if(thirst != null) {
            thirst.setThirstLevel((float) EasyHunger.get().getConfig().getMaxThirst());
        }
    }
}
