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

    public int getNbMetal() {
        return nbMetal;
    }

    public int getNbPlanche() {
        return nbPlanche;
    }

    public int getNbZombie() {
        return nbZombie;
    }    
}
