/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatiDiGioco;

import Classi.Direzione;
import Classi.Giocatore;
import Classi.Mossa;
import Classi.Nave;
import Classi.NomeNave;
import Classi.Partita;
import Classi.Pc;
import Classi.Punto;
import Grafica.Assets;
import Grafica.BottoneTesto;
import Grafica.ClickListener;
import Grafica.GuiManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mampreso.ivan
 */
public class Carica implements Stato{
    private Partita partita;
    private GuiManager guiManager;
    
    public Carica(Partita partita){
        this.partita=partita;
    }
    

    @Override
    public void aggiornaGui(Graphics g) {
        g.drawImage(Assets.bgCarica, 0, 0,null);
        guiManager.aggiornaGui(g);
    }

    @Override
    public void setUp() {
        this.guiManager=new GuiManager(partita);
        partita.getMouse().setGuiManager(guiManager);
        guiManager.addOggGui(new BottoneTesto(579,636,121,42,Assets.btnPronto,new ClickListener(){
            public void leftClick(){
                String nomePartita=(String) partita.getPg().getFrame().getLista().getSelectedValue();
                if(nomePartita!=null){
                    partita.setNomePartita(nomePartita);
                    carica(nomePartita);
                    partita.getMouse().setGuiManager(null);//cambio stato, il mouse dovrà lavorare solo sul nuovo stato in cui sarò
                    //porto la dimensione del form a 1100x700
                    partita.getPg().getFrame().setSize(1100,700);
                    partita.getPg().getCanvas().setSize(new Dimension(1100,700));
                    partita.getPg().getCanvas().setPreferredSize(new Dimension(1100,700));
                    partita.getPg().getCanvas().setMaximumSize(new Dimension(1100,700));
                    partita.getPg().getCanvas().setMinimumSize(new Dimension(1100,700));
                    partita.getPg().getFrame().getPnlSfondo().setSize(new Dimension(1100,700));
                    partita.getPg().getFrame().getPnlSfondo().setPreferredSize(new Dimension(1100,700));
                    partita.getPg().getFrame().getPnlSfondo().setMaximumSize(new Dimension(1100,700));
                    partita.getPg().getFrame().getPnlSfondo().setMinimumSize(new Dimension(1100,700));
                    partita.getPg().getFrame().getDlm().removeAllElements();
                    partita.getPg().getFrame().getPnlLista().setVisible(false);
                    partita.getPg().getFrame().pack();
                    partita.getBattaglia().setUp();
                    partita.getStateManager().setStatoCorrente(partita.getBattaglia());
                }else{
                    if(partita.getPg().getFrame().getDlm().isEmpty())
                        JOptionPane.showMessageDialog(null,"Non ci sono partite da caricare");
                    else if(partita.getPg().getFrame().getLista().getSelectionModel().isSelectionEmpty())
                        JOptionPane.showMessageDialog(null,"Seleziona una partita da caricare");
                }
            }
            public void rightClick(){}
        }));
        guiManager.addOggGui(new BottoneTesto(50,636,71,127,Assets.btnEsciB,new ClickListener(){
            public void leftClick(){
                partita.getMouse().setGuiManager(null);//cambio stato, il mouse dovrà lavorare solo sul nuovo stato in cui sarò
                partita.getMenu().setUp();
                partita.getPg().getFrame().getDlm().removeAllElements();
                partita.getPg().getFrame().getPnlLista().setVisible(false);
                partita.getStateManager().setStatoCorrente(partita.getMenu());
            }
            public void rightClick(){}
        }));
        caricaPartite();
        partita.getPg().getFrame().getPnlLista().setVisible(true);
    }
    public void caricaPartite(){
        try{
            
            List<String> lista=new ArrayList<>();
            File f=new File("file/salvataggi.txt");
            BufferedReader br=new BufferedReader(new FileReader(f));
            String riga=br.readLine();
            if(riga!=null){//prima partita
                lista.add(riga);
            }
            while(riga!=null){
                if(riga.equals("fine partita")){
                    riga=br.readLine();//prendo il nome della partita;
                    lista.add(riga);
                }
                riga=br.readLine();
            }
            br.close();
            for(String s:lista){//aggiungo alla lista delle partite tutte le partite presenti nei salvataggi
                partita.getPg().getFrame().getDlm().addElement(s);
            }
        }catch(Exception e){
            System.exit(1);
        }
        
    }
    public void carica(String nomePartita){//carica una partita secondo lo schema sottostante
        try{
            /**
             * schema del file:
             * nome partita
             * dimensione griglie
             * numero di navi
             * nome giocatore
             * successione di navi del giocatore (ogni nave ha blocchi, partenza, nome, affondato, colpito e orizzontale
             * griglia difesa del giocatore
             * griglia attacco del giocatore
             * successione navi pc
             * griglia difesa del pc
             * griglia attacco del pc
             * primocolpito
             * mossa precedente
             * stringa "fine partita"
             */
            File f=new File("file/salvataggi.txt");
            BufferedReader br=new BufferedReader(new FileReader(f));
            String riga=br.readLine();
            boolean caricata=false;
            while(riga!=null&&!caricata){
                if(riga.equals(nomePartita)){
                    int dimensione,numeroNavi,blocchi,r,c,colpito;
                    int [][]gAtt,gDef;
                    boolean affondato, orizzontale;
                    NomeNave nomeNave;
                    String nomeGiocatore;
                    String [] input;
                    List<Nave> navi=new ArrayList<>();
                    dimensione=Integer.parseInt(br.readLine());
                    numeroNavi=Integer.parseInt(br.readLine());
                    nomeGiocatore=br.readLine();
                    for(int i=0;i<numeroNavi;i++){
                        input=br.readLine().split(" ");
                        blocchi=Integer.parseInt(input[0]);
                        r=Integer.parseInt(input[1]);
                        c=Integer.parseInt(input[2]);
                        nomeNave=NomeNave.valueOf(input[3]);
                        affondato=Boolean.parseBoolean(input[4]);
                        colpito=Integer.parseInt(input[5]);
                        orizzontale=Boolean.parseBoolean(input[6]);
                        
                        navi.add(new Nave(blocchi,new Punto(r,c),nomeNave,affondato,colpito,orizzontale));
                    }
                    gAtt=new int[dimensione][dimensione];
                    gDef=new int[dimensione][dimensione];
                    for(int i=0;i<dimensione;i++){
                        input=br.readLine().split(" ");
                        for(int j=0;j<dimensione;j++){
                            gDef[i][j]=Integer.parseInt(input[j]);
                        }
                    }
                    for(int i=0;i<dimensione;i++){
                        input=br.readLine().split(" ");
                        for(int j=0;j<dimensione;j++){
                            gAtt[i][j]=Integer.parseInt(input[j]);
                        }
                    }
                    
                    Giocatore giocatore=new Giocatore(nomeGiocatore,dimensione,gAtt,gDef,navi);
                    
                    partita.setGiocatore(giocatore);
                    
                    navi.removeAll(navi);
                    riga=br.readLine();
                    for(int i=0;i<numeroNavi;i++){
                        input=br.readLine().split(" ");
                        blocchi=Integer.parseInt(input[0]);
                        r=Integer.parseInt(input[1]);
                        c=Integer.parseInt(input[2]);
                        nomeNave=NomeNave.valueOf(input[3]);
                        affondato=Boolean.parseBoolean(input[4]);
                        colpito=Integer.parseInt(input[5]);
                        orizzontale=Boolean.parseBoolean(input[6]);
                        navi.add(new Nave(blocchi,new Punto(r,c),nomeNave,affondato,colpito,orizzontale));
                    }
                    gAtt=new int[dimensione][dimensione];
                    gDef=new int[dimensione][dimensione];
                    for(int i=0;i<dimensione;i++){
                        input=br.readLine().split(" ");
                        for(int j=0;j<dimensione;j++){
                            gDef[i][j]=Integer.parseInt(input[j]);
                        }
                    }
                    for(int i=0;i<dimensione;i++){
                        input=br.readLine().split(" ");
                        for(int j=0;j<dimensione;j++){
                            gAtt[i][j]=Integer.parseInt(input[j]);
                        }
                    }
                    riga=br.readLine();
                    Punto p;
                    input=br.readLine().split(" ");
                    r=Integer.parseInt(input[0]);
                    c=Integer.parseInt(input[1]);
                    p=new Punto(r,c);
                    Mossa m;
                    riga=br.readLine();
                    if(riga.equals("null"))
                        m=null;
                    else{
                        Direzione d;
                        boolean colpitoo;
                        input=riga.split(" ");
                        r=Integer.parseInt(input[0]);
                        c=Integer.parseInt(input[1]);
                        colpitoo=Boolean.parseBoolean(input[2]);
                        d=Direzione.valueOf(input[3]);
                        m=new Mossa(new Punto(r,c),colpitoo,d);
                    }
                    Pc pc=new Pc(dimensione,gAtt,gDef,navi,p,m);
                    partita.setPc(pc);
                    caricata=true;
                }
                riga=br.readLine();
            }
            br.close();
        }catch(Exception e){
            System.exit(1);
        }
    }
    public void stampaGriglia(int[][] g){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                System.out.print(g[i][j]);
            }
            System.out.println(" ");
        }
    }
}
