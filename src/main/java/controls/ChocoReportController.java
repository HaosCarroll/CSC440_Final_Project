package controls;

// Needed for : convertObjectToJSON() Method.
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.StringWriter;

//import org.joda.time.DateTime;
import org.joda.time.*;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entities.*;
import drivers.*;

import utilities.*;

public class ChocoReportController {
    
    // This is hacky but it works for now.
    private int numConsultsForLastReport;
    private double totalFeesForLastReport;

    // Basic Constructor.
    public ChocoReportController() {
        numConsultsForLastReport = 0;
        totalFeesForLastReport = 0.00;
    }

    public String getHtmlStringUserAddress(UserRepository userRepository, String idToQuery){

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getHtmlStringUserAddress()\n");

        String returnString = "";
        String id =  idToQuery;
        
        User queriedUser = userRepository.findOneByEntityUserIdNumber(id);
        
        returnString += String.format("<h2>%s</h2>\n", queriedUser.getMemberName());
        returnString += String.format("<h3> USER ID# : %s</h3>\n", queriedUser.getEntityUserIdNumber());
        returnString += String.format("<p>\n");
        returnString += String.format("   %s<br>\n", queriedUser.getMemberStreetAddress());
        returnString += String.format("   %s<br>\n", queriedUser.getMemberCity());
        returnString += String.format("   %s<br>\n", queriedUser.getMemberState());
        returnString += String.format("   %s<br>\n", String.valueOf(queriedUser.getMemberZip()));
        returnString += String.format("</p>\n");
        
        if (dBug) System.out.println("returnString:\n" + returnString);

        return returnString;        
    }

    public String getBillablesReportForUserByDateRangeInJson(BillableRepository billableRepository, ProviderRepository providerRepository, ServiceRepository serviceRepository, String idOfUser, DateTime startDateTime, DateTime endDateTime){

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.gettBillablesForUser()\n");

        String returnString = "";
        
        // Create a list of billables for the user being queried.
        List<Billable> usersBillables = billableRepository.findByMemberNumberServiceAndDateServicedRecordedBetween(idOfUser, startDateTime, endDateTime);

        // Start of returned a JSON string.
        returnString += "[\n";
        
        // Iterate through the list of billables for the user.
        for (int i = 0; i < usersBillables.size(); i++){
            
            // Required spec #1 for user report.
            String serviceProvidedDate = usersBillables.get(i).getDateServiced();
            
            // Required spec #2 for user report.
            String serviceProvidedProviderId = usersBillables.get(i).getProviderNumberServicing();
            Provider providerProvidingService = providerRepository.findOneByEntityProviderIdNumber(serviceProvidedProviderId);
            String providerProvidingServiceNameString = providerProvidingService.getProviderName();
            
            // Required spec #3 for user report.
            String serviceProvidedId = usersBillables.get(i).getServiceNumberServiced();
            Service providedService =serviceRepository.findOneByEntityServiceIdNumber(serviceProvidedId);
            String serviceProvidedName = providedService.getProvidableServiceDescription();
            
            // Turn specs into JSON.
            String temp = "   {\n";
            temp += "      \"Servicing Provided Date\" : \"" + serviceProvidedDate + "\",\n";
            temp += "      \"Provider Servicing\" : \"" + providerProvidingServiceNameString + "\",\n";
            temp += "      \"Provided Service Name\" : \"" + serviceProvidedName + "\"\n";

            // Add JSON element end depending on if is or is not last element.
            if (i < (usersBillables.size()-1)){
                temp += "   },\n";
            } else {
                temp += "   }\n";
            }
            returnString += temp;
        }
        returnString += "]"; // End  of returned a JSON string.
        
        if (dBug) System.out.printf ("\nQuerried id = %s\n", idOfUser);
        if (dBug) System.out.printf ("\n# Billables for id = %s\n", usersBillables.size());

        return returnString;
    }

