import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection conn;

    public ReizigerDAOPsql(Connection conn) throws SQLException{

        this.conn = conn;

    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            // Query

            String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, " +
                    "achternaam, geboortedatum)" +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, reiziger.getId());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegsel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboortedatum());

            boolean result = pst.execute();
            pst.close();
            return result;


        } catch (SQLException sqlException) {
            System.err.println("[SQLException] Reiziger met id: " + reiziger.getId() + " niet gevonden." + sqlException.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            // Query

            String query = "UPDATE reiziger SET achternaam = ? WHERE reiziger_id = ?";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, reiziger.getAchternaam());
            pst.setInt(2, reiziger.getId());

            boolean result = pst.execute();
            pst.close();
            return result;


        } catch (SQLException sqlException) {
            System.err.println("[SQLException] Reiziger met id: " + reiziger.getId() + " niet gevonden." + sqlException.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            // Query
            String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);

            pst.setInt(1, reiziger.getId());

            boolean result = pst.execute();
            pst.close();
            return result;




        } catch (SQLException sqlException) {
            System.err.println("[SQLException] Reiziger met id: " + reiziger.getId() + " niet gevonden." + sqlException.getMessage());
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        try{
            //Query
            Statement st = conn.createStatement();
            String query = "SELECT * FROM reiziger WHERE reiziger_id =" + id ;

            ResultSet rs = st.executeQuery(query);

            Reiziger reiziger = null;

            while (rs.next()) {
                if (rs.getString("tussenvoegsel") != null) {
                    reiziger = new Reiziger(rs.getInt("reiziger_id"),
                            rs.getString("voorletters"),
                            rs.getString("tussenvoegsel"),
                            rs.getString("achternaam"),
                            rs.getDate("geboortedatum"));
                } else {
                    reiziger = new Reiziger(rs.getInt("reiziger_id"),
                            rs.getString("voorletters"),
                            "",
                            rs.getString("achternaam"),
                            rs.getDate("geboortedatum"));
                }
            }

            rs.close();
            st.close();
            return reiziger;
        } catch (SQLException sqlException) {
            System.err.println("[SQLException] Reiziger met id: " + id + " niet gevonden." + sqlException.getMessage());
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try{
            //Query
            Statement st = conn.createStatement();
            String query = "SELECT * FROM reiziger WHERE geboortedatum = '" + datum + "'";

            ResultSet rs = st.executeQuery(query);

            Reiziger reiziger = null;
            List<Reiziger> reizigers = null;

            while (rs.next()) {
                int r_id = rs.getInt("reiziger_id");
                String r_vl = rs.getString("voorletters");
                String r_tv = "";
                if (rs.getString("tussenvoegsel") != null) {
                    r_tv = rs.getString("tussenvoegsel");
                }
                String r_an = rs.getString("achternaam");
                Date r_gd = rs.getDate("geboortedatum");

                reiziger = new Reiziger(r_id, r_vl, r_tv, r_an, r_gd);

                reizigers.add(reiziger);
            }

            rs.close();
            st.close();
            return reizigers;
        } catch (SQLException sqlException) {
            System.err.println("[SQLException] Reizigers met geboortedatum: " + datum + " niet gevonden." + sqlException.getMessage());
        }
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        try{
            //Query
            Statement st = conn.createStatement();
            String query = "SELECT * FROM reiziger";


            ResultSet rs = st.executeQuery(query);

            Reiziger reiziger = null;
            List<Reiziger> reizigers = new ArrayList<>();

            while (rs.next()) {
                int r_id = rs.getInt("reiziger_id");
                String r_vl = rs.getString("voorletters");
                String r_tv = "";
                if (rs.getString("tussenvoegsel") != null) {
                    r_tv = rs.getString("tussenvoegsel");
                }
                String r_an = rs.getString("achternaam");
                Date r_gd = rs.getDate("geboortedatum");

                reiziger = new Reiziger(r_id, r_vl, r_tv, r_an, r_gd);
                reizigers.add(reiziger);
            }

            rs.close();
            st.close();
            return reizigers;
        } catch (SQLException sqlException) {
            System.err.println("[SQLException] Geen reizigers gevonden. " + sqlException.getMessage());
        }
        return null;
    }
}
