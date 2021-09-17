import java.sql.*;
import java.util.List;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws Exception {
        connection = getConnection();
        AdresDAO postgresAdresDao = new AdresDAOPsql(connection);
        ReizigerDAO postgresReizigerDao = new ReizigerDAOPsql(connection, postgresAdresDao);
        testReizigerDAO(postgresReizigerDao);

        AdresDAO postgresAdresDao2 = new AdresDAOPsql(connection, postgresReizigerDao);
        testAdresDAO(postgresAdresDao2);

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
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");


        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", null, "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        // Update achternaam naar Boeren
        System.out.println("[TEST] Achternaam van Sietske vervangen naar 'Boeren'\n");
        sietske.setAchternaam("Boeren");
        rdao.update(sietske);

        reizigers = rdao.findAll();

        for (Reiziger r : reizigers) {
            if (r.getId() == 77) {
                System.out.println(r+"\n");
            }
        }


        // Delete de aangemaakte sietske
        System.out.print("[TEST] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        rdao.delete(sietske);

        // Nieuwe reiziger aanmaken voor adres test later.
        String marvin_gbdatum = "1995-09-11";
        Reiziger marvin = new Reiziger(100, "M", null, "Bont", java.sql.Date.valueOf(marvin_gbdatum));
        rdao.save(marvin);
    }


    /**
     * P3. Adres DAO: persistentie van twee klassen met een één-op-één-relatie.
     *
     * Deze methode test de CRUD-functionaliteit van de Adres DAO
     *
     * @throws SQLException
     */
    public static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDao -------------");

        // Maak een nieuwe reiziger aan voor later gebruik.
        String marvin_gbdatum = "1995-09-11";
        Reiziger marvin = new Reiziger(100, "M", null, "Bont", java.sql.Date.valueOf(marvin_gbdatum));

        System.out.print("[Test] DELETE : Marvin's adres verwijderen indien hij er al een heeft. \n\n");
        adao.delete(marvin.getAdres());

        System.out.print("[Test] CREATE : Eerst heeft reiziger M. Bont geen adres. Na AdresDAO.save() heeft deze een adres.\n\n");

        Adres mAdres = new Adres(10,"1445GA","14A","Dagmaatstraat","Purmerend", marvin);

        adao.save(mAdres);
        System.out.println(adao.findByReiziger(marvin));

        System.out.print("[Test] UPDATE : Marvin's huisnummer veranderend naar '20b'. \n\n");
        mAdres.setHuisnummer("20b");
        adao.update(mAdres);
        System.out.println(adao.findByReiziger(marvin));

        System.out.println("[TEST] AdresDAO findAll() check. \n");
        List<Adres> adressen = adao.findAll();
        for (Adres a : adressen) {
            System.out.println(a);
        }






//
//        List<Adres> adressen = adao.findAll();
//
//        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
//        for (Adres a : adressen) {
//            System.out.println(a);
//        }
//        System.out.println();

    }

}
