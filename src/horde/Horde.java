/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horde;

import java.io.IOException;
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

    /* Random */
    private Random ra;

    /* Constructeur */
    public Horde(int _nbCitoyens) {
        this.carte = new Case[25][25];

        this.nbCitoyens = _nbCitoyens;
        this.citoyens = new ArrayList<>();

        this.actionVille = new ArrayList<>();
        this.actionExterieur = new ArrayList<>();

        this.sc = new Scanner(System.in);

        this.ra = new Random();
    }

    /* Initialise la partie => Défini les actions, génère la carte, ajoute les 
    planches, le métal et les boissons, puis créé les Citoyens */
    public void init() {
        System.out.println("Démarrage du jeu...\n");

        /* Actions Ville */
        this.actionVille.add("0 - Passer son tour");
        this.actionVille.add("1 - Ouvrir la porte");
        this.actionVille.add("2 - Fermer la porte");
        this.actionVille.add("3 - Prendre une ration");
        this.actionVille.add("4 - Boire au puit");
        this.actionVille.add("5 - Remplir une gourde");
        this.actionVille.add("6 - Déposer des planches de bois");
        this.actionVille.add("7 - Déposer des plaques de métal");
        this.actionVille.add("8 - Déposer des boissons énergisantes");
        this.actionVille.add("9 - Accéder aux défenses");
        this.actionVille.add("10 - Se déplacer");

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

        /* Créé toute les Cases que la carte */
        for (int j = 0; j < 25; j++) {
            for (int i = 0; i < 25; i++) {
                if (i == 12 && j == 12) {
                    this.carte[j][i] = new Ville();
                } else {
                    this.carte[j][i] = new Exterieur(i, j);
                }
            }
        }

        /* Ajoute les 1000 planches */
        for (int i = 0; i < 1000; i++) {
            int x = this.ra.nextInt(25);
            int y = this.ra.nextInt(25);

            while (x == 12 && y == 12) {
                x = this.ra.nextInt(25);
                y = this.ra.nextInt(25);
            }

            ((Exterieur) this.carte[y][x]).ajouterPlanche();
        }

        /* Ajoute les 500 plaques */
        for (int i = 0; i < 500; i++) {
            int x = this.ra.nextInt(25);
            int y = this.ra.nextInt(25);

            while (x == 12 && y == 12) {
                x = this.ra.nextInt(25);
                y = this.ra.nextInt(25);
            }

            ((Exterieur) this.carte[y][x]).ajouterMetal();
        }

        /* Ajoute les 100 boissons */
        for (int i = 0; i < 100; i++) {
            int x = this.ra.nextInt(25);
            int y = this.ra.nextInt(25);

            while (x == 12 && y == 12) {
                x = this.ra.nextInt(25);
                y = this.ra.nextInt(25);
            }

            ((Exterieur) this.carte[y][x]).ajouterBoisson();
        }

        System.out.println("Création des joueurs");

        for (int i = 0; i < this.nbCitoyens; i++) {
            System.out.print("Joueur " + (i + 1) + " : ");
            this.citoyens.add(new Citoyen(this.sc.nextLine(), this.carte));
        }
    }

    /* Lance le jeu */
    public void jouer() {
        int jour = 0;
        int nbZ = 0;

        /* Tant qu'il reste plus d'un joueur */
        while (this.citoyens.size() > 1) {

            jour++;
            nbZ += 10;
            int tour = 0;

            System.out.println("==== JOUR " + jour + " ====");

            /* Boucle sur les 12 tours d'une journée */
            for (int i = 0; i < 12; i++) {

                tour++;

                /* Pour chaque Citoyen de this.citoyens */
                for (Citoyen citoyen : this.citoyens) {

                    System.out.println("Jour " + jour + " - Tour " + tour);
                    System.out.println("");

                    boolean continuer = true;

                    /* Tant que le Citoyen souhaite continuer à jouer */
                    while (continuer) {
                        this.afficherCarte();
                        
                        System.out.println(citoyen);
                        
                        /* Vérifie si le Citoyen est encore en vie (si il a 
                        encore des PV) */
                        if (citoyen.estMort()) {
                            System.out.println(citoyen.getNom() + " est mort");
                            continuer = false;
                            this.citoyens.remove(citoyen);
                        } else {
                            /* Propose les actions disponibles en fonction de
                            l'emplacement du Citoyen */
                            if (citoyen.isEnVille()) {
                                System.out.println((Ville) this.carte[12][12]);
                                System.out.println("");
                                System.out.println("== ACTIONS ==");
                                for (String action : this.actionVille) {
                                    System.out.println("  " + action);
                                }
                                System.out.print("Action : ");
                                int action = this.scanner();
                                
                                while (action < 0 || action > this.actionVille
                                        .size() - 1) {
                                    System.out.print("Saisie incorrecte, "
                                            + "ressaisissez un nombre :  ");
                                    action = this.scanner();
                                }
                                
                                continuer = this.actionVille(citoyen,
                                        action);
                            } else {
                                System.out.println((Exterieur) this.carte
                                        [citoyen.getY()][citoyen.getX()]);
                                System.out.println("");
                                System.out.println("== ACTIONS ==");
                                for (String action : this.actionExterieur) {
                                    System.out.println("  " + action);
                                }
                                System.out.print("Action : ");
                                int action = this.scanner();
                                
                                while (action < 0 || action > this.actionExterieur.size()) {
                                    System.out.print("Saisie incorrecte, "
                                            + "ressaisissez un nombre :  ");
                                    action = this.scanner();
                                }
                                
                                continuer = this.actionExterieur(citoyen,
                                        action);
                            }
                        }
                        System.out.println("");
                    }
                    citoyen.finDeTour();
                    System.out.println("");
                }
            }

            /* Fin de la journée, attaque des zombies */
            System.out.println("La journée est finie, les zombies arrivent...");

            ArrayList<Citoyen> citoyensMorts = new ArrayList<>();

            /* "Tue" tous les Citoyens à l'extérieur de la Ville => Les supprime 
            de la List des citoyens et l'ajoute à la List des citoyens morts */
            for (Citoyen citoyen : this.citoyens) {
                if (!citoyen.isEnVille()) {
                    citoyensMorts.add(citoyen);
                    this.citoyens.remove(citoyen);
                }
            }

            /* Défini le nombre de zombies attaquant la Ville */
            int nbZombie = nbZ + this.ra.nextInt(11);

            /* Si le nombre de zombies attaquants est plus grand que le nombre 
            de zombies auquel la Ville peut résister, "Tue" aléatoirement la 
            moitié des Citoyens restants */
            if (nbZombie > ((Ville) this.carte[12][12]).defenses()) {
                System.out.println("Les zombies ont réussi à passer les "
                        + "défenses de la ville...");
                int nbVictimes = this.citoyens.size() / 2;
                for (int i = 0; i < nbVictimes; i++) {
                    int a = this.ra.nextInt(this.citoyens.size());
                    citoyensMorts.add(this.citoyens.get(a));
                    this.citoyens.remove(a);
                }
            }
            System.out.println("");

            /* Affiche le bilan de la nuit (nombre et liste des morts) */
            System.out.println("==== BILAN DE LA NUIT ====");
            System.out.println(citoyensMorts.size() + " morts");
            for (Citoyen citoyenMort : citoyensMorts) {
                System.out.println(citoyenMort.getNom());
            }
            System.out.println("");
            System.out.println("Pressez ENTRER pour continuer...");
            this.pressEnter();
        }

        /* Fin de la partie, affiche le Citoyen gagnant */
        System.out.println("===== PARTIE TERMINEE =====");
        if(this.citoyens.size() == 0) {
            System.out.println("Aucun citoyen a survécu...");
        } else {
            System.out.println(this.citoyens.get(0).getNom() + " a gagné la "
                    + "partie");
        }
    }

    /* Gère les actions en Ville */
    private boolean actionVille(Citoyen _citoyen, int _action) {
        switch (_action) {
            /* Passer son tour */
            case 0 :
                System.out.println(_citoyen.getNom() + " passe son tour");
                return false;
            /* Ouvrir porte */
            case 1 :
                _citoyen.ouvrirPorte();
                return true;
            /* Fermer porte */
            case 2 :
                _citoyen.fermerPorte();
                return true;
            /* Prendre ration */
            case 3 :
                _citoyen.prendreRation();
                return true;
            /* Aller au puit */
            case 4 :
                _citoyen.allerAuPuit();
                return true;
            /* Remplir gourde */
            case 5 :
                _citoyen.remplirGourde();
                return true;
            /* Déposer des planches */
            case 6 :
                _citoyen.deposerPlanches();
                return true;
            /* Déposer des plaques */
            case 7 :
                _citoyen.deposerMetal();
                return true;
            /* Déposer des boissons */
            case 8 :
                System.out.print("Nombre de boissons énergisantes : ");
                _citoyen.deposerBoissons(this.scanner());
                return true;
            /* Menu des défenses */
            case 9 :
                System.out.println(((Ville)this.carte[12][12]).listDef());
                System.out.print("Choisissez la défense : ");
                int action = this.scanner();
                while (action < -1 || action > ((Ville)this.carte[12][12])
                        .nbDef()) {
                    System.out.print("Saisie incorrecte, "
                             + "ressaisissez un nombre :  ");
                    action = this.scanner();
                }
                
                if (action == -1) {
                    System.out.println("Sortie du menu des Défenses");
                    return true;
                } else if (action == 0) {
                    System.out.println(Defense.listeDefenses());
                    System.out.print("Defense : ");
                    int def = this.scanner();
                    while (def < -1 || def == 0 || def > 7) {
                        System.out.print("Saisie incorrecte, "
                             + "ressaisissez un nombre :  ");
                        def= this.scanner();
                    }
                    if (def == -1) {
                        System.out.println("Sortie du menu des Défenses");
                        return true;
                    }
                    _citoyen.nouvelleDefense(def - 1);
                    return true;
                } else {
                    System.out.print("Nombre de PA : ");
                    int pa = this.scanner();
                    while (pa < 0 || pa > 10 ) {
                        System.out.print("Saisie incorrecte, "
                             + "ressaisissez un nombre :  ");
                        pa = this.scanner();
                    }
                    _citoyen.construire(action - 1, pa);
                    return true;
                }
            /* Se déplacer */
            case 10 :
                System.out.println("Déplacement vers :");
                System.out.println("  0 - Annuler");
                System.out.println("  1 - Nord");
                System.out.println("  2 - Sud");
                System.out.println("  3 - Est");
                System.out.println("  4 - Ouest");
                System.out.print("Direction : ");
                
                int dir = this.scanner();
                
                while (dir < 0 || dir > 4) {
                    System.out.print("Saisie incorrecte, "
                             + "ressaisissez un nombre :  ");
                    dir = this.scanner();
                }
                
                if (dir == 0) {
                    System.out.println("Déplacement annulé");
                    return true;
                } else {
                    _citoyen.deplacer(dir);
                    return true;
                }
        }
        return true;
    }

    /* Gère les actions à l'Exterieur */
    private boolean actionExterieur(Citoyen _citoyen, int _action) {
        switch (_action) {
            /* Passer son tour */
            case 0 :
                System.out.println(_citoyen.getNom() + " passe son tour");
                return false;
            /* Fouiller */
            case 1 :
                _citoyen.fouiller();
                return true;
            /* Récupérer planches */
            case 2 :
                System.out.print("Nombre de planches de bois : ");
                _citoyen.prendrePlanches(this.scanner());
                return true;
            /* Récupérer plaques */
            case 3 :
                System.out.print("Nombre de plaques de métal : ");
                _citoyen.prendreMetal(this.scanner());
                return true;
            /* Récupérer boissons */
            case 4 :
                System.out.print("Nombre de boissons énergisantes : ");
                _citoyen.prendreBoissons(this.scanner());
                return true;
            /* Attaquer zombies */
            case 5 :
                _citoyen.attaquerZombie();
                return true;
            /* Manger une ration */
            case 6 :
                _citoyen.mangerRation();
                return true;
            /* Boire une goude */
            case 7 :
                _citoyen.boireGourde();
                return true;
            /* Se déplacer */
            case 9 :
                System.out.println("Déplacement vers :");
                System.out.println("  0 - Annuler");
                System.out.println("  1 - Nord");
                System.out.println("  2 - Sud");
                System.out.println("  3 - Est");
                System.out.println("  4 - Ouest");
                System.out.print("Direction : ");
                
                int dir = this.scanner();
                
                while (dir < 0 || dir > 4) {
                    System.out.print("Saisie incorrecte, "
                             + "ressaisissez un nombre :  ");
                    dir = this.scanner();
                }
                
                if (dir == 0) {
                    System.out.println("Déplacement annulé");
                    return true;
                } else {
                    _citoyen.deplacer(dir);
                    return true;
                }  
        }
        return true;
    }
    
    /* Affiche toute la carte */
    private void afficherCarte() {
        for (int j = 0; j < 25; j++) {
            for (int i = 0; i < 25; i++) {
                if (i == 12 && j == 12) {
                    System.out.print("@@@");
                } else if (((Exterieur) this.carte[j][i]).isFouiller()){
                    System.out.print("   ");
                } else {
                    System.out.print("-#-");
                }
            }
            System.out.println();
        }
    }
    
    /* Protection du Scanner (saisie obligatoire d'un int */
    private int scanner() {
        int n = -1;
        boolean valid = false;
        
        do {
            if(this.sc.hasNextInt()) {
                n = this.sc.nextInt();
                valid = true;
            } else {
                System.out.print("Veuillez saisir un nombre ! ");
                this.sc.next();
            }
        } while(!valid);
        
        return n;
    }

    private void pressEnter(){
        try {
            System.in.read();
        } catch(IOException e) {}
    }
}
