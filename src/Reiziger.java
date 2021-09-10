import java.sql.Date;

public class Reiziger {
    int id;
    String voorletters, tussenvoegsel, achternaam;
    java.sql.Date geboortedatum;

    public Reiziger(int id, String vl, String tv, String an, java.sql.Date gb) {
        this.id = id;
        this.voorletters =  vl;
        this.tussenvoegsel = tv;
        this.achternaam = an;
        this.geboortedatum = gb;
    }

    public int getId() { return id; }
    public String getVoorletters() { return voorletters; }
    public String getTussenvoegsel() { return tussenvoegsel; }
    public String getAchternaam() { return achternaam; }
    public Date getGeboortedatum() { return geboortedatum; }
    public String getNaam() {
        return voorletters + ". " + tussenvoegsel + " " + achternaam;
    }

    public void setId(int id) { this.id = id; }
    public void setVoorletters(String voorletters) { this.voorletters = voorletters; }
    public void setTussenvoegsel(String tussenvoegsel) { this.tussenvoegsel = tussenvoegsel; }
    public void setAchternaam(String achternaam) { this.achternaam = achternaam; }
    public void setGeboortedatum(java.sql.Date geboortedatum) { this.geboortedatum = geboortedatum; }

    @Override
    public String toString() {
        return "#"+id+": "+ getNaam() + " ("+geboortedatum+")";
    }
}
