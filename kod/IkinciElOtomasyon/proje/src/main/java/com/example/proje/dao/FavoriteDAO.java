package com.example.proje.dao;

import com.example.proje.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoriteDAO {
    public List<Integer> getAllFavoriteUrunIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT urun_id FROM Favorites";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getInt("urun_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }

    // ✅ FAVORİ EKLE
    public void addFavorite(int urunId) {
        String sql = "INSERT INTO Favorites (urun_id) VALUES (?)";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, urunId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ FAVORİ SİL
    public void removeFavorite(int urunId) {
        String sql = "DELETE FROM Favorites WHERE urun_id = ?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, urunId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥🔥🔥 EN ÖNEMLİ METOT
    // ✅ UYGULAMA AÇILINCA DB'DEN FAVORİLERİ GERİ ÇEKER
    public Set<Integer> getAllFavoriteIds() {

        Set<Integer> favoriteIds = new HashSet<>();
        String sql = "SELECT urun_id FROM Favorites";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                favoriteIds.add(rs.getInt("urun_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return favoriteIds;
    }
}
