package com.ozgeburak.yazilimlaboratuvari1;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;


public class AnaPencere extends JFrame {
    
    
    public static void main(String[] args) throws InterruptedException{
        JFrame pencere = new JFrame("Yazılım Laboratuvarı 1");
        Oyun oyun = new Oyun();
        oyun.setPreferredSize(new Dimension(Oyun.PENCERE_GENISLIK, Oyun.PENCERE_YUKSEKLIK));
        pencere.add(oyun);
        pencere.pack();
        pencere.setResizable(true);
        pencere.setVisible(true);
        pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pencere.setLocationRelativeTo(null);
        
        pencere.addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
             Oyun.PENCERE_GENISLIK = oyun.getWidth();
             Oyun.PENCERE_YUKSEKLIK = oyun.getHeight();
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
