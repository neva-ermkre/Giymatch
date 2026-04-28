public class Kiyafet {
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

  
    public int getId() { return id; }
    public String getAd() { return ad; }
    public String getKategori() { return kategori; }
    public String getRenk() { return renk; }
    public String getTarz() { return tarz; }
}
