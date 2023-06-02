/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafica;

import Classi.Nave;
import Classi.Punto;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author mampreso.ivan
 */
public class NaveGrafica extends Nave{
    private BufferedImage[] immagini;
    private int x,y,width,height;
    private int dimensioneTile;
    public NaveGrafica(int x, int y,BufferedImage[] immagini,int i,int blocchi,int dimensioneTile) {
        super(i);
        this.immagini=immagini;
        this.x=x;
        this.y=y;
        this.setWidth();
        this.setHeight();
        this.dimensioneTile=dimensioneTile;
    }
    //per crearla, inizializzarla e in seguito aggiornare x, y, e orizzontale
    public NaveGrafica(BufferedImage[] immagini,int i,int dimensioneTile){
        super(i);
        this.immagini=immagini;
        this.setX(-1);
        this.setY(-1);
        this.dimensioneTile=dimensioneTile;
    }
    public NaveGrafica(BufferedImage[] immagini,Nave nave,int xGriglia,int yGriglia,int dimensioneTile){
        super(nave);
        this.immagini=immagini;
        //inverto x e y della partenza perchè la x corrisponde alle righe (movimento in verticale nella matrice)
        //mentre y corrisponde alle colonne (movimento orizzontale nella matrice)
        this.setX(nave.getPartenza().getColonna()*dimensioneTile+xGriglia);
        this.setY(nave.getPartenza().getRiga()*dimensioneTile+yGriglia);
        this.dimensioneTile=dimensioneTile;
        this.setWidth();
        this.setHeight();
    }
    //aggiorna la partenza di una nave
    public void aggiornaNave(Punto partenza){
        super.aggiorna(partenza);
    }
    public Nave getNave(){
        return new Nave(this);
    }
    public void setWidth(){
        if(super.getOrizzontale()){
            width=(immagini[0].getWidth()/30)*dimensioneTile;
        }
        else{
            width=(immagini[2].getWidth()/30)*dimensioneTile;
        }
    }
    public void setHeight(){
        if(super.getOrizzontale()){
            height=(immagini[0].getHeight()/30)*dimensioneTile;
        }
        else{
            height=(immagini[2].getHeight()/30)*dimensioneTile;
        }
    }
    
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    
    //disegna la nave se esiste il punto
    public void aggiornaGui(Graphics g) {
        if(x!=-1&&y!=-1){
            if(super.getOrizzontale()){
                if(super.getAffondato())
                    g.drawImage(immagini[1], x, y, width,height, null);
                else
                    g.drawImage(immagini[0], x, y, width,height, null);
            }
                
            
            else{
                if(super.getAffondato())
                    g.drawImage(immagini[3], x, y,width,height, null);
                else
                    g.drawImage(immagini[2], x, y,width,height, null);
            }
                
        }
        
    } 
    
}
