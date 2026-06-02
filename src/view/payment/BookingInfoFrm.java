package view.payment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.BookedTable;

public class BookingInfoFrm extends JFrame implements ActionListener {
    private BookedTable bookedTable;
    private PaymentDetailFrm parentFrm;

    private JButton btnApplyDeposit;
    private JButton btnCancel;

    public BookingInfoFrm(BookedTable bookedTable, PaymentDetailFrm parentFrm) {
        super("Thông Tin Đặt Bàn (Tiền Cọc)");
        this.bookedTable = bookedTable;
        this.parentFrm = parentFrm;

        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Mã đặt bàn:"));
        add(new JLabel(String.valueOf(bookedTable.getId())));

        add(new JLabel("Tiền cọc (Deposit):"));
        add(new JLabel(bookedTable.getAmount() + " VNĐ"));

        add(new JLabel("Ghi chú của khách:"));
        add(new JLabel(bookedTable.getNote() != null ? bookedTable.getNote() : "Không có"));

        btnApplyDeposit = new JButton("Xác nhận & Trừ cọc");
        btnApplyDeposit.addActionListener(this);
        add(btnApplyDeposit);

        btnCancel = new JButton("Đóng");
        btnCancel.addActionListener(this);
        add(btnCancel);

        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnApplyDeposit) {
            // Gửi tiền cọc về cho form PaymentDetailFrm trừ đi
            parentFrm.applyDeposit((float) bookedTable.getAmount());
            this.dispose();
        } else if (e.getSource() == btnCancel) {
            this.dispose();
        }
    }
}