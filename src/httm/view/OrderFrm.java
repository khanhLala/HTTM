package httm.view;

import httm.dao.CartDAO;
import httm.dao.OrderDetailDAO;
import httm.model.Order;
import httm.model.OrderDetail;
import httm.model.Product;
import httm.model.User;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

public class OrderFrm extends javax.swing.JFrame implements ActionListener {
    
    private javax.swing.JButton btnBackHome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTable tblProduct;
    private javax.swing.JLabel lblFullname;
    private javax.swing.JLabel lblOrderid;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUserid;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JScrollPane productScroll;
    
    private User user;
    private Order order;
    
    private Function function = new Function();
    
    public OrderFrm(User user, Order order) {
        this.user = user;
        this.order = order;
        initComponents();
        
        this.setLocationRelativeTo(null);
        
        lblWelcome.setText("Xin chào, " + user.getFullname());
        lblFullname.setText("Tên khách hàng: " + order.getUser().getFullname());
        lblOrderid.setText("Mã đơn hàng: " + order.getId());
        lblUserid.setText("Mã khách hàng: " + order.getUser().getId());
        lblTime.setText("Thời gian tạo đơn: " + function.formatTime(order.getCreatedTime()));
        lblPhone.setText("Số điện thoại: " + order.getUser().getPhoneNumber());
        
        OrderDetailDAO odDao = new OrderDetailDAO();
        List<OrderDetail> items = odDao.getAllByOrder(order);
        
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();        
        model.setRowCount(0);
        int index = 1;
        float total = 0;
        for (OrderDetail item : items) {
            total += item.getQuantity() * item.getPrice();
            model.addRow(new Object[]{index++,
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                function.formatPrice(item.getQuantity() * item.getPrice())});
        }
        
        lblTotal.setText("Tổng tiền: " + function.formatPrice(total));
        
        btnBackHome.addActionListener(this);
    }
    
    @SuppressWarnings("unchecked")    
    private void initComponents() {
        
        jLabel1 = new javax.swing.JLabel();
        btnBackHome = new javax.swing.JButton();
        productScroll = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        lblWelcome = new javax.swing.JLabel();
        lblOrderid = new javax.swing.JLabel();
        lblUserid = new javax.swing.JLabel();
        lblFullname = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("XEM ĐƠN HÀNG");
        
        btnBackHome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnBackHome.setText("Về trang chủ");
        
        productScroll.setBackground(java.awt.Color.lightGray);
        productScroll.setBorder(null);
        
        tblProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null}
                },
                new String[]{
                    "STT", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        productScroll.setViewportView(tblProduct);
        
        lblWelcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblWelcome.setText("Xin chào, Dang Quoc Khanh");
        
        lblOrderid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblOrderid.setText("Mã đơn hàng: 1234567");
        
        lblUserid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblUserid.setText("Mã khách hàng: 65433673");
        
        lblFullname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblFullname.setText("Tên khách hàng: Dang Quoc Khanh");
        
        lblTime.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTime.setText("Thời gian tạo đơn: 11:03:01 ngày 01/11/2025");
        
        lblPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPhone.setText("Số điện thoại: 0854395072");
        
        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotal.setText("Tổng tiền: 123.456,5 $");
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(173, 173, 173)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(19, 19, 19)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(btnBackHome, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(lblOrderid, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(lblTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addComponent(lblUserid, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(productScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 9, Short.MAX_VALUE))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnBackHome)
                                        .addComponent(lblWelcome))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblOrderid)
                                        .addComponent(lblTime))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblUserid)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPhone)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(productScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addComponent(lblTotal)
                                .addGap(17, 17, 17))      
        );
        
        tblProduct.setFillsViewportHeight(false);
        productScroll.getViewport().setBackground(Color.decode("#EEEEEE"));
        productScroll.setBorder(null);
        
        productScroll.setBorder(BorderFactory.createEmptyBorder());
        productScroll.setViewportBorder(BorderFactory.createEmptyBorder());
        Color bgColor = this.getContentPane().getBackground();
        productScroll.setBackground(bgColor);
        productScroll.getViewport().setBackground(bgColor);
        tblProduct.setShowGrid(true);
        tblProduct.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        tblProduct.setFillsViewportHeight(false);
        
        TableColumnModel columnModel = tblProduct.getColumnModel();
        
        TableColumn stt = columnModel.getColumn(0);
        stt.setPreferredWidth(40);
        stt.setMinWidth(30);
        stt.setMaxWidth(50);
        
        TableColumn name = columnModel.getColumn(1);
        name.setPreferredWidth(170);
        stt.setMinWidth(180);
        stt.setMaxWidth(190);
        pack();
    }    
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if ((evt.getSource() instanceof JButton) && (((evt.getSource()).equals(btnBackHome)))) {
            CartDAO cDao = new CartDAO();
            UserHomeFrm homeFrm = new UserHomeFrm(user, cDao.getByUser(user));
            homeFrm.setVisible(true);
            OrderFrm.this.dispose();
        }
    }
}
