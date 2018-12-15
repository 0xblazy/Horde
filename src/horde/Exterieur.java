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

    /* Nombres d'items sur la case*/
    private int nbPlanches,nbMetal,nbBoissons;
    /* Nombre de zombies */
    private int nbZombies;
    /* Booléen qui défini si la case est fouillée => FAUX par défaut */
    private boolean fouillee;

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

        this.fouillee = false;
    }

    /* GÉNÉRATION */
    /* Ajoute une planche de bois sur la Case Exterieur (utilisée lors de la 
    génération de la carte) */
    public void ajouterPlanche() {
        this.nbPlanches++;
    }

    /* Ajoute une plaque de métal sur la Case Exterieur (utilisée lors de la 
    génération de la carte) */
    public void ajouterMetal() {
        this.nbMetal++;
    }

    /* Ajoute une boisson énergisante sur la Case Exterieur (utilisée lors de la 
    génération de la carte) */
    public void ajouterBoisson() {
        this.nbBoissons++;
    }

    /* ACTIONS */
    /* Fouille une case => Retourne FALSE si la Case était déjà fouillée */
    public boolean fouiller() {
        if (this.fouillee) {
            System.out.println("Cette case a déjà été fouillée !!!");
            return false;
        } else {
            this.fouillee = true;
            return true;
        }

    }

    /* Prend _qt Planches de la Case => Retourne FALSE si il n'y a pas assez de 
    Planches sur la Case ou si la Case n'a pas été fouillée */
    public boolean prendrePlanches(int _qt) {
        if (this.fouillee) {
            if (this.nbPlanches - _qt < 0) {
                System.out.println("Il n'y a que " + this.nbPlanches
                        + " Planches de bois sur cette case...");
                return false;
            } else {
                this.nbPlanches -= _qt;
                return true;
            }
        } else {
            System.out.println("La case n'a pas encore été fouillée !!!");
            return false;
        }
    }

    /* Prend _qt Metal de la Case => Retourne FALSE si il n'y a pas assez de 
    Metal sur la Case ou si la Case n'a pas été fouillée */
    public boolean prendreMetal(int _qt) {
        if (this.fouillee) {
            if (this.nbMetal - _qt < 0) {
                System.out.println("Il n'y a que " + this.nbMetal
                        + " plaques de Métal sur cette case...");
                return false;
            } else {
                this.nbMetal -= _qt;
                return true;
            }
        } else {
            System.out.println("La case n'a pas encore été fouillée !!!");
            return false;
        }
    }

    /* Prend _qt Boissons de la Case => Retourne FALSE si il n'y a pas assez de 
    Boissons sur la Case ou si la Case n'a pas été fouillée  */
    public boolean prendreBoissons(int _qt) {
        if (this.fouillee) {
            if (this.nbBoissons - _qt < 0) {
                System.out.println("Il n'y a que " + this.nbBoissons
                        + " Boissons énergisantes sur cette case...");
                return false;
            } else {
                this.nbBoissons -= _qt;
                return true;
            }
        } else {
            System.out.println("La case n'a pas encore été fouillée !!!");
            return false;
        }
    }

    /* Attaque un Zombie => retourne TRUE si il a des zombies à attaquer, et 
    retire un zombie de la Case */
    public boolean attaquerZombie() {
        if (this.nbZombies == 0) {
            System.out.println("Il n'y a plus de zombies sur cette case...");
            return false;
        } else {
            this.nbZombies--;
            return true;
        }
    }

    /* Retourne TRUE si il reste des zombies sur la Case */
    public boolean encoreZombies() {
        return !(this.nbZombies == 0);
    }

    /* Retourne la case Exterieur sous la forme Case x, y : -Zombies = nbZombie
    (ajoute les items à la chaîne si la case Exterieur est fouillée)*/
    public String toString() {
        String s = "Case " + super.getX() + "," + super.getY() + " :\n";
        s += "  - Zombies = " + this.nbZombies;

        if (this.fouillee) {
            s += "\n" + this.getItems();
        }

        return s;
    }

    /* Retourne les items restants sur la Case sous forme d'une chaîne de 
    caractères */
    private String getItems() {
        String s = "  - Items =\n";
        s += "    - Planches de bois x" + this.nbPlanches + "\n";
        s += "    - Plaques de métal x" + this.nbMetal + "\n";
        s += "    - Boissons énergisantes x" + this.nbBoissons;

        return s;
    }

    /* Retourne le nombre de Planches sur la Case */
    public int getNbPlanches() {
        return this.nbPlanches;
    }

    /* Retourne le nombre de Metal sur la Case */
    public int getNbMetal() {
        return this.nbMetal;
    }

    /* Retourne le nombre de Boissons sur la Case */
    public int getNbBoissons() {
        return this.nbBoissons;
    }

    /* Retourne TRUE si la Case est fouillée */
    public boolean isFouillee() {
        return this.fouillee;
    }
    
    
}
