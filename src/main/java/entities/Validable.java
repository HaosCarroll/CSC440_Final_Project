package entities;

public class Validable {

    private int nameMaxLength = 25;
    private int streetAddressMaxLength = 25;
    private int cityMaxLength = 14;
    private int StateMaxLength = 2;
    private int zipMaxLength = 5;

    private int entityUserIdNumberLength = 9;
    private int memberNameMaxLength = nameMaxLength;
    private int memberStreetAddressMaxLength = streetAddressMaxLength;
    private int memberCityMaxLength = cityMaxLength;
    private int memberStateMaxLength = StateMaxLength;
    private int memberZipLength = zipMaxLength;

    private int entityProviderIdNumberLength = 9;
    private int providerNameMaxLength = nameMaxLength;
    private int providerStreetAddressMaxLength = streetAddressMaxLength;
    private int providerCityMaxLength = cityMaxLength;
    private int providerStateMaxLength = StateMaxLength;
    private int providerZipLength = zipMaxLength;

    private int entityServiceIdNumberLength = 6;    
    private int entityServiceDescriptionStringMaxLength = 20;    

    private int entityBillableCommentStringMaxLength = 100;

    private boolean validationIsDisable = false;

    public boolean isValidEntity(){

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("* * dBug true IN : Validable.isValidEntity()");
        
        String entityClass = this.getClass().getSimpleName();

        if (dBug) System.out.println("entityClass = " + entityClass);
        
        boolean returnBoolean = false;
        
        switch (entityClass) {
            case "User":
                returnBoolean = isValidEntity((User)this);
                break;
            case "Provider":
                returnBoolean = isValidEntity((Provider)this);
                break;
            case "Service":
                returnBoolean = isValidEntity((Service)this);
                break;
            case "Billable":
                returnBoolean = isValidEntity((Billable)this);
                break;
            default:
                returnBoolean = isValidEntity(this);
        }
        return returnBoolean;
    }

    private boolean isValidEntity(User user){

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("* * dBug true IN : Validable.isValidEntity(User user)");
        
        if (validationIsDisable) return true;

        boolean returnBoolean = true;
        
        if(user.getEntityUserIdNumber() == null) {
            returnBoolean = false;
        } else {
            if(user.getEntityUserIdNumber().length() > entityUserIdNumberLength)
                returnBoolean = false;
            if(user.getEntityUserIdNumber().length() < entityUserIdNumberLength)
                returnBoolean = false;
            if (thereAreNonDigitCharsInString(user.getEntityUserIdNumber()))
                returnBoolean = false;
        }

        if(user.getMemberName() == null || user.getMemberName().length() > memberNameMaxLength)
            returnBoolean = false;

        if(user.getMemberStreetAddress() == null || user.getMemberStreetAddress().length() > memberStreetAddressMaxLength)
            returnBoolean = false;

        if(user.getMemberCity() == null || user.getMemberCity().length() > memberCityMaxLength)
            returnBoolean = false;

        if(user.getMemberState() == null || user.getMemberState().length() > memberStateMaxLength)
            returnBoolean = false;

        if(user.getMemberZip() == 0 || (String.valueOf(user.getMemberZip())).length() != memberZipLength)
            returnBoolean = false;

        if(user.getMemberValidThrough() == null)
            returnBoolean = false;

        if (thereAreNonDigitCharsInString(String.valueOf(user.getMemberZip())))
            returnBoolean = false;

        return returnBoolean;
    }

    private boolean isValidEntity(Provider provider){

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("* * dBug true IN : Validable.isValidEntity(Provider provider)");

        if (validationIsDisable) return true;

        boolean returnBoolean = true;
        
        if(provider.getEntityProviderIdNumber() == null) {
            returnBoolean = false;
        } else {
            if(provider.getEntityProviderIdNumber().length() > entityProviderIdNumberLength)
                returnBoolean = false;
            if(provider.getEntityProviderIdNumber().length() < entityProviderIdNumberLength)
                returnBoolean = false;
            if (thereAreNonDigitCharsInString(provider.getEntityProviderIdNumber()))
                returnBoolean = false;
        }


        if(provider.getProviderName() == null || provider.getProviderName().length() > providerNameMaxLength)
            return false;

        if(provider.getProviderStreetAddress() == null || provider.getProviderStreetAddress().length() > providerStreetAddressMaxLength)
            return false;

        if(provider.getProviderCity() == null || provider.getProviderCity().length() > providerCityMaxLength)
            return false;

        if(provider.getProviderState() == null || provider. getProviderState().length() > providerStateMaxLength)
            return false;

        if(provider.getProviderZip() == 0 || (String.valueOf(provider.getProviderZip())).length() != providerZipLength)
            return false;
        
        if (thereAreNonDigitCharsInString(String.valueOf(provider.getProviderZip())))
            returnBoolean = false;

        return returnBoolean;
    }

