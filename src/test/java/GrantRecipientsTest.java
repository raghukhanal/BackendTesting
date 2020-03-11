import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

public class GrantRecipientsTest {
    private GrantRecipients g = new GrantRecipients();
    private List<Institution> institutions = new ArrayList<>();;

    @Before
    public void setUp(){
        Institution institution = new Institution(5159,"test","test","test","test",13.0,3);
        Institution institution2 = new Institution(5160,"test2","test2","test2","test2",113.0,4);
        Institution institution3 = new Institution(5161,"test3","test3","test3","test3",115.0,7);
        Institution institution4 = new Institution(5162,"test","test","test","test",133.0,2);
        Institution institution5 = new Institution(5163,"test2","test2","test2","test2",143.0,9);
        Institution institution6 = new Institution(5164,"test3","test3","test3","test3",193.0,8);
        Institution institution7 = new Institution(5165,"test","test","test","test",253.0,2);
        Institution institution8 = new Institution(5166,"test2","test2","test2","test2",274.0,13);
        Institution institution9 = new Institution(5167,"test3","test3","test3","test3",299.0,15);
        Institution institution10 = new Institution(5168,"test","test","test","test",300.1,19);
        Institution institution11 = new Institution(5169,"test2","test2","test2","test2",700.1,27);
        Institution institution12 = new Institution(5170,"test3","test3","test3","test3",303.0,33);
        Institution institution13 = new Institution(5171,"test","test","test","test",333.0,37);
        Institution institution14 = new Institution(5172,"test2","test2","test2","test2",833.0,23);
        Institution institution15 = new Institution(5173,"test3","test3","test3","test3",999.1,31);

        institutions.add(institution);
        institutions.add(institution2);
        institutions.add(institution3);
        institutions.add(institution4);
        institutions.add(institution5);
        institutions.add(institution6);
        institutions.add(institution7);
        institutions.add(institution8);
        institutions.add(institution9);
        institutions.add(institution10);
        institutions.add(institution11);
        institutions.add(institution12);
        institutions.add(institution13);
        institutions.add(institution14);
        institutions.add(institution15);

        g.setInstitutions(institutions);
    }

    @Test
    public void testGetTopTenHighestAverageAward() {
        Assert.assertEquals(10,g.getTopTenHighestAverageAward().size());
        Assert.assertEquals(institutions.get(6),g.getTopTenHighestAverageAward().get(0));
    }

    @Test
    public void testGetTopTenLowestAverageAward() {
        Assert.assertEquals(10,g.getTopTenLowestAverageAward().size());
        Assert.assertEquals(institutions.get(0),g.getTopTenLowestAverageAward().get(0));
    }

    @Test
    public void testGetTopTenMostNumStu() {
        Assert.assertEquals(10,g.getTopTenMostNumStu().size());
        Assert.assertEquals(institutions.get(12),g.getTopTenMostNumStu().get(0));
    }

    @Test
    public void testGetStates() {
        Assert.assertNotNull(g.getStates());
        Assert.assertEquals(3,g.getStates().size());
    }

    @Test
    public void testGetPellGrant() {
        System.out.println("Testing --> 15 institutions in 3 states");
        Assert.assertNotNull(g.getPellGrant());
        Assert.assertEquals(3,g.getPellGrant().size());
    }

}
