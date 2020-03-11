import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTest {
    private static Connection c;
    private GrantRecipients g;
    private List<Institution> institutions;

    @BeforeClass
    public static void setUPDB() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:pellgrant.db");
            Statement s = c.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp(){
        g = new GrantRecipients();
        institutions = new ArrayList<>();
    }


    private int getTotalRows(Connection c) throws Exception {
        Statement s = c.createStatement();
        ResultSet set = s.executeQuery("select count() from institutions");
        int rows = set.getInt("count()");
        return rows;
    }

    @Test
    public void testTotalRows() throws Exception{
        Assert.assertEquals(822, getTotalRows(c));
    }

    @Test
    public void testAddToDatabaseFromInstitutions()
    {
        Institution institution = new Institution(5159,"test","test","test","test",3.0,3);
        Institution institution2 = new Institution(5160,"test2","test2","test2","test2",3.0,3);
        Institution institution3 = new Institution(5161,"test3","test3","test3","test3",3.0,3);
        institutions.add(institution);
        institutions.add(institution2);
        institutions.add(institution3);
        g.setInstitutions(institutions);

        try {
            int before = getTotalRows(c);
            g.addToDatabaseFromInstitutions(c);
            int after = getTotalRows(c);
            Assert.assertEquals(before + institutions.size(), after);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddToDatabaseFromJSON(){

        try {
            int before = getTotalRows(c);
            g.addToDatabaseFromJSON(c,"test.json");
            int after = getTotalRows(c);
            Assert.assertEquals(before + 3 , after);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}
