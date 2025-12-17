package com.example.proje.model;

import java.time.LocalDateTime;

public class KitapUrun extends Urun {

    private String yazar;
    private String yayinEvi;

    // ==================================================
    // 🔹 DAO → DB’den okunan ürünler
    // user_id DB’den gelir
    // ==================================================
    public KitapUrun(
            int id,
            String name,
            double fiyat,
            String yazar,
            String yayinEvi,
            String extra,
            String photoPath,
            String city,
            String district,
            LocalDateTime ilanTarihi,
            int userId
    ) {
        super(
                id,
                name,
                fiyat,
                "Kitap",
                extra,          // extra_info
                photoPath,
                city,
                district,
                ilanTarihi,
                userId
        );
        this.yazar = yazar;
        this.yayinEvi = yayinEvi;
    }

    // ==================================================
    // 🔹 UI → İlan Ver ekranı
    // userId otomatik SessionContext’ten gelir
    // ==================================================
    public KitapUrun(
            int id,
            String name,
            double fiyat,
            String yazar,
            String yayinEvi,
            String extra,
            String photoPath,
            String city,
            String district
    ) {
        super(
                id,
                name,
                fiyat,
                "Kitap",
                extra,          // extra_info
                photoPath,
                city,
                district
        );
        this.yazar = yazar;
        this.yayinEvi = yayinEvi;
    }

    @Override
    public void displayDetails() {
        // JavaFX UI kullanılıyor
    }

    // ---------- GETTERS & SETTERS ----------

    public String getYazar() {
        return yazar;
    }

    public void setYazar(String yazar) {
        if (yazar == null || yazar.trim().isEmpty()) {
            throw new IllegalArgumentException("Yazar boş olamaz!");
        }
        this.yazar = yazar;
    }

    public String getYayinEvi() {
        return yayinEvi;
    }

    public void setYayinEvi(String yayinEvi) {
        if (yayinEvi == null || yayinEvi.trim().isEmpty()) {
            throw new IllegalArgumentException("Yayın Evi boş olamaz!");
        }
        this.yayinEvi = yayinEvi;
    }
}
