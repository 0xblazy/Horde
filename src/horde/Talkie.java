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
public class Talkie {
    private String[][] carteInfo;
    private Case[][] carte;
    
    public Talkie(Case[][] _carte) {
        this.carte = _carte;
        
        this.carteInfo = new String[25][25];
        for (int j = 0 ; j < this.carteInfo.length ; j++) {
            for (int i = 0 ; i < this.carteInfo[0].length ; i++) {
                if (j == 12 && i == 12) {
                    this.carteInfo[j][i] = "VILLE ";
                } else {
                    this.carteInfo[j][i] = "######";
                }
            }
        }
    }
    
    public boolean miseAJour(int _x, int _y) {
        if (((Exterieur)this.carte[_y][_x]).isFouiller()) {
            this.carteInfo[_y][_x] = "" + 
                ((Exterieur)this.carte[_y][_x]).getNbPlanches() + "P" + 
                ((Exterieur)this.carte[_y][_x]).getNbMetal() + "M" +
                ((Exterieur)this.carte[_y][_x]).getNbBoissons() + "B";
            System.out.println("Le Talkie-Walkie est mis à jour");
            return true;
        } else {
            System.out.println("La case n'a pas été fouillée !");
            return false;
        }
    }
    
    public String toString() {
        String s = "";
        for (int j = -1 ; j < this.carteInfo.length ; j++) {
            for (int i = -1 ; i < this.carteInfo[0].length ; i++) {
                if (j == -1) {
                    if (i == -1) {
                        s += "   ";
                    } else if (i < 9) {
                        s += "     " + (i + 1) + "   ";
                    } else {
                        s += "    " + (i + 1) + "   ";
                    }
                } else if (i == -1) {
                    if (j < 9) {
                        s += " " + (j + 1) + " ";
                    } else {
                        s += "" + (j + 1) + " ";
                    }
                } else {
                    s += "| " + this.carteInfo[j][i] + " ";
                }
            }
            if (j == -1) {
                s += "\n";
            } else {
                s += "|\n"; 
            }
        }
        return s;
    }
}
