/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horde;

import java.util.Random;

/**
 *
 * @author nK_BlaZy
 */
public class Horde {

    private final int MAX_PLANCHES = 1000;
    private final int MAX_METAL = 500;
    private final int MAX_BOISSONS = 100;
    
    private Case carte[][];
    
    private int nbCitoyens;
    private Citoyen[] citoyens;
    
    public Horde(int _nbCitoyens) {
        this.carte = new Case[25][25];
        
        this.nbCitoyens = _nbCitoyens;
        this.citoyens = new Citoyen[this.nbCitoyens];
    }
    
    public void init() {
        System.out.println("Génération de la carte...");
        
        Random ra = new Random();
        int planchesRestantes = this.MAX_PLANCHES;
        int metalRestant = this.MAX_METAL;
        int boissonsRestantes = this.MAX_BOISSONS;
        
        for(int j = 0 ; j < 25 ; j++) {
            for(int i = 0 ; i < 25 ; i++) {
                if(i == 12 && j == 12) {
                    this.carte[j][i] = new Ville();
                } else {
                    this.carte[j][i] = new Exterieur(i, j);
                }
            }
        }
        
        for(int i = 0 ; i < 1000 ; i++){
            int x = ra.nextInt(25);
            while(x == 12) x = ra.nextInt(25);
            
            int y = ra.nextInt(25);
            while(y == 12) y = ra.nextInt(25);
            
            ((Exterieur) this.carte[y][x]).ajouterPlanche();
        }
        
        for(int i = 0 ; i < 500 ; i++){
            int x = ra.nextInt(25);
            while(x == 12) x = ra.nextInt(25);
            
            int y = ra.nextInt(25);
            while(y == 12) y = ra.nextInt(25);
            
            ((Exterieur) this.carte[y][x]).ajouterMetal();
        }
        
        for(int i = 0 ; i < 100 ; i++){
            int x = ra.nextInt(25);
            while(x == 12) x = ra.nextInt(25);
            
            int y = ra.nextInt(25);
            while(y == 12) y = ra.nextInt(25);
            
            ((Exterieur) this.carte[y][x]).ajouterBoisson();
        }
    }
    
    public void afficherCarte(){
        for(int j = 0 ; j < 25 ; j++){
            for(int i = 0 ; i < 25 ; i++){
                if(i == 12 && j == 12) {
                    System.out.print("| "
                            + ((Ville) carte[j][i]).affichageSimple() + " " );
                } else {
                    System.out.print("| "+
                            ((Exterieur) carte[j][i]).affichageSimple() + " " );
                }
            }
            System.out.println("|");
        }
    }

}
