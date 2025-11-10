package httm.dao;

import httm.model.Order;
import httm.model.User;
import java.sql.*;

public class OrderDAO extends DAO {

    public OrderDAO() {
        super();
    }

    public Order getOrderById(int id) {
        String sql = "SELECT o.id, o.createdTime, "
                + "u.id as userid, u.username, u.fullname, u.phoneNumber, u.email "
                + "FROM tblOrder o "
                + "JOIN tblUser u ON o.tblUserid = u.id "
                + "WHERE o.id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getInt("userid"),
                        rs.getString("username"),
                        rs.getString("fullname"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"));

                return new Order(rs.getInt("id"),
                        rs.getTimestamp("createdTime").toLocalDateTime(),
                        user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addOrder(Order order) {
        String sql = "INSERT INTO tblOrder (tblUserid) "
                + "VALUES (?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUser().getId());

            int ans = ps.executeUpdate();

            if (ans > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);
                    order.setId(orderId);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
