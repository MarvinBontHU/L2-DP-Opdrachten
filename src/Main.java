import java.sql.*;

public class Main
{
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=wachtwoord";

        try {
            Connection conn = DriverManager.getConnection(url);

            //Query
            Statement st = conn.createStatement();
            String query = "SELECT * FROM reiziger";

            ResultSet rs = st.executeQuery(query);

            String r_id, r_vl, r_tv, r_a, r_gb;

            System.out.println("Alle reizigers:");
            while (rs.next()) {
                r_id = rs.getString("reiziger_id");
                r_vl = rs.getString("voorletters");

                if (rs.getString("tussenvoegsel") != null){
                    r_tv = rs.getString("tussenvoegsel");
                } else {
                    r_tv = "";
                }

                r_a = rs.getString("achternaam");
                r_gb = rs.getString("geboortedatum");

                System.out.println("#"+r_id+": "+ r_vl +". "+r_tv+" "+r_a+" ("+r_gb+")");
            }

            rs.close();
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
