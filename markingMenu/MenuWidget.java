package markingMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

public class MenuWidget extends JComponent {
    private Ellipse2D.Double menu;
    private int idCourant = 0;
    Stage comp;
    Stage main;
    JPanel panel;
    int eX, eY = 0;
    public boolean expert;


    public MenuWidget(Ellipse2D.Double m) {
        this.menu = m;
    }

    public MenuWidget(JPanel panel) {
        super();
        this.panel = panel;
        this.expert = false;
        Stage mainStage = new Stage("Main");
        mainStage.setId(idCourant);
        idCourant++;
        this.comp = mainStage;
        Stage tmp = new Stage("Tempo");
        tmp.setId(idCourant);
        idCourant++;
        this.main = tmp;
        this.menu = new Ellipse2D.Double();
        this.setSize(200, 200);
        this.setVisible(false);
    }

    public void addLeafToStage(int stage, Leaf leaf) throws Exception {
        Stage s = this.comp.getStage(stage);
        if (s == null) {
            throw new IllegalArgumentException("Stage " + stage + " introuvable");
        }
        leaf.setId(idCourant);
        idCourant++;
        s.addLeaf(leaf);
    }

    public void addLeaf(Leaf l) throws Exception {
        l.setId(idCourant);
        idCourant++;
        this.comp.addLeaf(l);
    }

    public void addStageToStage(int parentStage, Stage stage) throws Exception {
        Stage s = this.comp.getStage(parentStage);
        if (s == null) {
            throw new IllegalArgumentException("Stage " + parentStage + " introuvable");
        }
        stage.setId(idCourant);
        idCourant++;
        s.addStage(stage);
    }

    public void addStage(Stage stage) throws Exception {
        stage.setId(idCourant);
        idCourant++;
        this.comp.addStage(stage);
    }

    public void drawMenu(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (this.isVisible()) {
            if (!this.expert) {
                this.comp.draw(g2);
            }
        }
    }

    public void draggedMenu(MouseEvent e) throws Exception {
        eX = e.getX();
        eY = e.getY();
        LinkedList<MenuComponent> m = new LinkedList<MenuComponent>();
        for (Stage s : this.comp.getStages()) {
            m.add(s);
        }
        for (Leaf l : this.comp.getLeaves()) {
            m.add(l);
        }
        if (menu.contains(e.getPoint())) {
            for (MenuComponent c : m) {
                if (c.getArc().contains(e.getPoint())) {
                    this.comp.setStageCourant(c);
                }
            }
        } else {
            MenuComponent selected = this.comp.getStageCourant();
            if (selected instanceof Leaf) {
                ((Leaf) selected).actionned();
            } else {
                Stage s = (Stage) selected;
                if (s.getName() == "Retour") {
                    this.comp = main;
                    this.menu.setFrame(eX - 100, eY - 100, 200, 200);
                    this.setAllArcs();
                } else if (s.length() > 0) {
                    if (main.getName() == "Tempo") {
                        this.main = comp;
                    }
                    this.comp = s;
                    if (s.nbStages() > 0) {
                        this.comp.setStageCourant(s.getStages().get(0));
                    } else {
                        this.comp.setStageCourant(s.getLeaves().get(0));
                    }
                    this.menu.setFrame(eX - 100, eY - 100, 200, 200);
                    this.setAllArcs();
                }

            }
        }
        panel.repaint();
    }

    public void openMenu(MouseEvent e) {
        eX = e.getPoint().x;
        eY = e.getPoint().y;
        this.menu.setFrame(eX - 100, eY - 100, 200, 200);
        if (main.getName() != "Tempo") {
            this.comp = main;
        }
        this.setAllArcs();

        if (e.getButton() == 3) {
            System.out.println(e.getButton());

            this.setVisible(true);
            panel.repaint();
        }

    }

    public void setAllArcs() {
        int angle = 0;
        float degree = 360 / this.comp.length();
        for (Stage s : this.comp.getStages()) {
            System.out.println(s.getName());
            s.setArc(menu.getBounds(), angle, degree);
            angle += degree;
        }
        for (Leaf l : this.comp.getLeaves()) {
            l.setArc(menu.getBounds(), angle, degree);
            angle += degree;
        }

    }


    public void closeMenu(MouseEvent e) {
        // TODO Auto-generated method stub
        if (e.getButton() == 3) {
            this.setVisible(false);
            panel.repaint();
        }

    }

}
