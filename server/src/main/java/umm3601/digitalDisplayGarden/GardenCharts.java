package umm3601.digitalDisplayGarden;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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

            Random rand = new Random();
            for (int i = 1; i < 24 + 1; i++) {
                dataTable[i][0] = Integer.toString(i - 1);
                dataTable[i][1] = Math.abs(rand.nextInt()) % 300; //TODO: put REAL data here
            }

            System.out.println(makeJSON(dataTable));
            return makeJSON(dataTable);
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
