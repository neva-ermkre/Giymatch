import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Kiyafet {
    private int id;
    private String ad;
    private String kategori; 
    private String renk;     
    private String tarz; // Spor, Şık, Günlük

    public Kiyafet(int id, String ad, String kategori, String renk, String tarz) {
        this.id = id;
        this.ad = ad;
        this.kategori = kategori;
        this.renk = renk;
        this.tarz = tarz;
    }

    // Getter Metotları // 7 hafta dersının bu 
    public int getId() { return id; }
    public String getAd() { return ad; }
    public String getKategori() { return kategori; }
    public String getRenk() { return renk; }
    public String getTarz() { return tarz; }
}
public class VeritabaniBaglantisi {
    
   
    private static final String URL = "jdbc:sqlite:giymatch.db";// bu benım dosyanın urlsı bozulmasaın dıyeydı
    public static void sistemiBaslat() {
        try (Connection conn = DriverManager.getConnection(URL); 
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("CREATE TABLE IF NOT EXISTS kiyafetler (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "ad TEXT, kategori TEXT, renk TEXT, tarz TEXT)");  // tamamen url bırlıkde gemını...

            // Favoriler tablosu
            stmt.execute("CREATE TABLE IF NOT EXISTS favoriler (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, kiyafet_id INTEGER UNIQUE)");

            System.out.println(">> GiyMatch Veritabanı ");
        } catch (SQLException e) {
            System.out.println("Başlatma Hatası: " + e.getMessage());
        }
    }

    // Yeni Kıyafet Ekleme  butona basınca burayı çağıraca  maın karakterı ve algorıtmadan gelıcekler
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
        } catch (SQLException e) { e.printStackTrace(); }
    }

    //  Ürünü favoriye (sağa) ekle
    public static void favoriyeEkle(int kiyafetId) {
        String sql = "INSERT OR IGNORE INTO favoriler(kiyafet_id) VALUES(?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kiyafetId);
            pstmt.executeUpdate();
            System.out.println(">> Ürün Beğenildi! (Favorilere Eklendi)");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    //tarza göre ürünleri listeler
    public static List<Kiyafet> tarzaGoreListele(String secilenTarz) {
        List<Kiyafet> liste = new ArrayList<>();
        String sql = "SELECT * FROM kiyafetler WHERE tarz = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, secilenTarz);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                liste.add(new Kiyafet(rs.getInt("id"), rs.getString("ad"), 
                          rs.getString("kategori"), rs.getString("renk"), rs.getString("tarz")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public static void main(String[] args) {
        sistemiBaslat();
        kiyafetEkle(new Kiyafet(0, "Siyah Pantolon", "Alt Giyim", "Siyah", "Şık"));
        kiyafetEkle(new Kiyafet(0, "Mavi Tişört", "Üst Giyim", "Mavi", "Spor"));

       /* Favoriye Ekleme */
        favoriyeEkle(1);

        /* Filtreleme */
        System.out.println("\n--- Spor Ürünler Listeleniyor ---");
        for (Kiyafet k : tarzaGoreListele("Spor")) {
            System.out.println("Bulunan: " + k.getAd());
        }
    }
}
