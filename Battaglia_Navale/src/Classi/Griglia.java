/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ivan.mampreso
 */
public class Griglia {
    /*
    i valori possibili sono:
    0 = acqua
    1 = sottomarino
    2 = cacciatorpediniere
    3 = incrociatore
    4 = corazzata
    5 = portaerei
    -1 = colpito
    -2 = affondato
    -3 = tentativo
    */
    public int[][]griglia;
    public int dimensione;
    public Griglia(int dimensione){
        this.setDimensione(dimensione);
        this.setGriglia(dimensione);
        this.inizializza();  
    }
    public Griglia(int dimensione, int griglia[][]){
        this.setDimensione(dimensione);
        this.setGriglia(griglia);
    }
    private void setGriglia(int [][] griglia){
        this.griglia=new int[dimensione][dimensione];
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++){
                this.griglia[i][j]=griglia[i][j];
            }
        }
    }
    private void setDimensione(int dimensione){
        this.dimensione=dimensione;
    }
    private void setGriglia(int dimensione){
        this.griglia=new int[dimensione][dimensione];
    }
    //inizializza la griglia a 0 (acqua)
    private void inizializza(){
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++){
                griglia[i][j]=0;
            }
        }
    }
    public int getDimensione(){
        return dimensione;
    }
    public int[][] getGriglia(){
        int[][] clone=new int[dimensione][dimensione];
        for(int i=0;i<dimensione;i++){
            System.arraycopy(griglia[i], 0, clone[i], 0, dimensione);
        }
        return clone;
    }
    //mette -2 dove è presente la nave passata come parametro (la nave deve essere all'interno della griglia)
    public void affonda(Nave nave){
        if(nave.getOrizzontale()){
            for(int j=nave.getPartenza().getColonna();j<nave.getPartenza().getColonna()+nave.getBlocchi();j++){
                griglia[nave.getPartenza().getRiga()][j]=-2;
            }
        }
        else{
            for(int i=nave.getPartenza().getRiga();i<nave.getPartenza().getRiga()+nave.getBlocchi();i++){
                griglia[i][nave.getPartenza().getColonna()]=-2;
            }
        }
    }
    //controlla se un punto della griglia è occupato
    public boolean isOccupato(Punto p){
        return (this.isDentro(p)&&griglia[p.getRiga()][p.getColonna()]!=0);
    }
    //controlla se un punto della griglia è dentro
    public boolean isDentro(Punto p){
        return p.getRiga()>=0&&p.getRiga()<dimensione&&p.getColonna()>=0&&p.getColonna()<dimensione;
    }
    //controlla se una nave è posizionabile
    public boolean isPosizionabile(Nave nave){
        boolean pos=false;
        if(this.isDentro(nave)&&!this.tocca(nave))
            pos=true;
        return pos;
    }
    //controlla che una nave sia dentro
    public boolean isDentro(Nave nave){
        boolean dentro=false;
        if(nave.getOrizzontale()){
            if(this.isDentro(nave.getPartenza())&&nave.getPartenza().getColonna()+nave.getBlocchi()-1<dimensione)
                dentro=true;
        }
        else{
            if(this.isDentro(nave.getPartenza())&&nave.getPartenza().getRiga()+nave.getBlocchi()-1<dimensione)
                dentro=true;
        }
        return dentro;
    }
    
    /*controlla se una nave ne tocca un altra, ovvero se è dove ci sono le 'X' nell'esempio qui sotto (1 è una nave)
     XXX
    X111X
     XXX
    */
    public boolean tocca(Nave nave){
        boolean tocca=false;
        if(nave.getOrizzontale()){
            if(this.controlloRiga(nave.getPartenza(),nave.getBlocchi())||
                this.controlloRiga(new Punto(nave.getPartenza().getRiga()-1,nave.getPartenza().getColonna()),nave.getBlocchi())||
                this.controlloRiga(new Punto(nave.getPartenza().getRiga()+1,nave.getPartenza().getColonna()),nave.getBlocchi())||
                this.isOccupato(new Punto(nave.getPartenza().getRiga(),nave.getPartenza().getColonna()-1))||
                this.isOccupato(new Punto(nave.getPartenza().getRiga(),nave.getPartenza().getColonna()+nave.getBlocchi())))
                    tocca=true;  
        }else{
            if(this.controlloColonna(nave.getPartenza(),nave.getBlocchi())||
            this.controlloColonna(new Punto(nave.getPartenza().getRiga(),nave.getPartenza().getColonna()-1),nave.getBlocchi())||
            this.controlloColonna(new Punto(nave.getPartenza().getRiga(),nave.getPartenza().getColonna()+1),nave.getBlocchi())||
            this.isOccupato(new Punto(nave.getPartenza().getRiga()-1,nave.getPartenza().getColonna()))||
            this.isOccupato(new Punto(nave.getPartenza().getRiga()+nave.getBlocchi(),nave.getPartenza().getColonna())))
                tocca=true;
        }
        return tocca;
    }
    // controlla se una riga è occupata o meno
    private boolean controlloRiga(Punto partenza, int blocchi){
        if(partenza.getRiga()>=0&&partenza.getRiga()<dimensione){
            for(int j=partenza.getColonna();j<partenza.getColonna()+blocchi;j++){
                if(this.isOccupato(new Punto(partenza.getRiga(),j))){
                    return true;
                }
            }
        }
        return false;
    }
    //controlla se una colonna è occupata o meno
    private boolean controlloColonna(Punto partenza, int blocchi){
        if(partenza.getColonna()>=0&&partenza.getColonna()<dimensione){
                for(int i=partenza.getRiga();i<partenza.getRiga()+blocchi;i++){
                    if(this.isOccupato(new Punto(i,partenza.getColonna()))){
                        return true;
                    }
                }
            }

        return false;
    }
    //posiziona una nave andando a mettere il valore corrispondente alla nave nella griglia
    public void posiziona(Nave nave){
        if(nave.getOrizzontale()){
            for(int j=nave.getPartenza().getColonna();j<nave.getPartenza().getColonna()+nave.getBlocchi();j++){
                griglia[nave.getPartenza().getRiga()][j]=nave.getValore();
            }
        }       
        else{
            for(int i=nave.getPartenza().getRiga();i<nave.getPartenza().getRiga()+nave.getBlocchi();i++){
                griglia[i][nave.getPartenza().getColonna()]=nave.getValore();
            }
        }
    }
    
    //randomizza posizionamento partendo da una nave data (non per forza dall'inizio)
    public List<Nave> randomizzaPosizionamento(int iStart,int jStart, int[] navi){
        List<Nave> listaNavi=new ArrayList<>();
        int j=jStart;
        Nave nave;
        for(int i=iStart;i<navi.length;i++){
            for(;j<navi[i];j++){
                nave=this.randomizzaNave(i);
                while(!this.isPosizionabile(nave)){
                    nave=this.randomizzaNave(i);
                }
                this.posiziona(nave);
                listaNavi.add(new Nave(nave));
            }
            j=0;
        }
        return listaNavi;
    }
    private Nave randomizzaNave(int i){
        int x,y;
        boolean orizzontale;
        Random r=new Random();
        orizzontale=r.nextBoolean();
        x=r.nextInt(dimensione);
        y=r.nextInt(dimensione);
        Punto p=new Punto(x,y);
        Nave nave=new Nave(i,p,orizzontale);
        return nave;
    }
    //da utilizzare solo se le navi inserite sono di quella griglia, rimuove tutte le navi presenti nella lista navi
    public void rimuoviNavi(List<Nave> navi){
        for(Nave nave:navi){
            this.rimuoviNave(nave);
        }
    }
    //nei punti in cui c'è la nave (che sicuramente esiste) mette acqua (0)
    public void rimuoviNave(Nave nave){
        if(nave.getOrizzontale()){
            for(int j=nave.getPartenza().getColonna();j<nave.getPartenza().getColonna()+nave.getBlocchi();j++){
                griglia[nave.getPartenza().getRiga()][j]=0;
            }
        }       
        else{
            for(int i=nave.getPartenza().getRiga();i<nave.getPartenza().getRiga()+nave.getBlocchi();i++){
                griglia[i][nave.getPartenza().getColonna()]=0;
            }
        }
    }
    //mi dice il valore di un punto, che deve essere valido
    public int getValore(Punto p){
        return griglia[p.getRiga()][p.getColonna()];
    }
    //colpire equivale a mettere -1 in quel punto che deve essere valido
    public void colpisci(Punto p){
        griglia[p.getRiga()][p.getColonna()]=-1;
    }
    //fare un tentativo equivale a mettere -3 in quel punto che deve essere valido
    public void tentativo(Punto p){
        griglia[p.getRiga()][p.getColonna()]=-3;
    }
    //un punto è colpibile se è una nave(val>0) o acqua (val==0)
    public boolean isColpibile(Punto p){
        return (this.isDentro(p)&&!(griglia[p.getRiga()][p.getColonna()]<0));
    }
   
    @Override
    public String toString(){
        String s="";
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++){
                s+=griglia[i][j]+" ";
            }
            s+="\n";
        }
        return s;
    }
    //mi dice se un punto è circondato (ovvero se sono stati fatti tentativi in tutte le direzioni rispetto al punto
    public boolean circondato(Punto p){
        boolean circondato=false;
        Punto su,giu,destra,sinistra;
        su=p.puntoDirezionato(Direzione.SU);
        giu=p.puntoDirezionato(Direzione.GIU);
        destra=p.puntoDirezionato(Direzione.DESTRA);
        sinistra=p.puntoDirezionato(Direzione.SINISTRA);
        if(!this.isColpibile(su)&&!this.isColpibile(giu)&&!this.isColpibile(destra)&&!this.isColpibile(sinistra)){
            circondato=true;
        }
        return circondato;
    }
}
