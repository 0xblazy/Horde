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
public class Entrepot {
    
    private int nbPlanches, nbMetal, nbBoissons, nbRation;
    
    public Entrepot() {
        this.nbPlanches = 0;
        this.nbMetal = 0;
        this.nbBoissons = 0;
        this.nbRation = 50;
    }
    
    public void deposerPlanches(int _qt) {
        this.nbPlanches += _qt;
    }
    
    public void deposerMetal(int _qt) {
        this.nbMetal += _qt;
    }
    
    public void deposerBoissons(int _qt) {
        this.nbBoissons += _qt;
    }
}
