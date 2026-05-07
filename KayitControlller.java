package com.giymatch.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.giymatch.model.Kullanici;
import com.giymatch.model.VeritabaniBaglantisi;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class KayitController {

    @FXML private TextField txtYeniKullaniciAdi;
    @FXML private TextField txtEposta;
    @FXML private PasswordField txtYeniSifre;
    @FXML private Label lblKayitHata;

    @FXML
    void kayitOlButonu(ActionEvent event) {
        String ad = txtYeniKullaniciAdi.getText();
        String mail = txtEposta.getText();
        String sifre = txtYeniSifre.getText();

        if (ad.isEmpty() || mail.isEmpty() || sifre.isEmpty()) {
            lblKayitHata.setText("Lütfen tüm alanları doldurun!");
            lblKayitHata.setStyle("-fx-text-fill: red;");
            return;
        }

        // Yeni kullanıcı nesnesi 
        Kullanici yeniKullanici = new Kullanici(0, ad, sifre, mail);
        
        // Veritabanına kayıt 
        boolean sonuc = VeritabaniBaglantisi.kullaniciKaydet(yeniKullanici);

        if (sonuc) {
            System.out.println("Kayıt Başarılı!");
            sayfayiDegistir(event, "/com/giymatch/view/girisEkrani.fxml");
        } else {
            lblKayitHata.setText("Bu kullanıcı adı zaten alınmış!");
            lblKayitHata.setStyle("-fx-text-fill: red;");
        }
    }

    private void sayfayiDegistir(ActionEvent event, String fxmlYolu) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlYolu));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