    private boolean isValidEntity(Service service){

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("* * dBug true IN : Validable.isValidEntity(Service service)");

        if (validationIsDisable) return true;

        boolean returnBoolean = true;
        
        if(service.getEntityServiceIdNumber() == null) {
            returnBoolean = false;
        } else {
            if(service.getEntityServiceIdNumber().length() > entityServiceIdNumberLength)
                returnBoolean = false;
            if(service.getEntityServiceIdNumber().length() < entityServiceIdNumberLength)
                returnBoolean = false;
            if (thereAreNonDigitCharsInString(service.getEntityServiceIdNumber()))
                returnBoolean = false;
        }

        if(service.getProvidableServiceDescription() == null || service.getProvidableServiceDescription().length() > entityServiceDescriptionStringMaxLength)
            returnBoolean = false;

        return returnBoolean;
    }

    private boolean isValidEntity(Billable billable){

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("* * dBug true IN : Validable.isValidEntity(Billable billable)");

        if (validationIsDisable) return true;

        boolean returnBoolean = true;
        
        if(billable.getEntityBillableIdNumber() == null) {
            returnBoolean = false;
        } else {
            if (thereAreNonDigitCharsInString(billable.getEntityBillableIdNumber()))
                returnBoolean = false;
        }
        
        if(billable.getMemberNumberService() == null) {
            returnBoolean = false;
        } else {
            if(billable.getMemberNumberService().length() > entityUserIdNumberLength)
                returnBoolean = false;
            if(billable.getMemberNumberService().length() < entityUserIdNumberLength)
                returnBoolean = false;
            if (thereAreNonDigitCharsInString(billable.getMemberNumberService()))
                returnBoolean = false;
        }

        if(billable.getProviderNumberServicing() == null) {
            returnBoolean = false;
        } else {
            if(billable.getProviderNumberServicing().length() > entityProviderIdNumberLength)
                returnBoolean = false;
            if(billable.getProviderNumberServicing().length() < entityProviderIdNumberLength)
                returnBoolean = false;
            if (thereAreNonDigitCharsInString(billable.getProviderNumberServicing()))
                returnBoolean = false;
        }

        if(billable.getServiceNumberServiced() == null) {
            returnBoolean = false;
        } else {
            if(billable.getServiceNumberServiced().length() > entityServiceIdNumberLength)
                returnBoolean = false;
            if(billable.getServiceNumberServiced().length() < entityServiceIdNumberLength)
                returnBoolean = false;
            if (thereAreNonDigitCharsInString(billable.getServiceNumberServiced()))
                returnBoolean = false;
        }
        
        if(billable.getServiceComment().length() > entityBillableCommentStringMaxLength)
            returnBoolean = false;
/*

TODO : Validate dates!

        if(billable.getDateServiced() == null || !billable.getDateServiced().matches("^\\d{2}\\-\\d{2}\\-\\d{4}"))
            return false;
        if(billable.getDateServicedRecorded() == null || !billable.getDateServicedRecorded().matches("^\\d{2}\\-\\d{2}\\-\\d{4}\\s{1}\\d{2}\\:\\d{2}\\:\\d{2}"))
            return false;
        if(billable.getServiceComment().length() > 100)
            return false;
*/

        return returnBoolean;
    }


    private boolean isValidEntity(Object objectIn){

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("* * dBug true IN : Validable.isValid(Object objectIn)");

        return false;
    }
    
    private boolean thereAreNonDigitCharsInString(String stringToTestForOnlyDigits){
        
        boolean returnBoolean = false;
        
        for(int i = 0; i < stringToTestForOnlyDigits.length(); i++){
            if(!Character.isDigit(stringToTestForOnlyDigits.charAt(i)))
                returnBoolean = true;
        }
        
        return returnBoolean;
    }
}