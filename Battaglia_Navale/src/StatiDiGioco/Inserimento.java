/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatiDiGioco;


import Classi.Giocatore;
import Classi.Griglia;
import Classi.Nave;
import Classi.NomeNave;
import Classi.Partita;
import Classi.Pc;
import Classi.Punto;

import Grafica.Assets;
import Grafica.BottoneTesto;
import Grafica.ClickListener;
import Grafica.GrigliaGrafica;
import Grafica.GuiManager;
import Grafica.NaveGrafica;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author mampreso.ivan 
 */
public class Inserimento implements Stato{
    private Griglia griglia;
    private GrigliaGrafica grigliaG;
    private final int startX=40,startY=190; //coordinate del vertice alto sinistra in cui può stare la griglia
    private Partita partita;
    private GuiManager guiManager; //gestisce bottoni e navi grafiche
    private List<Nave> navi,naviRandom;//navi scelte da me e randomizzate non grafiche
    private int iCorrente,jCorrente,nextI,iVecchio,jVecchio;
    private String nomeGiocatore;
    private List<NaveGrafica> naviG,naviGRandom;//navi grafiche messe da me e quelle randomizzate
    private NaveGrafica naveCorrente;
    private Punto puntoCorrente;

    public Inserimento(Partita partita){
        this.partita=partita;
    }
    
