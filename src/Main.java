import java.sql.*;
import java.util.List;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws Exception {
        connection = getConnection();
        ReizigerDAO postgresReizigerDao = new ReizigerDAOPsql(connection);
        testReizigerDAO(postgresReizigerDao);
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
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
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
    }

}
