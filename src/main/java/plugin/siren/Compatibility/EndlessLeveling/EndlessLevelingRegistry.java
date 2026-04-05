package plugin.siren.Compatibility.EndlessLeveling;

import com.airijko.endlessleveling.EndlessLeveling;
import com.airijko.endlessleveling.enums.SkillAttributeType;
import com.airijko.endlessleveling.races.*;
import com.hypixel.hytale.server.core.HytaleServer;
import plugin.siren.Mermaids;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class EndlessLevelingRegistry {

    private EndlessLevelingRegistry(){}

    public static void register() {
        EndlessLeveling endlessLeveling = EndlessLeveling.getInstance();
        RaceManager raceManager = endlessLeveling != null ? endlessLeveling.getRaceManager() : null;

        RaceDefinition mermaidRace = createMermaidRace();
        RaceDefinition mermaidQueenRace = createMermaidQueenRace();

        if(raceManager != null) {
            raceManager.registerExternalRace(mermaidRace);
            raceManager.registerExternalRace(mermaidQueenRace);
        }else{
            Mermaids.LOGGER.atSevere().log("ERROR: RaceManager is NULL : EndlessLevelingRegister.register()");
        }

        //HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(checkForMermaidRace,15, 2, TimeUnit.SECONDS); //Remember to set period to at least 10!!!!!!!!!!!
    }

    private static RaceDefinition createMermaidRace(){
        String id = "mermaid";
        String displayName = "Mermaid";
        String description = "A mythical sea creature who has the tail of a fish.";
        String icon = "Ingredient_Water_Essence";
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
        boolean ascensionSingleRouteOnly = true;
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
        ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_queen", "Mermaid Queen"));

        RaceAscensionDefinition ascension =
                new RaceAscensionDefinition(ascensionId, ascensionStage, ascensionPath, ascensionFinalForm,
                        ascensionSingleRouteOnly, ascensionRequirements, ascensionNextPaths);

        return new RaceDefinition(id, displayName, description, icon, modelId, modelScale,
                enabled, baseAttributes, passives, passiveDefinitions, ascension);
    }

    private static RaceDefinition createMermaidQueenRace(){
        String id = "mermaid_queen";
        String displayName = "Mermaid Queen";
        String description = "The ultimate mythical sea creature who rules the seas.";
        String icon = "Ingredient_Water_Essence";
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
        String ascensionPath = "none";
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
