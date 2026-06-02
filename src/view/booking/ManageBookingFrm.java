package view.booking;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.BookingDAO;
import model.User;

public class ManageBookingFrm extends JFrame implements ActionListener {
    private JButton btnUpdate, btnCancel, btnBack;
    private JTable tblBookings;
    private User user;
    
    public ManageBookingFrm(User user) {
        super("Manage Bookings");
        this.user = user;
        
        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        
        // tblBookings
        tblBookings = new JTable();
        refreshTable(); // Gọi hàm refresh
        pnMain.add(new JScrollPane(tblBookings));
        
        JPanel pnBottom = new JPanel(new FlowLayout());
        btnUpdate = new JButton("Update Booking");
        btnUpdate.addActionListener(this);
        pnBottom.add(btnUpdate);
        
        btnCancel = new JButton("Cancel Booking");
        btnCancel.addActionListener(this);
        pnBottom.add(btnCancel);
        
        btnBack = new JButton("Back to Home");
        btnBack.addActionListener(this);
        pnBottom.add(btnBack);
        
        pnMain.add(pnBottom);
        
        this.setContentPane(pnMain);
        this.setSize(700, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int row = tblBookings.getSelectedRow();
        BookingDAO bd = new BookingDAO();
        
        if (e.getSource().equals(btnBack)) {
            new ReceptionistHomeFrm(user).setVisible(true);
            this.dispose();
            return;
        }

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a booking from the list!");
            return;
        }
        
        if (e.getSource().equals(btnUpdate)) {
            int bookingId = Integer.parseInt(tblBookings.getValueAt(row, 0).toString());
            model.Booking b = bd.getBookingById(bookingId);
            if (b != null) {
                new UpdateBookingFrm(this, b).setVisible(true);
            }
        } else if (e.getSource().equals(btnCancel)) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to cancel this booking?");
            if (confirm == JOptionPane.YES_OPTION) {
                int bookingId = Integer.parseInt(tblBookings.getValueAt(row, 0).toString());
                if (bd.cancelBooking(bookingId)) {
                    JOptionPane.showMessageDialog(this, "Booking cancelled successfully!");
                    refreshTable();
                }
            }
        }
    }
    
    // Hàm hỗ trợ để load lại dữ liệu bảng
    public void refreshTable() {
        BookingDAO bd = new BookingDAO();
        java.util.ArrayList<model.Booking> list = bd.getAllBookings();
        String[] columns = {"Booking ID", "Client Name", "Date", "Time", "Status"};
        String[][] data = new String[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            model.Booking b = list.get(i);
            data[i][0] = String.valueOf(b.getId());
            data[i][1] = b.getClient().getName();
            data[i][2] = b.getBookDate().toString();
            data[i][3] = b.getTime();
            data[i][4] = b.getStatus();
        }
        tblBookings.setModel(new javax.swing.table.DefaultTableModel(data, columns) {
            public boolean isCellEditable(int r, int c) { return false; }
        });
    }
}
