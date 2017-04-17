package com.example.daniaskar.fasilbogor;


public class InfoDetail {
    public static String TAG_INFO = "data";
    public static String INFO_DETAIL_ID = "id_detail";
    public static String INFO_DETAIL_NAMA = "nama_detail";
    public static String INFO_DETAIL_ALAMAT = "alamat_detail";
    public static String INFO_DETAIL_OPR = "jam_opr_detail";
    public static String INFO_DETAIL_LONG = "long_detail";
    public static String INFO_DETAIL_LAT = "lat_detail";
    public static String INFO_NO_TELP = "no_telepon";

    private String id_info;
    private String nama_info;
    private String alamat;
    private String jam_operasional;
    private String longtitude;
    private String latitude;
    private String phone;

    public String getId_info() {
        return id_info;
    }
    public void setId_info(String id_info) {
        this.id_info = id_info;
    }
    public String getNama_info() {
        return nama_info;
    }
    public void setNama_info(String nama_info) {
        this.nama_info = nama_info;
    }
    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public String getJam_operasional() {
        return jam_operasional;
    }
    public void setJam_operasional(String jam_operasional) {
        this.jam_operasional = jam_operasional;
    }
    public String getLongtitude() {
        return longtitude;
    }
    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }


}
