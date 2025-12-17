package com.example.proje.model;

import java.time.LocalDateTime;

public class SporUrun extends Urun {

    private String marka;
    private String model;

    // ==================================================
    // 🔹 DAO → DB’den okunan ürünler
    // user_id DB’den gelir
    // ==================================================
    public SporUrun(
            int id,
            String name,
            double fiyat,
            String marka,
            String model,
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
                "Spor",
                extra,          // extra_info
                photoPath,
                city,
                district,
                ilanTarihi,
                userId
        );
        this.marka = marka;
        this.model = model;
    }

    // ==================================================
    // 🔹 UI → İlan Ver ekranı
    // userId otomatik SessionContext’ten gelir
    // ==================================================
    public SporUrun(
            int id,
            String name,
            double fiyat,
            String marka,
            String model,
            String extra,
            String photoPath,
            String city,
            String district
    ) {
        super(
                id,
                name,
                fiyat,
                "Spor",
                extra,          // extra_info
                photoPath,
                city,
                district
        );
        this.marka = marka;
        this.model = model;
    }

    @Override
    public void displayDetails() {
        // JavaFX UI kullanılıyor
    }

    // ---------- GETTERS & SETTERS ----------

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        if (marka == null || marka.trim().isEmpty()) {
            throw new IllegalArgumentException("Marka boş olamaz!");
        }
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model boş olamaz!");
        }
        this.model = model;
    }
}
