package de.daviddiviad.render;

/**
 * Created by David on 14.02.2017.
 */

public class Alignment {

    /**
     * The {@link HierarchicView}'s content will be aligned leading (horizontally left and vertically on top).
     */
    public static final int ALIGN_LEADING = 3;

    /**
     * The {@link HierarchicView}'s content will be aligned trailing (horizontally right and vertically on the bottom).
     */
    public static final int ALIGN_TRAILING = 4;

    /**
     * The {@link HierarchicView}'s content will be aligned centered.
     */
    public static final int ALIGN_CENTER = 5;

    public static final Alignment CENTERED = new Alignment(ALIGN_CENTER, ALIGN_CENTER);

    private int verticalConstrains, horizontalConstrains;

    public Alignment(int horizontalConstrains, int verticalConstrains) {
        this.verticalConstrains = verticalConstrains;
        this.horizontalConstrains = horizontalConstrains;
    }

    public int getVerticalConstrains() {
        return verticalConstrains;
    }

    public void setVerticalConstrains(int verticalConstrains) {
        this.verticalConstrains = verticalConstrains;
    }

    public int getHorizontalConstrains() {
        return horizontalConstrains;
    }

    public void setHorizontalConstrains(int horizontalConstrains) {
        this.horizontalConstrains = horizontalConstrains;
    }

    @Override
    public String toString() {
        String representation = "[";
        representation = representation.concat("horizontal= " + (horizontalConstrains == ALIGN_LEADING ? "leading" : (horizontalConstrains == ALIGN_CENTER ? "center" : "trailing")) + ", ");
        representation = representation.concat("vertical= " + (verticalConstrains == ALIGN_LEADING ? "leading" : (verticalConstrains == ALIGN_CENTER ? "center" : "trailing")) + "]");
        return representation;
    }
}
