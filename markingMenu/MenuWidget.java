package markingMenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.naming.NameAlreadyBoundException;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class MenuWidget extends JComponent {
    private Ellipse2D.Double menu;
    private final int MAX_ITEMS = 8;
    private int nbItem = 0;
    Stage comp;
    Stage main;
    HashMap<String, Arc2D.Double> items;
    HashMap<String, Ellipse2D.Double> itemsShape;
    Stage courant;
    JPanel panel;
    int eX, eY = 0;


    public MenuWidget(Ellipse2D.Double m) {
        this.menu = m;
    }

    public MenuWidget(JPanel panel) {
        super();
        this.panel = panel;
        this.comp = new Stage("Main");
        this.main = new Stage("Tempo");
        items = new HashMap<String, Arc2D.Double>();
        itemsShape = new HashMap<String, Ellipse2D.Double>();
        this.menu = new Ellipse2D.Double();
        nbItem = items.size();
        this.setSize(100, 100);
        this.setVisible(false);
    }

    public void addLeafToStage(String stage, Leaf leaf) throws Exception {
         Stage s = this.comp.getStage(stage);
         if(s == null){
             throw new IllegalArgumentException("Stage " + stage + " introuvable");
         }
         if(this.comp.getLeaf(leaf.getName()) != null){
             throw new IllegalArgumentException("Leaf " + leaf.getName() + " déja existant");
         }
         s.addLeaf(leaf);
    }
    public void addLeaf(Leaf l) throws Exception {
        this.comp.addLeaf(l);
    }
    public void addStageToStage(String parentStage, Stage stage) throws Exception {
        Stage s = this.comp.getStage(parentStage);
        if(s==null){
            throw new IllegalArgumentException("Stage " + parentStage +" introuvable");
        }
        if(this.comp.getStage(stage.getName()) != null){
            throw new IllegalArgumentException("Stage "+ stage.getName()+" déja existant");
        }
        s.addStage(stage);
    }

    public void addStage(Stage stage) throws Exception {
        this.comp.addStage(stage);
    }

    public void drawMenu(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (this.isVisible()) {
            this.comp.draw(g2);
        }


    }

    public void draggedMenu(MouseEvent e) throws Exception {
        eX = e.getX();
        eY = e.getY();
        LinkedList<MenuComponent> m = new LinkedList<MenuComponent>();
        for(Stage s : this.comp.getStages()){
            m.add(s);
        }
        for(Leaf l : this.comp.getLeaves()){
            m.add(l);
        }
        if(menu.contains(e.getPoint())){
            for(MenuComponent c : m){
                if(c.getArc().contains(e.getPoint())){
                    this.comp.setStageCourant(c);
                }
            }
        } else {
            MenuComponent selected = this.comp.getStageCourant();
            if(selected instanceof Leaf){
                ((Leaf) selected).actionned();
            } else {
                Stage s = (Stage) selected;
                if(s.length() > 0) {
                    if(main.getName() == "Tempo"){
                        this.main = comp;
                    }
                    this.comp = s;
                    if(s.nbStages() > 0){
                        this.comp.setStageCourant(s.getStages().get(0));
                    } else {
                        this.comp.setStageCourant(s.getLeaves().get(0));
                    }
                    this.menu.setFrame(eX-40,eY-40,80,80);
                    this.setAllArcs();
                }

            }
        }
        panel.repaint();
    }

    public void openMenu(MouseEvent e) {
        eX = e.getPoint().x;
        eY = e.getPoint().y;
        this.menu.setFrame(eX-40,eY-40,80,80);
        if(main.getName() != "Tempo"){
            this.comp = main;
        }
        this.setAllArcs();

        //arcCourant = items.get("Forme");

        if (e.getButton() == 3) {
            System.out.println(e.getButton());

            this.setVisible(true);
            panel.repaint();
        }

    }

    public void setAllArcs(){
        int angle = 0;
        int degree = 360/this.comp.length();
        for (Stage s: this.comp.getStages()) {
            System.out.println(s.getName());
            s.setArc(menu.getBounds(),angle,degree);
            angle+=degree;
        }
        for(Leaf l : this.comp.getLeaves()){
            l.setArc(menu.getBounds(),angle,degree);
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
