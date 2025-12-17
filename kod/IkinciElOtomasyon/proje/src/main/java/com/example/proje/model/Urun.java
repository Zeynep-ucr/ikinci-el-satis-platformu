package com.example.proje.model;

import com.example.proje.session.SessionContext;

import java.time.LocalDateTime;

public abstract class Urun {

    // 🔹 USER (Login yok, sabit admin)
    protected int userId;

    // 🔹 DB alanlarıyla birebir
    protected int id;                 // PK
    protected String name;             // name
    protected double fiyat;            // fiyat
    protected String kategori;          // kategori
    protected String extraInfo;         // extra_info
    protected String photoPath;         // photo_path
    protected String city;              // city
    protected String district;          // district
    protected LocalDateTime ilanTarihi; // ilan_tarihi

    // ==================================================
    // 🔹 ANA CONSTRUCTOR (DAO → DB’den okuma)
    // ==================================================
    protected Urun(
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
        this.id = id;
        this.name = name != null ? name : "";
        this.fiyat = fiyat;
        this.kategori = kategori != null ? kategori : "";
        this.extraInfo = extraInfo != null ? extraInfo : "";
        this.photoPath = photoPath;
        this.city = city != null ? city : "";
        this.district = district != null ? district : "";
        this.ilanTarihi = ilanTarihi != null ? ilanTarihi : LocalDateTime.now();
        this.userId = userId;
    }

    // ==================================================
    // 🔹 UI TARAFI CONSTRUCTOR (İlan Ver / Manuel)
    // userId → sabit admin
    // ==================================================
    protected Urun(
            int id,
            String name,
            double fiyat,
            String kategori,
            String extraInfo,
            String photoPath,
            String city,
            String district
    ) {
        this(
                id,
                name,
                fiyat,
                kategori,
                extraInfo,
                photoPath,
                city,
                district,
                LocalDateTime.now(),
                SessionContext.CURRENT_USER_ID
        );
    }

    // ---------- GETTERS ----------
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getFiyat() {
        return fiyat;
    }

    public String getKategori() {
        return kategori;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public LocalDateTime getIlanTarihi() {
        return ilanTarihi;
    }

    // ---------- SETTERS ----------
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name != null ? name : "";
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori != null ? kategori : "";
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo != null ? extraInfo : "";
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setCity(String city) {
        this.city = city != null ? city : "";
    }

    public void setDistrict(String district) {
        this.district = district != null ? district : "";
    }

    public void setIlanTarihi(LocalDateTime ilanTarihi) {
        this.ilanTarihi = ilanTarihi != null ? ilanTarihi : LocalDateTime.now();
    }

    // 🔹 GUI projelerinde boş bırakılır
    public abstract void displayDetails();

    @Override
    public String toString() {
        return "Urun{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", fiyat=" + fiyat +
                ", kategori='" + kategori + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}
