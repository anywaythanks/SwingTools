package swingTools;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Class for calculating optimal font boundaries in Swing graphics.
 *
 * @author anywaythanks
 * @version 0.0
 */
public class FontSizeCalculator {
    /**
     * @return the size of a glyph vector.
     */
    public static Rectangle2D getVectorBounds(Graphics2D g2D, String text) {
        return g2D.getFont().createGlyphVector(g2D.getFontRenderContext(), text).getVisualBounds();
    }

    /**
     * @return the size of the string.
     */
    public static Rectangle2D getStringBounds(Graphics2D g2D, String text) {
        return getFontMetric(g2D).getStringBounds(text, g2D);
    }

    /**
     * @return font metric.
     */
    public static FontMetrics getFontMetric(Graphics2D g2D) {
        return g2D.getFontMetrics(g2D.getFont());
    }

    /**
     * @param zeroX the zero X of the container.
     * @param g2D   {@link Graphics2D} which draws text.
     * @param text  is the text in the container.
     * @param width is the width of the container.
     * @return midpoint of text at X in the container.
     */
    public static float calculateStringMiddleXInContainer(double zeroX, Graphics2D g2D, String text, double width) {
        return (float) (zeroX + (width - getStringBounds(g2D, text).getWidth()) / 2);
    }

    /**
     * @param zeroY  the zero Y of the container.
     * @param g2d    {@link Graphics2D} which draws text.
     * @param height is the height of the container.
     * @return midpoint of text at Y in the container.
     */
    public static float calculateStringMiddleYInContainer(double zeroY, Graphics2D g2d, double height) {
        return (float) ((zeroY + (height - getFontMetric(g2d).getHeight()) / 2) + getFontMetric(g2d).getAscent());
    }

    /**
     *
     * @param g2D {@link Graphics2D} which needs to set the maximum font size in the container.
     * @param weight is the weight of the container.
     * @param height is the height of the container.
     * @param text is the text in the container.
     */
    public static void setMaxSizeFontContainer(Graphics2D g2D, float weight, float height, String text) {
        Rectangle2D maxRect = new Rectangle2D.Float(0, 0, weight, height);

        // starting with a very big font due to a high res image
        float size = 80f * 4f;
        // starting with a diff half the size of the font
        float diff = size / 2;
        Font bufferFont = g2D.getFont().deriveFont(size);
        FontMetrics fontMetrics;
        Rectangle2D stringBounds;
        while (Math.abs(diff) > 0.5F) {
            bufferFont = bufferFont.deriveFont(size);
            g2D.setFont(bufferFont);
            fontMetrics = g2D.getFontMetrics(bufferFont);
            stringBounds = fontMetrics.getStringBounds(text, g2D);
            stringBounds = new Rectangle2D.Float(0f, 0f, (float) (stringBounds.getX() + stringBounds.getWidth()), (float) (stringBounds.getHeight()));

            if (maxRect.contains(stringBounds)) {
                if (0 < diff) {
                    diff = Math.abs(diff);
                } else if (diff < 0) {
                    diff = Math.abs(diff) / 2;
                }
            } else {
                if (0 < diff) {
                    diff = -Math.abs(diff) / 2;
                } else if (diff < 0) {
                    if (size <= Math.abs(diff)) {
                        diff = -Math.abs(diff) / 2;
                    } else {
                        diff = -Math.abs(diff);
                    }
                }
            }
            size += diff;
        }
    }

    /**
     *
     * @param g2D {@link Graphics2D}, which needs to be changed to a specific multiplier.
     * @param multiplier the multiplier by which to change g2D.
     */
    public static void reMultiplierFont(Graphics2D g2D, float multiplier) {
        g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize2D() * multiplier));
    }

}
