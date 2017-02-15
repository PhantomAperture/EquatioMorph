package de.daviddiviad.render.render.views;

import de.daviddiviad.render.AdaptiveText;
import de.daviddiviad.render.Alignment;
import de.daviddiviad.render.HierarchicView;
import de.daviddiviad.render.Measure;
import de.daviddiviad.structure.Node;

import java.awt.*;

/**
 * Created by David on 14.02.2017.
 */

public class TextView extends HierarchicView<String> {

    private AdaptiveText adaptiveText;

    public TextView(Node parent, String content) {
        super(parent, content);
        adaptiveText = new AdaptiveText(content);
    }

    @Override
    public void onMeasure(Measure constrains) throws Measure.MeasureException {
        adaptiveText.update(constrains.getFont());

        switch (constrains.getMeasureSpec()) {
            case Measure.EXACTLY: //Die maximal Größe nutzen
                setPreferredSize(new Dimension(constrains.getLimitations().width, constrains.getLimitations().height));
                adaptiveText.update(constrains.getFont(), getPreferredSize()); //Der adaptive Text wird zur Not verkleinert
                break;
            case Measure.AT_MOST: //Die vom adaptiven Text benötigte Größe nutzen solange diese nicht die Maximalgröße übersteigt
                if (constrains.getLimitations().width < adaptiveText.getExtent().width || constrains.getLimitations().height < adaptiveText.getExtent().height) {
                    setPreferredSize(constrains.getLimitations());
                    adaptiveText.update(constrains.getFont(), getPreferredSize());
                } else
                    setPreferredSize(adaptiveText.getExtent());
                break;
            case Measure.UNSPECIFIED: //TODO: Implement stuff
                setPreferredSize(adaptiveText.getExtent());
                break;
        }
    }

    @Override
    public void onDraw(Graphics2D graphics, Alignment alignment) {
        FontMetrics fontMetrics = graphics.getFontMetrics();

        Point align = getAlignedPosition(alignment, getBoundingBox(), adaptiveText.getExtent());

        graphics.setFont(getScaledFont(getContent(), getPreferredSize(), graphics.getFont()));
        graphics.setColor(isHovered() ? getContainer().getForeground() : getContainer().getForeground().brighter());
        graphics.drawString(getContent(), align.x, (int) (align.y + adaptiveText.getExtent().getHeight()));
    }
}

