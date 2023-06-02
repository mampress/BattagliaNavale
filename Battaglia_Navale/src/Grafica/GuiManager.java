/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafica;

import Classi.Partita;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 *
 * @author mampreso.ivan
 */
//gestisce gli elementi grafici con eventi
public class GuiManager {
    private Partita partita;
    private List<OggettoGui> oggettiGui;
    public GuiManager(Partita partita){
        this.partita=partita;
        oggettiGui=new ArrayList<>(); 
    }
    public void aggiornaGui(Graphics g){//per disegnare gli elementi
        for(int i=0;i<oggettiGui.size();i++){
            oggettiGui.get(i).aggiornaGui(g);
        }
    }
    public void onMouseMove(MouseEvent e){//quando muovo il mouse
        for(int i=0;i<oggettiGui.size();i++){
            oggettiGui.get(i).onMouseMove(e);
        }
    }
    public void onMouseRelease(MouseEvent e){//quando clicclo il mouse
        for(int i=0;i<oggettiGui.size();i++){
            oggettiGui.get(i).onMouseRelease(e);
        }
    }
    public void addOggGui(OggettoGui o){
        oggettiGui.add(o);
    }
    public  void removeOggGui(OggettoGui o){
        oggettiGui.remove(o);
    }
    public void removeOggGui(int i){
        oggettiGui.remove(i);
    }
    public int getSize(){
        return oggettiGui.size();
    }
}