    @Override
    public void aggiornaGui(Graphics g) {
        //stampa in ordine: sfondo, griglia, bottoni, navi inserite manualmente,navi randomizzate e outline per vedere dove posizionare la nave
        g.drawImage(Assets.bgInserimento,0,0,null);
        grigliaG.disegnaGriglia(g);
        guiManager.aggiornaGui(g);
        this.scriviNomeNavi(g);
        for(NaveGrafica n:naviG){
            n.aggiornaGui(g);
        }
        for(NaveGrafica n:naviGRandom){
            n.aggiornaGui(g);
        }
        this.disegnaOutline(g);//bordo delle navi
    }
    @Override
    public void setUp(){
        //partita.getPg().getFrame().getTxtNome().setVisible(true);
        //indici
        iCorrente=0;
        jCorrente=0;
        nextI();//calcolo l'indice successivo
        //partita
        partita.getPg().getFrame().getPnlTesto().setVisible(true);
        this.guiManager=new GuiManager(partita);
        partita.getMouse().setGuiManager(guiManager);
        //navi
        navi=new ArrayList<>();
        naviG=new ArrayList<>();
        naviRandom=new ArrayList<>();
        naviGRandom=new ArrayList<>();
        naveCorrente=new NaveGrafica(Assets.getImage(iCorrente),iCorrente,30);
        puntoCorrente=new Punto(0,0);
        //bottoni e griglia
        griglia=new Griglia(partita.getDimensione());
        guiManager.addOggGui(new BottoneTesto(576,49,81,42,Assets.btnAuto,new ClickListener(){
            public  void leftClick(){
                if(iVecchio<5&&jVecchio<partita.getNavi()[iVecchio]){//se ci sono navi da posizionare
                    griglia.rimuoviNavi(naviRandom);//rimuovo tutte le navi che avevo randomzzato prima dalla griglia
                    naviRandom=griglia.randomizzaPosizionamento(iVecchio,jVecchio,partita.getNavi());
                    //porto gli indici a numeri che non mi permettono di posizionare navi manualmente
                    iCorrente=5;
                    jCorrente=partita.getNavi()[4];//numero di barche di quel tipo da posizionare
                    naviGRandom=new ArrayList<>();
                    //creo le nuovi navi grafiche dalle navi appena randomizzate
                    
                    for(Nave n:naviRandom){
                        NaveGrafica nG=new NaveGrafica(Assets.getImage(n.getIndice()),n,grigliaG.getX(),grigliaG.getY(),30);
                        naviGRandom.add(nG);
                    }
                }
            }
            public void rightClick(){}
        }));
        guiManager.addOggGui(new BottoneTesto(576,660,71,127,Assets.btnEsciB,new ClickListener(){
            public void leftClick(){
                partita.getMouse().setGuiManager(null);//cambio stato, il mouse dovrà lavorare solo sul nuovo stato in cui sarò
                partita.getMenu().setUp();
                partita.getPg().getFrame().getPnlTesto().setVisible(false);
                partita.getPg().getFrame().getTxtNome().setText("");
                partita.getStateManager().setStatoCorrente(partita.getMenu());
            }
            public void rightClick(){}
        }));
        guiManager.addOggGui(new BottoneTesto(579,624,121,42,Assets.btnPronto,new ClickListener(){
            public void leftClick(){
                if(iCorrente>=5){
                    nomeGiocatore=partita.getPg().getFrame().getTxtNome().getText();
                    if(nomeGiocatore.length()>21||nomeGiocatore.isBlank()||nomeGiocatore.isEmpty()||nomeGiocatore.split(" ").length>1){
                        partita.getPg().getFrame().getTxtNome().setText("Inserisci il nome");
                    }else{
                        navi.addAll(naviRandom);
                        //cambio State e instanzio il giocatore passandogli le navi e il nome
                        partita.getPg().getFrame().setSize(1100,700);
                        partita.getPg().getCanvas().setSize(new Dimension(1100,700));
                        partita.getPg().getCanvas().setPreferredSize(new Dimension(1100,700));
                        partita.getPg().getCanvas().setMaximumSize(new Dimension(1100,700));
                        partita.getPg().getCanvas().setMinimumSize(new Dimension(1100,700));
                        partita.getPg().getFrame().getPnlSfondo().setSize(new Dimension(1100,700));
                        partita.getPg().getFrame().getPnlSfondo().setPreferredSize(new Dimension(1100,700));
                        partita.getPg().getFrame().getPnlSfondo().setMaximumSize(new Dimension(1100,700));
                        partita.getPg().getFrame().getPnlSfondo().setMinimumSize(new Dimension(1100,700));
                        partita.getPg().getFrame().getPnlTesto().setVisible(false);
                        partita.getPg().getFrame().getTxtNome().setText("");
                        partita.getPg().getFrame().pack();
                        Pc pc=new Pc(partita.getDimensione(),partita.getNavi());
                        partita.setPc(pc);
                        partita.setNomePartita(nomeGiocatore+" "+LocalDate.now()+" "+LocalTime.now());
                        Giocatore giocatore=new Giocatore(nomeGiocatore,partita.getDimensione(),navi);
                        partita.setGiocatore(giocatore);
                        partita.getMouse().setGuiManager(null);
                        partita.getBattaglia().setUp();
                        partita.getStateManager().setStatoCorrente(partita.getBattaglia());
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "non hai inserito tutte le navi");
                }
            }
            public void rightClick(){}
        }));
        grigliaG=new GrigliaGrafica(partita.getDimensione(),this.getRightX(),this.getRightY(),new ClickListener(){
            public void leftClick(){
                if(iCorrente<5){//se ci sono navi da posizionare
                    //inverto x e y della partenza perchè la x corrisponde alle righe (movimento in verticale nella matrice)
                    //mentre y corrisponde alle colonne (movimento orizzontale nella matrice)
                    puntoCorrente.setRiga(grigliaG.getCurrentI(partita.getMouse().getMouseY()));
                    puntoCorrente.setColonna(grigliaG.getCurrentJ(partita.getMouse().getMouseX()));
                    naveCorrente.aggiornaNave(puntoCorrente);
                    if(griglia.isPosizionabile(naveCorrente.getNave())){
                       //se la nave è posizionabile devo settare i parametri di quella grafica
                       naveCorrente.setX(grigliaG.getCurrentX(partita.getMouse().getMouseX()));
                       naveCorrente.setY(grigliaG.getCurrentY(partita.getMouse().getMouseY()));
                       naveCorrente.setWidth();
                       naveCorrente.setHeight();
                       //posiziono la nave
                       griglia.posiziona(naveCorrente.getNave());
                       //aggiungo la nave alla listsa delle navi
                       navi.add((naveCorrente.getNave()));
                       //aggiungo la nave grafica alla lista di navin grafiche
                       naviG.add(naveCorrente);
                       //calcolo i nuovi indici per capire quale nave devo instanziare
                       jCorrente++;
                       iCorrente();
                       nextI();
                       //metto iVecchio e jVecchio agli indici correnti perchè in caso debba fare più di una volta la randomizzazione so da che indici partire
                       iVecchio=iCorrente;
                       jVecchio=jCorrente;
                       //creo una nuova nave grafica in caso ne debba creare ancora
                       if(iCorrente<5){
                           naveCorrente=new NaveGrafica(Assets.getImage(iCorrente),iCorrente,30);
                       }
                    }
                } 
            }
            public void rightClick(){//cambia la nave da inserire da orizzontale a verticale e viceversa
                naveCorrente.setOpposto();
            }
        },30);//30 è la dimensione della tile
        guiManager.addOggGui(grigliaG);   
    }
    //calcola la x che deve avere la griglia in base alla dimensione per essere centrata
    private int getRightX(){
        return startX+(30*(16-partita.getDimensione()))/2;
    }
    //calcola la y che deve avere la griglia in base alla dimensione per essere centrata
    private int getRightY(){
        return startY+(30*(16-partita.getDimensione()))/2;
    }
    //calcola l'indice corrente del vettore che mi dice quante navi inserire
    public void iCorrente(){
        if(iCorrente<5&&jCorrente>=partita.getNavi()[iCorrente]){
            iCorrente++;
            jCorrente=0;
        }
    }
    //calcola il prossimo indice del vettore che mi dice quante navi inserire
    public void nextI(){
        if(iCorrente<5&&jCorrente+1>=partita.getNavi()[iCorrente]){
            nextI=iCorrente+1;
        }
        else{
            nextI=iCorrente;
        }
    }
    //dice la x per posizionare l'immagine in maniera centrata rispetto al riquadro (nave prossima o nave corrente)
    private int getRightImageX(int startX,int imageX){
        return startX+(180-imageX)/2; //180 dimensione cornice
    }
    
