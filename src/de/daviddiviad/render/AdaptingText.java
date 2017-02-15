package de.daviddiviad.render;

import java.awt.*;

/**
 * Created by David on 15.02.2017.
 */

public class AdaptingText {

    //TEST
    private Dimension boundingBox;
    private Font font;
    private final String content;

    //BoundingBox berechnen nach normaler Schriftgröße
    public AdaptingText(String content, Font font) {
        this.content = content;
        this.font = font;
        this.boundingBox = calculateBoundingBox(content, font);
    }

    //BoundingBox sowie Schriftgröße an Limitierungen anpassen
    public AdaptingText(String content, Dimension extent, Font targetFont) {
        this(content, targetFont);
        if (isScalingNeeded(extent)) {
            this.font = getScaledFont(content, extent, targetFont);
            this.boundingBox = calculateBoundingBox(content, this.font);
        }
    }

    private Dimension calculateBoundingBox (String content, Font font) {
        FontMetrics fontMetrics = HierarchicView.getFontMetrics(font);
        return new Dimension(fontMetrics.stringWidth(content), fontMetrics.getAscent());
    }

    private boolean isScalingNeeded(Dimension extent) {
        if (boundingBox == null) return false;
        return extent.width < boundingBox.width || extent.height < boundingBox.height;
    }

    public Dimension getBoundingBox() {
        return boundingBox;
    }

    public Font getFont() {
        return font;
    }

    public String getContent() {
        return content;
    }

    public static Font getScaledFont (String content, Dimension extent, Font font) {
        FontMetrics fontMetrics = HierarchicView.getFontMetrics(font);
        float scaleX = ((float) extent.width / fontMetrics.stringWidth(content));
        float scaleY = ((float) extent.height / fontMetrics.getAscent());
        if (scaleX == 1 && scaleY == 1)
            return font;
        else
            return font.deriveFont(Math.min(scaleX, scaleY) * font.getSize());
    }
}
