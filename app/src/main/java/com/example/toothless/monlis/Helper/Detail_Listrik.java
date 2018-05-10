package com.example.toothless.monlis.Helper;

public class Detail_Listrik {

    private String id_detail;
    private String biaya_bulanan;
    private String energi_bulanan;
    private String batas_energi;
    private String batas_biaya;

    public String getBatas_biaya() {
        return batas_biaya;
    }

    public void setBatas_biaya(String batas_Biaya) {
        batas_biaya = batas_Biaya;
    }

    public String getBatas_energi() {

        return batas_energi;
    }

    public void setBatas_energi(String batas_Energi) {
        batas_energi = batas_Energi;
    }

    public String getEnergi_bulanan() {
        return energi_bulanan;
    }

    public void setEnergi_bulanan(String energi_bulanan) {
        this.energi_bulanan = energi_bulanan;
    }

    public String getBiaya_bulanan() {

        return biaya_bulanan;
    }

    public void setBiaya_bulanan(String biaya_bulanan) {
        this.biaya_bulanan = biaya_bulanan;
    }

    public String getId_detail() {

        return id_detail;
    }

    public void setId_detail(String id_detail) {
        this.id_detail = id_detail;
    }



}