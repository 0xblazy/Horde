/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horde;

/**
 *
 * @author nK_BlaZy
 */
public class Defense {
    
    private String nom;
    private int nbPlanche, nbMetal;
    private int nbPA;
    private int nbZombie;
    private boolean active;
    
    public static enum Defenses {
        MUR("Mur d'enceinte", 20, 5, 10 , 20),
        FILS("Fils barbelés", 20, 30, 20, 30),
        FOSSES("Fosses à zombies", 50, 25, 30, 50),
        MINES("Mines autour de la ville", 10, 50, 30, 50),
        PORTES("Portes blindées", 50, 50, 40, 100),
        MIRADORS("Miradors avec mitrailleuses automatiques", 75, 75, 50, 200),
        ABRIS("Abris anti-atomique", 100, 200, 60, 500);
        
        public final String nom;
        public final int nbPlanches;
        public final int nbMetal;
        public final int nbPA;
        public final int nbZombies;
        
        Defenses(String _nom, int _nbPlanche, int _nbMetal, int _nbPA ,
                int _nbZombies) {
            this.nom = _nom;
            this.nbPlanches = _nbPlanche;
            this.nbMetal = _nbMetal;
            this.nbPA = _nbPA;
            this.nbZombies = _nbZombies;
        }
    }
    
    public Defense(String _nom, int _nbPlanche, int _nbMetal, int _nbPA, 
            int _nbZombie) {
        this.nom = _nom;
        this.nbPlanche = _nbPlanche;
        this.nbMetal = _nbMetal;
        this.nbPA = _nbPA;
        this.nbZombie = _nbZombie;
        this.active = false;
    }
    
    public boolean contruire() {
        if(!this.active) {
            this.nbPA --;
            this.active = (this.nbPA == 0);
            return true;
        } else {
            System.out.println(this.nom + " déjà construit !!");
            return false;
        }
    }
    
    public String toString() {
        String s = this.nom + " [";
        
        if (this.active) {
            s += "Active]";
        } else {
            s += this.nbPA + " restants]";
        }
        
        return s;
    }

    public int getNbMetal() {
        return nbMetal;
    }

    public int getNbPlanche() {
        return nbPlanche;
    }

    public int getNbZombie() {
        return nbZombie;
    }

    public boolean isActive() {
        return this.active;
    }
}
