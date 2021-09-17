package SQL;

import model.Reiziger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection conn;
    private AdresDAO adao;
    private OVChipkaartDAO ovdao;

    public ReizigerDAOPsql(Connection conn) throws SQLException{

        this.conn = conn;

    }

    public ReizigerDAOPsql(Connection conn, AdresDAO adao) throws SQLException{

        this.conn = conn;
        this.adao = adao;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            // Query waarbij een Prepared Statement gebruikt wordt om de gegevens van de reiziger mee te geven.

            String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, " +
                    "achternaam, geboortedatum)" +
                    "VALUES (?, ?, ?, ?, ?)";

            // Prepared statement openen.
            PreparedStatement pst = conn.prepareStatement(query);

            // java.Reiziger gegevens meegeven.
            pst.setInt(1, reiziger.getId());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegsel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboortedatum());

            // Statement uitvoeren en boolean in een aparte result toevoegen.
            boolean result = pst.execute();

            // Prepared Statement sluiten.
            pst.close();

            // Als adres niet null is wordt deze ook opgeslagen via adao.
            if (reiziger.getAdres() != null) {
                adao.save(reiziger.getAdres());
            }

            // Result returnen.
            return result;


        } catch (SQLException sqlException) {
            System.err.println("[SQLException] java.Reiziger niet kunnen opslaan :" + sqlException.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] " + npe.getMessage());
        }catch (Exception e) {
            System.err.println("[Exception] Error. : " + e.getMessage() );
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            // Query waarbij een Prepared Statement gebruikt wordt om de gegevens van de reiziger mee te geven.
            String query = "UPDATE reiziger SET achternaam = ? WHERE reiziger_id = ?";

            // Prepared statement openen.
            PreparedStatement pst = conn.prepareStatement(query);

            // java.Reiziger gegevens meegeven.
            pst.setString(1, reiziger.getAchternaam());
            pst.setInt(2, reiziger.getId());

            // Statement uitvoeren en boolean in een aparte result toevoegen.
            boolean result = pst.execute();

            // Prepared Statement sluiten.
            pst.close();

            // Als adres niet null is wordt deze ook opgeslagen via adao.
            if (reiziger.getAdres() != null) {
                adao.save(reiziger.getAdres());
            }

            // Result returnen.
            return result;


        } catch (SQLException sqlException) {
            System.err.println("[SQLException] java.Reiziger met id: " + reiziger.getId() + " niet gevonden." + sqlException.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] " + npe.getMessage());
        }catch (Exception e) {
            System.err.println("[Exception] Error. : " + e.getMessage() );
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            // Als reiziger een adres heeft wordt deze eerst verwijderd om constraint issues te voorkomen.
            if (reiziger.getAdres() !=null) {
                adao.delete(reiziger.getAdres());
            }
            // Query waarbij een Prepared Statement gebruikt wordt om de gegevens van de reiziger mee te geven.
            String query = "DELETE FROM reiziger WHERE reiziger_id = ?";

            // Prepared statement openen.
            PreparedStatement pst = conn.prepareStatement(query);

            // java.Reiziger gegevens meegeven.
            pst.setInt(1, reiziger.getId());

            // Statement uitvoeren en boolean in een aparte result toevoegen.
            boolean result = pst.execute();

            // Prepared Statement sluiten.
            pst.close();


            // Result returnen.
            return result;

        } catch (SQLException sqlException) {
            System.err.println("[SQLException] java.Reiziger delete failed : " + sqlException.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] " + npe.getMessage());
        }catch (Exception e) {
            System.err.println("[Exception] Error. : " + e.getMessage() );
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        try{
            // Statement openen.
            Statement st = conn.createStatement();

            // Query waarbij een Statement gebruikt wordt om een reiziger te zoeken aan de hand van een id.
            String query = "SELECT * FROM reiziger WHERE reiziger_id =" + id ;

            // Resultset aanmaken aan de hand van de query.
            ResultSet rs = st.executeQuery(query);

            // Lege reiziger aanmaken waar een gevonden reiziger's gegevens in worden gestopt.
            Reiziger reiziger = null;

            // While loop waar rekening wordt gehouden met eventuele null tussenvoegsel.
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
                            null,
                            rs.getString("achternaam"),
                            rs.getDate("geboortedatum"));
                }
            }


            // ResultSet en Statement sluiten.
            rs.close();
            st.close();

            // java.Reiziger returnen.
            return reiziger;
        } catch (SQLException sqlException) {
            System.err.println("[SQLException] java.Reiziger met id: " + id + " niet gevonden." + sqlException.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] " + npe.getMessage());
        }catch (Exception e) {
            System.err.println("[Exception] Error. : " + e.getMessage() );
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try{
            //Statement openen.
            Statement st = conn.createStatement();

            // Query om alle info van reizigers met een bepaalde geboortedatum op te vragen.
            String query = "SELECT * FROM reiziger WHERE geboortedatum = '" + datum + "'";

            // Resultset openen aan de hand van query.
            ResultSet rs = st.executeQuery(query);

            // Lege reiziger en lijst aanmaken om later data in te stoppen.
            Reiziger reiziger = null;
            List<Reiziger> reizigers = new ArrayList<>();

            // While loop om data op te vragen.
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

            // ResultSet en Statement sluiten.
            rs.close();
            st.close();

            // Lijst van rijzigers returnen.
            return reizigers;
        } catch (SQLException sqlException) {
            System.err.println("[SQLException] Reizigers met geboortedatum: " + datum + " niet gevonden." + sqlException.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] " + npe.getMessage());
        }catch (Exception e) {
            System.err.println("[Exception] Error. : " + e.getMessage() );
        }
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        try{
            // Statement openen
            Statement st = conn.createStatement();

            // Query om alle reizigers op te vragen.
            String query = "SELECT * FROM reiziger";

            // Resultset openen.
            ResultSet rs = st.executeQuery(query);

            // Lege reiziger en lijst aanmaken om later data in te stoppen.
            Reiziger reiziger = null;
            List<Reiziger> reizigers = new ArrayList<>();

            // While loop om data op te vragen.
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

            // Resultset en Statement sluiten
            rs.close();
            st.close();

            // Reizigers returnen.
            return reizigers;
        } catch (SQLException sqlException) {
            System.err.println("[SQLException] Geen reizigers gevonden. " + sqlException.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] " + npe.getMessage());
        }catch (Exception e) {
            System.err.println("[Exception] Error. : " + e.getMessage() );
        }
        return null;
    }
}
