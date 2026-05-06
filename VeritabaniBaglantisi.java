import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeritabaniBaglantisi {
    private static final String URL = "jdbc:sqlite:giymatch.db";
    public static void sistemiBaslat() {
        try (Connection conn = DriverManager.getConnection(URL); 
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS kullanicilar (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "kullanici_adi TEXT UNIQUE, " +
                         "sifre TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS kiyafetler (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "ad TEXT, kategori TEXT, renk TEXT, tarz TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS favoriler (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "kullanici_id INTEGER, " +
                         "kiyafet_id INTEGER, " +
                         "FOREIGN KEY(kullanici_id) REFERENCES kullanicilar(id), " +
                         "FOREIGN KEY(kiyafet_id) REFERENCES kiyafetler(id))");

            System.out.println(">> GiyMatch Veritabanı ve Kullanıcı Sistemi Hazır.");
        } catch (SQLException e) {
            System.err.println("Veritabanı Başlatma Hatası: " + e.getMessage());
        }
    }
    public static boolean kullaniciKayit(String username, String password) {
        String sql = "INSERT INTO kullanicilar(kullanici_adi, sifre) VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            System.out.println(">> Yeni kullanıcı başarıyla kaydedildi: " + username);
            return true;
        } catch (SQLException e) {
            System.err.println("Kayıt Hatası: " + e.getMessage());
            return false;
        }
    }
    public static boolean kullaniciGiris(String username, String password) {
        String sql = "SELECT * FROM kullanicilar WHERE kullanici_adi = ? AND sifre = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next(); // Kayıt bulunduysa true döner
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void kiyafetEkle(Kiyafet k) {
        String sql = "INSERT INTO kiyafetler(ad, kategori, renk, tarz) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, k.getAd());
            pstmt.setString(2, k.getKategori());
            pstmt.setString(3, k.getRenk());
            pstmt.setString(4, k.getTarz());
            pstmt.executeUpdate();
            
            System.out.println(">> Kıyafet Eklendi: " + k.getAd());
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }
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
