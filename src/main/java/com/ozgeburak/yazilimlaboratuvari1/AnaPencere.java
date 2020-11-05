package com.ozgeburak.yazilimlaboratuvari1;

import java.awt.Dimension;
import javax.swing.JFrame;


public class AnaPencere extends JFrame {
    
    
    public static void main(String[] args) throws InterruptedException{
        JFrame pencere = new JFrame("Yazılım Laboratuvarı 1");
        Oyun oyun = new Oyun();
        oyun.setPreferredSize(new Dimension(Oyun.OYUN_GENISLIK, Oyun.OYUN_YUKSEKLIK));
        pencere.add(oyun);
        pencere.pack();
        pencere.setResizable(false);
        pencere.setVisible(true);
        pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pencere.setLocationRelativeTo(null);
        
        //Ana Oyun Kontrolü
        oyun.ayarla();
        
        while(true){
            oyun.guncelle();
            Thread.sleep(1);
        }
    }
    
}
