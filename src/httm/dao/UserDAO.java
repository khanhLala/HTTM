package httm.dao;

import httm.model.User;
import java.sql.*;

public class UserDAO extends DAO {

    public UserDAO() {
        super();
    }
    
    public User getById(int id){
        String sql = "SELECT * FROM tblUser "
                + "WHERE id = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("fullname"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User login(User user) {
        String sql = "SELECT * FROM tblUser "
                + "WHERE username = ? AND password = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("fullname"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
