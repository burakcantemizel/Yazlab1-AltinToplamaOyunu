package com.ozgeburak.yazilimlaboratuvari1;

import com.ozgeburak.yazilimlaboratuvari1.AStar;
import com.ozgeburak.yazilimlaboratuvari1.Dugum;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class OyuncuC extends Oyuncu {

    OyuncuC(int koordinatX, int koordinatY) {
        super(koordinatX, koordinatY);
        this.r = 255;
        this.g = 255;
        this.b = 255;

        try {
            this.resim = ImageIO.read(new File("kaynaklar/oyuncuC.png"));
        } catch (IOException ex) {
            Logger.getLogger(OyuncuA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void gizliAltinlariAcigaCikart(Harita harita, int adet) throws IOException {
        //Her turun başında 2 tane en yakin gizli altini aciga cikaracak
        int acigaCikan = 0;

        for (int tekrar = 0; tekrar < adet; tekrar++) {
            
            
           
             boolean gizliAltinVarMi = false;
            
            for (Kare kare : harita.gizliAltinOlanKareler) {
                if (kare.gizliAltin == true) {
                    //as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY);
                    //enKisaGizliAltinYol = as.yolBul(kare.koordinatX, kare.koordinatY);
                    //enKisaGizliAltinKare = harita.gizliAltinOlanKareler.get(kare;
                    gizliAltinVarMi = true;
                    break;
                }
            }
            
            if (gizliAltinVarMi == false) {
                return;
            }
            Kare enKisaGizliAltinKare = harita.gizliAltinOlanKareler.get(0);
            //AStar as;
            List<Dugum> enKisaGizliAltinYol = null;
           
            int enKisaGizliAltinIndex = 0;
            
            /*
            //En kisa gizli altini bulcaz
            for (int i = 0; i < harita.gizliAltinOlanKareler.size(); i++) {
                if (harita.gizliAltinOlanKareler.get(i).gizliAltin == true) {
                    as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY);
                    List<Dugum> yol = as.yolBul(harita.gizliAltinOlanKareler.get(i).koordinatX, harita.gizliAltinOlanKareler.get(i).koordinatY);
                    if (yol != null) {
                        if (yol.get(yol.size() - 1).g < enKisaGizliAltinYol.get(enKisaGizliAltinYol.size() - 1).g) {
                            enKisaGizliAltinYol = yol;
                            this.hedefKareIndeks = i;
                        }
                    }
                }
            }
            */
            
            for(int i = 0; i < harita.gizliAltinOlanKareler.size(); i++){
                if(harita.gizliAltinOlanKareler.get(i).gizliAltin == true){
                    if(manhattanUzaklik(this.koordinatX, this.koordinatY, harita.gizliAltinOlanKareler.get(i).koordinatX, harita.gizliAltinOlanKareler.get(i).koordinatY) <=
                        manhattanUzaklik(this.koordinatX, this.koordinatY, harita.gizliAltinOlanKareler.get(enKisaGizliAltinIndex).koordinatX, harita.gizliAltinOlanKareler.get(enKisaGizliAltinIndex).koordinatY)){
                        enKisaGizliAltinIndex = i;
                        enKisaGizliAltinKare = harita.gizliAltinOlanKareler.get(i);
                    }
                }
            }
            

            //Burdan sonra elimizde en kisa gizli altin var
            for (Kare kare : harita.gizliAltinOlanKareler) {
                if (kare.koordinatX == enKisaGizliAltinKare.koordinatX
                        && kare.koordinatY == enKisaGizliAltinKare.koordinatY) {
                    kare.gizliAltin = false;
                    kare.altin = true;
                    harita.altinOlanKareler.add(kare);
                    Oyun.fwOyuncuC.write("Gizli altin aciga cikarildi. x: " + Integer.toString(kare.koordinatX) + " y: " + Integer.toString(kare.koordinatY) + "\n");
                    harita.gizliAltinOlanKareler.remove(kare);
                    break;
                    
                }
            }

        }

    }

}
