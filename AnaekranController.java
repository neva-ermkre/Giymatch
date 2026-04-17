import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AnaEkranController {

    @FXML
    private Button ekleButonu;

    @FXML
    void ekleButonunaBasildi() {
        System.out.println("Butona basıldı! Veritabanı bağlantısı bekleniyor...");
    }
}
