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
    private int nbPlanche, nbMetal, nbBoisson, nbGourde, nbRation;

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

    /* Permet au joueur de se déplacer dans la direction choisie (1 = Nord, 2 = 
    Sud, 3 = Est, 4 = Ouest) => Retourne false si l'action de n'effectue pas */
    public boolean deplacer(int _direction) {
        /* Vérifie la direction donnée */
        if (_direction < 1 || _direction > 4) {
            System.out.println("La direction choisie n'est pas valide");
            return false;
        }

        /* Vérifie si la porte est ouverte ou si il reste des zombies sur la 
        case */
        if (this.enVille) {
            if (!((Ville) this.carte[12][12]).porteOuverte()) {
                System.out.println("La porte de la ville est fermée" + this.nom
                        + "ne peux pas se déplacer !");
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
                System.out.println(this.nom + " ne peut pas au Nord !");
                return false;
            } else {
                this.y--;
                this.pa--;
                System.out.println(this.nom + "se déplace aux coordonnées"
                        + this.x + this.y);
                return true;
            }
        }

        /* Sud */
        if (_direction == 2) {
            if (this.y == 24) {
                System.out.println(this.nom + " ne peut pas aller au Sud !");
                return false;
            } else {
                this.y++;
                this.pa--;
                System.out.println(this.nom + "se déplace aux coordonnées"
                        + this.x + this.y);
                return true;
            }
        }

        /* Est */
        if (_direction == 3) {
            if (this.x == 24) {
                System.out.println(this.nom + " ne peut pas aller à l'Est !");
                return false;
            } else {
                this.x++;
                this.pa--;
                System.out.println(this.nom + "se déplace aux coordonnées"
                        + this.x + this.y);
                return true;
            }
        }

        /* Ouest */
        if (_direction == 4) {
            if (this.x == 0) {
                System.out.println(this.nom + " ne peut pas aller à l'Ouest !");
                return false;
            } else {
                this.x--;
                this.pa--;
                System.out.println(this.nom + "se déplace aux coordonnées"
                        + this.x + this.y);
                return true;
            }
        }
        return false;
    }

    /* Permet d'attaquer un zombie => Retourne false si l'action ne s'effectue
    pas */
    public boolean attaquer() {
        /* Vérifie si le Citoyen est en Ville */
        if(enVille) {
            System.out.println(this.nom + " en est ville !");
            return false;
        }
        /* Vérifie si le Citoyen n'a plus de PA */
        if(this.pa == 0) {
            System.out.println(this.nom + " n'a plus de point d'action !");
            return false;
        }
        
        /* Essaye d'attaquer un zombie */
        if(((Exterieur) this.carte[this.y][this.x]).attaquerZombie()) {
            System.out.println(this.nom + " attaque un zombie");
            this.pa--;

            if (Math.random() * 100 < 10) {
                System.out.println(this.nom + " est blessé !");
                this.pv -= 10;
            }
            return true;
        } else {
            System.out.println("Il n'y a pas de zombies sur cette case");
            return false;
        }

    }
    
    /* Ouvre la porte de la ville */
    public void ouvrirPorte() {
        if(((Ville) this.carte[12][12]).ouvrirPorte()){
            this.pa--;
        }
    }
    
    /* Ferme la porte de la ville */
    public void fermerPorte() {
        if(((Ville) this.carte[12][12]).fermerPorte()){
            this.pa--;
        }
    }
    
    /* Prendre une ration */
    public void prendreRation() {
        if(this.invRestant() == 0) {
            System.out.println("Il n'y plus de place dans l'inventaire");
        } else if(((Ville) this.carte[12][12]).prendreRation()) {
            this.nbRation++;
        }
    }
    
    /* Aller au puit */
    public void allerAuPuit(){
        this.pa += 6;
        if(this.pa > this.MAX_PA) this.pa = this.MAX_PA;
        System.out.println(this.nom + " va au puit, il a maintenant " +
                this.pa + " PA");
    }
    
    /* Remplir une goude */
    public void remplirGourde(){
        if(this.invRestant() == 0) {
            System.out.println("Il n'y plus de place dans l'inventaire");
        } else {
            this.nbGourde++;
            System.out.println(this.nom + " a maintenant " + this.nbGourde +
                    " dans son inventaire");
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
        
        s += "  Inventaire (" + (this.nbPlanche + this.nbMetal + this.nbBoisson 
                + this.nbGourde + this.nbRation) + "/" + this.TAILLE_SAC 
                + "):\n";
        s += "    - Planches de bois : " + this.nbPlanche + "\n";
        s += "    - Plaques de métal : " + this.nbMetal + "\n";
        s += "    - Boissons énergisantes : " + this.nbBoisson + "\n";
        s += "    - Gourdes : " + this.nbGourde + "\n";
        s += "    - Rations : " + this.nbRation + "\n";

        return s;
    }

    public String getNom() {
        return this.nom;
    }
    
    public int invRestant() {
        return 10 - (this.nbPlanche + this.nbMetal + this.nbBoisson + 
                this.nbGourde + this.nbRation);
    }

    public boolean isEnVille() {
        return this.enVille;
    }
    
}
