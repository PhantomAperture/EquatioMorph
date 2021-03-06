package de.daviddiviad.render;

import java.awt.*;

/**
 * Created by David on 14.02.2017.
 */

public class Measure {

    /**
     * Measures an {@link HierarchicView} in a bounding box which may be filled at most.
     */
    public static final int AT_MOST = 0;

    /**
     * Measures an {@link HierarchicView} in a bounding box which must be filled exactly.
     */
    public static final int EXACTLY = 1;

    /**
     * Measures an {@link HierarchicView} without limitations by a bounding box.
     */
    public static final int UNSPECIFIED = 2;

    private static final Font DEFAULT_FONT = new Font("Tohama", Font.PLAIN, 24);

    private Dimension limitations;
    private int measureSpec;
    private Font font;

    public Measure(Dimension limitations) {
        this(limitations, EXACTLY);
    }

    public Measure(Dimension limitations, int measureSpec) {
        this.limitations = limitations;
        this.measureSpec = measureSpec;
        this.font = DEFAULT_FONT;
    }

    public Dimension getLimitations() {
        return limitations;
    }

    public int getMeasureSpec() {
        return measureSpec;
    }

    public void setMeasureSpec(int measureSpec) {
        this.measureSpec = measureSpec;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public String toString() {
        String representation = "Measure[";
        representation = representation.concat("measureSpec= " + (measureSpec == EXACTLY ? "exactly" : (measureSpec == AT_MOST ? "at_most" : "unspecified")) + ", ");
        representation = representation.concat("font= " + getFont().toString() + ", ");
        representation = representation.concat("limitations= " + getLimitations().toString() + "]");
        return representation;
    }

    public class MeasureException extends Exception {

    }
}
