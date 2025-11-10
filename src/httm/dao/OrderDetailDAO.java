package httm.dao;

import httm.model.Order;
import httm.model.OrderDetail;
import httm.model.Product;
import httm.model.User;
import java.sql.*;
import java.util.*;

public class OrderDetailDAO extends DAO {

    public OrderDetailDAO() {
        super();
    }

    public List<OrderDetail> getAllByOrder(Order order) {
        List<OrderDetail> items = new ArrayList<OrderDetail>();

        String sql = "SELECT od.id, od.quantity, od.unitPrice, "
                + "p.id AS productid, p.name, p.brand, p.price, p.quantity as productqty, p.des, p.categorycode "
                + "FROM tblOrderDetail od "
                + "JOIN tblProduct p ON p.id = od.tblProductid "
                + "WHERE od.tblOrderid = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, order.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getInt("productid"),
                        rs.getInt("productqty"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("des"),
                        rs.getString("categorycode"),
                        rs.getFloat("price"));

                OrderDetail od = new OrderDetail(rs.getInt("id"),
                        rs.getInt("quantity"),
                        rs.getFloat("unitPrice"),
                        order,
                        product);

                items.add(od);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public boolean addOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO tblOrderDetail (quantity, unitPrice, tblOrderid, tblProductid) "
                + "VALUES (?, ?, ?, ?)";
        int qty = orderDetail.getQuantity();
        int productQty = orderDetail.getProduct().getQuantity();

        if (qty > productQty) {
            System.out.println("Khong du ton kho!");
            return false;
        }

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, qty);
            ps.setFloat(2, orderDetail.getProduct().getPrice());
            ps.setInt(3, orderDetail.getOrder().getId());
            ps.setInt(4, orderDetail.getProduct().getId());

            int ans = ps.executeUpdate();

            return ans > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<OrderDetail> getNLastestProductByUser(User user, int N) {
        List<OrderDetail> items = new ArrayList<>();

        Map<Integer, Order> createdOrders = new HashMap<>();

        String sql = "SELECT "
                + "od.id as odid, od.quantity, od.unitPrice, "
                + "p.id as productid, p.name, p.brand, p.des, p.categorycode, p.price as currentPrice, p.quantity as productqty, "
                + "o.id as orderid, o.createdTime "
                + "FROM tblOrderDetail od "
                + "JOIN tblProduct p ON od.tblProductid = p.id "
                + "JOIN tblOrder o ON od.tblOrderid = o.id "
                + "WHERE o.tblUserid = ? "
                + "ORDER BY o.createdTime DESC "
                + "LIMIT ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setInt(2, N);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderid");
                Order order;

                if (createdOrders.containsKey(orderId)) {
                    order = createdOrders.get(orderId);
                } 
                else {
                    order = new Order(
                            orderId,
                            rs.getTimestamp("createdTime").toLocalDateTime(),
                            user);
                    createdOrders.put(orderId, order);
                }

                Product product = new Product(
                        rs.getInt("productid"),
                        rs.getInt("productqty"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getString("des"),
                        rs.getString("categorycode"),
                        rs.getFloat("currentPrice"));

                OrderDetail od = new OrderDetail(
                        rs.getInt("odid"),
                        rs.getInt("quantity"),
                        rs.getFloat("unitPrice"),
                        order,
                        product);

                items.add(od);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }  
//    public static void main(String[] args) {
//        OrderDetailDAO odDao = new OrderDetailDAO();
//        UserDAO uDao = new UserDAO();
//        User user = uDao.getById(565012671);
//        List<OrderDetail> od = odDao.getNLastestProductByUser(user, 5);
//        for(OrderDetail o : od){
//            System.out.println(o);
//        }
//    }
}
