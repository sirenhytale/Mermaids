package plugin.siren.Compatibility.EndlessLeveling;

import com.airijko.endlessleveling.EndlessLeveling;
import com.airijko.endlessleveling.api.EndlessLevelingAPI;
import com.airijko.endlessleveling.enums.*;
import com.airijko.endlessleveling.passives.util.PassiveDefinitionParser;
import com.airijko.endlessleveling.races.*;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Compatibility.EndlessLeveling.Races.Adventurer.RaceMermaidPathfinder;
import plugin.siren.Compatibility.EndlessLeveling.Races.Adventurer.RaceMermaidTidecaller;
import plugin.siren.Compatibility.EndlessLeveling.Races.Hybrid.RaceMermaidPrincess;
import plugin.siren.Compatibility.EndlessLeveling.Races.Hybrid.RaceMermaidQueen;
import plugin.siren.Compatibility.EndlessLeveling.Races.RaceMermaid;
import plugin.siren.Compatibility.EndlessLeveling.Races.RaceMermaidEmpress;
import plugin.siren.Compatibility.EndlessLeveling.Races.Sorcery.RaceMermaidMoonbinder;
import plugin.siren.Compatibility.EndlessLeveling.Races.Sorcery.RaceMermaidTideweaver;
import plugin.siren.Compatibility.EndlessLeveling.Races.Strength.RaceMermaidAbyssal;
import plugin.siren.Compatibility.EndlessLeveling.Races.Strength.RaceMermaidTidebreaker;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidSettingsComponent;

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

                            checkForMermaidRaces(store, ref, playerRef);
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

        RegisterMermaidRaces(raceManager, true);

        Runnable updateLanguageVariables = new Runnable() {
            @Override
            public void run() {
                EndlessLeveling runnableEndlessLeveling = EndlessLeveling.getInstance();
                RaceManager runnableRaceManager = runnableEndlessLeveling != null ? runnableEndlessLeveling.getRaceManager() : null;

                RegisterMermaidRaces(runnableRaceManager, false);
            }
        };

        HytaleServer.SCHEDULED_EXECUTOR.schedule(updateLanguageVariables,15, TimeUnit.SECONDS);

        HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(runnableCheckForMermaidRaces,15, 12, TimeUnit.SECONDS);

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

    public static void checkForMermaidRaces(Store<EntityStore> store, Ref<EntityStore> ref, PlayerRef playerRef){
        MermaidSettingsComponent mermaidSettings = store.getComponent(ref, MermaidSettingsComponent.getComponentType());
        if(mermaidSettings == null){
            Mermaids.LOGGER.atSevere().log("ERROR: checkForMermaidRaces: Mermaid Settings Component is null for EndlessLevelingRegistry");
        }else{
            String playersRaceId = EndlessLevelingAPI.get().getRaceId(playerRef.getUuid());

            if(playersRaceId.length() >= 6){
                String raceIdSub = playersRaceId.substring(0,7);

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

    private static void RegisterMermaidRaces(RaceManager raceManager, boolean startUp){
        RaceDefinition mermaidRace = RaceMermaid.createMermaidRace(startUp);
        RaceDefinition mermaidPathfinderRace = RaceMermaidPathfinder.createMermaidPathfinderRace(startUp);
        RaceDefinition mermaidTidecallerRace = RaceMermaidTidecaller.createMermaidTidecallerRace(startUp);
        RaceDefinition mermaidTidebreakerRace = RaceMermaidTidebreaker.createMermaidTidebreakerRace(startUp);
        RaceDefinition mermaidAbyssalRace = RaceMermaidAbyssal.createMermaidAbyssalRace(startUp);
        RaceDefinition mermaidTideweaver = RaceMermaidTideweaver.createMermaidTideweaverRace(startUp);
        RaceDefinition mermaidMoonbinder = RaceMermaidMoonbinder.createMermaidMoonbinderRace(startUp);
        RaceDefinition mermaidPrincessRace = RaceMermaidPrincess.createMermaidPrincessRace(startUp);
        RaceDefinition mermaidQueenRace = RaceMermaidQueen.createMermaidQueenRace(startUp);
        RaceDefinition mermaidEmpressRace = RaceMermaidEmpress.createMermaidEmpressRace(startUp);

        if(raceManager != null) {
            raceManager.registerExternalRace(mermaidRace);
            raceManager.registerExternalRace(mermaidPathfinderRace);
            raceManager.registerExternalRace(mermaidTidecallerRace);
            raceManager.registerExternalRace(mermaidTidebreakerRace);
            raceManager.registerExternalRace(mermaidAbyssalRace);
            raceManager.registerExternalRace(mermaidTideweaver);
            raceManager.registerExternalRace(mermaidMoonbinder);
            raceManager.registerExternalRace(mermaidPrincessRace);
            raceManager.registerExternalRace(mermaidQueenRace);
            raceManager.registerExternalRace(mermaidEmpressRace);
        }else{
            Mermaids.LOGGER.atSevere().log("ERROR: RaceManager is NULL : EndlessLevelingRegister.register()");
        }
    }

    public static List<RacePassiveDefinition> buildRacePassieDefinition(List<Map<String, Object>> passives){
        List<RacePassiveDefinition> definition = new ArrayList<>();

        for(Map<String, Object> passive : passives){
            ArchetypePassiveType type = ArchetypePassiveType.fromConfigKey((String) passive.get("type"));
            double value = (double) passive.get("value");

            SkillAttributeType attributeType = null;
            if(type == ArchetypePassiveType.INNATE_ATTRIBUTE_GAIN){
                String attributeKey = (String) passive.get("attribute");
                attributeType = SkillAttributeType.fromConfigKey(attributeKey);
            }

            DamageLayer damageLayer = PassiveDefinitionParser.resolveDamageLayer(type, passive);
            String tag = PassiveDefinitionParser.resolveTag(type, passive);
            PassiveStackingStyle stacking = PassiveDefinitionParser.resolveStacking(type, passive);

            PassiveTier tier = PassiveTier.COMMON;
            if(passive.containsKey("tier")) {
                tier = PassiveTier.fromConfig(passive.get("tier"), PassiveTier.COMMON);
            }
            Object rawCategory = passive.get("category");
            PassiveCategory category = PassiveCategory.fromConfigOrNull(rawCategory);

            Map<String, Double> classValues = parseClassValues(passive.get("class_values"));

            definition.add(new RacePassiveDefinition(type, value, passive, attributeType, damageLayer,
                    tag, category, stacking, tier, classValues));
        }

        return definition;
    }

    private static Map<String, Double> parseClassValues(Object node) {
        Map<String, Double> result = new LinkedHashMap<>();
        if (!(node instanceof Map<?, ?> map)) {
            return result;
        }
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object rawKey = entry.getKey();
            Object rawVal = entry.getValue();
            if (!(rawKey instanceof String key)) {
                continue;
            }
            String normalizedKey = key.trim().toLowerCase(Locale.ROOT);
            if (normalizedKey.isEmpty()) {
                continue;
            }
            Double value = extractValue(rawVal);
            if (value != null) {
                result.put(normalizedKey, value);
            }
        }
        return result;
    }

    private static Double extractValue(Object rawVal) {
        if (rawVal instanceof Number number) {
            return number.doubleValue();
        }
        if (rawVal instanceof Map<?, ?> map) {
            Object inner = map.get("value");
            if (inner instanceof Number number) {
                return number.doubleValue();
            }
            if (inner instanceof String str) {
                return parseNumericString(str);
            }
        }
        if (rawVal instanceof String str) {
            return parseNumericString(str);
        }
        return null;
    }

    private static Double parseNumericString(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
