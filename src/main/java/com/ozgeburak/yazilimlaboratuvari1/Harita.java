package com.ozgeburak.yazilimlaboratuvari1;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class Harita {
  ArrayList<Kare> kareler;
  int yatayKareSayisi;
  int dikeyKareSayisi;
  int toplamKareSayisi;
  java.util.Random random;
  int altinSayisi;
  int gizliAltinSayisi;
  
  int maaliyetsizMatris[][];
  
  ArrayList<Kare> altinOlanKareler;
  
  
  public Harita(int yatayKareSayisi, int dikeyKareSayisi){
    maaliyetsizMatris = new int[yatayKareSayisi][dikeyKareSayisi];
    for(int i = 0; i < yatayKareSayisi; i++){
        for(int j = 0; j < dikeyKareSayisi; j++){
            maaliyetsizMatris[i][j] = 0;
        }
    }
    
    this.yatayKareSayisi = yatayKareSayisi;
    this.dikeyKareSayisi = dikeyKareSayisi;
    this.toplamKareSayisi = yatayKareSayisi * dikeyKareSayisi;
    this.kareler = new ArrayList<Kare>();
    this.altinOlanKareler = new ArrayList<Kare>();
    
    
    
    random = new java.util.Random();
    Olustur();
    //4 adet ozel baslangic karesi var altin spawnlanmayacak onlari isaretliyoruz
    KareBul(0,0).baslangicKaresi = true;
    KareBul(yatayKareSayisi-1, 0).baslangicKaresi = true;
    KareBul(0, dikeyKareSayisi-1).baslangicKaresi = true;
    KareBul(yatayKareSayisi-1, dikeyKareSayisi-1).baslangicKaresi = true;
    AltinlariYerlestir();
    
  }
  
  void Olustur(){
    for(int i = 0; i < dikeyKareSayisi; i++){
      for(int j = 0; j < yatayKareSayisi; j++){
        kareler.add(new Kare(i, j));
      }
    }
  }
  
  void AltinlariYerlestir(){
    altinSayisi = 0;
    gizliAltinSayisi = 0;
    
    //Karelerin %20 sine Altin Yerlestiricez.
    //Altinlari ve miktarlarini yerlestiriyoruz
    for(int i = 0; i < toplamKareSayisi * 0.2; i++){
      int secilenKare = random.nextInt(toplamKareSayisi);
      
      if(kareler.get(secilenKare).altin == false && kareler.get(secilenKare).baslangicKaresi == false){
        kareler.get(secilenKare).altin = true;
        kareler.get(secilenKare).altinMiktari = (1+random.nextInt(4)) * 5;
        altinSayisi++;
      }else{
        //Eğer karede bir altin var ise
        while(kareler.get(secilenKare).altin == true || kareler.get(secilenKare).baslangicKaresi == true){
          secilenKare = random.nextInt(toplamKareSayisi);
          if(kareler.get(secilenKare).altin == false && kareler.get(secilenKare).baslangicKaresi == false){
            kareler.get(secilenKare).altin = true;
            kareler.get(secilenKare).altinMiktari = (1+random.nextInt(4)) * 5;
            altinSayisi++;
            break;
          }
        }
      }    
    }
    
    
    //Altin olan karelerinde %10 unu gizli altina ceviriyoruz
    //Altinlarin %10 unda gizli Altin bulunacak
    for(int i = 0; i < altinSayisi * 0.1; i++){
      int secilenKare = random.nextInt(toplamKareSayisi);
      
      if(kareler.get(secilenKare).altin == true){
        kareler.get(secilenKare).altin = false;
        altinSayisi--;
        kareler.get(secilenKare).gizliAltin = true;
        gizliAltinSayisi++;
      }else{
        //Eğer karede altin yoksa
        while(kareler.get(secilenKare).altin == false){
          secilenKare = random.nextInt(toplamKareSayisi);
          if(kareler.get(secilenKare).altin == true){
            kareler.get(secilenKare).altin = false;
            altinSayisi--;
            kareler.get(secilenKare).gizliAltin = true;
            gizliAltinSayisi++;
          break;
          }
        }
      }
    }
    
    
    for(Kare kare : kareler){
        if(kare.altin == true){
            altinOlanKareler.add(kare);
        }
    }
    
    //println(altinSayisi);
    //println(gizliAltinSayisi);
  }
  
  
    void Cizdir(Graphics2D g){
    for(Kare kare : kareler){
      kare.Cizdir(g);
      
    }
    }
    
    void altinCizdir(Graphics2D g){
    for(Kare kare : kareler){
      kare.altinCizdir(g);
      
      
    }
    
    
  }
  
  
  Kare KareBul(int koordinatX, int koordinatY){
    return kareler.get(koordinatY * yatayKareSayisi + koordinatX);
  }
  
  
}
