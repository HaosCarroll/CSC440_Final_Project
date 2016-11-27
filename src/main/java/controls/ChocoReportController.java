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
    
    // Basic Constructor
    public ChocoReportController() {
    }

    public String gettBillablesForUserInJson(BillableRepository billableRepository, String idOfUser){

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

    public String gettBillablesReportForUserInJson(BillableRepository billableRepository, ProviderRepository providerRepository, ServiceRepository serviceRepository, String idOfUser){

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : ChocoReportController.gettBillablesForUser()\n");

        String returnString = "";
        
        List<Billable> usersBillables = billableRepository.findByMemberNumberService(idOfUser);

        returnString += "[\n";
        for (int i = 0; i < usersBillables.size(); i++){
            
            String serviceProvidedDate = usersBillables.get(i).getDateServiced();
            
            String serviceProvidedProviderId = usersBillables.get(i).getProviderNumberServicing();
            Provider providerProvidingService = providerRepository.findOneByEntityProviderIdNumber(serviceProvidedProviderId);
            String providerProvidingServiceNameString = providerProvidingService.getProviderName();
            
            String serviceProvidedId = usersBillables.get(i).getServiceNumberServiced();
            Service providedService =serviceRepository.findOneByEntityServiceIdNumber(serviceProvidedId);
            String serviceProvidedName = providedService.getProvidableServiceDescription();

            String temp = "{\n";
            temp += "\"Servicing Provided Date\" : \"" + serviceProvidedDate + "\",\n";
            temp += "\"Provider Servicing\" : \"" + providerProvidingServiceNameString + "\",\n";
            temp += "\"Provided Service Name\" : \"" + serviceProvidedName + "\"\n";

            if (i < (usersBillables.size()-1)){
                temp += "\n},\n";
            } else {
                temp += "\n}\n";
            }
            returnString += temp;
        }
        returnString += "]";
        
        if (dBug) System.out.printf ("\nQuerried id = %s\n", idOfUser);
        if (dBug) System.out.printf ("\n# Billables for id = %s\n", usersBillables.size());

        return returnString;
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
    public String getBillablesForEachProvider(MongoRepository billableRepository, MongoRepository providerRepository){
        String returnString = "";
        
        // For Testing and Debug.
        boolean dBug = true;
        if (dBug) System.out.println("\nDEBUG ON IN : ChocoReportController.getBillablesForEachProvider()\n");
        
        
        List<Provider> allProviders = providerRepository.findAll();
        List<Billable> allBillables = billableRepository.findAll();
        
        System.out.printf ("\n# Providers = %s\n", allProviders.size());

        System.out.printf ("\n# Billables = %s\n", allBillables.size());
        
        return returnString;
    }
*/