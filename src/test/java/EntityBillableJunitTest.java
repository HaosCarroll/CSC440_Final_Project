import org.junit.Test;
import static org.junit.Assert.*;
import org.joda.time.*;
import entities.Billable;

public class EntityBillableJunitTest {
    public EntityBillableJunitTest() {
    }
    
    @Test
    public void aValidBillableIsValid() {
        
        Billable validBillable = createValidBillable();
        assertTrue(validBillable.isValidEntity());
    }
    
    @Test
    public void aBillableWithNullBillableIdIsNotValid() {
        
        Billable testBillable = new Billable();

        //testBillable.setEntityBillableIdNumber("123456789");
        testBillable.setMemberNumberService("987654321");
        testBillable.setProviderNumberServicing("123456789");
        testBillable.setServiceNumberServiced("123456");
        testBillable.setDateServiced("12/21/2012");
        testBillable.setDateServicedRecorded();
        testBillable.setServiceCost(100.00);
        testBillable.setServiceComment("Words Words Words");

        assertFalse(testBillable.isValidEntity());
    }

    @Test
    public void aBillableWithNullMemberIdIsNotValid() {
        
        Billable testBillable = new Billable();

        testBillable.setEntityBillableIdNumber("123456789");
        //testBillable.setMemberNumberService("987654321");
        testBillable.setProviderNumberServicing("123456789");
        testBillable.setServiceNumberServiced("123456");
        testBillable.setDateServiced("12/21/2012");
        testBillable.setDateServicedRecorded();
        testBillable.setServiceCost(100.00);
        testBillable.setServiceComment("Words Words Words");

        assertFalse(testBillable.isValidEntity());
    }
    
    @Test
    public void aBillableWithNullProviderIdIsNotValid() {
        
        Billable testBillable = new Billable();

        testBillable.setEntityBillableIdNumber("123456789");
        testBillable.setMemberNumberService("987654321");
        //testBillable.setProviderNumberServicing("123456789");
        testBillable.setServiceNumberServiced("123456");
        testBillable.setDateServiced("12/21/2012");
        testBillable.setDateServicedRecorded();
        testBillable.setServiceCost(100.00);
        testBillable.setServiceComment("Words Words Words");

        assertFalse(testBillable.isValidEntity());
    }
    
    @Test
    public void aBillableWithNullServiceIdIsNotValid() {
        
        Billable testBillable = new Billable();

        testBillable.setEntityBillableIdNumber("123456789");
        testBillable.setMemberNumberService("987654321");
        testBillable.setProviderNumberServicing("123456789");
        //testBillable.setServiceNumberServiced("123456");
        testBillable.setDateServiced("12/21/2012");
        testBillable.setDateServicedRecorded();
        testBillable.setServiceCost(100.00);
        testBillable.setServiceComment("Words Words Words");

        assertFalse(testBillable.isValidEntity());
    }
    
    @Test
    public void aBillableWithNullDateServicedIsNotValid() {
        
        Billable testBillable = new Billable();

        testBillable.setEntityBillableIdNumber("123456789");
        testBillable.setMemberNumberService("987654321");
        testBillable.setProviderNumberServicing("123456789");
        testBillable.setServiceNumberServiced("123456");
        //testBillable.setDateServiced("12/21/2012");
        testBillable.setDateServicedRecorded();
        testBillable.setServiceCost(100.00);
        testBillable.setServiceComment("Words Words Words");

        assertFalse(testBillable.isValidEntity());
    }
    
    
    @Test
    public void aBillableWithNullDateServicedRecordedIsNotValid() {
        
        Billable testBillable = new Billable();

        testBillable.setEntityBillableIdNumber("123456789");
        testBillable.setMemberNumberService("987654321");
        testBillable.setProviderNumberServicing("123456789");
        testBillable.setServiceNumberServiced("123456");
        testBillable.setDateServiced("12/21/2012");
        //testBillable.setDateServicedRecorded();
        testBillable.setServiceCost(100.00);
        testBillable.setServiceComment("Words Words Words");

        assertFalse(testBillable.isValidEntity());
    }
    
    @Test
    public void aBillableWithNullServiceCostIsNotValid() {
        
        Billable testBillable = new Billable();

        testBillable.setEntityBillableIdNumber("123456789");
        testBillable.setMemberNumberService("987654321");
        testBillable.setProviderNumberServicing("123456789");
        testBillable.setServiceNumberServiced("123456");
        testBillable.setDateServiced("12/21/2012"); 
        testBillable.setDateServicedRecorded();
        //testBillable.setServiceCost(100.00);
        testBillable.setServiceComment("Words Words Words");

        assertFalse(testBillable.isValidEntity());
    }
    
