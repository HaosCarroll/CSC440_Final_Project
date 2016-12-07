package drivers;

// Imports for spring framework.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import org.springframework.beans.factory.annotation.Autowired;

// Imports for spark framework.
import static spark.Spark.*;
import spark.ModelAndView;
import templateEngine.FreeMarkerEngine;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import java.io.StringWriter;
import java.io.IOException;

import org.joda.time.*;

import entities.*;
import controls.*;

@SpringBootApplication
public class Application implements CommandLineRunner{

	@Autowired
	private BillableRepository billableRepository;
	@Autowired
	private ProviderRepository providerRepository;
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private UserRepository userRepository;

    private ChocoMongoController mongoController = new ChocoMongoController();
    private ChocoReportController reportController = new ChocoReportController();
    private ChocoTestDataController testDataController = new ChocoTestDataController();

	public static void main(String[] args) {

        // For Testing and Debug.
        boolean dBug = true;
        if (dBug) System.out.println("\n* * dBug true IN : Application.main()\n");

		SpringApplication.run(Application.class, args);

		if (dBug) display_spark_startup_text();
	}
    
	@Override
	public void run(String... args) throws Exception {
        // This allow non static method to be called from static main while
        // allowing same method to access 'autowired' repositories.

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\n* * dBug true IN : Application.run()\n");

        runSparkServer();
	}

