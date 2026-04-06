package plugin.siren.Compatibility.EndlessLeveling.Races.Hybrid;

import com.airijko.endlessleveling.enums.SkillAttributeType;
import com.airijko.endlessleveling.races.*;
import com.hypixel.hytale.server.core.Message;
import plugin.siren.Compatibility.EndlessLeveling.EndlessLevelingRegistry;

import java.util.*;

public class RaceMermaidPrincess {
    // Mermaid Princess hybrid tier 1
    public static RaceDefinition createMermaidPrincessRace(boolean startUp){
        String id = "mermaid_princess";
        String displayName = "Mermaid Princess";
        String description = "A mythical sea creature with royal blood.";
        if(!startUp) {
            displayName = Message.translation("server.races.mermaidPrincess.name").getAnsiMessage();
            description = Message.translation("server.races.mermaidPrincess.desc").getAnsiMessage();
        }
        String icon = "Ingredient_Water_Essence";
        String modelId = "Player";
        double modelScale = 1.0;
        boolean enabled = true;
        Map<SkillAttributeType, Double> baseAttributes = new HashMap<>();
        baseAttributes.put(SkillAttributeType.LIFE_FORCE, 93.1);
        baseAttributes.put(SkillAttributeType.STRENGTH, 1.08);
        baseAttributes.put(SkillAttributeType.DEFENSE, 0.89);
        baseAttributes.put(SkillAttributeType.HASTE, 1.16);
        baseAttributes.put(SkillAttributeType.PRECISION, 12.6);
        baseAttributes.put(SkillAttributeType.FEROCITY, 33.6);
        baseAttributes.put(SkillAttributeType.STAMINA, 7.4);
        baseAttributes.put(SkillAttributeType.FLOW, 22.1);
        baseAttributes.put(SkillAttributeType.SORCERY, 1.08);
        baseAttributes.put(SkillAttributeType.DISCIPLINE, 0.0);
        List<Map<String, Object>> passives = new ArrayList<>();
        Map<String, Object> xpBonusMap = new HashMap<>();
        xpBonusMap.put("type", "XP_BONUS");
        xpBonusMap.put("value", 0.6);
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
        Map<String, Object> swiftnessMap = new HashMap<>();
        swiftnessMap.put("type", "SWIFTNESS");
        swiftnessMap.put("value", 0.14);
        swiftnessMap.put("duration", 5);
        swiftnessMap.put("max_stacks", 8);
        passives.add(swiftnessMap);
        Map<String, Object> innateAttributeGainMap = new HashMap<>();
        innateAttributeGainMap.put("type", "INNATE_ATTRIBUTE_GAIN");
        innateAttributeGainMap.put("attribute", "life_force");
        innateAttributeGainMap.put("value", 1.2);
        List<RacePassiveDefinition> passiveDefinitions = EndlessLevelingRegistry.buildRacePassieDefinition(passives);

        //Ascension
        String ascensionId = "mermaid_princess";
        String ascensionStage = "tier_1";
        String ascensionPath = "hybrid";
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
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_queen", "Mermaid Queen"));
        }else {
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_queen", Message.translation("server.races.mermaidQueen.name").getAnsiMessage()));
        }

        RaceAscensionDefinition ascension =
                new RaceAscensionDefinition(ascensionId, ascensionStage, ascensionPath, ascensionFinalForm,
                        ascensionSingleRouteOnly, ascensionRequirements, ascensionNextPaths);

        return new RaceDefinition(id, displayName, description, icon, modelId, modelScale,
                enabled, baseAttributes, passives, passiveDefinitions, ascension);
    }
}
