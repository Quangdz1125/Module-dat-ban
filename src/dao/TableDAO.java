package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import model.Table;

public class TableDAO extends DAO {
    public TableDAO() {
        super();
    }
    
    /**
     * Search free tables based on date, time and minimum capacity
     */
    public ArrayList<Table> searchFreeTable(Date date, String time, int capacity) {
        ArrayList<Table> result = new ArrayList<Table>();
        // Query tìm các bàn có capacity >= yêu cầu và chưa được đặt trong ngày giờ đó
        String sql = "SELECT * FROM tblTable WHERE capacity >= ? AND id NOT IN "
                   + "(SELECT tableId FROM tblBookedTable bt, tblBooking b "
                   + "WHERE bt.bookingId = b.id AND b.bookDate = ? AND b.time = ? AND b.status != 'cancelled')";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, capacity);
            ps.setDate(2, new java.sql.Date(date.getTime()));
            ps.setString(3, time);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Table t = new Table();
                t.setId(rs.getInt("id"));
                t.setName(rs.getString("name"));
                t.setCapacity(rs.getInt("capacity"));
                t.setDes(rs.getString("des"));
                result.add(t);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
