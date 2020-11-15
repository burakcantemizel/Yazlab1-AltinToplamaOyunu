package com.ozgeburak.yazilimlaboratuvari1;

import com.ozgeburak.yazilimlaboratuvari1.AStar.Dugum;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Oyun extends JPanel {

    boolean oyunSonu = false;

    //Sabitler üzerinden hesaplanan değişkenler ve yeni sabitler
    public static int pencereGenislik = 1280;
    public static int pencereYukseklik = 720;
    public static final int TAHTA_GENISLIK = Sabitler.HARITA_SUTUN * Sabitler.KARE_GENISLIK;
    public static final int TAHTA_YUKSEKLIK = Sabitler.HARITA_SATIR * Sabitler.KARE_YUKSEKLIK;

    //Değişkenler 
    private Harita harita;
    private OyuncuA oyuncuA;
    private OyuncuB oyuncuB;
    private OyuncuC oyuncuC;
    private OyuncuD oyuncuD;

    static FileWriter fwOyuncuA, fwOyuncuB, fwOyuncuC, fwOyuncuD;
    static BufferedWriter writerOyuncuA, writerOyuncuB, writerOyuncuC, writerOyuncuD;

    private JSlider oyunHizi;
    private int tur;

    public Oyun() {
        //tanımlamalar,atamalar ve ayarlamalar ayarla metodunda yapiliyor.
        try {
            fwOyuncuA = new FileWriter("OyuncuA.txt");
            writerOyuncuA = new BufferedWriter(fwOyuncuA);
            fwOyuncuB = new FileWriter("OyuncuB.txt");
            writerOyuncuB = new BufferedWriter(fwOyuncuB);
        } catch (IOException e) {
            System.out.println("io hatasi");
        }
    }

    public void ayarla() {
        //Nesneler oluşturuluyor.
        harita = new Harita(Sabitler.HARITA_SUTUN, Sabitler.HARITA_SATIR);
        oyuncuA = new OyuncuA(0, 0);
        oyuncuB = new OyuncuB(harita.yatayKareSayisi - 1, 0);
        oyuncuC = new OyuncuC(0, harita.dikeyKareSayisi - 1);
        oyuncuD = new OyuncuD(harita.yatayKareSayisi - 1, harita.dikeyKareSayisi - 1);

        oyunHizi = new JSlider(1, 1000, 1);
        oyunHizi.setValue(300);

        //Slider pozisyonu ayarlanacak -------------------------------------------------------------------------------------------------------------------------------------------
        oyunHizi.setBounds(Sabitler.OYUN_GENISLIK - 120, Sabitler.OYUN_YUKSEKLIK - 60, 120, 30);
        this.add(oyunHizi);

        //Arkaplan
        this.setBackground(new Color(141, 183, 242));
        
        //Oyunun başında herkes hedef belirlesin.
    }

    public void guncelle() throws InterruptedException, IOException {
        if (oyunSonu == false) {
            //OYNANİS
            if (tur % 4 == 0) {
                System.out.println("Tur : " + Integer.toString((tur / 4) + 1) + "\n");
                fwOyuncuA.write("Tur : " + Integer.toString((tur / 4) + 1) + "\n");
                oyunSonuKontrol();

                if (oyuncuA.yasiyor == true) {
                    //Tur başı beklemesi
                    Thread.sleep(oyunHizi.getValue());

                    //A OYUNCUSU
                    //System.out.println("A oynuyor");
                    //Oyunun En başında hedefi yoksa bir kere hedef belirleyecek.
                    if (oyuncuA.koordinatX == 0 && oyuncuA.koordinatY == 0) {
                        oyuncuA.hedefBelirle(harita);
                        oyuncuA.olumKontrol();
                        Thread.sleep(oyunHizi.getValue());
                    }

                    //Mevcut bir hedefi var ve ilerliyor.
                    //Artık oyuncuA her tur ilerleyerek hedefe gidebilir 3 kare ilerleyerek
                    //Hareket Döngüsüne girmeden önce altindan hamle altin maaliyeti bir kerelik düşecek
                    if (oyuncuA.yasiyor == true) {
                        oyuncuA.altin -= Sabitler.OYUNCU_A_HAMLE_MAALIYET;
                        fwOyuncuA.write("Hamle Maaliyeti: " + Sabitler.OYUNCU_A_HAMLE_MAALIYET + " kalan altin: " + Integer.toString(oyuncuA.altin) + "\n");
                        oyuncuA.olumKontrol();
                    }

                    if (oyuncuA.yasiyor == true) {
                        boolean yenidenHedefBelirlendi = false;
                        
                        if (yenidenHedefBelirlendi == false) {
                                if (oyuncuA.hedefAltin.altin == false) {
                                    oyuncuA.hedefBelirle(harita);
                                    System.out.println("Hedef Belirleme 1 Altını alındı ve hedef belirledi" + oyuncuA.hedefkare);
                                    yenidenHedefBelirlendi = true;
                                    Thread.sleep(oyunHizi.getValue());
                                }
                            }
                        
                        for (int i = 0; i < oyuncuA.kalanHareket; i++) {
                            //Hareket etmeden önce hedefledigimiz altinin hala mevcut olup olmadigina bakacagiz
                            // eger hedefledigimiz altin hala mevcut degilse yeniden hedef belirleyeceğiz
                            

                            //Hareket ediyor
                            if (oyuncuA.mevcutHedefVarMi == true && oyuncuA.hedefYol.size() > 0) {
                                oyuncuA.koordinatX = oyuncuA.hedefYol.get(0).x;
                                oyuncuA.koordinatY = oyuncuA.hedefYol.get(0).y;
                                oyuncuA.hedefYol.remove(0);
                                fwOyuncuA.write("x: " + Integer.toString(oyuncuA.koordinatX) + " y: " + Integer.toString(oyuncuA.koordinatY) + "konumuna hareket etti. \n");
                                this.repaint();
                                Thread.sleep(oyunHizi.getValue() * 3);
                            }

                            //Her hamlede hamle maaliyeti kadar azalcak constant
                            //oyuncuA.altin -= 5;
                            //Her hareketten sonra altini aldi mi diye bakacagiz
                            if (oyuncuA.koordinatX == oyuncuA.hedefkare.x && oyuncuA.koordinatY == oyuncuA.hedefkare.y) {

                                //Ayni yerdeyse altini alacak ve hareket etmeyecek.
                                for (Kare kare : harita.kareler) {
                                    if (kare.koordinatX == oyuncuA.koordinatX && kare.koordinatY == oyuncuA.koordinatY) {
                                        //System.out.println("Alinan Altin Miktari " + kare.altinMiktari);
                                        oyuncuA.altin += kare.altinMiktari;
                                        kare.altin = false;
                                        fwOyuncuA.write("Altin toplandi. Toplanan Altin Miktari: " + kare.altinMiktari + " kalan altin: " + oyuncuA.altin + "\n");
                                        //altin miktarini oyuncuya ekleyecegiz
                                        oyunSonuKontrol();
                                        Thread.sleep(oyunHizi.getValue());
                                        break;
                                    }
                                }

                                oyuncuA.mevcutHedefVarMi = false;
                                oyuncuA.hedefYol = null;
                                oyuncuA.hedefkare = null;
                                //Altini da listeden silecegiz.
                                //System.out.println("altin aldi");

                                //Bu Noktadan sonra altını aldı ve hedefsiz kaldı
                                if (oyuncuA.mevcutHedefVarMi == false && oyuncuA.hedefkare == null) {
                                    oyuncuA.hedefBelirle(harita);
                                    System.out.println("Hedef Belirleme 2 Altın aldı ve hedef belirledi" + oyuncuA.hedefkare);
                                    oyuncuA.olumKontrol();
                                    oyunSonuKontrol();
                                    Thread.sleep(oyunHizi.getValue());
                                }

                                //Altını aldığında mevcut hedefi kalmıyor
                                this.repaint();
                                Thread.sleep(oyunHizi.getValue() * 3);
                                break;

                            }

                            //Ayrıca her hareketten sonra gizli altinda mi diye de bakacagiz
                            for (Kare kare : harita.kareler) {
                                if (kare.gizliAltin == true && kare.koordinatX == oyuncuA.koordinatX && kare.koordinatY == oyuncuA.koordinatY) {
                                    //O kare artık gizli altin değil normal altin olacak
                                    kare.gizliAltin = false;
                                    kare.altin = true;
                                    fwOyuncuA.write("Gizli altin aciga cikarildi. x: " + kare.koordinatX + " y: " + kare.koordinatY + "\n");
                                }
                            }

                        }
                    }
                }

                tur++;
                this.repaint();
                //}
            } else if (tur % 4 == 1) {
                fwOyuncuB.write("Tur : " + Integer.toString((tur / 4) + 1) + "\n");
                oyunSonuKontrol();
                if (oyuncuB.yasiyor == true) {
                    //B Oyuncusu
                    Thread.sleep(oyunHizi.getValue());

                    //System.out.println("B oynuyor");
                    //Oyunun En başında hedefi yoksa bir kere hedef belirleyecek.
                    if (oyuncuB.mevcutHedefVarMi == false) {

                        oyuncuB.maaliyetliHedefBelirle(harita, Sabitler.OYUNCU_B_HAMLE_MAALIYET, Sabitler.OYUNCU_B_HEDEF_BELIRLEME_MAALIYET);
                        oyuncuB.altin -= Sabitler.OYUNCU_B_HEDEF_BELIRLEME_MAALIYET;
                        fwOyuncuB.write("Hamle Maaliyeti: " + Sabitler.OYUNCU_B_HAMLE_MAALIYET + " kalan altin: " + Integer.toString(oyuncuB.altin) + "\n");
                        oyuncuB.olumKontrol();
                        Thread.sleep(oyunHizi.getValue());

                    }

                    if (oyuncuB.yasiyor == true) {
                        oyuncuB.altin -= Sabitler.OYUNCU_B_HAMLE_MAALIYET;
                        oyuncuB.olumKontrol();
                    }

                    if (oyuncuB.yasiyor == true) {
                        //Mevcut bir hedefi var ve ilerliyor.
                        //Artık oyuncuA her tur ilerleyerek hedefe gidebilir 3 kare ilerleyerek
                        for (int i = 0; i < oyuncuB.kalanHareket; i++) {
                            if (oyuncuB.hedefAltin != null) {
                                if (oyuncuB.hedefAltin.altin == false) {
                                    oyuncuB.maaliyetliHedefBelirle(harita, Sabitler.OYUNCU_B_HAMLE_MAALIYET, Sabitler.OYUNCU_B_HEDEF_BELIRLEME_MAALIYET);
                                    oyuncuB.altin -= Sabitler.OYUNCU_B_HEDEF_BELIRLEME_MAALIYET;
                                    if (oyuncuB.olumKontrol()) {
                                        break;
                                    }
                                    Thread.sleep(oyunHizi.getValue());
                                }
                            }

                            //Hareket ediyor
                            if (oyuncuB.mevcutHedefVarMi == true && oyuncuB.hedefYol.size() > 0) {
                                oyuncuB.koordinatX = oyuncuB.hedefYol.get(0).x;
                                oyuncuB.koordinatY = oyuncuB.hedefYol.get(0).y;
                                oyuncuB.hedefYol.remove(0);
                                this.repaint();
                                fwOyuncuB.write("x: " + Integer.toString(oyuncuB.koordinatX) + " y: " + Integer.toString(oyuncuB.koordinatY) + "konumuna hareket etti. \n");
                                Thread.sleep(oyunHizi.getValue() * 3);
                            }

                            //Her hareketten sonra altini aldi mi diye bakacagiz
                            if (oyuncuB.koordinatX == oyuncuB.hedefkare.x && oyuncuB.koordinatY == oyuncuB.hedefkare.y) {
                                //Ayni yerdeyse altini alacak ve hareket etmeyecek.
                                for (Kare kare : harita.kareler) {
                                    if (kare.koordinatX == oyuncuB.koordinatX && kare.koordinatY == oyuncuB.koordinatY) {
                                        oyuncuB.altin += kare.altinMiktari;
                                        kare.altin = false;
                                        fwOyuncuB.write("Altin toplandi. Toplanan Altin Miktari: " + kare.altinMiktari + " kalan altin: " + oyuncuB.altin + "\n");
                                        Thread.sleep(oyunHizi.getValue());
                                        oyunSonuKontrol();
                                        break;
                                    }
                                }
                                oyuncuB.mevcutHedefVarMi = false;
                                oyuncuB.hedefYol = null;
                                oyuncuB.hedefkare = null;
                                //Altini da listeden silecegiz.
                                //System.out.println("altin aldi");

                                //altini aldiysak
                                if (oyuncuB.mevcutHedefVarMi == false) {
                                    oyuncuB.maaliyetliHedefBelirle(harita, Sabitler.OYUNCU_B_HAMLE_MAALIYET, Sabitler.OYUNCU_B_HEDEF_BELIRLEME_MAALIYET);
                                    oyuncuB.altin -= Sabitler.OYUNCU_B_HEDEF_BELIRLEME_MAALIYET;
                                    oyuncuB.olumKontrol();
                                    oyunSonuKontrol();
                                    Thread.sleep(oyunHizi.getValue());
                                }

                                //Altını aldığında mevcut hedefi kalmıyor
                                this.repaint();
                                Thread.sleep(oyunHizi.getValue() * 3);
                                break;

                            }

                            //Ayrıca her hareketten sonra gizli altinda mi diye de bakacagiz
                            for (Kare kare : harita.kareler) {
                                if (kare.gizliAltin == true && kare.koordinatX == oyuncuB.koordinatX && kare.koordinatY == oyuncuB.koordinatY) {
                                    //O kare artık gizli altin değil normal altin olacak
                                    kare.gizliAltin = false;
                                    kare.altin = true;
                                    fwOyuncuA.write("Gizli altin aciga cikarildi. x: " + kare.koordinatX + " y: " + kare.koordinatY + "\n");
                                }
                            }

                        }
                    }

                }

                tur++;
                this.repaint();

            } else if (tur % 4 == 2) {
                oyunSonuKontrol();
                if (oyuncuC.yasiyor == true) {
                    //C Oyuncusu
                    Thread.sleep(oyunHizi.getValue());
                    oyuncuC.gizliAltinlariAcigaCikart(harita, Sabitler.OYUNCU_C_ACILACAK_GIZLI_ALTIN_SAYISI);
                    Thread.sleep(oyunHizi.getValue());

                    //System.out.println("C oynuyor");
                    //Oyunun En başında hedefi yoksa bir kere hedef belirleyecek.
                    if (oyuncuC.mevcutHedefVarMi == false) {

                        oyuncuC.maaliyetliHedefBelirle(harita, Sabitler.OYUNCU_C_HAMLE_MAALIYET, Sabitler.OYUNCU_C_HEDEF_BELIRLEME_MAALIYET);
                        oyuncuC.altin -= Sabitler.OYUNCU_C_HEDEF_BELIRLEME_MAALIYET;
                        oyuncuC.olumKontrol();
                        Thread.sleep(oyunHizi.getValue());

                    }

                    if (oyuncuC.yasiyor == true) {
                        oyuncuC.altin -= Sabitler.OYUNCU_C_HAMLE_MAALIYET;
                        oyuncuC.olumKontrol();
                    }

                    if (oyuncuC.yasiyor == true) {
                        //Mevcut bir hedefi var ve ilerliyor.
                        //Artık oyuncuA her tur ilerleyerek hedefe gidebilir 3 kare ilerleyerek
                        for (int i = 0; i < oyuncuC.kalanHareket; i++) {
                            if (oyuncuC.hedefAltin != null) {
                                if (oyuncuC.hedefAltin.altin == false) {
                                    oyuncuC.maaliyetliHedefBelirle(harita, Sabitler.OYUNCU_C_HAMLE_MAALIYET, Sabitler.OYUNCU_C_HEDEF_BELIRLEME_MAALIYET);
                                    oyuncuC.altin -= Sabitler.OYUNCU_C_HEDEF_BELIRLEME_MAALIYET;
                                    if (oyuncuC.olumKontrol()) {
                                        break;
                                    }
                                    Thread.sleep(oyunHizi.getValue());
                                }
                            }

                            //Hareket ediyor
                            if (oyuncuC.mevcutHedefVarMi == true && oyuncuC.hedefYol.size() > 0) {
                                oyuncuC.koordinatX = oyuncuC.hedefYol.get(0).x;
                                oyuncuC.koordinatY = oyuncuC.hedefYol.get(0).y;
                                oyuncuC.hedefYol.remove(0);
                                this.repaint();
                                Thread.sleep(oyunHizi.getValue() * 3);
                            }

                            //Her hareketten sonra altini aldi mi diye bakacagiz
                            if (oyuncuC.koordinatX == oyuncuC.hedefkare.x && oyuncuC.koordinatY == oyuncuC.hedefkare.y) {
                                //Ayni yerdeyse altini alacak ve hareket etmeyecek.
                                for (Kare kare : harita.kareler) {
                                    if (kare.koordinatX == oyuncuC.koordinatX && kare.koordinatY == oyuncuC.koordinatY) {
                                        oyuncuC.altin += kare.altinMiktari;
                                        kare.altin = false;
                                        oyunSonuKontrol();
                                        Thread.sleep(oyunHizi.getValue());
                                        break;
                                    }
                                }
                                oyuncuC.mevcutHedefVarMi = false;
                                oyuncuC.hedefYol = null;
                                oyuncuC.hedefkare = null;
                                //Altini da listeden silecegiz.
                                //System.out.println("altin aldi");

                                //altini aldiysak
                                if (oyuncuC.mevcutHedefVarMi == false) {
                                    oyuncuC.maaliyetliHedefBelirle(harita, Sabitler.OYUNCU_C_HAMLE_MAALIYET, Sabitler.OYUNCU_C_HEDEF_BELIRLEME_MAALIYET);
                                    oyuncuC.altin -= Sabitler.OYUNCU_C_HEDEF_BELIRLEME_MAALIYET;
                                    oyuncuC.olumKontrol();
                                    oyunSonuKontrol();
                                    Thread.sleep(oyunHizi.getValue());
                                }

                                //Altını aldığında mevcut hedefi kalmıyor
                                this.repaint();
                                Thread.sleep(oyunHizi.getValue() * 3);
                                break;

                            }

                            //Ayrıca her hareketten sonra gizli altinda mi diye de bakacagiz
                            for (Kare kare : harita.kareler) {
                                if (kare.gizliAltin == true && kare.koordinatX == oyuncuC.koordinatX && kare.koordinatY == oyuncuC.koordinatY) {
                                    //O kare artık gizli altin değil normal altin olacak
                                    kare.gizliAltin = false;
                                    kare.altin = true;
                                }
                            }

                        }
                    }

                }

                tur++;
                this.repaint();
            } else if (tur % 4 == 3) {
                oyunSonuKontrol();
                if (oyuncuD.yasiyor == true) {
                    //D Oyuncusu
                    Thread.sleep(oyunHizi.getValue());

                    //System.out.println("D oynuyor");
                    //Oyunun En başında hedefi yoksa bir kere hedef belirleyecek.
                    if (oyuncuD.mevcutHedefVarMi == false) {

                        oyuncuD.sezgiselMaaliyetliHedefBelirle(harita, oyuncuA, oyuncuB, oyuncuC);
                        oyuncuD.altin -= Sabitler.OYUNCU_D_HEDEF_BELIRLEME_MAALIYET;
                        oyuncuD.olumKontrol();
                        Thread.sleep(oyunHizi.getValue());

                    }

                    if (oyuncuD.yasiyor == true) {
                        oyuncuD.altin -= Sabitler.OYUNCU_D_HAMLE_MAALIYET;
                        oyuncuD.olumKontrol();
                    }

                    if (oyuncuD.yasiyor == true) {
                        //Mevcut bir hedefi var ve ilerliyor.
                        //Artık oyuncuA her tur ilerleyerek hedefe gidebilir 3 kare ilerleyerek
                        for (int i = 0; i < oyuncuD.kalanHareket; i++) {
                            if (oyuncuD.hedefAltin != null) {
                                if (oyuncuD.hedefAltin.altin == false) {
                                    oyunSonuKontrol();
                                    oyuncuD.sezgiselMaaliyetliHedefBelirle(harita, oyuncuA, oyuncuB, oyuncuC);
                                    oyuncuD.altin -= Sabitler.OYUNCU_D_HEDEF_BELIRLEME_MAALIYET;
                                    if (oyuncuD.olumKontrol()) {
                                        break;
                                    }
                                    Thread.sleep(oyunHizi.getValue());
                                }
                            }

                            //Hareket ediyor
                            if (oyuncuD.mevcutHedefVarMi == true && oyuncuD.hedefYol.size() > 0) {
                                oyuncuD.koordinatX = oyuncuD.hedefYol.get(0).x;
                                oyuncuD.koordinatY = oyuncuD.hedefYol.get(0).y;
                                oyuncuD.hedefYol.remove(0);
                                this.repaint();
                                Thread.sleep(oyunHizi.getValue() * 3);
                            }

                            //Her hareketten sonra altini aldi mi diye bakacagiz
                            if (oyuncuD.koordinatX == oyuncuD.hedefkare.x && oyuncuD.koordinatY == oyuncuD.hedefkare.y) {
                                //Ayni yerdeyse altini alacak ve hareket etmeyecek.
                                for (Kare kare : harita.kareler) {
                                    if (kare.koordinatX == oyuncuD.koordinatX && kare.koordinatY == oyuncuD.koordinatY) {
                                        oyuncuD.altin += kare.altinMiktari;
                                        kare.altin = false;
                                        oyunSonuKontrol();
                                        Thread.sleep(oyunHizi.getValue());
                                        break;
                                    }
                                }
                                oyuncuD.mevcutHedefVarMi = false;
                                oyuncuD.hedefYol = null;
                                oyuncuD.hedefkare = null;
                                //Altini da listeden silecegiz.
                                //System.out.println("altin aldi");

                                //altini aldiysak
                                if (oyuncuD.mevcutHedefVarMi == false) {
                                    oyuncuD.sezgiselMaaliyetliHedefBelirle(harita, oyuncuA, oyuncuB, oyuncuC);
                                    oyuncuD.altin -= Sabitler.OYUNCU_D_HEDEF_BELIRLEME_MAALIYET;
                                    oyuncuD.olumKontrol();
                                    oyunSonuKontrol();
                                    Thread.sleep(oyunHizi.getValue());
                                }

                                //Altını aldığında mevcut hedefi kalmıyor
                                this.repaint();
                                Thread.sleep(oyunHizi.getValue() * 3);
                                break;

                            }

                            //Ayrıca her hareketten sonra gizli altinda mi diye de bakacagiz
                            for (Kare kare : harita.kareler) {
                                if (kare.gizliAltin == true && kare.koordinatX == oyuncuD.koordinatX && kare.koordinatY == oyuncuD.koordinatY) {
                                    //O kare artık gizli altin değil normal altin olacak
                                    kare.gizliAltin = false;
                                    kare.altin = true;
                                }
                            }

                        }
                    }

                }

                tur++;
                this.repaint();
            }

        }

        this.repaint();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (oyunSonu == false) {
            //Oyun tahtasını çoklu çözünürlüklü hale getirme ve pencereye ortalama
            float kenarlik = 0.9f;
            float olcekX = (float) pencereYukseklik * kenarlik / (float) TAHTA_GENISLIK;
            float olcekY = (float) pencereYukseklik * kenarlik / (float) TAHTA_YUKSEKLIK;
            int kaymaX = (int) ((float) pencereGenislik - ((float) TAHTA_GENISLIK * olcekX)) / 2;
            int kaymaY = (int) ((float) pencereYukseklik - ((float) TAHTA_YUKSEKLIK * olcekY)) / 2;

            g2d.translate(kaymaX, kaymaY);
            g2d.scale(olcekX, olcekY);

            //Elemanların çizdirilmesi
            harita.Cizdir(g2d);

            if (oyuncuA.yasiyor == true) {
                oyuncuA.Cizdir(g2d);
            }

            if (oyuncuA.yasiyor == true) {
                oyuncuA.HedefCizdir(g2d);
            }

            if (oyuncuB.yasiyor == true) {
                oyuncuB.Cizdir(g2d);
            }

            if (oyuncuB.yasiyor == true) {
                oyuncuB.HedefCizdir(g2d);
            }

            if (oyuncuC.yasiyor == true) {
                oyuncuC.Cizdir(g2d);
            }

            if (oyuncuC.yasiyor == true) {
                oyuncuC.HedefCizdir(g2d);
            }

            if (oyuncuD.yasiyor == true) {
                oyuncuD.Cizdir(g2d);
            }

            if (oyuncuD.yasiyor == true) {
                oyuncuD.HedefCizdir(g2d);
            }

            harita.altinCizdir(g2d);

            if (oyuncuA.yasiyor == true) {
                oyuncuA.YolCizdir(g2d);
            }

            if (oyuncuB.yasiyor == true) {
                oyuncuB.YolCizdir(g2d);
            }

            if (oyuncuC.yasiyor == true) {
                oyuncuC.YolCizdir(g2d);
            }

            if (oyuncuD.yasiyor == true) {
                oyuncuD.YolCizdir(g2d);
            }

            //Diğer elemanların düzgün çizdirilmesi için ölçeği eski haline getiriyoruz.
            g2d.scale(1 / olcekX, 1 / olcekY);
            g2d.translate(-kaymaX, -kaymaY);

            BilgiGoster(g2d);
        } else {
            g2d.drawString("Oyun bitti ", 100, 100);
        }

    }

    public void BilgiGoster(Graphics2D g) {
        //Bilgileri ekrana çizen fonksiyon
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        if (oyuncuA != null) {
            g.drawString("Oyuncu A Altin: " + oyuncuA.altin, pencereGenislik - 200, 20);
        }
        if (oyuncuB != null) {
            g.drawString("Oyuncu B Altin: " + oyuncuB.altin, pencereGenislik - 200, 40);
        }
        if (oyuncuC != null) {
            g.drawString("Oyuncu C Altin: " + oyuncuC.altin, pencereGenislik - 200, 60);
        }
        if (oyuncuD != null) {
            g.drawString("Oyuncu D Altin: " + oyuncuD.altin, pencereGenislik - 200, 80);
        }
    }

    public boolean oyunSonuKontrol() throws IOException {
        int kalanaltin = 0;

        for (Kare kare : harita.altinOlanKareler) {
            if (kare.altin == true) {
                kalanaltin++;
            }
        }

        if (kalanaltin <= 0) {
            this.repaint();
            Thread.sleep(300 * 3);
            oyunSonu = true;
            fwOyuncuA.close();
            fwOyuncuB.close();
            System.out.println("Oyun bitti");
            oyuncuA.yasiyor = false;
            oyuncuB.yasiyor = false;
            oyuncuC.yasiyor = false;
            oyuncuD.yasiyor = false;
            return true;
        } else if (oyuncuA.altin <= 0 && oyuncuB.altin <= 0 && oyuncuC.altin <= 0 && oyuncuD.altin <= 0) {
            this.repaint();
            Thread.sleep(300 * 3);
            oyunSonu = true;
            fwOyuncuA.close();
            fwOyuncuB.close();
            System.out.println("Oyun bitti 2");
            return true;
        }

        return false;
    }

}
