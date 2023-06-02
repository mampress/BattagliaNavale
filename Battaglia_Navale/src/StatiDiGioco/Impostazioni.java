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
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author mampreso.ivan
 */
public class Impostazioni implements Stato{
    private Partita partita;
    private GuiManager guiManager;
    //in ordine, in base alla dimensione, mi dice quante navi inserire per tipo
    private final int [][] navi={{1,1,1,1,1},{1,1,1,2,1},{1,1,1,2,2},{1,1,2,2,2},{1,2,2,2,2},{1,2,2,3,2},{2,2,2,2,2},{2,2,2,3,3},{2,2,3,3,3}};
    public Impostazioni(Partita partita){
        this.partita=partita;
    }
   

    @Override
    public void aggiornaGui(Graphics g) {
        g.drawImage(Assets.bgImpostazioni,0,0,null);
        guiManager.aggiornaGui(g);
        int[] navi=this.navi[partita.getPg().getFrame().getSldDimensione().getValue()-8];//leggo la dimensione dallo slider
        Font f=new Font(Font.SANS_SERIF,Font.BOLD,30);
        g.setFont(f);
        g.setColor(Color.WHITE);
        //quantità per ogni nave
        g.drawString("x "+navi[0], 270, 490);
        g.drawString("x "+navi[1], 490, 490);
        g.drawString("x "+navi[2], 175, 640);
        g.drawString("x "+navi[3], 385, 640);
        g.drawString("x "+navi[4], 590, 640);
        //sagoma per ognin tipo di nave
        g.drawImage(Assets.portaerei[0], this.getRightImageX(200,Assets.portaerei[0].getWidth()), 420, null);
        g.drawImage(Assets.corazzata[0], this.getRightImageX(413,Assets.corazzata[0].getWidth()), 420, null);
        g.drawImage(Assets.incrociatore[0], this.getRightImageX(103,Assets.incrociatore[0].getWidth()), 570, null);
        g.drawImage(Assets.cacciatorpediniere[0], this.getRightImageX(310,Assets.cacciatorpediniere[0].getWidth()), 570, null);
        g.drawImage(Assets.sottomarino[0], this.getRightImageX(517,Assets.sottomarino[0].getWidth()), 570, null);
    }

    @Override
    public void setUp() {
        partita.getPg().getFrame().getPnlRegole().setVisible(true);
        partita.getPg().getFrame().getPnlDimensione().setVisible(true);
        this.guiManager=new GuiManager(partita);
        partita.getMouse().setGuiManager(guiManager);
        try{//scrivo le regole
            File f=new File("file/regole.txt");
            BufferedReader br=new BufferedReader(new FileReader(f));
            String riga=br.readLine();
            while(riga!=null){
                partita.getPg().getFrame().getTxaRegole().append(riga+"\n");
                riga=br.readLine();
            }
            br.close();
            partita.getPg().getFrame().getTxaRegole().setCaretPosition(0);
        }catch(Exception e){
            System.exit(1);
        }
        guiManager.addOggGui(new BottoneTesto(25,294,71,127,Assets.btnEsciB,new ClickListener(){
            public void leftClick(){
                partita.setDimensione(partita.getPg().getFrame().getSldDimensione().getValue());
                partita.setNavi(navi[partita.getPg().getFrame().getSldDimensione().getValue()-8]);
                partita.getMouse().setGuiManager(null);//cambio stato, il mouse dovrà lavorare solo sul nuovo stato in cui sarò
                partita.getMenu().setUp();
                partita.getPg().getFrame().getPnlRegole().setVisible(false);
                partita.getPg().getFrame().getPnlDimensione().setVisible(false);
                partita.getStateManager().setStatoCorrente(partita.getMenu());
            }
            public void rightClick(){}
        }));
    }
    //dice la x per posizionare l'immagine in maniera centrata rispetto al riquadro
    private int getRightImageX(int startX,int imageX){
        return startX+(180-imageX)/2; //180 dimensione cornice
    }
}