    private List<String> getListOfDatesThatHaveBillables(BillableRepository billableRepository){
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getListOfDatesThatHaveBillables(...)\n");

        List<String> returnStringList = new ArrayList<String>();

        DateTime earliestDateTime = getDateTimeOfFirstBillableInRepository(billableRepository);
        DateTime latestDateTime = getDateTimeOfLastBillableInRepository(billableRepository);
        
        DateTime todaysDateTime = new DateTime();
        DateTime endDateTime;
        DateTime startDateTime;
        
        PairDateTime dateTimePair = null;
        JodaTimeUtil jodaTimeUtil = new JodaTimeUtil();
        
        if (latestDateTime.isAfter(previousFridayFromDate(todaysDateTime))){
            if (dBug) System.out.println("\nUnreported Records Exist for this week!\n");

            dateTimePair = jodaTimeUtil.getPreviousAndFollowingDateTimeCutoffsOfDateTime(todaysDateTime);
            
            startDateTime = dateTimePair.getFirst();
            endDateTime = dateTimePair.getFinal();
        } else {
            if (dBug) System.out.println("\nNo records for this week.\n");

            dateTimePair = jodaTimeUtil.getPreviousAndFollowingDateTimeCutoffsOfDateTime(latestDateTime);

            startDateTime = dateTimePair.getFirst();
            endDateTime = dateTimePair.getFinal();
        }
        
        returnStringList.add(startDateTime.toString());
        
        if (dBug) System.out.println("todaysDateTime.toString() = " + todaysDateTime.toString("MM-dd-yyyy"));
        if (dBug) System.out.println("\nearliestDateTime.toString() = " + earliestDateTime.toString("MM-dd-yyyy"));
        if (dBug) System.out.println("\nlatestDateTime.toString() = " + latestDateTime.toString("MM-dd-yyyy"));
        if (dBug) System.out.println("\nstartDateTime.toString() = " + startDateTime.toString("MM-dd-yyyy"));
        if (dBug) System.out.println("endDateTime.toString() = " + endDateTime.toString("MM-dd-yyyy") + "\n");

        while (startDateTime.isAfter(earliestDateTime)){
            startDateTime = startDateTime.minusWeeks(1);
            endDateTime = endDateTime.minusWeeks(1);
            
            int numBillablesInWeek = billableRepository.findByDateServicedRecordedBetween(startDateTime, endDateTime).size();
            
            if ( numBillablesInWeek > 0){
                if (dBug) System.out.printf("(%s - %s) * FOUND %s RECORD(S).\n", startDateTime.toString("MM-dd-yyyy"), endDateTime.toString("MM-dd-yyyy"), numBillablesInWeek);
                returnStringList.add(startDateTime.toString());
            } else {
                if (dBug) System.out.printf("(%s - %s) * NONE.\n", startDateTime.toString(), endDateTime.toString());
            }
        }
        
        if (dBug){
            System.out.printf("\n%s DATE RANGE(S) WITH RECORDS FOUND.\n\n", returnStringList.size());
            for (int i = 0; i < returnStringList.size(); i++){
                System.out.printf("%s (list item %d)\n", returnStringList.get(i), i);
            }
            System.out.printf("-end-\n");
        }
        return returnStringList;
    }

    private List<String> getListOfDatesThatHaveBillablesForUser(BillableRepository billableRepository, String idOfUser){
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getListOfDatesThatHaveBillablesForUser(...)\n");

        List<String> returnStringList = new ArrayList<String>();

        DateTime earliestDateTime = getDateTimeOfFirstBillableInRepositoryForUserId(billableRepository, idOfUser);
        DateTime latestDateTime = getDateTimeOfLastBillableInRepositoryForUserId(billableRepository, idOfUser);
        
        DateTime todaysDateTime = new DateTime();
        DateTime endDateTime;
        DateTime startDateTime;
        
        PairDateTime dateTimePair = null;
        JodaTimeUtil jodaTimeUtil = new JodaTimeUtil();
        
        if (latestDateTime.isAfter(previousFridayFromDate(todaysDateTime))){
            if (dBug) System.out.println("\nUnreported Records Exist for this week!\n");

            dateTimePair = jodaTimeUtil.getPreviousAndFollowingDateTimeCutoffsOfDateTime(todaysDateTime);
            
            startDateTime = dateTimePair.getFirst();
            endDateTime = dateTimePair.getFinal();
        } else {
            if (dBug) System.out.println("\nNo records for this week.\n");

            dateTimePair = jodaTimeUtil.getPreviousAndFollowingDateTimeCutoffsOfDateTime(latestDateTime);

            startDateTime = dateTimePair.getFirst();
            endDateTime = dateTimePair.getFinal();
        }
        
        returnStringList.add(startDateTime.toString());
        
        if (dBug) System.out.println("todaysDateTime.toString() = " + todaysDateTime.toString("MM-dd-yyyy"));
        if (dBug) System.out.println("\nearliestDateTime.toString() = " + earliestDateTime.toString("MM-dd-yyyy"));
        if (dBug) System.out.println("\nlatestDateTime.toString() = " + latestDateTime.toString("MM-dd-yyyy"));
        if (dBug) System.out.println("\nstartDateTime.toString() = " + startDateTime.toString("MM-dd-yyyy"));
        if (dBug) System.out.println("endDateTime.toString() = " + endDateTime.toString("MM-dd-yyyy") + "\n");

        while (startDateTime.isAfter(earliestDateTime)){
            startDateTime = startDateTime.minusWeeks(1);
            endDateTime = endDateTime.minusWeeks(1);
            
            int numBillablesInWeek = billableRepository.findByMemberNumberServiceAndDateServicedRecordedBetween(idOfUser, startDateTime, endDateTime).size();
            
            if ( numBillablesInWeek > 0){
                if (dBug) System.out.printf("(%s - %s) * FOUND %s RECORD(S).\n", startDateTime.toString("MM-dd-yyyy"), endDateTime.toString("MM-dd-yyyy"), numBillablesInWeek);
                returnStringList.add(startDateTime.toString());
            } else {
                if (dBug) System.out.printf("(%s - %s) * NONE.\n", startDateTime.toString(), endDateTime.toString());
            }
        }
        
        if (dBug){
            System.out.printf("\n%s DATE RANGE(S) WITH RECORDS FOUND.\n\n", returnStringList.size());
            for (int i = 0; i < returnStringList.size(); i++){
                System.out.printf("%s (list item %d)\n", returnStringList.get(i), i);
            }
            System.out.printf("-end-\n");
        }
        return returnStringList;
    }

