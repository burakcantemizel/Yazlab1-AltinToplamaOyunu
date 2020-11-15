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


public class AnaPencere extends JFrame {
  

    
    AnaPencere(){

    }
    
    public static void main(String[] args) throws InterruptedException, IOException{
        
        
            LafManager.install();
            JFrame pencere = new JFrame("Yazılım Laboratuvarı 1");
           
            SecimEkrani secimEkrani = new SecimEkrani();
  
            pencere.add(secimEkrani);
            pencere.pack();
            pencere.setResizable(true);
            pencere.setVisible(true);
            pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pencere.setLocationRelativeTo(null);
            
            
            pencere.remove(secimEkrani);
            
            Oyun oyun = new Oyun();
            oyun.setPreferredSize(new Dimension(Oyun.pencereGenislik, Oyun.pencereYukseklik));
            
            pencere.add(oyun);
            
            pencere.pack();
            pencere.setResizable(true);
            pencere.setVisible(true);
            pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pencere.setLocationRelativeTo(null);
            
            pencere.addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
            Oyun.pencereGenislik = oyun.getWidth();
            Oyun.pencereYukseklik = oyun.getHeight();
            }
            });
            
            //Ana Oyun Kontrolü
            oyun.ayarla();
            
            while(true){
            oyun.guncelle();
            Thread.sleep(1);
            }
            
            
            
            
        
    }
    
}
