import org.junit.Test;
import static org.junit.Assert.*;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;

import entities.*;

public class CreateUserTest {
    
    public CreateUserTest() {
    }

    @Test
    public void aSimpleJunitTest() {
        System.out.println("\n\n\n\nSIMPLE TEST\n\n\n\n");
    }    

    @Test
    public void aValidUserIsValid() {
        
        User usrTest = new User();

        usrTest.setEntityUserIdNumber("123456789");
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
        /*



        */
    
    /**
    @Test
    public void aUserIsCorrectlyCreated() {
        User usr = new User();
        usr.setId("T12");
        usr.setFirstName("Test");
        usr.setAge(10);
        usr.setGender('M');
        usr.setLastName("Test");
        usr.setPhone("1234567891");
        assertTrue(usr.isValid());
        
        UserModel userTestmodel = EasyMock.createMock(UserModel.class);
        expect(userTestmodel.createUser("T13", "Test","","Test",20,'M',"1234567891",12 
        )).andReturn(1);
    }
     */

