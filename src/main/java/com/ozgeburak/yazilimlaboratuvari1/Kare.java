package com.ozgeburak.yazilimlaboratuvari1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;


public class Kare {
  int koordinatX;
  int koordinatY;
  int pozisyonX;
  int pozisyonY;
  int genislik;
  int yukseklik;
  boolean baslangicKaresi = false;
  boolean altin = false;
  boolean gizliAltin = false;
  int altinMiktari = 0;
  
  List neighbors;
  Kare pathParent;
  
  Kare(int koordinatX, int koordinatY){
    this.koordinatX = koordinatX;
    this.koordinatY = koordinatY;
    genislik = 32;
    yukseklik = 32;
    this.pozisyonX = koordinatX * genislik;
    this.pozisyonY = koordinatY * yukseklik;
  }
  
  
   void Cizdir(Graphics2D g){
    
        
    if(baslangicKaresi == true){
        g.setColor(Color.WHITE);
        g.fillRect(pozisyonX, pozisyonY, genislik, yukseklik);
        g.setColor(Color.GRAY);
        g.drawRect(pozisyonX, pozisyonY, genislik, yukseklik);
    }else{
        g.setColor(Color.WHITE);
        g.fillRect(pozisyonX, pozisyonY, genislik, yukseklik);
        g.setColor(Color.BLACK);
        g.drawRect(pozisyonX, pozisyonY, genislik, yukseklik);
    }
    


    

   
       
  }
   
  void altinCizdir(Graphics2D g){
   if(altin == true){
        g.setColor(Color.YELLOW);
        g.fillOval(pozisyonX + genislik/2 - 6, pozisyonY + yukseklik/2 - 6, 12, 12);
        g.setColor(Color.BLACK);
        g.drawOval(pozisyonX + genislik/2 - 6, pozisyonY + yukseklik/2 - 6, 12, 12);
    }else if(gizliAltin == true){
        g.setColor(Color.ORANGE);
        g.fillOval(pozisyonX + genislik/2 - 6, pozisyonY + yukseklik/2 - 6, 12, 12);
        g.setColor(Color.BLACK);
        g.drawOval(pozisyonX + genislik/2 - 6, pozisyonY + yukseklik/2 - 6, 12, 12);
    }
    
    if(altin == true || gizliAltin == true){
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(altinMiktari), pozisyonX + genislik/2 - 4, pozisyonY + yukseklik/2 + 2);
    }
  }
  

}
