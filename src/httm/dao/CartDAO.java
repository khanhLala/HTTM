package httm.dao;

import httm.model.Cart;
import httm.model.User;
import java.sql.*;

public class CartDAO extends DAO{

    public CartDAO() {
        super();
    }
    
    public Cart getByUser(User user){
        String sql = "SELECT * FROM tblCart "
                + "WHERE tblUserid = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Cart(rs.getInt("id"),
                rs.getTimestamp("lastModifiedTime").toLocalDateTime(),
                user);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
}
