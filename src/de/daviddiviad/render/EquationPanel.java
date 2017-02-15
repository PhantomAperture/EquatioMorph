package de.daviddiviad.render;

import de.daviddiviad.render.render.views.InputView;
import de.daviddiviad.structure.NodeIterator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by David on 13.02.2017.
 */

public class EquationPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

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
    private HierarchicView selected, hovered;
    private long lastMouseAction;

    public EquationPanel(HierarchicView rootView) {
        this.rootView = rootView;
        this.rootView.setContainer(this);
        addMouseMotionListener(this);
        addMouseListener(this);

        if (CM == null)
            CM = new Font("CMU Serif Roman", Font.PLAIN, 24);
        if (CAMBRIA_MATH == null)
            CAMBRIA_MATH = new Font("Cambria Math", Font.PLAIN, 24);

        setFont(CM);
        Timer timer = new Timer(1000 / 25, this);
        timer.start();
    }

    public HierarchicView getRootView() {
        return rootView;
    }

    public void setRootView(HierarchicView rootView) {
        this.rootView = rootView;
    }

    public HierarchicView getSelected() {
        return selected;
    }

    public void setSelected(HierarchicView selected) {
        if (this.selected != null) this.selected.setSelected(false);
        this.selected = selected;
        if (this.selected != null) this.selected.setSelected(true);
    }

    public HierarchicView getHovered() {
        return hovered;
    }

    public void setHovered(HierarchicView hovered) {
        if (this.hovered != null) this.hovered.setHovered(false);
        this.hovered = hovered;
        if (this.hovered != null) this.hovered.setHovered(true);
    }

    public void onMeasure() {
        try {
            Measure measure = new Measure(getSize());
            measure.setMeasureSpec(Measure.AT_MOST);
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

        Rectangle extent = new Rectangle(new Point(0, 0), getSize());

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

        NodeIterator viewIterator = rootView.iterator();
        while (viewIterator.hasNext()) {
            Graphics2D restoreGraphics = null;
            try {
                restoreGraphics = (Graphics2D) graphics.create();
                ((HierarchicView) viewIterator.next()).onDraw(restoreGraphics, Alignment.CENTERED);
            } finally {
                if (restoreGraphics != null) restoreGraphics.dispose();
            }
        }

        if (System.currentTimeMillis() - lastMouseAction >= 1000) {
            paintToolTip(graphics, selected);
        }
    }

    public void paintToolTip(Graphics2D graphics, HierarchicView hovered) {
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mouse, this);

        if (mouse != null) graphics.fillRect(mouse.x, mouse.y, 10, 10);
    }

    public HierarchicView getRaytracedView (Point point) {
        HierarchicView currView = this.rootView;
        while (currView.intersects(point)) {
            for (int i = 0; i < currView.size(); i++) {
                HierarchicView view = (HierarchicView) currView.getBranches().get(i);
                if (view.intersects(point)) {
                    currView = view;
                    break;
                }
            }
            break;
        }
        if (currView.intersects(point))
            return currView;
        else
            return null;
    }

    public static void main(String[] args) {
        HierarchicView a = new InputView();

        JFrame frame = new JFrame("EquationRenderer Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new EquationPanel(a));
        frame.setSize(480, 320);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        setSelected(getRaytracedView(e.getPoint()));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        lastMouseAction = System.currentTimeMillis();
        setHovered(getRaytracedView(e.getPoint()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
