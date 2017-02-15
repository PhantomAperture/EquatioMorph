package de.daviddiviad.render;

import java.awt.*;

/**
 * Created by David on 15.02.2017.
 */

public class AdaptiveText {

    private Dimension extent;
    private Font font;
    private String content;

    public AdaptiveText(String content) {
        this.content = content;
    }

    public void update(Font font) {
        this.font = font;
        this.extent = calculateExtent(getContent(), font);
    }

    public void update(Font font, Dimension limitations) {
        this.update(font);
        if (isScalingNeeded(limitations)) {
            this.font = getScaledFont(getContent(), limitations, getFont());
            this.extent = calculateExtent(getContent(), this.font);
        }
    }

    public static Dimension calculateExtent(String content, Font font) {
        FontMetrics fontMetrics = HierarchicView.getFontMetrics(font);
        return new Dimension(fontMetrics.stringWidth(content), fontMetrics.getAscent() + fontMetrics.getDescent());
    }

    private boolean isScalingNeeded(Dimension limitations) {
        if (limitations == null) return false;
        return limitations.width < this.extent.width || limitations.height < this.extent.height;
    }

    public Dimension getExtent() {
        return extent;
    }

    public Font getFont() {
        return font;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public static Font getScaledFont (String content, Dimension limitations, Font font) {
        FontMetrics fontMetrics = HierarchicView.getFontMetrics(font);
        double scaleX = ((float) limitations.width / fontMetrics.stringWidth(content));
        double scaleY = ((float) limitations.height / fontMetrics.getAscent());
        if (scaleX == 1 && scaleY == 1)
            return font;
        else {
            float derive = (float) Math.floor(Math.min(scaleX, scaleY) * ((double) font.getSize()));
            return font.deriveFont(derive);
        }
    }
}
