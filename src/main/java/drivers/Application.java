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


@SpringBootApplication
public class Application implements CommandLineRunner{

	@Autowired
	private ProviderRepository providerRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		System.out.println("\n\n\n\nSPRING SERVER RUNNING!\n\n\n\n");
		System.out.println("\n\n\n\nSPARK SERVER RUNNING!\n\n\n\n");
		
	}


	@Override
	public void run(String... args) throws Exception {
        startSparkServer();
	}


    private void startSparkServer() {

        staticFileLocation("/public");
        port(8080); // Spark Server will run on port 8080

        /**
         *  Function for Routes
         */
        get("/", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("title", "Welcome to Team Five's Final Project!");
           viewObjects.put("templateName", "home.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

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
                    int id = 1;
                    
                    System.out.println("request.body() = " + request.body());
                    //String requestBodyString = (String)request.body();
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
