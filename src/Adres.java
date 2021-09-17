public class Adres {
    private int id;
    private String postcode, huisnummer, straat, woonplaats;
    private Reiziger reiziger;

    public Adres(int id, String pc, String hn, String st, String wp){
        this.id = id;
        this.postcode = pc;
        this.huisnummer = hn;
        this.straat = st;
        this.woonplaats = wp;
    };

    public Adres(int id, String pc, String hn, String st, String wp, Reiziger rz){
        this.id = id;
        this.postcode = pc;
        this.huisnummer = hn;
        this.straat = st;
        this.woonplaats = wp;
        this.reiziger = rz;
    };

    public int getId() { return id; }
    public String getPostcode() { return postcode; }
    public String getHuisnummer() { return huisnummer; }
    public String getStraat() { return straat; }
    public String getWoonplaats() { return woonplaats; }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setPostcode(String postcode) { this.postcode = postcode; }
    public void setHuisnummer(String huisnummer) { this.huisnummer = huisnummer; }
    public void setStraat(String straat) { this.straat = straat; }
    public void setWoonplaats(String woonplaats) { this.woonplaats = woonplaats; }

    @Override
    public String toString() {
        return "{#" + id + " " + postcode + "-" + huisnummer + "}";
    }
}
