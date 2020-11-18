package com.ozgeburak.yazilimlaboratuvari1;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


//Menüdeki Bulut ve Altınların sınıfı
//yapısal özellikleri ve cizdirme fonksiyonlarını barındırıyor
public class MenuGrafigi {
    int x;
    int y;
    String tip;
    int bulutTipi;
    int bulutYon;
    int hiz;
    Random r;
    MenuGrafigi(int x, int y, String tip){
         this.x = x;
         this.y = y;
         this.tip = tip;
          r = new Random();
         this.bulutTipi = r.nextInt(5);
         this.bulutYon = r.nextInt(2);
         
         if(this.tip == "altin"){
             this.hiz = 5;
         }else{
             if(Math.random() > 0.5){
                 this.hiz = 1;
             }else{
                 this.hiz = -1;
             }
                 
         }
    }
    
    //Menü Animasyonları
    void guncelle(){
        if(tip == "altin"){
            this.y += this.hiz;
            
             if(this.y >= 720){
            this.y = -100;
            //this.hiz = r.nextInt(5);
        }
        }else{
            this.x += this.hiz;
            
            if(this.x <= -500){
                this.x = 2000;
            }
            
            if(this.x >= 2000){
                this.x = -500;
            }
        }
    }
    
    
    //Menü Grafiklerini Çizdirme
    void cizdir(Graphics g){
        if(tip == "altin"){
            g.drawImage(SecimEkrani.altin, this.x, this.y, null);
        }else{
            switch(bulutTipi){
                case 0:
                    g.drawImage(SecimEkrani.bulut1, this.x, this.y, null);
                    break;
                    
                case 1:
                    g.drawImage(SecimEkrani.bulut2, this.x, this.y, null);
                    break;
                    
                case 2:
                    g.drawImage(SecimEkrani.bulut3, this.x, this.y, null);
                    break;
                    
                case 3:
                    g.drawImage(SecimEkrani.bulut4, this.x, this.y, null);
                    break;
                    
                case 4:
                    g.drawImage(SecimEkrani.bulut5,this.x, this.y, null);
                    break;
            }
        }
        
    }
}
