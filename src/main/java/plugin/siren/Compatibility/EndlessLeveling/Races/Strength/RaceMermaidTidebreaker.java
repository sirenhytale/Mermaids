package plugin.siren.Compatibility.EndlessLeveling.Races.Strength;

import com.airijko.endlessleveling.enums.SkillAttributeType;
import com.airijko.endlessleveling.races.*;
import com.hypixel.hytale.server.core.Message;
import plugin.siren.Compatibility.EndlessLeveling.EndlessLevelingRegistry;

import java.util.*;

public class RaceMermaidTidebreaker {
    // Mermaid Tidebreaker strength tier 1
    public static RaceDefinition createMermaidTidebreakerRace(boolean startUp){
        String id = "mermaid_tidebreaker";
        String displayName = "Mermaid Tidebreaker";
        String description = "A mythical sea creature who can break the waves.";
        if(!startUp) {
            displayName = Message.translation("server.races.mermaidTidebreaker.name").getAnsiMessage();
            description = Message.translation("server.races.mermaidTidebreaker.desc").getAnsiMessage();
        }
        String icon = "Ingredient_Water_Essence";
        String modelId = "Player";
        double modelScale = 1.0;
        boolean enabled = true;
        Map<SkillAttributeType, Double> baseAttributes = new HashMap<>();
        baseAttributes.put(SkillAttributeType.LIFE_FORCE, 84.6);
        baseAttributes.put(SkillAttributeType.STRENGTH, 1.19);
        baseAttributes.put(SkillAttributeType.DEFENSE, 0.86);
        baseAttributes.put(SkillAttributeType.HASTE, 1.13);
        baseAttributes.put(SkillAttributeType.PRECISION, 12.6);
        baseAttributes.put(SkillAttributeType.FEROCITY, 33.6);
        baseAttributes.put(SkillAttributeType.STAMINA, 6.9);
        baseAttributes.put(SkillAttributeType.FLOW, 22.1);
        baseAttributes.put(SkillAttributeType.SORCERY, 1.0);
        baseAttributes.put(SkillAttributeType.DISCIPLINE, 0.0);
        List<Map<String, Object>> passives = new ArrayList<>();
        Map<String, Object> xpBonusMap = new HashMap<>();
        xpBonusMap.put("type", "XP_BONUS");
        xpBonusMap.put("value", 0.55);
        passives.add(xpBonusMap);
        Map<String, Object> healthRegenMap = new HashMap<>();
        healthRegenMap.put("type", "HEALTH_REGEN");
        healthRegenMap.put("value", 0.04);
        passives.add(healthRegenMap);
        Map<String, Object> adrenalineMap = new HashMap<>();
        adrenalineMap.put("type", "ADRENALINE");
        adrenalineMap.put("value", 0.76);
        adrenalineMap.put("threshold", 0.2);
        adrenalineMap.put("duration", 3);
        adrenalineMap.put("cooldown", 42);
        passives.add(adrenalineMap);
        Map<String, Object> swiftnessMap = new HashMap<>();
        swiftnessMap.put("type", "SWIFTNESS");
        swiftnessMap.put("value", 0.12);
        swiftnessMap.put("duration", 5);
        swiftnessMap.put("max_stacks", 8);
        passives.add(swiftnessMap);
        Map<String, Object> innateAttributeGainMap = new HashMap<>();
        innateAttributeGainMap.put("type", "INNATE_ATTRIBUTE_GAIN");
        innateAttributeGainMap.put("attribute", "life_force");
        innateAttributeGainMap.put("value", 1.1);
        List<RacePassiveDefinition> passiveDefinitions = EndlessLevelingRegistry.buildRacePassieDefinition(passives);

        //Ascension
        String ascensionId = "mermaid_tidebreaker";
        String ascensionStage = "tier_1";
        String ascensionPath = "strength";
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
        if(startUp){
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_abyssal", "Mermaid Abyssal"));
        }else {
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_abyssal", Message.translation("server.races.mermaidAbyssal.name").getAnsiMessage()));
        }

        RaceAscensionDefinition ascension =
                new RaceAscensionDefinition(ascensionId, ascensionStage, ascensionPath, ascensionFinalForm,
                        ascensionSingleRouteOnly, ascensionRequirements, ascensionNextPaths);

        return new RaceDefinition(id, displayName, description, icon, modelId, modelScale,
                enabled, baseAttributes, passives, passiveDefinitions, ascension);
    }
}
