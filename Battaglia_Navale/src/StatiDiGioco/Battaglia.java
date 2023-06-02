/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatiDiGioco;

import Classi.Giocatore;
import Classi.Mossa;
import Classi.Nave;
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
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mampreso.ivan
 */
public class Battaglia implements Stato{
    private Partita partita;
    private Giocatore giocatore;
    private Pc pc;
    private GuiManager guiManager;
    private String nomePartita;
    private GrigliaGrafica grigliaGDef,grigliaGAtt;
    private int vittoria; //0 non ha vinto nessuno, 1 ha vinto il giocatore, 2 ha vinto il pc
    private List<NaveGrafica> mie,naviPc;
    private List<Punto> puntiGifColpito;//punti in cui mettere la gif colpito
    private List<Integer> riprodotta;//mi dice quante volte è stata riprodotta una gif colpito (la posizione corriscponde alla lista puntiGifColpito)
    private NaveGrafica navePcDaAggiungere; //utilizzata poichè dava problemi di concurrent modification delle navi grafiche del pc
    
    public Battaglia(Partita partita){
        this.partita=partita;
    }
    

    @Override
    public void aggiornaGui(Graphics g) {
        switch (vittoria) {
            case 0:
                g.drawImage(Assets.bgBattaglia, 0, 0, null);
                guiManager.aggiornaGui(g);
                if(navePcDaAggiungere!=null &&naviPc.indexOf(navePcDaAggiungere)==-1){
                    naviPc.add(navePcDaAggiungere);
                }
                
                for(NaveGrafica n:naviPc){
                    n.aggiornaGui(g);
                }   
                for(NaveGrafica n:mie){
                    n.aggiornaGui(g);
                }  
                grigliaGAtt.disegnaStatus(g);
                grigliaGDef.disegnaStatus(g);
                for(int i=0;i<puntiGifColpito.size();i++){//riproduco la gif
                    if(riprodotta.get(i)<100){//se l'ho riprodotta meno di 100 volte la riproduco, 100 valore provato a tentativi finchè non andava bene
                        g.drawImage(Assets.esplosione,puntiGifColpito.get(i).getRiga(),puntiGifColpito.get(i).getColonna(),30,30,null);
                        int temp=riprodotta.get(i);
                        temp++;
                        riprodotta.set(i, temp);
                    }
                    
                }    
                this.disegnaMirino(g);
                break;
            case 1:
                g.drawImage(Assets.bgVittoria, 0, 0, null);
                guiManager.aggiornaGui(g);//bottone esci
                break;
            default:
                g.drawImage(Assets.bgSconfitta, 0, 0, null);
                guiManager.aggiornaGui(g);//bottone esci e griglia attacco grafica
                for(NaveGrafica n:naviPc){
                    n.aggiornaGui(g);
                } 
                break;
            
        }
        
    }

