package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.Booking;
import model.BookedTable;

public class BookingDAO extends DAO {
    public BookingDAO() {
        super();
    }
    
    public boolean addBooking(Booking booking) {
        String sqlBooking = "INSERT INTO tblBooking(clientId, userId, bookDate, time, note, status, totalAmount) VALUES(?, ?, ?, ?, ?, ?, ?)";
        String sqlBookedTable = "INSERT INTO tblBookedTable(bookingId, tableId, price) VALUES(?, ?, ?)";
        boolean result = false;
        try {
            con.setAutoCommit(false); // Bắt đầu transaction
            
            // 1. Lưu Booking
            PreparedStatement ps = con.prepareStatement(sqlBooking, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, booking.getClient().getId());
            ps.setInt(2, booking.getUser().getId());
            ps.setDate(3, new java.sql.Date(booking.getBookDate().getTime()));
            ps.setString(4, booking.getTime());
            ps.setString(5, booking.getNote());
            ps.setString(6, "booked");
            ps.setFloat(7, booking.getTotalAmount());
            ps.executeUpdate();
            
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                booking.setId(generatedKeys.getInt(1)); // Lấy id của booking
                
                // 2. Lưu danh sách BookedTable
                PreparedStatement psTable = con.prepareStatement(sqlBookedTable);
                for(BookedTable bt : booking.getBookedTables()) {
                    psTable.setInt(1, booking.getId());
                    psTable.setInt(2, bt.getTable().getId());
                    psTable.setFloat(3, bt.getPrice());
                    psTable.executeUpdate();
                }
            }
            con.commit(); // Commit transaction
            result = true;
        } catch(Exception e) {
            try { con.rollback(); } catch(Exception ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { con.setAutoCommit(true); } catch(Exception ex) { ex.printStackTrace(); }
        }
        return result;
    }
    
    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE tblBooking SET note=?, status=?, totalAmount=?, time=? WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, booking.getNote());
            ps.setString(2, booking.getStatus());
            ps.setFloat(3, booking.getTotalAmount());
            ps.setString(4, booking.getTime());
            ps.setInt(5, booking.getId());
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean cancelBooking(int id) {
        String sql = "UPDATE tblBooking SET status='cancelled' WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public java.util.ArrayList<Booking> getAllBookings() {
        java.util.ArrayList<Booking> list = new java.util.ArrayList<>();
        String sql = "SELECT b.id, c.name as clientName, b.bookDate, b.time, b.status " +
                     "FROM tblBooking b JOIN tblClient c ON b.clientId = c.id";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                
                model.Client c = new model.Client();
                c.setName(rs.getString("clientName"));
                b.setClient(c);
                
                b.setBookDate(rs.getDate("bookDate"));
                b.setTime(rs.getString("time"));
                b.setStatus(rs.getString("status"));
                list.add(b);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Booking getBookingById(int id) {
        String sql = "SELECT * FROM tblBooking WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setTime(rs.getString("time"));
                b.setNote(rs.getString("note"));
                b.setStatus(rs.getString("status"));
                b.setTotalAmount(rs.getFloat("totalAmount"));
                return b;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
