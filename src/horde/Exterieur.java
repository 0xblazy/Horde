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
public class Exterieur extends Case {

    /* Nombres d'items */
    private int nbPlanches, nbMetal, nbBoissons;
    /* Nombre de zombies */
    private int nbZombies;
    /* Booléen qui défini si la case est fouillée => FAUX par défaut */
    private boolean fouiller;

    /* Constructeur */
    public Exterieur(int _x, int _y) {
        super(_x, _y);
        
        this.nbPlanches = 0;
        this.nbMetal = 0;
        this.nbBoissons = 0;
        
        int i = (int) (Math.random() * 100);
        if (i < 30) {
            this.nbZombies = 0;
        } else {
            this.nbZombies = (i - 30) / 10 + 1;
        }

        this.fouiller = false;
    }
    
    /* Fouille une case */
    public boolean fouiller(){
        if(this.fouiller) {
            System.out.println("Cette case a déjà été fouillée !!!");
            return false;
        } else {
            this.fouiller = true;
            return true;
        }
        
    }
    
    public void ajouterPlanche() {
        this.nbPlanches ++;
    }
    
     public void ajouterMetal() {
        this.nbMetal ++;
    }
     
    public void ajouterBoisson() {
        this.nbBoissons ++;
    }
    
    /* Prend _qt Planches de la Case */
    public boolean prendrePlanches(int _qt) {
        if(this.nbPlanches - _qt < 0){
            System.out.println("Il n'y a que " + this.nbPlanches
                    + " planches sur cette case");
            return false;
        } else {
            this.nbPlanches -= _qt;
            return true;
        }
    }
    
    /* Prend _qt Metal de la Case */
    public boolean prendreMetal(int _qt) {
        if(this.nbMetal - _qt < 0){
            System.out.println("Il n'y a que " + this.nbMetal
                    + " plaques de métal sur cette case");
            return false;
        } else {
            this.nbMetal -= _qt;
            return true;
        }
    }
    
    /* Prend _qt Boissons de la Case */
    public boolean prendreBoissons(int _qt) {
        if(this.nbBoissons - _qt < 0){
            System.out.println("Il n'y a que " + this.nbBoissons
                    + " boisson énergisantes sur cette case");
            return false;
        } else {
            this.nbBoissons -= _qt;
            return true;
        }
    }
    
    /* Attaque un Zombie => retourne VRAI si il a des zombie à attaquer */
    public boolean attaquerZombie() {
        if(this.nbZombies == 0){
            System.out.println("Il n'y a plus de zombies !!!");
            return false;
        } else {
            this.nbZombies--;
            return true;
        }
    }
    
    /* Test si il reste des zombies */
    public boolean encoreZombies(){
        return !(this.nbZombies == 0);
    }
    
    /* Retourne la case Exterieur sous la forme Case x, y : -Zombies = nbZombie
    (ajoute les items à la chaîne si la case Exterieur est fouillée)*/
    public String toString() {
        String s = "Case " + super.getX() + "," + super.getY() + " :\n";
        s += "  -Zombies = " + this.nbZombies + "\n";

        if (this.fouiller) {
            s += "  -Items =\n";
            s += "    -Planches x" + this.nbPlanches + "\n";
            s += "    -Plaques de métal x" + this.nbMetal + "\n";
            s += "    -Boissons énergisantes x" + this.nbBoissons + "\n";
        }

        return s;
    }
    
    public String affichageSimple(){
        return "Z" + this.nbZombies + " P" + this.nbPlanches + " M"
                + this.nbMetal + " B" + this.nbBoissons;
    }
}
