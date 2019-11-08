package markingMenu;

//////////////////////////////////////////////////////////////////////////////

// file    : Paint.java
// content : basic painting app
//////////////////////////////////////////////////////////////////////////////

/* imports *****************************************************************/

import static java.lang.Math.*;

import java.util.Hashtable;
import java.util.Vector;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import java.awt.event.*;
import javax.swing.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.AbstractAction;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

/* paint *******************************************************************/

class Paint extends JFrame implements MouseInputListener {
    Vector<Shape> shapes = new Vector<Shape>();
    Hashtable<Shape, Color> colorShape = new Hashtable<Shape, Color>();
    MenuWidget menu;

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (tool != null) {
            tool.toolPressed(mouseEvent);
        }
        menu.openMenu(mouseEvent);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (tool != null) {
            tool.toolReleased(mouseEvent);
        }
        menu.closeMenu(mouseEvent);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (tool != null) {
            tool.toolDragged(mouseEvent);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    class Tool extends AbstractAction {
        Point o;
        Shape shape;

        public Tool(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("using tool " + this);
            tool = this;
        }

        public void toolPressed(MouseEvent e) {
            o = e.getPoint();
        }

        public void toolDragged(MouseEvent e) {
        }

        public void toolReleased(MouseEvent e) {
            shape = null;
        }

    }

    ;

    Tool tools[] = {new Tool("pen") {
        public void toolDragged(MouseEvent e) {
            Path2D.Double path = (Path2D.Double) shape;
            if (path == null) {
                path = new Path2D.Double();
                path.moveTo(o.getX(), o.getY());
                shapes.add(shape = path);

            }
            path.lineTo(e.getX(), e.getY());
            colorShape.put(path, selectedColor);
            panel.repaint();
        }
    }, new Tool("rect") {
        public void toolDragged(MouseEvent e) {
            Rectangle2D.Double rect = (Rectangle2D.Double) shape;
            if (rect == null) {
                rect = new Rectangle2D.Double(o.getX(), o.getY(), 0, 0);
                shapes.add(shape = rect);

            }

            rect.setRect(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
                    abs(e.getY() - o.getY()));
            colorShape.put(rect, selectedColor);
            panel.repaint();
        }
    }, new Tool("ellipse") {
        public void toolDragged(MouseEvent e) {
            Ellipse2D.Double ellipse = (Ellipse2D.Double) shape;
            if (ellipse == null) {
                ellipse = new Ellipse2D.Double(o.getX(), o.getY(), 0, 0);
                shapes.add(shape = ellipse);

            }

            ellipse.setFrame(min(e.getX(), o.getX()), min(e.getY(), o.getY()), abs(e.getX() - o.getX()),
                    abs(e.getY() - o.getY()));
            colorShape.put(ellipse, selectedColor);
            panel.repaint();
        }
    }};

    class Coloration extends AbstractAction {
        Color color;

        public Coloration(Color c, String name) {
            super(name);
            this.color = c;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selectedColor = this.color;
        }
    }

    Tool tool;

    Coloration[] colors = {new Coloration(Color.BLACK, "Noir"), new Coloration(Color.BLUE, "Bleu"), new Coloration(Color.RED, "Rouge"),
            new Coloration(Color.GREEN, "Vert"), new Coloration(Color.YELLOW, "Jaune")};

    JPanel panel;

    Color selectedColor = Color.BLACK;


    public Paint(String title) {
        super(title);
        addMouseListener(this);
        addMouseMotionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        this.
                add(new JToolBar() {
                    {
                        for (AbstractAction tool : tools) {
                            add(tool);
                        }
                        for (AbstractAction c : colors) {
                            add(c);
                        }

                    }
                }, BorderLayout.NORTH);
        add(panel = new JPanel() {

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());

                for (Shape shape : shapes) {
                    Color c = colorShape.get(shape);
                    System.out.println(c);
                    g2.setColor(c);
                    g2.draw(shape);
                }
                menu.drawMenu(g);
            }
        });
        menu = new MenuWidget(panel);
        pack();
        setVisible(true);
    }

    /* main *********************************************************************/

    public static void main(String argv[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Paint paint = new Paint("paint");

            }
        });
    }
}
