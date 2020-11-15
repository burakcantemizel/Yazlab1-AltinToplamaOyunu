package com.ozgeburak.yazilimlaboratuvari1;

 
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
 
class AStar {
    private final List<Dugum> acikKume;
    private final List<Dugum> kapaliKume;
    private final List<Dugum> yol;
    private final int[][] harita;
    private Dugum mevcutDugum;
    private final int baslangicX;
    private final int baslangicY;
    private int bitisX, bitisY;
 
    // Dugum class for convienience
    static class Dugum implements Comparable {
        public Dugum parent;
        public int x, y;
        public double g;
        public double h;
        Dugum(Dugum parent, int xpos, int ypos, double g, double h) {
            this.parent = parent;
            this.x = xpos;
            this.y = ypos;
            this.g = g;
            this.h = h;
       }
       // Compare by f value (g + h)
       @Override
       public int compareTo(Object o) {
           Dugum that = (Dugum) o;
           return (int)((this.g + this.h) - (that.g + that.h));
       }
   }
 
    AStar(int[][] maze, int xstart, int ystart, boolean diag) {
        this.acikKume = new ArrayList<>();
        this.kapaliKume = new ArrayList<>();
        this.yol = new ArrayList<>();
        this.harita = maze;
        this.mevcutDugum = new Dugum(null, xstart, ystart, 0, 0);
        this.baslangicX = xstart;
        this.baslangicY = ystart;
    }
    /*
    ** Finds yol to bitisX/yend or returns null
    **
    ** @param (int) bitisX coordinates of the target position
    ** @param (int) bitisY
    ** @return (List<Node> | null) the yol
    */
    public List<Dugum> findPathTo(int xend, int yend) {
        this.bitisX = xend;
        this.bitisY = yend;
        this.kapaliKume.add(this.mevcutDugum);
        addNeigborsToOpenList();
        while (this.mevcutDugum.x != this.bitisX || this.mevcutDugum.y != this.bitisY) {
            if (this.acikKume.isEmpty()) { // Nothing to examine
                return null;
            }
            this.mevcutDugum = this.acikKume.get(0); // get first node (lowest f score)
            this.acikKume.remove(0); // remove it
            this.kapaliKume.add(this.mevcutDugum); // and add to the kapaliKume
            addNeigborsToOpenList();
        }
        this.yol.add(0, this.mevcutDugum);
        while (this.mevcutDugum.x != this.baslangicX || this.mevcutDugum.y != this.baslangicY) {
            this.mevcutDugum = this.mevcutDugum.parent;
            this.yol.add(0, this.mevcutDugum);
        }
        return this.yol;
    }
    /*
    ** Looks in a given List<> for a node
    **
    ** @return (bool) NeightborInListFound
    */
    private static boolean findNeighborInList(List<Dugum> array, Dugum node) {
        return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
    }
    /*
    ** Calulate distance between this.mevcutDugum and bitisX/yend
    **
    ** @return (int) distance
    */
    private double distance(int dx, int dy) {
            return Math.abs(this.mevcutDugum.x + dx - this.bitisX) + Math.abs(this.mevcutDugum.y + dy - this.bitisY); // else return "Manhattan distance"
    }
    private void addNeigborsToOpenList() {
        Dugum node;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if ( x != 0 && y != 0) {
                    continue; // skip if diagonal movement is not allowed
                }
                node = new Dugum(this.mevcutDugum, this.mevcutDugum.x + x, this.mevcutDugum.y + y, this.mevcutDugum.g, this.distance(x, y));
                if ((x != 0 || y != 0) // not this.mevcutDugum
                    && this.mevcutDugum.x + x >= 0 && this.mevcutDugum.x + x < this.harita[0].length // check harita boundaries
                    && this.mevcutDugum.y + y >= 0 && this.mevcutDugum.y + y < this.harita.length
                    && this.harita[this.mevcutDugum.y + y][this.mevcutDugum.x + x] != -1 // check if square is walkable
                    && !findNeighborInList(this.acikKume, node) && !findNeighborInList(this.kapaliKume, node)) { // if not already done
                        node.g = node.parent.g + 1.; // Horizontal/vertical cost = 1.0
                        node.g += harita[this.mevcutDugum.y + y][this.mevcutDugum.x + x]; // add movement cost for this square
 
                        // diagonal cost = sqrt(hor_cost² + vert_cost²)
                        // in this example the cost would be 12.2 instead of 11
                        /*
                        if (diag && x != 0 && y != 0) {
                            node.g += .4;	// Diagonal movement cost = 1.4
                        }
                        */
                        this.acikKume.add(node);
                }
            }
        }
        Collections.sort(this.acikKume);
}
}