    private void runSparkServer() {


        // Set vars for Spark Server.
        staticFileLocation("/public");
        port(8080); // Spark Server will run on port 8080

        // Functions for Spark Server Routes

        // TODO : Determine the best way to abstract these routes.
        // TODO : Implement the best way to abstract these routes!

/* _   _   _ ___    _   _     ___ _
 *|_) / \ / \ |    |_) / \ | | | |_ |
 *| \ \_/ \_/ |    | \ \_/ |_| | |_ o
 */                       

        get("/", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/\") route.\n");

           Map<String, Object> viewObjects = new HashMap<String, Object>();
           //viewObjects.put("title", "Welcome to Team Five's Final Project!");
           viewObjects.put("templateName", "aHome.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

/*     __  _  _     _   _     ___ _  __ 
 *| | (_  |_ |_)   |_) / \ | | | |_ (_  
 *|_| __) |_ | \   | \ \_/ |_| | |_ __)
 */
                                     
        get("/createUser", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createUserForm.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());
        
        post("/createUser", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : post(\"/createUser\") route.\n");

            ObjectMapper mapper = new ObjectMapper();
            try {
                User u = mapper.readValue(request.body(), User.class);
                
                if (!u.isValidEntity()) {
                    response.status(400);
                    return "Correct the fields";
                }
                
                if(userRepository.countByEntityUserIdNumber(u.getEntityUserIdNumber()) == 0) {
                    
                    int id = 1;

                    if (dBug) System.out.println("request.body() = " + request.body());
                    if (dBug) System.out.println("u = " + convertObjectToJSON(u));
                    
                    userRepository.save(u);
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    
                    return "User ID Number Already Exists!!";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/getAllUsers", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/getAllUsers\") route.\n");

            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showUser.ftl");
            return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("/getJsonUserList", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/getJsonUserList\") route.\n");
            
            String returnString = mongoController.getJSONListOfObjectsFromRepo(userRepository);
            response.status(200);

            if (dBug) System.out.println("\nreturnString : ");
            if (dBug) System.out.println(returnString);
            if (dBug) System.out.println("\nReturning above.");

            return returnString;
        });

        get("/removeUser", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeUserForm.ftl");
           viewObjects.put("users", mongoController.getJSONListOfIdsFromRepo(userRepository));
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        put("/removeUser/:id", (request, response) -> {
            String id = request.params(":id");
            
            long numRemoved = userRepository.deleteUserByEntityUserIdNumber(id);
            
            if (numRemoved == 1){
                response.status(200);
                return "One User Removed.";
            } else if (numRemoved > 1){
                response.status(200);
                String returnString = "" + numRemoved + " Users REMOVED!!";
                return returnString;
            }
            else {
                response.status(400);
                return "No Such User Found.";
            }
        });
        
        get("/updateUser", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateUserForm.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateUser", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                User u = mapper.readValue(request.body(), User.class);
                
                if (!u.isValidEntity()) {
                    response.status(400);
                    return "Correct The Fields.";
                }
                if(userRepository.countByEntityUserIdNumber(u.getEntityUserIdNumber()) == 1) {
                    userRepository.deleteUserByEntityUserIdNumber(u.getEntityUserIdNumber());
                    userRepository.save(u);
                    response.status(200);
                    response.type("application/json");
                    return 1;
                } else {
                    response.status(404);
                    return "User Does Not Exists or More Than One Exists.";
                }
            } catch (JsonParseException jpe) {
                response.status(404);
                return "Exception";
            }
        });

        // Useful for testing and debuging.
        get("/getJsonUserIdsList", (request, response) -> {
            response.status(200);
            return mongoController.getJSONListOfIdsFromRepo(userRepository);
        });

/* _   _   _      ___  _   _  _     _   _     ___ _  __ 
 *|_) |_) / \ \  / |  | \ |_ |_)   |_) / \ | | | |_ (_  
 *|   | \ \_/  \/ _|_ |_/ |_ | \   | \ \_/ |_| | |_ __) 
 */                                                     

        get("/createProvider", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createProviderForm.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());
        
        post("/createProvider", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : post(\"/createProvider\") route.\n");

            ObjectMapper mapper = new ObjectMapper();
            try {
                Provider u = mapper.readValue(request.body(), Provider.class);
                
                if (!u.isValidEntity()) {
                    response.status(400);
                    return "Correct the fields";
                }
                
                if(providerRepository.countByEntityProviderIdNumber(u.getEntityProviderIdNumber()) == 0) {
                    
                    int id = 1;

                    if (dBug) System.out.println("request.body() = " + request.body());
                    if (dBug) System.out.println("u = " + convertObjectToJSON(u));
                    
                    providerRepository.save(u);
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    
                    return "Provider ID Number Already Exists!!";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/getAllProviders", (request, response) -> {
            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showProvider.ftl");
            return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("/getJsonProviderList", (request, response) -> {
            response.status(200);
            return mongoController.getJSONListOfObjectsFromRepo(providerRepository);
        });

        get("/removeProvider", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeProviderForm.ftl");
           viewObjects.put("providers", mongoController.getJSONListOfIdsFromRepo(providerRepository));
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        put("/removeProvider/:id", (request, response) -> {
            String id = request.params(":id");
            
            long numRemoved = providerRepository.deleteProviderByEntityProviderIdNumber(id);
            
            if (numRemoved == 1){
                response.status(200);
                return "One Provider Removed.";
            } else if (numRemoved > 1){
                response.status(200);
                String returnString = "" + numRemoved + " Providers REMOVED!!";
                return returnString;
            }
            else {
                response.status(400);
                return "No Such Provider Found.";
            }
        });
        
        get("/updateProvider", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateProviderForm.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateProvider", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                Provider u = mapper.readValue(request.body(), Provider.class);
                
                if (!u.isValidEntity()) {
                    response.status(400);
                    return "Correct The Fields.";
                }
                if(providerRepository.countByEntityProviderIdNumber(u.getEntityProviderIdNumber()) == 1) {
                    providerRepository.deleteProviderByEntityProviderIdNumber(u.getEntityProviderIdNumber());
                    providerRepository.save(u);
                    response.status(200);
                    response.type("application/json");
                    return 1;
                } else {
                    response.status(404);
                    return "Provider Does Not Exists or More Than One Exists.";
                }
            } catch (JsonParseException jpe) {
                response.status(404);
                return "Exception";
            }
        });

        // Useful for testing and debuging.
        get("/getJsonProviderIdsList", (request, response) -> {
            response.status(200);
            return mongoController.getJSONListOfIdsFromRepo(providerRepository);
        });

/* __  _  _      ___  _  _    _   _     ___ _  __
 *(_  |_ |_) \  / |  /  |_   |_) / \ | | | |_ (_ 
 *__) |_ | \  \/ _|_ \_ |_   | \ \_/ |_| | |_ __)
*/

        get("/createService", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createServiceForm.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());
        
        post("/createService", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : post(\"/createService\") route.\n");

            ObjectMapper mapper = new ObjectMapper();
            try {
                Service u = mapper.readValue(request.body(), Service.class);
                
                if (!u.isValidEntity()) {
                    response.status(400);
                    return "Correct the fields";
                }
                
                if(serviceRepository.countByEntityServiceIdNumber(u.getEntityServiceIdNumber()) == 0) {
                    
                    int id = 1;

                    if (dBug) System.out.println("request.body() = " + request.body());
                    if (dBug) System.out.println("u = " + convertObjectToJSON(u));
                    
                    serviceRepository.save(u);
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    
                    return "Service ID Number Already Exists!!";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/getAllServices", (request, response) -> {
            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showService.ftl");
            return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("/getJsonServiceList", (request, response) -> {
            response.status(200);
            return mongoController.getJSONListOfObjectsFromRepo(serviceRepository);
        });

        get("/removeService", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeServiceForm.ftl");
           viewObjects.put("services", mongoController.getJSONListOfIdsFromRepo(serviceRepository));
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        put("/removeService/:id", (request, response) -> {
            String id = request.params(":id");
            
            long numRemoved = serviceRepository.deleteServiceByEntityServiceIdNumber(id);
            
            if (numRemoved == 1){
                response.status(200);
                return "One Service Removed.";
            } else if (numRemoved > 1){
                response.status(200);
                String returnString = "" + numRemoved + " Services REMOVED!!";
                return returnString;
            }
            else {
                response.status(400);
                return "No Such Service Found.";
            }
        });
        
        get("/updateService", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateServiceForm.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateService", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                Service u = mapper.readValue(request.body(), Service.class);
                
                if (!u.isValidEntity()) {
                    response.status(400);
                    return "Correct The Fields.";
                }
                if(serviceRepository.countByEntityServiceIdNumber(u.getEntityServiceIdNumber()) == 1) {
                    serviceRepository.deleteServiceByEntityServiceIdNumber(u.getEntityServiceIdNumber());
                    serviceRepository.save(u);
                    response.status(200);
                    response.type("application/json");
                    return 1;
                } else {
                    response.status(404);
                    return "Service Does Not Exists or More Than One Exists.";
                }
            } catch (JsonParseException jpe) {
                response.status(404);
                return "Exception";
            }
        });

        // Useful for testing and debuging.
        get("/getJsonServiceIdsList", (request, response) -> {
            response.status(200);
            return mongoController.getJSONListOfIdsFromRepo(serviceRepository);
        });

/*  _  ___             _      _    _   _     ___ _  __ 
 * |_)  |  |  |   /\  |_) |  |_   |_) / \ | | | |_ (_  
 * |_) _|_ |_ |_ /--\ |_) |_ |_   | \ \_/ |_| | |_ __) 
 */

        get("/createBillable", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createBillableForm.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());
        
        post("/createBillable", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : post(\"/createBillable\") route.\n");

            ObjectMapper mapper = new ObjectMapper();
            try {
                Billable u = mapper.readValue(request.body(), Billable.class);
                u.setDateServicedRecorded();
                if (!u.isValidEntity()) {
                    response.status(400);
                    return "Correct the fields";
                }
                
                if(billableRepository.countByEntityBillableIdNumber(u.getEntityBillableIdNumber()) == 0) {
                    
                    int id = 1;

                    if (dBug) System.out.println("request.body() = " + request.body());
                    if (dBug) System.out.println("u = " + convertObjectToJSON(u));
                    
                    billableRepository.save(u);
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    
                    return "Billable ID Number Already Exists!!";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/getAllBillables", (request, response) -> {
            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showBillable.ftl");
            return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("/getJsonBillableList", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/getJsonBillableList\" route.\n");

            response.status(200);
            String returnString = mongoController.getJSONListOfObjectsFromRepo(billableRepository);
            
            if (dBug) System.out.println("returnString:");
            if (dBug) System.out.println(returnString);
            
            return returnString;
        });

        get("/removeBillable", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeBillableForm.ftl");
           viewObjects.put("billables", mongoController.getJSONListOfIdsFromRepo(billableRepository));
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        put("/removeBillable/:id", (request, response) -> {
            String id = request.params(":id");
            
            long numRemoved = billableRepository.deleteBillableByEntityBillableIdNumber(id);
            
            if (numRemoved == 1){
                response.status(200);
                return "One Billable Removed.";
            } else if (numRemoved > 1){
                response.status(200);
                String returnString = "" + numRemoved + " Billables REMOVED!!";
                return returnString;
            }
            else {
                response.status(400);
                return "No Such Billable Found.";
            }
        });
        
        get("/updateBillable", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateBillableForm.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateBillable", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                Billable u = mapper.readValue(request.body(), Billable.class);
                u.setDateServicedRecorded();
                if (!u.isValidEntity()) {
                    response.status(400);
                    return "Correct The Fields.";
                }
                if(billableRepository.countByEntityBillableIdNumber(u.getEntityBillableIdNumber()) == 1) {
                    billableRepository.deleteBillableByEntityBillableIdNumber(u.getEntityBillableIdNumber());
                    billableRepository.save(u);
                    response.status(200);
                    response.type("application/json");
                    return 1;
                } else {
                    response.status(404);
                    return "Billable Does Not Exists or More Than One Exists.";
                }
            } catch (JsonParseException jpe) {
                response.status(404);
                return "Exception";
            }
        });

        // Useful for testing and debuging.
        get("/getJsonBillableIdsList", (request, response) -> {
            response.status(200);
            return mongoController.getJSONListOfIdsFromRepo(billableRepository);
        });

/* _   _  _   _   _ ___    _   _     ___ _  __ 
 *|_) |_ |_) / \ |_) |    |_) / \ | | | |_ (_  
 *| \ |_ |   \_/ | \ |    | \ \_/ |_| | |_ __)
 */

        get("/userReport", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/userReport\") route.\n");

           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "report_pages/user_report.ftl");
           viewObjects.put("title", "User Report being constructed!");
           viewObjects.put("users", mongoController.getJSONListOfIdsFromRepo(userRepository));
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("userAddress/:id", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/userAddress/:id\") route.\n");

            String returnString;
            String id =  request.params(":id");

            returnString = reportController.getHtmlStringUserAddress(userRepository, id);

            if (dBug) System.out.println("returnString:\n" + returnString);

            return returnString;
        });

        get("userReport/:id", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/userReport/:id\") route.\n");

            //String returnString = "";
            //String id =  request.params(":id");
            
            String returnString = reportController.getJsonListOfDatesThatHaveBillablesForUser(billableRepository, request.params(":id"));
            
            if (dBug) System.out.println("returnString:\n" + returnString);

            return returnString;
        });

