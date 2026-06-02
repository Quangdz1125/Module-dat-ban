package view.booking;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.User;

public class ReceptionistHomeFrm extends JFrame implements ActionListener {
    private JButton btnBooking, btnManageBooking;
    private User user;

    public ReceptionistHomeFrm(User user) {
        super("Receptionist Home");
        this.user = user;
        
        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JLabel lblHome = new JLabel("Receptionist Home: " + user.getName());
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblHome.setFont(lblHome.getFont().deriveFont(20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));
        
        btnBooking = new JButton("Book a Table");
        btnBooking.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBooking.addActionListener(this);
        pnMain.add(btnBooking);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));
        
        btnManageBooking = new JButton("Manage Bookings");
        btnManageBooking.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnManageBooking.addActionListener(this);
        pnMain.add(btnManageBooking);
        
        this.setContentPane(pnMain);
        this.setSize(400, 250);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnBooking)) {
            new SearchFreeTableFrm(user).setVisible(true);
            this.dispose();
        } else if (e.getSource().equals(btnManageBooking)) {
            new ManageBookingFrm(user).setVisible(true);
            this.dispose();
        }
    }
    
    // Main method added for testing/running the UI standalone
    public static void main(String[] args) {
        // Create a dummy user to bypass login
        User dummyUser = new User();
        dummyUser.setId(1);
        dummyUser.setName("Receptionist Admin");
        
        // Start the UI safely
        SwingUtilities.invokeLater(() -> {
            new ReceptionistHomeFrm(dummyUser).setVisible(true);
        });
    }
}
