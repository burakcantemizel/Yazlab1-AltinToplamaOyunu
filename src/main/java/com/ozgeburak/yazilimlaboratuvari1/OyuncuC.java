package com.ozgeburak.yazilimlaboratuvari1;

import com.ozgeburak.yazilimlaboratuvari1.AStar.Dugum;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class OyuncuC extends Oyuncu {

    OyuncuC(int koordinatX, int koordinatY) {
        super(koordinatX, koordinatY);
        this.r = 0;
        this.g = 0;
        this.b = 255;
        
                try {
            this.resim = ImageIO.read(new File("kaynaklar/oyuncuC.png"));
        } catch (IOException ex) {
            Logger.getLogger(OyuncuA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void gizliAltinlariAcigaCikart(Harita harita, int adet) {
        //Her turun başında 2 tane en yakin gizli altini aciga cikaracak
        int acigaCikan = 0;
        
        for (int tekrar = 0; tekrar < adet; tekrar++) {
            AStar as;
            List<AStar.Dugum> enKisaGizliAltinYol = null;
            boolean gizliAltinVarMi = false;
            for (Kare kare : harita.kareler) {
                if (kare.gizliAltin == true) {
                    as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                    enKisaGizliAltinYol = as.findPathTo(kare.koordinatX, kare.koordinatY);
                    gizliAltinVarMi = true;
                    break;
                }
            }
            
            if(gizliAltinVarMi == false){
                return;
            }
            

            //En kisa gizli altini bulcaz
            for (int i = 0; i < harita.kareler.size(); i++) {
                if (harita.kareler.get(i).gizliAltin == true) {
                    as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                    List<Dugum> yol = as.findPathTo(harita.kareler.get(i).koordinatX, harita.kareler.get(i).koordinatY);
                    if (yol != null) {
                        if (yol.get(yol.size() - 1).g < enKisaGizliAltinYol.get(enKisaGizliAltinYol.size() - 1).g) {
                            enKisaGizliAltinYol = yol;
                            this.hedefKareIndeks = i;
                        }
                    }
                }
            }

            //Burdan sonra elimizde en kisa gizli altin var
            for (Kare kare : harita.kareler) {
                if (kare.koordinatX == enKisaGizliAltinYol.get(enKisaGizliAltinYol.size() - 1).x
                        && kare.koordinatY == enKisaGizliAltinYol.get(enKisaGizliAltinYol.size() - 1).y) {
                    kare.gizliAltin = false;
                    kare.altin = true;
                }
            }

        }

    }

}
