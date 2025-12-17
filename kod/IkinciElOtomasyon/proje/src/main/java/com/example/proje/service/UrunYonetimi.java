package com.example.proje.service;

import com.example.proje.dao.UrunDAO;
import com.example.proje.model.Urun;

import java.util.Collections;
import java.util.List;

public class UrunYonetimi implements UrunEkleCikar {

    private final UrunDAO urunDAO;

    public UrunYonetimi() {
        this.urunDAO = new UrunDAO();
    }

    // ==================================================
    // 🔹 YENİ ÜRÜN EKLE
    // categoryId UI / Controller’dan gelir
    // user_id DAO içinde otomatik atanır (admin)
    // ==================================================
    public void urunEkle(Urun urun, int categoryId) {
        urunDAO.urunEkle(urun, categoryId);
    }

    // ==================================================
    // 🔹 Interface gereği (kullanılmıyor)
    // ==================================================
    @Override
    public void urunEkle(Urun urun) {
        System.out.println("Uyarı: Bu metot kullanılmıyor. categoryId gerekli.");
    }

    // ==================================================
    // 🔹 SİL
    // ==================================================
    @Override
    public void urunCikar(int id) {
        urunDAO.urunSil(id);
    }

    // ==================================================
    // 🔹 GÜNCELLE (ŞİMDİLİK KAPALI)
    // ==================================================
    @Override
    public void urunGuncelle(Urun urun) {
        System.out.println("Uyarı: Güncelleme geçici olarak devre dışı.");
    }

    // ==================================================
    // 🔹 TÜM ÜRÜNLERİ GÖSTER (KONSOL)
    // ==================================================
    @Override
    public void urunleriGoster() {
        List<Urun> list = getUrunList();
        for (Urun u : list) {
            u.displayDetails();
            System.out.println("------");
        }
    }

    // ==================================================
    // 🔹 ID İLE ÜRÜN BUL
    // ==================================================
    @Override
    public Urun urunBul(int id) {
        return urunDAO.getUrunById(id);
    }

    // ==================================================
    // 🔹 KATEGORİ FİLTRESİ (DEVRE DIŞI)
    // ==================================================
    @Override
    public List<Urun> kategoriyeGoreFiltrele(String kategori) {
        System.out.println("Kategori filtresi devre dışı (normalize DB).");
        return Collections.emptyList();
    }

    // ==================================================
    // 🔹 FİYAT FİLTRESİ (DEVRE DIŞI)
    // ==================================================
    @Override
    public List<Urun> fiyatAraliginaGoreFiltrele(double min, double max) {
        System.out.println("Fiyat filtresi geçici olarak kapalı.");
        return Collections.emptyList();
    }

    // ==================================================
    // 🔹 TÜM ÜRÜNLER (UI TARAFI)
    // ==================================================
    @Override
    public List<Urun> getUrunList() {
        try {
            List<Urun> list = urunDAO.getTumUrunler();
            System.out.println("Service -> gelen ürün sayısı = " + list.size());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
