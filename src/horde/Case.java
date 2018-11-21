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
public class Case {
    
    /* Coordonnées de la Case */
    private int x,y;
    
    /* Constructeur */
    public Case(int _x, int _y) {
        this.x = _x;
        this.y = _y;
    }

    /* Retourne la coordonnée x */
    public int getX() {
        return x;
    }

    /* Retourne la coordonnée y */
    public int getY() {
        return y;
    }
}
