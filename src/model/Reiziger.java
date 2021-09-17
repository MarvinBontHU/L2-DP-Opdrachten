package model;

import java.sql.Date;

public class Reiziger {
    private int id;
    private String voorletters, tussenvoegsel, achternaam;
    private java.sql.Date geboortedatum;
    private Adres adres;
    private OVChipkaart ovchipkaart;

    public Reiziger(int id, String vl, String tv, String an, java.sql.Date gb) {
        this.id = id;
        this.voorletters =  vl;
        this.tussenvoegsel = tv;
        this.achternaam = an;
        this.geboortedatum = gb;
    }

    public Reiziger(int id, String vl, String tv, String an, java.sql.Date gb, Adres ad) {
        this.id = id;
        this.voorletters =  vl;
        this.tussenvoegsel = tv;
        this.achternaam = an;
        this.geboortedatum = gb;
        this.adres = ad;
    }

    public Reiziger(int id, String vl, String tv, String an, java.sql.Date gb, OVChipkaart ov) {
        this.id = id;
        this.voorletters =  vl;
        this.tussenvoegsel = tv;
        this.achternaam = an;
        this.geboortedatum = gb;
        this.ovchipkaart = ov;
    }

    public int getId() { return id; }
    public String getVoorletters() { return voorletters; }
    public String getTussenvoegsel() { return tussenvoegsel; }
    public String getAchternaam() { return achternaam; }
    public Date getGeboortedatum() { return geboortedatum; }
    public String getNaam() {
        return voorletters + ". " + tussenvoegsel + " " + achternaam;
    }

    public Adres getAdres() { return adres; }

    public void setId(int id) { this.id = id; }
    public void setVoorletters(String voorletters) { this.voorletters = voorletters; }
    public void setTussenvoegsel(String tussenvoegsel) { this.tussenvoegsel = tussenvoegsel; }
    public void setAchternaam(String achternaam) { this.achternaam = achternaam; }
    public void setGeboortedatum(java.sql.Date geboortedatum) { this.geboortedatum = geboortedatum; }

    public void setAdres(Adres adres) { this.adres = adres; }

    @Override
    public String toString() {
        if (this.getAdres() != null) {
            return "java.Reiziger {#"+id+": "+ getNaam() + ", geb. "+geboortedatum+", java.Adres"+ adres.toString() +" }";
        }

        return "java.Reiziger {#"+id+": "+ getNaam() + ", geb. "+geboortedatum+"}";
    }
}