    @Test
    public void aBillableWithNullServiceCommentIsValid() {
        //SHOULD RETURN TRUE; COMMENT IS NOT NEEDED
        Billable testBillable = new Billable();

        testBillable.setEntityBillableIdNumber("123456789");
        testBillable.setMemberNumberService("987654321");
        testBillable.setProviderNumberServicing("123456789");
        testBillable.setServiceNumberServiced("123456");
        testBillable.setDateServiced("12/21/2012");
        testBillable.setDateServicedRecorded();
        testBillable.setServiceCost(100.00);
        //testBillable.setServiceComment("Words Words Words");

        assertTrue(testBillable.isValidEntity());
    }
    @Test
    public void aBillableWithCharsInBillableIdIsNotValid() {
        
        Billable invalidBillable = createValidBillable();
        
        invalidBillable.setEntityBillableIdNumber("a87654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setEntityBillableIdNumber("9b7654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setEntityBillableIdNumber("98c654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setEntityBillableIdNumber("987d54321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setEntityBillableIdNumber("9876e4321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setEntityBillableIdNumber("98765f321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setEntityBillableIdNumber("987654g21");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setEntityBillableIdNumber("987654h1");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setEntityBillableIdNumber("98765432i");
        assertFalse(invalidBillable.isValidEntity());
    }
    
    @Test
    public void aBillableWithCharsInMemberIdIsNotValid() {
        
        Billable invalidBillable = createValidBillable();
        
        invalidBillable.setMemberNumberService("a87654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("9b7654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("98c654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("987d54321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("9876e4321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("98765f321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("987654g21");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("987654h1");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("98765432i");
        assertFalse(invalidBillable.isValidEntity());
    }
    
    @Test
    public void aBillableWithMemberIdLessThenNineDigitsIsNotValid() {
        
        Billable invalidBillable = createValidBillable();
        
        invalidBillable.setMemberNumberService("87654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("54321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("4321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("21");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("1");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setMemberNumberService("");
        assertFalse(invalidBillable.isValidEntity());
    }
    
    @Test
    public void aBillableWithCharsInProviderIdIsNotValid() {
        
        Billable invalidBillable = createValidBillable();
        
        invalidBillable.setProviderNumberServicing("a87654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("9b7654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("98c654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("987d54321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("9876e4321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("98765f321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("987654g21");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("987654h1");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("98765432i");
        assertFalse(invalidBillable.isValidEntity());
    }
    
    @Test
    public void aBillableWithProviderIdLessThenNineDigitsIsNotValid() {
        
        Billable invalidBillable = createValidBillable();
        
        invalidBillable.setProviderNumberServicing("87654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("654321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("54321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("4321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("321");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("21");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("1");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setProviderNumberServicing("");
        assertFalse(invalidBillable.isValidEntity());
    }
    
    @Test
    public void aBillableWithCharsInServiceIdIsNotValid() {
        
        Billable invalidBillable = createValidBillable();
        
        invalidBillable.setServiceNumberServiced("a87654");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setServiceNumberServiced("9b7654");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setServiceNumberServiced("98c654");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setServiceNumberServiced("987d54");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setServiceNumberServiced("9876e4");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setServiceNumberServiced("98765f");
        assertFalse(invalidBillable.isValidEntity());
    }
    
    @Test
    public void aBillableWithServiceIdLessThenNineDigitsIsNotValid() {
        
        Billable invalidBillable = createValidBillable();
        
        invalidBillable.setServiceNumberServiced("87654");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setServiceNumberServiced("651");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setServiceNumberServiced("54");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setServiceNumberServiced("4");
        assertFalse(invalidBillable.isValidEntity());
        
        invalidBillable.setServiceNumberServiced("");
        assertFalse(invalidBillable.isValidEntity());
    }

    private Billable createValidBillable(){

        Billable returnBillable = new Billable();

        returnBillable.setEntityBillableIdNumber("123456789");
        returnBillable.setMemberNumberService("987654321");
        returnBillable.setProviderNumberServicing("123456789");
        returnBillable.setServiceNumberServiced("123456");
        returnBillable.setDateServiced("12/21/2012");
        returnBillable.setDateServicedRecorded();
        returnBillable.setServiceCost(100.00);
        returnBillable.setServiceComment("Words Words Words");
        
        return returnBillable;
    }
}