    public String getBillablesReportForUserInJson(BillableRepository billableRepository, ProviderRepository providerRepository, ServiceRepository serviceRepository, String idOfUser){

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.gettBillablesForUser()\n");

        String returnString = "";
        
        // Create a list of billables for the user being queried.
        List<Billable> usersBillables = billableRepository.findByMemberNumberService(idOfUser);

        // Start of returned a JSON string.
        returnString += "[\n";
        
        // Iterate through the list of billables for the user.
        for (int i = 0; i < usersBillables.size(); i++){
            
            // Required spec #1 for user report.
            String serviceProvidedDate = usersBillables.get(i).getDateServiced();
            
            // Required spec #2 for user report.
            String serviceProvidedProviderId = usersBillables.get(i).getProviderNumberServicing();
            Provider providerProvidingService = providerRepository.findOneByEntityProviderIdNumber(serviceProvidedProviderId);
            String providerProvidingServiceNameString = providerProvidingService.getProviderName();
            
            // Required spec #3 for user report.
            String serviceProvidedId = usersBillables.get(i).getServiceNumberServiced();
            Service providedService =serviceRepository.findOneByEntityServiceIdNumber(serviceProvidedId);
            String serviceProvidedName = providedService.getProvidableServiceDescription();

            // Turn specs into JSON.
            String temp = "{\n";
            temp += "\"Servicing Provided Date\" : \"" + serviceProvidedDate + "\",\n";
            temp += "\"Provider Servicing\" : \"" + providerProvidingServiceNameString + "\",\n";
            temp += "\"Provided Service Name\" : \"" + serviceProvidedName + "\"\n";

            // Add JSON element end depending on if is or is not last element.
            if (i < (usersBillables.size()-1)){
                temp += "\n},\n";
            } else {
                temp += "\n}\n";
            }
            returnString += temp;
        }
        
        // End  of returned a JSON string.
        returnString += "]";
        
        if (dBug) System.out.printf ("\nQuerried id = %s\n", idOfUser);
        if (dBug) System.out.printf ("\n# Billables for id = %s\n", usersBillables.size());

        return returnString;
    }

    public String gettBillablesForUserInJson(BillableRepository billableRepository, String idOfUser){

        // This entire method is not used in production but is useful for testing.

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.gettBillablesForUser()\n");

        String returnString = "";
        
        List<Billable> usersBillables = billableRepository.findByMemberNumberService(idOfUser);

        returnString += "[\n";
        for (int i = 0; i < usersBillables.size(); i++){
            String temp = convertObjectToJSON(usersBillables.get(i));
            if (i < (usersBillables.size()-1)){
                temp += ",\n";
            }
            returnString += temp;
        }
        returnString += "\n]";
        
        if (dBug) System.out.printf ("\nQuerried id = %s\n", idOfUser);
        if (dBug) System.out.printf ("\n# Billables for id = %s\n", usersBillables.size());

        return returnString;
    }

