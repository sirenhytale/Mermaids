package plugin.siren.Compatibility.EndlessLeveling.Races;

import com.airijko.endlessleveling.enums.SkillAttributeType;
import com.airijko.endlessleveling.races.*;
import com.hypixel.hytale.server.core.Message;
import plugin.siren.Compatibility.EndlessLeveling.EndlessLevelingRegistry;

import java.util.*;

public class RaceMermaid {
    // BASE MERMAID RACE
    public static RaceDefinition createMermaidRace(boolean startUp){
        String id = "mermaid";
        String displayName = "Mermaid";
        String description = "A mythical sea creature who has the tail of a fish.";
        if(!startUp) {
            displayName = Message.translation("server.races.mermaid.name").getAnsiMessage();
            description = Message.translation("server.races.mermaid.desc").getAnsiMessage();
        }
        String icon = "Ingredient_Ice_Essence";
        String modelId = "Player";
        double modelScale = 1.0;
        boolean enabled = true;
        Map<SkillAttributeType, Double> baseAttributes = new HashMap<>();
        baseAttributes.put(SkillAttributeType.LIFE_FORCE, 81.0);
        baseAttributes.put(SkillAttributeType.STRENGTH, 0.98);
        baseAttributes.put(SkillAttributeType.DEFENSE, 0.83);
        baseAttributes.put(SkillAttributeType.HASTE, 1.1);
        baseAttributes.put(SkillAttributeType.PRECISION, 10.5);
        baseAttributes.put(SkillAttributeType.FEROCITY, 28.0);
        baseAttributes.put(SkillAttributeType.STAMINA, 7.0);
        baseAttributes.put(SkillAttributeType.FLOW, 21.0);
        baseAttributes.put(SkillAttributeType.SORCERY, 0.98);
        baseAttributes.put(SkillAttributeType.DISCIPLINE, 0.0);
        List<Map<String, Object>> passives = new ArrayList<>();
        Map<String, Object> xpBonusMap = new HashMap<>();
        xpBonusMap.put("type", "XP_BONUS");
        xpBonusMap.put("value", 0.35);
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
        Map<String, Object> swiftnessMap = new HashMap<>();
        swiftnessMap.put("type", "SWIFTNESS");
        swiftnessMap.put("value", 0.07);
        swiftnessMap.put("duration", 5);
        swiftnessMap.put("max_stacks", 8);
        passives.add(swiftnessMap);
        Map<String, Object> innateAttributeGainMap = new HashMap<>();
        innateAttributeGainMap.put("type", "INNATE_ATTRIBUTE_GAIN");
        innateAttributeGainMap.put("attribute", "life_force");
        innateAttributeGainMap.put("value", 0.65);
        passives.add(innateAttributeGainMap);
        List<RacePassiveDefinition> passiveDefinitions = EndlessLevelingRegistry.buildRacePassieDefinition(passives);

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
        if(startUp){
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_princess", "Mermaid Princess"));
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_tideweaver", "Mermaid Tideweaver"));
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_tidebreaker", "Mermaid Tidebreaker"));
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_pathfinder", "Mermaid Pathfinder"));
        }else {
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_princess", Message.translation("server.races.mermaidPrincess.name").getAnsiMessage()));
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_tideweaver", Message.translation("server.races.mermaidTideweaver.name").getAnsiMessage()));
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_tidebreaker", Message.translation("server.races.mermaidTidebreaker.name").getAnsiMessage()));
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_pathfinder", Message.translation("server.races.mermaidPathfinder.name").getAnsiMessage()));
        }

        RaceAscensionDefinition ascension =
                new RaceAscensionDefinition(ascensionId, ascensionStage, ascensionPath, ascensionFinalForm,
                        ascensionSingleRouteOnly, ascensionRequirements, ascensionNextPaths);

        return new RaceDefinition(id, displayName, description, icon, modelId, modelScale,
                enabled, baseAttributes, passives, passiveDefinitions, ascension);
    }
}
