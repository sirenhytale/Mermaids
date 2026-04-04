package plugin.siren.api;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.Systems.MermaidSettingsComponent;

public class MermaidsAPI {

    /**
     * Check to see if the reference is a mermaid.
     *
     * @param  store  The entity store, store
     * @param  ref  The entity store, reference
     * @return  boolean of rather the reference is a mermaid
     */
    public static boolean isMermaid(Store<EntityStore> store, Ref<EntityStore> ref){
        MermaidComponent mermaid = store.getComponent(ref, MermaidComponent.getComponentType());
        if(mermaid == null){
            Mermaids.LOGGER.atSevere().log("ERROR: isMermaid: Mermaid Component is null for MermaidsAPI getMermaid -- returning false");
            return false;
        }else{
            return mermaid.isMermaid();
        }
    }

    /**
     * Check to see if the reference is underwater.
     *
     * @param  store  The entity store, store
     * @param  ref  The entity store, reference
     * @return  boolean of rather the reference is underwater
     */
    public static boolean isUnderwater(Store<EntityStore> store, Ref<EntityStore> ref){
        MermaidComponent mermaid = store.getComponent(ref, MermaidComponent.getComponentType());
        if(mermaid == null){
            Mermaids.LOGGER.atSevere().log("ERROR: isUnderwater: Mermaid Component is null for MermaidsAPI getMermaid -- returning false");
            return false;
        }else{
            return mermaid.isUnderwater();
        }
    }

    /**
     * Check to see if the reference is forced to be a mermaid.
     *
     * @param  store  The entity store, store
     * @param  ref  The entity store, reference
     * @return  boolean of rather the reference is forced to be a mermaid
     */
    public static boolean isForcedMermaid(Store<EntityStore> store, Ref<EntityStore> ref){
        MermaidSettingsComponent mermaidSettings = store.getComponent(ref, MermaidSettingsComponent.getComponentType());
        if(mermaidSettings == null){
            Mermaids.LOGGER.atSevere().log("ERROR: isForcedMermaid: Mermaid Settings Component is null for MermaidsAPI getMermaid -- returning false");
            return false;
        }else{
            return mermaidSettings.isForcedMermaid();
        }
    }

    /**
     * Force the reference to be / not be a mermaid.
     *
     * @param  store  The entity store, store
     * @param  ref  The entity store, reference
     * @param  forcedMermaid  rather to force to (not) be a mermaid
     */
    public static void setForcedMermaid(Store<EntityStore> store, Ref<EntityStore> ref, boolean forcedMermaid){
        MermaidSettingsComponent mermaidSettings = store.getComponent(ref, MermaidSettingsComponent.getComponentType());
        if(mermaidSettings == null){
            Mermaids.LOGGER.atSevere().log("ERROR: setForcedMermaid: Mermaid Settings Component is null for MermaidsAPI getMermaid -- doing nothing");
        }else{
            mermaidSettings.setForcedMermaid(forcedMermaid);
        }
    }

    /**
     * Get required that all users to need the Forced Mermaid setting to be a mermaid.
     *
     * @return  boolean  require forced Mermaid transformation
     */
    public static boolean ifRequireForcedMermaid(){
        return Mermaids.getConfig().get().ifRequireForceMermaid();
    }

    /**
     * Require that all users to need the Forced Mermaid setting to be a mermaid.
     *
     * @param  requireForcedMermaid  require forced Mermaid transformation
     */
    public static void setRequireForcedMermaid(boolean requireForcedMermaid){
        Mermaids.getConfig().get().setRequireForceMermaid(requireForcedMermaid);
    }

    /**
     * Get if forced Mermaid will only work if player is in water.
     *
     * @return  boolean  require Mermaid transformation to happen only in water
     */
    public static boolean ifForcedMermaidOnlyInWater(){
        return Mermaids.getConfig().get().ifRequireForceMermaid();
    }

    /**
     * Forced Mermaid will only work if player is in water.
     *
     * @param  forcedOnlyInWater  require Mermaid transformation to happen only in water
     */
    public static void setForcedMermaidOnlyInWater(boolean forcedOnlyInWater){
        Mermaids.getConfig().get().setForceMermaidOnlyInWater(forcedOnlyInWater);
    }
}