    public String getHtmlStringProviderAddress(ProviderRepository providerRepository, String idToQuery){
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getHtmlStringUserAddress()\n");

        String returnString = "";
        String id =  idToQuery;
        
        Provider queriedProvider = providerRepository.findOneByEntityProviderIdNumber(id);
        
        returnString += String.format("<p>\n");
        returnString += String.format("<h2>%s</h2>\n", queriedProvider.getProviderName());
        returnString += String.format("   %s<br>\n", queriedProvider.getProviderStreetAddress());
        returnString += String.format("   %s, %s<br>\n", queriedProvider.getProviderCity(), queriedProvider.getProviderState());
        returnString += String.format("   %s<br>\n", String.valueOf(queriedProvider.getProviderZip()));
        returnString += String.format("<h4>ID# : %s</h>\n", queriedProvider.getEntityProviderIdNumber());
        returnString += String.format("</p>\n");
        
        if (dBug) System.out.println("returnString:\n" + returnString);

        return returnString;        
    }

    public String getJsonListOfDatesThatHaveBillables(BillableRepository billableRepository){
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getJsonListOfDatesThatHaveBillables()\n");

        String returnString = "";

        List <String> stringList;
        stringList = getListOfDatesThatHaveBillables(billableRepository);
         
        String temp = "[\n"; // Just cuz?

        for (int i = 0; i < stringList.size(); i++){
            temp += String.format("   \"%s\"", new DateTime(stringList.get(i)).toString("yyyy-MM-dd"));
            if (i+1 < stringList.size()){
                temp += ",\n";
            } else {
                temp += "\n";
            }
        }
        temp += "]\n";
        
        returnString = temp;
        
        if (dBug) System.out.printf("returnString:\n%s\n", returnString);
        return returnString;
    }

    public String getJsonListOfDatesThatHaveBillablesForUser(BillableRepository billableRepository, String idOfUser){
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getJsonListOfDatesThatHaveBillablesForProvider(...)\n");

        String returnString = "";
        
        List<String> stringList;
        stringList = getListOfDatesThatHaveBillablesForUser(billableRepository, idOfUser);
        
        String temp = "[\n"; // Just cuz?

        for (int i = 0; i < stringList.size(); i++){
            temp += String.format("   \"%s\"", new DateTime(stringList.get(i)).toString("yyyy-MM-dd"));
            if (i+1 < stringList.size()){
                temp += ",\n";
            } else {
                temp += "\n";
            }
        }
        temp += "]\n";
        
        returnString = temp;
        
        if (dBug) System.out.println("returnString:");
        if (dBug) System.out.println(returnString);
        
        return returnString;
    }

    public String getJsonListOfDatesThatHaveBillablesForProvider(BillableRepository billableRepository, String idOfProvider){
        String returnString = "";
        
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getJsonListOfDatesThatHaveBillablesForProvider(...)\n");
        
        List<String> stringList;
        stringList = getListOfDatesThatHaveBillablesForProvider(billableRepository, idOfProvider);
        
        String temp = "[\n"; // Just cuz?

        for (int i = 0; i < stringList.size(); i++){
            temp += String.format("   \"%s\"", new DateTime(stringList.get(i)).toString("yyyy-MM-dd"));
            if (i+1 < stringList.size()){
                temp += ",\n";
            } else {
                temp += "\n";
            }
        }
        temp += "]\n";
        
        returnString = temp;
        
        if (dBug) System.out.println("returnString:");
        if (dBug) System.out.println(returnString);
        
        return returnString;
    }

