package umm3601.digitalDisplayGarden;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Projections.fields;

import java.io.IOException;
import java.util.*;

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

    public String getPlantViewsPerHour(String uploadID) {

        try {
            Object[][] dataTable = new Object[24 + 1][2];

            Document filter = new Document();
            filter.put("uploadId", uploadID);

            dataTable[0][0] = "Hour";
            dataTable[0][1] = "Views";

            ArrayList<Date> dates = new ArrayList<Date>();

            //Get all plants
            FindIterable doc = plantCollection.find(filter);

            Iterator iterator = doc.iterator();
            while(iterator.hasNext()) {
                Document result = (Document) iterator.next();

                //Get metadata.rating array
                List<Document> ratings = (List<Document>) ((Document) result.get("metadata")).get("visits");

                //Loop through all of the entries within the array, counting like=true(like) and like=false(dislike)
                for(int i = 0; i < ratings.size(); i++){
                    Document d = ratings.get(i);
                    dates.add(((ObjectId) d.get("visit")).getDate());
                }

            }

            //Create a Map between Hours and page visit times
            HashMap<Integer, Integer> hours = new HashMap<Integer, Integer>();
            for (Date date : dates){
                int hour = date.getHours();

                if(hours.get(hour) == null) {
                    hours.put(hour, 1);
                }
                else
                {
                    int visits = hours.get(hour);
                    hours.put(hour, visits + 1);
                }


            }


            for(int i = 0; i < 25; i++){
                if(hours.get(i) == null){
                    hours.put(i, 0);
                }
            }


            for (int i = 1; i < 24 + 1; i++) {
                dataTable[i][0] = Integer.toString(i - 1);
                dataTable[i][1] = hours.get(i - 1).intValue();
            }

            return makeJSON(dataTable);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Form a JSON to pass to the client to render in the Google Maps Bed Metadata Map
     *
     * This returns an array with an entry for each gardenLocation
     * {gardenLocation : string, likes : number, dislikes : number, comments : number}
     * @param plantController
     * @param uploadID
     * @return
     */
    public String getBedMetadataForMap(PlantController plantController, String uploadID) {
        // Count Flower likes
        // Count flower dislikes
        // Count flower comments

        try {


            String[] bedNames = plantController.getGardenLocations(uploadID);
            JsonArray out = new JsonArray();

            for (int i = 0; i < bedNames.length; i++) {
                int likes = 0;
                int dislikes = 0;
                int comments = 0;
                JsonObject bed = new JsonObject();
                Document filter = new Document();
                filter.append("uploadId", uploadID);
                filter.append("gardenLocation", bedNames[i]);

                FindIterable<Document> iter = plantCollection.find(filter);
                for (Document plant : iter) {
                    long[] feedback = plantController.getPlantFeedbackByPlantId(plant.getString("id"), uploadID);

                    likes += feedback[PlantController.PLANT_FEEDBACK_LIKES];
                    dislikes += feedback[PlantController.PLANT_FEEDBACK_DISLIKES];
                    comments += feedback[PlantController.PLANT_FEEDBACK_COMMENTS];
                }
                bed.addProperty("gardenLocation", bedNames[i]);
                bed.addProperty("likes", likes);
                bed.addProperty("dislikes", dislikes);
                bed.addProperty("comments", comments);//TODO: could be refactored to include pageViews
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

    /**
     * Form a JSON to pass to the client to render in the BubbleMap google chart.
     *
     * This returns an array with an entry for each gardenLocation
     * {gardenLocation : string, likes : number, pageViews : number}
     * @param plantController in order to getGardenLocations and PlantFeedback to count likes
     * @param bedController in order to get Page views for a bed
     * @param uploadID
     * @return
     */
    public String getBedMetadataForBubbleMap(PlantController plantController, BedController bedController, String uploadID) {
        // Count Flower likes
        // Count flower dislikes
        // Count flower comments

        try {
            int pageViews;

            String[] bedNames = plantController.getGardenLocations(uploadID);
            JsonArray out = new JsonArray();

            for (int i = 0; i < bedNames.length; i++) {
                int likes = 0;
                JsonObject bed = new JsonObject();
                Document filter = new Document();
                filter.append("uploadId", uploadID);
                filter.append("gardenLocation", bedNames[i]);

                FindIterable<Document> iter = plantCollection.find(filter);
                for (Document plant : iter) {
                    long[] feedback = plantController.getPlantFeedbackByPlantId(plant.getString("id"), uploadID);
                    likes += feedback[PlantController.PLANT_FEEDBACK_LIKES];
                }

                pageViews = bedController.getPageViews(bedNames[i], uploadID);

                bed.addProperty("gardenLocation", bedNames[i]);
                bed.addProperty("likes", likes);
                bed.addProperty("pageViews", pageViews);
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


    /**
     * Creates a two dimensional Json array from an Object[][] in.
     *
     * Valid Objects to put in the Object[][] are Strings, Numbers (bool, integer, double, etc.), and JsonElements
     *
     * @param in
     * @return
     */
        public String makeJSON(Object[][] in) {

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
