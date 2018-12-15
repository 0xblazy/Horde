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
    private Case[][] carte;
    /* Talkie */
    private Talkie talkie;

    /* Citoyens */
    private int nbCitoyens;
    private ArrayList<Citoyen> citoyens;

    /* Actions */
    private ArrayList<String> actionVille;
    private ArrayList<String> actionExterieur;

    /* Random */
    private Random ra;

    /* Constructeur */
    public Horde(int _nbCitoyens) {
        this.carte = new Case[25][25];
        
        this.talkie = new Talkie(this.carte);

        this.nbCitoyens = _nbCitoyens;
        this.citoyens = new ArrayList<>();

        this.actionVille = new ArrayList<>();
        this.actionExterieur = new ArrayList<>();

        this.ra = new Random();
    }
    
    /* Demande le nombre de joueur puis créé et retourne une partie de Horde 
    avec le nombre de joueurs demandé */
    public static Horde nouvellePartie() {
        System.out.println("=== NOUVELLE PARTIE ===");
        System.out.print("Nombre de joueurs (entre 2 et 20) : ");
        int nbJoueurs = Horde.scanner();
        while (nbJoueurs < 2 || nbJoueurs > 20) {
            System.out.print("Nombre de joueurs incorrect, ressaisissez un "
                    + "nombre : ");
            nbJoueurs = Horde.scanner();
        }
        return new Horde(nbJoueurs);
    }

    /* Initialise la partie => Défini les actions, génère la carte, ajoute les 
    Planches, le Métal et les Boissons, puis créé les Citoyens */
    public void init() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Démarrage du jeu...\n");

        /* Actions Ville */
        this.actionVille.add(" 0 - Passer son tour");
        this.actionVille.add(" 1 - Ouvrir la porte");
        this.actionVille.add(" 2 - Fermer la porte");
        this.actionVille.add(" 3 - Prendre une Ration");
        this.actionVille.add(" 4 - Boire au puit");
        this.actionVille.add(" 5 - Remplir une Gourde");
        this.actionVille.add(" 6 - Manger une Ration");
        this.actionVille.add(" 7 - Boire une Gourde");
        this.actionVille.add(" 8 - Boire une Boisson énergisante");
        this.actionVille.add(" 9 - Déposer des Planches de bois");
        this.actionVille.add("10 - Déposer des plaques de Métal");
        this.actionVille.add("11 - Déposer des Boissons énergisantes");
        this.actionVille.add("12 - Accéder au menu des Défenses");
        this.actionVille.add("13 - Consulter le Talkie-Walkie");
        this.actionVille.add("14 - Se déplacer");

        /* Actions Exterieur */
        this.actionExterieur.add(" 0 - Passer son tour");
        this.actionExterieur.add(" 1 - Fouiller");
        this.actionExterieur.add(" 2 - Ramasser des Planches de bois");
        this.actionExterieur.add(" 3 - Ramasser des plaques de Métal");
        this.actionExterieur.add(" 4 - Ramasser des Boissons énergisantes");
        this.actionExterieur.add(" 5 - Attaquer des Zombies");
        this.actionExterieur.add(" 6 - Manger une Ration");
        this.actionExterieur.add(" 7 - Boire une Gourde");
        this.actionExterieur.add(" 8 - Boire une Boisson énergisante");
        this.actionExterieur.add(" 9 - Consulter le Talkie-Walkie");
        this.actionExterieur.add("10 - Mettre à jour le Talkie-Walkie");
        this.actionExterieur.add("11 - Se déplacer");

        System.out.println("Génération de la carte...\n");

        /* Créé toutes les Cases de la carte */
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

        this.afficherCarte();
        System.out.println();
        
        System.out.println("=== CRÉATION DES JOUEURS ===");

        for (int i = 0; i < this.nbCitoyens; i++) {
            System.out.print("Joueur " + (i + 1) + " : ");
            this.citoyens.add(new Citoyen(sc.nextLine(), this.carte, 
                    this.talkie));
        }
        
        System.out.println();
    }

    /* Boucle de jeu */
    public void jouer() {
        int jour = 0;
        int nbZ = 0;
        
        /* Tant qu'il reste plus d'un joueur */
        while (this.citoyens.size() > 1) {

            jour++;
            nbZ += 10;
            int tour = 0;

            System.out.println("=== JOUR " + jour + " ===");

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
                        
                        System.out.println(citoyen);
                        System.out.println();
                        
                        /* Vérifie si le Citoyen est encore en vie (si il a 
                        encore des PV) */
                        if (citoyen.estMort()) {
                            System.out.println(citoyen.getNom() + " est "
                                    + "mort...");
                            continuer = false;
                            this.citoyens.remove(citoyen);
                        } else {
                            /* Propose les actions disponibles en fonction de
                            l'emplacement du Citoyen */
                            if (citoyen.isEnVille()) {
                                System.out.println((Ville) this.carte[12][12]);
                                System.out.println();
                                System.out.println("=== ACTIONS EN VILLE ===");
                                for (String action : this.actionVille) {
                                    System.out.println("  " + action);
                                }
                                System.out.print("Action : ");
                                int action = this.scanner();
                                
                                while (action < 0 || action > this.actionVille
                                        .size() - 1) {
                                    System.out.print("Action invalide, "
                                            + "ressaisissez un nombre :  ");
                                    action = this.scanner();
                                }
                                
                                System.out.println();
                                continuer = this.actionVille(citoyen,
                                        action);
                            } else {
                                System.out.println((Exterieur) this.carte
                                        [citoyen.getY()][citoyen.getX()]);
                                System.out.println();
                                System.out.println("=== ACTIONS EN EXTÉRIEUR "
                                        + "===");
                                for (String action : this.actionExterieur) {
                                    System.out.println("  " + action);
                                }
                                System.out.print("Action : ");
                                int action = this.scanner();
                                
                                while (action < 0 || action > 
                                        this.actionExterieur.size()) {
                                    System.out.print("Action invalide, "
                                            + "ressaisissez un nombre :  ");
                                    action = this.scanner();
                                }
                                
                                System.out.println();
                                continuer = this.actionExterieur(citoyen,
                                        action);
                            }
                        }
                        System.out.println();
                    }
                    citoyen.finDeTour();
                    System.out.println();
                }
            }

            /* Fin de la journée, attaque des zombies */
            System.out.println("La journée est finie, les zombies arrivent...");

            ArrayList<Citoyen> citoyensMorts = new ArrayList<>();

            /* "Tue" tous les Citoyens à l'extérieur de la Ville => Les supprime 
            de la liste des citoyens et les ajoute à la liste des citoyens 
            morts */
            for (Citoyen citoyen : this.citoyens) {
                if (!citoyen.isEnVille()) {
                    citoyensMorts.add(citoyen);
                    this.citoyens.remove(citoyen);
                }
            }

            /* Défini le nombre de zombies attaquant la Ville */
            int nbZombie = nbZ + this.ra.nextInt(11);

            /* Si le nombre de zombies attaquant est plus grand que le nombre 
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
            System.out.println("=== BILAN DE LA NUIT ===");
            System.out.println(citoyensMorts.size() + " morts");
            for (Citoyen citoyenMort : citoyensMorts) {
                System.out.println(citoyenMort.getNom());
            }
            System.out.println("");
            System.out.println("Pressez ENTRER pour continuer...");
            this.pressEnter();
            
            for (Citoyen citoyen : this.citoyens) {
                citoyen.finDeJournee();
            }
        }

        /* Fin de la partie, affiche le Citoyen gagnant */
        System.out.println("=== PARTIE TERMINEE ===");
        if(this.citoyens.size() == 0) {
            System.out.println("Aucun citoyen a survécu...");
        } else {
            System.out.println(this.citoyens.get(0).getNom() + " a gagné la "
                    + "partie !!!");
        }
    }

    /* Gère les actions en Ville */
    private boolean actionVille(Citoyen _citoyen, int _action) {
        switch (_action) {
            /* Passer son tour */
            case 0 :
                System.out.println(_citoyen.getNom() + " passe son tour...");
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
             /* Manger une ration */
            case 6 :
                _citoyen.mangerRation();
                return true;
            /* Boire une goude */
            case 7 :
                _citoyen.boireGourde();
                return true;
            /* Boire une boisson énergisante */
            case 8 :
                _citoyen.boireBoisson();
                return true;
            /* Déposer des planches */
            case 9 :
                _citoyen.deposerPlanches();
                return true;
            /* Déposer des plaques */
            case 10 :
                _citoyen.deposerMetal();
                return true;
            /* Déposer des boissons */
            case 11 :
                System.out.print("Nombre de boissons énergisantes : ");
                int nb = this.scanner();
                while (nb < 1 || nb >10) {
                    System.out.print("Nombre incorrect, ressaisissez un nombre "
                            + ": ");
                    nb = this.scanner();
                }
                System.out.println();
                _citoyen.deposerBoissons(nb);
                return true;
            /* Menu des défenses */
            case 12 :
                System.out.println(((Ville)this.carte[12][12]).listDef());
                System.out.print("Défense : ");
                int action = this.scanner();
                while (action < -1 || action > ((Ville)this.carte[12][12])
                        .nbDef()) {
                    System.out.print("Choix incorrect, "
                             + "ressaisissez un nombre : ");
                    action = this.scanner();
                }
                
                if (action == -1) {
                    System.out.println();
                    System.out.println("Sortie du menu des Défenses");
                    return true;
                } else if (action == 0) {
                    System.out.println(Defense.listeDefenses());
                    System.out.print("Nouvelle défense : ");
                    int def = this.scanner();
                    while (def < -1 || def == 0 || def > 7) {
                        System.out.print("Choix incorrect, "
                             + "ressaisissez un nombre : ");
                        def= this.scanner();
                    }
                    if (def == -1) {
                        System.out.println();
                        System.out.println("Sortie du menu des Défenses");
                        return true;
                    }
                    System.out.println();
                    _citoyen.nouvelleDefense(def - 1);
                    return true;
                } else {
                    System.out.print("Nombre de PA : ");
                    int pa = this.scanner();
                    while (pa < 0 || pa > 10 ) {
                        System.out.print("Nombre de PA incorrect, "
                             + "ressaisissez un nombre : ");
                        pa = this.scanner();
                    }
                    System.out.println();
                    _citoyen.construire(action - 1, pa);
                    return true;
                }
            /* Consulter le Talkie-Walkie */
            case 13 :
                System.out.println(this.talkie);
                return true;
            /* Se déplacer */
            case 14 :
                System.out.println("Déplacement vers :");
                System.out.println("  0 - Annuler");
                System.out.println("  1 - Nord");
                System.out.println("  2 - Sud");
                System.out.println("  3 - Est");
                System.out.println("  4 - Ouest");
                System.out.print("Direction : ");
                
                int dir = this.scanner();
                
                while (dir < 0 || dir > 4) {
                    System.out.print("Direction incorrecte, "
                             + "ressaisissez un nombre : ");
                    dir = this.scanner();
                }
                System.out.println();
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
                System.out.println(_citoyen.getNom() + " passe son tour...");
                return false;
            /* Fouiller */
            case 1 :
                _citoyen.fouiller();
                return true;
            /* Ramasser planches */
            case 2 :
                System.out.print("Nombre de Planches de bois : ");
                int nbP = this.scanner();
                while (nbP < 1 || nbP >10) {
                    System.out.print("Nombre incorrect, ressaisissez un nombre "
                            + ": ");
                    nbP = this.scanner();
                }
                System.out.println();
                _citoyen.prendrePlanches(nbP);
                return true;
            /* Ramasser plaques */
            case 3 :
                System.out.print("Nombre de plaques de Métal : ");
                int nbM = this.scanner();
                while (nbM < 1 || nbM >10) {
                    System.out.print("Nombre incorrect, ressaisissez un nombre "
                            + ": ");
                    nbM = this.scanner();
                }
                System.out.println();
                _citoyen.prendreMetal(nbM);
                return true;
            /* Ramasser boissons */
            case 4 :
                System.out.print("Nombre de Boissons énergisantes : ");
                int nbB = this.scanner();
                while (nbB < 1 || nbB >10) {
                    System.out.print("Nombre incorrect, ressaisissez un nombre "
                            + ": ");
                    nbB = this.scanner();
                }
                System.out.println();
                _citoyen.prendreBoissons(nbB);
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
            /* Boire une boisson énergisante */
            case 8 :
                _citoyen.boireBoisson();
                return true;
            /* Consulter le Talkie-Walkie */
            case 9 :
                System.out.println(this.talkie);
                return true;
            /* Mettre à jour le Talkie-Walkie */
            case 10 :
                _citoyen.majTalkie();
                return true;
            /* Se déplacer */
            case 11 :
                System.out.println("Déplacement vers :");
                System.out.println("  0 - Annuler");
                System.out.println("  1 - Nord");
                System.out.println("  2 - Sud");
                System.out.println("  3 - Est");
                System.out.println("  4 - Ouest");
                System.out.print("Direction : ");
                
                int dir = this.scanner();
                
                while (dir < 0 || dir > 4) {
                    System.out.print("Direction incorrecte, "
                             + "ressaisissez un nombre : ");
                    dir = this.scanner();
                }
                
                System.out.println();
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
    
    /* Protection du Scanner (saisie obligatoire d'un int) */
    private static int scanner() {
        int n = -1;
        boolean valid = false;
        Scanner sc = new Scanner(System.in);
        
        do {
            if(sc.hasNextInt()) {
                n = sc.nextInt();
                valid = true;
            } else {
                System.out.print("Veuillez saisir un nombre ! ");
                sc.next();
            }
        } while(!valid);
        
        return n;
    }

    /* Permet de laisser le temps de lire le bilan de fin de nuit (appelé à la 
    fin de la journée) */
    private void pressEnter(){
        try {
            System.in.read();
        } catch(IOException e) {}
    }
    
    /* Affiche la carte au début de la partie */
    private void afficherCarte() {
        for (int j = 0 ; j < this.carte.length ; j++) {
            for (int i = 0 ; i < this.carte[0].length ; i++) {
                if (j == 12 && i == 12) {
                    System.out.print("[V]");
                } else {
                    System.out.print("-#-");
                }
            }
            System.out.println();
        }
    }
}
