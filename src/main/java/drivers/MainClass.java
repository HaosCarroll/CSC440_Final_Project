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

    	BillableModel billableMod = new BillableModel();
    	ProviderModel providerMod = new ProviderModel();
    	ServiceModel serviceMod = new ServiceModel();
    	UserModel userMod = new UserModel();

        get("/", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("title", "Welcome to Team Five's Final Project!");
           viewObjects.put("templateName", "home.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/createBillable", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createBillableForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/createBillable", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Billable u = mapper.readValue(request.body(), Billable.class);
                if (!u.isValid(u)) {
                    response.status(400);
                    return "Correct the fields";
                }
                    if(billableMod.checkBillable(u.getChocoServiceProvidedIdNumber())) {
                    int id = billableMod.createBillable(u.getChocoServiceProvidedIdNumber(), u.getMemberNumberService(), u.getProviderNumberServicing(), u.getDateServiced(), u.getDateServicedRecorded(), u.getServiceComment());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    return "Billable Already Exists";
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
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/getJsonBillableList", (request, response) -> {
            response.status(200);
            return toJSON(billableMod.sendElements());
        });

        get("/removeBillable", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeBillableForm.ftl"); 
           viewObjects.put("billables", toJSON(billableMod.sendBillablesId()));
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        put("/removeBillable/:id", (request, response) -> {
            String id = request.params(":id");
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            if(billableMod.removeBillable(id)) return "Billable Removed";
            else return "No Such Billable Found";
        });
        
        get("/updateBillable", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateBillableForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateBillable", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                Billable u = mapper.readValue(request.body(), Billable.class);
                if (!u.isValid(u)) {
                    response.status(400);
                    return "Correct the fields";
                }
                if(!billableMod.checkBillable(u.getChocoServiceProvidedIdNumber())) {
                    int id = billableMod.updateBillable(u.getChocoServiceProvidedIdNumber(), u.getMemberNumberService(), u.getProviderNumberServicing(), u.getDateServiced(), u.getDateServicedRecorded(), u.getServiceComment());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(404);
                    return "Billable Does Not Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
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
                    if(providerMod.checkProvider(u.getProviderNumber())) {
                    int id = providerMod.createProvider(u.getProviderNumber(), u.getProviderName(), u.getProviderStreetAddress(), u.getProviderCity(), u.getProviderState(), u.getProviderZip(), u.getIsDietitian(), u.getIsExerciseExpert(), u.getIsInternist());
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

        get("/getJsonProviderList", (request, response) -> {
            response.status(200);
            return toJSON(providerMod.sendElements());
        });

        get("/removeProvider", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeProviderForm.ftl"); 
           viewObjects.put("providers", toJSON(providerMod.sendProvidersId()));
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        put("/removeProvider/:id", (request, response) -> {
            String id = request.params(":id");
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            if(providerMod.removeProvider(id)) return "Provider Removed";
            else return "No Such Provider Found";
        });
        
        get("/updateProvider", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateProviderForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateProvider", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                Provider u = mapper.readValue(request.body(), Provider.class);
                if (!u.isValid(u)) {
                    response.status(400);
                    return "Correct the fields";
                }
                if(!providerMod.checkProvider(u.getProviderNumber())) {
                    int id = providerMod.updateProvider(u.getProviderNumber(), u.getProviderName(), u.getProviderStreetAddress(), u.getProviderCity(), u.getProviderState(), u.getProviderZip(), u.getIsDietitian(), u.getIsExerciseExpert(), u.getIsInternist());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(404);
                    return "Provider Does Not Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/createService", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createServiceForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/createService", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Service u = mapper.readValue(request.body(), Service.class);
                if (!u.isValid(u)) {
                    response.status(400);
                    return "Correct the fields";
                }
                    if(serviceMod.checkService(u.getProvidableServiceIdNum())) {
                    int id = serviceMod.createService(u.getProvidableServiceIdNum(), u.getProvidableServiceDescription(), u.getIsProvidableByDietitian(), u.getIsProvidableByExerciseExpert(), u.getIsProvidableByInternist());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    return "Service Already Exists";
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
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/getJsonServiceList", (request, response) -> {
            response.status(200);
            return toJSON(serviceMod.sendElements());
        });

        get("/removeService", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeServiceForm.ftl"); 
           viewObjects.put("services", toJSON(serviceMod.sendServicesId()));
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        put("/removeService/:id", (request, response) -> {
            String id = request.params(":id");
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            if(serviceMod.removeService(id)) return "Service Removed";
            else return "No Such Service Found";
        });
        
        get("/updateService", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateServiceForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateService", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                Service u = mapper.readValue(request.body(), Service.class);
                if (!u.isValid(u)) {
                    response.status(400);
                    return "Correct the fields";
                }
                if(!serviceMod.checkService(u.getProvidableServiceIdNum())) {
                    int id = serviceMod.updateService(u.getProvidableServiceIdNum(), u.getProvidableServiceDescription(), u.getIsProvidableByDietitian(), u.getIsProvidableByExerciseExpert(), u.getIsProvidableByInternist());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(404);
                    return "Service Does Not Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/createUser", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "createUserForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/createUser", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                User u = mapper.readValue(request.body(), User.class);
                if (!u.isValid(u)) {
                    response.status(400);
                    return "Correct the fields";
                }
                    if(userMod.checkUser(u.getMemberNumber())) {
                    int id = userMod.createUser(u.getMemberNumber(), u.getMemberName(), u.getMemberStreetAddress(), u.getMemberCity(), u.getMemberState(), u.getMemberZip());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(400);
                    response.type("application/json");
                    return "User Already Exists";
                }
                } catch (JsonParseException jpe) {
                    response.status(404);
                    return "Exception";
                }
        });
        
        get("/getAllUsers", (request, response) -> {
            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showUser.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/getJsonUserList", (request, response) -> {
            response.status(200);
            return toJSON(userMod.sendElements());
        });

        get("/removeUser", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "removeUserForm.ftl"); 
           viewObjects.put("users", toJSON(userMod.sendUsersId()));
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        put("/removeUser/:id", (request, response) -> {
            String id = request.params(":id");
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            if(userMod.removeUser(id)) return "User Removed";
            else return "No Such User Found";
        });
        
        get("/updateUser", (request, response) -> {
           Map<String, Object> viewObjects = new HashMap<String, Object>();
           viewObjects.put("templateName", "updateUserForm.ftl");
           return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        
        post("/updateUser", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            
            try {
                User u = mapper.readValue(request.body(), User.class);
                if (!u.isValid(u)) {
                    response.status(400);
                    return "Correct the fields";
                }
                if(!userMod.checkUser(u.getMemberNumber())) {
                    int id = userMod.updateUser(u.getMemberNumber(), u.getMemberName(), u.getMemberStreetAddress(), u.getMemberCity(), u.getMemberState(), u.getMemberZip());
                    response.status(200);
                    response.type("application/json");
                    return id;
                }
                else {
                    response.status(404);
                    return "User Does Not Exists";
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

