package com.giymatch.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.giymatch.model.VeritabaniBaglantisi;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class GirisController {
	@FXML
	void ekleButonunaBasildi(ActionEvent event) {
	    System.out.println("Butona basıldı");
	}

    @FXML private TextField txtKullaniciAdi;
    @FXML private PasswordField txtSifre;
    @FXML private Label lblHata;

    // "Giriş Yap" butonuna tıklandığında çalışıyo
    @FXML
    void girisYapButonu(ActionEvent event) {
        String ad = txtKullaniciAdi.getText();
        String sifre = txtSifre.getText();

        // Veritabanından kontrol ediyoruz safiye
        boolean basariliMi = VeritabaniBaglantisi.girisKontrol(ad, sifre);

        if (basariliMi) {
            System.out.println("Giriş başarılı! Ana ekrana geçiliyor...");
            sayfayiDegistir(event, "/com/giymatch/view/anaEkran.fxml");
        } else {
            lblHata.setText("Kullanıcı adı veya şifre hatalı!");
            lblHata.setStyle("-fx-text-fill: red;");
        }
    }

    // kaıt ekranına götürür
    @FXML
    void kayitOlSayfasinaGit(ActionEvent event) {
        System.out.println("Kayıt ekranına gidiliyor...");
        sayfayiDegistir(event, "/com/giymatch/view/kayitEkrani.fxml");
    }

    // Ekran değiştirmek için ortak yardımcı metodumuz
    private void sayfayiDegistir(ActionEvent event, String fxmlYolu) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlYolu));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Sayfa yükleme hatası: " + e.getMessage());
        }
    }
}
