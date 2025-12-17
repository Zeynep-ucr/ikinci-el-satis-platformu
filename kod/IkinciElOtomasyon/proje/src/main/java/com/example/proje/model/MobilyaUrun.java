package com.example.proje.model;

import java.time.LocalDateTime;

public class MobilyaUrun extends Urun {

    private String malzeme;
    private String boyut;
    private String ozellikler;

    // ==================================================
    // 🔹 DAO → DB’den okunan ürünler
    // user_id DB’den gelir
    // ==================================================
    public MobilyaUrun(
            int id,
            String name,
            double fiyat,
            String malzeme,
            String boyut,
            String ozellikler,
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
                "Mobilya",
                ozellikler,     // extra_info
                photoPath,
                city,
                district,
                ilanTarihi,
                userId
        );
        this.malzeme = malzeme;
        this.boyut = boyut;
        this.ozellikler = ozellikler;
    }

    // ==================================================
    // 🔹 UI → İlan Ver ekranı
    // userId otomatik SessionContext’ten gelir
    // ==================================================
    public MobilyaUrun(
            int id,
            String name,
            double fiyat,
            String malzeme,
            String boyut,
            String ozellikler,
            String photoPath,
            String city,
            String district
    ) {
        super(
                id,
                name,
                fiyat,
                "Mobilya",
                ozellikler,     // extra_info
                photoPath,
                city,
                district
        );
        this.malzeme = malzeme;
        this.boyut = boyut;
        this.ozellikler = ozellikler;
    }

    @Override
    public void displayDetails() {
        // JavaFX UI kullanılıyor
    }

    // ---------- GETTERS & SETTERS ----------

    public String getMalzeme() {
        return malzeme;
    }

    public void setMalzeme(String malzeme) {
        if (malzeme == null || malzeme.trim().isEmpty()) {
            throw new IllegalArgumentException("Malzeme boş olamaz!");
        }
        this.malzeme = malzeme;
    }

    public String getBoyut() {
        return boyut;
    }

    public void setBoyut(String boyut) {
        if (boyut == null || boyut.trim().isEmpty()) {
            throw new IllegalArgumentException("Boyut boş olamaz!");
        }
        this.boyut = boyut;
    }

    public String getOzellikler() {
        return ozellikler;
    }

    public void setOzellikler(String ozellikler) {
        if (ozellikler == null || ozellikler.trim().isEmpty()) {
            throw new IllegalArgumentException("Özellikler boş olamaz!");
        }
        this.ozellikler = ozellikler;
        this.extraInfo = ozellikler; // DB ile senkron
    }
}
