package com.ozgeburak.yazilimlaboratuvari1;

import static com.ozgeburak.yazilimlaboratuvari1.Oyun.fwOyuncuA;
import com.ozgeburak.yazilimlaboratuvari1.AStar;
import com.ozgeburak.yazilimlaboratuvari1.Dugum;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class OyuncuA extends Oyuncu {

    OyuncuA(int koordinatX, int koordinatY) {
        super(koordinatX, koordinatY);
        this.r = 255;
        this.g = 0;
        this.b = 0;

        try {
            this.resim = ImageIO.read(new File("kaynaklar/oyuncuA.png"));
        } catch (IOException ex) {
            Logger.getLogger(OyuncuA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void hedefBelirle(Harita harita) throws IOException {
        if(this.altin < Sabitler.OYUNCU_A_HEDEF_BELIRLEME_MAALIYET){
            this.yasiyor = false;
            return;
        }
        
        //if (this.mevcutHedefVarMi == false && this.hedefkare == null) {
            if (altinKaldiMiKontrol(harita) == false) {
                return;
            }
            
            int enKisaIndex = 0;

            
            //Önce en kısa altini bulup daha sonra en kisa altina astar yapcak
            
            for(int i = 0; i < harita.altinOlanKareler.size(); i++){
                if(manhattanUzaklik(this.koordinatX, this.koordinatY, harita.altinOlanKareler.get(i).koordinatX, harita.altinOlanKareler.get(i).koordinatY) <=
                        manhattanUzaklik(this.koordinatX, this.koordinatY, harita.altinOlanKareler.get(enKisaIndex).koordinatX, harita.altinOlanKareler.get(enKisaIndex).koordinatY)){
                    enKisaIndex = i;
                }
            }
            
            AStar as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY);
            List<Dugum> enKisaYol = as.yolBul(harita.altinOlanKareler.get(enKisaIndex).koordinatX,harita.altinOlanKareler.get(enKisaIndex).koordinatY);
            
            
           

            this.mevcutHedefVarMi = true;
            this.hedefYol = enKisaYol;
            this.hedefkare = enKisaYol.get(enKisaYol.size() - 1);

            for (Kare kare : harita.altinOlanKareler) {
                if (kare.koordinatX == this.hedefkare.x && kare.koordinatY == this.hedefkare.y) {
                    this.hedefAltin = kare;
                    break;
                }
            }

            Oyun.fwOyuncuA.write("En yakın hedef belirlendi. Hedef kare x: " + this.hedefkare.x + " y: " + this.hedefkare.y + "\n");
            Oyun.fwOyuncuA.write("hedefteki altin miktari: " + this.hedefAltin.altinMiktari + "\n");
            this.altin -= Sabitler.OYUNCU_A_HEDEF_BELIRLEME_MAALIYET;
            this.harcananAltin += Sabitler.OYUNCU_A_HEDEF_BELIRLEME_MAALIYET;
            Oyun.fwOyuncuA.write("Hedef Belirleme Maaliyeti: " + Sabitler.OYUNCU_A_HEDEF_BELIRLEME_MAALIYET + " kalan altin: " + Integer.toString(this.altin) + "\n");
            this.hedefYol.remove(0); // üstünde durduğu node'u sildik.
            //System.out.println(this.hedefYol.get(this.hedefYol.size()-1).g);

        //}

    }

}
