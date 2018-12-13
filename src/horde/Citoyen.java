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

    /* Carte de la partie à laquelle participe le Citoyen */
    private Case[][] carte;
    
    /* Talkie */
    private Talkie talkie;

    private String nom;
    private int pv, pa;

    /* PA Max */
    private final int MAX_PA = 10;

    /* Coordonnées du Citoyen (12, 12 au début de la partie) */
    private int x, y;
    private boolean enVille;

    /* Taille de l'inventaire */
    private final int TAILLE_SAC = 10;
    /* Variables contenants le nombre d'items que possède le joueur dans son
    inventaire (le total de doit pas être supérieur à TAILLE_SAC */
    private int nbPlanches, nbMetal, nbBoissons, nbGourde, nbRation;
    /* Booleens passant à TRUE après avoir bu une gourde ou mangé une ration
    => Repasse à FALSE à la fin de la journée */
    private boolean aBu, aMange;
    /* Booleen passant à vrai après avoir bu une boisson énergisante */
    private boolean accroc;
    /* Compteur pour le besoin en boisson énergisante */
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
    
    /* Permet au joueur de se déplacer dans la direction choisie (1 = Nord, 2 = 
    Sud, 3 = Est, 4 = Ouest) */
    public boolean deplacer(int _direction) {
        /* Vérifie la direction donnée */
        if (_direction < 1 || _direction > 4) {
            System.out.println("La direction choisie n'est pas valide");
            return false;
        }

        /* Vérifie si la porte est ouverte ou si il reste des zombies sur la 
        Case */
        if (this.enVille) {
            if (!((Ville) this.carte[12][12]).porteOuverte()) {
                System.out.println("La porte de la ville est fermée" + this.nom
                        + "ne peut pas se déplacer !");
                return false;
            }
        } else {
            if (((Exterieur) this.carte[this.y][this.x]).encoreZombies()) {
                System.out.println("Il reste des zombies !");
                return false;
            }
        }
        /* Vérifie si le Citoyen n'a plus de PA */
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de point d'action !");
            return false;
        }

        /* Nord */
        if (_direction == 1) {
            if (this.y == 0) {
                System.out.println(this.nom + " ne peut pas aller au Nord !");
                return false;
            }
            if (this.x == 12 && this.y - 1 == 12) {
                if (((Ville) this.carte[this.y - 1][this.x]).porteOuverte()) {
                    this.deplacerN();
                    return true;
                } else {
                    System.out.println("La porte est fermée " + this.nom 
                            + " est coincé dehors");
                    return false;
                }
            } else {
                this.deplacerN();
                return true;
            }
        }

        /* Sud */
        if (_direction == 2) {
            if (this.y == 24) {
                System.out.println(this.nom + " ne peut pas aller au Sud !");
                return false;
            }
            if (this.x == 12 && this.y + 1 == 12) {
                if (((Ville) this.carte[this.y + 1][this.x]).porteOuverte()) {
                    this.deplacerS();
                    return true;
                } else {
                    System.out.println("La porte est fermée " + this.nom 
                            + " est coincé dehors");
                    return false;
                }
            } else {
                this.deplacerS();
                return true;
            }
        }

        /* Est */
        if (_direction == 3) {
            if (this.x == 24) {
                System.out.println(this.nom + " ne peut pas aller à l'Est !");
                return false;
            }
            if (this.x + 1 == 12 && this.y == 12) {
                if (((Ville) this.carte[this.y][this.x + 1]).porteOuverte()) {
                    this.deplacerE();
                    return true;
                } else {
                    System.out.println("La porte est fermée " + this.nom 
                            + " est coincé dehors");
                    return false;
                }
            } else {
                this.deplacerE();
                return true;
            }
        }

        /* Ouest */
        if (_direction == 4) {
            if (this.x == 0) {
                System.out.println(this.nom + " ne peut pas aller à l'Ouest !");
                return false;
            }
            if (this.x - 1 == 12 && this.y == 12) {
                if (((Ville) this.carte[this.y][this.x - 1]).porteOuverte()) {
                    this.deplacerO();
                    return true;
                } else {
                    System.out.println("La porte est fermée " + this.nom 
                            + " est coincé dehors");
                    return false;
                }
            } else {
                this.deplacerO();
                return true;
            }
        }
        return false;
    }
    
    /* Déplace le Citoyen au Nord */
    private boolean deplacerN() {
        this.y--;
        this.pa--;
        this.enVille = (this.x == 12 && this.y == 12);
        return true;
    }
    
    /* Déplace le Citoyen au Sud */
    private boolean deplacerS() {
        this.y++;
        this.pa--;
        this.enVille = (this.x == 12 && this.y == 12);
        return true;
    }
    
    /* Déplace le Citoyen à l'Est */
    private boolean deplacerE() {
        this.x++;
        this.pa--;
        this.enVille = (this.x == 12 && this.y == 12);
        return true;
    }
    
    /* Déplace le Citoyen à l'Ouest */
    private boolean deplacerO() {
        this.x--;
        this.pa--;
        this.enVille = (this.x == 12 && this.y == 12);
        return true;
    }

    /* BOIRE/MANGER */
    
    /* Permet de récupérer 6 PA en mangeant une ration */
    public void mangerRation() {
        if (this.aMange) {
            System.out.println(this.nom + " a déjà mangé une ration "
                    + "aujourd'hui");
        } else if (this.nbRation == 0) {
            System.out.println(this.nom + " n'a plus de ration dans son "
                    + "inventaire");
        } else {
            this.nbRation = 0;
            this.aMange = true;
            this.pa += 6;
            if (this.pa > this.MAX_PA) {
                this.pa = this.MAX_PA;
            }
            System.out.println(this.nom + " mange une ration");
        }
    }

    /* Permet de récupérer 6 PA en buvant une gourde */
    public void boireGourde() {
        if (this.aBu) {
            System.out.println(this.nom + " a déjà bu une gourde "
                    + "aujourd'hui");
        } else if (this.nbGourde == 0) {
            System.out.println(this.nom + " n'a plus de gourde dans son "
                    + "inventaire");
        } else {
            this.nbGourde = 0;
            this.aBu = true;
            this.pa += 6;
            if (this.pa > this.MAX_PA) {
                this.pa = this.MAX_PA;
            }
            System.out.println(this.nom + " boit un gourde");
        }
    }
    
    /* Permet de récupérer 4 PA en buvant une boisson énergisante */
    public void boireBoisson() {
        if (this.nbBoissons == 0) {
            System.out.println(this.nom + " n'a plus de boissons énergisantes "
                    + "dans son inventaire");
        } else {
            this.nbBoissons --;
            this.pa += 4;
            if (this.pa > this.MAX_PA) {
                this.pa = this.MAX_PA;
            }
            this.accroc = true;
            this.dose = 3;
            System.out.println(this.nom + " boit une boisson énergisante");
        }
    }
    
    /* ACTIONS EN VILLE */
    
    /* Ouvre la porte de la ville */
    public boolean ouvrirPorte() {
        /* Vérifie si le Citoyen n'a plus de PA */
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de point d'action !");
            return false;
        }
        if (((Ville) this.carte[12][12]).ouvrirPorte()) {
            this.pa--;
            return true;
        } else {
            return false;
        }
    }

    /* Ferme la porte de la ville */
    public boolean fermerPorte() {
        /* Vérifie si le Citoyen n'a plus de PA */
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de point d'action !");
            return false;
        }
        if (((Ville) this.carte[12][12]).fermerPorte()) {
            this.pa--;
            return true;
        } else {
            return false;
        }
    }

    /* Prend une ration dans l'entrepôt */
    public boolean prendreRation() {
        if (this.invRestant() == 0) {
            System.out.println("Il n'y plus de place dans l'inventaire");
            return false;
        }
        if (this.nbRation == 1) {
            System.out.println(this.nom + " a déjà une ration dans son "
                    + "inventaire");
            return false;
        }
        if (((Ville) this.carte[12][12]).prendreRation()) {
            this.nbRation = 1;
            System.out.println(this.nom + " prend une ration");
            return true;
        } else {
            return false;
        }
    }

    /* Va au puit pour récupérer 6 PA */
    public void allerAuPuit() {
        this.pa += 6;
        if (this.pa > this.MAX_PA) {
            this.pa = this.MAX_PA;
        }
        System.out.println(this.nom + " va boire au puit");
    }

    /* Remplis une goude et l'ajoute à l'inventaire */
    public void remplirGourde() {
        if (this.invRestant() == 0) {
            System.out.println("Il n'y plus de place dans l'inventaire");
        } else if (this.nbGourde == 1) {
            System.out.println(this.nom + " a déjà une gourde dans son "
                    + "inventaire");
        } else {
            this.nbGourde++; 
        }
    }
    
    /* Dépose des planches dans l'entrepot */
    public void deposerPlanches() {
        ((Ville) this.carte[12][12]).deposerPlanches(this.nbPlanches);
        this.nbPlanches = 0;
    }
    
    /* Dépose du metal dans l'entrepot */
    public void deposerMetal() {
        ((Ville) this.carte[12][12]).deposerMetal(this.nbMetal);
        this.nbMetal = 0;
    }
    
    /* Dépose des boissons dans l'entrepot */
    public void deposerBoissons(int _qt) {
        if(this.nbBoissons < _qt) {
            System.out.println(this.nom + " n'a que " + this.nbBoissons 
                    + " dans son inventaire !");
        } else {
            ((Ville) this.carte[12][12]).deposerBoissons(_qt);
            this.nbBoissons -= _qt;
        }
    }
    
    /* Contruit une défense */
    public void construire(int _id, int _pa) {
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de point d'action !");
        } else if(this.pa - _pa < 0) {
            System.out.println(this.nom + " n'a pas assez de point d'action !");
        } else if(((Ville) this.carte[12][12]).construire(_id, _pa)) {
            this.pa -= _pa;
            System.out.println(this.nom + " a maintenant " + this.pa
                    + " PA");
        }
    }
    
    /* Contruit une nouvelle défense */
    public void nouvelleDefense(int _id) {
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de point d'action !");
        } else if(((Ville) this.carte[12][12]).nouvelleDefense(_id)) {
            this.pa --;
            System.out.println(this.nom + " a maintenant " + this.pa
                    + " PA");
        }
    }
    
    /* ACTIONS EXTERIEUR */

    /* Fouille la Case Exterieur sur laquelle est le Citoyen */
    public void fouiller() {
        /* Vérifie si le Citoyen n'a plus de PA */
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de point d'action !");
        } else if (((Exterieur) this.carte[this.y][this.x]).fouiller()) {
            this.pa--;
            System.out.println(this.nom + " a maintenant " + this.pa
                    + " PA");
        }
    }

    /* Prend _qt planches s'il y a assez de place dans l'inventaire */
    public void prendrePlanches(int _qt) {
        if (this.invRestant() - _qt < 0) {
            System.out.println("Il n'y a pas assez de place dans l'inventaire");
        } else if (((Exterieur) this.carte[this.y][this.x]).prendrePlanches(_qt)) {
            this.nbPlanches += _qt;
        }
    }

    /* Prend _qt plaques s'il y a assez de place dans l'inventaire */
    public void prendreMetal(int _qt) {
        if (this.invRestant() - _qt < 0) {
            System.out.println("Il n'y a pas assez de place dans l'inventaire");
        } else if (((Exterieur) this.carte[this.y][this.x]).prendreMetal(_qt)) {
            this.nbMetal += _qt;
        } 
    }

    /* Prend _qt boisson s'il y a assez de place dans l'inventaire */
    public void prendreBoissons(int _qt) {
        if (this.invRestant() - _qt < 0) {
            System.out.println("Il n'y a pas assez de place dans l'inventaire");
        } else if (((Exterieur) this.carte[this.y][this.x]).prendreBoissons(_qt)) {
            this.nbBoissons += _qt;
        }
    }

    /* Permet d'attaquer un zombie */
    public void attaquerZombie() {
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de point d'action !");
        } else if (((Exterieur) this.carte[this.y][this.x]).attaquerZombie()) {
            System.out.println(this.nom + " attaque un zombie");
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
            System.out.println(this.nom + " n'a plus de point d'action !");
        } else if (this.talkie.miseAJour(this.x, this.y)) {
            this.pa--;
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
    public String getInventaire() {
        String s = "  Inventaire (" + (this.nbPlanches + this.nbMetal
                + this.nbBoissons + this.nbGourde + this.nbRation)
                + "/" + this.TAILLE_SAC + "):\n";
        s += "    - Planches de bois : " + this.nbPlanches + "\n";
        s += "    - Plaques de métal : " + this.nbMetal + "\n";
        s += "    - Boissons énergisantes : " + this.nbBoissons + "\n";
        s += "    - Gourdes : " + this.nbGourde + "\n";
        s += "    - Rations : " + this.nbRation;

        return s;
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

    /* Retourne un entier correspondant au nombre d'emplacements restants dans
    l'inventaire */
    public int invRestant() {
        return 10 - (this.nbPlanches + this.nbMetal + this.nbBoissons
                + this.nbGourde + this.nbRation);
    }

    /* TRUE si le Citoyen est en Ville, FALSE sinon */
    public boolean isEnVille() {
        return this.enVille;
    }

    /* TRUE si le Citoyen est mort (moins d'un PV), FALSE sinon */
    public boolean estMort() {
        if (this.pv < 1) {
            return true;
        }
        return false;
    }

    /* Appelée à la fin du tour d'un Citoyen => Régénère 4 PA */
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
    
    /* Appelé à la fin de la journée => repasse aBu et aMange à FALSE */
    public void finDeJournee() {
        this.aBu = false;
        this.aMange = false;
    }

}
