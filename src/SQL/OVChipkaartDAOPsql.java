package SQL;

import model.OVChipkaart;
import model.Reiziger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    private Connection conn;
    private ReizigerDAO rdao;

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        return null;
    }
}
