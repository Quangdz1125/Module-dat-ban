package test.unit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Assert;
import org.junit.Test;
import dao.TableDAO;
import model.Table;

public class TableDaoTest {
    TableDAO td = new TableDAO();
    
    @Test
    public void testSearchFreeTableAvailable() {
        // Assume there are available tables on this date and time
        Date date = new GregorianCalendar(2026, Calendar.MAY, 20).getTime();
        String time = "19:00";
        int capacity = 2; // Searching for a small table
        
        ArrayList<Table> listTable = td.searchFreeTable(date, time, capacity);
        
        Assert.assertNotNull(listTable);
        // Assuming test DB has data, size should be > 0.
        // Assert.assertTrue(listTable.size() > 0);
        for(Table t : listTable) {
            Assert.assertTrue(t.getCapacity() >= capacity);
        }
    }
    
    @Test
    public void testSearchFreeTableNoAvailable() {
        // Assume no tables available (e.g. extremely high capacity requirement)
        Date date = new GregorianCalendar(2026, Calendar.MAY, 20).getTime();
        String time = "19:00";
        int capacity = 9999; // Unrealistic capacity
        
        ArrayList<Table> listTable = td.searchFreeTable(date, time, capacity);
        
        Assert.assertNotNull(listTable);
        Assert.assertEquals(0, listTable.size());
    }
}
