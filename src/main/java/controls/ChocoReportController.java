package controls;

// Needed for : convertObjectToJSON() Method.
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.StringWriter;

//import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import entities.*;

import drivers.*;

public class ChocoReportController {
    
    // The most Basic Constructor. (i wonder if there could be more use to this?)
    public ChocoReportController() {
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

    public String gettBillablesReportForUserInJson(BillableRepository billableRepository, ProviderRepository providerRepository, ServiceRepository serviceRepository, String idOfUser){

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
