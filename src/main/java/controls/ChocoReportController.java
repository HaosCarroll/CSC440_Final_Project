package controls;

// Needed for : convertObjectToJSON() Method.
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.StringWriter;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import entities.*;

public class ChocoReportController {
    
    // Basic Constructor
    public ChocoReportController() {
    }

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
