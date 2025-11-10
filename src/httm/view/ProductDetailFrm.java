package httm.view;

import httm.dao.CartDAO;
import httm.dao.CartDetailDAO;
import httm.dao.OrderDAO;
import httm.dao.OrderDetailDAO;
import httm.dao.ProductDAO;
import httm.model.Cart;
import httm.model.CartDetail;
import httm.model.Order;
import httm.model.OrderDetail;
import httm.model.Product;
import httm.model.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ProductDetailFrm extends javax.swing.JFrame implements ActionListener {

    private JButton btnAddToCart;
    private JButton btnBack;
    private JButton btnBuy;
    private JButton btnViewCart;
    private JLabel jLabel1;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JLabel lblBrand;
    private JLabel lblName;
    private JLabel lblPrice;
    private JLabel lblProductId;
    private JLabel lblQuantity;
    private JLabel lblWelcome;
    private JSpinner spnBuyQty;
    private JTextArea txtDes;

    private User user;
    private Product product;
    private Cart cart;

    private Function function = new Function();

    public ProductDetailFrm(User user, Product product, Cart cart) {
        this.user = user;
        this.product = product;
        this.cart = cart;

        initComponents();
        this.setLocationRelativeTo(null);
        
        try{
            function.sendLog(user.getId(), product, "view");
        } catch (Exception e){
            System.out.println("Lỗi khi gửi log");
        }

        lblWelcome.setText("Xin chào, " + user.getFullname());
        lblProductId.setText("Mã sản phẩm: " + product.getId());
        lblBrand.setText("Hãng: " + product.getBrand());
        lblName.setText("Tên sản phẩm: " + product.getName());
        lblPrice.setText("Giá: " + function.formatPrice(product.getPrice()));
        lblQuantity.setText("Số lượng: " + product.getQuantity());
        txtDes.setText(product.getDes());

        btnAddToCart.addActionListener(this);
        btnBuy.addActionListener(this);
        btnBack.addActionListener(this);
        btnViewCart.addActionListener(this);
    }

    @SuppressWarnings("unchecked")                        
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        lblProductId = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblBrand = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDes = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnBuy = new javax.swing.JButton();
        spnBuyQty = new javax.swing.JSpinner();
        btnBack = new javax.swing.JButton();
        btnViewCart = new javax.swing.JButton();
        btnAddToCart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("XEM CHI TIẾT SẢN PHẨM");

        lblWelcome.setText("Xin chào, [username]");

        lblProductId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblProductId.setText("Mã sản phẩm: [id]");

        lblName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblName.setText("Tên sản phẩm: [name]");

        lblBrand.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblBrand.setText("Hãng: [brand]");

        lblQuantity.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblQuantity.setText("Số lượng tồn kho: [quantity]");

        lblPrice.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPrice.setText("Giá thành: [price]");

        jScrollPane1.setBorder(null);

        txtDes.setEditable(false);
        txtDes.setColumns(20);
        txtDes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDes.setLineWrap(true);
        txtDes.setRows(5);
        txtDes.setText("[description]");
        txtDes.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtDes);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Mô tả sản phẩm: ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Nhập số lượng mua: ");

        btnBuy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuy.setText("Mua ngay");

        spnBuyQty.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnBack.setText("Quay lại");

        btnViewCart.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnViewCart.setText("Giỏ hàng");

        btnAddToCart.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddToCart.setText("Thêm vào giỏ ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                .addComponent(lblPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(lblName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(btnBack))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 138, Short.MAX_VALUE)
                                                                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(spnBuyQty, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(btnAddToCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnBuy))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(49, 49, 49)
                                                                .addComponent(btnViewCart)))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnBack)
                                        .addComponent(lblWelcome))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(btnViewCart))
                                .addGap(18, 18, 18)
                                .addComponent(lblProductId)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBrand)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPrice)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblQuantity)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel9)
                                                .addComponent(spnBuyQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(btnBuy)
                                                .addComponent(btnAddToCart)))
                                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if ((evt.getSource() instanceof JButton) && (((JButton) evt.getSource()).equals(btnBack))) {
            UserHomeFrm homeFrm = new UserHomeFrm(user, cart);
            homeFrm.setVisible(true);
            ProductDetailFrm.this.dispose();
        } else if ((evt.getSource() instanceof JButton) && (((JButton) evt.getSource()).equals(btnAddToCart))) {
            int quantity = (int) spnBuyQty.getValue();
            if (quantity < 0 || quantity == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn số lượng lớn hơn 0");
            } else if (quantity > product.getQuantity()) {
                JOptionPane.showMessageDialog(this, "Không đủ sản phẩm tồn kho!");
            } else {
                CartDetailDAO cdDao = new CartDetailDAO();
                CartDetail cd = new CartDetail(quantity, product, cart);
                if (cdDao.addCartDetail(cd)) {
                    try{
                        function.sendLog(user.getId(), product, "cart");
                    }
                    catch (Exception e){
                        System.out.println("Lỗi khi gửi log");
                    }
                    JOptionPane.showMessageDialog(this, "Thêm vào giỏ hàng thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể thêm vào giỏ hàng");
                }
            }
        } else if ((evt.getSource() instanceof JButton) && (((JButton) evt.getSource()).equals(btnBuy))) {
            Order order = new Order(user);
            int quantity = (int) spnBuyQty.getValue();

            if (quantity < 0 || quantity == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn số lượng lớn hơn 0");
            } else if (quantity > product.getQuantity()) {
                JOptionPane.showMessageDialog(this, "Không đủ sản phẩm tồn kho!");
            } else {
                Object[] opts = {"Có", "Không"};
                int opt = JOptionPane.showOptionDialog(this,
                        "Bạn có chắc chắn muốn mua sản phẩm này?",
                        "Thông báo",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        opts,
                        opts[0]);

                if (opt == 0) {
                    OrderDetail od = new OrderDetail(quantity, order, product);
                    OrderDAO oDao = new OrderDAO();
                    if (oDao.addOrder(order)) {
                        OrderDetailDAO odDao = new OrderDetailDAO();
                        if (odDao.addOrderDetail(od)) {
                            ProductDAO pDao = new ProductDAO();
                            pDao.updateQty(product, (-1) * quantity);
                            lblQuantity.setText("Số lượng: " + product.getQuantity());
                            function.sendLog(user.getId(), product, "purchase");
                            Object[] options = {"Xem đơn hàng", "Trở về"};
                            int option = JOptionPane.showOptionDialog(this,
                                    "Mua hàng thành công",
                                    "Thông báo",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null,
                                    options,
                                    options[1]);
//                        JOptionPane.showMessageDialog(this, "Mua hàng thành công");
                            if (option == 0) {
                                Order selectedOrder = oDao.getOrderById(order.getId());
                                OrderFrm orderFrm = new OrderFrm(user, selectedOrder);
                                orderFrm.setVisible(true);
                                ProductDetailFrm.this.dispose();
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào đơn hàng thất bại");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể tạo đơn hàng");
                    }
                }
            }
        } else if ((evt.getSource() instanceof JButton) && (((JButton) evt.getSource()).equals(btnViewCart))) {
            CartFrm cartFrm = new CartFrm(user, cart);
            cartFrm.setVisible(true);
            ProductDetailFrm.this.dispose();
        }
    }
}
