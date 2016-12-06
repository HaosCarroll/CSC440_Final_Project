import org.junit.Test;
import static org.junit.Assert.*;

import entities.Provider;

public class EntityProviderJunitTest {
    public EntityProviderJunitTest() {
    }
    
    @Test
    public void aValidProviderIsValid() {
        
        Provider validProvider = createValidProvider();
        assertTrue(validProvider.isValidEntity());
    }

    @Test
    public void aProviderWithNullIdIsNotValid() {
        
        Provider testProvider = new Provider();

        //testProvider.setEntityProviderIdNumber("987654321");
        testProvider.setEntityProviderEmailAddress("testUser@emailserver.com");
        testProvider.setProviderName("Mr. Test User");
        testProvider.setProviderStreetAddress("555 N. People St.");
        testProvider.setProviderCity("People City");
        testProvider.setProviderState("WI");
        testProvider.setProviderZip(55522);
        testProvider.setIsDietitian(true);
        testProvider.setIsExerciseExpert(true);
        testProvider.setIsInternist(true);

        assertFalse(testProvider.isValidEntity());
    }

    @Test
    public void aProviderWithNullNameIsNotValid() {
        
        Provider testProvider = new Provider();

        testProvider.setEntityProviderIdNumber("987654321");
        testProvider.setEntityProviderEmailAddress("testUser@emailserver.com");
        //testProvider.setProviderName("Mr. Test User");
        testProvider.setProviderStreetAddress("555 N. People St.");
        testProvider.setProviderCity("People City");
        testProvider.setProviderState("WI");
        testProvider.setProviderZip(55522);
        testProvider.setIsDietitian(true);
        testProvider.setIsExerciseExpert(true);
        testProvider.setIsInternist(true);

        assertFalse(testProvider.isValidEntity());
    }

    @Test
    public void aProviderWithNullStreetAddressIsNotValid() {
        
        Provider testProvider = new Provider();

        testProvider.setEntityProviderIdNumber("987654321");
        testProvider.setEntityProviderEmailAddress("testUser@emailserver.com");
        testProvider.setProviderName("Mr. Test User");
        //testProvider.setProviderStreetAddress("555 N. People St.");
        testProvider.setProviderCity("People City");
        testProvider.setProviderState("WI");
        testProvider.setProviderZip(55522);
        testProvider.setIsDietitian(true);
        testProvider.setIsExerciseExpert(true);
        testProvider.setIsInternist(true);

        assertFalse(testProvider.isValidEntity());
    }

    @Test
    public void aProviderWithNullCityIsNotValid() {
        
        Provider testProvider = new Provider();

        testProvider.setEntityProviderIdNumber("987654321");
        testProvider.setEntityProviderEmailAddress("testUser@emailserver.com");
        testProvider.setProviderName("Mr. Test User");
        testProvider.setProviderStreetAddress("555 N. People St.");
        //testProvider.setProviderCity("People City");
        testProvider.setProviderState("WI");
        testProvider.setProviderZip(55522);
        testProvider.setIsDietitian(true);
        testProvider.setIsExerciseExpert(true);
        testProvider.setIsInternist(true);

        assertFalse(testProvider.isValidEntity());
    }

    @Test
    public void aProviderWithNullStateIsNotValid() {
        
        Provider testProvider = new Provider();

        testProvider.setEntityProviderIdNumber("987654321");
        testProvider.setEntityProviderEmailAddress("testUser@emailserver.com");
        testProvider.setProviderName("Mr. Test User");
        testProvider.setProviderStreetAddress("555 N. People St.");
        testProvider.setProviderCity("People City");
        //testProvider.setProviderState("WI");
        testProvider.setProviderZip(55522);
        testProvider.setIsDietitian(true);
        testProvider.setIsExerciseExpert(true);
        testProvider.setIsInternist(true);

        assertFalse(testProvider.isValidEntity());
    }

    @Test
    public void aProviderWithZipCodeNotSetIsNotValid() {
        
        Provider testProvider = new Provider();

        testProvider.setEntityProviderIdNumber("987654321");
        testProvider.setEntityProviderEmailAddress("testUser@emailserver.com");
        testProvider.setProviderName("Mr. Test User");
        testProvider.setProviderStreetAddress("555 N. People St.");
        testProvider.setProviderCity("People City");
        testProvider.setProviderState("WI");
        //testProvider.setProviderZip(55522);
        testProvider.setIsDietitian(true);
        testProvider.setIsExerciseExpert(true);
        testProvider.setIsInternist(true);

        assertFalse(testProvider.isValidEntity());
    }
    
    @Test
    public void aProviderWithNoTypesTrueIsNotValid() {
        
        Provider testProvider = new Provider();

        testProvider.setEntityProviderIdNumber("987654321");
        testProvider.setEntityProviderEmailAddress("testUser@emailserver.com");
        testProvider.setProviderName("Mr. Test User");
        testProvider.setProviderStreetAddress("555 N. People St.");
        testProvider.setProviderCity("People City");
        testProvider.setProviderState("WI");
        testProvider.setProviderZip(55522);
        testProvider.setIsDietitian(false);
        testProvider.setIsExerciseExpert(false);
        testProvider.setIsInternist(false);

        assertFalse(testProvider.isValidEntity());
    }
    

    @Test
    public void aProviderWithCharsInIdIsNotValid() {
        
        Provider invalidProvider = createValidProvider();
        
        invalidProvider.setEntityProviderIdNumber("a87654321");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("9b7654321");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("98c654321");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("987d54321");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("9876e4321");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("98765f321");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("987654g21");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("987654h1");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("98765432i");
        assertFalse(invalidProvider.isValidEntity());
    }

    @Test
    public void aProviderWithIdLessThenNineDigitsIsNotValid() {
        
        Provider invalidProvider = createValidProvider();
        
        invalidProvider.setEntityProviderIdNumber("87654321");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("654321");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("54321");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("4321");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("321");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("21");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("1");
        assertFalse(invalidProvider.isValidEntity());
        
        invalidProvider.setEntityProviderIdNumber("");
        assertFalse(invalidProvider.isValidEntity());
    }

    private Provider createValidProvider(){

        Provider returnProvider = new Provider();

        returnProvider.setEntityProviderIdNumber("987654321");
        returnProvider.setEntityProviderEmailAddress("testUser@emailserver.com");
        returnProvider.setProviderName("Mr. Test Provider");
        returnProvider.setProviderStreetAddress("555 N. People St.");
        returnProvider.setProviderCity("People City");
        returnProvider.setProviderState("WI");
        returnProvider.setProviderZip(55522);
        returnProvider.setIsDietitian(true);
        returnProvider.setIsExerciseExpert(true);
        returnProvider.setIsInternist(true);
        
        return returnProvider;
    }
}
