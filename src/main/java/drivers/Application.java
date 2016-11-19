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
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

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
		SpringApplication.run(Application.class, args);

		System.out.println("\n\n\n\nSPRING SERVER RUNNING!\n");
		System.out.println("\nSPARK SERVER RUNNING!\n\n\n\n");
	}

	@Override
	public void run(String... args) throws Exception {
        // This allow non static method to be called from static main while
        // allowing same method to access 'autowired' repositories.
        startSparkServer();
	}


    private void startSparkServer() {

        staticFileLocation("/public");
        port(8080); // Spark Server will run on port 8080

        // Functions for Spark Server Routes
         
        // Landing/Home Page Route.
        get("/", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("title", "Welcome to Team Five's Final Project!!!");
           viewObjects.put("templateName", "home.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        
        // Provider Entity CRUD Routes.

        get("/createProvider", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createProviderForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        post("/createProvider", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Provider u = mapper.readValue(request.body(), Provider.class);
                if (!u.isValid(u)) {
                    response.status(400);
                    return "Correct the fields";
                }
                    
                if(true) {
                //if(providerMod.checkProvider(u.getProviderNumber())) {
                    //int id = providerMod.createProvider(u.getProviderNumber(), u.getProviderName(), u.getProviderStreetAddress(), u.getProviderCity(), u.getProviderState(), u.getProviderZip(), u.getIsDietitian(), u.getIsExerciseExpert(), u.getIsInternist());
                    
                    if (providerRepository.exists(u.getProviderNumber())){
                        System.out.println("Create Provider finds exists.");
                    } else {
                        System.out.println("Create Provider finds not.");
                    }
                    int id = 1;
                    
                    System.out.println("request.body() = " + request.body());
                    System.out.println("u = " + toJSON(u));
                    
					providerRepository.save(u);
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    return "Provider Already Exists";
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
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/getJsonProviderIdsList", (request, response) -> {
            response.status(200);
            return mongoController.getJSONListOfIdsFromRepo(providerRepository);
        });


        get("/removeProvider", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeProviderForm.ftl");
           viewObjects.put("providers", mongoController.getJSONListOfIdsFromRepo(providerRepository));
           return new ModelAndView(viewObjects, "main.ftl");
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
/*
*/
        
    }

	
	    /**
     *  This function converts an Object to JSON String
     * @param obj
     */
    private String toJSON(Object obj) {
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
