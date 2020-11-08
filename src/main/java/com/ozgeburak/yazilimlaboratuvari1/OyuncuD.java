package com.ozgeburak.yazilimlaboratuvari1;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class OyuncuD extends Oyuncu {
    
  OyuncuD(int koordinatX, int koordinatY){
    super(koordinatX,koordinatY);
    this.r = 255;
    this.g = 255;
    this.b = 0;
    
            try {
            this.resim = ImageIO.read(new File("kaynaklar/oyuncuD.png"));
        } catch (IOException ex) {
            Logger.getLogger(OyuncuA.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

}
