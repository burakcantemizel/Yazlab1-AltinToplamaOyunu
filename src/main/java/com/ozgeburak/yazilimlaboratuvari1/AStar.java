package com.ozgeburak.yazilimlaboratuvari1;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

//AStar Sınıfı Oluşutulurken
//Implementasyon kısmında rosettacode.org'un rosettacode.org/wiki/A*_search_algorithm#Java baglantisindaki
//kod referans alınarak 4-directional olmayan kısımlar kaldırılarak, sürekli astar objesini initialize etmeden
//kullanmak için sifirla metodu gibi bazı ek özellikler eklenerek yeniden yazılmıştır.
//Ayrıca bazı kısımlarda daniel shiffmanın a* derslerinden faydalanılmıştır.
class AStar {

    //Değişken tanımlamaları
    List<Dugum> acikKume, kapaliKume, yol;
    int[][] harita;
    static Dugum mevcutDugum;
    int baslangicX, baslangicY, bitisX, bitisY;

    AStar(int[][] harita, int baslangicX, int baslangicY) {
        this.acikKume = new ArrayList<>();
        this.kapaliKume = new ArrayList<>();
        this.yol = new ArrayList<>();
        this.harita = harita;
        this.mevcutDugum = new Dugum(null, baslangicX, baslangicY, 0, 0);
        this.baslangicX = baslangicX;
        this.baslangicY = baslangicY;
    }

    //Objeyi yeniden tanımlamadan özelliklerini sifirliyor.
    public void sifirla() {
        //Constructor ile ayni yapida bu fonksiyon fakat
        //objeyi tekrar initialize etmeden özelliklerini resetleyerek
        //tekrardan kullanmayı ve bellekten tasarruf etmeyi sağlıyor.
        this.acikKume = new ArrayList<>();
        this.kapaliKume = new ArrayList<>();
        this.yol = new ArrayList<>();
        this.harita = harita;
        this.mevcutDugum = new Dugum(null, baslangicX, baslangicY, 0, 0);
        this.baslangicX = baslangicX;
        this.baslangicY = baslangicY;
    }

    //Verilen Listede dugumun olup olmadigina bakan fonksiyon
    private static boolean listedeKomsuBul(List<Dugum> liste, Dugum dugum) {
        for (Dugum d : liste) {
            if (d.x == dugum.x && d.y == dugum.y) {
                return true;
            }
        }

        return false;
    }

    //Mevcut dugum ile hedef arasindaki uzakligi donduruyor
    private double uzaklik(int yonx, int yony) { //Dugumden hareketle yonde geliyor
        return Math.abs(this.mevcutDugum.x + yonx - this.bitisX) + Math.abs(this.mevcutDugum.y + yony - this.bitisY); // Manhattan uzakligini returnluyoruz
    }

    private void acikKumeyeKomsuEkle() {
        Dugum dugum;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x != 0 && y != 0) {
                    continue; // 9 yön içinden 4 yön haricindeki yönleri atlıyoruz sadece 4 yöne hareket edicez x ve y den birisi 0sa 4 ana yöndeyizdir 
                }
                dugum = new Dugum(this.mevcutDugum, this.mevcutDugum.x + x, this.mevcutDugum.y + y, this.mevcutDugum.g, this.uzaklik(x, y));
                if ((x != 0 || y != 0) // eğer bir yöndeysek , mevcut Dugumde degilsek
                        && this.mevcutDugum.x + x >= 0 && this.mevcutDugum.x + x < this.harita[0].length // haritanın sınırlarına bakıyoruz
                        && this.mevcutDugum.y + y >= 0 && this.mevcutDugum.y + y < this.harita.length
                        && this.harita[this.mevcutDugum.y + y][this.mevcutDugum.x + x] != -1 // eğer yürünebilir alansa (-1 yürünemez alanları ifade ediyor)
                        && !listedeKomsuBul(this.acikKume, dugum) && !listedeKomsuBul(this.kapaliKume, dugum)) { // hala tamamlanmamissa hala devam ediyorsa
                    dugum.g = dugum.ustDugum.g + 1.; // yatay/dikey birim maaliyeti = 1.0
                    dugum.g += harita[this.mevcutDugum.y + y][this.mevcutDugum.x + x]; // kare icin birim maaliyetini ekliyoruz
                    this.acikKume.add(dugum);
                }
            }
        }
        //acik kumeyi compare metoduyla siraliyoruz
        Collections.sort(this.acikKume);
    }

    // Girilen hedefX ve hedefY parametrelerine göre yol buluyor
    // eğer bulamazsa null dönüyor.
    public List<Dugum> yolBul(int hedefX, int hedefY) {
        this.bitisX = hedefX;
        this.bitisY = hedefY;
        this.kapaliKume.add(this.mevcutDugum);
        acikKumeyeKomsuEkle();

        //AStar bu methodla tetikleniyor. Yol Bulma yardımcı metodlar ile sürüyor.
        while (this.mevcutDugum.x != this.bitisX || this.mevcutDugum.y != this.bitisY) {

            if (this.acikKume.isEmpty()) { // acikKume boşsa yani uygun yol yoksa null döndürüyoruz.
                return null;
            }

            this.mevcutDugum = this.acikKume.get(0); // ilk dugumu yani en dusuk f degeri olan dugumu mevcut belirledik

            this.acikKume.remove(0); // daha sonra acikKumeden siliyoruz

            this.kapaliKume.add(this.mevcutDugum); // daha sonra kapaliKumeye ekliyoruz

            acikKumeyeKomsuEkle();
        }

        this.yol.add(0, this.mevcutDugum);

        while (this.mevcutDugum.x != this.baslangicX || this.mevcutDugum.y != this.baslangicY) {

            this.mevcutDugum = this.mevcutDugum.ustDugum;

            this.yol.add(0, this.mevcutDugum);
        }

        return this.yol;
    }

}
