package umm3601.digitalDisplayGarden;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import com.mongodb.util.JSON;
import org.bson.BsonInvalidOperationException;
import org.bson.Document;
import org.bson.types.ObjectId;

import org.bson.conversions.Bson;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Projections.fields;

import java.io.IOException;
import java.util.*;

import static com.mongodb.client.model.Updates.push;
/**
 * Created by Dogxx000 on 4/8/17.
 */
public class GardenCharts
{
    private final MongoCollection<Document> plantCollection;
    private final MongoCollection<Document> commentCollection;
    private final MongoCollection<Document> configCollection;

    public GardenCharts(String databaseName) throws IOException {

        MongoClient mongoClient = new MongoClient();
        // Try connecting to a database
        MongoDatabase db = mongoClient.getDatabase(databaseName);

        plantCollection = db.getCollection("plants");
        commentCollection = db.getCollection("comments");
        configCollection = db.getCollection("config");
    }

    public String getViewsPerHour(String uploadID) {

        try {
            Object[][] dataTable = new Object[24 + 1][2];

            Document filter = new Document();
            filter.put("uploadId", uploadID);

            dataTable[0][0] = "Hour";
            dataTable[0][1] = "Views";


            // DB STUFF
            ArrayList<Date> dates = new ArrayList<Date>();

            //Get a plant by plantID
            FindIterable doc = plantCollection.find();

            Iterator iterator = doc.iterator();
            while(iterator.hasNext()) {
                Document result = (Document) iterator.next();

                //Get metadata.rating array
                List<Document> ratings = (List<Document>) ((Document) result.get("metadata")).get("visits");

                //Loop through all of the entries within the array, counting like=true(like) and like=false(dislike)

                for(Document i : ratings){
                    dates.add(((ObjectId) i.get("visit")).getDate());
                }

            }
            System.out.println(dates.toString());

            HashMap<Integer, Integer> hours = new HashMap<Integer, Integer>();
            for (Date date : dates){
                System.out.print("Date: " + date.toString() + " | ");

                int hour = date.getHours();
                System.out.print("Hour: " + hour + " | ");

                if(hours.get(hour) == null) hours.put(hour, 0);
                int visits = hours.get(hour);
                hours.put(hour, visits += 1);
                System.out.println(hours.get(hour));
            }

//            System.out.println("hours: " + hours.toString());

            for(int i = 0; i < 25; i++){
                if(hours.get(i) == null){
                    hours.put(i, 0);
                }
            }

//            System.out.println("hours: " + hours.toString());
            // DB STUFF

            for (int i = 1; i < 24 + 1; i++) {
                dataTable[i][0] = Integer.toString(i - 1);
                dataTable[i][1] = hours.get(i - 1).intValue(); //TODO: put REAL data here
            }

//            System.out.println(makeJSON(dataTable));
            return makeJSON(dataTable);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public String getBedMetadataForMap(PlantController plantController, String uploadID) {
        // Count Flower likes
        // Count flower dislikes
        // Count flower comments

        try {
            int likes = 0;
            int dislikes = 0;
            int comments = 0;

            String[] bedNames = plantController.getGardenLocations(uploadID);
            JsonArray out = new JsonArray();

            for (int i = 0; i < bedNames.length; i++) {
                JsonObject bed = new JsonObject();
                Document filter = new Document();
                filter.append("uploadId", uploadID);
                filter.append("gardenLocation", bedNames[i]);

                FindIterable<Document> fitr = plantCollection.find(filter);
                for (Document plant : fitr) {
                    long[] feedback = plantController.getFeedbackForPlantByPlantID(plant.getString("id"), uploadID);

                    likes += feedback[PlantController.PLANT_FEEDBACK_LIKES];
                    dislikes += feedback[PlantController.PLANT_FEEDBACK_DISLIKES];
                    comments += feedback[PlantController.PLANT_FEEDBACK_COMMENTS];
                }
                bed.addProperty("gardenLocation", bedNames[i]);
                bed.addProperty("likes", likes);
                bed.addProperty("dislikes", dislikes);
                bed.addProperty("comments", comments);
                out.add(bed);
            }

            return out.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public String getBedMetadataForBubbleMap(PlantController plantController, BedController bedController, String uploadID) {
        // Count Flower likes
        // Count flower dislikes
        // Count flower comments

        try {
            int likes = 0;
            int pageViews;

            String[] bedNames = plantController.getGardenLocations(uploadID);
            JsonArray out = new JsonArray();

            for (int i = 0; i < bedNames.length; i++) {
                JsonObject bed = new JsonObject();
                Document filter = new Document();
                filter.append("uploadId", uploadID);
                filter.append("gardenLocation", bedNames[i]);

                FindIterable<Document> fitr = plantCollection.find(filter);
                for (Document plant : fitr) {
                    long[] feedback = plantController.getFeedbackForPlantByPlantID(plant.getString("id"), uploadID);
                    likes += feedback[PlantController.PLANT_FEEDBACK_LIKES];
                }

                pageViews = bedController.getPageViews(bedNames[i], uploadID);

                bed.addProperty("gardenLocation", bedNames[i]);
                bed.addProperty("likes", likes);
                bed.addProperty("pageViews", pageViews);
                out.add(bed);
            }

            System.out.println(out);
            return out.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }



        public String makeJSON(Object[][] in) { //TODO write a test for thsi
        JsonArray outerArray = new JsonArray();
        for(int i = 0; i < in.length; i++)
        {
            JsonArray innerArray = new JsonArray();
            for(int j = 0; j < in[i].length; j++) {
                Object a = in[i][j];
                Class objectClass = a.getClass();
                if(objectClass == String.class)
                {
                    innerArray.add(in[i][j].toString());
                }
                else if(Number.class.isAssignableFrom(objectClass))
                {
                    innerArray.add((Number)in[i][j]);
                }
                else if(objectClass == JsonElement.class)
                {
                    innerArray.add((JsonElement)in[i][j]);
                }
                else
                {
                    throw new RuntimeException("WHAT ARE YOU");
                }
            }

            outerArray.add(innerArray);
        }
        return outerArray.toString();
    }



}
