package com.ozgeburak.yazilimlaboratuvari1;

import com.ozgeburak.yazilimlaboratuvari1.AStar.Node;
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
  
}
