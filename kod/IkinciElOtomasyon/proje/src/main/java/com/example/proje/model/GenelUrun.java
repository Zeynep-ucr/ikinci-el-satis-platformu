package com.example.proje.model;

import java.time.LocalDateTime;

public class GenelUrun extends Urun {

    // ==================================================
    // 🔹 DAO → DB’den okunan ürünler için constructor
    // (user_id DB’den gelir)
    // ==================================================
    public GenelUrun(
            int id,
            String name,
            double fiyat,
            String kategori,
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
                kategori,
                extraInfo,
                photoPath,
                city,
                district,
                ilanTarihi,
                userId
        );
    }

    // ==================================================
    // 🔹 UI tarafı / manuel oluşturulan ürünler
    // (userId otomatik SessionContext’ten gelir)
    // ==================================================
    public GenelUrun(
            int id,
            String name,
            double fiyat,
            String kategori,
            String extraInfo,
            String photoPath,
            String city,
            String district
    ) {
        super(
                id,
                name,
                fiyat,
                kategori,
                extraInfo,
                photoPath,
                city,
                district
        );
    }

    // GUI projede konsol çıktısı istemiyoruz
    @Override
    public void displayDetails() {
        // boş bırakıldı – JavaFX UI kullanılıyor
    }
}
