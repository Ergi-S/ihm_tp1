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

import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class MenuWidget extends JComponent {
    private Ellipse2D.Double menu;
    private final int MAX_ITEMS = 8;
    HashMap<String, MenuWidget> items;
    HashMap<String, Ellipse2D.Double> itemsShape;
    JPanel panel;
    int eX,eY = 0;


    public MenuWidget(Ellipse2D.Double m) {
        this.menu = m;
    }

    public MenuWidget(JPanel panel) {
        super();
        this.panel = panel;
        items = new HashMap<String, MenuWidget>();
        itemsShape = new HashMap<String, Ellipse2D.Double>();
        items.put("AA", null);
        items.put("AB", null);
        items.put("AC", null);
        this.menu = new Ellipse2D.Double();
        this.setSize(100, 100);
        this.setVisible(false);
    }

    public void drawMenu(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        System.out.println("Paint");
        if (this.isVisible()) {
            g2.setColor(Color.RED);
            g2.draw(new Arc2D.Double(menu.getBounds(),0,180, Arc2D.PIE));
            g2.setColor(Color.BLUE);
            g2.draw(new Arc2D.Double(menu.getBounds(),180,180, Arc2D.PIE));
            g2.setColor(Color.BLACK);
            g2.drawString("Couleur",(float)(menu.getCenterX() - g2.getFontMetrics().stringWidth("Couleur")/2), (float) (menu.getCenterY() + 20));
            g2.drawString("Forme",(float)(menu.getCenterX() - g2.getFontMetrics().stringWidth("Forme")/2), (float) (menu.getCenterY() - 15));
            System.out.println("Visible");
        }


    }

    public void draggedMenu(MouseEvent e){
        eX = e.getX();
        eY = e.getY();
        if(!menu.contains(e.getPoint()) && e.getY() > menu.getCenterY() + 40) {
            this.menu.setFrame(eX-40, eY-40, 80, 80);
        }
        if(!menu.contains(e.getPoint()) && e.getY() < menu.getCenterY() - 40) {
            System.out.println("Forme");
        }
        panel.repaint();
    }

    public void openMenu(MouseEvent e) {
         eX = e.getPoint().x;
         eY = e.getPoint().y;
        System.out.println("Open Menu");

        if (e.getButton() == 3) {
            System.out.println(e.getButton());
            this.menu.setFrame(eX-40, eY-40, 80, 80);

            this.setVisible(true);
            panel.repaint();
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
