package com.example.toothless.monlis.data;

public class Data {
    private String id_data, golongan;

    public Data() {
    }

    public Data(String id_data, String golongan) {
        this.id_data = id_data;
        this.golongan = golongan;
    }

    public String getId_data() {
        return id_data;
    }

    public void setId_data(String id_data) {
        this.id_data = id_data;
    }

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

}