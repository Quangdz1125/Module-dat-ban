package test.unit.payment;

import dao.payment.BillDAO;
import model.payment.Bill;
import org.junit.Assert;
import org.junit.Test;

public class BillDAOTest {

    // Test trường hợp lấy hóa đơn thành công
    @Test
    public void testGetBillByTable_Found() {
        BillDAO dao = new BillDAO();
        // Giả sử bàn 1 đang có khách 
        Bill bill = dao.getBillByTable(1); 
        
        Assert.assertNotNull("Phải tìm thấy hóa đơn của bàn 1", bill);
        Assert.assertEquals("Trạng thái phải là unpaid", "unpaid", bill.getStatus());
    }

    // Test trường hợp lấy hóa đơn của bàn trống
    @Test
    public void testGetBillByTable_NotFound() {
        BillDAO dao = new BillDAO();
        // Giả sử bàn 999 không tồn tại hoặc không có khách
        Bill bill = dao.getBillByTable(999); 
        
        Assert.assertNull("Không được có hóa đơn cho bàn 999", bill);
    }
}