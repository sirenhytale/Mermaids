package plugin.siren.Utils.Misc;

import plugin.siren.Utils.Cosmetics.MermaidCosmetic;
import plugin.siren.Utils.Models.MermaidColor;

public enum ItemSpeedType {
    NONE(0),
    TIER_ONE(1),
    TIER_TWO(2),
    TIER_THREE(3);

    private final int value;

    private ItemSpeedType(int value) {
        this.value = value;
    }

    public static ItemSpeedType get(int value){
        ItemSpeedType itemSpeedType = null;
        for(ItemSpeedType type : ItemSpeedType.values()){
            if(type.value == value){
                itemSpeedType = type;
            }
        }

        return itemSpeedType;
    }

    public int getValue() {
        return this.value;
    }
}
