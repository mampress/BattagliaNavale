/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classi;

/**
 *
 * @author ivan.mampreso
 */
public class Nave {
    private NomeNave nomeNave;
    private int blocchi;
    private Punto partenza;
    boolean orizzontale;
    private int colpito;
    private boolean affondato;
    
    public Nave(int blocchi, Punto partenza, boolean orizzontale,int i){
        this.setBlocchi(blocchi);
        this.setNomeNave(i);
        this.setPartenza(partenza);
        this.setOrizzontale(orizzontale);
        this.setColpito(0);
        this.setAffondato(false);
    }
    //per clonare
    public Nave(Nave nave){
        this.setBlocchi(nave.getBlocchi());
        this.setNomeNave(nave.getNomeNave());
        this.setPartenza(nave.getPartenza());
        this.setOrizzontale(nave.getOrizzontale());
        this.setColpito(nave.getColpito());
        this.setAffondato(nave.getAffondato());
    }
    //per creare una nave che poi viene aggiornata
    public Nave(int i){
        this.setBlocchi(this.getBlocchiIndice(i));
        this.setNomeNave(i);
        this.setColpito(0);
        this.setAffondato(false);
        this.setOrizzontale(true);   
    }
    //per randomizzare
    public Nave(int i,Punto partenza,boolean orizzontale){
        this.setBlocchi(this.getBlocchiIndice(i));
        this.setNomeNave(i);
        this.setColpito(0);
        this.setAffondato(false);
        this.setOrizzontale(orizzontale);
        this.setPartenza(partenza);
    }
    //per clonare una nave ma con una partenza diversa
    public Nave(Nave nave,Punto partenza){
        this.setPartenza(partenza);
        this.setBlocchi(nave.getBlocchi());
        this.setNomeNave(nave.getNomeNave());
        this.setOrizzontale(nave.getOrizzontale());
        this.setColpito(nave.getColpito());
        this.setAffondato(nave.getAffondato());
    }
    //per creare una nave partendo dai dati del file
    public Nave(int blocchi, Punto partenza, NomeNave nomeNave, boolean affondato, int colpito, boolean orizzontale){
        this.setPartenza(partenza);
        this.setBlocchi(blocchi);
        this.setNomeNave(nomeNave);
        this.setOrizzontale(orizzontale);
        this.setColpito(colpito);
        this.setAffondato(affondato);
    }
    //mi dice i blocchi in base all'indice del vettore che mi dice quanti nave inserire per tipo
    private int getBlocchiIndice(int i){
        int blocchi=0;
        switch(i){
            case 0://portaerei
                blocchi=5;
                break;
            case 1://corazzata
                blocchi=4;
                break;
            case 2://incrociatore
                blocchi=3;
                break;
            case 3://cacciatorpediniere
                blocchi=2;
                break;
            case 4://sottomarino
                blocchi=3;
                break;
        }
        return blocchi;
    }
    //in base al nome mi dice qual'è l'indice del vettore che mi dice quante navi inserire a cui appartiene questa nave
    public int getIndice(){
        return NomeNave.valueOf(nomeNave.toString()).ordinal();
    }
    private void setNomeNave(NomeNave nome){
        this.nomeNave=nome;
    }
    public void aggiorna(Punto partenza){
        this.setPartenza(partenza); 
    }
    private void setBlocchi(int blocchi){
        this.blocchi=blocchi;
    }
    private void setPartenza(Punto partenza){
        this.partenza=new Punto(partenza);
    }
    private void setOrizzontale(boolean orizzontale){
        this.orizzontale=orizzontale;
    }
    public void setAffondato(boolean affondato){
        this.affondato=affondato;
    }
    private void setNomeNave(int i){
        this.nomeNave=NomeNave.values()[i];
    }
    private void setColpito(int colpito){
        this.colpito=colpito;
    }
    public int getBlocchi(){
        return blocchi;
    }
    public Punto getPartenza(){
        Punto p=new Punto(this.partenza);
        return p;
    }
    public int getColpito(){
        return colpito;
    }
    public boolean getOrizzontale(){
        return orizzontale;
    }
    public boolean getAffondato(){
        return affondato;
    }
    public NomeNave getNomeNave(){
        return nomeNave;
    }
    public void setOpposto(){ //se verticale mette orizzontale e viceversa
        if(orizzontale)
            this.setOrizzontale(false);
        else
            this.setOrizzontale(true);
    }
    public int getValore(){//mi dice il valore da mettere nella griglia
        if(nomeNave==NomeNave.SOTTOMARINO){
            return 1;
        }else{
            return this.getBlocchi();
        }
    }
    //mi dice se un punto appartiene ad una nave
    public boolean appartiene(Punto p){
        boolean appartiene=false;
        if(orizzontale){
            if(p.getRiga()==partenza.getRiga()&&p.getColonna()>=partenza.getColonna()&&p.getColonna()<partenza.getColonna()+blocchi)
                appartiene=true;
        }
        else{
            if(p.getColonna()==partenza.getColonna()&&p.getRiga()>=partenza.getRiga()&&p.getRiga()<partenza.getRiga()+blocchi)
                appartiene=true;
        }
        return appartiene;
    }
    public void colpisci(){
        colpito++;
    }
    //confronta due navi (no affondato, no colpito)
    public boolean equals(Nave nave){
        return (nomeNave==nave.getNomeNave()
                &&partenza.equals(nave.getPartenza())&&blocchi==nave.getBlocchi()&&orizzontale==nave.getOrizzontale());
    }
    @Override
    public String toString(){
        return blocchi+" "+partenza.getRiga()+" "+partenza.getColonna()+" "+nomeNave+" "+affondato+" "+colpito+" "+orizzontale;
    }
}
