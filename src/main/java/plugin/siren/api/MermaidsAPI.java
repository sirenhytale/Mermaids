package plugin.siren.api;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.MermaidSettings;

public class MermaidsAPI {

    // Check to see if the reference is a mermaid.
    public static boolean isMermaid(Store<EntityStore> store, Ref<EntityStore> ref){
        MermaidComponent mermaid = store.getComponent(ref, Mermaids.get().getMermaidComponentType());
        if(mermaid == null){
            Mermaids.LOGGER.atSevere().log("ERROR: isMermaid: Mermaid Component is null for MermaidsAPI getMermaid -- returning false");
            return false;
        }else{
            return mermaid.isMermaid();
        }
    }

    // Check to see if the reference is underwater.
    public static boolean isUnderwater(Store<EntityStore> store, Ref<EntityStore> ref){
        MermaidComponent mermaid = store.getComponent(ref, Mermaids.get().getMermaidComponentType());
        if(mermaid == null){
            Mermaids.LOGGER.atSevere().log("ERROR: isUnderwater: Mermaid Component is null for MermaidsAPI getMermaid -- returning false");
            return false;
        }else{
            return mermaid.isUnderwater();
        }
    }

    // Check to see if the reference is forced to be a mermaid.
    public static boolean isForcedMermaid(Store<EntityStore> store, Ref<EntityStore> ref){
        MermaidSettings mermaidSettings = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());
        if(mermaidSettings == null){
            Mermaids.LOGGER.atSevere().log("ERROR: isForcedMermaid: Mermaid Settings Component is null for MermaidsAPI getMermaid -- returning false");
            return false;
        }else{
            return mermaidSettings.isForcedMermaid();
        }
    }

    // Force the reference to be / not be a mermaid.
    public static void setForcedMermaid(Store<EntityStore> store, Ref<EntityStore> ref, boolean forcedMermaid){
        MermaidSettings mermaidSettings = store.getComponent(ref, Mermaids.get().getMermaidSetingsComponentType());
        if(mermaidSettings == null){
            Mermaids.LOGGER.atSevere().log("ERROR: isForcedMermaid: Mermaid Settings Component is null for MermaidsAPI getMermaid -- doing nothing");
        }else{
            mermaidSettings.setForcedMermaid(forcedMermaid);
        }
    }
}