    @Override
    public void setUp() {
        //gui manager
        this.guiManager=new GuiManager(partita);
        partita.getMouse().setGuiManager(this.guiManager);
        //generali
        nomePartita=partita.getNomePartita();
        vittoria=0;
        //navi
        mie=new ArrayList<>();
        naviPc=new ArrayList<>();
        navePcDaAggiungere=null;
        //giocatori
        pc=partita.getPc();
        giocatore=partita.getGiocatore();
        //aggiungo bottoni e griglie
         this.guiManager.addOggGui(new BottoneTesto(874,645,71,127,Assets.btnEsciB,new ClickListener(){
            public void leftClick(){
                esci();
            }
            public void rightClick(){}
        }));
        this.guiManager.addOggGui(new BottoneTesto(131,645,92,84,Assets.btnSalva,new ClickListener(){
            public void leftClick(){
                rimuoviPartita();
                salvaPartita();
            }
            public void rightClick(){}
        }));
        this.guiManager.addOggGui(new BottoneTesto(451,645,218,127,Assets.btnSalvaEsci,new ClickListener(){
            public void leftClick(){
                rimuoviPartita();
                salvaPartita();
                esci();
            }
            public void rightClick(){}
        }));
       
        grigliaGAtt=new GrigliaGrafica(giocatore.getDimensione(),this.getRightX(33),this.getRightY(133),new ClickListener(){
            public void leftClick(){
                //prende il punto corrispondente al puntatore del mouse
                Punto p=new Punto(grigliaGAtt.getCurrentI(partita.getMouse().getMouseY()),grigliaGAtt.getCurrentJ(partita.getMouse().getMouseX()));
                if(vittoria==0&&!giocatore.puntoOccupato(p)){//se un punto possibile
                    Nave nave;
                    //segno i tentativi del giocatore sia graficamente che non
                    giocatore.tentativo(p);  
                    grigliaGAtt.aggiornaVal(p, -3);
                    int val=pc.getValoreETenta(p);//guardo se ho colpito o meno
                    if(val>0){
                        //colpisco sia graficamente che non
                        puntiGifColpito.add(new Punto(grigliaGAtt.getXPunto(p),grigliaGAtt.getYPunto(p)));
                        riprodotta.add(0);
                        giocatore.colpisciAttacco(p);
                        grigliaGAtt.aggiornaVal(p, -1);
                        nave=pc.aggiorna(val,p);//sicuramente la nave è colpita, vado a colpirla nella griglia di difesa del pc

                        if(nave!=null&&nave.getAffondato()){
                            //affondo la nave sia graficamente che non
                            giocatore.affondaAttacco(nave);
                            grigliaGAtt.affonda(nave);
                            navePcDaAggiungere=new NaveGrafica(Assets.getImage(nave.getIndice()),nave,grigliaGAtt.getX(),grigliaGAtt.getY(),30);
                        }
                        if(pc.tutteAffondate()){//controllo vittoria
                            rimuoviPartita();
                            aggiornaStorico(1);
                            for(int i=1;i<guiManager.getSize();i=1){//rimuovo griglie e tasti tranne il tasto esci
                                guiManager.removeOggGui(i);
                            }
                            vittoria=1;
                        }    
                    }else{
                        Punto tentPc=pc.tentativo();
                        //aggiorno la griglia del giocatore e guardo se il tentativo del pc ha colpito
                        val=giocatore.getValoreETenta(tentPc);
                        grigliaGDef.aggiornaVal(tentPc, -3);
                        while(val>0&&vittoria==0){
                            //gif colpito
                            if(pc.getPrimoColpito().getRiga()==-1){//se la nave è stata colpita per la prima volta con questo tentativo
                                pc.setPrimoColpito(tentPc);//ovvero ha coordinate negative setto il primo colpito al tentativo corrente
                            }
                            else{//se non è la prima volta che colpisco questa nave, vado aggiornare l'efficacia della mossa
                                pc.setColpitoMossa(true); 
                            }
                            puntiGifColpito.add(new Punto(grigliaGDef.getXPunto(tentPc),grigliaGDef.getYPunto(tentPc)));
                            riprodotta.add(0);
                            //aggiorno graficamente e non la griglia del pc e quella di difesa del giocatore
                            pc.colpisciAttacco(tentPc);
                            grigliaGDef.aggiornaVal(tentPc, -1);
                            nave=giocatore.aggiorna(val,tentPc);
                            if(nave!=null&&nave.getAffondato()){
                                pc.affondaAttacco(nave);
                                pc.nascondi(nave);//nascondo i dintorni della nave
                                affondaGrafica(nave);//affondo la nave sulla griglia di difesa sia grafica che non
                                grigliaGDef.affonda(nave);//vado a togliere i cerchi di tutti i colpiti per lasciar spazio alla nave affondata
                                pc.setPrimoColpito(new Punto(-1,-1));//risetto il primo colpito ad un punto non possibile
                                pc.annullaMossaPrecedente();
                            }
                            if(giocatore.tutteAffondate()){
                                vittoria=2;
                                rimuoviPartita();
                                aggiornaStorico(2);
                                for(int i=1;i<guiManager.getSize();i=1){//rimuovo griglie e tasti tranne il tasto esci
                                    guiManager.removeOggGui(i);
                                }
                                naviPc=new ArrayList<>();
                                List<Nave> naviPcNormali=pc.getNavi();
                                grigliaGAtt.setX(getRightX(308));//sposto la griglia d'attacco in centro
                                grigliaGAtt.setY(getRightY(186));
                                grigliaGAtt.setTiles();
                                for(Nave n:naviPcNormali)//prendo tutte le navi del pc e le aggiungo a quelle da stampare e le mostro al giocatore
                                {
                                    naviPc.add(new NaveGrafica(Assets.getImage(n.getIndice()),n,grigliaGAtt.getX(),grigliaGAtt.getY(),30));
                                }
                                guiManager.addOggGui(grigliaGAtt);
                                
                            }
                            //timer di un quarto di secondo per ritardare un po' le mosse di seguito del pc
                            long lastTime=System.nanoTime(),now,timer=0;
                            while(timer<=250000000){
                                now=System.nanoTime();
                                timer+=now-lastTime;
                                lastTime=now;
                            }
                            timer=0;
                            if(vittoria!=2){
                                tentPc=pc.tentativo();//rieffettuo un tentativo col pc
                                val=giocatore.getValoreETenta(tentPc);
                                grigliaGDef.aggiornaVal(tentPc, -3);
                            }  
                        }
                        pc.setColpitoMossa(false);//se non ho colpito setto l'efficacia della mossa a falsa
                    }
                }
            }
            public void rightClick(){}
        },30);
        grigliaGDef=new GrigliaGrafica(giocatore.getDimensione(),this.getRightX(582),this.getRightY(133),new ClickListener(){
           public void leftClick(){    
           }
           public void rightClick(){}
       },30);
        guiManager.addOggGui(grigliaGDef);
        guiManager.addOggGui(grigliaGAtt);
        //inizializzo le navi rendendole grafiche
        for(Nave nave:giocatore.getNavi()){
            mie.add(new NaveGrafica(Assets.getImage(nave.getIndice()),nave,grigliaGDef.getX(),grigliaGDef.getY(),30));
        }
        for(Nave nave:pc.getNavi()){
            if(nave.getAffondato()){
                naviPc.add(new NaveGrafica(Assets.getImage(nave.getIndice()),nave,grigliaGAtt.getX(),grigliaGAtt.getY(),30));
            }
        }
        aggiornaValGrigliaG(grigliaGAtt,false);
        aggiornaValGrigliaG(grigliaGDef,true);
        puntiGifColpito=new ArrayList<>();
        riprodotta=new ArrayList<>();
    }
    //calcola la x che deve avere la griglia in base alla dimensione per essere centrata
    private int getRightX(int startX){
        return startX+(30*(16-giocatore.getDimensione()))/2;
    }
    //calcola la y che deve avere la griglia in base alla dimensione per essere centrata
    private int getRightY(int startY){
        return startY+(30*(16-giocatore.getDimensione()))/2;
    }
    //cerca nelle navi grafiche e in caso la trovi la affonda
    public void affondaGrafica(Nave nave){
        for(int i=0;i<mie.size();i++){
            if(mie.get(i).getNave().equals(nave)){
                mie.get(i).setAffondato(true);
                i=mie.size();
            }
        }
    }
    public void disegnaMirino(Graphics g){
        if(grigliaGAtt.isSopra()){
            g.drawImage(Assets.mirino,grigliaGAtt.getCurrentX(partita.getMouse().getMouseX()),grigliaGAtt.getCurrentY(partita.getMouse().getMouseY()),null);
        }  
    }
    public void rimuoviPartita(){
        try{

            File f=new File("file/salvataggi.txt");
            List<String> temp=new ArrayList<>();
            BufferedReader br=new BufferedReader(new FileReader(f));
            String riga;
            riga=br.readLine();
            while(riga!=null){
                if(riga.equals(nomePartita)){ //se trovo la partita la salto del tutto
                    int righe, dimensione, numeroNavi;
                    dimensione=Integer.parseInt(br.readLine());
                    numeroNavi=Integer.parseInt(br.readLine());
                    righe=dimensione*4+numeroNavi*2+1+2+1+2;//4 griglie x partita, 2 flotte per partita, 1 nomeGiocatore, 2 righe lasciate da un giocatore all'altro,2 punto primo colpito e mossa precedente, 1 riga con scritto fine partita 
                    for(int i=0;i<righe;i++)//leggo tutte le linee che tanto poi non mi serviranno
                        riga=br.readLine();
                }
                else{
                    temp.add(riga);
                }
                riga=br.readLine();
            }
            br.close();
            PrintWriter pw=new PrintWriter(new FileWriter(f));
            for(String s:temp){
                pw.println(s);
            }
            pw.close();
            
    }catch(Exception e){
        System.exit(1);
    }
    }
    
