package com.ozgeburak.yazilimlaboratuvari1;

import com.github.weisj.darklaf.LafManager;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


//AnaSinif main methodu burada bulunuyor.
public class AnaSinif extends JFrame {
  
    static JFrame pencere;
    
    AnaSinif(){

    }
    
    public static void main(String[] args) throws InterruptedException, IOException{
        
            //Gui tema kütüphanesini kuruyoruz
            //Böylece javanın standart swing arayüzü daha güzel görünümlü bir hale gelecek
            LafManager.install();
            
            //Pencere Başlığı
            pencere = new JFrame("Yazılım Laboratuvarı 1 - Altın Toplama Oyunu");
           
            //Secim ekranini olusturuyoruz
            SecimEkrani secimEkrani = new SecimEkrani();
            pencere.add(secimEkrani); // pencereye ekleme
            pencere.pack(); // pencereyi panele oturtuyoruz
            pencere.setResizable(false); // boyutlandırma menülerde kapalı
            pencere.setVisible(true); // görünür hale getirdik
            pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x butonu ile çıkma için
            pencere.setLocationRelativeTo(null); // ekranın ortasına konumlandırdık
            
        
    }
    
}
