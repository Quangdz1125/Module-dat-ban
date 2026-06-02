package view.booking;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.TableDAO;
import model.Booking;
import model.BookedTable;
import model.Table;
import model.User;

public class SearchFreeTableFrm extends JFrame implements ActionListener {
    private JTextField txtDate, txtTime, txtCapacity;
    private JButton btnSearch, btnBack;
    private JTable tblResult;
    private User user;
    private ArrayList<Table> listTable;
    
    public SearchFreeTableFrm(User user) {
        super("Search Free Table");
        this.user = user;
        
        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        
        JPanel pnInputs = new JPanel(new FlowLayout());
        pnInputs.add(new JLabel("Date (yyyy-MM-dd):"));
        txtDate = new JTextField(10); pnInputs.add(txtDate);
        pnInputs.add(new JLabel("Time:"));
        txtTime = new JTextField(5); pnInputs.add(txtTime);
        pnInputs.add(new JLabel("Capacity:"));
        txtCapacity = new JTextField(3); pnInputs.add(txtCapacity);
        
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);
        pnInputs.add(btnSearch);
        btnBack = new JButton("Back to Home");
        btnBack.addActionListener(this);
        pnInputs.add(btnBack);
        pnMain.add(pnInputs);
        
        tblResult = new JTable();
        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tblResult.getSelectedRow();
                if (row >= 0) {
                    Table selectedTable = listTable.get(row);
                    Booking booking = new Booking();
                    booking.setUser(user);
                    try {
                        booking.setBookDate(new SimpleDateFormat("yyyy-MM-dd").parse(txtDate.getText()));
                        booking.setTime(txtTime.getText());
                    } catch(Exception ex) { ex.printStackTrace(); }
                    
                    BookedTable bt = new BookedTable();
                    bt.setTable(selectedTable);
                    booking.setBookedTables(new ArrayList<>(Arrays.asList(bt)));
                    
                    new SearchClientFrm(user, booking).setVisible(true);
                    dispose();
                }
            }
        });
        
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
                
                String[] columns = {"ID", "Name", "Capacity", "Description"};
                String[][] data = new String[listTable.size()][4];
                for(int i = 0; i < listTable.size(); i++) {
                    data[i][0] = String.valueOf(listTable.get(i).getId());
                    data[i][1] = listTable.get(i).getName();
                    data[i][2] = String.valueOf(listTable.get(i).getCapacity());
                    data[i][3] = listTable.get(i).getDes();
                }
                tblResult.setModel(new DefaultTableModel(data, columns) {
                    public boolean isCellEditable(int row, int col) { return false; }
                });
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid information!");
            }
        } else if (e.getSource().equals(btnBack)) {
            new ReceptionistHomeFrm(user).setVisible(true);
            this.dispose();
        }
    }
}
