package model;

import java.sql.Date;
import java.util.List;

public class Reiziger {
    private int id;
    private String voorletters, tussenvoegsel, achternaam;
    private java.sql.Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovchipkaarten;

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

    public Reiziger(int id, String vl, String tv, String an, java.sql.Date gb, Adres ad, List<OVChipkaart> ov) {
        this.id = id;
        this.voorletters =  vl;
        this.tussenvoegsel = tv;
        this.achternaam = an;
        this.geboortedatum = gb;
        this.adres = ad;
        this.ovchipkaarten = ov;
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

    public List<OVChipkaart> getOvchipkaarten() {
        return ovchipkaarten;
    }

    public void setId(int id) { this.id = id; }
    public void setVoorletters(String voorletters) { this.voorletters = voorletters; }
    public void setTussenvoegsel(String tussenvoegsel) { this.tussenvoegsel = tussenvoegsel; }
    public void setAchternaam(String achternaam) { this.achternaam = achternaam; }
    public void setGeboortedatum(java.sql.Date geboortedatum) { this.geboortedatum = geboortedatum; }

    public void setAdres(Adres adres) { this.adres = adres; }

    public void setOvchipkaarten(List<OVChipkaart> ovchipkaarten) {
        this.ovchipkaarten = ovchipkaarten;
    }

    @Override
    public String toString() {
        String reiziger = "Reiziger {#"+id+": "+ getNaam() + ", geb. "+geboortedatum+"}";
        if (this.getAdres() != null) {
            reiziger = reiziger + ", " + adres +" }";
        }
        if (this.ovchipkaarten != null) {
            if (this.ovchipkaarten.toString() == "[]")
                reiziger = reiziger + " Geen OV-Chipkaart aanwezig.";
            else
                reiziger = reiziger + " " + ovchipkaarten + " }";
        }

        return reiziger;
    }
}
