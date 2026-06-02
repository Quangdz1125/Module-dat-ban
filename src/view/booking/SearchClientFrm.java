package view.booking;

import view.ReceptionistHomeFrm;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.booking.ClientDAO;
import model.booking.Booking;
import model.booking.Client;
import model.User;

public class SearchClientFrm extends JFrame implements ActionListener {
    private JTextField txtKey;
    private JButton btnSearch, btnAddClient, btnBack;
    private JTable tblResult;
    private User user;
    private Booking booking;
    private ArrayList<Client> listClient;

    public SearchClientFrm(User user, Booking booking) {
        super("Tìm Khách Hàng");
        this.user = user;
        this.booking = booking;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));

        JPanel pnInputs = new JPanel(new FlowLayout());
        pnInputs.add(new JLabel("Tên / SĐT:"));
        txtKey = new JTextField(15);
        pnInputs.add(txtKey);
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(this);
        pnInputs.add(btnSearch);

        btnAddClient = new JButton("Thêm Khách Mới");
        btnAddClient.addActionListener(this);
        pnInputs.add(btnAddClient);

        btnBack = new JButton("Về Trang Chủ");
        btnBack.addActionListener(this);
        pnInputs.add(btnBack);

        pnMain.add(pnInputs);

        tblResult = new JTable();
        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblResult.getSelectedRow();
                if (row >= 0) {
                    booking.setClient(listClient.get(row));
                    new BookingConfirmFrm(user, booking).setVisible(true);
                    dispose();
                }
            }
        });

        pnMain.add(new JScrollPane(tblResult));
        this.setContentPane(pnMain);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnSearch)) {
            ClientDAO cd = new ClientDAO();
            listClient = cd.searchClient(txtKey.getText());
            String[] columns = { "ID", "Tên", "Email", "SĐT", "Địa chỉ" };
            String[][] data = new String[listClient.size()][5];
            for (int i = 0; i < listClient.size(); i++) {
                data[i][0] = String.valueOf(listClient.get(i).getId());
                data[i][1] = listClient.get(i).getName();
                data[i][2] = listClient.get(i).getEmail();
                data[i][3] = listClient.get(i).getTel();
                data[i][4] = listClient.get(i).getAddress();
            }
            tblResult.setModel(new DefaultTableModel(data, columns) {
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            });
        } else if (e.getSource().equals(btnAddClient)) {
            new AddClientFrm(user, booking).setVisible(true);
            this.dispose();
        } else if (e.getSource().equals(btnBack)) {
            new ReceptionistHomeFrm(user).setVisible(true);
            this.dispose();
        }
    }
}
