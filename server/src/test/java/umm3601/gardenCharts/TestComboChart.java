package umm3601.gardenCharts;


import com.google.gson.JsonArray;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.GardenCharts;
import umm3601.digitalDisplayGarden.PlantController;
import umm3601.plant.PopulateMockDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class TestComboChart {
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
    public void testComboChart() throws IOException {
        GardenCharts gardenCharts = new GardenCharts(testDB);
        testUtils TestUTILS = new testUtils();

        String string = gardenCharts.getComboChart("googleCharts uploadId");

        ArrayList<Object> arrayList = TestUTILS.JSONtoArrayList(string);

        TestUTILS.printDoubleArrayList(arrayList);

        System.out.println(arrayList);

        Object midnightSunday = ((ArrayList<Object>)arrayList.get(1)).get(3);
        Object midnightFriday = ((ArrayList<Object>)arrayList.get(1)).get(6);


    }
}
