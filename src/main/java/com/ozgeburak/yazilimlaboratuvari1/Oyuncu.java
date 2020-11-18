package com.ozgeburak.yazilimlaboratuvari1;

import com.ozgeburak.yazilimlaboratuvari1.AStar;
import com.ozgeburak.yazilimlaboratuvari1.Dugum;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Oyuncu {

    BufferedImage resim;
    int koordinatX;
    int koordinatY;
    int pozisyonX;
    int pozisyonY;
    int genislik;
    int yukseklik;
    int r;
    int g;
    int b;
    int altin;
    boolean mevcutHedefVarMi = false;
    int kalanHareket;
    Dugum hedefkare;
    List<Dugum> hedefYol;
    int hedefKareIndeks;
    Kare hedefAltin;
    boolean yasiyor = true;

    int harcananAltin;
    int toplananAltin;
    int adimSayisi;

    Oyuncu(int koordinatX, int koordinatY) {
        this.koordinatX = koordinatX;
        this.koordinatY = koordinatY;
        this.genislik = 128;
        this.yukseklik = 128;
        this.pozisyonX = koordinatX * genislik;
        this.pozisyonY = koordinatY * yukseklik;
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.altin = Sabitler.OYUNCU_ALTIN_MIKTARI;
        this.kalanHareket = Sabitler.HAMLE_ADIM_SAYISI;

        this.hedefkare = null;
        this.hedefYol = null;
        this.hedefKareIndeks = -1;
        this.hedefAltin = null;

        //Oyun Sonu Degerleri
        //this.altin -> kasadaki altin miktari
        this.harcananAltin = 0;
        this.toplananAltin = 0;
        this.adimSayisi = 0;
    }

    void Cizdir(Graphics2D G) {
        G.drawImage(this.resim, koordinatX * genislik, koordinatY * yukseklik, null);

    }

    void HedefCizdir(Graphics2D G) {

        if (this.hedefkare != null) {
            G.setColor(new Color(r, g, b));
            G.setStroke(new BasicStroke(5.0f));
            G.drawRect(this.hedefkare.x * genislik, this.hedefkare.y * yukseklik, genislik, yukseklik);
            G.setStroke(new BasicStroke(1f));
        }

    }

    void YolCizdir(Graphics2D G) {

        if (this.hedefYol != null) {
            for (int i = 0; i < this.hedefYol.size(); i++) {
                G.setColor(new Color(r, g, b));
                G.fillOval(this.hedefYol.get(i).x * genislik + genislik / 2 - genislik / 12, this.hedefYol.get(i).y * yukseklik + yukseklik / 2 - yukseklik / 12, genislik / 6, yukseklik / 6);
            }
        }

    }

    //B ve C Oyuncuları Ortak Kullanıyor.
    void maaliyetliHedefBelirle(Harita harita, String oyuncu, int hamleMaaliyeti, int hedefBelirlemeMaaliyeti) throws IOException {
        if (this.altin < hedefBelirlemeMaaliyeti) {
            this.yasiyor = false;
            return;
        }

        if (this.mevcutHedefVarMi == false && this.hedefkare == null) {
            if (altinKaldiMiKontrol(harita) == false) {
                return;
            }

            int enKisaIndex = 0;
            Kare maaliyetAlinacakKare = harita.altinOlanKareler.get(0);
            AStar as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY);
            List<Dugum> enKisaYol = null;
            int kar;

            //Altin olan kareler yerine direkt karelerde altin varsa diye bakarsak
            // .g bize yolun uzunlugunu veriyor 
            // 5 adımda gidilecekse .g = 5
            // biz 3 adim 3 adim ilerliyoruz her hamlede belli bir maaliyetimiz var
            // 3/3 = 1 4/3 = 2 5/3 = 2 6/3 = 2 
            // g'yi hamledeki adim sayisina bölüp üste yuvarlayacağız
            // g / hamledeki adim sayisi * hamle maaliyeti - hedefteki altin miktari
            // üsteki formül bize karli hamleyi verecek ve bunlari kiyaslicaz
            for (int i = 0; i < harita.altinOlanKareler.size(); i++) {

                if (((int) Math.ceil((manhattanUzaklik(this.koordinatX, this.koordinatY, harita.altinOlanKareler.get(i).koordinatX, harita.altinOlanKareler.get(i).koordinatY) / (float) Sabitler.HAMLE_ADIM_SAYISI)) * hamleMaaliyeti) - harita.altinOlanKareler.get(i).altinMiktari
                        < (((int) Math.ceil(manhattanUzaklik(this.koordinatX, this.koordinatY, harita.altinOlanKareler.get(enKisaIndex).koordinatX, harita.altinOlanKareler.get(enKisaIndex).koordinatY) / (float) Sabitler.HAMLE_ADIM_SAYISI)) * hamleMaaliyeti) - maaliyetAlinacakKare.altinMiktari) {
                    enKisaIndex = i;
                    maaliyetAlinacakKare = harita.altinOlanKareler.get(i);
                    //kar = ((int) Math.ceil((yol.get(yol.size() - 1).g / (float) Sabitler.HAMLE_ADIM_SAYISI)) * hamleMaaliyeti) - harita.altinOlanKareler.get(i).altinMiktari;
                    this.hedefKareIndeks = i;
                } else if (((int) Math.ceil((manhattanUzaklik(this.koordinatX, this.koordinatY, harita.altinOlanKareler.get(i).koordinatX, harita.altinOlanKareler.get(i).koordinatY) / (float) Sabitler.HAMLE_ADIM_SAYISI)) * hamleMaaliyeti) - harita.altinOlanKareler.get(i).altinMiktari
                        == (((int) Math.ceil(manhattanUzaklik(this.koordinatX, this.koordinatY, harita.altinOlanKareler.get(enKisaIndex).koordinatX, harita.altinOlanKareler.get(enKisaIndex).koordinatY) / (float) Sabitler.HAMLE_ADIM_SAYISI)) * hamleMaaliyeti) - maaliyetAlinacakKare.altinMiktari) {
                    if (manhattanUzaklik(this.koordinatX, this.koordinatY, harita.altinOlanKareler.get(i).koordinatX, harita.altinOlanKareler.get(i).koordinatY)
                            <= manhattanUzaklik(this.koordinatX, this.koordinatY, harita.altinOlanKareler.get(enKisaIndex).koordinatX, harita.altinOlanKareler.get(enKisaIndex).koordinatY)) {
                        enKisaIndex = i;
                        maaliyetAlinacakKare = harita.altinOlanKareler.get(i);
                        //kar = ((int) Math.ceil((yol.get(yol.size() - 1).g / (float) Sabitler.HAMLE_ADIM_SAYISI)) * hamleMaaliyeti) - harita.altinOlanKareler.get(i).altinMiktari;
                        this.hedefKareIndeks = i;
                    }
                }

            }

            enKisaYol = as.yolBul(harita.altinOlanKareler.get(enKisaIndex).koordinatX, harita.altinOlanKareler.get(enKisaIndex).koordinatY);

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
            this.altin -= hedefBelirlemeMaaliyeti;
            this.harcananAltin += hedefBelirlemeMaaliyeti;

            if (oyuncu == "B" && this.hedefkare != null && this.hedefYol != null) {
                Oyun.fwOyuncuB.write("En karlı hedef belirlendi. Hedef kare x: " + this.hedefkare.x + " y: " + this.hedefkare.y + "\n");
                Oyun.fwOyuncuB.write("Hedefin uzakligi: " + this.hedefYol.get(this.hedefYol.size() - 1).g + " hedefteki altin miktari: " + this.hedefAltin.altinMiktari + "\n");
                Oyun.fwOyuncuB.write("Hedef Belirleme Maaliyeti: " + Sabitler.OYUNCU_B_HEDEF_BELIRLEME_MAALIYET + " kalan altin: " + Integer.toString(this.altin) + "\n");
            } else if (oyuncu == "C" && this.hedefkare != null && this.hedefYol != null) {
                Oyun.fwOyuncuC.write("En karlı hedef belirlendi. Hedef kare x: " + this.hedefkare.x + " y: " + this.hedefkare.y + "\n");

                Oyun.fwOyuncuC.write("Hedefin uzakligi: " + this.hedefYol.get(this.hedefYol.size() - 1).g + " hedefteki altin miktari: " + this.hedefAltin.altinMiktari + "\n");
                Oyun.fwOyuncuC.write("Hedef Belirleme Maaliyeti: " + Sabitler.OYUNCU_C_HEDEF_BELIRLEME_MAALIYET + " kalan altin: " + Integer.toString(this.altin) + "\n");
            }
        }
    }

    boolean YasiyorMu() {
        if (this.altin > 0) {
            return true;
        }

        return false;
    }

    boolean olumKontrol() {
        if (YasiyorMu() == false) {
            yasiyor = false;

            return true;
        }

        return false;
    }

    boolean altinKaldiMiKontrol(Harita harita) {
        if (harita.altinOlanKareler.size() <= 0) {
            return false;
        }

        return true;
    }

    int manhattanUzaklik(int x1, int y1, int x2, int y2) {
        return (int) (Math.abs(x1 - x2) + Math.abs(y1 - y2));
    }

}
