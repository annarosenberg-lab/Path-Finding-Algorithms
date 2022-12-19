

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class AStarNode implements Comparable<AStarNode>{
    private Point pos;
    private int g;
    private int h;
    private int f;
    private AStarNode parent;

    public AStarNode (Point pos, int g, int h, int f, AStarNode parent){
        this.pos = pos;
        this.g = g;
        this.h = h;
        this.f = f;
        this.parent = parent;
    }

    public int getG(){return g;}
    public int getH(){return h;}
    public int getF(){return f;}
    public AStarNode getParent(){return parent;}
    public Point getPos(){return pos;}



    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        if (o.getClass() != this.getClass())
            return false;
        if (this.parent == null)
            return false;

        AStarNode other = (AStarNode)o;
        return this.g == other.g && h == other.h && f == other.f &&  this.pos.equals(other.pos) && this.parent.equals(other.parent);
    }


    @Override
    public int compareTo(AStarNode n) {
        if (f < n.f)
            return -1;
        if (f > n.f)
            return 1;
        else {
            if (g < n.g)
                return 1;
            if (g > n.g)
                return -1;
            return 0;
        }

    }
}

