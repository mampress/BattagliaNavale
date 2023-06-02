/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafica;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author mampreso.ivan
 */
public class Assets {//carica immagini, suoni e tutto quello che mi serve come "media"
    public static BufferedImage acqua,bgMenu,bgImpostazioni,bgInserimento,bgBattaglia,bgStorico,bgVittoria,bgSconfitta,bgCarica,testi,navi,naviO,tiles,tentativo,colpito,mirino;
    public static BufferedImage[] btnCarica,btnNuova,btnEsci,btnStorico,btnImpostazioni,btnAuto,btnPronto,sottomarino,cacciatorpediniere,
            incrociatore,corazzata,portaerei,sottomarinoO,cacciatorpediniereO,
            incrociatoreO,corazzataO,portaereiO,btnSalva,btnSalvaEsci,btnEsciB;
    public static Image esplosione;
    //carica quello che mi serve
    public static void inizializza(){
        try{
            esplosione=new ImageIcon(Assets.class.getResource("/Assets/esplosione.gif")).getImage();
        }catch(Exception e){
            System.exit(1);
        }
        
        //sfondi
        bgMenu=Assets.carica("/Assets/base.png");
        bgInserimento=Assets.carica("/Assets/ins_base.png");
        bgBattaglia=Assets.carica("/Assets/sfondo_battaglia.png");
        bgVittoria=Assets.carica("/Assets/vittoria.png");
        bgSconfitta=Assets.carica("/Assets/sconfitta.png"); 
        bgCarica=Assets.carica("/Assets/sfondo_carica.png");
        bgImpostazioni=Assets.carica("/Assets/impostazioni.png");
        bgStorico=Assets.carica("/Assets/storico.png");
        //bottoni testi
        //0 bottone 1 bottone schiacciato
        testi=Assets.carica("/Assets/SpreadSheetTextButton.png");
        btnCarica=new BufferedImage[2];
        btnNuova=new BufferedImage[2];
        btnEsci=new BufferedImage[2];
        btnImpostazioni=new BufferedImage[2];
        btnStorico=new BufferedImage[2];
        btnAuto=new BufferedImage[2];
        btnPronto=new BufferedImage[2];
        btnSalva=new BufferedImage[2];
        btnSalvaEsci=new BufferedImage[2];
        btnEsciB=new BufferedImage[2];

        btnNuova[0]=testi.getSubimage(17, 15, 255, 30);
        btnNuova[1]=testi.getSubimage(286, 13, 255, 32);
        btnCarica[0]=testi.getSubimage(15, 68, 267, 30);
        btnCarica[1]=testi.getSubimage(288, 66, 267, 32);
        btnEsci[0]=testi.getSubimage(14, 225, 80, 30);
        btnEsci[1]=testi.getSubimage(102, 222, 80, 32);
        btnStorico[0]=testi.getSubimage(13, 123, 333, 30);
        btnStorico[1]=testi.getSubimage(358, 122, 333, 32);
        btnImpostazioni[0]=testi.getSubimage(12, 172, 409, 30);
        btnImpostazioni[1]=testi.getSubimage(187, 222, 409, 32);
        btnAuto[0]=testi.getSubimage(11, 323, 85, 29);
        btnAuto[1]=testi.getSubimage(109, 321, 85, 31);
        btnPronto[0]=testi.getSubimage(10, 275, 125, 27);
        btnPronto[1]=testi.getSubimage(141, 273, 125, 29);
        btnSalva[0]=testi.getSubimage(446, 275, 92, 27);
        btnSalva[1]=testi.getSubimage(553, 273, 92, 29);
        btnSalvaEsci[0]=testi.getSubimage(216, 323, 218, 27);
        btnSalvaEsci[1]=testi.getSubimage(448, 321, 218, 29);
        btnEsciB[0]=testi.getSubimage(280, 275, 71, 29);
        btnEsciB[1]=testi.getSubimage(360, 273, 71, 31);
        //tiles
        tiles=Assets.carica("/Assets/tiles.png");
        acqua=tiles.getSubimage(0,0,30,30);
        tentativo=tiles.getSubimage(30,0,30,30);
        colpito=tiles.getSubimage(60,0,30,30);
        mirino=tiles.getSubimage(90,0,30,30);
        //navi
        //0 normale orizzontale 1 affondata orizzontale 2 normale verticale 3 affondata verticale
        navi=Assets.carica("/Assets/spreadSheetNavi1.png");
        sottomarino=new BufferedImage[4];
        cacciatorpediniere=new BufferedImage[4];
        incrociatore=new BufferedImage[4];
        corazzata=new BufferedImage[4];
        portaerei=new BufferedImage[4];
        sottomarino[0]=navi.getSubimage(0, 0, 90, 30);
        sottomarino[1]=navi.getSubimage(90, 0, 90, 30);
        sottomarino[2]=navi.getSubimage(36, 37, 30, 90);
        sottomarino[3]=navi.getSubimage(6, 36, 30, 90);
        cacciatorpediniere[0]=navi.getSubimage(187, 0, 60, 30);
        cacciatorpediniere[1]=navi.getSubimage(254, 0, 60, 30);
        cacciatorpediniere[2]=navi.getSubimage(73, 42, 30, 60);
        cacciatorpediniere[3]=navi.getSubimage(105, 40, 30, 60);
        incrociatore[0]=navi.getSubimage(150, 42, 90, 30);
        incrociatore[1]=navi.getSubimage(150, 72, 90, 30);
        incrociatore[2]=navi.getSubimage(254, 37, 30, 90);
        incrociatore[3]=navi.getSubimage(287, 37, 30, 90);
        corazzata[0]=navi.getSubimage(6, 131, 120, 30);
        corazzata[1]=navi.getSubimage(130, 110, 120, 30);
        corazzata[2]=navi.getSubimage(364, 4, 30, 120);
        corazzata[3]=navi.getSubimage(323, 4, 30, 120);
        portaerei[0]=navi.getSubimage(250, 131, 150, 30);
        portaerei[1]=navi.getSubimage(3, 163, 150, 30);
        portaerei[2]=navi.getSubimage(173, 146, 30, 150);
        portaerei[3]=navi.getSubimage(207, 144, 30, 150);
        //outlines
        //0 verde orizzontale 1 rosso orizzontale 2 verde verticale 3 rosso verticale
        naviO=Assets.carica("/Assets/spriteSheetOutline.png");
        sottomarinoO=new BufferedImage[4];
        cacciatorpediniereO=new BufferedImage[4];
        incrociatoreO=new BufferedImage[4];
        corazzataO=new BufferedImage[4];
        portaereiO=new BufferedImage[4];
        sottomarinoO[0]=naviO.getSubimage(0, 0, 90, 30);
        sottomarinoO[1]=naviO.getSubimage(90, 0, 90, 30);
        sottomarinoO[2]=naviO.getSubimage(36, 37, 30, 90);
        sottomarinoO[3]=naviO.getSubimage(6, 36, 30, 90);
        cacciatorpediniereO[0]=naviO.getSubimage(187, 1, 60, 30);
        cacciatorpediniereO[1]=naviO.getSubimage(254, 1, 60, 30);
        cacciatorpediniereO[2]=naviO.getSubimage(73, 42, 30, 60);
        cacciatorpediniereO[3]=naviO.getSubimage(105, 40, 30, 60);
        incrociatoreO[0]=naviO.getSubimage(150, 42, 90, 30);
        incrociatoreO[1]=naviO.getSubimage(150, 72, 90, 30);
        incrociatoreO[2]=naviO.getSubimage(254, 37, 30, 90);
        incrociatoreO[3]=naviO.getSubimage(287, 37, 30, 90);
        corazzataO[0]=naviO.getSubimage(6, 131, 120, 30);
        corazzataO[1]=naviO.getSubimage(130, 110, 120, 30);
        corazzataO[2]=naviO.getSubimage(364, 4, 30, 120);
        corazzataO[3]=naviO.getSubimage(324, 4, 30, 120);
        portaereiO[0]=naviO.getSubimage(250, 131, 150, 30);
        portaereiO[1]=naviO.getSubimage(3, 163, 150, 30);
        portaereiO[2]=naviO.getSubimage(173, 146, 30, 150);
        portaereiO[3]=naviO.getSubimage(207, 144, 30, 150);
        
    }
    //in base all'indice restituisce le immagini di una nave
    public static BufferedImage[] getImage(int i){
        switch(i){
            case 0:
                return Assets.portaerei;
            case 1:
                return Assets.corazzata;
            case 2:
                return Assets.incrociatore;
            case 3:
                return Assets.cacciatorpediniere;
            case 4:
                return Assets.sottomarino;
        }
        return null;
    }
    //in base all'indice restituisce i bordi di una nave
    public static BufferedImage[] getImageO(int i){
        switch(i){
            case 0:
                return Assets.portaereiO;
            case 1:
                return Assets.corazzataO;
            case 2:
                return Assets.incrociatoreO;
            case 3:
                return Assets.cacciatorpediniereO;
            case 4:
                return Assets.sottomarinoO;
        }
        return null;
    }
    //carica un immagine
    public static BufferedImage carica(String percorso){
        try{
            
            return ImageIO.read(Assets.class.getResource(percorso));
        }catch(Exception e){
            System.exit(1);
        }
        return null;
    }
}
