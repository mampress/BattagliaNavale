/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classi;

import java.util.Random;

/**
 *
 * @author ivan.mampreso
 */
public class Punto {
    private int riga;
    private int colonna;
    public Punto(int riga,int colonna){
        this.setRiga(riga);
        this.setColonna(colonna);
    }
    //clona
    public Punto(Punto punto){
        this.setRiga(punto.getRiga());
        this.setColonna(punto.getColonna());
    }
    public Punto(int dimensione){//randomizza riga e colonna
        int x,y;
        Random r=new Random();
        x=r.nextInt(dimensione);
        y=r.nextInt(dimensione);
        this.setRiga(x);
        this.setColonna(y);
    }
    public void setRiga(int riga){
        this.riga=riga;
    }
    public void setColonna(int colonna){
        this.colonna=colonna;
    }
    public int getRiga(){
        return riga;
    }
    public int getColonna(){
        return colonna;
    }
    public char getLettera(){
        char a=(char)('A'+colonna);
        return a;
    }
    public int getNumero(){
        return riga+1;
    }
    public boolean equals(Punto p){
        return (riga==p.getRiga()&&colonna==p.getColonna());
    }
    @Override
    public String toString(){
        return "riga: "+riga+" colonna: "+colonna;
    }
    //la direzione deve essere possibile
    public Punto puntoDirezionato(Direzione d){
        Punto p=null;
        switch(d){
            case SU:
                p=new Punto(riga-1,colonna);
                break;
            case GIU:
                p=new Punto(riga+1,colonna);
                break;
            case DESTRA:
                p=new Punto(riga,colonna+1);
                break;
            case SINISTRA:
                p=new Punto(riga,colonna-1);
                break;
        }
        return p;
    }
    //mi dice la direzione di questo punto ripetto a quello passato
    public Direzione getDirezione(Punto p){
        Direzione d;
        if(p.getRiga()==riga&&p.getColonna()<colonna)
            d=Direzione.DESTRA;
        else if(p.getRiga()==riga&&p.getColonna()>colonna)
            d=Direzione.SINISTRA;
        else if(p.getRiga()<riga&&p.getColonna()==colonna)
            d=Direzione.GIU;
        else
            d=Direzione.SU;
        return d;
    }
}
