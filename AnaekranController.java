package com.giymatch.controller;
import com.giymatch.model.Kiyafet;
import com.giymatch.model.VeritabaniBaglantisi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AnaEkranController {

    @FXML //scene builder için
    private Button ekleButonu; 

    @FXML
    void ekleButonunaBasildi(ActionEvent event) {
        System.out.println("Butona basıldı! Veritabanına kayıt ekleniyor...");

        try {
           
            Kiyafet yeniKiyafet = new Kiyafet(0, "Mavi Tişört", "Üst Giyim", "Mavi", "Spor");
            VeritabaniBaglantisi.kiyafetEkle(yeniKiyafet); //bilgileri vceritabanına çekiyor

            System.out.println("Başarılı: Mavi Tişört veritabanına kaydedildi!");
        } catch (Exception e) {
            System.err.println("Kayıt sırasında bir hata oluştu: " + e.getMessage());
        }
    }
}
