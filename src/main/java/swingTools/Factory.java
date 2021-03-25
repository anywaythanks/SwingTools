package swingTools;

import java.awt.*;

/**
 * A class for deriving some objects of the Swing classes.
 *
 * @author anywaythanks
 * @version 0.1
 */
public class Factory {
    public static Graphics2D factoryGraphics2D(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        return g2d;
    }
}
