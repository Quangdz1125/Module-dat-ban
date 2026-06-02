package dao.payment;
import dao.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.payment.Bill;

public class BillDAO extends DAO {

    public BillDAO() {
        super();
    }

    // Lấy Hóa đơn chưa thanh toán theo ID bàn
    public Bill getBillByTable(int tableId) {
        Bill bill = null;
        String sql = "SELECT b.id as bill_id, b.totalAmount as total_amount, b.discountAmount as discount_amount, b.status " +
                     "FROM tblBill b JOIN tblOrder o ON b.orderId = o.id " +
                     "WHERE o.tableId = ? AND b.status = 'unpaid'"; 
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, tableId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bill = new Bill();
                bill.setId(rs.getInt("bill_id"));
                bill.setTotalAmount(rs.getFloat("total_amount"));
                bill.setDiscount(rs.getFloat("discount_amount"));
                bill.setStatus(rs.getString("status"));
                
                // Mặc định VAT 10%
                bill.setTax(0.1f); 
                
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bill;
    }

    // Cập nhật thanh toán thành công
    public boolean updatePayment(Bill bill, int tableId) {
        String sql = "UPDATE tblBill SET finalAmount = ?, paymentMethod = ?, status = 'paid', paymentTime = NOW() WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setFloat(1, bill.getFinalAmount()); 
            ps.setString(2, bill.getPaymentMethod());
            ps.setInt(3, bill.getId());
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
   
    // Hàm mới: Lấy danh sách món ăn khách đã gọi của bàn đó
    public java.util.List<Object[]> getBillDetails(int tableId) {
        java.util.List<Object[]> list = new java.util.ArrayList<>();
        // Query nối 3 bảng tblOrder, tblOrderedDish và tblDish để lấy chi tiết
        String sql = "SELECT d.name, od.quantity, od.unitPrice as unit_price, (od.quantity * od.unitPrice) AS total_price " +
                     "FROM tblOrder o " +
                     "JOIN tblOrderedDish od ON o.id = od.orderId " +
                     "JOIN tblDish d ON od.dishId = d.id " +
                     "WHERE o.tableId = ? AND o.status = 'active'";
        try {
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, tableId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getFloat("unit_price"),
                    rs.getFloat("total_price")
                };
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}