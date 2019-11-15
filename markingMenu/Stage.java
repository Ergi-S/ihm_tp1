package markingMenu;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Stage extends MenuComponent {
    private List<Stage> stages; //Les stages sont les éléments du menu permettant de se diriger vers un autre menu
    private List<Leaf> leaves; // Les leaves sont les éléments du menu permettant de réaliser une action
    private MenuComponent Courant; //L'élement du menu actuellement selectionné

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

        for (Stage s : this.stages) { //on dessine tous les stages
            if (s == Courant) {
                g.setColor(Color.BLUE); //élément sélectionné est dessiné en bleu
            }
            name = s.getName();
            font = g.getFontMetrics().getStringBounds(name, g); //Récupère la dimension du string name
            arc = s.getArc();
            xName = (int) ((arc.width/4)*Math.cos(Math.toRadians(arc.start+arc.extent/2))); // Calcule l'emplacement en x du string name en fonction de la postion de l'arc
            yName = (int) ((arc.height/4)*Math.sin(Math.toRadians(arc.start+arc.extent/2))); // Calcule l'emplacement en y du string name en fonction de la position de l'arc
            g.draw(arc);
            g.drawString(name,(int)(arc.getCenterX() - font.getWidth()/2+xName),(int)(arc.getCenterY()-yName)); //dessine le string
            g.setColor(Color.BLACK);
        }
        for (Leaf l : this.leaves) { //Même chose pour les feuilles
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
