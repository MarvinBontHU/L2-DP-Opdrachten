package model;

import java.sql.Date;

public class OVChipkaart {
    private int kaart_nummer, klasse;
    private Date geldig_tot;
    private double saldo;
    private Reiziger reiziger;

    public OVChipkaart(int kn, Date gt, int kl, double sd, Reiziger rz){
        this.kaart_nummer = kn;
        this.geldig_tot = gt;
        this.klasse = kl;
        this.saldo = sd;
        this.reiziger = rz;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaart_nummer=" + kaart_nummer +
                ", klasse=" + klasse +
                ", geldig_tot=" + geldig_tot +
                ", saldo=" + saldo +
                ", reiziger=" + reiziger +
                '}';
    }
}
