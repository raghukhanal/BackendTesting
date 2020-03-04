import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;

public class DBTest {
    private static Connection c;
    GrantRecipients g;

    @BeforeClass
    public static void setUPDB() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:pellgrant.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp(){
//        g = new GrantRecipients();
    }


    private int getTotalRows(Connection c) throws Exception {
        Statement s = c.createStatement();
        ResultSet set = s.executeQuery("select count() from institutions");
        int rows = set.getInt("count()");
        return rows;
    }

    //stand in db (for testing)

    @Test
    public void testTotalRows() throws Exception{
        Assert.assertEquals(5158, getTotalRows(c));
    }

    @Test
    public void testInstitutionToDB() throws Exception{
        Institution institution = new Institution(1,"test","test","test","test",3.0,3);
//        GrantRecipients grantRecipients = new GrantRecipients();

        try {
            int before = getTotalRows(c);
//            grantRecipients.addToDatabase();
//            g.addToDatabase(c, institution);
            int after = getTotalRows(c);
            Assert.assertEquals(5158 , after);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Test
    public void cleanUp(){
        if(c != null) {
            try{
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
