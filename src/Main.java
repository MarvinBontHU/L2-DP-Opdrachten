import data.*;


import model.Adres;
import model.OVChipkaart;
import model.Product;
import model.Reiziger;

import java.sql.*;
import java.util.List;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws Exception {
        connection = getConnection();
        ReizigerDAO postgresReizigerDao = new ReizigerDAOPsql(connection);
        AdresDAO postgresAdresDao = new AdresDAOPsql(connection);
        OVChipkaartDAO postgresOvchipkaartDao = new OVChipkaartDAOPsql(connection);
        ProductDAO postgresProductDao = new ProductDAOPsql(connection);

        testReizigerDAO(postgresReizigerDao);
        testAdresDAO(postgresAdresDao, postgresReizigerDao);
        testOvchipkaartDAO(postgresOvchipkaartDao, postgresReizigerDao);
        testProductDAO(postgresProductDao);
        connection.close();
    }



    private static Connection getConnection() {
        // URL voor connectie met database
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=wachtwoord";
        try {
            // Connectie starten
            return connection = DriverManager.getConnection(url);
        } catch (SQLException sqlException) {
            System.err.println("[SQLException] Something went wrong. " + sqlException.getMessage());
        }
        return null;
    }

    private static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException sqlException) {
            System.err.println("[SQLException] Something went wrong. " + sqlException.getMessage());
        }
    }

    /**
     * P2. java.Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de java.Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test SQL.ReizigerDAO -------------");


        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] SQL.ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", null, "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Aanmaken van een nieuwe reiziger." + "\n" + "Eerst " + reizigers.size() + " reizigers, na SQL.ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        // Update achternaam naar Boeren
        System.out.println("[TEST] Achternaam van Sietske vervangen naar 'Boeren'\n");
        sietske.setAchternaam("Boeren");
        System.out.println("Naam van Sietske voor update :" + "\n" + rdao.findById(77));
        rdao.update(sietske);
        System.out.println("Naam van Sietske na update :" + "\n" + rdao.findById(77) + "\n");



        // Delete de aangemaakte sietske
        System.out.println("[TEST] Verwijderen van de net aangemaakte Sietske.");
        System.out.print("Eerst " + reizigers.size() + " reizigers, na SQL.ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        rdao.delete(sietske);

        // Nieuwe reiziger aanmaken voor adres test later.

    }


    /**
     * P3. java.Adres DAO: persistentie van twee klassen met een één-op-één-relatie.
     *
     * Deze methode test de CRUD-functionaliteit van de java.Adres DAO
     *
     * @throws SQLException
     */
    public static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDao -------------");


        // Maak een nieuwe reiziger aan voor later gebruik.
        String marvin_gbdatum = "1995-09-11";
        Reiziger marvin = new Reiziger(100, "M", null, "Bont", java.sql.Date.valueOf(marvin_gbdatum));
        Adres mAdres = new Adres(10,"1445GA","14A","Dagmaatstraat","Purmerend", marvin);

        // Verwijderen van reiziger en adres
        System.out.print("[Test] DELETE : Marvin en zijn adres verwijderen indien deze bestaan. \n\n");
        adao.delete(mAdres);
        rdao.delete(marvin);
        System.out.println("Het zoeken van Marvin na het verwijderen geeft: " +  rdao.findById(100) + " aan.");

        // Reiziger opslaan.
        rdao.save(marvin);
        System.out.print("[Test] CREATE : Eerst heeft reiziger M. Bont geen adres. Na SQL.AdresDAO.save() heeft deze een adres.\n\n");
        System.out.println("Zonder adres : " + adao.findByReiziger(marvin));
        adao.save(mAdres);
        mAdres.setReiziger(marvin);
        marvin.setAdres(mAdres);
        System.out.println("Met adres : " + adao.findByReiziger(marvin));

        System.out.print("[Test] UPDATE : Marvin's huisnummer veranderend naar '20b'. \n\n");
        mAdres.setHuisnummer("20b");
        adao.update(mAdres);
        System.out.println(adao.findByReiziger(marvin));

        System.out.println("[TEST] SQL.AdresDAO findAll() check. \n");
        List<Adres> adressen = adao.findAll();
        for (Adres a : adressen) {
            System.out.println(a);
        }
    }

    /**
     * P4. Ovchipkaart DAO: Persistentie van twee klassen met een één-op-veel-relatie.
     *
     * Deze methode test de CRUD-functionaliteit van de ovchipkaart DAO
     *
     * @throws SQLException
     */
    public static void testOvchipkaartDAO(OVChipkaartDAO ovdao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test OvchipkaartDao -------------");

        // Nieuwe reiziger en OV aanmaken.
        String jan_gbdatum = "1990-10-06";
        String ov_datum = "2020-01-01";
        Reiziger jan = new Reiziger(115, "J", "de", "Wit", java.sql.Date.valueOf(jan_gbdatum));
        OVChipkaart ov1 = new OVChipkaart(64824, java.sql.Date.valueOf(ov_datum), 1, 250.00, jan);
        OVChipkaart ov2 = new OVChipkaart(97682, java.sql.Date.valueOf(ov_datum), 2, 20.00, jan);

        // Reiziger en ov verwijderen indien deze al aangemaakt zijn.
        System.out.println("[TEST] Verwijderen van bestaande OV chipkaarten van Jan.");
        System.out.println("Huidige kaarten van Jan : ");
        System.out.println(ovdao.findByReiziger(jan) + "\n");
        ovdao.delete(ov1);
        ovdao.delete(ov2);

        System.out.println("Kaarten van Jan na delete: ");
        System.out.println(ovdao.findByReiziger(jan) + "\n");
        rdao.delete(jan);

        System.out.println("[TEST] Find Reiziger Check. Kaarten van Jan na saven : ");
        rdao.save(jan);
        ovdao.save(ov1);
        ovdao.save(ov2);
        System.out.println(ovdao.findByReiziger(jan));

        System.out.print("\n[TEST] UPDATE Check. OV1 Saldo voor update = " + ov1.getSaldo());
        ov1.setSaldo(500.00);
        ovdao.update(ov1);
        System.out.println(". Na update heeft OV1 Saldo = " + ov1.getSaldo() + "\n");

    }

    private static void testProductDAO(ProductDAO pdao) throws SQLException {
        System.out.println("\n---------- Test ProductDAO -------------");

        // Een nieuw testproduct aanmaken
        Product product1 = new Product(7, "Test product", "Een testproduct om te kijken of dit werkt", 15.50);


        System.out.println("[TEST] Opslaan van een nieuw testproduct");
        pdao.save(product1);
        System.out.println(pdao.findAll() + "\n");

        System.out.println("[TEST] Aanpassen van de prijs van een bestaand product. Prijs van testproduct veranderd naar 20 euro");
        product1.setPrijs(20);
        pdao.update(product1);
        System.out.println(pdao.findAll());

        System.out.println("[TEST] Verwijderen van bestaand product.");
        System.out.println("Huidige Producten :");
        System.out.println(pdao.findAll() + "\n");
        pdao.delete(product1);
        System.out.println("Producten na deleten:");
        System.out.println(pdao.findAll() + "\n");


    }


}
