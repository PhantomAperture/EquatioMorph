package de.daviddiviad.render;

import de.daviddiviad.render.render.views.TextView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by David on 13.02.2017.
 */

public class EquationPanel extends JPanel {

    static {
        try {
            URL url = EquationPanel.class.getClassLoader().getResource("cmunrm.ttf");
            if (url != null) {
                File file = new File(url.getFile());
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, file));
            }
        } catch (FontFormatException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Font CM;
    private static Font CAMBRIA_MATH;

    private HierarchicView rootView;

    public EquationPanel(HierarchicView rootView) {
        this.rootView = rootView;

        if (CM == null)
            CM = new Font("CMU Serif Roman", Font.PLAIN, 24);
        if (CAMBRIA_MATH == null)
            CAMBRIA_MATH = new Font("Cambria Math", Font.PLAIN, 24);

        setFont(CM);
    }


    public void onMeasure() {
        try {
            Measure measure = new Measure(new Dimension(200, 80));
            measure.setMeasureSpec(Measure.EXACTLY);
            measure.setFont(getFont());
            rootView.onMeasure(measure);
        } catch (Measure.MeasureException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();
        onMeasure();

        Dimension size = new Dimension(200, 80);
        Rectangle extent = new Rectangle(HierarchicView.getAlignedPosition(Alignment.CENTERED, getBounds(), size), size);

        rootView.onLayout(extent, Alignment.CENTERED);
        //Layout components
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;

        //Enable AA and set rendering quality to maximum
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        //Reset font and color
        graphics.setFont(getFont());
        graphics.setColor(Color.BLACK);

        rootView.onDraw(graphics, Alignment.CENTERED);
    }

    public static void main(String[] args) {
        HierarchicView a = new TextView(null, "Das ist ein Test Stringα");
        System.out.println(a.toTreeString());

        JFrame frame = new JFrame("EquationRenderer Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new EquationPanel(a));
        frame.setSize(480, 320);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
