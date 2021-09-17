package SQL;

import model.OVChipkaart;
import model.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
}
