package drivers;

import templateEngine.FreeMarkerEngine;
import java.io.IOException;
import static spark.Spark.*;
import spark.ModelAndView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import models.*;
import entities.*;

public class MainClass {
    
    public static void main(String[] args) {
        staticFileLocation("/public");
        MainClass s = new MainClass();
        
        port(8080); // Spark will run on port 8080
        
        s.init();
    }
    
    /**
     *  Function for Routes
     */
    private void init() {

    	ChocoProvidableModel chocoProvidableMod = new ChocoProvidableModel();
    	ChocoProviderModel chocoProviderMod = new ChocoProviderModel();
    	ChocoServiceProvidedModel chocoServiceProvidedMod = new ChocoServiceProvidedModel();
    	ChocoUserModel chocoUserMod = new ChocoUserModel();

        get("/", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("title", "Welcome to Spark Project");
           viewObjects.put("templateName", "home.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/createChocoProvidable", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createChocoProvidableForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/createChocoProvidable", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ChocoProvidable u = mapper.readValue(request.body(), ChocoProvidable.class);
                if (!u.isValid()) {
                    response.status(400);
                    return "Correct the fields";
                }
                    if(chocoProvidableMod.checkChocoProvidable(u.getProvidableServiceIdNum())) {
                    int id = chocoProvidableMod.createChocoProvidable(u.getProvidableServiceIdNum(), u.getProvidableServiceDescription());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    return "ChocoProvidable Already Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/getAllChocoProvidables", (request, response) -> {
            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showChocoProvidable.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/getJsonChocoProvidableList", (request, response) -> {
            response.status(200);
            return toJSON(chocoProvidableMod.sendElements());
        });

        get("/removeChocoProvidable", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeChocoProvidableForm.ftl"); 
           viewObjects.put("chocoProvidables", toJSON(chocoProvidableMod.sendChocoProvidablesId()));
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        put("/removeChocoProvidable/:id", (request, response) -> {
            String id = request.params(":id");
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            if(chocoProvidableMod.removeChocoProvidable(id)) return "ChocoProvidable Removed";
            else return "No Such ChocoProvidable Found";
        });
        
        get("/updateChocoProvidable", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateChocoProvidableForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateChocoProvidable", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                ChocoProvidable u = mapper.readValue(request.body(), ChocoProvidable.class);
                if (!u.isValid()) {
                    response.status(400);
                    return "Correct the fields";
                }
                if(!chocoProvidableMod.checkChocoProvidable(u.getProvidableServiceIdNum())) {
                    int id = chocoProvidableMod.updateChocoProvidable(u.getProvidableServiceIdNum(), u.getProvidableServiceDescription());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(404);
                    return "ChocoProvidable Does Not Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/createChocoProvider", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createChocoProviderForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/createChocoProvider", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ChocoProvider u = mapper.readValue(request.body(), ChocoProvider.class);
                if (!u.isValid()) {
                    response.status(400);
                    return "Correct the fields";
                }
                    if(chocoProviderMod.checkChocoProvider(u.getProviderNumber())) {
                    int id = chocoProviderMod.createChocoProvider(u.getProviderNumber(), u.getProviderName(), u.getProviderStreetAddress(), u.getProviderCity(), u.getProviderState(), u.getProviderZip());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    return "ChocoProvider Already Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/getAllChocoProviders", (request, response) -> {
            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showChocoProvider.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/getJsonChocoProviderList", (request, response) -> {
            response.status(200);
            return toJSON(chocoProviderMod.sendElements());
        });

        get("/removeChocoProvider", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeChocoProviderForm.ftl"); 
           viewObjects.put("chocoProviders", toJSON(chocoProviderMod.sendChocoProvidersId()));
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        put("/removeChocoProvider/:id", (request, response) -> {
            String id = request.params(":id");
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            if(chocoProviderMod.removeChocoProvider(id)) return "ChocoProvider Removed";
            else return "No Such ChocoProvider Found";
        });
        
        get("/updateChocoProvider", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateChocoProviderForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateChocoProvider", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                ChocoProvider u = mapper.readValue(request.body(), ChocoProvider.class);
                if (!u.isValid()) {
                    response.status(400);
                    return "Correct the fields";
                }
                if(!chocoProviderMod.checkChocoProvider(u.getProviderNumber())) {
                    int id = chocoProviderMod.updateChocoProvider(u.getProviderNumber(), u.getProviderName(), u.getProviderStreetAddress(), u.getProviderCity(), u.getProviderState(), u.getProviderZip());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(404);
                    return "ChocoProvider Does Not Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/createChocoServiceProvided", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createChocoServiceProvidedForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/createChocoServiceProvided", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ChocoServiceProvided u = mapper.readValue(request.body(), ChocoServiceProvided.class);
                if (!u.isValid()) {
                    response.status(400);
                    return "Correct the fields";
                }
                    if(chocoServiceProvidedMod.checkChocoServiceProvided(u.getChocoServiceProvidedIdNumber())) {
                    int id = chocoServiceProvidedMod.createChocoServiceProvided(u.getChocoServiceProvidedIdNumber(), u.getMemberNumberService(), u.getProviderNumberServicing(), u.getDateServiced(), u.getDateServicedRecorded(), u.getServiceComment());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    return "ChocoServiceProvided Already Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/getAllChocoServiceProvideds", (request, response) -> {
            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showChocoServiceProvided.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/getJsonChocoServiceProvidedList", (request, response) -> {
            response.status(200);
            return toJSON(chocoServiceProvidedMod.sendElements());
        });

        get("/removeChocoServiceProvided", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeChocoServiceProvidedForm.ftl"); 
           viewObjects.put("chocoServiceProvideds", toJSON(chocoServiceProvidedMod.sendChocoServiceProvidedsId()));
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        put("/removeChocoServiceProvided/:id", (request, response) -> {
            String id = request.params(":id");
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            if(chocoServiceProvidedMod.removeChocoServiceProvided(id)) return "ChocoServiceProvided Removed";
            else return "No Such ChocoServiceProvided Found";
        });
        
        get("/updateChocoServiceProvided", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateChocoServiceProvidedForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateChocoServiceProvided", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                ChocoServiceProvided u = mapper.readValue(request.body(), ChocoServiceProvided.class);
                if (!u.isValid()) {
                    response.status(400);
                    return "Correct the fields";
                }
                if(!chocoServiceProvidedMod.checkChocoServiceProvided(u.getChocoServiceProvidedIdNumber())) {
                    int id = chocoServiceProvidedMod.updateChocoServiceProvided(u.getChocoServiceProvidedIdNumber(), u.getMemberNumberService(), u.getProviderNumberServicing(), u.getDateServiced(), u.getDateServicedRecorded(), u.getServiceComment());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(404);
                    return "ChocoServiceProvided Does Not Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/createChocoUser", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createChocoUserForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/createChocoUser", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ChocoUser u = mapper.readValue(request.body(), ChocoUser.class);
                if (!u.isValid()) {
                    response.status(400);
                    return "Correct the fields";
                }
                    if(chocoUserMod.checkChocoUser(u.getMemberNumber())) {
                    int id = chocoUserMod.createChocoUser(u.getMemberNumber(), u.getMemberName(), u.getMemberStreetAddress(), u.getMemberCity(), u.getMemberState(), u.getMemberZip());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    return "ChocoUser Already Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/getAllChocoUsers", (request, response) -> {
            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showChocoUser.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/getJsonChocoUserList", (request, response) -> {
            response.status(200);
            return toJSON(chocoUserMod.sendElements());
        });

        get("/removeChocoUser", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeChocoUserForm.ftl"); 
           viewObjects.put("chocoUsers", toJSON(chocoUserMod.sendChocoUsersId()));
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        put("/removeChocoUser/:id", (request, response) -> {
            String id = request.params(":id");
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            if(chocoUserMod.removeChocoUser(id)) return "ChocoUser Removed";
            else return "No Such ChocoUser Found";
        });
        
        get("/updateChocoUser", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateChocoUserForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateChocoUser", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                ChocoUser u = mapper.readValue(request.body(), ChocoUser.class);
                if (!u.isValid()) {
                    response.status(400);
                    return "Correct the fields";
                }
                if(!chocoUserMod.checkChocoUser(u.getMemberNumber())) {
                    int id = chocoUserMod.updateChocoUser(u.getMemberNumber(), u.getMemberName(), u.getMemberStreetAddress(), u.getMemberCity(), u.getMemberState(), u.getMemberZip());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(404);
                    return "ChocoUser Does Not Exists";
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
    private static String toJSON(Object obj) {
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

