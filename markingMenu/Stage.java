package markingMenu;

import javax.naming.NameAlreadyBoundException;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
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
        if(this.length()>= MAX_ITEM){
            throw new Exception("This stage is full");
        }
        if (this.getStage(s.getName()) != null) {
            throw new NameAlreadyBoundException();
        }
        this.stages.add(s);
    }

    public Stage getStage(String name) {
        if(this.getName() == name){
            return this;
        }
        for (Stage s : stages) {
            Stage res = s.getStage(name);
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
        if(this.length()>= MAX_ITEM){
            throw new Exception("This stage is full");
        }
        if (this.getLeaf(l.getName()) != null) {
            throw new NameAlreadyBoundException();
        }
        this.leaves.add(l);
    }

    public Leaf getLeaf(String name) {
        Iterator<Leaf> iter = leaves.iterator();
        while (iter.hasNext()) {
            Leaf l = iter.next();
            if (l.getName() == name) {
                return l;
            }
        }
        for (Stage s : stages) {
            Leaf res = s.getLeaf(name);
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
        for (Stage s : this.stages) {
            if(s == Courant){
                g.setColor(Color.BLUE);
            }
            System.out.println(s.getName());
            g.draw(s.getArc());
            g.setColor(Color.BLACK);
        }
        for (Leaf l : this.leaves) {
            if(l==Courant){
                g.setColor(Color.BLUE);
            }
            g.draw(l.getArc());
            g.setColor(Color.BLACK);
        }
    }

}
