/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horde;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author nK_BlaZy
 */
public class Horde {
    
    /* Carte du jeu */
    private Case carte[][];
    
    /* Citoyens */
    private int nbCitoyens;
    private ArrayList<Citoyen> citoyens;
    
    /* Actions */
    private ArrayList<String> actionVille;
    private ArrayList<String> actionExterieur;
    
    /* Scanner */
    private Scanner sc;
    
    /* Constructeur */
    public Horde(int _nbCitoyens) {
        this.carte = new Case[25][25];
        
        this.nbCitoyens = _nbCitoyens;
        this.citoyens = new ArrayList<>();
        
        this.actionVille = new ArrayList<>();
        this.actionExterieur = new ArrayList<>();
        
        this.sc = new Scanner(System.in);
    }
    
    /* Initialise la partie => Génère la carte, ajoute les planches, le métal et
    les boissons, puis créé les Citoyens */
    public void init() {
        System.out.println("Démarrage du jeu...\n");
        
        /* Actions Ville */
        this.actionVille.add("0 - Passer son tour");
        this.actionVille.add("1 - Ouvrir la porte");
        this.actionVille.add("2 - Fermer la porte");
        this.actionVille.add("3 - Prendre une ration");
        this.actionVille.add("4 - Boire au puit");
        this.actionVille.add("5 - Remplir une gourde");
        //this.actionVille.add("Déposer planche");
        this.actionVille.add("6 - Se déplacer");
        
        /* Actions Exterieur */
        this.actionExterieur.add("0 - Passer son tour");
        this.actionExterieur.add("1 - Fouiller");
        this.actionExterieur.add("2 - Récupérer des planches de bois");
        this.actionExterieur.add("3 - Récupérer des plaques de métal");
        this.actionExterieur.add("4 - Récupérer des boissons énergisantes");
        this.actionExterieur.add("5 - Attaquer des zombies");
        this.actionExterieur.add("6 - Manger une ration");
        this.actionExterieur.add("7 - Boire une gourde");
        //this.actionExterieur.add("8 - Boire une boisson énergisante");
        this.actionExterieur.add("9 - Se déplacer");
        
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
            int y = ra.nextInt(25); 
            
            while(x == 12 && y == 12){
                x = ra.nextInt(25);
                y = ra.nextInt(25);
            }
            
            ((Exterieur) this.carte[y][x]).ajouterPlanche();
        }
        
        /* Ajoute les 500 plaques */
        for(int i = 0 ; i < 500 ; i++){
            int x = ra.nextInt(25);
            int y = ra.nextInt(25);
            
            while(x == 12 && y == 12){
                x = ra.nextInt(25);
                y = ra.nextInt(25);
            }
            
            ((Exterieur) this.carte[y][x]).ajouterMetal();
        }
        
        /* Ajoute les 100 boissons */
        for(int i = 0 ; i < 100 ; i++){
            int x = ra.nextInt(25);
            int y = ra.nextInt(25);
            
            while(x == 12 && y == 12){
                x = ra.nextInt(25);
                y = ra.nextInt(25);
            }
            
            ((Exterieur) this.carte[y][x]).ajouterBoisson();
        }
        
        System.out.println("Création des joueurs");
        
        for(int i = 0 ; i < this.nbCitoyens ; i++) {
            System.out.print("Joueur " + (i+1) + " : ");
            this.citoyens.add(new Citoyen(this.sc.nextLine(), this.carte));
        }
    }
    
    public void jouer() {
        /* Tant qu'il reste des joueurs */
        while(!this.citoyens.isEmpty()) {
            
            /* Boucle sur les 12 tours d'une journée */
            for(int i = 0 ; i < 12 ; i++) {
                
                /* Pour chaque Citoyen de this.citoyens */
                for(Citoyen citoyen : this.citoyens) {
                    
                    System.out.println(citoyen);
                    
                    boolean continuer = true;
                    /* Tant que le Citoyen souhaite continuer à jouer */
                    while(continuer) {
                        if(citoyen.isEnVille()) {
                            for(int j = 0 ; j < this.actionVille.size() ; i++) {
                                System.out.println(this.actionVille.get(j));
                            }
                            
                            System.out.println("/nAction : ");
                            continuer = this.actionVille(citoyen, sc.nextInt());
                        } else {
                            for(int j = 0 ; j < this.actionExterieur.size()
                                    ; i++) {
                                System.out.println(this.actionExterieur.get(j));
                            }
                            
                            System.out.println("/nAction : ");
                            continuer = this.actionExterieur(citoyen,
                                    sc.nextInt());
                        }
                    }
                }
            }
        }
        
        System.out.println("===== PARTIE TERMINEE =====");
    }
    
    /* Gère les actions en Ville */
    private boolean actionVille(Citoyen _citoyen, int _action) {
        /* Passer son tour */
        if(_action == 0) {
            System.out.println(_citoyen.getNom() + " passe son tour");
            return false;
        }
        /* Ouvrir porte */
        if(_action == 1) {
            _citoyen.ouvrirPorte();
            return true;
        }
        /* Fermer porte */
        if(_action == 2) {
            _citoyen.fermerPorte();
            return true;
        }
        /* Prendre ration */
        if(_action == 3) {
            _citoyen.prendreRation();
            return true;
        }
        /* Aller au puit */
        if(_action == 4) {
            _citoyen.allerAuPuit();
            return true;
        }
        /* Remplir gourde */
        if(_action == 5) {
            _citoyen.remplirGourde();
            return true;
        }
        /* Se déplacer */
        if(_action == 6) {
            
        }
        return true;
    }
    
    private boolean actionExterieur(Citoyen _citoyen, int _action) {
        /* Passer son tour */
        if(_action == 0) {
            
        }
        /* Fouiller */
        if(_action == 1) {
            
        }
        /* Récuperer planches */
        if(_action == 2) {
            
        }
        /* Récupérer plaques */
        if(_action == 3) {
            
        }
        /* Récupérer boissons */
        if(_action == 4) {
            
        }
        /* Attaquer zombies */
        if(_action == 5) {
            
        }
        /* Manger une ration */
        if(_action == 6) {
            
        }
        /* Boire une goude */
        if(_action == 7) {
            
        }
        /* Se déplacer */
        if(_action == 9) {
            
        }
        return false;
    }
    
    /* Affiche toute la carte => Simple vérification */
    public void afficherCarte(){
        for(int j = 0 ; j < 25 ; j++){
            for(int i = 0 ; i < 25 ; i++){
                if(i == 12 && j == 12) {
                    System.out.print("| "
                            + ((Ville) carte[j][i]).affichageSimple() + " " );
                } else {
                    System.out.print("| "
                            + ((Exterieur) carte[j][i])
                                    .affichageSimple() + " " );
                }
            }
            System.out.println("|");
        }
    }

    /* Retourne la liste de Citoyen */
    public ArrayList<Citoyen> getCitoyens() {
        return citoyens;
    }

    /* Retourne le Citoyen en _i */
    public Citoyen getCitoyenAtI(int _i) {
        return this.citoyens.get(_i);
    }
}
