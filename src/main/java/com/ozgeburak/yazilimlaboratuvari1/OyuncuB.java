package com.ozgeburak.yazilimlaboratuvari1;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class OyuncuB extends Oyuncu {
      
  OyuncuB(int koordinatX, int koordinatY){
    super(koordinatX,koordinatY);
    this.r = 0;
    this.g = 0;
    this.b = 255;
    
    
            try {
            this.resim = ImageIO.read(new File("kaynaklar/oyuncuB.png"));
        } catch (IOException ex) {
            Logger.getLogger(OyuncuA.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
}