    public void salvaPartita(){
        try{
            List<Nave> navi=giocatore.getNavi();
            File f=new File("file/salvataggi.txt");//aggihngo la partita ai salvataggi
            PrintWriter pw=new PrintWriter(new FileWriter(f,true));
            pw.println(nomePartita);
            pw.println(giocatore.getDimensione());
            pw.println(giocatore.getDimNavi());
            pw.println(giocatore.getNome());
            for(Nave n:navi){//navi giocatore
                pw.println(n.toString());
            }
            pw.println(giocatore.griglieToString());//griglie giocatore
            navi=pc.getNavi();
            for(Nave n:navi){//navi pc
                pw.println(n.toString());
            }
            pw.println(pc.griglieToString());//griglie pc
            pw.println(pc.getPrimoColpito().getRiga()+" "+pc.getPrimoColpito().getColonna());
            Mossa mossa=pc.getMossa();
            if(mossa==null){
                pw.println("null");
            }else{
                pw.println(mossa.getPunto().getRiga()+" "+mossa.getPunto().getColonna()+" "+mossa.getColpito()+" "+mossa.getDirezione());
            }
            pw.println("fine partita");
            pw.close();
        }catch(Exception e){
            System.exit(1);
        }
    }
    public void esci(){//riporto la dimensione del form a 800x700
        partita.getPg().getFrame().setSize(800,700);
        partita.getPg().getCanvas().setSize(new Dimension(800,700));
        partita.getPg().getCanvas().setPreferredSize(new Dimension(800,700));
        partita.getPg().getCanvas().setMaximumSize(new Dimension(800,700));
        partita.getPg().getCanvas().setMinimumSize(new Dimension(800,700));
        partita.getPg().getFrame().getPnlSfondo().setSize(new Dimension(800,700));
        partita.getPg().getFrame().getPnlSfondo().setPreferredSize(new Dimension(800,700));
        partita.getPg().getFrame().getPnlSfondo().setMaximumSize(new Dimension(800,700));
        partita.getPg().getFrame().getPnlSfondo().setMinimumSize(new Dimension(800,700));
        partita.getPg().getFrame().pack();
        partita.getMouse().setGuiManager(null);
        partita.getMenu().setUp();//richiamo il menu principale
        partita.getStateManager().setStatoCorrente(partita.getMenu());
    }
    public void aggiornaValGrigliaG(GrigliaGrafica g,boolean difesa){//aggiorna i valori della griglia grafica che sia o di difesa o d'attacco
        for(int i=0;i<giocatore.getDimensione();i++){
            for(int j=0;j<giocatore.getDimensione();j++){
                if(giocatore.getValore(new Punto(i,j),difesa)<0){
                    g.aggiornaVal(new Punto(i,j),giocatore.getValore(new Punto(i,j),difesa));
                }   
            }
        }
    }
    //vittoria se 1 ha vinto il giocatore, se 2 ha vinto il pc
    public void aggiornaStorico(int vittoria){
        String nome=giocatore.getNome();
        boolean trovato=false;
        try{
            File f=new File("file/storico.txt");
            List<String> temp=new ArrayList<>();
            BufferedReader br=new BufferedReader(new FileReader(f));
            
            String riga=br.readLine(),rigaTrovata="";
            while(riga!=null&&!riga.isBlank()&&!riga.isEmpty()){
                if(riga.split(" ")[0].equals(nome)){
                    trovato=true;
                    rigaTrovata=riga;
                }
                else{
                    temp.add(riga);
                }
                riga=br.readLine();
            }
            br.close();
            //nome , partite giocate, partite vinte, percentuale vittorie
            if(!trovato){
                if(vittoria==1){
                    temp.add(nome+" "+"1 1 100");
                }
                else{
                   temp.add(nome+" "+"1 0 0");
                }
                System.out.println(temp.size());
            }
            else{
                String input[]=rigaTrovata.split(" ");
                int vittorie,giocate;
                double ratio,ratioRounded;
                vittorie=Integer.parseInt(input[2]);
                giocate=Integer.parseInt(input[1])+1;
                if(vittoria==1){
                    vittorie++;
                }
                ratio=((double)vittorie/(double)giocate)*100.0;
                ratioRounded=Math.round(ratio*100)/100;//arrotondo a due cifre decimali la percentuale
                temp.add(input[0]+" "+giocate+" "+vittorie+" "+ratioRounded);  
            }
            PrintWriter pw=new PrintWriter(new FileWriter(f));
            for(String s:temp){
                pw.println(s);
            }
            pw.close();
        }catch(Exception e){
            System.exit(1);
        }

    }
}