        get("userReport/:id/:endQueryDate", (request, response) -> {
            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/userReport/:id/:endQueryDate\") route.\n");

            String returnString = "";

            String id =  request.params(":id");
            String endDate =  request.params(":endQueryDate");

            DateTime endDateTime = new DateTime(endDate).plusWeeks(1).withTime(21, 0, 1, 0);
            DateTime startDateTime = new DateTime(endDate).withTime(21, 0, 0, 0);
            
            if (dBug) System.out.println("\nendDate :" + endDate);
            if (dBug) System.out.println("endDateTime :" + endDateTime);
            if (dBug) System.out.println("startDateTime :" + startDateTime);
            
            returnString = reportController.getBillablesReportForUserByDateRangeInJson(billableRepository, providerRepository, serviceRepository, id, startDateTime, endDateTime);

            if (dBug) System.out.println("returnString:\n" + returnString);
            return returnString;
        });

        get("/providerReport", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "report_pages/provider_report.ftl");
           viewObjects.put("title", "Provider Report!");
           viewObjects.put("providers", mongoController.getJSONListOfIdsFromRepo(providerRepository));
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("providerReport/:id", (request, response) -> {
            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/providerReport/:id\") route.\n");

            String returnString = reportController.getJsonListOfDatesThatHaveBillablesForProvider(billableRepository, request.params(":id"));
            
            if (dBug) System.out.println("returnString:\n" + returnString);
            return returnString;
        });

        get("providerReport/:id/:endQueryDate", (request, response) -> {
            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/providerReport/:id/:endQueryDate\") route.\n");

            String returnString = "";

            String id =  request.params(":id");
            String endDate =  request.params(":endQueryDate");

            DateTime endDateTime = new DateTime(endDate).plusWeeks(1).withTime(21, 0, 1, 0);
            DateTime startDateTime = new DateTime(endDate).withTime(21, 0, 0, 0);
            
            if (dBug) System.out.println("\nendDate :" + endDate);
            if (dBug) System.out.println("endDateTime :" + endDateTime);
            if (dBug) System.out.println("startDateTime :" + startDateTime);
            
            returnString = reportController.getBillablesReportForProviderByDateRangeInJson(billableRepository, providerRepository, serviceRepository, userRepository, id, startDateTime, endDateTime);

            if (dBug) System.out.println("returnString:\n" + returnString);
            return returnString;
        });

        get("providerBillablesTabulations/:id", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/providerBillablesTabulations/:id\") route.\n");

            String returnString;
            String id =  request.params(":id");

            returnString = reportController.getHtmlConsultsAndFeeTotalForLastReportInHtml();

            if (dBug) System.out.println("returnString:\n" + returnString);

            return returnString;
        });

        get("providerAddress/:id", (request, response) -> {

            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/providerAddress/:id\") route.\n");

            String returnString;
            String id =  request.params(":id");

            returnString = reportController.getHtmlStringProviderAddress(providerRepository, id);

            if (dBug) System.out.println("returnString:\n" + returnString);

            return returnString;
        });

        get("/managerReport", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "report_pages/manager_report.ftl");
           viewObjects.put("title", "Manager Report :Under Construction!");
           
           String dateChoices = reportController.getJsonListOfDatesThatHaveBillables(billableRepository);
           viewObjects.put("date", dateChoices);
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("/managerReport/:startQueryDate", (request, response) -> {
            // For Testing and Debug.
            boolean dBug = false;
            if (dBug) System.out.println("\n* * dBug true IN : Application.runSparkServer : get(\"/managerReport/:startQueryDate\") route.\n");

            String returnString = "";

            String startDateString =  request.params(":startQueryDate");

            DateTime endDateTime = new DateTime(startDateString).plusWeeks(1).withTime(21, 0, 1, 0);
            DateTime startDateTime = new DateTime(startDateString).withTime(21, 0, 0, 0);
            
            if (dBug) System.out.println("\nstartDateString :" + startDateString);
            if (dBug) System.out.println("endDateTime       :" + endDateTime);
            if (dBug) System.out.println("startDateTime     :" + startDateTime);
            
            returnString = reportController.getManagerReportInJSON(billableRepository, providerRepository, startDateTime, endDateTime);

            if (dBug) System.out.println("returnString:\n" + returnString);
            return returnString;
        });

        get("/billablesPerEachUserReport", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("message", "Billables Per Each User Report is currently under construction!");
           viewObjects.put("templateName", "beingBuilt.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("/billablesPerEachProviderReport", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("message", "Billables Per Each Provider Report is currently under construction!");
           viewObjects.put("templateName", "beingBuilt.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

/*     _     _     _   _     ___ _  __
 *|_| |_ |  |_)   |_) / \ | | | |_ (_ 
 *| | |_ |_ |     | \ \_/ |_| | |_ __)
 */

        get("/developerHelp", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("status_message", "UNDER CONSTRUCTION!");
           viewObjects.put("templateName", "help_pages/developer_help.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("/assignment", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           //viewObjects.put("status_message", "UNDER CONSTRUCTION!");
           viewObjects.put("templateName", "help_pages/assignment.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("/installHelp", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("message", "Install Help Page is currently under construction!");
           viewObjects.put("templateName", "beingBuilt.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("/applicationHelp", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("message", "Application Help Page is currently under construction!");
           viewObjects.put("templateName", "beingBuilt.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("/miscellaneousHelp", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("message", "Miscellaneous Help Page is currently under construction!");
           viewObjects.put("templateName", "beingBuilt.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

/* ___ _  __ ___ ___       __    _   _     ___ _  __ 
 *  | |_ (_   |   |  |\ | /__   |_) / \ | | | |_ (_  
 *  | |_ __)  |  _|_ | \| \_|   | \ \_/ |_| | |_ __) 
 */                                                  

        get("/addTestData", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "testing_pages/addTestData.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        put("/autoAddDataToMongoDB", (request, response) -> {
            
            String returnString = "";

            if (testDataController.addTestDataTo(billableRepository)){
                returnString += "<h4><strong style=\"color: green;\">BILLABLE TEST DATA SUCCESSFULLY ADDED.<br></strong></h4>";
            } else {
                returnString += "<h4><strong style=\"color: red;\">WARNING : BILLABLE DATA PRESENT (No Data Was Added!)<br></strong></h4>";
            }

            if (testDataController.addTestDataTo(providerRepository)){
                returnString += "<h4><strong style=\"color: green;\">PROVIDER TEST DATA SUCCESSFULLY ADDED.<br></strong></h4>";
            } else {
                returnString += "<h4><strong style=\"color: red;\">WARNING : PROVIDER DATA PRESENT (No Data Was Added!)<br></strong></h4>";
            }

            if (testDataController.addTestDataTo(serviceRepository)){
                returnString += "<h4><strong style=\"color: green;\">SERVICE TEST DATA SUCCESSFULLY ADDED.<br></strong></h4>";
            } else {
                returnString += "<h4><strong style=\"color: red;\">WARNING : SERVICE DATA PRESENT (No Data Was Added!)<br></strong></h4>";
            }
            
            if (testDataController.addTestDataTo(userRepository)){
                returnString += "<h4><strong style=\"color: green;\">USER TEST DATA SUCCESSFULLY ADDED.<br></strong></h4>";
            } else {
                returnString += "<h4><strong style=\"color: red;\">WARNING : USER DATA PRESENT (No Data Was Added!)<br></strong></h4>";
            }

            response.status(200);
            return returnString;
        });
        
        put("/removeAllDataFromMongoDB", (request, response) -> {
            
            billableRepository.deleteAll();
            userRepository.deleteAll();
            serviceRepository.deleteAll();
            providerRepository.deleteAll();
            
            response.status(200);
            
            String returnString = "<br><h1><h4><strong style=\"color: red;\">GONE IS MONGO DATABASE DATA!</strong></h1><br>";
            return returnString;
        });        

        get("/clearMongoDB", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "testing_pages/clearMongoDB.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        get("/clearMongoDBAndAddTestData", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "testing_pages/clearMongoDBAndAddTestData.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        put("/clearMongoDBAndAddTestData", (request, response) -> {
            
            String returnString = "";

            billableRepository.deleteAll();
            userRepository.deleteAll();
            serviceRepository.deleteAll();
            providerRepository.deleteAll();
            
            returnString += "<h4><strong style=\"color: red;\">BILLABLE TEST DATA SUCCESSFULLY ADDED.</strong></h4><br>";

            if (testDataController.addTestDataTo(billableRepository)){
                returnString += "<h4><strong style=\"color: green;\">BILLABLE TEST DATA SUCCESSFULLY ADDED.<br></strong></h4>";
            } else {
                returnString += "<h4><strong style=\"color: red;\">WARNING : BILLABLE DATA PRESENT (No Data Was Added!)<br></strong></h4>";
            }

            if (testDataController.addTestDataTo(providerRepository)){
                returnString += "<h4><strong style=\"color: green;\">PROVIDER TEST DATA SUCCESSFULLY ADDED.<br></strong></h4>";
            } else {
                returnString += "<h4><strong style=\"color: red;\">WARNING : PROVIDER DATA PRESENT (No Data Was Added!)<br></strong></h4>";
            }

            if (testDataController.addTestDataTo(serviceRepository)){
                returnString += "<h4><strong style=\"color: green;\">SERVICE TEST DATA SUCCESSFULLY ADDED.<br></strong></h4>";
            } else {
                returnString += "<h4><strong style=\"color: red;\">WARNING : SERVICE DATA PRESENT (No Data Was Added!)<br></strong></h4>";
            }
            
            if (testDataController.addTestDataTo(userRepository)){
                returnString += "<h4><strong style=\"color: green;\">USER TEST DATA SUCCESSFULLY ADDED.<br></strong></h4>";
            } else {
                returnString += "<h4><strong style=\"color: red;\">WARNING : USER DATA PRESENT (No Data Was Added!)<br></strong></h4>";
            }

            response.status(200);
            return returnString;
        });
    }  // END OF SPRING SERVER
    
    private String convertObjectToJSON(Object obj) {
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

    private static void display_spark_startup_text(){
    
    // Sauce : http://patorjk.com/software/taag/#p=display&f=Fire%20Font-s&t=Spark
    //       : http://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_YELLOW = "\u001B[33m";
        
        
        System.out.println("");
        System.out.println(ANSI_YELLOW + " (");
        System.out.println(" )\\ )                  )  ");
        System.out.println("(()/(         ) (   ( /(  " + ANSI_RESET);
        System.out.println(ANSI_RED + " /("+ANSI_YELLOW+"_"+ANSI_RED+")`  )  ( /( )(  )\\()) ");
        System.out.println("("+ANSI_RESET+"___"+ANSI_RED+")) /(/(  )("+ANSI_YELLOW+"_"+ANSI_RED+")(()\\(("+ANSI_YELLOW+"_"+ANSI_RED+")\\  " + ANSI_RESET);
        System.out.println("/ __|_"+ANSI_RED+"("+ANSI_RESET+"_"+ANSI_RED+")"+ANSI_RESET+"  _"+ANSI_RED+"(("+ANSI_RESET+"_"+ANSI_RED+")"+ANSI_RESET+"_"+ANSI_RED+"("+ANSI_RESET+"_"+ANSI_YELLOW+"|"+ANSI_RESET+"_"+ANSI_YELLOW+"|"+ANSI_RED+"("+ANSI_RESET+"_"+ANSI_RED+") "+ANSI_RESET);
        System.out.println("\\__ | '_ \\/ _` | '_| / / ");
        System.out.println("|___| .__/\\__,_|_| |_\\_\\ ");
        System.out.println("    |_|");
        System.out.println("");
        System.out.println(ANSI_GREEN + "ChocAn Spark Server Started!" + ANSI_RESET);
        System.out.println("");
        
    }
}
/*
 * ASCII COMMENTS : http://patorjk.com/software/taag/#p=display&f=Mini&t=
 */