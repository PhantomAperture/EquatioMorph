package de.daviddiviad.render.render.views;

import de.daviddiviad.render.Alignment;
import de.daviddiviad.render.HierarchicView;
import de.daviddiviad.render.Measure;
import de.daviddiviad.structure.Node;

import java.awt.*;

/**
 * Created by David on 14.02.2017.
 */

public class TextView extends HierarchicView<String> {

    public TextView(Node parent, String content) {
        super(parent, content);
    }

    @Override
    public void onMeasure(Measure constrains) throws Measure.MeasureException {
        switch (constrains.getMeasureSpec()) {
            case Measure.EXACTLY: //Use max size
                setPreferredSize(new Dimension(constrains.getExtent().width, constrains.getExtent().height));
                setMinimalSize(getFittingBoundingBox(getContent(), constrains.getFont()));
                break;
            case Measure.AT_MOST: //Calculate needed space in font and use lower values
                setPreferredSize(new Dimension(Math.min(constrains.getExtent().width, getMinimalSize().width), Math.min(constrains.getExtent().height, getMinimalSize().height)));
                break;
            case Measure.UNSPECIFIED: //Just use needed space
                setPreferredSize(getMinimalSize());
                break;
        }
    }

    private Dimension getNeededSpace (Font font) {
        return getFittingBoundingBox(getContent(), font);
    }

    @Override
    public void onDraw(Graphics2D graphics, Alignment alignment) {
        FontMetrics fontMetrics = graphics.getFontMetrics();

        Point align = getAlignedPosition(alignment, getBoundingBox(), getMinimalSize());

        Font original = graphics.getFont();
        graphics.setFont(getScaledFont(getContent(), getPreferredSize(), original));
        System.out.println(graphics.getFont());

        graphics.setColor(Color.RED);
        graphics.fillRect(getBoundingBox().x, getBoundingBox().y, (int) getBoundingBox().getWidth(), (int) getBoundingBox().getHeight());

        graphics.setColor(Color.ORANGE);
        graphics.fillRect(align.x, align.y, (int) getMinimalSize().getWidth(), (int) getMinimalSize().getHeight());

        graphics.setColor(Color.BLACK);
        graphics.drawString(getContent(), align.x, (int) (align.y + getMinimalSize().getHeight()));

        graphics.setFont(original);
    }
}

