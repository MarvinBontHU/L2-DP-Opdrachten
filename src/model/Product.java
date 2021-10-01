package model;

import java.util.List;

public class Product {
    private int product_nummer;
    private String naam, beschrijving;
    private double prijs;
    private List<OVChipkaart> ovChipkaarten;

    public Product(int pn, String nm, String bs, double pr) {
        this.product_nummer = pn;
        this.naam = nm;
        this.beschrijving = bs;
        this.prijs = pr;
    }

    public Product(int pn, String nm, String bs, double pr, List<OVChipkaart> ov) {
        this.product_nummer = pn;
        this.naam = nm;
        this.beschrijving = bs;
        this.prijs = pr;
        this.ovChipkaarten = ov;
    }

    public int getProduct_nummer() { return product_nummer; }

    public String getNaam() { return naam; }

    public String getBeschrijving() { return beschrijving; }

    public double getPrijs() { return prijs; }

    public void setPrijs(double prijs) { this.prijs = prijs; }

    public void setBeschrijving(String beschrijving) { this.beschrijving = beschrijving; }

    public void setNaam(String naam) { this.naam = naam; }

    public void setProduct_nummer(int product_nummer) { this.product_nummer = product_nummer; }

    public List<OVChipkaart> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public void setOvChipkaarten(List<OVChipkaart> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    @Override
    public String toString() {
        return "Product{" +
                "#" + product_nummer +
                ", naam: '" + naam + '\'' +
                ", beschrijving: '" + beschrijving + '\'' +
                ", prijs: €" + prijs + "}";
    }
}
