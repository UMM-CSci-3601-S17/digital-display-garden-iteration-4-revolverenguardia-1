package umm3601.gardenCharts;

import com.google.gson.JsonArray;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.BedController;
import umm3601.digitalDisplayGarden.GardenCharts;
import umm3601.digitalDisplayGarden.PlantController;
import umm3601.plant.PopulateMockDatabase;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by lauxx265 on 4/27/17.
 */
public class TestGetBedMetadataForBubbleMap {
    private final static String databaseName = "data-for-testing-only";
    public MongoClient mongoClient = new MongoClient();
    public MongoDatabase testDB = mongoClient.getDatabase(databaseName);
    private PlantController plantController;
    private BedController bedController;

    @Before
    public void populateDB() throws IOException {
        PopulateMockDatabase.clearAndPopulateDBAgain(testDB);
        plantController = new PlantController(testDB);
        bedController = new BedController(testDB);
    }

    @Test
    public void TestTop20() throws IOException{
        GardenCharts gardenCharts = new GardenCharts(testDB);
        testUtils TestUTILS = new testUtils();

        String string = gardenCharts.getBedMetadataForBubbleMap(this.plantController, this.bedController, "googleCharts uploadId");
        String string3 = gardenCharts.getBedMetadataForBubbleMap(this.plantController, this.bedController, "third uploadId");

        JsonArray json = TestUTILS.stringToJSONArray(string);
        JsonArray json3 = TestUTILS.stringToJSONArray(string3);

        System.out.println(json.toString());
        System.out.println(json3.toString());

        ArrayList<Object> array = TestUTILS.JSONArrayToArrayList(json);
        ArrayList<Object> array3 = TestUTILS.JSONArrayToArrayList(json3);
        //System.out.println(array);
        String arraySub0 = array.get(0).toString();
        String arraySub1 = array.get(1).toString();
        String arraySub3 = array3.get(0).toString();
        String arraySub01 = "{\"gardenLocation\":\"2S\",\"likes\":2,\"pageViews\":10}";
        String arraySub02 = "{\"gardenLocation\":\"5.0\",\"likes\":1,\"pageViews\":10}";
        String arraySub03 = "{\"gardenLocation\":\"20\",\"likes\":0,\"pageViews\":10}";

        assertEquals("Should have the same likes, pageviews and bed 1", arraySub01, arraySub0);
        assertEquals("Should have the same likes, pageviews and bed 2", arraySub02, arraySub1);
        assertEquals("Should have the same likes, pageviews and bed 3", arraySub03, arraySub3);
    }
}
