import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JSONTest {
    private GrantRecipients g;
    private List<Institution> institutions;

    @Before
    public void setUp(){
        g = new GrantRecipients();
        institutions = new ArrayList<>();
    }

    @Test
    public void testParseToJSON() throws Exception{
        Institution institution = new Institution(5159,"test","test","test","test",3.0,3);
        Institution institution2 = new Institution(5160,"test2","test2","test2","test2",3.0,3);
        Institution institution3 = new Institution(5161,"test3","test3","test3","test3",3.0,3);
        institutions.add(institution);
        institutions.add(institution2);
        institutions.add(institution3);
        g.setInstitutions(institutions);
        g.parseToJSON("test.json");
        File testJSON = new File("test.json");
        Assert.assertTrue(testJSON.exists());
    }

}
