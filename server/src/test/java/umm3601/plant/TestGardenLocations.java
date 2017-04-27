package umm3601.plant;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.PlantController;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

public class TestGardenLocations {

    private final static String databaseName = "data-for-testing-only";
    public MongoClient mongoClient = new MongoClient();
    public MongoDatabase testDB = mongoClient.getDatabase(databaseName);
    private PlantController plantController;

    @Before
    public void populateDB() throws IOException {
        PopulateMockDatabase.clearAndPopulateDBAgain(testDB);
        plantController = new PlantController(testDB);
    }

    @Test
    public void GetGardenLocationAsJsonRemovesDuplicates() throws IOException {
        GardenLocation[] gardenLocations;
        Gson gson = new Gson();

        JsonArray json = plantController.getGardenLocationsJSON("first uploadId");
        gardenLocations = gson.fromJson(json, GardenLocation[].class);
        assertEquals("Incorrect number of unique garden locations in \"first uploadId\"", 1, gardenLocations.length);
        assertEquals("Incorrect zero index", "10.0", gardenLocations[0]._id);
    }

    @Test
    public void TestGetGardenLocations(){
//        GardenLocation[] gardenLocations;
//        Gson gson = new Gson();

        String[] gardenLocations = plantController.getGardenLocations("first uploadId");
        assertEquals("Incorrect number of unique garden locations", 1, gardenLocations.length);

        //System.out.println(gardenLocations[0]);
        assertEquals("Incorrect zero index", "10.0", gardenLocations[0]);

        gardenLocations = plantController.getGardenLocations("invalid uploadId");
        assertNull("Incorrect uploadId for garden location did not return no beds", gardenLocations);

        JsonArray gardenLocationsJSON = plantController.getGardenLocationsJSON("invalid uploadId");
        assertEquals("Incorrect uploadId for garden location did not return no beds", gardenLocationsJSON, new JsonArray());


    }

}
