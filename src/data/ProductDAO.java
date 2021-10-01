package data;

import model.OVChipkaart;
import model.Product;
import model.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    public boolean save(Product product) throws SQLException;
    public boolean update (Product product) throws SQLException;
    public boolean delete (Product product) throws SQLException;
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    public List<Product> findAll() throws SQLException;
}
