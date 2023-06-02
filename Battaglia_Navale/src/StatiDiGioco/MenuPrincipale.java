/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatiDiGioco;


import Classi.Partita;
import Grafica.Assets;
import Grafica.ClickListener;
import Grafica.BottoneTesto;

import Grafica.GuiManager;

import java.awt.Graphics;

/**
 *
 * @author mampreso.ivan
 */
public class MenuPrincipale implements Stato{
    private Partita partita;
    private GuiManager guiManager; 
    public MenuPrincipale(Partita partita){
        this.partita=partita;
    }
   
   
    

    @Override
    public void aggiornaGui(Graphics g) {
        //disegno sfondi e bottoni
        g.drawImage(Assets.bgMenu, 0, 0, null);
        guiManager.aggiornaGui(g);
        
    }
    @Override
    public void setUp() {
        this.guiManager=new GuiManager(partita);
        partita.getMouse().setGuiManager(guiManager);
        //bottoni
        guiManager.addOggGui(new BottoneTesto(274,284,255,46,Assets.btnNuova, new ClickListener(){
            public void leftClick(){
                partita.getPg().getFrame().getTxtNome().setVisible(true);
                partita.getPg().getFrame().getPnlTesto().setVisible(true);
                partita.getMouse().setGuiManager(null);//cambio stato, il mouse dovrà lavorare solo sul nuovo stato in cui sarò
                partita.getInserimento().setUp();
                partita.getStateManager().setStatoCorrente(partita.getInserimento());
            }  
            public void rightClick(){};
        }));
        guiManager.addOggGui(new BottoneTesto(266,330,267,46,Assets.btnCarica, new ClickListener(){
            public void leftClick(){
                partita.getMouse().setGuiManager(null);//cambio stato, il mouse dovrà lavorare solo sul nuovo stato in cui sarò
                partita.getCarica().setUp();
                partita.getStateManager().setStatoCorrente(partita.getCarica());
            }  
            public void rightClick(){};
        }));
        guiManager.addOggGui(new BottoneTesto(358,459,80,46,Assets.btnEsci, new ClickListener(){
            public void leftClick(){
                System.exit(1);
            } 
            public void rightClick(){};
        }));
        guiManager.addOggGui(new BottoneTesto(240,376,335,46,Assets.btnStorico, new ClickListener(){
            public void leftClick(){
                partita.getMouse().setGuiManager(null);//cambio stato, il mouse dovrà lavorare solo sul nuovo stato in cui sarò
                partita.getStorico().setUp();
                partita.getStateManager().setStatoCorrente(partita.getStorico());
            }  
            public void rightClick(){};
        }));
        guiManager.addOggGui(new BottoneTesto(200,420,409,46,Assets.btnImpostazioni, new ClickListener(){
            public void leftClick(){
                partita.getMouse().setGuiManager(null);//cambio stato, il mouse dovrà lavorare solo sul nuovo stato in cui sarò
                partita.getImpostazioni().setUp();
                partita.getStateManager().setStatoCorrente(partita.getImpostazioni());
            }
            public void rightClick(){};
        }));
    }
}
