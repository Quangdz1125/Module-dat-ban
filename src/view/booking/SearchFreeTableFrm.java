package view.booking;

import view.ReceptionistHomeFrm;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.TableDAO;
import model.booking.Booking;
import model.BookedTable;
import model.Table;
import model.User;

public class SearchFreeTableFrm extends JFrame implements ActionListener {
    private JTextField txtDate, txtTime, txtCapacity;
    private JButton btnSearch, btnBack, btnSelect;
    private JTable tblResult;
    private User user;
    private ArrayList<Table> listTable;

    public SearchFreeTableFrm(User user) {
        super("Tìm Bàn Trống");
        this.user = user;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));

        JPanel pnInputs = new JPanel(new FlowLayout());
        pnInputs.add(new JLabel("Date (yyyy-MM-dd):"));
        txtDate = new JTextField(10);
        pnInputs.add(txtDate);
        pnInputs.add(new JLabel("Time:"));
        txtTime = new JTextField(5);
        pnInputs.add(txtTime);
        pnInputs.add(new JLabel("Capacity:"));
        txtCapacity = new JTextField(3);
        pnInputs.add(txtCapacity);
        pnMain.add(pnInputs);

        JPanel pnButtons = new JPanel(new FlowLayout());
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(this);
        pnButtons.add(btnSearch);
        btnSelect = new JButton("Chọn các bàn");
        btnSelect.addActionListener(this);
        pnButtons.add(btnSelect);
        btnBack = new JButton("Về Trang Chủ");
        btnBack.addActionListener(this);
        pnButtons.add(btnBack);
        pnMain.add(pnButtons);

        tblResult = new JTable();
        // Cho phép chọn nhiều dòng
        tblResult.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        pnMain.add(new JScrollPane(tblResult));
        this.setContentPane(pnMain);
        this.setSize(650, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnSearch)) {
            try {
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(txtDate.getText());
                int capacity = Integer.parseInt(txtCapacity.getText());

                TableDAO td = new TableDAO();
                listTable = td.searchFreeTable(date, txtTime.getText(), capacity);

                String[] columns = { "ID", "Tên", "Số người", "Mô tả" };
                String[][] data = new String[listTable.size()][4];
                for (int i = 0; i < listTable.size(); i++) {
                    data[i][0] = String.valueOf(listTable.get(i).getId());
                    data[i][1] = listTable.get(i).getName();
                    data[i][2] = String.valueOf(listTable.get(i).getCapacity());
                    data[i][3] = listTable.get(i).getDes();
                }
                tblResult.setModel(new DefaultTableModel(data, columns) {
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                });
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hãy điền thông tin hợp lệ!");
            }
        } else if (e.getSource().equals(btnSelect)) {
            int[] rows = tblResult.getSelectedRows();
            if (rows.length == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một bàn!");
                return;
            }
            
            Booking booking = new Booking();
            booking.setUser(user);
            try {
                booking.setBookDate(new SimpleDateFormat("yyyy-MM-dd").parse(txtDate.getText()));
                booking.setTime(txtTime.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            ArrayList<BookedTable> bookedTables = new ArrayList<>();
            for (int r : rows) {
                Table selectedTable = listTable.get(r);
                BookedTable bt = new BookedTable();
                bt.setTable(selectedTable);
                bookedTables.add(bt);
            }
            booking.setBookedTables(bookedTables);

            new SearchClientFrm(user, booking).setVisible(true);
            this.dispose();
        } else if (e.getSource().equals(btnBack)) {
            new ReceptionistHomeFrm(user).setVisible(true);
            this.dispose();
        }
    }
}
