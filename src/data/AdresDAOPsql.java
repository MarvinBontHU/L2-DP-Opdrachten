package data;

import model.Adres;
import model.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection conn;
    private ReizigerDAO rdao;

    public AdresDAOPsql(Connection connection){
        this.conn = connection;
    }


    @Override
    public boolean save(Adres adres) throws SQLException{
        try {
            //Query om adres op de slaan
            String query = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";


            // Prepared statement waarbij we de attributen van adres meegeven.
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, adres.getId());
            pst.setString(2, adres.getPostcode());
            pst.setString(3, adres.getHuisnummer());
            pst.setString(4, adres.getStraat());
            pst.setString(5, adres.getWoonplaats());
            pst.setInt(6, adres.getReiziger().getId());

            // Result van de execute in een boolean opslaan.
            boolean result = pst.execute();

            // Prepared statement sluiten.
            pst.close();

            // Result teruggeven.
            return result;

        } catch (SQLException sqe) {
            System.err.println("[SQLException] java.Adres niet gevonden. " + sqe.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] Null data gegeven : " + npe.getMessage());
        } catch (Exception e) {
            System.err.println("[Exception] Error" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Adres adres) {
        try{
            //Query om huisnummer te veranderen.

            String query = "UPDATE adres SET huisnummer = ? WHERE adres_id = ?";


            // Prepared statement waarbij we de relevante attributen van adres meegeven.
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, adres.getHuisnummer());
            pst.setInt(2, adres.getId());

            // Result van de execute in een boolean opslaan.
            boolean result = pst.execute();

            // Prepared statement sluiten.
            pst.close();

            // Result teruggeven.
            return result;


        } catch (SQLException sqe) {
            System.err.println("[SQLException] SQL Error. " + sqe.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] Null Error : " + npe.getMessage());
        } catch (Exception e) {
            System.err.println("[Exception] Error" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            //Query om een adres te deleten, gegeven deze niet null is.
            if (adres != null) {
                String query = "DELETE FROM adres WHERE adres_id = ?";

                // Prepared statement waarbij de het id van adres meegeven.
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, adres.getId());

                // Result van de execute in een boolean opslaan.
                boolean result = pst.execute();

                // Prepared statement sluiten.
                pst.close();

                // Result teruggeven.
                return result;
            }

        } catch(SQLException sqe) {
            System.err.println("[SQLException] Delete failed : " + sqe.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] " + npe.getMessage());
        }catch (Exception e) {
            System.err.println("[Exception] Error. : " + e.getMessage() );
        }
        return false;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try{
            //Statement openen.
            Statement st = conn.createStatement();

            // Query om alle info van reiziger met specifiek id op te vragen.
            String query = "SELECT * FROM adres WHERE reiziger_id = " + reiziger.getId();

            // ResultSet openen.
            ResultSet rs = st.executeQuery(query);

            // Leeg adres aanmaken om data in op te slaan.
            Adres adres = null;

            // While loop waarbij de data in het net aangemaakte adres wordt opgeslagen.
            while (rs.next()) {
                int a_id = rs.getInt("adres_id");
                String a_pc = rs.getString("postcode");
                String a_hn = rs.getString("huisnummer");
                String a_str = rs.getString("straat");
                String a_wp = rs.getString("woonplaats");

                adres = new Adres(a_id, a_pc, a_hn, a_str, a_wp, reiziger);
            }

            // ResultSet en Statement sluiten.
            rs.close();
            st.close();

            // java.Adres returnen
            return adres;

        } catch (SQLException sqlException) {
            System.err.println("[SQLException] java.Adres via reiziger mislukt : " + sqlException.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] " + npe.getMessage());
        }catch (Exception e) {
            System.err.println("[Exception] Error. : " + e.getMessage() );
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        try{
            // Statement Openen
            Statement st = conn.createStatement();

            // Query om alle data in adres op te vragen.
            String query = "SELECT * FROM adres";

            // ResultSet openen aan de hand van query.
            ResultSet rs = st.executeQuery(query);

            // Leeg adres aanmaken om later data in op te slaan.
            Adres adres = null;

            // Lege lijst met adressen aanmaken om te vullen en te returnen.
            List<Adres> adressen = new ArrayList<>();

            // While Loop om data in aangemaakte adres te stoppen en die in de lijst te stoppen.
            while (rs.next()) {

                int a_id = rs.getInt("adres_id");
                String a_pc = rs.getString("postcode");
                String a_hn = rs.getString("huisnummer");
                String a_str = rs.getString("straat");
                String a_wp = rs.getString("woonplaats");

                adres = new Adres(a_id, a_pc, a_hn, a_str, a_wp);
                adressen.add(adres);
            }

            // ResultSet en Statement sluiten.
            rs.close();
            st.close();

            // Lijst met adressen returnen.
            return adressen;

        } catch (SQLException sqlException) {
            System.err.println("[SQLException] findAll Mislukt. : " + sqlException.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] " + npe.getMessage());
        }catch (Exception e) {
            System.err.println("[Exception] Error. : " + e.getMessage() );
        }
        return null;
    }
}
