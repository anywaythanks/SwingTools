package swingTools;

import java.awt.*;

/**
 * A class of different color management tools.
 *
 * @author anywaythanks
 * @version 0.1
 */
public class ColorTools {
    /**
     * @param col     color.
     * @param opacity opacity in the range [0;1].
     * @return col whose color has been changed by the alpha channel Alfa in direct proportion to the size of the opacity.
     */
    public static Color setOpacityColor(Color col, double opacity) {
        opacity = Math.max(0.0, Math.min(1.0, opacity));
        return new Color(col.getRed(), col.getGreen(), col.getBlue(), (int) (col.getAlpha() * opacity));
    }
}
