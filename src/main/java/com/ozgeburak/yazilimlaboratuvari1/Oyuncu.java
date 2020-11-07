package com.ozgeburak.yazilimlaboratuvari1;

import com.ozgeburak.yazilimlaboratuvari1.AStar.Node;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
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
  Kare hedefAltin;
  
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
    this.hedefAltin = null;
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
                List<Node> enKisaYol = null;
                for(Kare kare : harita.kareler){
                    if(kare.altin == true){
                        enKisaYol = as.findPathTo(kare.koordinatX, kare.koordinatY);
                        break;
                    }
                }
                this.hedefKareIndeks = 0;
                
                //Altin olan kareler yerine direkt karelerde altin varsa diye bakarsak
                
                for(int i = 0; i < harita.kareler.size(); i++){
                    if(harita.kareler.get(i).altin == true){
                        as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                        List<Node> yol = as.findPathTo(harita.kareler.get(i).koordinatX, harita.kareler.get(i).koordinatY);
                         if(yol != null){
                            if(yol.get(yol.size()-1).g < enKisaYol.get(enKisaYol.size()-1).g){
                              enKisaYol = yol;
                              this.hedefKareIndeks = i;
                            }
                         }
                    }
                }
                
                this.mevcutHedefVarMi = true;
                this.hedefYol = enKisaYol;
                this.hedefkare = enKisaYol.get(enKisaYol.size()-1);
                
                for(Kare kare : harita.altinOlanKareler){
                    if(kare.koordinatX == this.hedefkare.x && kare.koordinatY == this.hedefkare.y){
                        this.hedefAltin = kare;
                        break;
                    }
                }
                
                this.hedefYol.remove(0); // üstünde durduğu node'u sildik.
                //System.out.println(this.hedefYol.get(this.hedefYol.size()-1).g);
                
  }
  
  void maaliyetliHedefBelirle(Harita harita){
                //En kısa nesneyi belirleyeceğiz
                AStar as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                List<Node> enKisaYol = null;
                Kare maaliyetAlinacakKare = null;
                int kar = 0;
                for(Kare kare : harita.kareler){
                    if(kare.altin == true){
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
                
                
                for(int i = 0; i < harita.kareler.size(); i++){
                    if(harita.kareler.get(i).altin == true){
                        as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                        List<Node> yol = as.findPathTo(harita.kareler.get(i).koordinatX, harita.kareler.get(i).koordinatY);
                         if(yol != null){
                            //En kisa bulma kismi artik farkli olacak
                            if( (  (int)Math.ceil((yol.get(yol.size()-1).g / 3.0)) * 5) - harita.kareler.get(i).altinMiktari
                                    < ( ( (int)Math.ceil(enKisaYol.get(enKisaYol.size()-1).g / 3.0 )) * 5) - maaliyetAlinacakKare.altinMiktari ) {
                              enKisaYol = yol;
                              maaliyetAlinacakKare = harita.kareler.get(i);
                              kar = (  (int)Math.ceil((yol.get(yol.size()-1).g / 3.0)) * 5) - harita.kareler.get(i).altinMiktari;
                              this.hedefKareIndeks = i;
                            }
                         }
                    }
                }
                
                this.mevcutHedefVarMi = true;
                this.hedefYol = enKisaYol;
                this.hedefkare = enKisaYol.get(enKisaYol.size()-1);
                
                for(Kare kare : harita.altinOlanKareler){
                    if(kare.koordinatX == this.hedefkare.x && kare.koordinatY == this.hedefkare.y){
                        this.hedefAltin = kare;
                        break;
                    }
                }
                
                this.hedefYol.remove(0); // üstünde durduğu node'u sildik.
                //System.out.println((int)kar);
                
  }
  
  
  void sezgiselMaaliyetliHedefBelirle(Harita harita,Oyuncu oyuncuA, Oyuncu oyuncuB, Oyuncu oyuncuC){
                boolean Adanonce = false;
                boolean Bdenonce = false;
                boolean Cdenonce = false;
                
                AStar as;
                //D aramasını özel bir listede yapacak
                //bu listeye haritadaki tüm kareleri koyacağız daha sonra a,b ve c nin hedeflerine onlardan daha önce gidip gidemeyecegine bakacagiz
                //eger onlardan once gidemiyorsa bunlari listeden cikartip daha sonra normal maaliyetli aramayı yaptıracagiz.
                
                //A'nın hedefine daha önce gidebiliyor mu?
                as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                List<Node> Ayaolanyol = as.findPathTo(oyuncuA.hedefkare.x, oyuncuA.hedefkare.y);
                
                //Yolun uzakligina degil daha az hamlede yiyip yiyemedigine bakmam gerek
                int Ayaolanuzaklik = (int)Ayaolanyol.get(Ayaolanyol.size()-1).g;
                int AkacHamle = Ayaolanuzaklik / 3; // hamledeki adim sayisina bolerek kac hamlede gidecegini buluyoruz.
                
                //Ayni hesabi A icin de yapicaz
                int Aicinuzaklik = (int)oyuncuA.hedefYol.get(oyuncuA.hedefYol.size()-1).g;
                int AicinkacHamle = Aicinuzaklik / 3;
                
                if(AkacHamle <= AicinkacHamle){
                    Adanonce = true;
                }
                
                //B'nin hedefine daha önce gidebiliyor mu?
                as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                List<Node> Byeolanyol = as.findPathTo(oyuncuB.hedefkare.x, oyuncuB.hedefkare.y);
                
                int Byeolanuzaklik = (int)Byeolanyol.get(Byeolanyol.size()-1).g;
                int BkacHamle = Byeolanuzaklik / 3; // hamledeki adim sayisina bolerek kac hamlede gidecegini buluyoruz.
                
                //Ayni hesabi A icin de yapicaz
                int Bicinuzaklik = (int)oyuncuB.hedefYol.get(oyuncuB.hedefYol.size()-1).g;
                int BicinkacHamle = Bicinuzaklik / 3;
                
                if(BkacHamle <= BicinkacHamle){
                    Bdenonce = true;
                }
                
                as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                List<Node> Cyeolanyol = as.findPathTo(oyuncuC.hedefkare.x, oyuncuC.hedefkare.y);
                
                int Cyeolanuzaklik = (int)Cyeolanyol.get(Cyeolanyol.size()-1).g;
                int CkacHamle = Cyeolanuzaklik / 3; // hamledeki adim sayisina bolerek kac hamlede gidecegini buluyoruz.
                
                //Ayni hesabi A icin de yapicaz
                int Cicinuzaklik = (int)oyuncuC.hedefYol.get(oyuncuC.hedefYol.size()-1).g;
                int CicinkacHamle = Cicinuzaklik / 3;
                
                if(CkacHamle <= CicinkacHamle){
                    Cdenonce = true;
                }
                
                ArrayList<Kare> Dozelkareler = (ArrayList<Kare>)harita.kareler.clone();
                
                if(Adanonce == false){
                    Dozelkareler.remove(oyuncuA.hedefAltin);
                }
                
                if(Bdenonce == false){
                    Dozelkareler.remove(oyuncuB.hedefAltin);
                }
                
                if(Cdenonce == false){
                    Dozelkareler.remove(oyuncuC.hedefAltin);
                }
                
                //C'nin hedefine daha önce gidebiliyor mu?
                
                //En kısa nesneyi belirleyeceğiz
                as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                List<Node> enKisaYol = null;
                Kare maaliyetAlinacakKare = null;
                int kar = 0;
                for(Kare kare : Dozelkareler){
                    if(kare.altin == true){
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
                
                
                for(int i = 0; i < Dozelkareler.size(); i++){
                    if(Dozelkareler.get(i).altin == true){
                        as = new AStar(harita.maaliyetsizMatris, this.koordinatX, this.koordinatY, false);
                        List<Node> yol = as.findPathTo(Dozelkareler.get(i).koordinatX, Dozelkareler.get(i).koordinatY);
                         if(yol != null){
                            //En kisa bulma kismi artik farkli olacak
                            if( (  (int)Math.ceil((yol.get(yol.size()-1).g / 3.0)) * 5) - Dozelkareler.get(i).altinMiktari
                                    < ( ( (int)Math.ceil(enKisaYol.get(enKisaYol.size()-1).g / 3.0 )) * 5) - maaliyetAlinacakKare.altinMiktari ) {
                              enKisaYol = yol;
                              maaliyetAlinacakKare = Dozelkareler.get(i);
                              kar = (  (int)Math.ceil((yol.get(yol.size()-1).g / 3.0)) * 5) - Dozelkareler.get(i).altinMiktari;
                              this.hedefKareIndeks = i;
                            }
                         }
                    }
                }
                
                this.mevcutHedefVarMi = true;
                this.hedefYol = enKisaYol;
                this.hedefkare = enKisaYol.get(enKisaYol.size()-1);
                
                for(Kare kare : harita.altinOlanKareler){
                    if(kare.koordinatX == this.hedefkare.x && kare.koordinatY == this.hedefkare.y){
                        this.hedefAltin = kare;
                        break;
                    }
                }
                
                this.hedefYol.remove(0); // üstünde durduğu node'u sildik.
                //System.out.println((int)kar);
  }
  
  

  
}
