package markingMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

public abstract class MenuComponent extends JComponent {

    private JPanel panel;
    private Arc2D.Double arc;
    private String name;
    protected final int MAX_ITEM = 8;

    public MenuComponent( String name){
        this.name = name;
    }

    public void setArc(Rectangle rect, int debut, int extend){
        this.arc = new Arc2D.Double(rect,debut,extend,Arc2D.PIE);
    }

    public Arc2D.Double getArc(){
        return arc;
    }

    public void draw(Graphics2D g){
        g.draw(arc);
    }

    @Override
    public String getName() {
        return name;
    }
}
