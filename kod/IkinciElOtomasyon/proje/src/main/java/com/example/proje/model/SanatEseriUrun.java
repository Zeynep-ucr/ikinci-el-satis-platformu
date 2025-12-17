package com.example.proje.model;

import java.time.LocalDateTime;

public class SanatEseriUrun extends Urun {

    private String sanatci;
    private String malzeme;

    // ==================================================
    // 🔹 DAO → DB’den okunan ürünler
    // user_id DB’den gelir
    // ==================================================
    public SanatEseriUrun(
            int id,
            String name,
            double fiyat,
            String sanatci,
            String malzeme,
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
                "Sanat Eseri",
                extra,          // extra_info
                photoPath,
                city,
                district,
                ilanTarihi,
                userId
        );
        this.sanatci = sanatci;
        this.malzeme = malzeme;
    }

    // ==================================================
    // 🔹 UI → İlan Ver ekranı
    // userId otomatik SessionContext’ten gelir
    // ==================================================
    public SanatEseriUrun(
            int id,
            String name,
            double fiyat,
            String sanatci,
            String malzeme,
            String extra,
            String photoPath,
            String city,
            String district
    ) {
        super(
                id,
                name,
                fiyat,
                "Sanat Eseri",
                extra,          // extra_info
                photoPath,
                city,
                district
        );
        this.sanatci = sanatci;
        this.malzeme = malzeme;
    }

    @Override
    public void displayDetails() {
        // JavaFX UI kullanılıyor
    }

    // ---------- GETTERS & SETTERS ----------

    public String getSanatci() {
        return sanatci;
    }

    public void setSanatci(String sanatci) {
        if (sanatci == null || sanatci.trim().isEmpty()) {
            throw new IllegalArgumentException("Sanatçı boş olamaz!");
        }
        this.sanatci = sanatci;
    }

    public String getMalzeme() {
        return malzeme;
    }

    public void setMalzeme(String malzeme) {
        if (malzeme == null || malzeme.trim().isEmpty()) {
            throw new IllegalArgumentException("Malzeme boş olamaz!");
        }
        this.malzeme = malzeme;
    }
}
