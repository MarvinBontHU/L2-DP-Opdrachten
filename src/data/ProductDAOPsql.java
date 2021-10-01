package data;

import model.Adres;
import model.OVChipkaart;
import model.Product;
import model.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO{
    private Connection conn;
    private OVChipkaartDAO ovdao;

    public ProductDAOPsql(Connection connection){
        this.conn = connection;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        try{
            //Query om product op de slaan

            String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs)" +
                    "VALUES (?, ?, ?, ?)";

            // Prepared statement waarbij we de attributen van het product meegeven.
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, product.getProduct_nummer());
            pst.setString(2, product.getNaam());
            pst.setString(3, product.getBeschrijving());
            pst.setDouble(4, product.getPrijs());

            // Result van de execute in een boolean opslaan.
            boolean result = pst.execute();

            // Prepared statement sluiten.
            pst.close();

            // Result teruggeven.
            return result;
        } catch (SQLException sqe) {
            System.err.println("[SQLException] Product niet gevonden. " + sqe.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] Null data gegeven : " + npe.getMessage());
        } catch (Exception e) {
            System.err.println("[Exception] Error" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        try{
            //Query om prijs te veranderen.

            String query = "UPDATE product SET prijs = ? WHERE product_nummer = ?";

            // Prepared statement waarbij we de relevante attributen van het product meegeven.
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setDouble(1, product.getPrijs());
            pst.setInt(2, product.getProduct_nummer());

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
    public boolean delete(Product product) throws SQLException {
        try {
            //Query om een product te deleten, gegeven deze niet null is.
            if (product != null) {
                String query = "DELETE FROM product WHERE product_nummer = ?";

                // Prepared statement waarbij het product_nummer van het product wordt meegeven.
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, product.getProduct_nummer());

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
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            //Statement openen.
            Statement st = conn.createStatement();

            // Query om alle info van reiziger met specifiek id op te vragen.
            String query = "SELECT p.product_nummer = ?, p.naam = ?, p.beschrijving = ?, p.prijs = ? FROM product p CROSS JOIN ov_chipkaart " +
                    "WHERE p.kaart_nummer =" + ovChipkaart.getKaart_nummer();

            // ResultSet openen.
            ResultSet rs = st.executeQuery(query);

            // Lege lijst van producten aanmaken om data in op te slaan.
            List<Product> producten = new ArrayList<>();

            while (rs.next()) {
                int pd_kn = rs.getInt("product_nummer");
                String pd_nm = rs.getString("naam");
                String pd_bs = rs.getString("beschrijving");
                double pd_ps = rs.getDouble("prijs");


                Product product = new Product(pd_kn, pd_nm, pd_bs, pd_ps);
                producten.add(product);
            }

            // ResultSet en Statement sluiten.
            rs.close();
            st.close();

            return producten;
        } catch (SQLException sqlException) {
            System.err.println("[SQLException] Producten ophalen mislukt : " + sqlException.getMessage());
        } catch (NullPointerException npe) {
            System.err.println("[NullPointerException] " + npe.getMessage());
        } catch (Exception e) {
            System.err.println("[Exception] Error. : " + e.getMessage() );
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        try{
            // Statement openen
            Statement st = conn.createStatement();

            // Query om alle producten op te vragen.
            String query = "SELECT * FROM product";

            // Resultset openen.
            ResultSet rs = st.executeQuery(query);

            // Leeg product en lijst aanmaken om later data in te stoppen.
            Product product = null;
            List<Product> producten = new ArrayList<>();

            // While loop om data op te vragen.
            while (rs.next()) {
                int pd_kn = rs.getInt("product_nummer");
                String pd_nm = rs.getString("naam");
                String pd_bs = rs.getString("beschrijving");
                double pd_ps = rs.getDouble("prijs");


                product = new Product(pd_kn, pd_nm, pd_bs, pd_ps);
                producten.add(product);
            }

            // Resultset en Statement sluiten
            rs.close();
            st.close();

            // Producten returnen.
            return producten;
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
