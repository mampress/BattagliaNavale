/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafica;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author mampreso.ivan
 */
public class BottoneTesto extends OggettoGui{
    private BufferedImage[] testi;
    private ClickListener click; //gestore di click sulla scritta
    public BottoneTesto(int x, int y, int width, int height,BufferedImage[] testi,ClickListener click) {
        super(x, y, width, height);
        this.testi=testi;
        this.click=click;
    }
    
    
   
    @Override
    public void aggiornaGui(Graphics g) {//se ci si trova sopra con il mouse disegna il bottone schiacciato altrimenti quello normale
        if(sopra)
            g.drawImage(testi[1], x, y, null);
        else
            g.drawImage(testi[0], x, y, null);
        
        
    }

    @Override //esegue il metodo rightClick() passato come parametro
    public void rightClick() {
        click.rightClick();
    }
    @Override //esegue il metodo leftClick() passato come parametro
    public void leftClick(){
        click.leftClick();
    }
}
