import org.junit.Test;
import static org.junit.Assert.*;

import entities.User;

public class EntityUserJunitTest {
    
    public EntityUserJunitTest() {
    }

    @Test
    public void aValidUserIsValid() {
        
        User validUser = createValidUser();
        assertTrue(validUser.isValidEntity());
    }

    @Test
    public void aUserWithNullIdIsNotValid() {
        
        User testUser = new User();

        //testUser.setEntityUserIdNumber("987654321");
        testUser.setEntityUserEmailAddress("testUser@emailserver.com");
        testUser.setMemberName("Mr. Test User");
        testUser.setMemberStreetAddress("555 N. People St.");
        testUser.setMemberCity("People City");
        testUser.setMemberState("WI");
        testUser.setMemberZip(55522);
        testUser.setMemberValidThrough("02/10/2017");

        assertFalse(testUser.isValidEntity());
    }

    @Test
    public void aUserWithNullNameIsNotValid() {
        
        User testUser = new User();

        testUser.setEntityUserIdNumber("987654321");
        testUser.setEntityUserEmailAddress("testUser@emailserver.com");
        //testUser.setMemberName("Mr. Test User");
        testUser.setMemberStreetAddress("555 N. People St.");
        testUser.setMemberCity("People City");
        testUser.setMemberState("WI");
        testUser.setMemberZip(55522);
        testUser.setMemberValidThrough("02/10/2017");

        assertFalse(testUser.isValidEntity());
    }

    @Test
    public void aUserWithNullStreetAddressIsNotValid() {
        
        User testUser = new User();

        testUser.setEntityUserIdNumber("987654321");
        testUser.setEntityUserEmailAddress("testUser@emailserver.com");
        testUser.setMemberName("Mr. Test User");
        //testUser.setMemberStreetAddress("555 N. People St.");
        testUser.setMemberCity("People City");
        testUser.setMemberState("WI");
        testUser.setMemberZip(55522);
        testUser.setMemberValidThrough("02/10/2017");

        assertFalse(testUser.isValidEntity());
    }

    @Test
    public void aUserWithNullCityIsNotValid() {
        
        User testUser = new User();

        testUser.setEntityUserIdNumber("987654321");
        testUser.setEntityUserEmailAddress("testUser@emailserver.com");
        testUser.setMemberName("Mr. Test User");
        testUser.setMemberStreetAddress("555 N. People St.");
        //testUser.setMemberCity("People City");
        testUser.setMemberState("WI");
        testUser.setMemberZip(55522);
        testUser.setMemberValidThrough("02/10/2017");

        assertFalse(testUser.isValidEntity());
    }

    @Test
    public void aUserWithNullStateIsNotValid() {
        
        User testUser = new User();

        testUser.setEntityUserIdNumber("987654321");
        testUser.setEntityUserEmailAddress("testUser@emailserver.com");
        testUser.setMemberName("Mr. Test User");
        testUser.setMemberStreetAddress("555 N. People St.");
        testUser.setMemberCity("People City");
        //testUser.setMemberState("WI");
        testUser.setMemberZip(55522);
        testUser.setMemberValidThrough("02/10/2017");

        assertFalse(testUser.isValidEntity());
    }

    @Test
    public void aUserWithZipCodeNotSetIsNotValid() {
        
        User testUser = new User();

        testUser.setEntityUserIdNumber("987654321");
        testUser.setEntityUserEmailAddress("testUser@emailserver.com");
        testUser.setMemberName("Mr. Test User");
        testUser.setMemberStreetAddress("555 N. People St.");
        testUser.setMemberCity("People City");
        testUser.setMemberState("WI");
        //testUser.setMemberZip(55522);
        testUser.setMemberValidThrough("02/10/2017");

        assertFalse(testUser.isValidEntity());
    }

    @Test
    public void aUserWithCharsInIdIsNotValid() {
        
        User invalidUser = createValidUser();
        
        invalidUser.setEntityUserIdNumber("a87654321");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("9b7654321");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("98c654321");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("987d54321");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("9876e4321");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("98765f321");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("987654g21");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("987654h1");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("98765432i");
        assertFalse(invalidUser.isValidEntity());
    }

    @Test
    public void aUserWithIdLessThenNineDigitsIsNotValid() {
        
        User invalidUser = createValidUser();
        
        invalidUser.setEntityUserIdNumber("87654321");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("654321");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("54321");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("4321");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("321");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("21");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("1");
        assertFalse(invalidUser.isValidEntity());
        
        invalidUser.setEntityUserIdNumber("");
        assertFalse(invalidUser.isValidEntity());
    }

    private User createValidUser(){

        User returnUser = new User();

        returnUser.setEntityUserIdNumber("987654321");
        returnUser.setEntityUserEmailAddress("testUser@emailserver.com");
        returnUser.setMemberName("Mr. Test User");
        returnUser.setMemberStreetAddress("555 N. People St.");
        returnUser.setMemberCity("People City");
        returnUser.setMemberState("WI");
        returnUser.setMemberZip(55522);
        returnUser.setMemberValidThrough("02/10/2017");
        
        return returnUser;
    }
}

/*

TODO : Figure out why this test does not work!

    @Test
    public void aUserWithNullValidThroughIsNotValid() {
        
        User testUser = new User();

        testUser.setEntityUserIdNumber("987654321");
        testUser.setEntityUserEmailAddress("testUser@emailserver.com");
        testUser.setMemberName("Mr. Test User");
        testUser.setMemberStreetAddress("555 N. People St.");
        testUser.setMemberCity("People City");
        testUser.setMemberState("WI");
        testUser.setMemberZip(55522);
        //testUser.setMemberValidThrough("02/10/2017");

        assertFalse(testUser.isValidEntity());
    }
*/
