
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classi;

import java.util.*;
//commentare il tentativo
/**
 *
 * @author mampreso.ivan
 */
public class Pc extends Giocatore{
    private Punto primoColpito;
    private Mossa mossaPrecedente;
    public Pc(int dimensione,List<Nave> navi){
        super("Computer",dimensione,navi);
        primoColpito=new Punto(-1,-1);//punto non valido
        mossaPrecedente=null;
    }
    //randomizzare
    public Pc(int dimensione,int navi[]){
        super("Computer",dimensione,navi);
        primoColpito=new Punto(-1,-1);//punto non valido
        mossaPrecedente=null;
    }
    //clonare
    public Pc(Pc pc){
        super("Computer",pc.getDimensione(),pc.getNavi());
        primoColpito=new Punto(-1,-1);//punto non valido
        mossaPrecedente=null;
    }
    public Pc(int dimensione,int[][] gAtt,int [][]gDef,List<Nave> navi,Punto primoColpito,Mossa mossaPrecedente){
        super("Computer",dimensione,gAtt,gDef,navi);
        this.setPrimoColpito(primoColpito);
        if(mossaPrecedente==null){
            this.mossaPrecedente=null;
        }
        else{
            this.mossaPrecedente=new Mossa(mossaPrecedente);
        }
    }
    public void setPrimoColpito(Punto p){
        primoColpito=new Punto(p);
    }
    public Punto tentativo(){
        Punto tentativo;
        Mossa mossa;
        if(primoColpito.getRiga()!=-1){
            if(mossaPrecedente==null){//colpito per primo una volta fa, randomizzo una posizione 
                mossa=mossaRandom(primoColpito);
            }
            else{
                if(mossaPrecedente.getColpito()){
                    //continuo nella stessa direzione
                    Punto p=mossaPrecedente.getPunto().puntoDirezionato(mossaPrecedente.getDirezione());
                    if(!gAtt.isColpibile(p))//se non è possibile vado in direzione opposta
                        p=primoColpito.puntoDirezionato(mossaPrecedente.getDirezione().direzioneOpposta());
                    mossa=new Mossa(p,p.getDirezione(mossaPrecedente.getPunto()));
                }
                else{
                    //prima non ho colpito, vado nella direzione opposta rispetto a prima, partendo dal punto iniziale
                    Punto p=primoColpito.puntoDirezionato(mossaPrecedente.getDirezione().direzioneOpposta());
                    if(!gAtt.isColpibile(p)){//se non possibile cerco di andare in un'altra direzione di asse opposto
                        p=primoColpito.puntoDirezionato(p.getDirezione(primoColpito).direzioneAsse());
                        if(!gAtt.isColpibile(p)){//altrimenti prendo l'ultima direzione possibile, che sicuramente è colpibile, "randomizzo" un mossa che in realtà è l'unica direzione possibile
                            p=mossaRandom(primoColpito).getPunto();
                        } 
                    }
                    mossa=new Mossa(p,p.getDirezione(primoColpito));
                }
            }
            mossaPrecedente=new Mossa(mossa);
            tentativo=mossa.getPunto();
        }
        else{
            //randomizzo una mossa finchè non è valida
            tentativo=new Punto(dimensione);
            while(!gAtt.isColpibile(tentativo)){
                tentativo=new Punto(dimensione);
            }  
        }
        if(gAtt.isDentro(tentativo)){//non dovrebbe servire il controllo, ma in caso di problemi non tenta
            super.tentativo(tentativo);
        }
        //nascondiCircondato();
        return tentativo;
    }
    public Mossa mossaRandom(Punto p){//randomizza un punto in una direazione possibile
        Random r=new Random();
        Direzione d=Direzione.values()[r.nextInt(4)];
        while(!gAtt.isColpibile(p.puntoDirezionato(d))){
            d=Direzione.values()[r.nextInt(4)];
        }
        return (new Mossa(p.puntoDirezionato(d),d));
    }
    public void setColpitoMossa(boolean colpito){
        if(mossaPrecedente!=null){
            mossaPrecedente.setColpito(colpito);
        }
    }
    public Punto getPrimoColpito(){
        return new Punto(primoColpito);
    }
    public void annullaMossaPrecedente(){
        mossaPrecedente=null;
    }
    public Mossa getMossa(){
        if(mossaPrecedente==null){
            return null;
        }
        else{
            return new Mossa(mossaPrecedente);
        }
    }
    public void nascondiCircondato(){
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++){
                if(gAtt.circondato(new Punto(i,j))){
                    gAtt.tentativo(new Punto(i,j));
                }
            }
        }
    }
    public void nascondi(Nave nave){//va a mettere tutto intorno la nave -3 cosicchè il pc non faccia tentativi inutili
        if(nave.getOrizzontale()){
            nascondiRiga(new Nave(nave,new Punto(nave.getPartenza().getRiga()-1,nave.getPartenza().getColonna())));
            nascondiRiga(new Nave(nave,new Punto(nave.getPartenza().getRiga()+1,nave.getPartenza().getColonna())));
            nascondiPunto(new Punto(nave.getPartenza().getRiga(),nave.getPartenza().getColonna()-1));
            nascondiPunto(new Punto(nave.getPartenza().getRiga(),nave.getPartenza().getColonna()+nave.getBlocchi()));
        }
        else{
            nascondiColonna(new Nave(nave,new Punto(nave.getPartenza().getRiga(),nave.getPartenza().getColonna()-1)));
            nascondiColonna(new Nave(nave,new Punto(nave.getPartenza().getRiga(),nave.getPartenza().getColonna()+1)));
            nascondiPunto(new Punto(nave.getPartenza().getRiga()-1,nave.getPartenza().getColonna()));
            nascondiPunto(new Punto(nave.getPartenza().getRiga()+nave.getBlocchi(),nave.getPartenza().getColonna()));
        }
    }
    public void nascondiRiga(Nave nave){
        if(gAtt.isDentro(nave))
            for(int j=nave.getPartenza().getColonna();j<nave.getPartenza().getColonna()+nave.getBlocchi();j++)
                super.tentativo(new Punto(nave.getPartenza().getRiga(),j));
    }
    public void nascondiColonna(Nave nave){
        if(gAtt.isDentro(nave))
            for(int i=nave.getPartenza().getRiga();i<nave.getPartenza().getRiga()+nave.getBlocchi();i++)
                super.tentativo(new Punto(i,nave.getPartenza().getColonna()));
    }
    public void nascondiPunto(Punto p){
        if(gAtt.isDentro(p)){
            super.tentativo(p);
        }
    }
}
