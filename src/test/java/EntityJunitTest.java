import org.junit.Test;
import static org.junit.Assert.*;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;

import entities.*;

public class EntityJunitTest {
    
    public EntityJunitTest() {
    }

    @Test
    public void aValidUserIsValid() {
        
        User usrTest = new User();

        usrTest.setEntityUserIdNumber("987654321");
        usrTest.setEntityUserEmailAddress("testUser@emailserver.com");
        usrTest.setMemberName("Mr. Test User");
        usrTest.setMemberStreetAddress("555 N. People St.");
        usrTest.setMemberCity("People City");
        usrTest.setMemberState("WI");
        usrTest.setMemberZip(55522);
        usrTest.setMemberValidThrough("02/10/2017");
        
        assertTrue(usrTest.isValid(usrTest));
    }
}
