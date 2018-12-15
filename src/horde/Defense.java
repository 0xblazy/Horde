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
    
    /* Nom de la Defense */
    private String nom;
    /* Nombre de ressouces nécessaires à la construction de la Defense */
    private int nbPlanche,nbMetal;
    /* Nombre de PA nécessaires à la construction de la Defense */
    private int nbPA;
    /* Nombre de zombies auquel la Defense résiste */
    private int nbZombie;
    /* TRUE lorsque la Defense est active => FALSE par défaut */
    private boolean active;
    
    /* Énumération contenant les Defense posibles */
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
    
    /* Constructeur */
    public Defense(int _id) {
        this.nom = Defenses.values()[_id].nom;
        this.nbPlanche = Defenses.values()[_id].nbPlanches;
        this.nbMetal = Defenses.values()[_id].nbMetal;
        this.nbPA = Defenses.values()[_id].nbPA;
        this.nbZombie = Defenses.values()[_id].nbZombies;
        this.active = false;
    }
    
    /* Fait progresser la construction de la Defense de _pa PA => FALSE si la 
    Defense est active ou si _pa est plus grand que le nombre de PA restants 
    pour achever la construction */
    public boolean contruire(int _pa) {
        if(!this.active) {
            if(this.nbPA - _pa < 0) {
                System.out.println("Il suffit de " + this.nbPA + " PA pour "
                        + "achever la construction...");
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
            System.out.println(this.nom + " déjà construit !!!");
            return false;
        }
    }
    
    /* Retourne la Defense sous la forme Nom [Active/nbPA] */
    public String toString() {
        String s = this.nom + " [";
        
        if (this.active) {
            s += "Active]";
        } else {
            s += this.nbPA + " PA restants]";
        }
        
        return s;
    }
    
    /* Retourne une chaine contenant les Defense possibles (depuis l'enum), 
    destinée à être affichée lors de choix d'une nouvelle construction */
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

    /* Retourne le nombre de zombie auquel la Defense résiste */
    public int getNbZombie() {
        return this.nbZombie;
    }

    /* Retourne TRUE si la Defense est active */
    public boolean isActive() {
        return this.active;
    }
}
