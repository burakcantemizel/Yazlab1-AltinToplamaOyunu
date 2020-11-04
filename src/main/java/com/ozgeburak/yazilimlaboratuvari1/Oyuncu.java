package com.ozgeburak.yazilimlaboratuvari1;

import com.ozgeburak.yazilimlaboratuvari1.AStar.Node;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;


public class Oyuncu {
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
  Node hedefkare;
  List<Node> hedefYol;
  int hedefKareIndeks;
  
  Oyuncu(int koordinatX, int koordinatY){
    this.koordinatX = koordinatX;
    this.koordinatY = koordinatY;
    this.genislik = 32;
    this.yukseklik = 32;
    this.pozisyonX = koordinatX * genislik;
    this.pozisyonY = koordinatY * yukseklik;
    this.r = 0;
    this.g = 0;
    this.b = 0;
    this.altin = 200;
    this.kalanHareket = 3;
    this.hedefkare = null;
    this.hedefYol = null;
    this.hedefKareIndeks = -1;
  }
  
  void Cizdir(Graphics2D G){
      G.setColor(new Color(r,g,b));
      G.fillRect(koordinatX * genislik, koordinatY * yukseklik, genislik, yukseklik);
      G.setColor(Color.BLACK);
      G.drawRect(koordinatX * genislik, koordinatY * yukseklik, genislik, yukseklik);
  }
  
  void HedefCizdir(Graphics2D G){
      
      if(this.hedefkare != null){
      G.setColor(new Color(r,g,b));
      G.setStroke(new BasicStroke(5.0f));
      G.drawRect(this.hedefkare.x * genislik, this.hedefkare.y * yukseklik, genislik, yukseklik);
      G.setStroke(new BasicStroke(1f));
      }
      
  }
  
  void YolCizdir(Graphics2D G){
      if(this.hedefYol != null){
          for(int i = 0; i < this.hedefYol.size(); i++){
              G.setColor(new Color(r,g,b));
              G.fillOval(this.hedefYol.get(i).x * genislik + genislik/2 - genislik/12, this.hedefYol.get(i).y * yukseklik + yukseklik/2 - yukseklik/12, genislik/6, yukseklik/6);
          }
      }
  }
  
  
  void hedefBelirle(Harita harita){
  //En kısa nesneyi belirleyeceğiz
                AStar as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                List<Node> enKisaYol = as.findPathTo(harita.altinOlanKareler.get(0).koordinatX, harita.altinOlanKareler.get(0).koordinatY);
                this.hedefKareIndeks = 0;
                for(int i = 0; i < harita.altinOlanKareler.size(); i++){
                    as = new AStar(harita.maaliyetsizMatris,this.koordinatX, this.koordinatY,false);

                    //Altın olan tüm kareleri geziyoruz ve oraya olan uzakligi buluyoruz.
                    List<Node> yol = as.findPathTo(harita.altinOlanKareler.get(i).koordinatX, harita.altinOlanKareler.get(i).koordinatY);
                    if(yol != null){
                      if(yol.get(yol.size()-1).g < enKisaYol.get(enKisaYol.size()-1).g){
                        enKisaYol = yol;
                        this.hedefKareIndeks = i;
                      }
                    }
                    //Burda en kisa yolu bulduk. artik En Kisa Yol bize en kisa yol bilgisini veriyor
                }
                
                this.mevcutHedefVarMi = true;
                this.hedefYol = enKisaYol;
                this.hedefkare = enKisaYol.get(enKisaYol.size()-1);
                this.hedefYol.remove(0); // üstünde durduğu node'u sildik.
  }
  
  
  void maaliyetliHedefBelirle(Harita harita){
      //Sadece altin olan karelere bakacak gizli altinlari göremeyecek
      AStar as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
      List<Node> enKisaYol = as.findPathTo(harita.altinOlanKareler.get(0).koordinatX, harita.altinOlanKareler.get(0).koordinatY);
      this.hedefKareIndeks = 0;
      for(int i = 0; i < harita.altinOlanKareler.size(); i++){
          as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
          
          List<Node> yol = as.findPathTo(harita.altinOlanKareler.get(i).koordinatX, harita.altinOlanKareler.get(i).koordinatY);
          if(yol != null){
              if(yol.get(yol.size()-1).g < enKisaYol.get(enKisaYol.size()-1).g){
                  enKisaYol = yol;
                  this.hedefKareIndeks = i;
              }
          }
      }
      
      this.mevcutHedefVarMi = true;
      this.hedefYol = enKisaYol;
      this.hedefkare = enKisaYol.get(enKisaYol.size()-1);
      this.hedefYol.remove(0);
  }
  
}
