package com.ozgeburak.yazilimlaboratuvari1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class OyuncuD extends Oyuncu {
    
  OyuncuD(int koordinatX, int koordinatY){
    super(koordinatX,koordinatY);
    this.r = 255;
    this.g = 255;
    this.b = 0;
    
            try {
            this.resim = ImageIO.read(new File("kaynaklar/oyuncuD.png"));
        } catch (IOException ex) {
            Logger.getLogger(OyuncuA.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  
    void sezgiselMaaliyetliHedefBelirle(Harita harita, Oyuncu oyuncuA, Oyuncu oyuncuB, Oyuncu oyuncuC) {
        boolean Adanonce = false;
        boolean Bdenonce = false;
        boolean Cdenonce = false;

        AStar as;
        //D aramasını özel bir listede yapacak
        //bu listeye haritadaki tüm kareleri koyacağız daha sonra a,b ve c nin hedeflerine onlardan daha önce gidip gidemeyecegine bakacagiz
        //eger onlardan once gidemiyorsa bunlari listeden cikartip daha sonra normal maaliyetli aramayı yaptıracagiz.

        //A'nın hedefine daha önce gidebiliyor mu?
        as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
        List<AStar.Node> Ayaolanyol = as.findPathTo(oyuncuA.hedefkare.x, oyuncuA.hedefkare.y);

        //Yolun uzakligina degil daha az hamlede yiyip yiyemedigine bakmam gerek
        int Ayaolanuzaklik = (int) Ayaolanyol.get(Ayaolanyol.size() - 1).g;
        int AkacHamle = Ayaolanuzaklik / 3; // hamledeki adim sayisina bolerek kac hamlede gidecegini buluyoruz.

        //Ayni hesabi A icin de yapicaz
        int Aicinuzaklik = (int) oyuncuA.hedefYol.get(oyuncuA.hedefYol.size() - 1).g;
        int AicinkacHamle = Aicinuzaklik / 3;

        if (AkacHamle <= AicinkacHamle) {
            Adanonce = true;
        }

        //B'nin hedefine daha önce gidebiliyor mu?
        as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
        List<AStar.Node> Byeolanyol = as.findPathTo(oyuncuB.hedefkare.x, oyuncuB.hedefkare.y);

        int Byeolanuzaklik = (int) Byeolanyol.get(Byeolanyol.size() - 1).g;
        int BkacHamle = Byeolanuzaklik / 3; // hamledeki adim sayisina bolerek kac hamlede gidecegini buluyoruz.

        //Ayni hesabi A icin de yapicaz
        int Bicinuzaklik = (int) oyuncuB.hedefYol.get(oyuncuB.hedefYol.size() - 1).g;
        int BicinkacHamle = Bicinuzaklik / 3;

        if (BkacHamle <= BicinkacHamle) {
            Bdenonce = true;
        }

        as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
        List<AStar.Node> Cyeolanyol = as.findPathTo(oyuncuC.hedefkare.x, oyuncuC.hedefkare.y);

        int Cyeolanuzaklik = (int) Cyeolanyol.get(Cyeolanyol.size() - 1).g;
        int CkacHamle = Cyeolanuzaklik / 3; // hamledeki adim sayisina bolerek kac hamlede gidecegini buluyoruz.

        //Ayni hesabi A icin de yapicaz
        int Cicinuzaklik = (int) oyuncuC.hedefYol.get(oyuncuC.hedefYol.size() - 1).g;
        int CicinkacHamle = Cicinuzaklik / 3;

        if (CkacHamle <= CicinkacHamle) {
            Cdenonce = true;
        }

        ArrayList<Kare> Dozelkareler = (ArrayList<Kare>) harita.kareler.clone();

        if (Adanonce == false) {
            Dozelkareler.remove(oyuncuA.hedefAltin);
        }

        if (Bdenonce == false) {
            Dozelkareler.remove(oyuncuB.hedefAltin);
        }

        if (Cdenonce == false) {
            Dozelkareler.remove(oyuncuC.hedefAltin);
        }

        //C'nin hedefine daha önce gidebiliyor mu?
        //En kısa nesneyi belirleyeceğiz
        as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
        List<AStar.Node> enKisaYol = null;
        Kare maaliyetAlinacakKare = null;
        int kar = 0;
        for (Kare kare : Dozelkareler) {
            if (kare.altin == true) {
                enKisaYol = as.findPathTo(kare.koordinatX, kare.koordinatY);
                maaliyetAlinacakKare = kare;
                break;
            }
        }
        this.hedefKareIndeks = 0;

        //Altin olan kareler yerine direkt karelerde altin varsa diye bakarsak
        // .g bize yolun uzunlugunu veriyor 
        // 5 adımda gidilecekse .g = 5
        // biz 3 adim 3 adim ilerliyoruz her hamlede belli bir maaliyetimiz var
        // 3/3 = 1 4/3 = 2 5/3 = 2 6/3 = 2 
        // g'yi hamledeki adim sayisina bölüp üste yuvarlayacağız
        // g / hamledeki adim sayisi * hamle maaliyeti - hedefteki altin miktari
        // üsteki formül bize karli hamleyi verecek ve bunlari kiyaslicaz
        for (int i = 0; i < Dozelkareler.size(); i++) {
            if (Dozelkareler.get(i).altin == true) {
                as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                List<AStar.Node> yol = as.findPathTo(Dozelkareler.get(i).koordinatX, Dozelkareler.get(i).koordinatY);
                if (yol != null) {
                    //En kisa bulma kismi artik farkli olacak
                    if (((int) Math.ceil((yol.get(yol.size() - 1).g / 3.0)) * 5) - Dozelkareler.get(i).altinMiktari
                            < (((int) Math.ceil(enKisaYol.get(enKisaYol.size() - 1).g / 3.0)) * 5) - maaliyetAlinacakKare.altinMiktari) {
                        enKisaYol = yol;
                        maaliyetAlinacakKare = Dozelkareler.get(i);
                        kar = ((int) Math.ceil((yol.get(yol.size() - 1).g / 3.0)) * 5) - Dozelkareler.get(i).altinMiktari;
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
        //System.out.println((int)kar);
    }

}
