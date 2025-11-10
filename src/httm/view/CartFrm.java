package httm.view;

import httm.dao.CartDetailDAO;
import httm.dao.OrderDAO;
import httm.dao.OrderDetailDAO;
import httm.dao.ProductDAO;
import httm.model.Cart;
import httm.model.CartDetail;
import httm.model.Order;
import httm.model.OrderDetail;
import httm.model.User;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class CartFrm extends javax.swing.JFrame implements ActionListener {

    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBuy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JScrollPane scrollProduct;
    private javax.swing.JTable tblProduct;
    private javax.swing.JCheckBox tickSelectAll;

    private User user;
    private Cart cart;
    private List<CartDetail> cartItems;
    private boolean isTblUpdating = false;

    private Function function = new Function();

    public CartFrm(User user, Cart cart) {
        this.user = user;
        this.cart = cart;

        initComponents();
        this.setLocationRelativeTo(null);

        lblWelcome.setText("Xin chào, " + user.getFullname());

        CartDetailDAO cdDao = new CartDetailDAO();
        List<CartDetail> cartItems = cdDao.getAllByCart(cart);
        this.cartItems = cartItems;

        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setRowCount(0);
        int index = 1;
        float total = 0;
        for (CartDetail item : cartItems) {
            total += item.getQuantity() * item.getProduct().getPrice();
            model.addRow(new Object[]{true,
                index++,
                item.getProduct().getName(),
                item.getQuantity(),
                function.formatPrice(item.getProduct().getPrice()),
                function.formatPrice(item.getQuantity() * item.getProduct().getPrice())});
        }

        lblTotal.setText("Tổng tiền: " + function.formatPrice(total));

        btnBack.addActionListener(this);
        btnBuy.addActionListener(this);
        tickSelectAll.addActionListener(this);

        tblProduct.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (isTblUpdating) {
                    return;
                }

                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int col = e.getColumn();
                    try {
                        isTblUpdating = true;
                        if (col == 0) {
                            updateTotal(cartItems);
                            checkIfAllSelected();
                        } else if (col == 3) {
                            updateRowTotal(row, cartItems);
                            updateTotal(cartItems);
                        }
                    } finally {
                        isTblUpdating = false;
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        lblWelcome = new javax.swing.JLabel();
        scrollProduct = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        tickSelectAll = new javax.swing.JCheckBox();
        btnBuy = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("GIỎ HÀNG");

        btnBack.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnBack.setText("Về trang chủ");

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblWelcome.setText("Xin chào, Dang Quoc Khanh");

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Chọn", "STT", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[]{
                true, false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        scrollProduct.setViewportView(tblProduct);
        if (tblProduct.getColumnModel().getColumnCount() > 0) {
            tblProduct.getColumnModel().getColumn(0).setMinWidth(40);
            tblProduct.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblProduct.getColumnModel().getColumn(0).setMaxWidth(40);
            tblProduct.getColumnModel().getColumn(1).setMinWidth(40);
            tblProduct.getColumnModel().getColumn(1).setPreferredWidth(40);
            tblProduct.getColumnModel().getColumn(1).setMaxWidth(40);
            tblProduct.getColumnModel().getColumn(2).setMinWidth(170);
            tblProduct.getColumnModel().getColumn(2).setPreferredWidth(180);
            tblProduct.getColumnModel().getColumn(2).setMaxWidth(190);
            tblProduct.getColumnModel().getColumn(3).setMinWidth(70);
            tblProduct.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblProduct.getColumnModel().getColumn(3).setMaxWidth(70);
            tblProduct.getColumnModel().getColumn(4).setMinWidth(90);
            tblProduct.getColumnModel().getColumn(4).setPreferredWidth(90);
            tblProduct.getColumnModel().getColumn(4).setMaxWidth(90);
            tblProduct.getColumnModel().getColumn(5).setMinWidth(120);
            tblProduct.getColumnModel().getColumn(5).setPreferredWidth(120);
            tblProduct.getColumnModel().getColumn(5).setMaxWidth(120);
        }

        tickSelectAll.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tickSelectAll.setText("Chọn tất cả");
        tickSelectAll.setSelected(true);

        btnBuy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuy.setText("Mua hàng");

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotal.setText("Tổng tiền: 123.456,7 $");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(242, 242, 242)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addComponent(btnBack)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 251, Short.MAX_VALUE)
                                                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnBuy, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(tickSelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(scrollProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnBack)
                                        .addComponent(lblWelcome))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addGap(7, 7, 7)
                                .addComponent(tickSelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(scrollProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnBuy)
                                        .addComponent(lblTotal))
                                .addContainerGap(9, Short.MAX_VALUE))
        );

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = tblProduct.getColumnModel();

        columnModel.getColumn(1).setCellRenderer(centerRenderer);
        columnModel.getColumn(3).setCellRenderer(centerRenderer);
        columnModel.getColumn(4).setCellRenderer(rightRenderer);
        columnModel.getColumn(5).setCellRenderer(rightRenderer);

        tblProduct.setFillsViewportHeight(false);
        scrollProduct.getViewport().setBackground(Color.decode("#EEEEEE"));
        scrollProduct.setBorder(null);

        scrollProduct.setBorder(BorderFactory.createEmptyBorder());
        scrollProduct.setViewportBorder(BorderFactory.createEmptyBorder());
        Color bgColor = this.getContentPane().getBackground();
        scrollProduct.setBackground(bgColor);
        scrollProduct.getViewport().setBackground(bgColor);
        tblProduct.setShowGrid(true);
        tblProduct.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tblProduct.setFillsViewportHeight(false);

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if ((evt.getSource() instanceof JButton) && (((evt.getSource()).equals(btnBack)))) {
            UserHomeFrm homeFrm = new UserHomeFrm(user, cart);
            homeFrm.setVisible(true);
            CartFrm.this.dispose();
        } else if ((evt.getSource() instanceof JCheckBox) && (((evt.getSource()).equals(tickSelectAll)))) {
            boolean isSelected = tickSelectAll.isSelected();
            DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(isSelected, i, 0);
            }
        } else if ((evt.getSource() instanceof JButton) && (((evt.getSource()).equals(btnBuy)))) {
            Object[] opts = {"Có", "Không"};
            int opt = JOptionPane.showOptionDialog(this,
                    "Bạn có chắc chắn muốn mua hàng?",
                    "Thông báo",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opts,
                    opts[0]);

            if (opt == 0) {
                Order order = new Order(user);
                OrderDAO oDao = new OrderDAO();
                if (oDao.addOrder(order)) {
                    DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
                    List<OrderDetail> orderItems = new ArrayList<OrderDetail>();
                    boolean success = true;
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if ((Boolean) model.getValueAt(i, 0)) {
                            CartDetail cartItem = cartItems.get(i);
                            OrderDetail orderItem = new OrderDetail(cartItem.getQuantity(), order, cartItem.getProduct());
                            OrderDetailDAO odDao = new OrderDetailDAO();
                            if (!odDao.addOrderDetail(orderItem)) {
                                success = false;
                                JOptionPane.showMessageDialog(this, "Thêm sản phẩm " + cartItem.getProduct().getName() + " thất bại!");
                                return;
                            } else{
                                ProductDAO pDao = new ProductDAO();
                                pDao.updateQty(orderItem.getProduct(), (-1) * orderItem.getQuantity());
                                CartDetailDAO cDao = new CartDetailDAO();
                                cDao.deleteCartDetail(cartItem);
                                try{
                                    function.sendLog(user.getId(), orderItem.getProduct(), "purchase");
                                } catch (Exception e){
                                    System.out.println("Lỗi khi gửi log!");
                                }
                            }
                        }
                    }
                    if (success) {
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
                            CartFrm.this.dispose();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể tạo đơn hàng!");
                }
            }
        }
    }

    private void updateTotal(List<CartDetail> items) {
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        float total = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            boolean isSelected = (Boolean) model.getValueAt(i, 0);
            if (isSelected) {
                int quantity = (int) model.getValueAt(i, 3);
                float price = items.get(i).getProduct().getPrice();
                total += quantity * price;
            }
        }
        lblTotal.setText("Tổng tiền: " + function.formatPrice(total));
    }

    private void updateRowTotal(int row, List<CartDetail> items) {
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();

        int newQty;
        int productQty = items.get(row).getProduct().getQuantity();
        float price = items.get(row).getProduct().getPrice();
        try {
            newQty = (int) model.getValueAt(row, 3);
            if (newQty < 0) {
                newQty = 0;
            } else if (newQty > productQty) {
                newQty = productQty;
            }
        } catch (Exception e) {
            newQty = 0;
        }

        float rowTotal = newQty * price;
        model.setValueAt(newQty, row, 3);
        model.setValueAt(function.formatPrice(rowTotal), row, 5);
    }

    private void checkIfAllSelected() {
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (!(Boolean) model.getValueAt(i, 0)) {
                tickSelectAll.setSelected(false);
                return;
            }
        }
        tickSelectAll.setSelected(true);
    }
}
