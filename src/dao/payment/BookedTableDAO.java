package dao.payment;
import dao.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.BookedTable;

public class BookedTableDAO extends DAO {
    
    public BookedTableDAO() {
        super();
    }

    public BookedTable getBookedTableInfo(int tableId) {
        BookedTable bt = null;
        String sql = "SELECT b.id as booking_id, b.totalAmount as amount, b.note " +
                     "FROM tblBooking b JOIN tblBookedTable bt ON b.id = bt.bookingId " +
                     "WHERE bt.tableId = ? AND b.status = 'confirmed'";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, tableId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                bt = new BookedTable();
                bt.setId(rs.getInt("booking_id"));
                bt.setAmount(rs.getInt("amount")); // Tiền cọc (int)
                bt.setNote(rs.getString("note"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bt;
    }
}
