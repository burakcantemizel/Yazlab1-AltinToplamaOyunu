package com.ozgeburak.yazilimlaboratuvari1;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

//Harita Sınıfı Oyun Alanını Olusturan Sınıf
public class Harita {
    
    //Değişken tanımlamaları
    ArrayList<Kare> kareler;
    int yatayKareSayisi;
    int dikeyKareSayisi;
    int toplamKareSayisi;
    java.util.Random random;
    int altinSayisi;
    int gizliAltinSayisi;
    int maaliyetsizMatris[][];
    ArrayList<Kare> altinOlanKareler;
    ArrayList<Kare> gizliAltinOlanKareler;

    
    public Harita(int yatayKareSayisi, int dikeyKareSayisi) {
        
        //Bu boş maaliyetsiz matris astar algoritmasındaki yol bilgisini
        //almak için kullanılıyor.
        maaliyetsizMatris = new int[dikeyKareSayisi][yatayKareSayisi];
        for (int i = 0; i < dikeyKareSayisi; i++) {
            for (int j = 0; j < yatayKareSayisi; j++) {
                maaliyetsizMatris[i][j] = 0;
            }
        }

        this.yatayKareSayisi = yatayKareSayisi;
        this.dikeyKareSayisi = dikeyKareSayisi;
        this.toplamKareSayisi = yatayKareSayisi * dikeyKareSayisi;
        this.kareler = new ArrayList<Kare>();
        this.altinOlanKareler = new ArrayList<Kare>();
        this.gizliAltinOlanKareler = new ArrayList<Kare>();  
        random = new java.util.Random();
        
        //Kare Nesneleri Olusturuluyor.
        Olustur();
        
        //4 adet ozel baslangic karesi var altin spawnlanmayacak onlari isaretliyoruz
        KareBul(0, 0).baslangicKaresi = true;
        KareBul(yatayKareSayisi - 1, 0).baslangicKaresi = true;
        KareBul(0, dikeyKareSayisi - 1).baslangicKaresi = true;
        KareBul(yatayKareSayisi - 1, dikeyKareSayisi - 1).baslangicKaresi = true;
        AltinlariYerlestir();

    }

    //Haritadaki tum kareleri olusuturoyuz ve haritaya ekliyoruz.
    void Olustur() {
        for (int i = 0; i < yatayKareSayisi; i++) {
            for (int j = 0; j < dikeyKareSayisi; j++) {
                kareler.add(new Kare(i, j));
            }
        }
    }

    //Altinlari Yerlestiren Fonksiyon
    void AltinlariYerlestir() {
        altinSayisi = 0;
        gizliAltinSayisi = 0;
        
        float tmpAltinFor = (float)toplamKareSayisi * ((float) Sabitler.ALTIN_ORANI / 100.0f );
        if(Sabitler.ALTIN_ORANI == 100){
            tmpAltinFor = toplamKareSayisi-4;
        }

        //Karelerin %x sine Altin Yerlestiricez.
        //Altinlari ve miktarlarini yerlestiriyoruz
        for (float i = 0; i < tmpAltinFor ; i++) {
            int secilenKare = random.nextInt(toplamKareSayisi);

            if (kareler.get(secilenKare).altin == false && kareler.get(secilenKare).baslangicKaresi == false) {
                kareler.get(secilenKare).altin = true;
                kareler.get(secilenKare).altinMiktari = (1 + random.nextInt(4)) * 5;
                altinSayisi++;
            } else {
                //Eğer karede bir altin var ise
                while (kareler.get(secilenKare).altin == true || kareler.get(secilenKare).baslangicKaresi == true) {
                    secilenKare = random.nextInt(toplamKareSayisi);
                    if (kareler.get(secilenKare).altin == false && kareler.get(secilenKare).baslangicKaresi == false) {
                        kareler.get(secilenKare).altin = true;
                        kareler.get(secilenKare).altinMiktari = (1 + random.nextInt(4)) * 5;
                        altinSayisi++;
                        break;
                    }
                }
            }
        }

        //Altin olan karelerinde %x unu gizli altina ceviriyoruz
        //Altinlarin %x unda gizli Altin bulunacak
        float tmpGizliAltinFor = (float)altinSayisi * ((float) Sabitler.GIZLI_ALTIN_ORANI / 100.0f);
        for (float i = 0; i < tmpGizliAltinFor; i++) {
            int secilenKare = random.nextInt(toplamKareSayisi);

            if (kareler.get(secilenKare).altin == true) {
                kareler.get(secilenKare).altin = false;
                altinSayisi--;
                kareler.get(secilenKare).gizliAltin = true;
                gizliAltinSayisi++;
            } else {
                //Eğer karede altin yoksa
                while (kareler.get(secilenKare).altin == false) {
                    secilenKare = random.nextInt(toplamKareSayisi);
                    if (kareler.get(secilenKare).altin == true) {
                        kareler.get(secilenKare).altin = false;
                        altinSayisi--;
                        kareler.get(secilenKare).gizliAltin = true;
                        gizliAltinSayisi++;
                        break;
                    }
                }
            }
        }

        System.out.println(gizliAltinSayisi + " " + altinSayisi);
        
        //Altın ve Gizli Altın Listeleri
        for (Kare kare : kareler) {
            if (kare.altin == true) {
                altinOlanKareler.add(kare);
            }else if(kare.gizliAltin == true){
                gizliAltinOlanKareler.add(kare);
            }
        }

    }

    //Kareleri ve altını haritaya cizdirme
    void Cizdir(Graphics2D g) {
        for (Kare kare : kareler) {
            kare.Cizdir(g);

        }
    }

    void altinCizdir(Graphics2D g) {
        for (Kare kare : kareler) {
            kare.altinCizdir(g);
        }

    }

    //Listeden ilgili kareyi donduren fonksiyon
    Kare KareBul(int koordinatX, int koordinatY) {
        return kareler.get(koordinatX * dikeyKareSayisi + koordinatY);
    }

}
