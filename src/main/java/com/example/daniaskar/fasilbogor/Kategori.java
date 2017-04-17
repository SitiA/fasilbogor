package com.example.daniaskar.fasilbogor;


public class Kategori {
    public static String TAG_KAT = "data";
    public static String KAT_ID = "id_kategori";
    public static String KAT_NAMA = "nama_kategori";

    private String id_kategori;
    private String nama_kategory;

    public String getId_kategori() {
        return id_kategori;
    }
    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }
    public String getNama_kategory() {
        return nama_kategory;
    }
    public void setNama_kategory(String nama_kategory) {
        this.nama_kategory = nama_kategory;
    }



}
