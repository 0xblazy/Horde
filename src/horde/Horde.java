/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horde;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author nK_BlaZy
 */
public class Horde {

    private final int MAX_PLANCHES = 1000;
    private final int MAX_METAL = 500;
    private final int MAX_BOISSONS = 100;
    
    /* Carte du jeu */
    private Case carte[][];
    
    private int nbCitoyens;
    private Citoyen[] citoyens;
    
    private Scanner sc;
    
    /* Constructeur */
    public Horde(int _nbCitoyens) {
        this.carte = new Case[25][25];
        
        this.nbCitoyens = _nbCitoyens;
        this.citoyens = new Citoyen[this.nbCitoyens];
        
        this.sc = new Scanner(System.in);
    }
    
    /* Initialise la partie => Génère la carte, ajoute les planches, le métal et
    les boissons, puis créé les Citoyens */
    public void init() {
        System.out.println("Génération de la carte...\n");
        
        Random ra = new Random();
        
        /* Créé toute les Cases que la carte */
        for(int j = 0 ; j < 25 ; j++) {
            for(int i = 0 ; i < 25 ; i++) {
                if(i == 12 && j == 12) {
                    this.carte[j][i] = new Ville();
                } else {
                    this.carte[j][i] = new Exterieur(i, j);
                }
            }
        }
        
        /* Ajoute les 1000 planches */
        for(int i = 0 ; i < 1000 ; i++){
            int x = ra.nextInt(25);
            while(x == 12) x = ra.nextInt(25);
            
            int y = ra.nextInt(25);
            while(y == 12) y = ra.nextInt(25);
            
            ((Exterieur) this.carte[y][x]).ajouterPlanche();
        }
        
        /* Ajoute les 500 plaques */
        for(int i = 0 ; i < 500 ; i++){
            int x = ra.nextInt(25);
            while(x == 12) x = ra.nextInt(25);
            
            int y = ra.nextInt(25);
            while(y == 12) y = ra.nextInt(25);
            
            ((Exterieur) this.carte[y][x]).ajouterMetal();
        }
        
        /* Ajoute les 100 boissons */
        for(int i = 0 ; i < 100 ; i++){
            int x = ra.nextInt(25);
            while(x == 12) x = ra.nextInt(25);
            
            int y = ra.nextInt(25);
            while(y == 12) y = ra.nextInt(25);
            
            ((Exterieur) this.carte[y][x]).ajouterBoisson();
        }
        
        System.out.println("Création des joueurs");
        
        for(int i = 0 ; i < this.citoyens.length ; i++) {
            System.out.print("Joueur " + (i+1) + " : ");
            this.citoyens[i] = new Citoyen(this.sc.nextLine(), this.carte);
        }
    }
    
    /* Affiche toute la carte => Simple vérification */
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

    /* Retourne le tableau de Citoyen */
    public Citoyen[] getCitoyens() {
        return citoyens;
    }

    /* Retourne le Citoyen en _i */
    public Citoyen getCitoyenAtI(int _i) {
        return citoyens[_i];
    }
}
