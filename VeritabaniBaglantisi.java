import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VeritabaniBaglantisi {
    public static void main(String[] args) {
        Connection baglanti = null;
        try {
            // SQLite veritabanı dosyasının yolu ve adı
            String url = "jdbc:sqlite:test_veritabani.db"; 
            
            // Bağlantıyı kuruyoruz
            baglanti = DriverManager.getConnection(url);
            
            System.out.println("Tebrikler! SQLite veritabanına başarıyla bağlandık.");
            
        } catch (SQLException e) {
            System.out.println("Hata oluştu: " + e.getMessage());
        } finally {
            try {
                if (baglanti != null) {
                    baglanti.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
