package com.example.proje.model;

import java.time.LocalDateTime;

public class DigerUrun extends Urun {

    // ==================================================
    // 🔹 DAO → DB’den okunan ürünler
    // user_id DB’den gelir
    // ==================================================
    public DigerUrun(
            int id,
            String name,
            double fiyat,
            String extraInfo,
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
                "Diğer",
                extraInfo,
                photoPath,
                city,
                district,
                ilanTarihi,
                userId
        );
    }

    // ==================================================
    // 🔹 UI → İlan Ver ekranı
    // userId otomatik SessionContext’ten gelir
    // ==================================================
    public DigerUrun(
            int id,
            String name,
            double fiyat,
            String extraInfo,
            String photoPath,
            String city,
            String district
    ) {
        super(
                id,
                name,
                fiyat,
                "Diğer",
                extraInfo,
                photoPath,
                city,
                district
        );
    }

    @Override
    public void displayDetails() {
        // JavaFX UI kullanılıyor
    }
}
