/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafica;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 *
 * @author mampreso.ivan
 */
//rappresenta un qualsiasi oggetto grafico con evento
public abstract class OggettoGui { //astratta perchè un oggetto gui è troppo generale, non interfaccia perchè le variabili non devono essere in comune
    protected int x,y,width,height;
    protected Rectangle zona;
    protected boolean sopra;
    public OggettoGui(int x,int y,int width,int height){
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setZona();
        sopra=false;
    }
    
    //metodi astratti
    
    public abstract void aggiornaGui(Graphics g);
    public abstract void leftClick();
    public abstract void rightClick();
    //altri metodi
    public void onMouseMove(MouseEvent e){
        sopra = zona.contains(e.getX(),e.getY());//mi dice se sono sopra a quest'oggetto grafico
    }
    public void onMouseRelease(MouseEvent e){
        if(sopra&&e.getButton()==MouseEvent.BUTTON1)
            this.leftClick();
        if(sopra&&e.getButton()==MouseEvent.BUTTON3)
            this.rightClick();
    }
    
    public void setZona(){
        this.zona=new Rectangle(x,y,width,height);
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isSopra() {
        return sopra;
    }

    
    
}
