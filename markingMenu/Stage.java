package markingMenu;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Stage extends MenuComponent {
    private List<Stage> stages;
    private List<Leaf> leaves;
    private MenuComponent Courant;

    public Stage(String name) {
        super(name);
        stages = new LinkedList<Stage>();
        leaves = new LinkedList<Leaf>();
    }

    public void addStage(Stage s) throws Exception {
        if (this.length() >= MAX_ITEM) {
            throw new Exception("This stage is full");
        }
        this.stages.add(s);
    }

    public Stage getStage(int id) {
        if (this.getId() == id) {
            return this;
        }
        for (Stage s : stages) {
            Stage res = s.getStage(id);
            if (res != null) {
                return res;
            }
        }
        return null;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public List<Leaf> getLeaves() {
        return leaves;
    }

    public void addLeaf(Leaf l) throws Exception {
        if (this.length() >= MAX_ITEM) {
            throw new Exception("This stage is full");
        }
        this.leaves.add(l);
    }

    public Leaf getLeaf(int id) {
        Iterator<Leaf> iter = leaves.iterator();
        while (iter.hasNext()) {
            Leaf l = iter.next();
            if (l.getId() == id) {
                return l;
            }
        }
        for (Stage s : stages) {
            Leaf res = s.getLeaf(id);
            if (res != null) {
                return res;
            }
        }
        return null;
    }

    public MenuComponent getStageCourant() {
        return Courant;
    }

    public void setStageCourant(MenuComponent stageCourant) {
        this.Courant = stageCourant;
    }

    public int length() {
        return nbLeaves() + nbStages();
    }

    public int nbLeaves() {
        return this.leaves.size();
    }

    public int nbStages() {
        return this.stages.size();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        int xName;
        int yName;
        String name;
        Rectangle2D font;
        Arc2D.Double arc;

        for (Stage s : this.stages) {
            if (s == Courant) {
                g.setColor(Color.BLUE);
            }
            name = s.getName();
            font = g.getFontMetrics().getStringBounds(name, g);
            arc = s.getArc();
            xName = (int) ((arc.width/4)*Math.cos(Math.toRadians(arc.start+arc.extent/2)));
            yName = (int) ((arc.height/4)*Math.sin(Math.toRadians(arc.start+arc.extent/2)));
            g.draw(arc);
            g.drawString(name,(int)(arc.getCenterX() - font.getWidth()/2+xName),(int)(arc.getCenterY()-yName));
            g.setColor(Color.BLACK);
        }
        for (Leaf l : this.leaves) {
            if (l == Courant) {
                g.setColor(Color.BLUE);
            }
            name = l.getName();
            font = g.getFontMetrics().getStringBounds(name, g);
            arc = l.getArc();
            xName = (int) ((arc.width/4)*Math.cos(Math.toRadians(arc.start+arc.extent/2)));
            yName = (int) ((arc.height/4)*Math.sin(Math.toRadians(arc.start+arc.extent/2)));
            g.draw(arc);
            g.drawString(name,(int)(arc.getCenterX() - font.getWidth()/2+xName),(int)(arc.getCenterY()-yName));
            g.setColor(Color.BLACK);
        }
    }

}
