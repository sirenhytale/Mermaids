package plugin.siren.Compatibility.OrbisOrigins;

import com.hexvane.orbisorigins.data.PlayerSpeciesData;
import com.hexvane.orbisorigins.species.*;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidSettingsComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OrbisOriginsRegistry {
    private static Runnable runnableCheckForMermaidSpecies = new Runnable() {
        @Override
        public void run() {
            List<PlayerRef> onlinePlayerRefs = Universe.get().getPlayers();
            if(!onlinePlayerRefs.isEmpty()) {
                for (PlayerRef playerRef : onlinePlayerRefs) {
                    World world = Universe.get().getWorld(playerRef.getWorldUuid());
                    if (world != null) {
                        world.execute(() -> {
                            Ref<EntityStore> ref = playerRef.getReference();
                            Store<EntityStore> store = ref.getStore();

                            checkForMermaidSpecies(store, world, ref);
                        });
                    }
                }
            }
        }
    };

    private OrbisOriginsRegistry(){}

    public static void register() {
        int version = 1;
        String id = "mermaid";
        String displayName = "Mermaid";
        String displayNameKey = "species.mermaid.name";
        String modelBaseName = "Player";
        List<String> variants = new ArrayList<>();
        variants.add("Mermaid");
        List<SpeciesVariantData> variantsV2 = null;
        String description = "A mythical sea creature who has the legs as a tail of a fish.";
        String descriptionKey = "species.mermaid.desc";
        int healthModifier = 15;
        int staminaModifier = 5;
        int manaModifier = 25;
        boolean enabled = true;
        boolean usePlayerModel = true;
        boolean enableAttachmentDiscovery = true;
        Map<String, Map<String, AttachmentOption>> manualAttachments = new HashMap<>();
        Map<String, Float> eyeHeightModifiers = new HashMap<>();
        Map<String, Float> hitboxHeightModifiers = new HashMap<>();
        List<String> starterItems = new ArrayList<>();
        starterItems.add("Mermaids_Seashell_Bra");
        starterItems.add("Weapon_Spear_Fishbone");
        Map<String, Float> damageResistance = new HashMap<>();
        damageResistance.put("Physical", 0.95f);
        damageResistance.put("Magic", 0.85f);
        float modelScale = 1f;
        float sleepingRaiseHeight = 0f;
        List<SpeciesAbilityConfig> abilities = new ArrayList<>();
        List<String> selectCommands = new ArrayList<>();
        List<String> deselectCommands = new ArrayList<>();

        SpeciesData mermaidSpecies = new SpeciesData(version,id,displayName,displayNameKey,modelBaseName,variants,variantsV2,description,descriptionKey,healthModifier,staminaModifier,manaModifier,enabled,usePlayerModel,enableAttachmentDiscovery,manualAttachments,eyeHeightModifiers,hitboxHeightModifiers,starterItems,damageResistance,modelScale,sleepingRaiseHeight,abilities,selectCommands,deselectCommands);

        SpeciesRegistry.registerSpecies(mermaidSpecies);

        HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(runnableCheckForMermaidSpecies,15, 8, TimeUnit.SECONDS);

        requireForcedMermaids();
        mermaidOnlyInWater();
    }

    public static void requireForcedMermaids(){
        boolean requireForcedMermaids = !Mermaids.getOrbisOriginsConfig().get().getMermaidContent();
        Mermaids.getConfig().get().setRequireForceMermaid(requireForcedMermaids);
    }

    public static void mermaidOnlyInWater(){
        boolean onlyInWater = Mermaids.getOrbisOriginsConfig().get().ifMermaidOnlyInWater();
        Mermaids.getConfig().get().setForceMermaidOnlyInWater(onlyInWater);
    }

    public static void checkForMermaidSpecies(Store<EntityStore> store, World world, Ref<EntityStore> ref){
        PlayerSpeciesData.SpeciesSelection speciesSelection = PlayerSpeciesData.getSpeciesSelection(ref, store, world);

        MermaidSettingsComponent mermaidSettings = store.getComponent(ref, MermaidSettingsComponent.getComponentType());
        if(mermaidSettings == null){
            Mermaids.LOGGER.atSevere().log("ERROR: checkForMermaidSpecies: Mermaid Settings Component is null for OrbisOriginsRegistry");
        }else{
            if(speciesSelection != null){
                String id = speciesSelection.getSpeciesId();
                if(id.equalsIgnoreCase("mermaid")){
                    if(!mermaidSettings.isForcedMermaidOrbisOrigin()){
                        mermaidSettings.setForcedMermaidOrbisOrigin(true);
                    }
                }else{
                    if(mermaidSettings.isForcedMermaidOrbisOrigin()){
                        mermaidSettings.setForcedMermaidOrbisOrigin(false);
                    }
                }
            }else{
                if(mermaidSettings.isForcedMermaidOrbisOrigin()){
                    mermaidSettings.setForcedMermaidOrbisOrigin(false);
                }
            }
        }
    }
}
