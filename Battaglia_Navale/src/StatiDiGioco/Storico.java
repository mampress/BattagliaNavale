/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatiDiGioco;

import Classi.Partita;
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

/**
 *
 * @author mampreso.ivan
 */
public class Storico implements Stato{
    private Partita partita;
    private GuiManager guiManager;
    private List<String> giocatori;
    private String sceltaPrecedente;
    public Storico(Partita partita){
        this.partita=partita;
    }
    @Override
    public void aggiornaGui(Graphics g) {
       g.drawImage(Assets.bgStorico,0,0,null);
       guiManager.aggiornaGui(g);
       String scelta=partita.getPg().getFrame().getGroupStorico().getSelection().getActionCommand();
       if(!sceltaPrecedente.equals(scelta)){
           switch(scelta){//ordino in base al radiobutton scelto
                case "alfabetico":
                    ordina(0);
                    break;
                case "percentuale":
                    ordina(3);
                    break;
                case "vittorie":
                    ordina(2);
                    break;
                case "partite":
                    ordina(1);
                    break;
                default:
                    ordina(0);
                    break;
           }
           partita.getPg().getFrame().getTxaRegole().setText("");
           partita.getPg().getFrame().getTxaRegole().append(" Nome                 partite giocate    partite vinte   %vittorie\n");
           String [] input;
            for(String s:giocatori){
                input=s.split(" ");//formatto lo storico con i caratteri adeguati
                String formatta="",formatta2="",formatta3="";
                for(int i=0;i<21-input[0].length();i++)//21= caratteri da nome giocatore a partite giocate
                    formatta+=" ";
                for(int i=0;i<19-input[1].length();i++)//19 =  caratteri da partite giocate a partite vinte
                    formatta2+=" ";
                for(int i=0;i<16-input[2].length();i++)//16 = caratteri da partite vinte a % vittorie
                    formatta3+=" ";
                partita.getPg().getFrame().getTxaRegole().append(" "+input[0]+formatta+input[1]+formatta2+input[2]+formatta3+input[3]+"\n");
            }
             partita.getPg().getFrame().getTxaRegole().setCaretPosition(0);
           sceltaPrecedente=scelta;
       }
    }

    @Override
    public void setUp() {
        this.guiManager=new GuiManager(partita);
        partita.getMouse().setGuiManager(guiManager);
        giocatori=new ArrayList<>();
        guiManager.addOggGui(new BottoneTesto(70,620,71,127,Assets.btnEsciB,new ClickListener(){
            public void leftClick(){
                partita.getMouse().setGuiManager(null);//cambio stato, il mouse dovrà lavorare solo sul nuovo stato in cui sarò
                partita.getPg().getFrame().getPnlRegole().setVisible(false);
                partita.getPg().getFrame().getPnlRegole().setBounds(27,28,745,230);
                partita.getPg().getFrame().getPnlRegole().setPreferredSize(new Dimension(745,230));
                partita.getPg().getFrame().getTxaRegole().setPreferredSize(new Dimension(747,230));
                partita.getPg().getFrame().getRdbAlfabetico().setVisible(false);
                partita.getPg().getFrame().getRdbPercentuale().setVisible(false);
                partita.getPg().getFrame().getRdbVittorie().setVisible(false);
                partita.getPg().getFrame().getRdbPartite().setVisible(false);
                partita.getPg().getFrame().getTxaRegole().setText("");
                partita.getMenu().setUp();
                partita.getStateManager().setStatoCorrente(partita.getMenu());
                
            }
            public void rightClick(){}
        }));
        partita.getPg().getFrame().getPnlRegole().setBounds(26, 192, 747, 310);
        partita.getPg().getFrame().getPnlRegole().setPreferredSize(new Dimension(747,310));
        partita.getPg().getFrame().getTxaRegole().setPreferredSize(new Dimension(747,310));
        partita.getPg().getFrame().getPnlRegole().setVisible(true);
        partita.getPg().getFrame().getRdbAlfabetico().setVisible(true);
        partita.getPg().getFrame().getRdbPercentuale().setVisible(true);
        partita.getPg().getFrame().getRdbVittorie().setVisible(true);
        partita.getPg().getFrame().getRdbPartite().setVisible(true);
        prendiGiocatori();
        partita.getPg().getFrame().getTxaRegole().setText("");
        sceltaPrecedente="alfabetico";
        ordina(0);
        if(partita.getPg().getFrame().getTxaRegole().getText().isEmpty()){
            partita.getPg().getFrame().getTxaRegole().append(" Nome                 partite giocate    partite vinte   %vittorie\n");
            String [] input;
            for(String s:giocatori){
                input=s.split(" ");
                String formatta="",formatta2="",formatta3="";
                for(int i=0;i<21-input[0].length();i++)
                    formatta+=" ";
                for(int i=0;i<19-input[1].length();i++)
                    formatta2+=" ";
                for(int i=0;i<16-input[2].length();i++)
                    formatta3+=" ";
                partita.getPg().getFrame().getTxaRegole().append(" "+input[0]+formatta+input[1]+formatta2+input[2]+formatta3+input[3]+"\n");
            }
        }
         partita.getPg().getFrame().getTxaRegole().setCaretPosition(0);
    }
    
    public void prendiGiocatori(){
        try{
            File f=new File("file/storico.txt");
             BufferedReader br=new BufferedReader(new FileReader(f));
             String riga=br.readLine();
             while(riga!=null&&!riga.isBlank()&&!riga.isEmpty()){
                 giocatori.add(riga);
                 riga=br.readLine();
             }
             br.close();
        }catch(Exception e){
            System.exit(1);
        }
    }
    
    
    private void ordina(int parametro){
        if(giocatori.size()>0){
            int iMax;
            for(int i=0;i<giocatori.size();i++){
                iMax=max(i,parametro);
                scambia(iMax,i);
            }
        }else{
            partita.getPg().getFrame().getTxaRegole().setText(" Non sono presenti dati nello storico.");
        }
    }
    private int max(int start,int parametro){
        String input[]=giocatori.get(start).split(" ");
        int iMax=start;
        if(parametro>0){//se prenderò un dato numerico
            double max=Double.parseDouble(input[parametro]);
            for(int i=start+1;i<giocatori.size();i++){
                input=giocatori.get(i).split(" ");
                if(Double.parseDouble(input[parametro])>max){
                    iMax=i;
                    max=Double.parseDouble(input[parametro]);
                }
            }
        }else{//se prendo il nome del giocatore
            String max=input[parametro];
            for(int i=start+1;i<giocatori.size();i++){
                input=giocatori.get(i).split(" ");
                if(input[parametro].compareTo(max)<0){
                    iMax=i;
                    max=input[parametro];
                }
            }
        }
        return iMax;
    }
    private void scambia(int iMax,int i){
        String scambio=giocatori.get(iMax);
        giocatori.remove(iMax);
        giocatori.add(i,scambio);
    }
}
