package drivers;

// TODO : REMOVE UN-NEEDED IMPORTS! 
// TODO : Finish Commenting the imports.

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
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

//import servers.*;

import java.io.IOException;

import entities.*;
import controls.*;

@SpringBootApplication
public class Application implements CommandLineRunner{

	@Autowired
	private ProviderRepository providerRepository;

    private ChocoMongoController mongoController = new ChocoMongoController();

	public static void main(String[] args) {

        // For Testing and Debug.
        boolean dBug = true;
        if (dBug) System.out.println("\nDEBUG ON IN : Application.main\n");

		SpringApplication.run(Application.class, args);

		if (dBug) System.out.println("\n\n\n\nSPRING SERVER RUNNING!\n");
		if (dBug) System.out.println("\nSPARK SERVER RUNNING!\n\n\n\n");
	}

	@Override
	public void run(String... args) throws Exception {
        // This allow non static method to be called from static main while
        // allowing same method to access 'autowired' repositories.

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\nDEBUG ON IN : Application.run\n");

        startSparkServer();
	}

    private void startSparkServer() {

        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\nDEBUG ON IN : Application.startSparkServer\n");

        // Set vars for Spark Server.
        staticFileLocation("/public");
        port(8080); // Spark Server will run on port 8080


        // Functions for Spark Server Routes
         
        // Landing/Home Page Route.
        get("/", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("title", "Welcome to Team Five's Final Project!!!");
           viewObjects.put("templateName", "aHome.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        
        // Provider Entity CRUD Routes.

        get("/createProvider", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createProviderForm.ftl");
           return new ModelAndView(viewObjects, "aMain.ftl");
        }, new FreeMarkerEngine());

        post("/createProvider", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Provider u = mapper.readValue(request.body(), Provider.class);
                
                if (!u.isValid(u)) {
                    response.status(400);
                    return "Correct the fields";
                }
                
                if(providerRepository.countByProviderNumber(u.getProviderNumber()) == 0) {
                    
                    int id = 1;
                    
                    if (dBug) System.out.println("request.body() = " + request.body());
                    if (dBug) System.out.println("u = " + convertObjectToJSON(u));
                    
					providerRepository.save(u);
                    response.status(200);
                    response.type("application/json");
                    return id;
                } else {
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
            
            long numRemoved = providerRepository.deleteProviderByProviderNumber(id);
            if (numRemoved == 1){
                response.status(200);
                return "One Provider Removed.";
            } else if (numRemoved > 1){
                response.status(200);
                String returnString = "" + numRemoved + " PROVIDERS REMOVED!!";
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
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateProvider", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                System.out.println("TRYING!!");
                Provider u = mapper.readValue(request.body(), Provider.class);

                if (!u.isValid(u)) {
                    response.status(400);
                    return "Correct the fields";
                }

                if(providerRepository.countByProviderNumber(u.getProviderNumber()) == 1) {
                    System.out.println("u.getProviderNumber() = " + u.getProviderNumber());
                    providerRepository.deleteProviderByProviderNumber(u.getProviderNumber());
                    providerRepository.save(u);
                    response.status(200);
                    response.type("application/json");
                    return 1;
                } else {
                    response.status(404);
                    return "Provider Does Not Exists or More than one exists.";
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
    }

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
}