    private List<String> getListOfDatesThatHaveBillablesForProvider(BillableRepository billableRepository, String idOfProvider){
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getListOfDatesThatHaveBillablesForProvider(...)\n");

        List<String> returnStringList = new ArrayList<String>();

        DateTime earliestDateTime = getDateTimeOfFirstBillableInRepositoryForProviderId(billableRepository, idOfProvider);
        DateTime latestDateTime = getDateTimeOfLastBillableInRepositoryForProviderId(billableRepository, idOfProvider);
        
        DateTime todaysDateTime = new DateTime();
        DateTime endDateTime;
        DateTime startDateTime;

        if (latestDateTime.isAfter(previousFridayFromDate(todaysDateTime))){
            if (dBug) System.out.println("\nUnreported Records Exist for this week!\n");
            endDateTime = followingFridayFromDate(todaysDateTime);
            startDateTime = previousFridayFromDate(todaysDateTime);
            returnStringList.add(startDateTime.toString());
        } else {
            if (dBug) System.out.println("\nNo records for this week.\n");
            startDateTime = previousFridayFromDate(latestDateTime);
            endDateTime = followingFridayFromDate(latestDateTime);
        }
        
        if (dBug) System.out.println("todaysDateTime.toString() = " + todaysDateTime.toString());
        if (dBug) System.out.println("\nearliestDateTime.toString() = " + earliestDateTime.toString());
        if (dBug) System.out.println("\nlatestDateTime.toString() = " + latestDateTime.toString());
        if (dBug) System.out.println("\nstartDateTime.toString() = " + startDateTime.toString());
        if (dBug) System.out.println("endDateTime.toString() = " + endDateTime.toString() + "\n");

        while (startDateTime.isAfter(earliestDateTime)){
            startDateTime = startDateTime.minusWeeks(1);
            endDateTime = endDateTime.minusWeeks(1);
            
            int numBillablesInWeek = billableRepository.findByProviderNumberServicingAndDateServicedRecordedBetween(idOfProvider, startDateTime, endDateTime).size();
            
            if ( numBillablesInWeek > 0){
                if (dBug) System.out.printf("(%s - %s) * FOUND %s RECORD(S).\n", startDateTime.toString(), endDateTime.toString(), numBillablesInWeek);
                returnStringList.add(startDateTime.toString());
            } else {
                if (dBug) System.out.printf("(%s - %s) * NONE.\n", startDateTime.toString(), endDateTime.toString());
            }
        }
        
        if (dBug){
            System.out.printf("\n%s DATE RANGE(S) WITH RECORDS FOUND.\n\n", returnStringList.size());
            for (int i = 0; i < returnStringList.size(); i++){
                System.out.printf("%s (list item %d)\n", returnStringList.get(i), i);
            }
            System.out.printf("-end-\n");
        }
        return returnStringList;
    }
    
    private DateTime followingFridayFromDate(DateTime dateTimeIn) {
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.followingFridayFromDate(...)\n");
        if (dBug) System.out.println("dateTimeIn.toString(\"MM-dd-yyyy\")" + dateTimeIn.toString("MM-dd-yyyy"));
        
        DateTime returnDateTime = dateTimeIn.plusWeeks(1).withDayOfWeek(DateTimeConstants.FRIDAY).withTime(21, 0, 1, 0);
        if (dBug) System.out.println("returnDateTime.toString(\"MM-dd-yyyy\")" + returnDateTime.toString("MM-dd-yyyy"));
        return returnDateTime;
    }
    
    private DateTime previousFridayFromDate(DateTime dateTimeIn) {
        
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.previousFridayFromDate(...)\n");
        if (dBug) System.out.println("\ndateTimeIn.toString() = " + dateTimeIn.toString());

        if (dateTimeIn.getDayOfWeek() == DateTimeConstants.FRIDAY) {
            dateTimeIn = dateTimeIn.minusWeeks(1);
        }

        DateTime returnDateTime = dateTimeIn.withDayOfWeek(DateTimeConstants.FRIDAY).withTime(21, 0, 0, 0);
        
        if (dBug) System.out.println("returnDateTime.toString() = " + returnDateTime.toString() + "\n");
        return returnDateTime;
    }
    
    private DateTime getDateTimeOfFirstBillableInRepositoryForProviderId(BillableRepository billableRepository, String idOfProvider) {
        DateTime returnDateTime;
        
        Billable firstBillableForProvider = billableRepository.findByProviderNumberServicingOrderByDateServicedRecordedAsc(idOfProvider);
        
        returnDateTime = firstBillableForProvider.getDateTimeServicedRecorded();
        
        return returnDateTime;
    }
    
    private DateTime getDateTimeOfLastBillableInRepositoryForProviderId(BillableRepository billableRepository, String idOfProvider) {
        DateTime returnDateTime;
        
        Billable firstBillableForProvider = billableRepository.findByProviderNumberServicingOrderByDateServicedRecordedDesc(idOfProvider);
        
        returnDateTime = firstBillableForProvider.getDateTimeServicedRecorded();
        
        return returnDateTime;
    }

    private DateTime getDateTimeOfFirstBillableInRepository(BillableRepository billableRepository) {
        DateTime returnDateTime;
        
        returnDateTime = billableRepository.findFirstByOrderByDateServicedRecordedAsc().getDateTimeServicedRecorded();
        
        return returnDateTime;
    }
    
