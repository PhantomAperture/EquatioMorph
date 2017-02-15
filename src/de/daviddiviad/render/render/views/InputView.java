package de.daviddiviad.render.render.views;

import de.daviddiviad.render.Alignment;
import de.daviddiviad.render.HierarchicView;
import de.daviddiviad.render.Measure;

import java.awt.*;

/**
 * Created by David on 15.02.2017.
 */

public class InputView extends HierarchicView {

    private static Dimension PREFERRED_SIZE = new Dimension(20, 30);

    private static Stroke DASHED_STROKE = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);

    private Color borderColor;
    private int frame;

    public InputView() {
        this.borderColor = new Color(140, 140, 200);
    }

    @Override
    public void onMeasure(Measure constrains) throws Measure.MeasureException {
        switch (constrains.getMeasureSpec()) {
            case Measure.EXACTLY:
                setPreferredSize(constrains.getLimitations());
                break;
            case Measure.AT_MOST:
                setPreferredSize(getEnclosingDimension(PREFERRED_SIZE, constrains.getLimitations()));
                break;
            case Measure.UNSPECIFIED:
                setPreferredSize(PREFERRED_SIZE);
                break;
        }
    }

    @Override
    public void onDraw(Graphics2D graphics, Alignment alignment) {
        if (isSelected() && frame > 12) {
            graphics.setColor(Color.BLACK);
            int x = (int) (getBoundingBox().x + getBoundingBox().width * 0.5);
            int y = (int) (getBoundingBox().getY() + 0.5 * (getBoundingBox().getHeight() - PREFERRED_SIZE.height));
            graphics.drawLine(x, y + 4, x, y + PREFERRED_SIZE.height - 4);
        }

        graphics.setColor(getBorderColor());
        graphics.setStroke(DASHED_STROKE);
        graphics.drawRect(getBoundingBox().x, getBoundingBox().y, getBoundingBox().width, getBoundingBox().height);

        frame++;
        frame%=24;

    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
