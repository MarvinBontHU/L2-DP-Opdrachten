package model;

import java.sql.Date;
import java.util.List;

public class OVChipkaart {
    private int kaart_nummer, klasse;
    private Date geldig_tot;
    private double saldo;
    private Reiziger reiziger;
    private List<Product> producten;

    public OVChipkaart(int kn, Date gt, int kl, double sd){
        this.kaart_nummer = kn;
        this.geldig_tot = gt;
        this.klasse = kl;
        this.saldo = sd;
    }

    public OVChipkaart(int kn, Date gt, int kl, double sd, Reiziger rz){
        this.kaart_nummer = kn;
        this.geldig_tot = gt;
        this.klasse = kl;
        this.saldo = sd;
        this.reiziger = rz;
    }

    public OVChipkaart(int kn, Date gt, int kl, double sd, Reiziger rz, List<Product> pd){
        this.kaart_nummer = kn;
        this.geldig_tot = gt;
        this.klasse = kl;
        this.saldo = sd;
        this.reiziger = rz;
        this.producten = pd;
    }

    public OVChipkaart(int kn, Date gt, int kl, double sd, List<Product> pd){
        this.kaart_nummer = kn;
        this.geldig_tot = gt;
        this.klasse = kl;
        this.saldo = sd;
        this.producten = pd;
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

    public List<Product> getProducten() {
        return producten;
    }

    public void setProducten(List<Product> producten) { this.producten = producten; }


    public boolean deleteProduct(Product product) {
        if (this.producten.contains(product)) {
            this.producten.remove(product);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String text = "OVChipkaart {#" + this.kaart_nummer + " Klasse: " + this.klasse + " Geldig tot : " + this.geldig_tot + " Saldo: €" + this.saldo + "}";
        if (this.getProducten() != null) {
            text = text + this.getProducten();
        }
        return text;
    }
}
