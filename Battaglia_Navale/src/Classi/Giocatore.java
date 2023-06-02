/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classi;

import java.util.*;

/**
 *
 * @author ivan.mampreso
 */
public class Giocatore {//permette la gestione di un giocatore
    protected String nome;
    protected Griglia gAtt;
    protected Griglia gDef;
    protected List<Nave> navi;
    protected int dimensione;
    public Giocatore(String nome,int dimensione,List<Nave> navi){
        this.setNome(nome);
        this.setDimensione(dimensione);
        this.setGriglie(dimensione);
        this.setNavi(navi);
        this.posizionaNavi();
    }
    //costruttore clonatore
    public Giocatore(Giocatore giocatore){
        this.setNome(giocatore.getNome());
        this.setDimensione(giocatore.getDimensione());
        this.setGriglie(giocatore.getDimensione(),giocatore.getGrigliaAtt(),giocatore.getGrigliaDef());
        this.setNavi(giocatore.getNavi());
        this.posizionaNavi();
    }
    //per randomizzare il posizionamento delle navi
    public Giocatore(String nome,int dimensione,int navi[]){
        this.setNome(nome);
        this.setDimensione(dimensione);
        this.setGriglie(dimensione);
        this.setNavi(gDef.randomizzaPosizionamento(0, 0, navi));
        this.posizionaNavi();
    }
    //per il caricamento da file
    public Giocatore(String nome,int dimensione,int gAtt[][],int gDef[][],List<Nave> navi){
        this.setNome(nome);
        this.setDimensione(dimensione);
        this.setGriglie(dimensione,gAtt,gDef);
        this.setNavi(navi);
        this.posizionaNavi();
    }
    public int[][] getGrigliaAtt(){
        int [][]copia=new int[gAtt.getDimensione()][gAtt.getDimensione()];
        for(int i=0;i<gAtt.getDimensione();i++){
            for(int j=0;j<gAtt.getDimensione();j++){
                copia[i][j]=gAtt.getValore(new Punto(i,j));
            }
        }
        return copia;
    }
    public int[][] getGrigliaDef(){
        int [][]copia=new int[gDef.getDimensione()][gDef.getDimensione()];
        for(int i=0;i<gDef.getDimensione();i++){
            for(int j=0;j<gDef.getDimensione();j++){
                copia[i][j]=gDef.getValore(new Punto(i,j));
            }
        }
        return copia;
    }
    private void setDimensione(int dimensione){
        this.dimensione=dimensione;
    }
    public int getDimensione(){
        return dimensione;
    }
    private void setNome(String nome){
        this.nome=nome;
    }
    public  String getNome(){
        return nome;
    }
    //impostazione griglia e navi
    private void setGriglie(int dimensione, int [][] gAtt, int[][]gDef){
        this.gAtt=new Griglia(dimensione,gAtt);
        this.gDef=new Griglia(dimensione,gDef);
    }
    private void setGriglie(int dimensione){
        this.gAtt=new Griglia(dimensione);
        this.gDef=new Griglia(dimensione);
    }
    private void setNavi(List<Nave> navi){
        this.navi=new ArrayList<>();
        for(Nave nave:navi){
            this.navi.add(new Nave(nave));
        }
    }
    public List<Nave> getNavi(){
        List<Nave> navi=new ArrayList<>();
        for(Nave nave:this.navi){
            navi.add(new Nave(nave));
        }
        return navi;
    }
    public void posizionaNavi(){
        for(Nave nave:this.navi){
            gDef.posiziona(nave);
        }
    }
    //mi dice se un punto sulla griglia d'attacco è occupato o meno
    public boolean puntoOccupato(Punto p){
        return gAtt.isOccupato(p);
    }
    //colpisce difesa
    public void colpisciDifesa(Punto p){
        gDef.colpisci(p);
    }
    
    public void colpisciAttacco(Punto p){
        gAtt.colpisci(p);
    }
    public void tentativo(Punto p){
        gAtt.tentativo(p);
    }
    //cerca una nave nella lista delle navi controllando che il punto abbia lo stesso valore della nave e che appartenga a quella nave
    private int trovaNave(int valore, Punto p){
        int trovata=-1;
        
        for(int i=0;i<navi.size()&&trovata==-1;i++){
            if(navi.get(i).getValore()==valore&&navi.get(i).appartiene(p)){
                trovata=i;
            }
        }
        return trovata;
    }
    //aumenta il contatore di quante volte una nave è stata colpita di 1, e se sono stati colpiti tutti i blocchi affonda quella nave
    public int colpisciNave(int valore,Punto p){     
        int iNave=this.trovaNave(valore,p);
        if(iNave!=-1){
            navi.get(iNave).colpisci();
            if(navi.get(iNave).getBlocchi()==navi.get(iNave).getColpito())
                navi.get(iNave).setAffondato(true);
        }
        return iNave;
    }
    //richiamato quando una nave viene colpita, cerca la nave e la colpisce, colpisce nella griglia di difesa e se affondata la affonda anche nella griglia di difesa 
    public Nave aggiorna(int valore,Punto p){
        int iNave;
        iNave=this.colpisciNave(valore,p);
        this.colpisciDifesa(p);
        if(iNave!=-1){
            if(navi.get(iNave).getAffondato()){
                gDef.affonda(navi.get(iNave));
            }
            return new Nave(navi.get(iNave));
        }
        return null;
    }
    public void affondaAttacco(Nave nave){
        gAtt.affonda(nave);
    }
    
    //dice il valore presente nella griglia di difesa e poi lo sostituisce con -3, ovvero facendo un tentativo
    public int getValoreETenta(Punto p){
        int val=gDef.getValore(p);
        gDef.tentativo(p);
        return val;
    }
    //se difesa true mi dice il valore in quel punto della griglia di difesa altrimenti di quella d'attacco
    public int getValore(Punto p, boolean difesa){
        if(difesa)
            return gDef.getValore(p);
        else
            return gAtt.getValore(p);
    }
    //controlla che tutte le navi siano affondate
    public boolean tutteAffondate(){
        boolean affondato=true;
        for(int i=0;i<navi.size()&&affondato;i++){
            affondato=navi.get(i).getAffondato();
        }
        return affondato;
    }
    
    public int getDimNavi(){
        return navi.size();
    }
    public String griglieToString(){
        String s=gDef.toString();
        s+=gAtt.toString();
        return s;
    }
}
