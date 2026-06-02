package view.payment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.payment.Bill;
import dao.payment.BillDAO;
import model.BookedTable;
import model.Table;

public class PaymentDetailFrm extends JFrame implements ActionListener {

    private Bill bill;
    private int tableId;
    private float currentTotal;

    private JButton btnConfirm, btnCancel, btnCheckDeposit;
    private JLabel lblTotalAmount;
    private JTable tblDish;
    private DefaultTableModel tableModel;

    public PaymentDetailFrm(Bill bill, int tableId) {
        super("Hóa Đơn Thanh Toán - Bàn " + tableId);
        this.bill = bill;
        this.tableId = tableId;
        this.currentTotal = bill.getFinalAmount();

        setLayout(new BorderLayout(10, 10));

        // --- PHẦN ĐẦU: Thông tin chung ---
        JPanel pnlTop = new JPanel(new GridLayout(3, 1));
        pnlTop.add(new JLabel("  HOÁ ĐƠN THANH TOÁN", SwingConstants.CENTER));
        pnlTop.add(new JLabel("  Số HĐ (Bill ID): " + bill.getId(), SwingConstants.CENTER));

        String trangThaiVN = "unpaid".equals(bill.getStatus()) ? "Chưa thanh toán" : "Đã thanh toán";
        pnlTop.add(new JLabel("  Trạng thái: " + trangThaiVN, SwingConstants.CENTER));
        add(pnlTop, BorderLayout.NORTH);

        // --- PHẦN GIỮA: Bảng danh sách món ăn ---
        String[] columnNames = { "Tên món", "SL", "Đơn giá", "Thành tiền" };
        tableModel = new DefaultTableModel(columnNames, 0);
        tblDish = new JTable(tableModel);

        // Load dữ liệu món ăn từ Database lên bảng
        BillDAO dao = new BillDAO();
        List<Object[]> dishList = dao.getBillDetails(tableId);
        for (Object[] row : dishList) {
            tableModel.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(tblDish);
        add(scrollPane, BorderLayout.CENTER);

        // --- PHẦN CUỐI: Tổng tiền và Nút bấm ---
        JPanel pnlBottom = new JPanel(new BorderLayout());

        lblTotalAmount = new JLabel(" TỔNG THANH TOÁN: " + currentTotal + " VNĐ", SwingConstants.RIGHT);
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalAmount.setForeground(Color.RED);
        pnlBottom.add(lblTotalAmount, BorderLayout.NORTH);

        JPanel pnlButtons = new JPanel(new FlowLayout());
        btnCheckDeposit = new JButton("Kiểm Tra Trừ Cọc");
        btnCheckDeposit.addActionListener(this);
        pnlButtons.add(btnCheckDeposit);

        btnConfirm = new JButton("Xác Nhận Thu Tiền");
        btnConfirm.addActionListener(this);
        pnlButtons.add(btnConfirm);

        btnCancel = new JButton("Đóng");
        btnCancel.addActionListener(this);
        pnlButtons.add(btnCancel);

        pnlBottom.add(pnlButtons, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);

        setSize(500, 600); // Form bự ra cho giống tờ biên lai
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void applyDeposit(float depositAmount) {
        this.currentTotal = this.currentTotal - depositAmount;
        if (this.currentTotal < 0)
            this.currentTotal = 0;

        this.bill.setFinalAmount(this.currentTotal);
        lblTotalAmount.setText(" TỔNG THANH TOÁN: " + currentTotal + " VNĐ");

        JOptionPane.showMessageDialog(this, "Đã trừ tiền cọc: " + depositAmount + " VNĐ.");
        btnCheckDeposit.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConfirm) {
            bill.setPaymentMethod("cash");
            BillDAO dao = new BillDAO();
            if (dao.updatePayment(bill, tableId)) {
                dao.TableDAO tableDAO = new dao.TableDAO();
                tableDAO.updateTableStatus(tableId, "available");
                JOptionPane.showMessageDialog(this, "Thanh toán thành công! Bàn " + tableId + " đã được dọn trống!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi update database!");
            }
        } else if (e.getSource() == btnCheckDeposit) {
            Table t = new Table();
            t.setId(tableId);
            BookedTable fakeBooking = new BookedTable(99, new java.util.Date(), "19:00", 4, 200000, "Khách VIP", t);
            new BookingInfoFrm(fakeBooking, this).setVisible(true);
        } else if (e.getSource() == btnCancel) {
            this.dispose();
        }
    }
}