package httm.dao;

import httm.model.Product;
import java.sql.*;
import java.util.*;

public class ProductDAO extends DAO {

    public ProductDAO() {
        super();
    }

    public Product getById(int id) {
        String sql = "SELECT * FROM tblProduct "
                + "WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Product(rs.getInt("id"),
                        rs.getInt("quantity"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("des"),
                        rs.getString("categorycode"),
                        rs.getFloat("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Product> getByIdList(List<Integer> ids) {
        List<Product> result = new ArrayList<Product>();
        if ((ids == null) || ids.isEmpty()){
            return result;
        }
        int size = ids.size();
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < size - 1; i++) {
            sb.append("?, ");
        }
        sb.append("?");
        String paramString = sb.toString();
        String sql = "SELECT * FROM tblProduct "
                + "WHERE id IN (" + paramString + ")";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            int index = 1;
            for (Integer id : ids) {
                ps.setInt(index, id);
                index++;
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getInt("id"),
                        rs.getInt("quantity"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("des"),
                        rs.getString("categorycode"),
                        rs.getFloat("price"));
                result.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateQty(Product product, int qty) {
        String sql = "UPDATE tblProduct "
                + "SET quantity = ? "
                + "WHERE id = ?";

        try {
            int newQty = product.getQuantity() + qty;
            if(newQty < 0){
                return false;
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, newQty);
            ps.setInt(2, product.getId());

            int ans = ps.executeUpdate();

            if (ans > 0){
                product.setQuantity(newQty);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Product> searchProduct(String searchString) {
        List<Product> ans = new ArrayList<Product>();
        if(searchString.equals("")){
            return  ans;
        }

//        String sql = "SELECT * FROM tblProduct "
//                + "WHERE name LIKE ? OR brand LIKE ?";
        String sql = "SELECT *, MATCH(name, brand) AGAINST(? IN BOOLEAN MODE) "
                + "AS score "
                + "FROM tblProduct "
                + "WHERE MATCH(name, brand) AGAINST(? IN BOOLEAN MODE) "
                + "ORDER BY score DESC";

        String[] keywords = searchString.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String keyword : keywords) {
            sb.append("+").append(keyword).append(" ");
        }
        String txtSearch = sb.toString().trim();

        try {
            PreparedStatement ps = con.prepareStatement(sql);

//          String txtSearch = "%" + searchString + "%";
//          ps.setString(index++, txtSearch);
//          ps.setString(index++, txtSearch);
            ps.setString(1, txtSearch);
            ps.setString(2, txtSearch);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getInt("id"),
                        rs.getInt("quantity"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("des"),
                        rs.getString("categorycode"),
                        rs.getFloat("price"));
                ans.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ans;
    }
}
