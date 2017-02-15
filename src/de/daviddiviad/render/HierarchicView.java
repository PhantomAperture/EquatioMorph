package de.daviddiviad.render;

import de.daviddiviad.structure.Node;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by David on 13.02.2017.
 */
public abstract class HierarchicView<T> extends Node<T, HierarchicView> {

    private Dimension minimalSize;
    private Dimension preferredSize;
    private Rectangle boundingBox;

    public HierarchicView() {
        //Null
    }

    public HierarchicView(Node parent) {
        super(parent);
    }

    public HierarchicView(T content) {
        super(content);
    }

    public HierarchicView(Node parent, T content) {
        super(parent, content);
    }

    /**
     * Measures entities in depth-first traversal. Each {@link HierarchicView} receives constrains e.g. limitations
     * which must be respected during the calculation of the preferred size of that HierarchicView. This method is
     * possibly called more than one time to obtain best render quality. After calculating its own preferred size, the
     * HierarchicView must pass the measurement command and newly calculated constrains to all of its children which
     * then measure themselves again.
     *
     * @param constrains The constrains that are passed from the object containing this {@link HierarchicView}
     * @throws Measure.MeasureException An MeasureException is thrown if an error occurs during measurement
     */
    public abstract void onMeasure(Measure constrains) throws Measure.MeasureException;


    public abstract void onDraw(Graphics2D graphics, Alignment alignment);

    /**
     * Layouts components in depth-first traversal. After all entities have been measured successfully the top level
     * entity starts to lay out its children. Therefore it allocates the space needed to render and passes the layout
     * command to its children, again in depth-first order. The entity's children will may be laid out on top of it and
     * are therefore existent on different positions on the z-axis.
     * @param extent
     * @param alignment
     */
    public void onLayout(Rectangle extent, Alignment alignment) {
        /*
        Auswählen, welche Limitierungen für das letztendliche Layout genutzt werden sollen. Diese können entweder direkt übergeben
        werden, oder werden aus der BoundingBox der innewohnenden Entität entnommen. Innerhalb dieser Limitierung wird die BoundingBox, nach der
        übergebenen Ausrichtung einer Position zugewiesen. Das Verfahren arbeitet über das layer-first-traversal Verfahren.
         */
        extent = extent == null ? ((HierarchicView) getParent()).getBoundingBox() : extent;
        setBoundingBox(new Rectangle(getAlignedPosition(alignment, extent, getPreferredSize()), getPreferredSize()));
    }

    /**
     * Measures all of this {@link HierarchicView}'s branches using the same constrains
     * @param constrains
     * @throws Measure.MeasureException
     */
    public void measureBranches(Measure constrains) throws Measure.MeasureException {
        for (Node node : getBranches()) {
            ((HierarchicView) node).onMeasure(constrains);
        }
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }

    protected void setPreferredSize(Dimension preferredSize) {
        this.preferredSize = preferredSize;
    }

    public Dimension getMinimalSize() {
        return minimalSize;
    }

    public void setMinimalSize(Dimension minimalSize) {
        this.minimalSize = minimalSize;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public static Point getAlignedPosition(Alignment alignment, Rectangle container, Dimension entity) {
        if (alignment == null)
            alignment = Alignment.CENTERED;
        if (container == null)
            throw new IllegalArgumentException();
        if (entity == null)
            entity = new Dimension(0, 0);

        int x = 0, y = 0;
        switch (alignment.getHorizontalConstrains()) {
            case Alignment.ALIGN_LEADING:
                x = (int) container.getX();
                break;
            case Alignment.ALIGN_TRAILING:
                x = (int) (container.getX() + container.getWidth() - entity.getWidth());
                break;
            case Alignment.ALIGN_CENTER:
                x = (int) (container.getX() + 0.5 * (container.getWidth() - entity.getWidth()));
                break;
        }

        switch (alignment.getVerticalConstrains()) {
            case Alignment.ALIGN_LEADING:
                y = (int) container.getY();
                break;
            case Alignment.ALIGN_TRAILING:
                y = (int) (container.getY() + container.getHeight() - entity.getHeight());
                break;
            case Alignment.ALIGN_CENTER:
                y = (int) (container.getY() + 0.5 * (container.getHeight() - entity.getHeight()));
                break;
        }

        return new Point(x, y);
    }

    public static Font getScaledFont (String content, Dimension extent, Font font) {
        FontMetrics fontMetrics = getFontMetrics(font);
        float scaleX = ((float) extent.width / fontMetrics.stringWidth(content));
        float scaleY = ((float) extent.height / fontMetrics.getAscent());
        if (scaleX == 1 && scaleY == 1)
            return font;
        else
            return font.deriveFont(Math.min(scaleX, scaleY) * font.getSize());
    }

    public static Dimension getFittingBoundingBox(String content, Dimension extent, Font font) {
        FontMetrics fontMetrics = getFontMetrics(font);
        return new Dimension(fontMetrics.stringWidth(content), fontMetrics.getAscent());
    }

    public static final HashMap<Font, FontMetrics> FONT_METRICS = new HashMap<>();

    public static FontMetrics getFontMetrics(Font font){
        if (FONT_METRICS.containsKey(font))
            return FONT_METRICS.get(font);
        else {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration config = gd.getDefaultConfiguration();

            Canvas c = new Canvas(config);
            FontMetrics fontMetrics = c.getFontMetrics(font);
            FONT_METRICS.put(font, fontMetrics);
            return fontMetrics;
        }
    }
}
