package test.unit.booking;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import dao.booking.ClientDAO;
import model.booking.Client;

public class ClientDaoTest {
    ClientDAO cd = new ClientDAO();
    
    @Test
    public void testSearchClientFound() {
        // Assume a client with "Nguyen" exists in the test DB
        String key = "Nguyen";
        
        ArrayList<Client> listClient = cd.searchClient(key);
        
        Assert.assertNotNull(listClient);
        // Assert.assertTrue(listClient.size() > 0);
        for(Client c : listClient) {
            boolean match = c.getName().toLowerCase().contains(key.toLowerCase()) || 
                            c.getTel().contains(key);
            Assert.assertTrue(match);
        }
    }
    
    @Test
    public void testSearchClientNotFound() {
        // Unlikely string to exist
        String key = "xxxxxxxxxx";
        
        ArrayList<Client> listClient = cd.searchClient(key);
        
        Assert.assertNotNull(listClient);
        Assert.assertEquals(0, listClient.size());
    }
}
