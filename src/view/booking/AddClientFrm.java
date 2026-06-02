package view.booking;

import view.ReceptionistHomeFrm;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.booking.ClientDAO;
import model.booking.Booking;
import model.booking.Client;
import model.User;

public class AddClientFrm extends JFrame implements ActionListener {
    private JTextField txtName, txtEmail, txtTel, txtAddress, txtNote;
    private JButton btnAdd, btnBack;
    private User user;
    private Booking booking;

    public AddClientFrm(User user, Booking booking) {
        super("Add Client");
        this.user = user;
        this.booking = booking;

        JPanel pnMain = new JPanel(new GridLayout(6, 2, 10, 10));
        pnMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        pnMain.add(new JLabel("Tên:"));
        txtName = new JTextField();
        pnMain.add(txtName);
        pnMain.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        pnMain.add(txtEmail);
        pnMain.add(new JLabel("SĐT:"));
        txtTel = new JTextField();
        pnMain.add(txtTel);
        pnMain.add(new JLabel("Địa chỉ:"));
        txtAddress = new JTextField();
        pnMain.add(txtAddress);
        pnMain.add(new JLabel("Note:"));
        txtNote = new JTextField();
        pnMain.add(txtNote);

        btnAdd = new JButton("Thêm");
        btnAdd.addActionListener(this);
        pnMain.add(btnAdd);

        btnBack = new JButton("Về Trang Chủ");
        btnBack.addActionListener(this);
        pnMain.add(btnBack);

        this.setContentPane(pnMain);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnAdd)) {
            Client client = new Client(txtName.getText(), txtEmail.getText(), txtTel.getText(), txtAddress.getText(),
                    txtNote.getText());
            ClientDAO cd = new ClientDAO();
            if (cd.addClient(client)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
                booking.setClient(client);
                new BookingConfirmFrm(user, booking).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi thêm khách hàng!");
            }
        } else if (e.getSource().equals(btnBack)) {
            new ReceptionistHomeFrm(user).setVisible(true);
            this.dispose();
        }
    }
}
