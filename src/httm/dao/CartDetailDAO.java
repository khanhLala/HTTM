package httm.dao;

import httm.model.Cart;
import httm.model.CartDetail;
import httm.model.Product;
import httm.model.User;
import java.sql.*;
import java.util.*;

public class CartDetailDAO extends DAO {

    public CartDetailDAO() {
        super();
    }

    public List<CartDetail> getAllByUser(User user) {
        List<CartDetail> items = new ArrayList<CartDetail>();

        String sql = "SELECT cd.id, cd.quantity, cd.createdTime, "
                + "p.id as productid, p.name, p.price, p.brand, p.categorycode, p.des, p.quantity as productqty, "
                + "c.id as cartid, c.lastModifiedTime "
                + "FROM tblCartDetail cd "
                + "JOIN tblProduct p ON cd.tblProductid = p.id "
                + "JOIN tblCart c ON cd.tblCartid = c.id "
                + "WHERE c.tblUserid = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, user.getId());

            ResultSet rs = ps.executeQuery();

            // vì cart sử dụng 1 cho 1 user, chỉ cần tạo 1 lần
            Cart cart = null;
            while (rs.next()) {
                if (cart == null) {
                    cart = new Cart(rs.getInt("cartid"),
                            rs.getTimestamp("lastModifiedTime").toLocalDateTime(),
                            user);
                }

                Product product = new Product(rs.getInt("productid"),
                        rs.getInt("productqty"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("des"),
                        rs.getString("categorycode"),
                        rs.getFloat("price"));

                CartDetail cd = new CartDetail(rs.getInt("id"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("createdTime").toLocalDateTime(),
                        product,
                        cart);
                items.add(cd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
    
    public List<CartDetail> getAllByCart(Cart cart) {
        List<CartDetail> items = new ArrayList<CartDetail>();

        String sql = "SELECT cd.id, cd.quantity, cd.createdTime, "
                + "p.id as productid, p.name, p.price, p.brand, p.des, p.categorycode, p.quantity as productqty "
                + "FROM tblCartDetail cd "
                + "JOIN tblProduct p ON cd.tblProductid = p.id "
                + "WHERE cd.tblCartid = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, cart.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Product product = new Product(rs.getInt("productid"),
                        rs.getInt("productqty"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("des"),
                        rs.getString("categorycode"),
                        rs.getFloat("price"));
  
                CartDetail cd = new CartDetail(rs.getInt("id"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("createdTime").toLocalDateTime(),
                        product,
                        cart);
                items.add(cd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
    
    public boolean addCartDetail(CartDetail cd){
        String sql = "INSERT INTO tblCartDetail (quantity,  tblProductid, tblCartid) "
                + "VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cd.getQuantity());
            ps.setInt(2, cd.getProduct().getId());
            ps.setInt(3, cd.getCart().getId());
            ps.setInt(4, cd.getQuantity());
            
            int ans = ps.executeUpdate();
            
            return ans > 0;
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean deleteCartDetail(CartDetail cd){
        String sql = "DELETE FROM tblCartDetail "
                + "WHERE id = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cd.getId());
            
            int ans = ps.executeUpdate();
            return ans > 0;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateQuantity(CartDetail cd){
        String sql = "UPDATE tblCartDetail "
                + "SET quantity = ? "
                + "WHERE id = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, cd.getQuantity());
            ps.setInt(2, cd.getId());
            
            int ans = ps.executeUpdate();
            return ans > 0;
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return false;
    }
}
