package controls;

// Needed for : convertObjectToJSON() Method.
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.StringWriter;

// Needed for : getJSONListOfIdsFromRepo() Method.
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.MongoOperations;


//import entities.*;

public class ChocoMongoController {
    
    // Basic Constructor
    public ChocoMongoController() {
    }

    public String getJSONListOfIdsFromRepo(MongoRepository repoToGetIdsFrom){
        
        // For Testing and Debug.
        boolean dBug = false;
        if (dBug) System.out.println("\nDEBUG ON IN ChocoMongoController.getJSONListOfIdsFromRepo\n");
        
        String returnString = "[";
        
        List<String> repoObjects = repoToGetIdsFrom.findAll();
        
        for (int i = 0; i < repoObjects.size(); i++) {
            if (i > 0){
                returnString += ",\n    ";
            } else {
                returnString += "\n    ";
            }
            
            String temp = convertObjectToJSON(repoObjects.get(i));
            temp = temp.substring((temp.indexOf(": \"")+2),(temp.indexOf(",")));
            returnString += temp;
		}
		returnString += "\n]\n";
        
        if (dBug) System.out.println("\n" + returnString + "\n");
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


/*
    public void consoleDisplayCollections(){
    }
    
    public void add_new_record(Object objectToAdd){
    }
    public boolean removeObjectFromRepoByID(MongoRepository repoToRemoveObjectFrom, String idToRemoveFromRepo){
        boolean returnBoolean = false;
        
        System.out.println("REMOVING ID : " + idToRemoveFromRepo);
        
        //repoToRemoveObjectFrom.deleteProviderByProviderNumber(idToRemoveFromRepo);
        //Query removeQuery = new Query();
        //removeQuery.addCriteria(Criteria.where("providerNumber").is(idToRemoveFromRepo));
        
        //List<Provider> test = repoToRemoveObjectFrom.findByProviderNumber(idToRemoveFromRepo);
        
        //repoToRemoveObjectFrom.delete()
        
        return returnBoolean;
        
    }

    public boolean removeFromCollectionDocumentByID_OLD(String entityTypeToRemove, String entityToRemoveStringID) {
        
        String collectionToRemoveObjectFrom = "entities" + entityTypeToRemove + "Collection";
        String idString = "entity" + entityTypeToRemove + "IdNumber";
        
        boolean returnBoolean = false;

        if (mongoDatabaseServerIsAvailable){
        
            try{
            
                DB db = mongoClient.getDB(mongoDatabaseToWorkWithString);
                DBCollection collection = db.getCollection(collectionToRemoveObjectFrom);
                
                BasicDBObject removeQuery = new BasicDBObject();
                removeQuery.append(idString, entityToRemoveStringID);
                collection.remove(removeQuery);
                returnBoolean = true;
            }catch(Exception e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }
        }
        return returnBoolean;
    }

    public String getListOfCollectionsIDs_OLD(String entityTypeToGet) {
        
        String collectionToRemoveObjectFrom = "entities" + entityTypeToGet + "Collection";
        
        String idString = "entity" + entityTypeToGet + "IdNumber";

        String returnString = "NOT YET!";

        if (mongoDatabaseServerIsAvailable){
        
            try{
            
                DB db = mongoClient.getDB(mongoDatabaseToWorkWithString);
                DBCollection collection = db.getCollection(collectionToRemoveObjectFrom);
                DBCursor cursor = collection.find();
                
                int count = 1;
    
                //System.out.println("\nDocuments in " + collectionToMakeListInJSON + "\n");
                
                returnString = "[ ";
               
                while (cursor.hasNext()) { 

                    BasicDBObject object = (BasicDBObject) cursor.next();
                    
                    //System.out.println ("Object = " + object.getString(idString));

                    if (count > 1){
                        returnString += " , ";
                    }
                    
                    String temp = "";
                    
                    temp = "\"" + object.getString(idString) + "\"";
                    returnString += temp;
                    count++;
                }
                returnString += " ]";

                System.out.println("\nreturnString = \n" + returnString + "\n");
            }catch(Exception e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }
        }
        
        return returnString;
    }

    public String getCollectionListJSON_OLD(String entityTypeToGet){

        String collectionToMakeListInJSON = "entities" + entityTypeToGet + "Collection";
        
        String returnString = "NO MONGO!";

        if (mongoDatabaseServerIsAvailable){
        
            try{
            
                DB db = mongoClient.getDB(mongoDatabaseToWorkWithString);
                DBCollection collection = db.getCollection(collectionToMakeListInJSON);
                DBCursor cursor = collection.find();
                
                int count = 1;
    
                //System.out.println("\nDocuments in " + collectionToMakeListInJSON + "\n");
                
                returnString = "[ \n   {";
                
                while (cursor.hasNext()) { 
    
                    if (count > 1){
                        returnString += "},\n   {";
                    }
                    String temp = "" + cursor.next();
                    temp = temp.substring((temp.indexOf("} , \"") + 4), (temp.lastIndexOf("\"valid\"") - 3));
                    returnString += temp;
                    count++;
                }
                returnString += "}\n]\n";
                //System.out.println("\nreturnString = \n" + returnString + "\n");
            }catch(Exception e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }
        }

        return returnString;
    }
*/

}