    private DateTime getDateTimeOfLastBillableInRepository(BillableRepository billableRepository) {
        DateTime returnDateTime;
        
        returnDateTime = billableRepository.findLastByOrderByDateServicedRecordedDesc().getDateTimeServicedRecorded();
        
        return returnDateTime;
    }


    private DateTime getDateTimeOfFirstBillableInRepositoryForUserId(BillableRepository billableRepository, String idOfUser) {
        DateTime returnDateTime;
        
        Billable firstBillableForUser = billableRepository.findByMemberNumberServiceOrderByDateServicedRecordedAsc(idOfUser);
        
        returnDateTime = firstBillableForUser.getDateTimeServicedRecorded();
        
        return returnDateTime;
    }
    
    private DateTime getDateTimeOfLastBillableInRepositoryForUserId(BillableRepository billableRepository, String idOfUser) {
        DateTime returnDateTime;
        
        Billable firstBillableForUser = billableRepository.findByMemberNumberServiceOrderByDateServicedRecordedDesc(idOfUser);
        
        returnDateTime = firstBillableForUser.getDateTimeServicedRecorded();
        
        return returnDateTime;
    }

    public String getBillablesReportForProviderByDateRangeInJson(BillableRepository billableRepository, ProviderRepository providerRepository, ServiceRepository serviceRepository, UserRepository userRepository, String idOfProvider, DateTime startDateTime, DateTime endDateTime){
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getBillablesReportForProviderInJson(...)\n");

        String returnString = "";
        
        // Create a list of billables for the provider being queried.
        List<Billable> providerBillables = billableRepository.findByProviderNumberServicingAndDateServicedRecordedBetween(idOfProvider, startDateTime, endDateTime);
        if (dBug) System.out.println("providerBillables.size() = " +providerBillables.size());
        if (dBug) System.out.println("startDateTime = " + startDateTime);
        if (dBug) System.out.println("endDateTime   = " + endDateTime);
        
        resetTabulators();
        int numConsults = providerBillables.size();
        double feesToPayProvider = 0.0;
        
        // Start of returned a JSON string.
        returnString += "[\n";
        
        
        // Iterate through the list of billables for the provider.
        for (int i = 0; i < providerBillables.size(); i++){
            
            
            // Required spec #1 for provider report.
            String serviceProvidedDate = providerBillables.get(i).getDateServiced();
            
            // Required spec #2 for provider report.
            String serviceProvidedDateRecorded = providerBillables.get(i).getDateServicedRecorded();
            
            // Required spec #3 and #4 for provider report.
            String userServicedIdNum = providerBillables.get(i).getMemberNumberService();
            User userProvidedService = userRepository.findOneByEntityUserIdNumber(userServicedIdNum);
            String userProvidedServiceServiceNameString = userProvidedService.getMemberName();
            
            // Required spec #5 for provider report.
            String serviceProvidedId = providerBillables.get(i).getServiceNumberServiced();

            // Required spec #6 for provider report.
            String serviceProvidedFeeToPay = String.format("$%.2f", providerBillables.get(i).getServiceCost());
            // Add billable to fee tabulation.
            feesToPayProvider += providerBillables.get(i).getServiceCost();
            
            String not_yet_string = "Coming SOON!";
            // Turn specs into JSON.
            String temp = "{\n";
            temp += "\"Service Provided Date\" : \"" + serviceProvidedDate + "\",\n";
            temp += "\"Service Provided Recorded Date\" : \"" + serviceProvidedDateRecorded + "\",\n";
            temp += "\"Member Serviced Name\" : \"" + userProvidedServiceServiceNameString + "\",\n";
            temp += "\"Member Serviced ID Number\" : \"" + userServicedIdNum + "\",\n";
            temp += "\"Provided Service Code\" : \"" + serviceProvidedId + "\",\n";
            temp += "\"Provided Service Fee to Pay\" : \"" + serviceProvidedFeeToPay + "\"\n";

            // Add JSON element end depending on if is or is not last element.
            if (i < (providerBillables.size()-1)){
                temp += "\n},\n";
            } else {
                temp += "\n}\n";
            }
            returnString += temp;
        }
        // End  of returned a JSON string.
        returnString += "]";
        
        if (dBug) System.out.printf("\nQuerried id = %s\n", idOfProvider);
        if (dBug) System.out.printf("\n# Billables for id = %s\n", providerBillables.size());
        if (dBug) System.out.printf("\nreturnString :\n %s\n", returnString);
        
        setTabulators(numConsults, feesToPayProvider);
        //getHtmlConsultsAndFeeTotalForLastReportInHtml();
        return returnString;
    }

