package plugin.siren.Compatibility.EndlessLeveling;

import com.airijko.endlessleveling.EndlessLeveling;
import com.airijko.endlessleveling.api.EndlessLevelingAPI;
import com.airijko.endlessleveling.enums.SkillAttributeType;
import com.airijko.endlessleveling.races.*;
import com.hexvane.orbisorigins.data.PlayerSpeciesData;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidSettingsComponent;
import plugin.siren.api.MermaidsAPI;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class EndlessLevelingRegistry {
    private static Runnable runnableCheckForMermaidRaces = new Runnable() {
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

                            checkForMermaidRaces(store, world, ref, playerRef);
                        });
                    }
                }
            }
        }
    };

    private EndlessLevelingRegistry(){}

    public static void register() {
        EndlessLeveling endlessLeveling = EndlessLeveling.getInstance();
        RaceManager raceManager = endlessLeveling != null ? endlessLeveling.getRaceManager() : null;

        RegisterMermaidRaces(raceManager);

        Runnable updateLanguageVariables = new Runnable() {
            @Override
            public void run() {
                EndlessLeveling runnableEndlessLeveling = EndlessLeveling.getInstance();
                RaceManager runnableRaceManager = runnableEndlessLeveling != null ? runnableEndlessLeveling.getRaceManager() : null;

                RegisterMermaidRaces(runnableRaceManager);
            }
        };

        HytaleServer.SCHEDULED_EXECUTOR.schedule(updateLanguageVariables,13, TimeUnit.SECONDS);

        HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(runnableCheckForMermaidRaces,15, 3, TimeUnit.SECONDS); //Remember to set period to at least 10!!!!!!!!!!!

        requireForcedMermaids();
        mermaidOnlyInWater();
    }

    public static void requireForcedMermaids(){
        boolean requireForcedMermaids = !Mermaids.getEndlessLeveingConfig().get().getMermaidContent();
        Mermaids.getConfig().get().setRequireForceMermaid(requireForcedMermaids);
    }

    public static void mermaidOnlyInWater(){
        boolean onlyInWater = Mermaids.getEndlessLeveingConfig().get().ifMermaidOnlyInWater();
        Mermaids.getConfig().get().setForceMermaidOnlyInWater(onlyInWater);
    }

    public static void checkForMermaidRaces(Store<EntityStore> store, World world, Ref<EntityStore> ref, PlayerRef playerRef){
        MermaidSettingsComponent mermaidSettings = store.getComponent(ref, MermaidSettingsComponent.getComponentType());
        if(mermaidSettings == null){
            Mermaids.LOGGER.atSevere().log("ERROR: checkForMermaidRaces: Mermaid Settings Component is null for EndlessLevelingRegistry");
        }else{
            String playersRaceId = EndlessLevelingAPI.get().getRaceId(playerRef.getUuid());

            if(playersRaceId.length() >= 6){
                String raceIdSub = playersRaceId.substring(0,7);

                Mermaids.LOGGER.atInfo().log("MERMAID RACEID SUB: " + raceIdSub);
                if(raceIdSub.equalsIgnoreCase("mermaid")){
                    if(!mermaidSettings.isForcedMermaidEndlessLeveling()){
                        mermaidSettings.setForcedMermaidEndlessLeveling(true);
                    }
                }else{
                    if(mermaidSettings.isForcedMermaidEndlessLeveling()){
                        mermaidSettings.setForcedMermaidEndlessLeveling(false);
                    }
                }
            }else{
                if(mermaidSettings.isForcedMermaidEndlessLeveling()){
                    mermaidSettings.setForcedMermaidEndlessLeveling(false);
                }
            }
        }
    }

    private static void RegisterMermaidRaces(RaceManager raceManager){
        RaceDefinition mermaidRace = createMermaidRace();
        RaceDefinition mermaidPathfinderRace = createMermaidPathfinderRace();
        RaceDefinition mermaidTidecallerRace = createMermaidTidecallerRace();
        RaceDefinition mermaidTidebreakerRace = createMermaidTidebreakerRace();
        RaceDefinition mermaidAbyssalRace = createMermaidAbyssalRace();
        RaceDefinition mermaidEmpressRace = createMermaidEmpressRace();

        if(raceManager != null) {
            raceManager.registerExternalRace(mermaidRace);
            raceManager.registerExternalRace(mermaidPathfinderRace);
            raceManager.registerExternalRace(mermaidTidecallerRace);
            raceManager.registerExternalRace(mermaidTidebreakerRace);
            raceManager.registerExternalRace(mermaidAbyssalRace);
            raceManager.registerExternalRace(mermaidEmpressRace);
        }else{
            Mermaids.LOGGER.atSevere().log("ERROR: RaceManager is NULL : EndlessLevelingRegister.register()");
        }
    }

    // BASE MERMAID RACE
    private static RaceDefinition createMermaidRace(){
        String id = "mermaid";
        String displayName = Message.translation("server.races.mermaid.name").getAnsiMessage();
        String description = Message.translation("server.races.mermaid.desc").getAnsiMessage();
        String icon = "Ingredient_Ice_Essence";
        String modelId = "Player";
        double modelScale = 1.0;
        boolean enabled = true;
        Map<SkillAttributeType, Double> baseAttributes = new HashMap<>();
        baseAttributes.put(SkillAttributeType.LIFE_FORCE, 98.0);
        baseAttributes.put(SkillAttributeType.STRENGTH, 0.85);
        baseAttributes.put(SkillAttributeType.DEFENSE, 0.85);
        baseAttributes.put(SkillAttributeType.HASTE, 1.0);
        baseAttributes.put(SkillAttributeType.PRECISION, 7.0);
        baseAttributes.put(SkillAttributeType.FEROCITY, 35.0);
        baseAttributes.put(SkillAttributeType.STAMINA, 7.0);
        baseAttributes.put(SkillAttributeType.FLOW, 35.0);
        baseAttributes.put(SkillAttributeType.DISCIPLINE, 98.0);
        List<Map<String, Object>> passives = new ArrayList<>();
        Map<String, Object> xpBonusMap = new HashMap<>();
        xpBonusMap.put("type", "XP_BONUS");
        xpBonusMap.put("value", 0.5);
        passives.add(xpBonusMap);
        Map<String, Object> healthRegenMap = new HashMap<>();
        healthRegenMap.put("type", "HEALTH_REGEN");
        healthRegenMap.put("value", 0.03);
        passives.add(healthRegenMap);
        Map<String, Object> adrenalineMap = new HashMap<>();
        adrenalineMap.put("type", "ADRENALINE");
        adrenalineMap.put("value", 0.56);
        adrenalineMap.put("threshold", 0.2);
        adrenalineMap.put("duration", 3);
        adrenalineMap.put("cooldown", 45);
        passives.add(adrenalineMap);
        Map<String, Object> innateAttributeGainMap = new HashMap<>();
        innateAttributeGainMap.put("type", "INNATE_ATTRIBUTE_GAIN");
        innateAttributeGainMap.put("attribute", "life_force");
        innateAttributeGainMap.put("value", 1.3);
        passives.add(innateAttributeGainMap);
        List<RacePassiveDefinition> passiveDefinitions = null;

        //Ascension
        String ascensionId = "mermaid";
        String ascensionStage = "base";
        String ascensionPath = "none";
        boolean ascensionFinalForm = false;
        boolean ascensionSingleRouteOnly = false;
        //AscensionRequirements
        int ascensionRequirementsRequiredPrestige = 0;
        Map<SkillAttributeType, Integer> ascensionRequirementsminSkillLevels = Collections.emptyMap();
        Map<SkillAttributeType, Integer> ascensionRequirementsmaxSkillLevels = Collections.emptyMap();
        List<Map<SkillAttributeType, Integer>> ascensionRequirementsminAnySkillLevels = Collections.emptyList();
        List<String> ascensionRequirementsrequiredAugments = Collections.emptyList();
        List<String> ascensionRequirementsrequiredForms = Collections.emptyList();
        List<String> ascensionRequirementsrequiredAnyForms = Collections.emptyList();
        RaceAscensionRequirements ascensionRequirements =
                new RaceAscensionRequirements(ascensionRequirementsRequiredPrestige, ascensionRequirementsminSkillLevels, ascensionRequirementsmaxSkillLevels, ascensionRequirementsminAnySkillLevels,
                        ascensionRequirementsrequiredAugments, ascensionRequirementsrequiredForms, ascensionRequirementsrequiredAnyForms);
        List<RaceAscensionPathLink> ascensionNextPaths = new ArrayList<>();
        ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_pathfinder", Message.translation("server.races.mermaidPathfinder.name").getAnsiMessage()));
        ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_tidebreaker", Message.translation("server.races.mermaidTidebreaker.name").getAnsiMessage()));

        RaceAscensionDefinition ascension =
                new RaceAscensionDefinition(ascensionId, ascensionStage, ascensionPath, ascensionFinalForm,
                        ascensionSingleRouteOnly, ascensionRequirements, ascensionNextPaths);

        return new RaceDefinition(id, displayName, description, icon, modelId, modelScale,
                enabled, baseAttributes, passives, passiveDefinitions, ascension);
    }

    // PATH 1 MERMAID ADVENTURER RACE
    // Mermaid Pathfinder adventurer tier 1
    private static RaceDefinition createMermaidPathfinderRace(){
        String id = "mermaid_pathfinder";
        String displayName = Message.translation("server.races.mermaidPathfinder.name").getAnsiMessage();
        String description = Message.translation("server.races.mermaidPathfinder.desc").getAnsiMessage();
        String icon = "Ingredient_Water_Essence";
        String modelId = "Player";
        double modelScale = 1.0;
        boolean enabled = true;
        Map<SkillAttributeType, Double> baseAttributes = new HashMap<>();
        baseAttributes.put(SkillAttributeType.LIFE_FORCE, 127.4);
        baseAttributes.put(SkillAttributeType.STRENGTH, 0.89);
        baseAttributes.put(SkillAttributeType.DEFENSE, 0.91);
        baseAttributes.put(SkillAttributeType.HASTE, 1.05);
        baseAttributes.put(SkillAttributeType.PRECISION, 8.4);
        baseAttributes.put(SkillAttributeType.FEROCITY, 42.0);
        baseAttributes.put(SkillAttributeType.STAMINA, 8.4);
        baseAttributes.put(SkillAttributeType.FLOW, 16.8);
        baseAttributes.put(SkillAttributeType.DISCIPLINE, 0.0);
        List<Map<String, Object>> passives = new ArrayList<>();
        Map<String, Object> xpBonusMap = new HashMap<>();
        xpBonusMap.put("type", "XP_BONUS");
        xpBonusMap.put("value", 0.85);
        passives.add(xpBonusMap);
        Map<String, Object> healthRegenMap = new HashMap<>();
        healthRegenMap.put("type", "HEALTH_REGEN");
        healthRegenMap.put("value", 0.04);
        passives.add(healthRegenMap);
        Map<String, Object> adrenalineMap = new HashMap<>();
        adrenalineMap.put("type", "ADRENALINE");
        adrenalineMap.put("value", 0.72);
        adrenalineMap.put("threshold", 0.2);
        adrenalineMap.put("duration", 3);
        adrenalineMap.put("cooldown", 42);
        passives.add(adrenalineMap);
        Map<String, Object> innateAttributeGainMap = new HashMap<>();
        innateAttributeGainMap.put("type", "INNATE_ATTRIBUTE_GAIN");
        innateAttributeGainMap.put("attribute", "life_force");
        innateAttributeGainMap.put("value", 1.8);
        passives.add(innateAttributeGainMap);
        List<RacePassiveDefinition> passiveDefinitions = null;

        //Ascension
        String ascensionId = "mermaid_pathfinder";
        String ascensionStage = "tier_1";
        String ascensionPath = "adventurer";
        boolean ascensionFinalForm = false;
        boolean ascensionSingleRouteOnly = true;
        //AscensionRequirements
        int ascensionRequirementsRequiredPrestige = 3;
        Map<SkillAttributeType, Integer> ascensionRequirementsminSkillLevels = new HashMap<>();
        ascensionRequirementsminSkillLevels.put(SkillAttributeType.DISCIPLINE, 125);
        ascensionRequirementsminSkillLevels.put(SkillAttributeType.HASTE, 125);
        Map<SkillAttributeType, Integer> ascensionRequirementsmaxSkillLevels = Collections.emptyMap();
        List<Map<SkillAttributeType, Integer>> ascensionRequirementsminAnySkillLevels = Collections.emptyList();
        List<String> ascensionRequirementsrequiredAugments = Collections.emptyList();
        List<String> ascensionRequirementsrequiredForms = Collections.emptyList();
        List<String> ascensionRequirementsrequiredAnyForms = Collections.emptyList();
        RaceAscensionRequirements ascensionRequirements =
                new RaceAscensionRequirements(ascensionRequirementsRequiredPrestige, ascensionRequirementsminSkillLevels, ascensionRequirementsmaxSkillLevels, ascensionRequirementsminAnySkillLevels,
                        ascensionRequirementsrequiredAugments, ascensionRequirementsrequiredForms, ascensionRequirementsrequiredAnyForms);
        List<RaceAscensionPathLink> ascensionNextPaths = new ArrayList<>();
        ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_tidecaller", Message.translation("server.races.mermaidTidecaller.name").getAnsiMessage()));

        RaceAscensionDefinition ascension =
                new RaceAscensionDefinition(ascensionId, ascensionStage, ascensionPath, ascensionFinalForm,
                        ascensionSingleRouteOnly, ascensionRequirements, ascensionNextPaths);

        return new RaceDefinition(id, displayName, description, icon, modelId, modelScale,
                enabled, baseAttributes, passives, passiveDefinitions, ascension);
    }

    // Mermaid Tidecaller adventurer tier 2
    private static RaceDefinition createMermaidTidecallerRace(){
        String id = "mermaid_tidecaller";
        String displayName = Message.translation("server.races.mermaidTidecaller.name").getAnsiMessage();
        String description = Message.translation("server.races.mermaidTidecaller.desc").getAnsiMessage();
        String icon = "Ingredient_Void_Essence";
        String modelId = "Player";
        double modelScale = 1.0;
        boolean enabled = true;
        Map<SkillAttributeType, Double> baseAttributes = new HashMap<>();
        baseAttributes.put(SkillAttributeType.LIFE_FORCE, 165.6);
        baseAttributes.put(SkillAttributeType.STRENGTH, 0.94);
        baseAttributes.put(SkillAttributeType.DEFENSE, 0.98);
        baseAttributes.put(SkillAttributeType.HASTE, 1.1);
        baseAttributes.put(SkillAttributeType.PRECISION, 10.08);
        baseAttributes.put(SkillAttributeType.FEROCITY, 50.4);
        baseAttributes.put(SkillAttributeType.STAMINA, 10.1);
        baseAttributes.put(SkillAttributeType.FLOW, 20.2);
        baseAttributes.put(SkillAttributeType.DISCIPLINE, 0.0);
        List<Map<String, Object>> passives = new ArrayList<>();
        Map<String, Object> xpBonusMap = new HashMap<>();
        xpBonusMap.put("type", "XP_BONUS");
        xpBonusMap.put("value", 1.2);
        passives.add(xpBonusMap);
        Map<String, Object> healthRegenMap = new HashMap<>();
        healthRegenMap.put("type", "HEALTH_REGEN");
        healthRegenMap.put("value", 0.05);
        passives.add(healthRegenMap);
        Map<String, Object> adrenalineMap = new HashMap<>();
        adrenalineMap.put("type", "ADRENALINE");
        adrenalineMap.put("value", 0.95);
        adrenalineMap.put("threshold", 0.2);
        adrenalineMap.put("duration", 3);
        adrenalineMap.put("cooldown", 38);
        passives.add(adrenalineMap);
        Map<String, Object> innateAttributeGainMap = new HashMap<>();
        innateAttributeGainMap.put("type", "INNATE_ATTRIBUTE_GAIN");
        innateAttributeGainMap.put("attribute", "life_force");
        innateAttributeGainMap.put("value", 2.2);
        passives.add(innateAttributeGainMap);
        List<RacePassiveDefinition> passiveDefinitions = null;

        //Ascension
        String ascensionId = "mermaid_tidecaller";
        String ascensionStage = "tier_2";
        String ascensionPath = "adventurer";
        boolean ascensionFinalForm = false;
        boolean ascensionSingleRouteOnly = true;
        //AscensionRequirements
        int ascensionRequirementsRequiredPrestige = 9;
        Map<SkillAttributeType, Integer> ascensionRequirementsminSkillLevels = new HashMap<>();
        ascensionRequirementsminSkillLevels.put(SkillAttributeType.DISCIPLINE, 200);
        ascensionRequirementsminSkillLevels.put(SkillAttributeType.HASTE, 150);
        ascensionRequirementsminSkillLevels.put(SkillAttributeType.STAMINA, 150);
        Map<SkillAttributeType, Integer> ascensionRequirementsmaxSkillLevels = Collections.emptyMap();
        List<Map<SkillAttributeType, Integer>> ascensionRequirementsminAnySkillLevels = Collections.emptyList();
        List<String> ascensionRequirementsrequiredAugments = Collections.emptyList();
        List<String> ascensionRequirementsrequiredForms = Collections.emptyList();
        List<String> ascensionRequirementsrequiredAnyForms = Collections.emptyList();
        RaceAscensionRequirements ascensionRequirements =
                new RaceAscensionRequirements(ascensionRequirementsRequiredPrestige, ascensionRequirementsminSkillLevels, ascensionRequirementsmaxSkillLevels, ascensionRequirementsminAnySkillLevels,
                        ascensionRequirementsrequiredAugments, ascensionRequirementsrequiredForms, ascensionRequirementsrequiredAnyForms);
        List<RaceAscensionPathLink> ascensionNextPaths = new ArrayList<>();
        ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_empress", Message.translation("server.races.mermaidEmpress.name").getAnsiMessage()));

        RaceAscensionDefinition ascension =
                new RaceAscensionDefinition(ascensionId, ascensionStage, ascensionPath, ascensionFinalForm,
                        ascensionSingleRouteOnly, ascensionRequirements, ascensionNextPaths);

        return new RaceDefinition(id, displayName, description, icon, modelId, modelScale,
                enabled, baseAttributes, passives, passiveDefinitions, ascension);
    }

    // PATH 2 MERMAID DAMAGE RACE
    // Mermaid Tidebreaker damage tier 1
    private static RaceDefinition createMermaidTidebreakerRace(){
        String id = "mermaid_tidebreaker";
        String displayName = Message.translation("server.races.mermaidTidebreaker.name").getAnsiMessage();
        String description = Message.translation("server.races.mermaidTidebreaker.desc").getAnsiMessage();
        String icon = "Ingredient_Water_Essence";
        String modelId = "Player";
        double modelScale = 1.0;
        boolean enabled = true;
        Map<SkillAttributeType, Double> baseAttributes = new HashMap<>();
        baseAttributes.put(SkillAttributeType.LIFE_FORCE, 112.7);
        baseAttributes.put(SkillAttributeType.STRENGTH, 0.94);
        baseAttributes.put(SkillAttributeType.DEFENSE, 0.91);
        baseAttributes.put(SkillAttributeType.HASTE, 1.05);
        baseAttributes.put(SkillAttributeType.PRECISION, 8.4);
        baseAttributes.put(SkillAttributeType.FEROCITY, 42.0);
        baseAttributes.put(SkillAttributeType.STAMINA, 7.4);
        baseAttributes.put(SkillAttributeType.FLOW, 14.7);
        baseAttributes.put(SkillAttributeType.DISCIPLINE, 0.0);
        List<Map<String, Object>> passives = new ArrayList<>();
        Map<String, Object> xpBonusMap = new HashMap<>();
        xpBonusMap.put("type", "XP_BONUS");
        xpBonusMap.put("value", 0.8);
        passives.add(xpBonusMap);
        Map<String, Object> healthRegenMap = new HashMap<>();
        healthRegenMap.put("type", "HEALTH_REGEN");
        healthRegenMap.put("value", 0.035);
        passives.add(healthRegenMap);
        Map<String, Object> adrenalineMap = new HashMap<>();
        adrenalineMap.put("type", "ADRENALINE");
        adrenalineMap.put("value", 0.7);
        adrenalineMap.put("threshold", 0.2);
        adrenalineMap.put("duration", 3);
        adrenalineMap.put("cooldown", 42);
        passives.add(adrenalineMap);
        Map<String, Object> innateAttributeGainMap = new HashMap<>();
        innateAttributeGainMap.put("type", "INNATE_ATTRIBUTE_GAIN");
        innateAttributeGainMap.put("attribute", "life_force");
        innateAttributeGainMap.put("value", 1.7);
        passives.add(innateAttributeGainMap);
        List<RacePassiveDefinition> passiveDefinitions = null;

        //Ascension
        String ascensionId = "mermaid_tidebreaker";
        String ascensionStage = "tier_1";
        String ascensionPath = "damage";
        boolean ascensionFinalForm = false;
        boolean ascensionSingleRouteOnly = true;
        //AscensionRequirements
        int ascensionRequirementsRequiredPrestige = 3;
        Map<SkillAttributeType, Integer> ascensionRequirementsminSkillLevels = new HashMap<>();
        ascensionRequirementsminSkillLevels.put(SkillAttributeType.FEROCITY, 125);
        Map<SkillAttributeType, Integer> ascensionRequirementsmaxSkillLevels = Collections.emptyMap();
        List<Map<SkillAttributeType, Integer>> ascensionRequirementsminAnySkillLevels = new ArrayList<>(Collections.emptyList());
        Map<SkillAttributeType, Integer> strengthMap = new HashMap<>();
        strengthMap.put(SkillAttributeType.STRENGTH, 125);
        ascensionRequirementsminAnySkillLevels.add(strengthMap);
        Map<SkillAttributeType, Integer> sorceryMap = new HashMap<>();
        sorceryMap.put(SkillAttributeType.SORCERY, 125);
        ascensionRequirementsminAnySkillLevels.add(sorceryMap);
        List<String> ascensionRequirementsrequiredAugments = Collections.emptyList();
        List<String> ascensionRequirementsrequiredForms = Collections.emptyList();
        List<String> ascensionRequirementsrequiredAnyForms = Collections.emptyList();
        RaceAscensionRequirements ascensionRequirements =
                new RaceAscensionRequirements(ascensionRequirementsRequiredPrestige, ascensionRequirementsminSkillLevels, ascensionRequirementsmaxSkillLevels, ascensionRequirementsminAnySkillLevels,
                        ascensionRequirementsrequiredAugments, ascensionRequirementsrequiredForms, ascensionRequirementsrequiredAnyForms);
        List<RaceAscensionPathLink> ascensionNextPaths = new ArrayList<>();
        ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_abyssal", Message.translation("server.races.mermaidAbyssal.name").getAnsiMessage()));

        RaceAscensionDefinition ascension =
                new RaceAscensionDefinition(ascensionId, ascensionStage, ascensionPath, ascensionFinalForm,
                        ascensionSingleRouteOnly, ascensionRequirements, ascensionNextPaths);

        return new RaceDefinition(id, displayName, description, icon, modelId, modelScale,
                enabled, baseAttributes, passives, passiveDefinitions, ascension);
    }

    // Mermaid Abyssal damage tier 2
    private static RaceDefinition createMermaidAbyssalRace(){
        String id = "mermaid_abyssal";
        String displayName = Message.translation("server.races.mermaidAbyssal.name").getAnsiMessage();
        String description = Message.translation("server.races.mermaidAbyssal.desc").getAnsiMessage();
        String icon = "Ingredient_Void_Essence";
        String modelId = "Player";
        double modelScale = 1.0;
        boolean enabled = true;
        Map<SkillAttributeType, Double> baseAttributes = new HashMap<>();
        baseAttributes.put(SkillAttributeType.LIFE_FORCE, 129.6);
        baseAttributes.put(SkillAttributeType.STRENGTH, 1.03);
        baseAttributes.put(SkillAttributeType.DEFENSE, 0.98);
        baseAttributes.put(SkillAttributeType.HASTE, 1.1);
        baseAttributes.put(SkillAttributeType.PRECISION, 10.08);
        baseAttributes.put(SkillAttributeType.FEROCITY, 50.4);
        baseAttributes.put(SkillAttributeType.STAMINA, 7.7);
        baseAttributes.put(SkillAttributeType.FLOW, 15.4);
        baseAttributes.put(SkillAttributeType.DISCIPLINE, 0.0);
        List<Map<String, Object>> passives = new ArrayList<>();
        Map<String, Object> xpBonusMap = new HashMap<>();
        xpBonusMap.put("type", "XP_BONUS");
        xpBonusMap.put("value", 1.1);
        passives.add(xpBonusMap);
        Map<String, Object> healthRegenMap = new HashMap<>();
        healthRegenMap.put("type", "HEALTH_REGEN");
        healthRegenMap.put("value", 0.04);
        passives.add(healthRegenMap);
        Map<String, Object> adrenalineMap = new HashMap<>();
        adrenalineMap.put("type", "ADRENALINE");
        adrenalineMap.put("value", 0.9);
        adrenalineMap.put("threshold", 0.2);
        adrenalineMap.put("duration", 3);
        adrenalineMap.put("cooldown", 38);
        passives.add(adrenalineMap);
        Map<String, Object> innateAttributeGainMap = new HashMap<>();
        innateAttributeGainMap.put("type", "INNATE_ATTRIBUTE_GAIN");
        innateAttributeGainMap.put("attribute", "life_force");
        innateAttributeGainMap.put("value", 2.1);
        passives.add(innateAttributeGainMap);
        List<RacePassiveDefinition> passiveDefinitions = null;

        //Ascension
        String ascensionId = "mermaid_abyssal";
        String ascensionStage = "tier_2";
        String ascensionPath = "damage";
        boolean ascensionFinalForm = false;
        boolean ascensionSingleRouteOnly = true;
        //AscensionRequirements
        int ascensionRequirementsRequiredPrestige = 9;
        Map<SkillAttributeType, Integer> ascensionRequirementsminSkillLevels = new HashMap<>();
        ascensionRequirementsminSkillLevels.put(SkillAttributeType.FEROCITY, 250);
        Map<SkillAttributeType, Integer> ascensionRequirementsmaxSkillLevels = Collections.emptyMap();
        List<Map<SkillAttributeType, Integer>> ascensionRequirementsminAnySkillLevels = new ArrayList<>(Collections.emptyList());
        Map<SkillAttributeType, Integer> strengthMap = new HashMap<>();
        strengthMap.put(SkillAttributeType.STRENGTH, 250);
        ascensionRequirementsminAnySkillLevels.add(strengthMap);
        Map<SkillAttributeType, Integer> sorceryMap = new HashMap<>();
        sorceryMap.put(SkillAttributeType.SORCERY, 250);
        ascensionRequirementsminAnySkillLevels.add(sorceryMap);
        List<String> ascensionRequirementsrequiredAugments = Collections.emptyList();
        List<String> ascensionRequirementsrequiredForms = Collections.emptyList();
        List<String> ascensionRequirementsrequiredAnyForms = Collections.emptyList();
        RaceAscensionRequirements ascensionRequirements =
                new RaceAscensionRequirements(ascensionRequirementsRequiredPrestige, ascensionRequirementsminSkillLevels, ascensionRequirementsmaxSkillLevels, ascensionRequirementsminAnySkillLevels,
                        ascensionRequirementsrequiredAugments, ascensionRequirementsrequiredForms, ascensionRequirementsrequiredAnyForms);
        List<RaceAscensionPathLink> ascensionNextPaths = new ArrayList<>();
        ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_empress", Message.translation("server.races.mermaidEmpress.name").getAnsiMessage()));

        RaceAscensionDefinition ascension =
                new RaceAscensionDefinition(ascensionId, ascensionStage, ascensionPath, ascensionFinalForm,
                        ascensionSingleRouteOnly, ascensionRequirements, ascensionNextPaths);

        return new RaceDefinition(id, displayName, description, icon, modelId, modelScale,
                enabled, baseAttributes, passives, passiveDefinitions, ascension);
    }

    // FINAL MERMAID RACE
    private static RaceDefinition createMermaidEmpressRace(){
        String id = "mermaid_empress";
        String displayName = Message.translation("server.races.mermaidEmpress.name").getAnsiMessage();
        String description = Message.translation("server.races.mermaidEmpress.desc").getAnsiMessage();
        String icon = "Ingredient_Fire_Essence";
        String modelId = "Player";
        double modelScale = 1.0;
        boolean enabled = true;
        Map<SkillAttributeType, Double> baseAttributes = new HashMap<>();
        baseAttributes.put(SkillAttributeType.LIFE_FORCE, 314.7);
        baseAttributes.put(SkillAttributeType.STRENGTH, 1.23);
        baseAttributes.put(SkillAttributeType.DEFENSE, 1.2);
        baseAttributes.put(SkillAttributeType.HASTE, 1.21);
        baseAttributes.put(SkillAttributeType.PRECISION, 16.13);
        baseAttributes.put(SkillAttributeType.FEROCITY, 80.64);
        baseAttributes.put(SkillAttributeType.STAMINA, 16.1);
        baseAttributes.put(SkillAttributeType.FLOW, 32.3);
        baseAttributes.put(SkillAttributeType.DISCIPLINE, 0.0);
        List<Map<String, Object>> passives = new ArrayList<>();
        Map<String, Object> xpBonusMap = new HashMap<>();
        xpBonusMap.put("type", "XP_BONUS");
        xpBonusMap.put("value", 1.5);
        passives.add(xpBonusMap);
        Map<String, Object> healthRegenMap = new HashMap<>();
        healthRegenMap.put("type", "HEALTH_REGEN");
        healthRegenMap.put("value", 0.06);
        passives.add(healthRegenMap);
        Map<String, Object> adrenalineMap = new HashMap<>();
        adrenalineMap.put("type", "ADRENALINE");
        adrenalineMap.put("value", 1.0);
        adrenalineMap.put("threshold", 0.25);
        adrenalineMap.put("duration", 3);
        adrenalineMap.put("cooldown", 32);
        passives.add(adrenalineMap);
        Map<String, Object> innateAttributeGainMap = new HashMap<>();
        innateAttributeGainMap.put("type", "INNATE_ATTRIBUTE_GAIN");
        innateAttributeGainMap.put("attribute", "life_force");
        innateAttributeGainMap.put("value", 2.6);
        passives.add(innateAttributeGainMap);
        List<RacePassiveDefinition> passiveDefinitions = null;

        //Ascension
        String ascensionId = "mermaid_queen";
        String ascensionStage = "final";
        String ascensionPath = "Hybrid";
        boolean ascensionFinalForm = true;
        boolean ascensionSingleRouteOnly = true;
        //AscensionRequirements
        int ascensionRequirementsRequiredPrestige = 18;
        Map<SkillAttributeType, Integer> ascensionRequirementsminSkillLevels = Collections.emptyMap();
        Map<SkillAttributeType, Integer> ascensionRequirementsmaxSkillLevels = Collections.emptyMap();
        List<Map<SkillAttributeType, Integer>> ascensionRequirementsminAnySkillLevels = Collections.emptyList();
        List<String> ascensionRequirementsrequiredAugments = Collections.emptyList();
        List<String> ascensionRequirementsrequiredForms = Collections.emptyList();
        List<String> ascensionRequirementsrequiredAnyForms = Collections.emptyList();
        RaceAscensionRequirements ascensionRequirements =
                new RaceAscensionRequirements(ascensionRequirementsRequiredPrestige, ascensionRequirementsminSkillLevels, ascensionRequirementsmaxSkillLevels, ascensionRequirementsminAnySkillLevels,
                        ascensionRequirementsrequiredAugments, ascensionRequirementsrequiredForms, ascensionRequirementsrequiredAnyForms);
        List<RaceAscensionPathLink> ascensionNextPaths = new ArrayList<>();

        RaceAscensionDefinition ascension =
                new RaceAscensionDefinition(ascensionId, ascensionStage, ascensionPath, ascensionFinalForm,
                        ascensionSingleRouteOnly, ascensionRequirements, ascensionNextPaths);

        return new RaceDefinition(id, displayName, description, icon, modelId, modelScale,
                enabled, baseAttributes, passives, passiveDefinitions, ascension);
    }
}
