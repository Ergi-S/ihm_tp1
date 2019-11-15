package markingMenu;

//////////////////////////////////////////////////////////////////////////////

// file    : Paint.java
// content : basic painting app
//////////////////////////////////////////////////////////////////////////////

/* imports *****************************************************************/

import static java.lang.Math.*;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
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
import java.util.concurrent.Callable;
import javax.naming.NameAlreadyBoundException;
import javax.swing.*;
import javax.swing.event.*;

/* paint *******************************************************************/

class Paint extends JFrame implements MouseInputListener {
    Vector<Shape> shapes = new Vector<Shape>();
    Hashtable<Shape, Color> colorShape = new Hashtable<Shape, Color>(); //Assigne une couleur à une forme afin de pouvoir la redessiner avec la bonne couleur
    MenuWidget menu; //Marking menu

    enum State {IDLE, DROITE, GAUCHE}

    ;
    private State state = State.IDLE;

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == 1) {  //Test click gauche
            if (tool != null) {
                tool.toolPressed(mouseEvent);
            }
            state = State.GAUCHE;
        } else if (mouseEvent.getButton() == 3) { //Test click droit
            menu.openMenu(mouseEvent); //Ouvre le marking menu
            state = State.DROITE;
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (state == State.GAUCHE) {
            if (tool != null) {
                tool.toolReleased(mouseEvent);
            }
        } else if (state == State.DROITE) {
            menu.closeMenu(mouseEvent); //Ferme le marking menu
        }
        state = State.IDLE;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (state == State.GAUCHE) {
            if (tool != null) {
                tool.toolDragged(mouseEvent);
            }
        }
        if (state == State.DROITE) {
            try {
                menu.draggedMenu(mouseEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
    }, new Tool("ellipse") { //Forme cercle
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

    /*
     * Cette classe représente les couleurs qui sont assignées au forme
     * */
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


    public Paint(String title) throws Exception {
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
                        JCheckBox c = new JCheckBox("Mode Expert"); //Checkbox pour activer le mode expert
                        c.addItemListener(new ItemListener() {
                            @Override
                            public void itemStateChanged(ItemEvent itemEvent) {
                                menu.expert = !menu.expert;
                            }
                        });
                        add(c);
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
        Stage forme = new Stage("Forme"); //Menu Forme
        Stage couleur = new Stage("Couleur");//Menu Couleur
        Stage retourColor = new Stage("Retour");//Menu permettant le retour au menu principal
        Stage retourShape = new Stage("Retour");//Menu permettant le retour au menu principal

        menu.addStageToStage(menu.comp.getId(), forme); //On ajoute forme dans le menu principal
        menu.addStageToStage(menu.comp.getId(), couleur);//On ajoute couleur dans le menu principal
        for (Leaf l : createColorLeaf()) {
            menu.addLeafToStage(couleur.getId(), l); //On ajoute toutes nos couleurs dans le menu couleur
        }
        for (Leaf l : createLeafShape()) {
            menu.addLeafToStage(forme.getId(), l); //On ajoute toute nos formes dans le menu forme
        }
        menu.addStageToStage(couleur.getId(), retourColor); // ajout du menu retour dans couleur
        menu.addStageToStage(forme.getId(), retourShape); //ajout du menu retour dans forme
        menu.comp.setStageCourant(forme); //Défini le menu courant par défaut
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);

        pack();
        setVisible(true);
    }

    public List<Leaf> createColorLeaf() {
        LinkedList<Leaf> leaves = new LinkedList<>();
        leaves.add(new Leaf("Noir") {
            public void actionned() {
                selectedColor = Color.BLACK;
            }
        });
        leaves.add(new Leaf("Orange") {
            public void actionned() {
                selectedColor = Color.ORANGE;
            }
        });
        leaves.add(new Leaf("Rouge") {
            public void actionned() {
                selectedColor = Color.RED;
            }
        });
        leaves.add(new Leaf("Rose") {
            public void actionned() {
                selectedColor = Color.PINK;
            }
        });
        leaves.add(new Leaf("Jaune") {
            public void actionned() {
                selectedColor = Color.YELLOW;
            }
        });
        leaves.add(new Leaf("Vert") {
            public void actionned() {
                selectedColor = Color.GREEN;
            }
        });
        leaves.add(new Leaf("Bleu") {
            public void actionned() {
                selectedColor = Color.BLUE;
            }
        });

        return leaves;
    }

    public List<Leaf> createLeafShape() {
        LinkedList<Leaf> leaves = new LinkedList<Leaf>();
        leaves.add(new Leaf("Pen") {
            public void actionned() {
                tool = tools[0];
            }
        });
        leaves.add(new Leaf("Rect") {
            public void actionned() {
                tool = tools[1];
            }
        });
        leaves.add(new Leaf("Ellipse") {
            public void actionned() {
                tool = tools[2];
            }
        });

        return leaves;
    }


    /* main *********************************************************************/

    public static void main(String argv[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Paint paint = new Paint("paint");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
