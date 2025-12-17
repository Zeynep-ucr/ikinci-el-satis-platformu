package com.example.proje.service;

import com.example.proje.model.Urun;
import java.util.List;

public interface UrunEkleCikar {

    void urunEkle(Urun urun);

    void urunCikar(int id);

    void urunGuncelle(Urun urun);

    void urunleriGoster();

    Urun urunBul(int id);

    List<Urun> getUrunList();
    List<Urun> kategoriyeGoreFiltrele(String kategori);

    List<Urun> fiyatAraliginaGoreFiltrele(double min, double max);
}