    public String getAllBillablesReportForProviderInJson(BillableRepository billableRepository, ProviderRepository providerRepository, ServiceRepository serviceRepository, UserRepository userRepository, String idOfProvider){
        
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getBillablesReportForProviderInJson(...)\n");

        String returnString = "";
        
        
        // Create a list of billables for the provider being queried.
        List<Billable> providerBillables = billableRepository.findByProviderNumberServicing(idOfProvider);
        
        resetTabulators();
        int numConsults = providerBillables.size();
        double feesToPayProvider = 0.0;
        
        // Start of returned a JSON string.
        returnString += "[\n";
        
        
        // Iterate through the list of billables for the provider.
        for (int i = 0; i < providerBillables.size(); i++){
            
            
            // Required spec #1 for provider report.
            String serviceProvidedDate = providerBillables.get(i).getDateServiced();
            
            // Required spec #2 for provider report.
            String serviceProvidedDateRecorded = providerBillables.get(i).getDateServicedRecorded();
            
            // Required spec #3 and #4 for provider report.
            String userServicedIdNum = providerBillables.get(i).getMemberNumberService();
            User userProvidedService = userRepository.findOneByEntityUserIdNumber(userServicedIdNum);
            String userProvidedServiceServiceNameString = userProvidedService.getMemberName();
            
            // Required spec #5 for provider report.
            String serviceProvidedId = providerBillables.get(i).getServiceNumberServiced();

            // Required spec #6 for provider report.
            String serviceProvidedFeeToPay = String.format("$%.2f", providerBillables.get(i).getServiceCost());
            // Add billable to fee tabulation.
            feesToPayProvider += providerBillables.get(i).getServiceCost();
            
            String not_yet_string = "Coming SOON!";
            // Turn specs into JSON.
            String temp = "{\n";
            temp += "\"Service Provided Date\" : \"" + serviceProvidedDate + "\",\n";
            temp += "\"Service Provided Recorded Date\" : \"" + serviceProvidedDateRecorded + "\",\n";
            temp += "\"Member Serviced Name\" : \"" + userProvidedServiceServiceNameString + "\",\n";
            temp += "\"Member Serviced ID Number\" : \"" + userServicedIdNum + "\",\n";
            temp += "\"Provided Service Code\" : \"" + serviceProvidedId + "\",\n";
            temp += "\"Provided Service Fee to Pay\" : \"" + serviceProvidedFeeToPay + "\"\n";

            // Add JSON element end depending on if is or is not last element.
            if (i < (providerBillables.size()-1)){
                temp += "\n},\n";
            } else {
                temp += "\n}\n";
            }
            returnString += temp;
        }
        
        // End  of returned a JSON string.
        returnString += "]";
        
        if (dBug) System.out.printf("\nQuerried id = %s\n", idOfProvider);
        if (dBug) System.out.printf("\n# Billables for id = %s\n", providerBillables.size());
        if (dBug) System.out.printf("\nreturnString :\n %s\n", returnString);
        
        
        setTabulators(numConsults, feesToPayProvider);
        getHtmlConsultsAndFeeTotalForLastReportInHtml();
        return returnString;
    }
    
