import org.junit.Test;
import static org.junit.Assert.*;

import entities.Service;

public class EntityServiceJunitTest {
    public EntityServiceJunitTest() {
    }
    
    @Test
    public void aValidServiceIsValid() {
        
        Service validService = createValidService();
        assertTrue(validService.isValidEntity());
    }

    @Test
    public void aServiceWithNullIdIsNotValid() {
        
        Service testService = new Service();

        //testService.setEntityServiceIdNumber("987651");
        testService.setProvidableServiceDescription("Words Words Words");
        testService.setIsProvidableByDietitian(true);
        testService.setIsProvidableByExerciseExpert(true);
        testService.setIsProvidableByInternist(true);

        assertFalse(testService.isValidEntity());
    }

    @Test
    public void aServiceWithNullDescriptionIsNotValid() {
        
        Service testService = new Service();

        testService.setEntityServiceIdNumber("987654");
        //testService.setProvidableServiceDescription("Words Words Words");
        testService.setIsProvidableByDietitian(true);
        testService.setIsProvidableByExerciseExpert(true);
        testService.setIsProvidableByInternist(true);

        assertFalse(testService.isValidEntity());
    }

    @Test
    public void aServiceWithNoTypesTrueIsNotValid() {
        
        Service testService = new Service();

        testService.setEntityServiceIdNumber("987654");
        testService.setProvidableServiceDescription("Words Words Words");
        testService.setIsProvidableByDietitian(false);
        testService.setIsProvidableByExerciseExpert(false);
        testService.setIsProvidableByInternist(false);

        assertFalse(testService.isValidEntity());
    }

    @Test
    public void aServiceWithCharsInIdIsNotValid() {
        
        Service invalidService = createValidService();
        
        invalidService.setEntityServiceIdNumber("a87654");
        assertFalse(invalidService.isValidEntity());
        
        invalidService.setEntityServiceIdNumber("9b7654");
        assertFalse(invalidService.isValidEntity());
        
        invalidService.setEntityServiceIdNumber("98c654");
        assertFalse(invalidService.isValidEntity());
        
        invalidService.setEntityServiceIdNumber("987d54");
        assertFalse(invalidService.isValidEntity());
        
        invalidService.setEntityServiceIdNumber("9876e4");
        assertFalse(invalidService.isValidEntity());
        
        invalidService.setEntityServiceIdNumber("98765f");
        assertFalse(invalidService.isValidEntity());
    }

    @Test
    public void aServiceWithIdLessThenNineDigitsIsNotValid() {
        
        Service invalidService = createValidService();
        
        invalidService.setEntityServiceIdNumber("87654");
        assertFalse(invalidService.isValidEntity());
        
        invalidService.setEntityServiceIdNumber("654");
        assertFalse(invalidService.isValidEntity());
        
        invalidService.setEntityServiceIdNumber("54");
        assertFalse(invalidService.isValidEntity());
        
        invalidService.setEntityServiceIdNumber("4");
        assertFalse(invalidService.isValidEntity());
        
        invalidService.setEntityServiceIdNumber("");
        assertFalse(invalidService.isValidEntity());
    }

    private Service createValidService(){

        Service returnService = new Service();

        returnService.setEntityServiceIdNumber("987654");
        returnService.setProvidableServiceDescription("Words Words Words");
        returnService.setIsProvidableByDietitian(true);
        returnService.setIsProvidableByExerciseExpert(true);
        returnService.setIsProvidableByInternist(true);
        
        return returnService;
    }
}
