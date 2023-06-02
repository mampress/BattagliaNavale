/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafica;

import java.awt.Canvas;
import java.awt.Dimension;


/**
 *
 * @author flavio
 */
public class PartitaGrafica {
    private FormPartita partita;
    private Canvas canvas; //canvas="tela" dove posso disegnare
    public PartitaGrafica(){
        partita=new FormPartita();
        canvas=new Canvas();
        canvas.setSize(new Dimension(800,700));
        canvas.setPreferredSize(new Dimension(800,700));
        canvas.setMinimumSize(new Dimension(800,700));
        canvas.setMaximumSize(new Dimension(800,700));
        canvas.setFocusable(false);
        partita.getPnlSfondo().add(canvas);
        partita.setVisible(true);
        partita.pack();
    }
    public Canvas getCanvas(){
        return canvas;
    }
    public FormPartita getFrame(){
        return partita;
    }
    
    
}
