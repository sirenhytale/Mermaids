package plugin.siren.Utils.Misc;

public enum ArmorSpeedType {
    NONE(0),
    TIER_ONE(1),
    TIER_TWO(2),
    TIER_THREE(3);

    private final int value;

    private ArmorSpeedType(int value) {
        this.value = value;
    }

    public static ArmorSpeedType get(int value){
        ArmorSpeedType armorSpeedType = null;
        for(ArmorSpeedType type : ArmorSpeedType.values()){
            if(type.value == value){
                armorSpeedType = type;
            }
        }

        return armorSpeedType;
    }

    public int getValue() {
        return this.value;
    }
}
