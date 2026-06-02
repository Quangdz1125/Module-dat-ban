package view.booking;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.BookingDAO;
import model.Booking;

public class UpdateBookingFrm extends JFrame implements ActionListener {
    private JTextField txtTime, txtNote;
    private JComboBox<String> cbStatus;
    private JButton btnSave, btnCancel;
    private Booking booking;
    private ManageBookingFrm parentFrm;

    public UpdateBookingFrm(ManageBookingFrm parentFrm, Booking booking) {
        super("Update Booking Details");
        this.parentFrm = parentFrm;
        this.booking = booking;
        
        JPanel pnMain = new JPanel(new GridLayout(4, 2, 10, 10));
        pnMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        pnMain.add(new JLabel("Time:"));
        txtTime = new JTextField(booking.getTime());
        pnMain.add(txtTime);
        
        pnMain.add(new JLabel("Note:"));
        txtNote = new JTextField(booking.getNote());
        pnMain.add(txtNote);
        
        pnMain.add(new JLabel("Status:"));
        String[] statuses = {"booked", "cancelled", "completed"};
        cbStatus = new JComboBox<>(statuses);
        cbStatus.setSelectedItem(booking.getStatus());
        pnMain.add(cbStatus);
        
        btnSave = new JButton("Save Changes");
        btnSave.addActionListener(this);
        pnMain.add(btnSave);
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        pnMain.add(btnCancel);
        
        this.setContentPane(pnMain);
        this.setSize(350, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnSave)) {
            booking.setTime(txtTime.getText());
            booking.setNote(txtNote.getText());
            booking.setStatus(cbStatus.getSelectedItem().toString());
            
            BookingDAO bd = new BookingDAO();
            if (bd.updateBooking(booking)) {
                JOptionPane.showMessageDialog(this, "Booking updated successfully!");
                parentFrm.refreshTable();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update booking!");
            }
        } else if (e.getSource().equals(btnCancel)) {
            this.dispose();
        }
    }
}
