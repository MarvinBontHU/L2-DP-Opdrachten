package data;

import model.OVChipkaart;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    private Connection conn;
    private OVChipkaartDAO ovdao;

    public ProductDAOPsql(Connection connection) {
        this.conn = connection;
        ovdao = new OVChipkaartDAOPsql(conn);
    }

    @Override
    public boolean save(Product product) throws SQLException {
        //Query om product op de slaan
        String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs)" +
                "VALUES (?, ?, ?, ?)";

        // Prepared statement waarbij we de attributen van het product meegegeven.
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setInt(1, product.getProduct_nummer());
        pst.setString(2, product.getNaam());
        pst.setString(3, product.getBeschrijving());
        pst.setDouble(4, product.getPrijs());

        // Result van de execute in een boolean opslaan.
        boolean result = pst.execute();

        // Prepared statement sluiten.
        pst.close();

        if (product.getOvChipkaarten() != null) {
            for (OVChipkaart ovChipkaart : product.getOvChipkaarten()) {
                ovdao.save(ovChipkaart);
            }
        }

        // Result teruggeven.
        return result;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        //Query om prijs te veranderen.

        String query = "UPDATE product SET prijs = ? WHERE product_nummer = ?";

        // Prepared statement waarbij we de relevante attributen van het product meegegeven.
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setDouble(1, product.getPrijs());
        pst.setInt(2, product.getProduct_nummer());


        // Result van de execute in een boolean opslaan.
        boolean result = pst.execute();

        // Prepared statement sluiten.
        pst.close();

        // Ervoor zorgen dat het product bij elke OvChipkaart aangepast wordt. ( Incompleet )
        List<OVChipkaart> alleKaarten = ovdao.findAll();
        for (OVChipkaart kaart : alleKaarten) {
            if (kaart.getProducten() != null) {
                List<Product> huidigeProducten = kaart.getProducten();
                for (Product huidigProduct : huidigeProducten) {
                    if (huidigProduct.getProduct_nummer() == product.getProduct_nummer()) {
                        huidigProduct.setPrijs(product.getPrijs());
                    }
                }
            }
        }


        // Result teruggeven.
        return result;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        //Query om een product te deleten, gegeven deze niet null is.
        if (product != null) {
            String query = "DELETE FROM product WHERE product_nummer = ?";

            // Prepared statement waarbij het product_nummer van het product wordt meegegeven.
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, product.getProduct_nummer());

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
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
            //Statement openen.
            Statement st = conn.createStatement();

            // Query om alle info van reiziger met specifiek id op te vragen.
            String query = "SELECT p.product_nummer, p.naam, p.beschrijving, p.prijs FROM product p " +
                    "JOIN ov_chipkaart_product ocp " +
                    "ON p.product_nummer = ocp.product_nummer " +
                    "JOIN ov_chipkaart ov " +
                    "ON ocp.kaart_nummer = ov.kaart_nummer " +
                    "WHERE ov.kaart_nummer = " + ovChipkaart.getKaart_nummer();

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
    }

    @Override
    public List<Product> findAll() throws SQLException {
        // Statement openen
        Statement st = conn.createStatement();

        // Query om alle producten op te vragen.
        String query = "SELECT * FROM product";

        // Resultset openen.
        ResultSet rs = st.executeQuery(query);

        // Leeg product en lijst aanmaken om later data in te stoppen.
        Product product;
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
    }
}