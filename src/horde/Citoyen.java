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
    private int nbPlanche, nbMetal, nbBoissons, nbGourde, nbRation;
    /* Booleens passant à TRUE après avoir bu une gourde ou mangé une ration
    => Repasse à FALSE à la fin de la journée */
    private boolean aBu, aMange;

    /* Contructeur */
    public Citoyen(String _nom, Case[][] _carte) {
        this.carte = _carte;

        this.nom = _nom;
        this.pv = 100;
        this.pa = 6;

        this.x = 12;
        this.y = 12;
        this.enVille = true;
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
        System.out.println(this.nom + " a maintenant " + this.pa
                + " PA");
        this.enVille = (this.x == 12 && this.y == 12);
        return true;
    }
    
    /* Déplace le Citoyen au Sud */
    private boolean deplacerS() {
        this.y++;
        this.pa--;
        System.out.println(this.nom + " a maintenant " + this.pa
                + " PA");
        this.enVille = (this.x == 12 && this.y == 12);
        return true;
    }
    
    /* Déplace le Citoyen à l'Est */
    private boolean deplacerE() {
        this.x++;
        this.pa--;
        System.out.println(this.nom + " a maintenant " + this.pa
                + " PA");
        this.enVille = (this.x == 12 && this.y == 12);
        return true;
    }
    
    /* Déplace le Citoyen à l'Ouest */
    private boolean deplacerO() {
        this.x--;
        this.pa--;
        System.out.println(this.nom + " a maintenant " + this.pa
                + " PA");
        this.enVille = (this.x == 12 && this.y == 12);
        return true;
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
            System.out.println(this.nom + " a maintenant " + this.pa
                    + " PA");
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
            System.out.println(this.nom + " a maintenant " + this.pa
                    + " PA");
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
        System.out.println(this.nom + " va au puit, il a maintenant "
                + this.pa + " PA");
    }

    /* Remplis une goude et l'ajoute à l'inventaire */
    public boolean remplirGourde() {
        if (this.invRestant() == 0) {
            System.out.println("Il n'y plus de place dans l'inventaire");
            return false;
        }
        if (this.nbGourde == 1) {
            System.out.println(this.nom + " a déjà une gourde dans son "
                    + "inventaire");
            return false;
        }
        this.nbGourde++;
        return true;
    }

    
    /* ACTIONS EXTERIEUR */

    /* Fouille la Case Exterieur sur laquelle est le Citoyen */
    public boolean fouiller() {
        /* Vérifie si le Citoyen n'a plus de PA */
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de point d'action !");
            return false;
        }
        if (((Exterieur) this.carte[this.y][this.x]).fouiller()) {
            this.pa--;
            System.out.println(this.nom + " a maintenant " + this.pa
                    + " PA");
            return true;
        } else {
            return false;
        }
    }

    /* Prend _qt planches s'il y a assez de place dans l'inventaire */
    public boolean prendrePlanches(int _qt) {
        if (this.invRestant() - _qt < 0) {
            System.out.println("Il n'y a pas assez de place dans l'inventaire");
            return false;
        }
        if (((Exterieur) this.carte[this.y][this.x]).prendrePlanches(_qt)) {
            this.nbPlanche += _qt;
            return true;
        } else {
            return false;
        }
    }

    /* Prend _qt plaques s'il y a assez de place dans l'inventaire */
    public boolean prendreMetal(int _qt) {
        if (this.invRestant() - _qt < 0) {
            System.out.println("Il n'y a pas assez de place dans l'inventaire");
            return false;
        }
        if (((Exterieur) this.carte[this.y][this.x]).prendreMetal(_qt)) {
            this.nbMetal += _qt;
            return true;
        } else {
            return false;
        }
    }

    /* Prend _qt boisson s'il y a assez de place dans l'inventaire */
    public boolean prendreBoissons(int _qt) {
        if (this.invRestant() - _qt < 0) {
            System.out.println("Il n'y a pas assez de place dans l'inventaire");
            return false;
        }
        if (((Exterieur) this.carte[this.y][this.x]).prendreBoissons(_qt)) {
            this.nbBoissons += _qt;
            return true;
        } else {
            return false;
        }
    }

    /* Permet d'attaquer un zombie */
    public boolean attaquerZombie() {
        /* Vérifie si le Citoyen n'a plus de PA */
        if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de point d'action !");
            return false;
        }

        if (((Exterieur) this.carte[this.y][this.x]).attaquerZombie()) {
            System.out.println(this.nom + " attaque un zombie");
            this.pa--;

            if (Math.random() * 100 < 10) {
                System.out.println(this.nom + " est blessé !");
                this.pv -= 10;
                System.out.println(this.nom + " a maintenant " + this.pv
                        + " PV");
            }
            System.out.println(this.nom + " a maintenant " + this.pa
                    + " PA");

            return true;
        }
        return false;
    }

    /* Permet de récupérer 6 PA en mangeant une ration */
    public boolean mangerRation() {
        if (this.aMange) {
            System.out.println(this.nom + " a déjà mangé une ration "
                    + "aujourd'hui");
            return false;
        }
        if (this.nbRation == 0) {
            System.out.println(this.nom + " n'a plus de ration dans son "
                    + "inventaire");
            return false;
        } else {
            this.nbRation = 0;
            this.aMange = true;
            this.pa += 6;
            if (this.pa > this.MAX_PA) {
                this.pa = this.MAX_PA;
            }
            System.out.println(this.nom + " a maintenant " + this.pa + " PA");
            return true;
        }
    }

    /* Permet de récupérer 6 PA en buvant une gourde */
    public boolean boireGourde() {
        if (this.aBu) {
            System.out.println(this.nom + " a déjà bu une gourde "
                    + "aujourd'hui");
            return false;
        }
        if (this.nbGourde == 0) {
            System.out.println(this.nom + " n'a plus de gourde dans son "
                    + "inventaire");
            return false;
        } else {
            this.nbGourde = 0;
            this.aBu = true;
            this.pa += 6;
            if (this.pa > this.MAX_PA) {
                this.pa = this.MAX_PA;
            }
            System.out.println(this.nom + " a maintenant " + this.pa + " PA");
            return true;
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

        s += this.getInventaire();

        return s;
    }

    /* Retourne l'inventaire du Citoyen sous la forme d'une chaîne de 
    caractère */
    public String getInventaire() {
        String s = "  Inventaire (" + (this.nbPlanche + this.nbMetal
                + this.nbBoissons + this.nbGourde + this.nbRation)
                + "/" + this.TAILLE_SAC + "):\n";
        s += "    - Planches de bois : " + this.nbPlanche + "\n";
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
        return 10 - (this.nbPlanche + this.nbMetal + this.nbBoissons
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
    }

}
