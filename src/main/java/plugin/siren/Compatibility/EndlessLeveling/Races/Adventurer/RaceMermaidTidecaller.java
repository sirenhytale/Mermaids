package plugin.siren.Compatibility.EndlessLeveling.Races.Adventurer;

import com.airijko.endlessleveling.enums.SkillAttributeType;
import com.airijko.endlessleveling.races.*;
import com.hypixel.hytale.server.core.Message;
import plugin.siren.Compatibility.EndlessLeveling.EndlessLevelingRegistry;

import java.util.*;

public class RaceMermaidTidecaller {
    // Mermaid Tidecaller adventurer tier 2
    public static RaceDefinition createMermaidTidecallerRace(boolean startUp){
        String id = "mermaid_tidecaller";
        String displayName = "Mermaid Tidecaller";
        String description = "A mythical sea creature who can ride the waves.";
        if(!startUp) {
            displayName = Message.translation("server.races.mermaidTidecaller.name").getAnsiMessage();
            description = Message.translation("server.races.mermaidTidecaller.desc").getAnsiMessage();
        }
        String icon = "Ingredient_Void_Essence";
        String modelId = "Player";
        double modelScale = 1.0;
        boolean enabled = true;
        Map<SkillAttributeType, Double> baseAttributes = new HashMap<>();
        baseAttributes.put(SkillAttributeType.LIFE_FORCE, 124.3);
        baseAttributes.put(SkillAttributeType.STRENGTH, 1.13);
        baseAttributes.put(SkillAttributeType.DEFENSE, 1.01);
        baseAttributes.put(SkillAttributeType.HASTE, 1.25);
        baseAttributes.put(SkillAttributeType.PRECISION, 15.2);
        baseAttributes.put(SkillAttributeType.FEROCITY, 40.32);
        baseAttributes.put(SkillAttributeType.STAMINA, 8.4);
        baseAttributes.put(SkillAttributeType.FLOW, 22.2);
        baseAttributes.put(SkillAttributeType.SORCERY, 1.13);
        baseAttributes.put(SkillAttributeType.DISCIPLINE, 0.0);
        List<Map<String, Object>> passives = new ArrayList<>();
        Map<String, Object> xpBonusMap = new HashMap<>();
        xpBonusMap.put("type", "XP_BONUS");
        xpBonusMap.put("value", 0.9);
        passives.add(xpBonusMap);
        Map<String, Object> healthRegenMap = new HashMap<>();
        healthRegenMap.put("type", "HEALTH_REGEN");
        healthRegenMap.put("value", 0.06);
        passives.add(healthRegenMap);
        Map<String, Object> adrenalineMap = new HashMap<>();
        adrenalineMap.put("type", "ADRENALINE");
        adrenalineMap.put("value", 0.98);
        adrenalineMap.put("threshold", 0.2);
        adrenalineMap.put("duration", 3);
        adrenalineMap.put("cooldown", 38);
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
        innateAttributeGainMap.put("value", 1.95);
        List<RacePassiveDefinition> passiveDefinitions = EndlessLevelingRegistry.buildRacePassieDefinition(passives);

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
        if(startUp){
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_empress", "Mermaid Empress"));
        }else {
            ascensionNextPaths.add(new RaceAscensionPathLink("mermaid_empress", Message.translation("server.races.mermaidEmpress.name").getAnsiMessage()));
        }

        RaceAscensionDefinition ascension =
                new RaceAscensionDefinition(ascensionId, ascensionStage, ascensionPath, ascensionFinalForm,
                        ascensionSingleRouteOnly, ascensionRequirements, ascensionNextPaths);

        return new RaceDefinition(id, displayName, description, icon, modelId, modelScale,
                enabled, baseAttributes, passives, passiveDefinitions, ascension);
    }
}
