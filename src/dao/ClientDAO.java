package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Client;

public class ClientDAO extends DAO {
    public ClientDAO() {
        super();
    }
    
    public ArrayList<Client> searchClient(String key) {
        ArrayList<Client> result = new ArrayList<Client>();
        String sql = "SELECT * FROM tblClient WHERE name LIKE ? OR tel LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ps.setString(2, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setTel(rs.getString("tel"));
                c.setAddress(rs.getString("address"));
                c.setNote(rs.getString("note"));
                result.add(c);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean addClient(Client client) {
        String sql = "INSERT INTO tblClient(name, email, tel, address, note) VALUES(?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getName());
            ps.setString(2, client.getEmail());
            ps.setString(3, client.getTel());
            ps.setString(4, client.getAddress());
            ps.setString(5, client.getNote());
            ps.executeUpdate();
            
            // Lấy ID tự sinh gán lại cho đối tượng Client
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                client.setId(generatedKeys.getInt(1));
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
