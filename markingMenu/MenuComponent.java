package markingMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

public abstract class MenuComponent extends JComponent {

    private Arc2D.Double arc;
    private String name;
    private int id;
    protected final int MAX_ITEM = 8;

    public MenuComponent( String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setArc(Rectangle rect, int debut, float extend){
        this.arc = new Arc2D.Double(rect,(float)debut,extend,Arc2D.PIE);
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
