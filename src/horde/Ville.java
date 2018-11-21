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
public class Ville extends Case {

    /* Booléen qui définit si la porte est ouerte ou non => FAUX par défaut */
    private boolean porteOuverte;
    /* Entrepôt */
    private int nbPlanches, nbMetal, nbBoissons, nbRation;
    
    /* Constructeur */
    public Ville() {
        super(12, 12);
        
        this.nbPlanches = 0;
        this.nbMetal = 0;
        this.nbBoissons = 0;
        this.nbRation = 50;
        
        this.porteOuverte = false;
    }
    
    /* Ouvre la porte */
    public boolean ouvrirPorte() {
        if(this.porteOuverte) {
            System.out.println("La porte est déjà ouverte !!!");
            return false;
        } else {
            this.porteOuverte = true;
            return true;
        }
    }
    
    /* Retourne VRAI si la porte est ouverte */
    public boolean porteOuverte(){
        return this.porteOuverte;
    }
    
    public String affichageSimple() {
        return "   VILLE   ";
    }
}
