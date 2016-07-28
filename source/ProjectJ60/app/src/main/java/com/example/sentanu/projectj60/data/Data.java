package com.example.sentanu.projectj60.data;

/**
 * Created by sentanu on 28/07/2016.
 */
public class Data {
    private String id_quest, judul_quest, status_quest, la_luar, lo_luar, ra_luar, la_dalam, lo_dalam, ra_dalam;

    public Data() {
    }

    public Data(String id_quest, String judul_quest, String status_quest, String la_luar, String lo_luar, String ra_luar, String la_dalam, String lo_dalam, String ra_dalam) {
        this.id_quest = id_quest;
        this.judul_quest = judul_quest;
        this.status_quest = status_quest;
        this.la_luar = la_luar;
        this.lo_luar = lo_luar;
        this.ra_luar = ra_luar;
        this.la_dalam = la_dalam;
        this.lo_dalam = lo_dalam;
        this.ra_dalam = ra_dalam;
    }

    public String getId_quest() {
        return id_quest;
    }

    public void setId_quest(String id_quest) {
        this.id_quest = id_quest;
    }

    public String getJudul_quest() {
        return judul_quest;
    }

    public void setJudul_quest(String judul_quest) {
        this.judul_quest = judul_quest;
    }

    public String getStatus_quest() {
        return status_quest;
    }

    public void setStatus_quest(String status_quest) {
        this.status_quest = status_quest;
    }

    public String getLa_luar() {
        return la_luar;
    }

    public void setLa_luar(String la_luar) {
        this.la_luar = la_luar;
    }

    public String getLo_luar() {
        return lo_luar;
    }

    public void setLo_luar(String lo_luar) {
        this.lo_luar = lo_luar;
    }

    public String getRa_luar() {
        return ra_luar;
    }

    public void setRa_luar(String ra_luar) {
        this.ra_luar = ra_luar;
    }

    public String getLa_dalam() {
        return la_dalam;
    }

    public void setLa_dalam(String la_dalam) {
        this.la_dalam = la_dalam;
    }

    public String getLo_dalam() {
        return lo_dalam;
    }

    public void setLo_dalam(String lo_dalam) {
        this.lo_dalam = lo_dalam;
    }

    public String getRa_dalam() {
        return ra_dalam;
    }

    public void setRa_dalam(String ra_dalam) {
        this.ra_dalam = ra_dalam;
    }
}
