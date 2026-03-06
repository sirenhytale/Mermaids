package plugin.siren.Compatibility.PlaceholderAPI;

import at.helpch.placeholderapi.expansion.PlaceholderExpansion;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jetbrains.annotations.NotNull;
import plugin.siren.Mermaids;
import plugin.siren.Systems.MermaidComponent;
import plugin.siren.api.MermaidsAPI;

public class PlaceholderAPICompat extends PlaceholderExpansion {

    @Override
    @NotNull
    public String getAuthor() {
        return "Siren";
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "Mermaids";
    }

    @Override
    @NotNull
    public String getVersion() {
        return Mermaids.getVersion();
    }

    @Override
    public String onPlaceholderRequest(PlayerRef playerRef, @NotNull String params) {
        final Ref<EntityStore> ref = playerRef.getReference();
        final Store<EntityStore> store = ref.getStore();

        if(params.equalsIgnoreCase("mermaid")){
            if(MermaidsAPI.isMermaid(store,ref)){
                return "Mermaid";
            }else{
                return "";
            }
        }
        if(params.equalsIgnoreCase("underwater")){
            if(MermaidsAPI.isUnderwater(store,ref)){
                return "Underwater";
            }else{
                return "";
            }
        }

        return null;
    }
}