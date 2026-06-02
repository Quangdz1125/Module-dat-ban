package test.unit;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Assert;
import org.junit.Test;
import dao.BookingDAO;
import dao.DAO;
import dao.TableDAO;
import model.BookedTable;
import model.Booking;
import model.Client;
import model.Table;
import model.User;

public class BookingDaoTest {

    // Test Case: table exists, client exists, table still available
    @Test
    public void testAddBookingStandard1() {
        Booking booking = new Booking();

        Date bookDate = new GregorianCalendar(2026, Calendar.MAY, 20).getTime();
        booking.setBookDate(bookDate);
        booking.setTime("19:00");
        booking.setNote("Test Booking");
        booking.setTotalAmount(500);

        User creator = new User();
        creator.setId(1); // assume user 1 exists
        booking.setUser(creator);

        Client client = new Client();
        client.setId(1); // assume client 1 exists
        booking.setClient(client);

        booking.setBookedTables(new ArrayList<BookedTable>());
        Table table = new Table();
        table.setId(1); // assume table 1 exists
        BookedTable bt = new BookedTable();
        bt.setTable(table);
        bt.setPrice(500);
        booking.getBookedTables().add(bt);

        BookingDAO bd = new BookingDAO();
        Connection con = DAO.con;
        try {
            con.setAutoCommit(false);

            Assert.assertTrue(bd.addBooking(booking));

            // Verify table 1 is no longer in the free tables list
            TableDAO td = new TableDAO();
            ArrayList<Table> freeTables = td.searchFreeTable(bookDate, "19:00", 1);
            if (freeTables != null) {
                for (Table t : freeTables) {
                    Assert.assertFalse(t.getId() == 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!con.getAutoCommit()) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Test Case: table exists, client does not exist
    @Test
    public void testAddBookingException1() {
        Booking booking = new Booking();
        booking.setBookDate(new GregorianCalendar(2026, Calendar.MAY, 20).getTime());
        booking.setTime("19:00");

        User creator = new User();
        creator.setId(1);
        booking.setUser(creator);

        Client client = new Client();
        client.setId(9999); // client doesn't exist
        booking.setClient(client);

        booking.setBookedTables(new ArrayList<BookedTable>());
        Table table = new Table();
        table.setId(1);
        BookedTable bt = new BookedTable();
        bt.setTable(table);
        booking.getBookedTables().add(bt);

        BookingDAO bd = new BookingDAO();
        Connection con = DAO.con;
        try {
            con.setAutoCommit(false);
            Assert.assertFalse(bd.addBooking(booking));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!con.getAutoCommit()) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Test Case: table does not exist
    @Test
    public void testAddBookingException2() {
        Booking booking = new Booking();
        booking.setBookDate(new GregorianCalendar(2026, Calendar.MAY, 20).getTime());
        booking.setTime("19:00");

        User creator = new User();
        creator.setId(1);
        booking.setUser(creator);

        Client client = new Client();
        client.setId(1);
        booking.setClient(client);

        booking.setBookedTables(new ArrayList<BookedTable>());
        Table table = new Table();
        table.setId(9999); // table does not exist
        BookedTable bt = new BookedTable();
        bt.setTable(table);
        booking.getBookedTables().add(bt);

        BookingDAO bd = new BookingDAO();
        Connection con = DAO.con;
        try {
            con.setAutoCommit(false);
            Assert.assertFalse(bd.addBooking(booking));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!con.getAutoCommit()) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
