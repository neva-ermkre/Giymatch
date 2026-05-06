package com.giymatch.model;

public class Kullanici {
    // 1. Değişkenler (Private - Sadece bu sınıf içinden erişilir)
    private int id;
    private String kullaniciAdi;
    private String sifre;
    private String eposta;

    // 2. Constructor (Yapıcı Metot) 
    // Yeni bir kullanıcı nesnesi oluştururken bilgileri doldurmamızı sağlar
    public Kullanici(int id, String kullaniciAdi, String sifre, String eposta) {
        this.id = id;
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.eposta = eposta;
    }

    // 3. Getter Metotları
    // Diğer sınıfların (örneğin Controller veya Veritabanı) bu bilgilere ulaşması için
    public int getId() {
        return id;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public String getEposta() {
        return eposta;
    }
}
