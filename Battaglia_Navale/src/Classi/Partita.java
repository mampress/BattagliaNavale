/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classi;

import Grafica.Assets;
import Grafica.PartitaGrafica;
import StatiDiGioco.*;
import StatiDiGioco.StateManager;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;



/**
 *
 * @author mampreso.ivan
 */
public class Partita implements Runnable{
    private Giocatore giocatore;
    private Giocatore pc;
    private int[] navi;
    private int dimensione;
    private String nomePartita;
    //thread
    private Thread thread;
    private boolean run;
    //GRAFICA
    private PartitaGrafica pg; //form
    private Graphics g;
    private BufferStrategy bs;
    //INPUT
    private MouseManager mouse; 
    //STATI DI GIOCO
    private StateManager stateManager; //gestisce gli stati di gioco
    private Stato menu;
    private Stato inserimento; 
    private Stato battaglia;
    private Stato carica;
    private Stato impostazioni;
    private Stato storico;
    
    public Partita(){
        this.run=false;//quando la crea non è in run
        stateManager=new StateManager(); //crea lo state manager che gestirà i vari stati di gioco
        mouse=new MouseManager();
    }
    
    
    private void setUp(){
        //creo il formo
        pg=new PartitaGrafica();
        //aggiungo gli input del mouse al form e al canvas
        pg.getFrame().addMouseListener(mouse);
        pg.getFrame().addMouseMotionListener(mouse);
        pg.getCanvas().addMouseListener(mouse);
        pg.getCanvas().addMouseMotionListener(mouse);
        //inizializzo le immagini
        Assets.inizializza();
        //inizializzazione della dimensione delle griglie e di quante navi mettere
        dimensione=10;
        navi=new int[5];
        navi[0]=1;
        navi[1]=1;
        navi[2]=1;
        navi[3]=2;
        navi[4]=2;
        //inizializzo gli stati
        menu=new MenuPrincipale(this);
        inserimento=new Inserimento(this);
        battaglia=new Battaglia(this);
        carica=new Carica(this);
        impostazioni=new Impostazioni(this);
        storico=new Storico(this);
        stateManager.setStatoCorrente(menu);
        menu.setUp();
    }
    
   
    private void aggiornaGui(){
        bs=pg.getCanvas().getBufferStrategy(); //prendo il canvas con i suoi buffer
        if(bs==null){
            pg.getCanvas().createBufferStrategy(3);//se non esiste lo creo
            return;
        }
        g=bs.getDrawGraphics();
        g.clearRect(0,0,800,700); //cancello tutto
        if(stateManager.getStatoCorrente()!=null&&g!=null){
            stateManager.aggiornaGui(g); //stampo quello che lo stato corrente deve stampare
        }
        bs.show();//mostro quello che ho stampato
        g.dispose();
    }
    
    @Override
    public void run() {
        this.setUp();
        int fps=60;
        double timePerTick=1000000000/fps;//serve a far andare il gioco a 60 fps su tutti i computer, misuro il tempo in nanosecondi
        double delta=0;
        long now;
        long lastTime=System.nanoTime();
        long timer=0;
        int ticks=0;
        while(run){
            now=System.nanoTime();
            delta+=(now-lastTime)/timePerTick; //(now-lastTime) mi dice quanto tempo è passato dall'ultima volta che il ciclo ha girato
            //aggiungo a delta quanto tempo ho per chiamare poi gli altri due metodi
            timer+=now-lastTime;
            lastTime=now;
            if(delta>=1){
                this.aggiornaGui();
                delta--;
                ticks++;
            }
            
            //contatore fps
            if(timer>=1000000000){//se è passato un secondo  
                //stampo fps e riporto a 0 i contatori
                //System.out.println("fps: "+ticks);
                timer=0;
                ticks=0;
            }
        }
        this.stop();
    }
    public  void start(){
        if(this.run){//in caso sia già in run non deve creare un altro thread
            return;
        }
        this.run=true;
        thread=new Thread(this);
        thread.start();
    }
    public  void stop(){
        if(!run){return;}//in caso non sia in run non deve stoppare nulla
        this.run=false;
        try{
            thread.join();
        }catch(Exception e){
            System.exit(1);
        }
    }
    public StateManager getStateManager(){
        return stateManager;
    }
    public Stato getMenu() {
        return menu;
    }

    public Stato getInserimento() {
        return inserimento;
    }
    public Stato getBattaglia(){
        return battaglia;
    }
    public Stato getCarica(){
        return carica;
    }
    public Stato getImpostazioni(){
        return impostazioni;
    }
    public Stato getStorico(){
        return storico;
    }
    public void setDimensione(int dimensione){
        this.dimensione=dimensione;
    }

    public MouseManager getMouse() {
        return mouse;
    }
    
    
    public PartitaGrafica getPg(){
        return pg;
    }
    public int getDimensione(){
        return dimensione;
    }
    public int[] getNavi(){
        int [] ret=new int[navi.length];
        System.arraycopy(navi, 0, ret, 0, navi.length);
        return ret;
    }
    public void setGiocatore(Giocatore giocatore){
        this.giocatore=new Giocatore(giocatore);
    }
    public Giocatore getGiocatore(){
        return new Giocatore(giocatore);
    }
    public void setPc(Pc pc){
        this.pc=new Pc(pc);
    }
    public Pc getPc(){
        return new Pc((Pc) pc);
    }
    public String getNomePartita(){
        return nomePartita;
    }
    public void setNomePartita(String nomePartita){
        this.nomePartita=nomePartita;
    }
    public void setNavi(int[] navi){
        this.navi=new int[navi.length];
        System.arraycopy(navi, 0, this.navi, 0, navi.length);
    }

    public Graphics getG(){
        return g;
    }
}
