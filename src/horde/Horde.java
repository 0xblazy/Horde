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
                    System.out.println(citoyen);

                    boolean continuer = true;

                    /* Tant que le Citoyen souhaite continuer à jouer */
                    while (continuer) {
                        
                        /* Vérifie si le Citoyen est encore en vie (si il a 
                        encore des PV) */
                        if (citoyen.estMort()) {
                            System.out.println(citoyen.getNom() + " est mort");
                            continuer = false;
                            this.citoyens.remove(citoyen);
                        } else {
                            System.out.println("== ACTIONS ==");
                            /* Propose les actions disponibles en fonction de
                            l'emplacement du Citoyen */
                            if (citoyen.isEnVille()) {
                                for (String action : this.actionVille) {
                                    System.out.println("  " + action);
                                }
                                System.out.print("Action : ");
                                continuer = this.actionVille(citoyen,
                                        this.sc.nextInt());
                            } else {
                                for (String action : this.actionExterieur) {
                                    System.out.println("  " + action);
                                }
                                System.out.print("Action : ");
                                continuer = this.actionExterieur(citoyen,
                                        this.sc.nextInt());
                            }
                        }
                    }
                    citoyen.finDeTour();
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

            /* Affiche le bilan de la nuit (nombre et liste des morts) */
            System.out.println("==== BILAN DE LA NUIT ====");
            System.out.println(citoyensMorts.size() + " morts");
            for (Citoyen citoyenMort : citoyensMorts) {
                System.out.println(citoyenMort.getNom());
            }

        }

        /* Fin de la partie, affiche le Citoyen gagnant */
        System.out.println("===== PARTIE TERMINEE =====");
        System.out.println("Gagnant : " + this.citoyens.get(0).getNom());
    }

    /* Gère les actions en Ville */
    private boolean actionVille(Citoyen _citoyen, int _action) {
        /* Passer son tour */
        if (_action == 0) {
            System.out.println(_citoyen.getNom() + " passe son tour");
            return false;
        }
        /* Ouvrir porte */
        if (_action == 1) {
            _citoyen.ouvrirPorte();
            return true;
        }
        /* Fermer porte */
        if (_action == 2) {
            _citoyen.fermerPorte();
            return true;
        }
        /* Prendre ration */
        if (_action == 3) {
            _citoyen.prendreRation();
            return true;
        }
        /* Aller au puit */
        if (_action == 4) {
            _citoyen.allerAuPuit();
            return true;
        }
        /* Remplir gourde */
        if (_action == 5) {
            _citoyen.remplirGourde();
            return true;
        }
        /* Se déplacer */
        if (_action == 6) {
            System.out.println("Déplacement vers :");
            System.out.println("  1 - Nord");
            System.out.println("  2 - Sud");
            System.out.println("  3 - Est");
            System.out.println("  4 - Ouest");
            System.out.print("Direction : ");
            _citoyen.deplacer(this.sc.nextInt());
            return true;
        }
        return true;
    }

    /* Gère les actions à l'Exterieur */
    private boolean actionExterieur(Citoyen _citoyen, int _action) {
        /* Passer son tour */
        if (_action == 0) {
            System.out.println(_citoyen.getNom() + " passe son tour");
            return false;
        }
        /* Fouiller */
        if (_action == 1) {
            _citoyen.fouiller();
            return true;
        }
        /* Récupérer planches */
        if (_action == 2) {
            System.out.print("Nombre de planches de bois : ");
            _citoyen.prendrePlanches(this.sc.nextInt());
            return true;
        }
        /* Récupérer plaques */
        if (_action == 3) {
            System.out.print("Nombre de plaques de métal : ");
            _citoyen.prendreMetal(this.sc.nextInt());
            return true;
        }
        /* Récupérer boissons */
        if (_action == 4) {
            System.out.print("Nombre de boissons énergisantes : ");
            _citoyen.prendreBoissons(this.sc.nextInt());
            return true;
        }
        /* Attaquer zombies */
        if (_action == 5) {
            _citoyen.attaquerZombie();
            return true;
        }
        /* Manger une ration */
        if (_action == 6) {
            _citoyen.mangerRation();
            return true;
        }
        /* Boire une goude */
        if (_action == 7) {
            _citoyen.boireGourde();
            return true;
        }
        /* Se déplacer */
        if (_action == 9) {
            System.out.println("Déplacement vers :");
            System.out.println("  1 - Nord");
            System.out.println("  2 - Sud");
            System.out.println("  3 - Est");
            System.out.println("  4 - Ouest");
            System.out.print("Direction : ");
            _citoyen.deplacer(this.sc.nextInt());
            return true;
        }
        return false;
    }

    /* Retourne la liste de Citoyen */
    public ArrayList<Citoyen> getCitoyens() {
        return citoyens;
    }

    /* Retourne le Citoyen en _i */
    public Citoyen getCitoyenAtI(int _i) {
        return this.citoyens.get(_i);
    }
    
    /* Affiche toute la carte => Simple vérification */
    public void afficherCarte() {
        for (int j = 0; j < 25; j++) {
            for (int i = 0; i < 25; i++) {
                if (i == 12 && j == 12) {
                    System.out.print("| "
                            + ((Ville) carte[j][i]).affichageSimple() + " ");
                } else {
                    System.out.print("| "
                            + ((Exterieur) carte[j][i])
                                    .affichageSimple() + " ");
                }
            }
            System.out.println("|");
        }
    }

}
