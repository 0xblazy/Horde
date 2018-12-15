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
public class Citoyen {

    /* Carte du jeu */
    private Case[][] carte;
    /* Talkie-Walkie de la partie */
    private Talkie talkie;

    /* Nom du Citoyen */
    private String nom;
    /* Points de vie et points d'action du Citoyen (10 et 6 au début de la 
    partie) */
    private int pv,pa;

    /* PA max */
    private final int MAX_PA = 10;

    /* Coordonnées du Citoyen (12, 12 au début de la partie) */
    private int x, y;
    /* TRUE si le Citoyen est en Ville, FALSE sinon (TRUE au début de la 
    partie) */
    private boolean enVille;

    /* Taille de l'inventaire */
    private final int TAILLE_SAC = 10;
    /* Variables contenants le nombre d'items que possède le Citoyen dans son
    inventaire (le total de doit pas être supérieur à TAILLE_SAC */
    private int nbPlanches,nbMetal,nbBoissons ,nbGourde,nbRation;
    /* Booleens passant à TRUE après avoir bu une gourde ou mangé une ration
    => Repasse à FALSE à la fin de la journée */
    private boolean aBu,aMange;
    /* Booleen passant à TRUE après avoir bu une boisson énergisante */
    private boolean accroc;
    /* Compteur pour le besoin en boissons énergisantes */
    private int dose; 

    /* Contructeur */
    public Citoyen(String _nom, Case[][] _carte, Talkie _talkie) {
        this.carte = _carte;
        
        this.talkie = _talkie;

        this.nom = _nom;
        this.pv = 100;
        this.pa = 6;

        this.x = 12;
        this.y = 12;
        this.enVille = true;
        
        this.nbPlanches = 0;
        this.nbMetal = 0;
        this.nbBoissons = 0;
        this.nbGourde = 0;
        this.nbRation = 0;
        
        this.aBu = false;
        this.aMange = false;
        
        this.accroc = false;
    }

    /* DEPLACEMENTS */
    /* Permet au Citoyen de se déplacer dans la direction choisie (1 = Nord, 2 = 
    Sud, 3 = Est, 4 = Ouest) */
    public void deplacer(int _direction) {
        boolean possible = true;
        
        /* Vérifie si le Citoyen n'a plus de PA */
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de points d'action !");
            possible = false;
        }
        
        /* Vérifie si la porte est ouverte ou si il reste des zombies sur la 
        Case */
        if (this.enVille) {
            if (!((Ville) this.carte[12][12]).porteOuverte()) {
                System.out.println("La porte de la ville est fermée, " 
                        + this.nom + " ne peut pas sortir !!!");
                possible = false;
            }
        } else if (((Exterieur) this.carte[this.y][this.x]).encoreZombies()){
            System.out.println(this.nom + " doit tuer tous les zombies de la "
                    + "case avant de se déplacer !!!");
            possible = false;
        }

