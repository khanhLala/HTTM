package httm.view;

import httm.dao.OrderDetailDAO;
import httm.dao.ProductDAO;
import httm.model.Cart;
import httm.model.OrderDetail;
import httm.model.Product;
import httm.model.User;
import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.net.http.*;
import java.awt.event.*;

public class UserHomeFrm extends JFrame implements ActionListener {

    private JButton btnSearch;
    private JButton btnViewRcm;
    private JButton btnViewCart;
    private JPanel header;
    private JPanel jPanel1;
    private JLabel lblMainPage;
    private JLabel lblWelcome;
    private JScrollPane scrollProduct;
    private JTable tblProduct;
    private JTextField txtSearchProduct;

    private User user;
    private Cart cart;
    private List<Product> products;

    private Function function = new Function();

    public UserHomeFrm(User user, Cart cart) {
        this.user = user;
        this.cart = cart;
        initComponents();
        this.setLocationRelativeTo(null);
        
//        List<Integer> ids = new ArrayList<>(Arrays.asList(4923,4934,4962,4965,4986));
        String apiUrl = "http://26.155.72.159:5000/api/recommend";
        OrderDetailDAO odDao = new OrderDetailDAO();
        List<OrderDetail> lastNProduct = odDao.getNLastestProductByUser(user, 5);
        List<Integer> ids = new ArrayList<Integer>();
        try{
            String rcmString = function.getRecommend(apiUrl, user, lastNProduct);
//            String rcmString = function.getRcm(apiUrl, user, lastNProduct);
            System.out.println(rcmString);
            ids = (function.convertJsonToIdList(rcmString));
        } catch (Exception e){
//            ids = null;
        }
        ProductDAO pDao = new ProductDAO();
        this.products = pDao.getByIdList(ids);

        lblWelcome.setText("Xin chào, " + user.getFullname());

        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setRowCount(0);
        int index = 1;
        for (Product product : this.products) {
            if (product.getBrand().equals("")) {
                product.setBrand("Không có");
            }
            model.addRow(new Object[]{index++, product.getName(), product.getBrand(), function.formatPrice(product.getPrice()), product.getQuantity()});
        }

        btnViewRcm.addActionListener(this);
        btnSearch.addActionListener(this);
        btnViewCart.addActionListener(this);

        tblProduct.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblProduct.rowAtPoint(evt.getPoint());
                int col = tblProduct.columnAtPoint(evt.getPoint());

                if (row >= 0 && (col == 1 || col == 4)) {
                    Product selectedProduct = products.get(row);
                    ProductDetailFrm detailFrame = new ProductDetailFrm(user, selectedProduct, cart);
                    detailFrame.setVisible(true);
                    UserHomeFrm.this.dispose();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        lblMainPage = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        btnViewCart = new javax.swing.JButton();
        scrollProduct = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        txtSearchProduct = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnViewRcm = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblMainPage.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblMainPage.setText("TRANG CHỦ");

        lblWelcome.setText("Xin chào, [username]");

        btnViewCart.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnViewCart.setText("Giỏ hàng");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);

        headerLayout.setHorizontalGroup(
                headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(headerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(headerLayout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblMainPage)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Lò xo 2 (đẩy sang 2 bên)
                                                .addComponent(btnViewCart)))
                                .addContainerGap())
        );

        headerLayout.setVerticalGroup(
                headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                                .addContainerGap(13, Short.MAX_VALUE)
                                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblMainPage)
                                        .addComponent(btnViewCart))
                                .addGap(27, 27, 27))
        );

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null},
                    {null, null, null, null, null}
                },
                new String[]{
                    "STT", "Tên sản phẩm", "Hãng", "Đơn giá", "Tồn kho"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
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
            tblProduct.getColumnModel().getColumn(0).setResizable(false);
            tblProduct.getColumnModel().getColumn(0).setPreferredWidth(3);
            tblProduct.getColumnModel().getColumn(0).setHeaderValue("STT");
            tblProduct.getColumnModel().getColumn(1).setResizable(false);
            tblProduct.getColumnModel().getColumn(1).setHeaderValue("Tên sản phẩm");
            tblProduct.getColumnModel().getColumn(2).setResizable(false);
            tblProduct.getColumnModel().getColumn(2).setPreferredWidth(4);
            tblProduct.getColumnModel().getColumn(2).setHeaderValue("Hãng");
            tblProduct.getColumnModel().getColumn(3).setResizable(false);
            tblProduct.getColumnModel().getColumn(3).setPreferredWidth(4);
            tblProduct.getColumnModel().getColumn(3).setHeaderValue("Đơn giá");
            tblProduct.getColumnModel().getColumn(4).setResizable(false);
            tblProduct.getColumnModel().getColumn(4).setPreferredWidth(4);
            tblProduct.getColumnModel().getColumn(4).setHeaderValue("Tồn kho");
        }

        txtSearchProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSearchProduct.setText("Nhập tên sản phẩm để tìm kiếm...");

        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSearch.setText("Tìm kiếm");

        btnViewRcm.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnViewRcm.setText("Xem đề xuất");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(header, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(btnViewRcm)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                                .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnSearch))
                                        .addComponent(scrollProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSearch)
                                        .addComponent(btnViewRcm))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                .addComponent(scrollProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();

        JTableHeader tableHeader = tblProduct.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        TableColumnModel columnModel = tblProduct.getColumnModel();

        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(3).setCellRenderer(rightRenderer);
        columnModel.getColumn(4).setCellRenderer(rightRenderer);

        tblProduct.setFillsViewportHeight(false);
        scrollProduct.getViewport().setBackground(Color.decode("#EEEEEE"));
        scrollProduct.setBorder(null);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if ((evt.getSource() instanceof JButton) && (((JButton) evt.getSource()).equals(btnViewRcm))) {
            OrderDetailDAO odDao = new OrderDetailDAO();
            String apiUrl = "http://26.155.72.159:5000/api/recommend";
            List<OrderDetail> lastNProduct = odDao.getNLastestProductByUser(user, 5);
            List<Integer> ids = (function.convertJsonToIdList(function.getRecommend(apiUrl, user, lastNProduct)));
//            List<Integer> ids = (function.convertJsonToIdList(function.getRcm(apiUrl, user, lastNProduct)));
            ProductDAO pDao = new ProductDAO();
            this.products = pDao.getByIdList(ids);

            DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
            model.setRowCount(0);
            int index = 1;
            for (Product product : this.products) {
                if (product.getBrand().equals("")) {
                    product.setBrand("Không có");
                }
                model.addRow(new Object[]{index++, product.getName(), product.getBrand(), function.formatPrice(product.getPrice()), product.getQuantity()});
            }
        } else if ((evt.getSource() instanceof JButton) && (((JButton) evt.getSource()).equals(btnSearch))) {
            String searchString = txtSearchProduct.getText();
            ProductDAO pDao = new ProductDAO();
            this.products = pDao.searchProduct(searchString);

            DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
            model.setRowCount(0);
            int index = 1;
            for (Product product : this.products) {
                if (product.getBrand().equals("")) {
                    product.setBrand("Không có");
                }
                model.addRow(new Object[]{index++, product.getName(), product.getBrand(), function.formatPrice(product.getPrice()), product.getQuantity()});
            }
        } else if ((evt.getSource() instanceof JButton) && (((JButton) evt.getSource()).equals(btnViewCart))) {
            CartFrm cartFrm = new CartFrm(user, cart);
            cartFrm.setVisible(true);
            UserHomeFrm.this.dispose();
        }
    }
}
