package com.ozgeburak.yazilimlaboratuvari1;

//Dugum Sınıfı
//AStar algoritmasına direkt olarak kare sınıfı girmiyor çünkü içlerinde
//oyun mekaniği ile ilgili değişken ve görseller barındırıyorlar bellekten 
//tasarruf etmek için yol bulma işlemleri dügüm nesneleriyle gerçekleştirilip
//daha sonra kare objeleriyle ilişkilendiriliyor.
class Dugum implements Comparable {

    public Dugum ustDugum;
    public int x, y;
    public double g;
    public double h;

    Dugum(Dugum parent, int koordinatX, int koordinatY, double g, double h) {
        this.ustDugum = parent;
        this.x = koordinatX;
        this.y = koordinatY;
        this.g = g; // köke olan uzaklik fonksiyonu
        this.h = h; // sezgisel fonk manhat dist kullanıyor
    }
    // f(g+h) degerine gore liste içerisinde karsilastirma ve siralama yapmak
    // için comparable interfaceinin compareTo metodunu override ediyoruz.

    @Override
    public int compareTo(Object karsilastirilacakObje) {
        //Override edilen methodun default parametresi Object sınıfı
        //düğüme dönüştürme yapmamız gerek
        Dugum karsilastirilacak = (Dugum) karsilastirilacakObje;
        return (int) ((this.g + this.h) - (karsilastirilacak.g + karsilastirilacak.h));
    }
}
