/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classi;

import java.util.Random;

/**
 *
 * @author mampreso.ivan
 */
public enum Direzione {
    SU,GIU,DESTRA,SINISTRA;
    
    public Direzione direzioneOpposta(){
        Direzione dir=SU;
        switch(this){
            case SU:
                dir=GIU;
                break;
            case GIU:
                dir=SU;
                break;
            case DESTRA:
                dir=SINISTRA;
                break;
            case SINISTRA:
                dir=DESTRA;
                break;
        }
        return dir;
    }
    //restituisce una direzione di asse opposta rispetto a quella chiamante
    public Direzione direzioneAsse(){
        Direzione d;
        Random r=new Random();
        if(this==SU||this==GIU)
            d=Direzione.values()[r.nextInt(2)+2];//destra o sinistra
        else
            d=Direzione.values()[r.nextInt(2)];//giu o su
        return d;
    }
}
