/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horde;

import java.util.ArrayList;

/**
 *
 * @author nK_BlaZy
 */
public class Ville extends Case {

    /* Booléen qui définit si la porte est ouerte ou non => FAUX par défaut */
    private boolean porteOuverte;
    /* Entrepôt */
    private int nbPlanches, nbMetal, nbBoissons, nbRations;
    /* Liste des défenses */
    private ArrayList<Defense> defenses;
    
    /* Constructeur */
    public Ville() {
        super(12, 12);
        
        this.nbPlanches = 0;
        this.nbMetal = 0;
        this.nbBoissons = 0;
        this.nbRations = 50;
        
        this.porteOuverte = false;
        
        this.defenses = new ArrayList<>();
        
        this.defenses.add(new Defense("Yolo", 0, 0, 1, 0));
        this.defenses.get(0).contruire();
        this.defenses.add(new Defense(Defense.Defenses.ABRIS.nom, Defense.Defenses.ABRIS.nbPlanches, Defense.Defenses.ABRIS.nbMetal, Defense.Defenses.ABRIS.nbPA, Defense.Defenses.ABRIS.nbZombies));
    }
    
    /* Ouvre la porte => Retourne FALSE si elle était déjà ouverte */
    public boolean ouvrirPorte() {
        if(this.porteOuverte) {
            System.out.println("La porte est déjà ouverte !!!");
            return false;
        } else {
            System.out.println("La porte s'ouvre");
            this.porteOuverte = true;
            return true;
        }
    }
    
    /* Ferme la porte => Retourne FALSE si elle était déjà fermée */
    public boolean fermerPorte() {
        if(!this.porteOuverte) {
            System.out.println("La porte est déjà fermée !!!");
            return false;
        } else {
            System.out.println("La porte se ferme");
            this.porteOuverte = false;
            return true;
        }
    }
    
    /* Prendre une ration de l'entrepôt => Retourne FALSE si il n'y a plus de 
    ration dans l'entrepôt */
    public boolean prendreRation(){
        if(this.nbRations == 0) {
            System.out.println("Il n'y a plus de ration dans l'entrepôt");
            return false;
        } else {
            this.nbRations--;
            return true;
        }
    }
    
    /* Dépose des planches dans l'entrepot */
    public void deposerPlanches(int _qt) {
        this.nbPlanches += _qt;
    }
    
    /* Dépose du métal dans l'entrepot */
    public void deposerMetal(int _qt) {
        this.nbMetal += _qt;
    }
    
    /* Dépose des boissons dans l'entrepot */
    public void deposerBoissons(int _qt) {
        this.nbBoissons += _qt;
    }
    
    /* Retourne la case Ville sous la forme Ville : Porte OUVERTE/FERMÉE 
    Entrepot */
    public String toString() {
        String s = "Ville :\n";
        s += "  Porte ";
        if(this.porteOuverte) {
            s += "OUVERTE\n";
        } else {
            s += "FERMÉE\n";
        }
        s += "  Entrepot :\n";
        s += "    - Planches de bois : " + this.nbPlanches + "\n";
        s += "    - Plaques de métal : " + this.nbMetal + "\n";
        s += "    - Boissons énergisantes : " + this.nbBoissons + "\n";
        s += "    - Rations : " + this.nbRations;
        
        return s;
    }
    
    /* Retourne VRAI si la porte est ouverte */
    public boolean porteOuverte(){
        return this.porteOuverte;
    }
    
    /* Retourne le nombre de zombie auquel la ville peut résister (20 + la 
    défense des Contructions) */
    public int defenses() {
        int def = 20;
        
        for(Defense defense : this.defenses) {
            if (defense.isActive()) {
                def += defense.getNbZombie();
            }
        }                
                
        return def;
    }
    
    /* Retourne la liste des Defense sous forme d'une chaîne de caractère */
    public String listDef() {
        String s = "=== DEFENSES ===\n";
        
        s += "0 - Nouvelle construction\n";
        
        for (Defense defense : this.defenses) {
            s += (this.defenses.indexOf(defense) + 1) + " - " + defense + "\n";
        }
        
        s += "-1 - Annuler\n";
        
        return s;
    }
    
    /* Retourne le nombre de Defense de la Ville */
    public int nbDef() {
        return this.defenses.size();
    }
    
    public String affichageSimple() {
        return "   VILLE   ";
    }
}
