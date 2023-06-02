/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafica;

import java.awt.Color;
import java.awt.Graphics;


/**
 *
 * @author mampreso.ivan
 */
public class Tile{
    private int dimensione;
    private int x,y;
    private int val;
    public Tile(int x, int y,int dimensione){
        this.setX(x);
        this.setY(y);
        this.setDimensione(dimensione);
        this.setVal(0);//inizializzo ad acqua
    }
    private void setDimensione(int dimensione){
        this.dimensione=dimensione;
    }
    public int getX() {
        return x;
    }
    public void setVal(int val){
        this.val=val;
    }
    public int getVal(){
        return val;
    }
    private void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private void setY(int y) {
        this.y = y;
    }
    public void disegnaAcqua(Graphics g){
        g.drawImage(Assets.acqua,x, y,dimensione,dimensione,null);
        g.setColor(Color.white);
        g.drawRect(x, y, dimensione, dimensione); //bordo della tile
    }
    public void disegna(Graphics g){
        switch(val){
            case -1: //colpito
                g.drawImage(Assets.colpito,x, y,dimensione,dimensione,null);
                break;
            case -3: //tentativo
                g.drawImage(Assets.tentativo,x, y,dimensione,dimensione,null);
                break;
            default:
                break;
        }
    }
    public int getDimensione(){
        return dimensione;
    }
}
