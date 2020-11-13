package com.ozgeburak.yazilimlaboratuvari1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class OyuncuA extends Oyuncu{
  
    OyuncuA(int koordinatX, int koordinatY){
    super(koordinatX,koordinatY);
    this.r = 255;
    this.g = 0;
    this.b = 0;
    
        try {
            this.resim = ImageIO.read(new File("kaynaklar/oyuncuA.png"));
        } catch (IOException ex) {
            Logger.getLogger(OyuncuA.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
    
  void hedefBelirle(Harita harita) {
        //En kısa nesneyi belirleyeceğiz
        AStar as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
        List<AStar.Node> enKisaYol = null;
        for (Kare kare : harita.kareler) {
            if (kare.altin == true) {
                enKisaYol = as.findPathTo(kare.koordinatX, kare.koordinatY);
                break;
            }
        }
        this.hedefKareIndeks = 0;

        //Altin olan kareler yerine direkt karelerde altin varsa diye bakarsak
        for (int i = 0; i < harita.kareler.size(); i++) {
            if (harita.kareler.get(i).altin == true) {
                as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                List<AStar.Node> yol = as.findPathTo(harita.kareler.get(i).koordinatX, harita.kareler.get(i).koordinatY);
                if (yol != null) {
                    if (yol.get(yol.size() - 1).g < enKisaYol.get(enKisaYol.size() - 1).g) {
                        enKisaYol = yol;
                        this.hedefKareIndeks = i;
                    }
                }
            }
        }

        this.mevcutHedefVarMi = true;
        this.hedefYol = enKisaYol;
        this.hedefkare = enKisaYol.get(enKisaYol.size() - 1);

        for (Kare kare : harita.altinOlanKareler) {
            if (kare.koordinatX == this.hedefkare.x && kare.koordinatY == this.hedefkare.y) {
                this.hedefAltin = kare;
                break;
            }
        }

        this.hedefYol.remove(0); // üstünde durduğu node'u sildik.
        //System.out.println(this.hedefYol.get(this.hedefYol.size()-1).g);

    }
    
}
