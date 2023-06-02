/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classi;

import Grafica.GuiManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author mampreso.ivan
 */
//permette di gestire il mouse
public class MouseManager implements MouseListener,MouseMotionListener{
    private boolean destro,sinistro;
    private int mouseX,mouseY;
    private GuiManager guiManager; //gestisce bottoni, griglie e tutti gli elementi grafici che hanno degli eventi (è lo stesso della partita)
    
    
    public void setGuiManager(GuiManager guiManager){
        this.guiManager=guiManager;
    }
    public boolean schiacciatoDestra(){
        return destro;
    }
    public boolean schiacciatoSinistra(){
        return sinistro;
    }
    //coordinata x del mouse
    public int getMouseX() {
        return mouseX;
    }
    //coordinata y del mouse
    public int getMouseY() {
        return mouseY;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public synchronized void mousePressed(MouseEvent e) { //se il mouse è schiacciato mette la variabile corrispondente a true
        if(e.getButton()==MouseEvent.BUTTON1)
            sinistro=true;
        else if(e.getButton()==MouseEvent.BUTTON3)
            destro=true;
    }
    
    @Override
    public synchronized void mouseReleased(MouseEvent e) {//se il mouse è rilasciato mette la variabile corrispondente a false
        if(e.getButton()==MouseEvent.BUTTON1){
            sinistro=false;
            
        }   
        else if(e.getButton()==MouseEvent.BUTTON3){
            destro=false;
            
        }       
        if(guiManager!=null){ //esegue l'evento del mouse rilasciato (ovvero cliccato) per tutti gli elementi grafici cliccabili
            guiManager.onMouseRelease(e);
        }
       
    }
    @Override
    public synchronized void mouseMoved(MouseEvent e) {//mi dice le coordinate del mouse nel form
        mouseX=e.getX();
        mouseY=e.getY();
        
        if(guiManager!=null){
            guiManager.onMouseMove(e); //esegue l'evento di quando si muove il mouse per tutti gli elemnti grafici
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    public GuiManager getGuiManager(){
        return guiManager;
    }  
}
