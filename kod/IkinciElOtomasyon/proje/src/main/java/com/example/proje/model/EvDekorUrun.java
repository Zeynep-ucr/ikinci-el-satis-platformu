package com.example.proje.model;

import java.time.LocalDateTime;

public class EvDekorUrun extends Urun {

    private String malzeme;
    private String boyut;

    // ==================================================
    // 🔹 DAO → DB’den okunan ürünler
    // user_id PARAMETRE OLARAK GELİR
    // ==================================================
    public EvDekorUrun(
            int id,
            String name,
            double fiyat,
            String malzeme,
            String boyut,
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
                "Ev Dekor",
                extra,
                photoPath,
                city,
                district,
                ilanTarihi,
                userId
        );
        this.malzeme = malzeme;
        this.boyut = boyut;
    }

    // ==================================================
    // 🔹 UI → İlan Ver
    // userId otomatik (SessionContext)
    // ==================================================
    public EvDekorUrun(
            int id,
            String name,
            double fiyat,
            String malzeme,
            String boyut,
            String extra,
            String photoPath,
            String city,
            String district
    ) {
        super(
                id,
                name,
                fiyat,
                "Ev Dekor",
                extra,
                photoPath,
                city,
                district
        );
        this.malzeme = malzeme;
        this.boyut = boyut;
    }

    @Override
    public void displayDetails() {
        // JavaFX kullanılıyor
    }

    // ---------- GETTERS & SETTERS ----------
    public String getMalzeme() {
        return malzeme;
    }

    public void setMalzeme(String malzeme) {
        this.malzeme = malzeme != null ? malzeme : "";
    }

    public String getBoyut() {
        return boyut;
    }

    public void setBoyut(String boyut) {
        this.boyut = boyut != null ? boyut : "";
    }
}
