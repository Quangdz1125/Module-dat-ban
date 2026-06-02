package view.booking;

import view.ReceptionistHomeFrm;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.booking.BookingDAO;
import model.User;

public class ManageBookingFrm extends JFrame implements ActionListener {
    private JButton btnUpdate, btnCancel, btnPayment, btnBack;
    private JTable tblBookings;
    private User user;

    public ManageBookingFrm(User user) {
        super("Quản Lý Đặt Bàn");
        this.user = user;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));

        // tblBookings
        tblBookings = new JTable();
        refreshTable(); // Gọi hàm refresh
        pnMain.add(new JScrollPane(tblBookings));

        JPanel pnBottom = new JPanel(new FlowLayout());
        btnUpdate = new JButton("Cập nhật Đặt Bàn");
        btnUpdate.addActionListener(this);
        pnBottom.add(btnUpdate);

        btnCancel = new JButton("Hủy Đặt Bàn");
        btnCancel.addActionListener(this);
        pnBottom.add(btnCancel);

        btnPayment = new JButton("Thanh Toán");
        btnPayment.addActionListener(this);
        pnBottom.add(btnPayment);

        btnBack = new JButton("Về Trang Chủ");
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
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một lịch đặt bàn từ danh sách!");
            return;
        }

        if (e.getSource().equals(btnUpdate)) {
            int bookingId = Integer.parseInt(tblBookings.getValueAt(row, 0).toString());
            model.booking.Booking b = bd.getBookingById(bookingId);
            if (b != null) {
                new UpdateBookingFrm(this, b).setVisible(true);
            }
        } else if (e.getSource().equals(btnCancel)) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy đặt bàn này không?");
            if (confirm == JOptionPane.YES_OPTION) {
                int bookingId = Integer.parseInt(tblBookings.getValueAt(row, 0).toString());
                if (bd.cancelBooking(bookingId)) {
                    JOptionPane.showMessageDialog(this, "Đã hủy đặt bàn thành công!");
                    refreshTable();
                }
            }
        } else if (e.getSource().equals(btnPayment)) {
            int bookingId = Integer.parseInt(tblBookings.getValueAt(row, 0).toString());
            int tableId = bd.getFirstTableIdByBooking(bookingId);

            if (tableId == -1) {
                JOptionPane.showMessageDialog(this, "Booking này chưa được xếp bàn thực tế!");
                return;
            }

            dao.payment.BillDAO billDAO = new dao.payment.BillDAO();
            model.payment.Bill bill = billDAO.getBillByTable(tableId);

            if (bill != null) {
                view.payment.PaymentDetailFrm detailFrm = new view.payment.PaymentDetailFrm(bill, tableId);
                detailFrm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Bàn này chưa gọi món hoặc đã thanh toán xong!");
            }
        }
    }

    // Hàm hỗ trợ để load lại dữ liệu bảng
    public void refreshTable() {
        BookingDAO bd = new BookingDAO();
        java.util.ArrayList<model.booking.Booking> list = bd.getAllBookings();
        String[] columns = { "Mã Đặt Bàn", "Tên Khách Hàng", "Ngày", "Giờ", "Trạng Thái" };
        String[][] data = new String[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            model.booking.Booking b = list.get(i);
            data[i][0] = String.valueOf(b.getId());
            data[i][1] = b.getClient().getName();
            data[i][2] = b.getBookDate().toString();
            data[i][3] = b.getTime();
            data[i][4] = b.getStatus();
        }
        tblBookings.setModel(new javax.swing.table.DefaultTableModel(data, columns) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        });
    }
}