    public String getManagerReportInJSON(BillableRepository billableRepository, ProviderRepository providerRepository, DateTime beginDate, DateTime endDate){
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getManagerReportInJSON(...)\n");
       
        double summaryCost = 0;
        
        String returnString = "";
        
        // Create a list of billables for the provider being queried.
        List<Billable> providerBillables = billableRepository.findByDateServicedRecordedBetween(beginDate,endDate);

        Map<String,Integer> providerBillablesQuantity = new HashMap<String, Integer>();
        Map<String,Double> providerBillablesTotal = new HashMap<String, Double>();
        
        for (int j = 0; j < providerBillables.size(); j++){
            String curProvider = providerBillables.get(j).getProviderNumberServicing();
            if(providerBillablesQuantity.putIfAbsent(curProvider, 1 ) != null){
                providerBillablesQuantity.put(curProvider, providerBillablesQuantity.get(curProvider) + 1);
            }
            if(providerBillablesTotal.putIfAbsent(curProvider,providerBillables.get(j).getServiceCost())!=null){
                providerBillablesTotal.put(curProvider,  providerBillablesTotal.get(curProvider) + providerBillables.get(j).getServiceCost());
            }
        }
        
        // Start of returned a JSON string.
        returnString += "[\n";
        
        int i = 0;
        // Iterate through the list of billables for the provider.
        for (Map.Entry<String,Integer> entry: providerBillablesQuantity.entrySet()){
            
            //need provider number, provider name, number of consultations, total fee
            // Required spec #1 for manager report - Provider Number.
            String providerNumber = entry.getKey();
            
            // Required spec #2 for manager report - Provider Name.
            String providerName = providerRepository.findOneByEntityProviderIdNumber(providerNumber).getProviderName();
            
            // Required spec #3 for manager report - Number of Consultations.
            int providerQty = entry.getValue();
            
            // Required spec #4 for manager report - Total Fee.
            double providerTotal = providerBillablesTotal.get(providerNumber);
            String providerTotalS = String.format("$%.2f", providerTotal);
            
            // For totals
            summaryCost += providerTotal;
            
            String not_yet_string = "Coming SOON!";
            // Turn specs into JSON.
            String temp = "{\n";
            temp += "\"Provider ID\" : \"" + providerNumber + "\",\n";
            temp += "\"Provider Name\" : \"" + providerName + "\",\n";
            temp += "\"Number of Consultations\" : \"" + providerQty + "\",\n";
            temp += "\"Total Fee\" : \"" + providerTotalS + "\"\n";

            // Add JSON element end depending on if is or is not last element.
            if ( i < providerBillablesQuantity.entrySet().size()-1){
                temp += "\n},\n";
            } else {
                temp += "\n}\n";
            }
            returnString += temp;
            i++;
        }
        
        // End  of returned a JSON string.
        returnString += "]";
        
        if (dBug) System.out.printf("\nQueried dates from %s to %s\n", beginDate, endDate);
        if (dBug) System.out.printf("\nreturnString :\n %s\n", returnString);
        
        getHtmlManagerSummary(providerBillablesQuantity.entrySet().size(), providerBillables.size(), summaryCost);

        return returnString;
    }

    public String getHtmlConsultsAndFeeTotalForLastReportInHtml(){
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getHtmlConsultsAndFeeTotalForLastReportInHtml()\n");
        
        String returnString = "";
        
        if (dBug) System.out.printf("Total Consults : %d\n", numConsultsForLastReport);
        if (dBug) System.out.printf("Total Fees to Pay : $%.2f\n", totalFeesForLastReport);
        
        returnString += String.format("Total Consults : %d<br>", numConsultsForLastReport);
        returnString += String.format("Total Fees to Pay : $%.2f<br>", totalFeesForLastReport);
        
        return returnString;
    }
    
    public String getHtmlManagerSummary(int count, int consultations, double total){
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.getHtmlTotalSummary()\n");
        
        String returnString = "";
        
        if (dBug) System.out.printf("Providers : %d\n", count);
        if (dBug) System.out.printf("Consultations : %d\n", consultations);
        if (dBug) System.out.printf("Total Fees : $%.2f\n", total);
        
        returnString += String.format("Providers : %d<br>", count);
        returnString += String.format("Consultations : %d<br>", consultations);
        returnString += String.format("Total Fees : $%.2f<br>", total);
        
        return returnString;
    }

    public void resetTabulators(){
        numConsultsForLastReport = 0;
        totalFeesForLastReport = 0.00;
    }

    public void setTabulators(int numConsults, double totalFees){
        numConsultsForLastReport = numConsults;
        totalFeesForLastReport = totalFees;
    }

    private static String convertObjectToJSON(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, obj);
            return sw.toString();
        }
        catch(IOException e) {
            System.err.println(e);
        }
        return null;
    }
}

/*
Sauce List:

https://www.google.com/search?sourceid=chrome-psyapi2&ion=1&espv=2&ie=UTF-8&q=joda%20datetime%20object%20change%20time&oq=joda%20datetime%20object%20change%20time&aqs=chrome..69i57j0l2.5135j0j4
http://stackoverflow.com/questions/17172782/comparing-two-joda-time-datetime-objects
http://stackoverflow.com/questions/1636038/time-how-to-get-the-next-friday
http://stackoverflow.com/questions/12661203/what-is-the-best-way-to-retrieve-the-dates-for-last-monday-and-friday
http://stackoverflow.com/questions/29380681/how-to-set-time-property-in-java-using-joda-time
*/