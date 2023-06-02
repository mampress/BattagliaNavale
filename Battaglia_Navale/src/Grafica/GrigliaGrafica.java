/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafica;

import Classi.Nave;
import Classi.Punto;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 *
 * @author mampreso.ivan
 */
//rappresenta una griglia graficamente
public class GrigliaGrafica extends OggettoGui{
    private int dimensione;
    private Tile[][] tiles; //griglia effettiva di tiles
    private ClickListener click;//gestore di click
    private int dimensioneTile;
    public GrigliaGrafica(int dimensione,int x,int y,ClickListener click,int dimensioneTile){
        super(x,y,dimensioneTile*dimensione,dimensioneTile*dimensione);
        this.setDimensione(dimensione);
        this.setDimensioneTile(dimensioneTile);
        this.setTiles();
        this.click=click;
        
    }
    private void setDimensioneTile(int dimensioneTile){
        this.dimensioneTile=dimensioneTile;
    }
    public Rectangle getZona(){
        return zona;
    }
  
    @Override
    public void onMouseRelease(MouseEvent e){
        if(sopra&&e.getButton()==MouseEvent.BUTTON1)//se sono sopra la griglia e premo tasto sinistro
            this.leftClick();
        else if(sopra&&e.getButton()==MouseEvent.BUTTON3)//se sono sopra la griglia e premo tasto destro
            this.rightClick();
    }
    @Override
    public void leftClick(){
        click.leftClick();
    }
    @Override
    public void rightClick(){
        click.rightClick();
    }
    //imposta le tile dando loro anche una posizione (per ciascuna)
    public void setTiles(){
        tiles=new Tile[dimensione][dimensione];
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++){
                tiles[i][j]=new Tile(x+j*dimensioneTile,y+i*dimensioneTile,dimensioneTile);
            }
        }
    }
    //metodi separati perchè disegno griglia -> disegno navi -> disegno status
    public void disegnaGriglia(Graphics g){
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++){
                tiles[i][j].disegnaAcqua(g);
            }
        }
    }
    //cerchio=colpito, x=tentato
    public void disegnaStatus(Graphics g){
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++){
                tiles[i][j].disegna(g);
            }
        }
    }
    //mette -2 comi valore nelle tiles
    public void affonda(Nave nave){
        if(nave.getOrizzontale()){
            for(int i=nave.getPartenza().getRiga(),j=nave.getPartenza().getColonna();j<nave.getPartenza().getColonna()+nave.getBlocchi();j++){
                this.aggiornaVal(new Punto(i,j),-2);
            }
        }
        else{
            for(int i=nave.getPartenza().getRiga(),j=nave.getPartenza().getColonna();i<nave.getPartenza().getRiga()+nave.getBlocchi();i++){
                this.aggiornaVal(new Punto(i,j),-2);
            }
        }
    }
    
    
    public void setDimensione(int dimensione) {
        this.dimensione = dimensione;
    }
    //in base alla x passata restituisce in quale colonna dela griglia si trova, da richiamare solo se si è sopra la griglia
    public int getCurrentJ(int x){
        x-=this.x;
        return x/dimensioneTile;
    }
    //in base alla y passata restituisce in quale riga dela griglia si trova, da richiamare solo se si è sopra la griglia
    public int getCurrentI(int y){
        y-=this.y;
        return y/dimensioneTile;
    }
    //in base alla x passata restituisce la x della cella in cui ci si trova, da richiamare solo se si è sopra la griglia
    public int getCurrentX(int x){
        return this.getCurrentJ(x)*dimensioneTile+this.x;
    }
    //in base alla y passata restituisce la y della cella in cui ci si trova , da richiamare solo se si è sopra la griglia
    public int getCurrentY(int y){
        return this.getCurrentI(y)*dimensioneTile+this.y;
    }
    //mi dice se sono sopra alla griglia
    public boolean contains(int x,int y){
        return zona.contains(x,y);
    }

    

    @Override
    public void aggiornaGui(Graphics g) {
       this.disegnaGriglia(g);
    }
    //mi dice la x finale della griglia
    public int getFinalX(){
        return x+dimensioneTile*dimensione;
    }
    //mi dice la y finale della griglia
    public int getFinalY(){
        return y+dimensioneTile*dimensione;
    }
    //aggiorna il valore di una tile
    public void aggiornaVal(Punto p,int val){
        tiles[p.getRiga()][p.getColonna()].setVal(val);
    }
    public int getXPunto(Punto p){
        return tiles[p.getRiga()][p.getColonna()].getX();
    }
    public int getYPunto(Punto p){
        return tiles[p.getRiga()][p.getColonna()].getY();
    }
}
