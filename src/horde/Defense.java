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
    
    public Defense(int _id) {
        this.nom = Defenses.values()[_id].nom;
        this.nbPlanche = Defenses.values()[_id].nbPlanches;
        this.nbMetal = Defenses.values()[_id].nbMetal;
        this.nbPA = Defenses.values()[_id].nbPA;
        this.nbZombie = Defenses.values()[_id].nbZombies;
        this.active = false;
    }
    
    public boolean contruire(int _pa) {
        if(!this.active) {
            if(this.nbPA - _pa < 0) {
                System.out.println("Il suffit de " + this.nbPA + " PA pour achever"
                        + " la construction");
                return false;
            } else {
                this.nbPA -= _pa;
                this.active = (this.nbPA == 0);
                if (this.active) {
                    System.out.println("La défense est maintenant active");
                } else {
                    System.out.println("Il reste " + this.nbPA + " PA pour "
                            + "achever la construction");
                }
                return true;
            }
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
            s += this.nbPA + " PA restants]";
        }
        
        return s;
    }
    
    public static String listeDefenses() {
        String s = "";
        int i = 1;
        for (Defenses def : Defenses.values()) {
            s += i + " - " + def.nom + " [" + def.nbPlanches + " Planches, " + 
                    def.nbMetal + " Metal, " + def.nbPA + " PA, " + 
                    def.nbZombies + " Zombies]\n";
            i++;
        }
        
        s += "-1 - Annuler";
        
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
