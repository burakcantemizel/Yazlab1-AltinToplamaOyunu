package com.ozgeburak.yazilimlaboratuvari1;

import com.ozgeburak.yazilimlaboratuvari1.AStar.Node;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;


public class Oyun extends JPanel {
    public static final int OYUN_GENISLIK = 1280;
    public static final int OYUN_YUKSEKLIK = 720;
    
    private final static int YATAY_KARE_SAYISI = 20;
    private final static int DIKEY_KARE_SAYISI = 20;
    
    private Harita harita;
    private OyuncuA oyuncuA;
    private OyuncuB oyuncuB;
    private OyuncuC oyuncuC;
    private OyuncuD oyuncuD;
    
   JButton turButon;
    
    private int tur;
    
    
    public Oyun(){
        
    }
    
    
    public void ayarla(){
        harita = new Harita(YATAY_KARE_SAYISI, DIKEY_KARE_SAYISI);
        oyuncuA = new OyuncuA(0,0);
        oyuncuB = new OyuncuB(harita.yatayKareSayisi-1,0);
        oyuncuC = new OyuncuC(0,harita.dikeyKareSayisi-1);
        oyuncuD = new OyuncuD(harita.yatayKareSayisi-1, harita.dikeyKareSayisi-1);
        turButon = new JButton("Tur ilerle");
        this.add(turButon);
        turButon.setBounds(OYUN_GENISLIK - 120, OYUN_YUKSEKLIK - 30, 120, 30);
        
        turButon.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                tur++;
            }
        });
    }
    
    public void guncelle() throws InterruptedException{
        //OYNANİS
        if(tur % 4 == 0){
          //A OYUNCUSU
          if(oyuncuA.mevcutHedefVarMi == false){
              System.out.println("A oynuyor");
               //En kısa nesneyi belirleyeceğiz
                AStar as = new AStar(harita.maaliyetsizMatris, oyuncuA.koordinatX, oyuncuA.koordinatY, false);
                List<Node> enKisaYol = as.findPathTo(harita.altinOlanKareler.get(0).koordinatX, harita.altinOlanKareler.get(0).koordinatY);
                oyuncuA.hedefKareIndeks = 0;
                for(int i = 0; i < harita.altinOlanKareler.size(); i++){
                    as = new AStar(harita.maaliyetsizMatris,oyuncuA.koordinatX, oyuncuA.koordinatY,false);

                    //Altın olan tüm kareleri geziyoruz ve oraya olan uzakligi buluyoruz.
                    List<Node> yol = as.findPathTo(harita.altinOlanKareler.get(i).koordinatX, harita.altinOlanKareler.get(i).koordinatY);
                    if(yol != null){
                      if(yol.get(yol.size()-1).g < enKisaYol.get(enKisaYol.size()-1).g){
                        enKisaYol = yol;
                        oyuncuA.hedefKareIndeks = i;
                      }
                    }
                    //Burda en kisa yolu bulduk. artik En Kisa Yol bize en kisa yol bilgisini veriyor
                }
                
                oyuncuA.mevcutHedefVarMi = true;
                oyuncuA.hedefYol = enKisaYol;
                oyuncuA.hedefkare = enKisaYol.get(enKisaYol.size()-1);
                oyuncuA.hedefYol.remove(0); // üstünde durduğu node'u sildik.
                
                }
          
                    //Mevcut bir hedefi var ve ilerliyor.
                    
                    //Artık oyuncuA her tur ilerleyerek hedefe gidebilir 3 kare ilerleyerek
                    for(int i=0; i < oyuncuA.kalanHareket; i++){
                        //Hareket ediyor
                        if(oyuncuA.mevcutHedefVarMi == true && oyuncuA.hedefYol.size() > 0){
                            oyuncuA.koordinatX = oyuncuA.hedefYol.get(0).x;
                            oyuncuA.koordinatY = oyuncuA.hedefYol.get(0).y;
                            oyuncuA.hedefYol.remove(0);
                            this.repaint();
                            Thread.sleep(1000);
                        }
                        
                        
                        //Her hareketten sonra altini aldi mi diye bakacagiz
                        if(oyuncuA.koordinatX == oyuncuA.hedefkare.x && oyuncuA.koordinatY == oyuncuA.hedefkare.y){
                            //Ayni yerdeyse altini alacak ve hareket etmeyecek.
                            for(Kare kare: harita.kareler){
                                if(kare.koordinatX == oyuncuA.koordinatX && kare.koordinatY == oyuncuA.koordinatY){
                                    kare.altin = false;
                                    break;
                                }
                            }
                            oyuncuA.mevcutHedefVarMi = false;
                            oyuncuA.hedefYol = null;
                            oyuncuA.hedefkare = null;
                            //Altini da listeden silecegiz.
                            if(harita.altinOlanKareler.size() > 0){
                            harita.altinOlanKareler.remove(oyuncuA.hedefKareIndeks);
                            }
                            
                            System.out.println("altin aldi");
                            //Altını aldığında mevcut hedefi kalmıyor
                            this.repaint();
                            Thread.sleep(1000);
                            break;
                            
                        }

                    }
                    
                
          tur++;
          //}
        }else if(tur % 4 == 1){
          //B OYUNCUSU
          System.out.println("B oynuyor");

        }else if(tur % 4 == 2){
          //C OYUNCUSU
          System.out.println("C oynuyor");

        }else if(tur % 4 == 3){
          //D Oyuncusu
          System.out.println("D oynuyor");
        }
        
        
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        harita.Cizdir(g2d);
        oyuncuA.Cizdir(g2d);
        oyuncuB.Cizdir(g2d);
        oyuncuC.Cizdir(g2d);
        oyuncuD.Cizdir(g2d);
        harita.altinCizdir(g2d);
        
        //BilgiGoster();
    }
    
    protected List constructPath(Kare node) {
    LinkedList path = new LinkedList();
    while (node.pathParent != null) {
      path.addFirst(node);
      node = node.pathParent;
    }
    return path;
  }
    
    public List search(Kare startNode, Kare goalNode) {
  // list of visited nodes
  LinkedList closedList = new LinkedList();
  
  // list of nodes to visit (sorted)
  LinkedList openList = new LinkedList();
  openList.add(startNode);
  startNode.pathParent = null;
  
  while (!openList.isEmpty()) {
    Kare node = (Kare)openList.removeFirst();
    if (node == goalNode) {
      // path found!
      return constructPath(goalNode);
    }
    else {
      closedList.add(node);
      
      // add neighbors to the open list
      Iterator i = node.neighbors.iterator();
      while (i.hasNext()) {
        Kare neighborNode = (Kare)i.next();
        if (!closedList.contains(neighborNode) &&
          !openList.contains(neighborNode)) 
        {
          neighborNode.pathParent = node;
          openList.add(neighborNode);
        }
      }
    }
  }
  
  // no path found
  return null;
}

}
