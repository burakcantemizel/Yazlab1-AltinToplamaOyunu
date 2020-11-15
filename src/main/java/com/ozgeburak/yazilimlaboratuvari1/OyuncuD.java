package com.ozgeburak.yazilimlaboratuvari1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class OyuncuD extends Oyuncu {

    OyuncuD(int koordinatX, int koordinatY) {
        super(koordinatX, koordinatY);
        this.r = 255;
        this.g = 255;
        this.b = 0;

        try {
            this.resim = ImageIO.read(new File("kaynaklar/oyuncuD.png"));
        } catch (IOException ex) {
            Logger.getLogger(OyuncuA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void sezgiselMaaliyetliHedefBelirle(Harita harita, Oyuncu oyuncuA, Oyuncu oyuncuB, Oyuncu oyuncuC) throws IOException {
        if (this.mevcutHedefVarMi == false && this.hedefkare == null) {

            boolean Adanonce = false;
            boolean Bdenonce = false;
            boolean Cdenonce = false;

            AStar as;
            //D aramasını özel bir listede yapacak
            //bu listeye haritadaki tüm kareleri koyacağız daha sonra a,b ve c nin hedeflerine onlardan daha önce gidip gidemeyecegine bakacagiz
            //eger onlardan once gidemiyorsa bunlari listeden cikartip daha sonra normal maaliyetli aramayı yaptıracagiz.

            if (oyuncuA.yasiyor == true) {
                //A'nın hedefine daha önce gidebiliyor mu?
                as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                List<AStar.Dugum> Ayaolanyol = as.findPathTo(oyuncuA.hedefkare.x, oyuncuA.hedefkare.y);

                //Yolun uzakligina degil daha az hamlede yiyip yiyemedigine bakmam gerek
                int Ayaolanuzaklik = (int) Ayaolanyol.get(Ayaolanyol.size() - 1).g;
                int AkacHamle = Ayaolanuzaklik / 3; // hamledeki adim sayisina bolerek kac hamlede gidecegini buluyoruz.

                //Ayni hesabi A icin de yapicaz
                int Aicinuzaklik = (int) oyuncuA.hedefYol.get(oyuncuA.hedefYol.size() - 1).g;
                int AicinkacHamle = Aicinuzaklik / 3;

                if (AkacHamle <= AicinkacHamle) {
                    Adanonce = true;
                }
            }

            if (oyuncuB.yasiyor == true) {
                //B'nin hedefine daha önce gidebiliyor mu?
                as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                List<AStar.Dugum> Byeolanyol = as.findPathTo(oyuncuB.hedefkare.x, oyuncuB.hedefkare.y);

                int Byeolanuzaklik = (int) Byeolanyol.get(Byeolanyol.size() - 1).g;
                int BkacHamle = Byeolanuzaklik / 3; // hamledeki adim sayisina bolerek kac hamlede gidecegini buluyoruz.

                //Ayni hesabi A icin de yapicaz
                int Bicinuzaklik = (int) oyuncuB.hedefYol.get(oyuncuB.hedefYol.size() - 1).g;
                int BicinkacHamle = Bicinuzaklik / 3;

                if (BkacHamle <= BicinkacHamle) {
                    Bdenonce = true;
                }
            }

            if (oyuncuC.yasiyor == true) {
                as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                List<AStar.Dugum> Cyeolanyol = as.findPathTo(oyuncuC.hedefkare.x, oyuncuC.hedefkare.y);

                int Cyeolanuzaklik = (int) Cyeolanyol.get(Cyeolanyol.size() - 1).g;
                int CkacHamle = Cyeolanuzaklik / 3; // hamledeki adim sayisina bolerek kac hamlede gidecegini buluyoruz.

                //Ayni hesabi A icin de yapicaz
                int Cicinuzaklik = (int) oyuncuC.hedefYol.get(oyuncuC.hedefYol.size() - 1).g;
                int CicinkacHamle = Cicinuzaklik / 3;

                if (CkacHamle <= CicinkacHamle) {
                    Cdenonce = true;
                }
            }

            ArrayList<Kare> Dozelkareler = (ArrayList<Kare>) harita.kareler.clone();

            if (Adanonce == false && oyuncuA.yasiyor == true) {
                Dozelkareler.remove(oyuncuA.hedefAltin);
            }

            if (Bdenonce == false && oyuncuB.yasiyor == true) {
                Dozelkareler.remove(oyuncuB.hedefAltin);
            }

            if (Cdenonce == false && oyuncuC.yasiyor == true) {
                Dozelkareler.remove(oyuncuC.hedefAltin);
            }

            int kalanaltin = 0;

            for (Kare kare : Dozelkareler) {
                if (kare.altin == true) {
                    kalanaltin++;
                }
            }

            if (kalanaltin <= 0) {
                return;
            }

            if (kalanaltin == 1 && Adanonce == false && Bdenonce == false && Cdenonce) {
                return;
            }

            //C'nin hedefine daha önce gidebiliyor mu?
            //En kısa nesneyi belirleyeceğiz
            as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
            List<AStar.Dugum> enKisaYol = null;
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
                    List<AStar.Dugum> yol = as.findPathTo(Dozelkareler.get(i).koordinatX, Dozelkareler.get(i).koordinatY);
                    if (yol != null) {
                        //En kisa bulma kismi artik farkli olacak
                        if (((int) Math.ceil((yol.get(yol.size() - 1).g / (float) Sabitler.HAMLE_ADIM_SAYISI)) * Sabitler.OYUNCU_D_HAMLE_MAALIYET) - Dozelkareler.get(i).altinMiktari
                                < (((int) Math.ceil(enKisaYol.get(enKisaYol.size() - 1).g / (float) Sabitler.HAMLE_ADIM_SAYISI)) * Sabitler.OYUNCU_D_HAMLE_MAALIYET) - maaliyetAlinacakKare.altinMiktari) {
                            enKisaYol = yol;
                            maaliyetAlinacakKare = Dozelkareler.get(i);
                            kar = ((int) Math.ceil((yol.get(yol.size() - 1).g / (float) Sabitler.HAMLE_ADIM_SAYISI)) * Sabitler.OYUNCU_D_HAMLE_MAALIYET) - Dozelkareler.get(i).altinMiktari;
                            this.hedefKareIndeks = i;
                        } else if (((int) Math.ceil((yol.get(yol.size() - 1).g / (float) Sabitler.HAMLE_ADIM_SAYISI)) * Sabitler.OYUNCU_D_HAMLE_MAALIYET) - Dozelkareler.get(i).altinMiktari
                                == (((int) Math.ceil(enKisaYol.get(enKisaYol.size() - 1).g / (float) Sabitler.HAMLE_ADIM_SAYISI)) * Sabitler.OYUNCU_D_HAMLE_MAALIYET) - maaliyetAlinacakKare.altinMiktari) {
                            if (yol.get(yol.size() - 1).g <= enKisaYol.get(enKisaYol.size() - 1).g) {
                                enKisaYol = yol;
                                maaliyetAlinacakKare = Dozelkareler.get(i);
                                kar = ((int) Math.ceil((yol.get(yol.size() - 1).g / (float) Sabitler.HAMLE_ADIM_SAYISI)) * Sabitler.OYUNCU_D_HAMLE_MAALIYET) - Dozelkareler.get(i).altinMiktari;
                                this.hedefKareIndeks = i;
                            }
                        }
                    }
                }
            }

            this.mevcutHedefVarMi = true;
            this.hedefYol = enKisaYol;
            this.hedefkare = enKisaYol.get(enKisaYol.size() - 1);

            for (Kare kare : harita.kareler) {
                if (kare.altin == true && kare.koordinatX == this.hedefkare.x && kare.koordinatY == this.hedefkare.y) {
                    this.hedefAltin = kare;
                    break;
                }
            }

            this.hedefYol.remove(0); // üstünde durduğu node'u sildik.
            //System.out.println((int)kar);
            
            this.altin -= Sabitler.OYUNCU_D_HEDEF_BELIRLEME_MAALIYET;
            Oyun.fwOyuncuD.write("En karlı hedef belirlendi. Hedef kare x: " + this.hedefkare.x + " y: " + this.hedefkare.y + "\n");
            Oyun.fwOyuncuD.write("Hedefin uzakligi: " + this.hedefYol.get(this.hedefYol.size() - 1).g + " hedefteki altin miktari: " + this.hedefAltin.altinMiktari + "\n");
            Oyun.fwOyuncuD.write("Hedef Belirleme Maaliyeti: " + Sabitler.OYUNCU_D_HEDEF_BELIRLEME_MAALIYET + " kalan altin: " + Integer.toString(this.altin) + "\n");
        }
    }

}