    //disegna il contorno della nave di verde se posizionabile altrimenti di rosso
    private void disegnaOutline(Graphics g){
        if(grigliaG.isSopra()&&iCorrente<5){
            //aggiorno la nave con la posizione sulla griglia in cui si trova il mouse (inverto x e y per il motivo scritto nel leftClick() della griglia
            puntoCorrente.setRiga(grigliaG.getCurrentI(partita.getMouse().getMouseY()));
            puntoCorrente.setColonna(grigliaG.getCurrentJ(partita.getMouse().getMouseX()));
            naveCorrente.aggiornaNave(puntoCorrente);
            if(griglia.isPosizionabile(naveCorrente.getNave())){ //outline di verde
                if(naveCorrente.getOrizzontale())
                g.drawImage(Assets.getImageO(iCorrente)[0],grigliaG.getCurrentX(partita.getMouse().getMouseX()),grigliaG.getCurrentY(partita.getMouse().getMouseY()),null);
                else
                g.drawImage(Assets.getImageO(iCorrente)[2],grigliaG.getCurrentX(partita.getMouse().getMouseX()),grigliaG.getCurrentY(partita.getMouse().getMouseY()),null); 
            }
            else if(griglia.isDentro(naveCorrente.getNave())&&griglia.tocca(naveCorrente.getNave())){//outline di rosso
                if(naveCorrente.getOrizzontale())
                g.drawImage(Assets.getImageO(iCorrente)[1],grigliaG.getCurrentX(partita.getMouse().getMouseX()),grigliaG.getCurrentY(partita.getMouse().getMouseY()),null);
                else
                g.drawImage(Assets.getImageO(iCorrente)[3],grigliaG.getCurrentX(partita.getMouse().getMouseX()),grigliaG.getCurrentY(partita.getMouse().getMouseY()),null);   
            }
            else if(!griglia.isDentro(naveCorrente.getNave())){//outline di rosso con la parte di nave che è fuori tagliata
                int taglia;
                BufferedImage img;
                if(naveCorrente.getOrizzontale()){
                    taglia=grigliaG.getFinalX()-grigliaG.getCurrentX(partita.getMouse().getMouseX());
                    img=Assets.getImageO(iCorrente)[1].getSubimage(0,0,taglia,30);
                    g.drawImage(img,grigliaG.getCurrentX(partita.getMouse().getMouseX()),grigliaG.getCurrentY(partita.getMouse().getMouseY()),null);
                }
                else{
                    taglia=grigliaG.getFinalY()-grigliaG.getCurrentX(partita.getMouse().getMouseY());
                    img=Assets.getImageO(iCorrente)[3].getSubimage(0, 0, 30, taglia);
                    g.drawImage(img,grigliaG.getCurrentX(partita.getMouse().getMouseX()),grigliaG.getCurrentY(partita.getMouse().getMouseY()),null);
                }
                
            }
        }
    }
    
    private void scriviNomeNavi(Graphics g){
        if(iCorrente<5){
            Font f=new Font(Font.SANS_SERIF,Font.BOLD,15);
            g.setFont(f);
            g.drawString(NomeNave.values()[iCorrente].toString(), 585, 200);
            g.drawImage(Assets.getImage(iCorrente)[0],this.getRightImageX(575,Assets.getImage(iCorrente)[0].getWidth()),255,null);
            
            if(nextI<5){
                g.drawString(NomeNave.values()[nextI].toString(), 585, 450);
                g.drawImage(Assets.getImage(nextI)[0],this.getRightImageX(575,Assets.getImage(nextI)[0].getWidth()),505,null);
            }
        }
    }
}
