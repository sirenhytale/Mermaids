package plugin.siren.Utils.Models;

import plugin.siren.Utils.Cosmetics.MermaidCosmeticSkin;

public enum MermaidColor {
    ORANGE(0),
    PINK(1),
    ROSE(2),
    PURPLE(3),
    AQUA(4),
    LIME(5),
    BLUE(6),
    CYAN(7),
    BLACK(8),
    GRAY(9);

    private final int value;

    private MermaidColor(int value) {
        this.value = value;
    }

    public static MermaidColor get(int value) {
        MermaidColor textureColor = null;
        for (MermaidColor color : MermaidColor.values()) {
            if (color.value == value) {
                textureColor = color;
            }
        }

        return textureColor;
    }

    public int getValue() {
        return this.value;
    }
}
