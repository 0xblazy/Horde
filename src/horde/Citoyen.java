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
    
    private Case[][] carte;
    
    private String nom;
    private int pv,pa;
    
    private int x,y;
    private boolean enVille;
    
    private final int TAILLE_SAC = 10;
    private int nbPlanche, nbMetal, nbBoisson, nbGourde, nbRation;
    
    public Citoyen(String _nom, Case[][] _carte) {
        this.carte = _carte;
        
        this.nom = _nom;
        this.pv = 100;
        this.pa = 6;
        
        this.x = 12;
        this.y = 12;
        this.enVille = true;
    }
    
    public boolean deplacer(int _x, int _y) {
        if(this.enVille) {
            System.out.println(this.nom + " est en ville !");
            return false;
        } else if(this.pa == 0) {
            System.out.println(this.nom + " n'a plus de point d'action !");
            return false;
        } else if(((Exterieur) this.carte[this.y][this.x]).encoreZombies()) {
            System.out.println("Il reste des zombies !");
            return false;
        } else {
            System.out.println(this.nom + " se déplace de " + this.x + ","
                    + this.y + " à " + _x + "," + _y);
            
            this.x = _x;
            this.y = _y;
            
            this.pa--;
            
            return true;
        }
    }
    
    public boolean attaquer() {
        if(enVille) {
            System.out.println(this.nom + " en est ville !");
            return false;
        } else if (this.pa == 0) {
            System.out.println(this.nom + " n'a plus de point d'action !");
            return false;
        } else {
            if(((Exterieur) this.carte[this.y][this.x]).attaquerZombie()) {
                System.out.println(this.nom + " attaque un zombie");
                this.pa--;
                
                if(Math.random() * 100 < 10) {
                    System.out.println(this.nom + " est blessé !");
                    this.pv -= 10;
                }
                 return true;
            } else {
                System.out.println("Il n'y a pas de zombies sur cette case");
                return false;
            }
        }
    }
    
    public String toString() {
        String s = this.nom + "(" + this.pv + " PV, " + this.pa + "PA)";
        
        if(this.enVille) {
            s += " [En ville]";
        } else {
            s += " [" + this.x + "," + this.y + "]";
        }
        
        return s;
    }
    
}
