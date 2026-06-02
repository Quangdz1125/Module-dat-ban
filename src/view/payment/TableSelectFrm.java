package view.payment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.payment.BillDAO;
import model.payment.Bill;

public class TableSelectFrm extends JFrame implements ActionListener {

    private JTextField txtTableId;
    private JButton btnSelect;

    public TableSelectFrm() {
        super("Chọn Bàn Thanh Toán");
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        add(new JLabel("Nhập ID bàn cần thanh toán:"));

        txtTableId = new JTextField(10);
        add(txtTableId);

        btnSelect = new JButton("Lấy Hóa Đơn");
        btnSelect.addActionListener(this);
        add(btnSelect);

        setSize(350, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSelect) {
            try {
                // Lấy số ID bàn khách vừa nhập
                int tableId = Integer.parseInt(txtTableId.getText().trim());

                BillDAO billDAO = new BillDAO();
                Bill bill = billDAO.getBillByTable(tableId);

                if (bill != null) {

                    PaymentDetailFrm detailFrm = new PaymentDetailFrm(bill, tableId);
                    detailFrm.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Bàn này không có khách hoặc đã thanh toán rồi!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nhập sai! ID Bàn phải là số (VD: 1, 2, 3...)");
            }
        }
    }
}