/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classi;

/**
 *
 * @author mampreso.ivan
 */
public class Mossa {
    private Punto punto;
    private Direzione direzione;
    private boolean colpito;
    public Mossa(Punto p,Direzione d){
        this.setPunto(p);
        this.setDirezione(d);
        this.setColpito(false);
    }
    //clona la mossa
    public Mossa(Mossa mossa){
        this.setPunto(mossa.getPunto());
        this.setDirezione(mossa.getDirezione());
        this.setColpito(mossa.getColpito());
    }
    public Mossa(Punto p, boolean colpito, Direzione d){
        this.setPunto(p);
        this.setDirezione(d);
        this.setColpito(colpito);
    }
    public Punto getPunto() {
        return new Punto(punto);
    }
    public void setColpito(boolean colpito){
        this.colpito=colpito;
    }
    public boolean getColpito(){
        return colpito;
    }
    public void setPunto(Punto punto) {
        this.punto = new Punto(punto);
    }

    public Direzione getDirezione() {
        return direzione;
    }

    public void setDirezione(Direzione direzione) {
        this.direzione = direzione;
    }
    
}