        /* Si le déplacement demandé est possible */
        if (possible) {
            switch (_direction) {
                /* Nord */
                case 1 :
                    if (this.y == 0) {
                        System.out.println(this.nom + " est déjà en haut de "
                                + "la Carte !!!");
                    } else if (this.x == 12 && this.y - 1 == 12) {
                        if (((Ville) this.carte[12][12]).porteOuverte()) {
                            this.deplacerN();
                        } else {
                            System.out.println("La porte est fermée " + this.nom 
                                + " est coincé dehors...");
                        }
                    } else {
                        this.deplacerN();
                    }
                    break;
                /* Sud */
                case 2 :
                    if (this.y == 24) {
                        System.out.println(this.nom + " est déjà en bas de "
                                + "la Carte !!!");
                    } else if (this.x == 12 && this.y + 1 == 12) {
                        if (((Ville) this.carte[12][12]).porteOuverte()) {
                            this.deplacerS();
                        } else {
                            System.out.println("La porte est fermée " + this.nom 
                                + " est coincé dehors...");
                        }
                    } else {
                        this.deplacerS();
                    }
                    break;
                /* Est */
                case 3 :
                    if (this.x == 24) {
                        System.out.println(this.nom + " est déjà à droite de "
                                + "la Carte !!!");
                    } else if (this.x + 1 == 12 && this.y == 12) {
                        if (((Ville) this.carte[12][12]).porteOuverte()) {
                            this.deplacerE();
                        } else {
                            System.out.println("La porte est fermée " + this.nom 
                                + " est coincé dehors...");
                        }
                    } else {
                        this.deplacerE();
                    }
                    break;
                /* Ouest */
                case 4:
                    if (this.x == 0) {
                        System.out.println(this.nom + " est déjà à gauche de "
                                + "la Carte !!!");
                    } else if (this.x - 1 == 12 && this.y == 12) {
                        if (((Ville) this.carte[12][12]).porteOuverte()) {
                            this.deplacerO();
                        } else {
                            System.out.println("La porte est fermée " + this.nom 
                                + " est coincé dehors...");
                        }
                    } else {
                        this.deplacerO();
                    }
                    break;
            }
        }
    }
    
    /* Déplace le Citoyen au Nord */
    private void deplacerN() {
        this.y--;
        this.pa--;
        this.enVille = (this.x == 12 && this.y == 12);
        System.out.println(this.nom + " se déplace au Nord");
    }
    
    /* Déplace le Citoyen au Sud */
    private void deplacerS() {
        this.y++;
        this.pa--;
        this.enVille = (this.x == 12 && this.y == 12);
        System.out.println(this.nom + " se déplace au Sud");
    }
    
    /* Déplace le Citoyen à l'Est */
    private void deplacerE() {
        this.x++;
        this.pa--;
        this.enVille = (this.x == 12 && this.y == 12);
        System.out.println(this.nom + " se déplace à l'Est");
    }
    
    /* Déplace le Citoyen à l'Ouest */
    private void deplacerO() {
        this.x--;
        this.pa--;
        this.enVille = (this.x == 12 && this.y == 12);
        System.out.println(this.nom + " se déplace à l'Ouest");
    }

    /* BOIRE/MANGER */
    /* Permet de récupérer 6 PA en mangeant une ration */
    public void mangerRation() {
        if (this.aMange) {
            System.out.println(this.nom + " a déjà mangé une ration "
                    + "aujourd'hui...");
        } else if (this.nbRation == 0) {
            System.out.println(this.nom + " n'a plus de ration dans son "
                    + "inventaire...");
        } else {
            this.nbRation = 0;
            this.aMange = true;
            this.pa += 6;
            if (this.pa > this.MAX_PA) {
                this.pa = this.MAX_PA;
            }
            System.out.println(this.nom + " mange une ration et récupère 6 PA");
        }
    }

    /* Permet de récupérer 6 PA en buvant une gourde */
    public void boireGourde() {
        if (this.aBu) {
            System.out.println(this.nom + " a déjà bu une gourde "
                    + "aujourd'hui...");
        } else if (this.nbGourde == 0) {
            System.out.println(this.nom + " n'a plus de gourde dans son "
                    + "inventaire...");
        } else {
            this.nbGourde = 0;
            this.aBu = true;
            this.pa += 6;
            if (this.pa > this.MAX_PA) {
                this.pa = this.MAX_PA;
            }
            System.out.println(this.nom + " boit une gourde et récupère 6 PA");
        }
    }
    
    /* Permet de récupérer 4 PA en buvant une boisson énergisante */
    public void boireBoisson() {
        if (this.nbBoissons == 0) {
            System.out.println(this.nom + " n'a plus de boissons énergisantes "
                    + "dans son inventaire...");
        } else {
            this.nbBoissons --;
            this.pa += 4;
            if (this.pa > this.MAX_PA) {
                this.pa = this.MAX_PA;
            }
            this.accroc = true;
            this.dose = 3;
            System.out.println(this.nom + " boit une boisson énergisante et "
                    + "récupère 4 PA");
        }
    }
    
    /* ACTIONS EN VILLE */
    /* Ouvre la porte de la ville */
    public void ouvrirPorte() {
        /* Vérifie si le Citoyen n'a plus de PA */
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de points d'action !");
        } else if (((Ville) this.carte[12][12]).ouvrirPorte()) {
            this.pa--;
        }
    }

    /* Ferme la porte de la ville */
    public void fermerPorte() {
        /* Vérifie si le Citoyen n'a plus de PA */
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de points d'action !");
        } else if (((Ville) this.carte[12][12]).fermerPorte()) {
            this.pa--;
        }
    }

    /* Prend une ration dans l'entrepôt */
    public void prendreRation() {
        if (this.invRestant() == 0) {
            System.out.println(this.nom + " n'a plus de place dans son "
                    + "inventaire...");
        } else if (this.nbRation == 1) {
            System.out.println(this.nom + " a déjà une ration dans son "
                    + "inventaire...");
        } else if (((Ville) this.carte[12][12]).prendreRation()) {
            this.nbRation = 1;
            System.out.println(this.nom + " prend une ration");
        }
    }

    /* Va au puit pour récupérer 6 PA */
    public void allerAuPuit() {
        this.pa += 6;
        if (this.pa > this.MAX_PA) {
            this.pa = this.MAX_PA;
        }
        System.out.println(this.nom + " va boire au puit et récupère 6 PA");
    }

    /* Remplis une goude et l'ajoute à l'inventaire */
    public void remplirGourde() {
        if (this.invRestant() == 0) {
            System.out.println(this.nom + " n'a plus de place dans son "
                    + "inventaire...");
        } else if (this.nbGourde == 1) {
            System.out.println(this.nom + " a déjà une gourde dans son "
                    + "inventaire...");
        } else {
            this.nbGourde = 1; 
            System.out.println(this.nom + " ajoute une gourde à son "
                    + "inventaire");
        }
    }
    
    /* Dépose des planches dans l'entrepot */
    public void deposerPlanches() {
        ((Ville) this.carte[12][12]).deposerPlanches(this.nbPlanches);
        System.out.println(this.nom + " dépose " + this.nbPlanches 
                + " Planches de bois dans l'entrepôt");
        this.nbPlanches = 0;
    }
    
    /* Dépose du metal dans l'entrepot */
    public void deposerMetal() {
        ((Ville) this.carte[12][12]).deposerMetal(this.nbMetal);
        System.out.println(this.nom + " dépose " + this.nbMetal 
                + " plaques de Métal dans l'entrepôt");
        this.nbMetal = 0;
    }
    
    /* Dépose des boissons dans l'entrepot */
    public void deposerBoissons(int _qt) {
        if(this.nbBoissons < _qt) {
            System.out.println(this.nom + " n'a que " + this.nbBoissons 
                    + " Boissons énergisantes dans son inventaire !!!");
        } else {
            ((Ville) this.carte[12][12]).deposerBoissons(_qt);
            System.out.println(this.nom + " dépose " + _qt
                + " Boissons énergisantes dans l'entrepôt");
            this.nbBoissons -= _qt;
        }
    }
    
    /* Contruit une défense */
    public void construire(int _id, int _pa) {
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de points d'action !!!");
        } else if(this.pa - _pa < 0) {
            System.out.println(this.nom + " n'a pas assez de points d'action "
                    + "!!!");
        } else if(((Ville) this.carte[12][12]).construire(_id, _pa)) {
            this.pa -= _pa;
            System.out.println(this.nom + " fait progresser la construction "
                    + "de " + _pa + " PA");
        }
    }
    
    /* Contruit une nouvelle défense */
    public void nouvelleDefense(int _id) {
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de points d'action !!!");
        } else if(((Ville) this.carte[12][12]).nouvelleDefense(_id)) {
            this.pa --;
            System.out.println(this.nom + " lance la construction de : " 
                    + Defense.Defenses.values()[_id].nom);
        }
    }
    
    /* ACTIONS EXTERIEUR */
    /* Fouille la Case Exterieur sur laquelle est le Citoyen */
    public void fouiller() {
        /* Vérifie si le Citoyen n'a plus de PA */
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de points d'action !!!");
        } else if (((Exterieur) this.carte[this.y][this.x]).fouiller()) {
            this.pa--;
            System.out.println(this.nom + " fouille la case");
        }
    }

    /* Prend _qt planches s'il y a assez de place dans l'inventaire */
    public void prendrePlanches(int _qt) {
        if (this.invRestant() - _qt < 0) {
            System.out.println(this.nom + " n' plus assez de place dans son "
                    + "inventaire...");
        } else if (((Exterieur) this.carte[this.y][this.x])
                .prendrePlanches(_qt)) {
            this.nbPlanches += _qt;
            System.out.println(this.nom + " ajoute " + _qt + " Planches de "
                    + "bois à son inventaire");
        }
    }

    /* Prend _qt plaques s'il y a assez de place dans l'inventaire */
    public void prendreMetal(int _qt) {
        if (this.invRestant() - _qt < 0) {
            System.out.println(this.nom + " n' plus assez de place dans son "
                    + "inventaire...");
        } else if (((Exterieur) this.carte[this.y][this.x]).prendreMetal(_qt)) {
            this.nbMetal += _qt;
            System.out.println(this.nom + " ajoute " + _qt + " plaques de "
                    + "Métal à son inventaire");
        } 
    }

    /* Prend _qt boisson s'il y a assez de place dans l'inventaire */
    public void prendreBoissons(int _qt) {
        if (this.invRestant() - _qt < 0) {
            System.out.println(this.nom + " n' plus assez de place dans son "
                    + "inventaire...");
        } else if (((Exterieur) this.carte[this.y][this.x])
                .prendreBoissons(_qt)) {
            this.nbBoissons += _qt;
            System.out.println(this.nom + " ajoute " + _qt + " Boissons "
                    + "énergisante à son inventaire");
        }
    }

    /* Permet d'attaquer un zombie */
    public void attaquerZombie() {
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de points d'action !!!");
        } else if (((Exterieur) this.carte[this.y][this.x]).attaquerZombie()) {
            System.out.println(this.nom + " attaque un zombie...");
            this.pa--;

            if (Math.random() * 100 < 10) {
                System.out.println(this.nom + " est blessé ! Il perd 10 PV !");
                this.pv -= 10;
            } else {
                System.out.println(this.nom + " massacre le zombie sans une "
                        + "égratignure");
            }
        }
    }
    
    /* Permet de mettre à jour le Talkie-Walkie */
    public void majTalkie() {
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de points d'action !!!");
        } else if (this.talkie.miseAJour(this.x, this.y)) {
            this.pa--;
            System.out.println(this.nom + " met à jour le Talkie-Walkie");
        }
    }
    
    /* Retourne le Citoyen sous la forme Nom (PV, PA) [x, y] Inventaire */
    public String toString() {
        String s = this.nom + " (" + this.pv + " PV, " + this.pa + " PA)";

        if (this.enVille) {
            s += " [En ville]\n";
        } else {
            s += " [" + this.x + "," + this.y + "]\n";
        }
        
        if (this.accroc) {
            if (this.dose > 0) {
                 s += "  Accroc : " + this.dose + " tours restants\n";
            } else {
                s += "  Accroc : En manque !!!\n";
            } 
        }

        s += this.getInventaire();

        return s;
    }

    /* Retourne l'inventaire du Citoyen sous la forme d'une chaîne de 
    caractère */
    private String getInventaire() {
        String s = "  Inventaire (" + (this.nbPlanches + this.nbMetal
                + this.nbBoissons + this.nbGourde + this.nbRation)
                + "/" + this.TAILLE_SAC + "):\n";
        s += "    - Planches de bois : " + this.nbPlanches + "\n";
        s += "    - Plaques de métal : " + this.nbMetal + "\n";
        s += "    - Boissons énergisantes : " + this.nbBoissons + "\n";
        s += "    - Gourde : " + this.nbGourde + "\n";
        s += "    - Ration : " + this.nbRation;

        return s;
    }
    
    /* Retourne un entier correspondant au nombre d'emplacements restants dans
    l'inventaire */
    private int invRestant() {
        return this.TAILLE_SAC - (this.nbPlanches + this.nbMetal 
                + this.nbBoissons + this.nbGourde + this.nbRation);
    }
    
    /* Appelée à la fin du tour d'un Citoyen => Régénère 4 PA et gère 
    l'addiction aux boissons énergisantes */
    public void finDeTour() {
        this.pa += 4;
        if (this.pa > this.MAX_PA) {
            this.pa = this.MAX_PA;
        }
        
        if (this.accroc) {
            this.dose --;
            if (this.dose < 0) {
                this.pv -= 5; 
            }
        }
    }
    
    /* Appelée à la fin de la journée => repasse aBu et aMange à FALSE */
    public void finDeJournee() {
        this.aBu = false;
        this.aMange = false;
    }
    
    /* TRUE si le Citoyen est mort (moins d'un PV), FALSE sinon */
    public boolean estMort() {
        if (this.pv < 1) {
            return true;
        }
        return false;
    }

    /* Retourne le nom du Citoyen */
    public String getNom() {
        return this.nom;
    }

    /* Retourne la coordonnée X du Citoyen */
    public int getX() {
        return this.x;
    }

    /* Retourne la coordonnée Y du Citoyen */
    public int getY() {
        return this.y;
    }

    /* TRUE si le Citoyen est en Ville, FALSE sinon */
    public boolean isEnVille() {
        return this.enVille;
    }

}
