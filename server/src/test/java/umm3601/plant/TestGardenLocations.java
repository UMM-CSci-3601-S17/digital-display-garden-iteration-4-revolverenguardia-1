package umm3601.plant;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.PlantController;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestGardenLocations {

    private final static String databaseName = "data-for-testing-only";
    private PlantController plantController;

    @Before
    public void populateDB() throws IOException {
        PopulateMockDatabase db = new PopulateMockDatabase();
        db.clearAndPopulateDBAgain();
        plantController = new PlantController(databaseName);
    }

    @Test
    public void GetGardenLocationAsJsonRemovesDuplicates() throws IOException {
        GardenLocation[] gardenLocations;
        Gson gson = new Gson();

        JsonArray json = plantController.getGardenLocationsAsJson("first uploadId");
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

    }

}
