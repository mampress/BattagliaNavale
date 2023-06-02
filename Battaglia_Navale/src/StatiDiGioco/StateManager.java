/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatiDiGioco;


import java.awt.Graphics;

/**
 *
 * @author mampreso.ivan
 */
public class StateManager {//gestisce gli stati di gioco
    private Stato statoCorrente;
    public void setStatoCorrente(Stato stato){
        this.statoCorrente=stato;
     
    }
    public Stato getStatoCorrente(){
        return this.statoCorrente;
    }
    public void aggiornaGui(Graphics g){
        statoCorrente.aggiornaGui(g);
    }
    
    
}
