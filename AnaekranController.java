import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AnaEkranController {

    @FXML
    private Button ekleButonu;

    @FXML
    void ekleButonunaBasildi(ActionEvent event) {
        //  Konsola bilgi veriyoruz
        System.out.println("Butona basıldı! Veritabanına kayıt ekleniyor...");

        try {
           
            Kiyafet yeniKiyafet = new Kiyafet(0, "Mavi Tişört", "Üst Giyim", "Mavi", "Spor");
            VeritabaniBaglantisi.kiyafetEkle(yeniKiyafet);

            System.out.println("Başarılı: Mavi Tişört veritabanına kaydedildi!");
        } catch (Exception e) {
            System.err.println("Kayıt sırasında bir hata oluştu: " + e.getMessage());
        }
    }
}
