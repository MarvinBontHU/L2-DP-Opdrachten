package data;

import model.OVChipkaart;
import model.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    private Connection conn;

    public OVChipkaartDAOPsql(Connection connection){
        this.conn = connection;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        //Query om ovchipkaart op de slaan

        String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id)" +
                "VALUES (?, ?, ?, ?, ?)";

        // Prepared statement waarbij we de attributen van ovkaart meegeven.
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setInt(1, ovChipkaart.getKaart_nummer());
        pst.setDate(2, ovChipkaart.getGeldig_tot());
        pst.setInt(3, ovChipkaart.getKlasse());
        pst.setDouble(4, ovChipkaart.getSaldo());
        pst.setInt(5, ovChipkaart.getReiziger().getId());


        // Result van de execute in een boolean opslaan.
        boolean result = pst.execute();

        // Prepared statement sluiten.
        pst.close();


        // Result teruggeven.
        return result;

    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        //Query om saldo te veranderen.

        String query = "UPDATE ov_chipkaart SET saldo = ? WHERE kaart_nummer = ?";

        // Prepared statement waarbij we de relevante attributen van de ovchipkaart meegeven.
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setDouble(1, ovChipkaart.getSaldo());
        pst.setInt(2, ovChipkaart.getKaart_nummer());

        // Result van de execute in een boolean opslaan.
        boolean result = pst.execute();

        // Prepared statement sluiten.
        pst.close();


        // Result teruggeven.
        return result;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        //Query om een ovchipkaart te deleten, gegeven deze niet null is.
        if (ovChipkaart != null) {
            String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";

            // Prepared statement waarbij we de kaartnummer van ovchipkaart meegeven.
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, ovChipkaart.getKaart_nummer());

            // Result van de execute in een boolean opslaan.
            boolean result = pst.execute();

            // Prepared statement sluiten.
            pst.close();

            // Result teruggeven.
            return result;
        }

        return false;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        //Statement openen.
        Statement st = conn.createStatement();

        // Query om alle info van reiziger met specifiek id op te vragen.
        String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = " + reiziger.getId();

        // ResultSet openen.
        ResultSet rs = st.executeQuery(query);

        // Lege lijst van ovchipkaarten aanmaken om data in op te slaan.
        List<OVChipkaart> ovchipkaarten = new ArrayList<OVChipkaart>();

        while (rs.next()) {
            int ov_id = rs.getInt("kaart_nummer");
            Date ov_gt = rs.getDate("geldig_tot");
            int ov_kl = rs.getInt("klasse");
            double ov_sd = rs.getDouble("saldo");


            OVChipkaart ovchipkaart = new OVChipkaart(ov_id, ov_gt, ov_kl, ov_sd, reiziger);
            ovchipkaarten.add(ovchipkaart);
        }

        // ResultSet en Statement sluiten.
        rs.close();
        st.close();

        return ovchipkaarten;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        // Statement openen
        Statement st = conn.createStatement();

        // Query om alle chipkaarten op te vragen.
        String query = "SELECT * FROM ov_chipkaart";

        // Resultset openen.
        ResultSet rs = st.executeQuery(query);

        // Lege ovchipkaart en lijst aanmaken om later data in te stoppen.
        OVChipkaart ovChipkaart = null;
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();

        while (rs.next()) {
            int ov_id = rs.getInt("kaart_nummer");
            Date ov_gt = rs.getDate("geldig_tot");
            int ov_kl = rs.getInt("klasse");
            double ov_sd = rs.getDouble("saldo");


            OVChipkaart ovchipkaart = new OVChipkaart(ov_id, ov_gt, ov_kl, ov_sd);
            ovChipkaarten.add(ovchipkaart);
        }

        // Resultset en Statement sluiten
        rs.close();
        st.close();

        // Ovchipkaarten returnen.
        return ovChipkaarten;
    }


}
