import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeritabaniBaglantisi {
    
    // Veritabanı dosya yolu
    private static final String URL = "jdbc:sqlite:giymatch.db";

    // Sistemi başlatan ve tabloları kuran metot
    public static void sistemiBaslat() {
        try (Connection conn = DriverManager.getConnection(URL); 
             Statement stmt = conn.createStatement()) {
            
            // Kıyafetler tablosu
            stmt.execute("CREATE TABLE IF NOT EXISTS kiyafetler (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "ad TEXT, kategori TEXT, renk TEXT, tarz TEXT)");

            // Favoriler tablosu
            stmt.execute("CREATE TABLE IF NOT EXISTS favoriler (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "kiyafet_id INTEGER UNIQUE)");

            System.out.println(">> GiyMatch Veritabanı başarıyla bağlandı ve hazır.");
        } catch (SQLException e) {
            System.err.println("Veritabanı Başlatma Hatası: " + e.getMessage());
        }
    }

    // Yeni Kıyafet Ekleme
    public static void kiyafetEkle(Kiyafet k) {
        String sql = "INSERT INTO kiyafetler(ad, kategori, renk, tarz) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, k.getAd());
            pstmt.setString(2, k.getKategori());
            pstmt.setString(3, k.getRenk());
            pstmt.setString(4, k.getTarz());
            pstmt.executeUpdate();
            
            System.out.println(">> Veritabanına Eklendi: " + k.getAd());
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }

    // Favoriye (Beğenilenlere) ekleme
    public static void favoriyeEkle(int kiyafetId) {
        String sql = "INSERT OR IGNORE INTO favoriler(kiyafet_id) VALUES(?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, kiyafetId);
            pstmt.executeUpdate();
            System.out.println(">> Ürün Favorilere Eklendi (ID: " + kiyafetId + ")");
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }

    // Tarza göre filtreleme (Algoritma için kritik)
    public static List<Kiyafet> tarzaGoreListele(String secilenTarz) {
        List<Kiyafet> liste = new ArrayList<>();
        String sql = "SELECT * FROM kiyafetler WHERE tarz = ?";
        
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, secilenTarz);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                liste.add(new Kiyafet(
                    rs.getInt("id"), 
                    rs.getString("ad"), 
                    rs.getString("kategori"), 
                    rs.getString("renk"), 
                    rs.getString("tarz")
                ));
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return liste;
    }
}
