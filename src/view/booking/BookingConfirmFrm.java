package view.booking;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.BookingDAO;
import model.Booking;
import model.User;

public class BookingConfirmFrm extends JFrame implements ActionListener {
    private JTextArea txtInfo;
    private JTextField txtNote;
    private JButton btnConfirm, btnBack;
    private User user;
    private Booking booking;
    
    public BookingConfirmFrm(User user, Booking booking) {
        super("Confirm Booking");
        this.user = user;
        this.booking = booking;
        
        JPanel pnMain = new JPanel(new BorderLayout());
        
        txtInfo = new JTextArea(10, 30);
        txtInfo.setEditable(false);
        String info = "Client: " + booking.getClient().getName() + " (" + booking.getClient().getTel() + ")\n" +
                      "Date: " + booking.getBookDate().toString() + "\n" +
                      "Time: " + booking.getTime() + "\n" +
                      "Number of Tables: " + booking.getBookedTables().size() + "\n";
        txtInfo.setText(info);
        pnMain.add(new JScrollPane(txtInfo), BorderLayout.CENTER);
        
        JPanel pnBottom = new JPanel(new FlowLayout());
        pnBottom.add(new JLabel("Booking Note:"));
        txtNote = new JTextField(20);
        pnBottom.add(txtNote);
        
        btnConfirm = new JButton("Confirm Booking");
        btnConfirm.addActionListener(this);
        pnBottom.add(btnConfirm);
        
        btnBack = new JButton("Back to Home");
        btnBack.addActionListener(this);
        pnBottom.add(btnBack);
        
        pnMain.add(pnBottom, BorderLayout.SOUTH);
        
        this.setContentPane(pnMain);
        this.setSize(550, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnConfirm)) {
            booking.setNote(txtNote.getText());
            // Có thể tính tổng tiền totalAmount dựa trên danh sách bàn ở đây
            BookingDAO bd = new BookingDAO();
            if (bd.addBooking(booking)) {
                JOptionPane.showMessageDialog(this, "Booking confirmed successfully!");
                new ReceptionistHomeFrm(user).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to confirm booking!");
            }
        } else if (e.getSource().equals(btnBack)) {
            new ReceptionistHomeFrm(user).setVisible(true);
            this.dispose();
        }
    }
}
