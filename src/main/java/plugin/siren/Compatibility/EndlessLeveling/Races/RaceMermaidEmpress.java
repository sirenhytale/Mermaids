package plugin.siren.Compatibility.EndlessLeveling.Races;

import com.airijko.endlessleveling.enums.SkillAttributeType;
import com.airijko.endlessleveling.races.*;
import com.hypixel.hytale.server.core.Message;
import plugin.siren.Compatibility.EndlessLeveling.EndlessLevelingRegistry;

import java.util.*;

public class RaceMermaidEmpress {
    // FINAL EMPRESS MERMAID RACE
    public static RaceDefinition createMermaidEmpressRace(boolean startUp){
        String id = "mermaid_empress";
        String displayName = "Mermaid Empress";
        String description = "The ultimate mythical sea creature who rules the seven seas.";
        if(!startUp){
            displayName = Message.translation("server.races.mermaidEmpress.name").getAnsiMessage();
            description = Message.translation("server.races.mermaidEmpress.desc").getAnsiMessage();
        }
        String icon = "Ingredient_Fire_Essence";
        String modelId = "Player";
        double modelScale = 1.0;
        boolean enabled = true;
        Map<SkillAttributeType, Double> baseAttributes = new HashMap<>();
        baseAttributes.put(SkillAttributeType.LIFE_FORCE, 214.7);
        baseAttributes.put(SkillAttributeType.STRENGTH, 1.42);
        baseAttributes.put(SkillAttributeType.DEFENSE, 1.2);
        baseAttributes.put(SkillAttributeType.HASTE, 1.33);
        baseAttributes.put(SkillAttributeType.PRECISION, 24.19);
        baseAttributes.put(SkillAttributeType.FEROCITY, 70.64);
        baseAttributes.put(SkillAttributeType.STAMINA, 16.1);
        baseAttributes.put(SkillAttributeType.FLOW, 29.3);
        baseAttributes.put(SkillAttributeType.SORCERY, 1.42);
        baseAttributes.put(SkillAttributeType.DISCIPLINE, 0.0);
        List<Map<String, Object>> passives = new ArrayList<>();
        Map<String, Object> xpBonusMap = new HashMap<>();
        xpBonusMap.put("type", "XP_BONUS");
        xpBonusMap.put("value", 1.1);
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
        Map<String, Object> swiftnessMap = new HashMap<>();
        swiftnessMap.put("type", "SWIFTNESS");
        swiftnessMap.put("value", 0.24);
        swiftnessMap.put("duration", 5);
        swiftnessMap.put("max_stacks", 8);
        passives.add(swiftnessMap);
        Map<String, Object> innateAttributeGainMap = new HashMap<>();
        innateAttributeGainMap.put("type", "INNATE_ATTRIBUTE_GAIN");
        innateAttributeGainMap.put("attribute", "life_force");
        innateAttributeGainMap.put("value", 2.3);
        List<RacePassiveDefinition> passiveDefinitions = EndlessLevelingRegistry.buildRacePassieDefinition(passives);

        //Ascension
        String ascensionId = "mermaid_empress";
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
