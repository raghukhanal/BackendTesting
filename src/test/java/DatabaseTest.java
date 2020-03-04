//import org.junit.*;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//
//public class DatabaseTest {
//    private static Dealership dealership;
//    private static Connection c;
//    private final static String URL = "jdbc:sqlite:pellgrant.db";
//
//    @BeforeClass
//    public static void connectDatabase()  throws Exception {
//        c = DriverManager.getConnection(URL);
//        dealership = new Dealership();
//    }
//
//    public int countRows() {
//        int count = 0;
//        try {
//            Statement s = c.createStatement();
//            ResultSet set = s.executeQuery("Select count(*) from vehicles");
//            count = set.getInt("count(*)");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return count;
//    }
//
//    @Test
//    public void testAddVehicleToDB() {
//        int current = countRows();
//        Vehicle v = new Vehicle("Subaru", "Forester", 2020, true, 30000, 20);
//        dealership.addVehicleToDatabase(c,v);
//        Assert.assertEquals(current+1, countRows());
//    }
//
//    @Test
//    @Ignore
//    public void testTableRows() throws Exception {
//        Statement st = c.createStatement();
//        String sql = "select * from vehicles";
//
//        ResultSet set = st.executeQuery(sql);
//        System.out.println(set.toString());
//        Assert.assertNotNull(set);
//
//        while (set.next()){
//            Assert.assertNotNull(set.getString("make"));
//            Assert.assertNotNull(set.getString("price"));
////            System.out.println(set.getString("make"));
////            System.out.println(set.getFloat("price"));
//        }
//        ResultSet set2 = st.executeQuery("select count(*) from vehicles");
//        set2.next();
//        Assert.assertEquals(5, set2.getInt("count(*)"));
//    }
//
//    @AfterClass
//    public static void disconnectDatabase() throws Exception {
//        if(c != null){
//            c.close();
//        }
//    }
//}
