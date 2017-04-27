package umm3601.gardenCharts;

import com.google.gson.JsonArray;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.GardenCharts;
import umm3601.digitalDisplayGarden.PlantController;
import umm3601.plant.PopulateMockDatabase;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by lauxx265 on 4/27/17.
 */
public class TestGetBedMetaDataForMap {
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
    public void TestTop20() throws IOException{
        GardenCharts gardenCharts = new GardenCharts(testDB);
        testUtils TestUTILS = new testUtils();

        String string = gardenCharts.getBedMetadataForMap(this.plantController, "googleCharts uploadId");
        String string3 = gardenCharts.getBedMetadataForMap(this.plantController, "third uploadId");

        JsonArray json = TestUTILS.stringToJSONArray(string);
        JsonArray json3 = TestUTILS.stringToJSONArray(string3);

        System.out.println(json.toString());
        System.out.println(json3.toString());

        ArrayList<Object> array = TestUTILS.JSONArrayToArrayList(json);
        ArrayList<Object> array3 = TestUTILS.JSONArrayToArrayList(json3);
        //System.out.println(array);
        String arraySub0 = array.get(0).toString();
        String arraySub1 = array.get(1).toString();
        String arraySub01 = "{\"gardenLocation\":\"2S\",\"likes\":2,\"dislikes\":1,\"comments\":0}";
        String arraySub02 = "{\"gardenLocation\":\"5.0\",\"likes\":1,\"dislikes\":1,\"comments\":0}";


        String arraySub5 = array3.get(0).toString();
        String arraySub05 = "{\"gardenLocation\":\"20\",\"likes\":0,\"dislikes\":0,\"comments\":0}";

        assertEquals("Should have the same lkes, dislikes and comments", arraySub01, arraySub0);
        assertEquals("Should have the same lkes, dislikes and comments", arraySub02, arraySub1);

        assertEquals("Should have the same lkes, dislikes and comments", arraySub05, arraySub5);
    }
}
