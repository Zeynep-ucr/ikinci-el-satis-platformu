package com.example.proje.dao;

import com.example.proje.db.DbConnection;
import com.example.proje.model.GenelUrun;
import com.example.proje.model.Urun;
import com.example.proje.session.SessionContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UrunDAO {

    // ==================================================
    // 🔹 TÜM ÜRÜNLER (DB → UI)
    // ==================================================
    public List<Urun> getTumUrunler() {
        List<Urun> list = new ArrayList<>();

        String sql = """
            SELECT 
                u.id,
                u.name,
                u.fiyat,
                u.extra_info,
                u.photo_path,
                c.kategori_adi AS kategori,
                u.city,
                u.district,
                u.ilan_tarihi,
                u.user_id
            FROM Urunler u
            JOIN Categories c ON u.category_id = c.category_id
            ORDER BY u.id DESC
        """;

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new GenelUrun(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("fiyat"),
                        rs.getString("kategori"),
                        rs.getString("extra_info"),
                        rs.getString("photo_path"),
                        rs.getString("city"),
                        rs.getString("district"),
                        rs.getTimestamp("ilan_tarihi").toLocalDateTime(),
                        rs.getInt("user_id")
                ));
            }

            System.out.println("DAO -> çekilen ürün sayısı = " + list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==================================================
    // 🔹 ID İLE TEK ÜRÜN BUL
    // ==================================================
    public Urun getUrunById(int id) {

        String sql = """
            SELECT 
                u.id,
                u.name,
                u.fiyat,
                u.extra_info,
                u.photo_path,
                c.kategori_adi AS kategori,
                u.city,
                u.district,
                u.ilan_tarihi,
                u.user_id
            FROM Urunler u
            JOIN Categories c ON u.category_id = c.category_id
            WHERE u.id = ?
        """;

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new GenelUrun(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("fiyat"),
                        rs.getString("kategori"),
                        rs.getString("extra_info"),
                        rs.getString("photo_path"),
                        rs.getString("city"),
                        rs.getString("district"),
                        rs.getTimestamp("ilan_tarihi").toLocalDateTime(),
                        rs.getInt("user_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ==================================================
    // 🔹 YENİ ÜRÜN EKLE
    // ==================================================
    public void urunEkle(Urun urun, int categoryId) {

        String sql = """
            INSERT INTO Urunler
            (name, fiyat, category_id, extra_info, photo_path, city, district, ilan_tarihi, user_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, urun.getName());
            ps.setDouble(2, urun.getFiyat());
            ps.setInt(3, categoryId);
            ps.setString(4, urun.getExtraInfo());
            ps.setString(5, urun.getPhotoPath());
            ps.setString(6, urun.getCity());
            ps.setString(7, urun.getDistrict());
            ps.setTimestamp(8, Timestamp.valueOf(urun.getIlanTarihi()));
            ps.setInt(9, SessionContext.CURRENT_USER_ID);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================================================
    // 🔹 SİL
    // ==================================================
    public void urunSil(int id) {
        String sql = "DELETE FROM Urunler WHERE id = ?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================================================
    // 🔹 KATEGORİ ADINDAN ID BUL
    // ==================================================
    public int getCategoryIdByName(String kategoriAdi) {

        String sql = "SELECT category_id FROM Categories WHERE kategori_adi = ?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, kategoriAdi);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("category_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Kategori bulunamadı: " + kategoriAdi);
    }
}
