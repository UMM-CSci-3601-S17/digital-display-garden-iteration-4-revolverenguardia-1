package umm3601.gardenCharts;

import com.google.gson.JsonArray;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.ExcelParser;
import umm3601.digitalDisplayGarden.GardenCharts;
import umm3601.digitalDisplayGarden.PlantController;
import umm3601.plant.PopulateMockDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by lauxx265 on 4/27/17.
 */
public class TestTop20 {
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

        String string = gardenCharts.top20Charts(this.plantController, "googleCharts uploadId", "likes");
        String string2 = gardenCharts.top20Charts(this.plantController, "googleCharts uploadId", "dislikes");
        String string3 = gardenCharts.top20Charts(this.plantController, "third uploadId", "likes");

        JsonArray json = TestUTILS.stringToJSONArray(string);
        JsonArray json2 = TestUTILS.stringToJSONArray(string2);
        JsonArray json3 = TestUTILS.stringToJSONArray(string3);

        System.out.println(json.toString());
        System.out.println(json3.toString());

        ArrayList<Object> array = TestUTILS.JSONArrayToArrayList(json);
        ArrayList<Object> array2 = TestUTILS.JSONArrayToArrayList(json2);
        ArrayList<Object> array3 = TestUTILS.JSONArrayToArrayList(json3);
        //System.out.println(array);
        String arraySub0 = array.get(0).toString();
        String arraySub1 = array.get(1).toString();
        String arraySub01 = "{\"cultivarName\":\"Sun Kiss\",\"likes\":2}";
        String arraySub02 = "{\"cultivarName\":\"Fireball\",\"likes\":1}";

        String arraySub3 = array2.get(0).toString();
        String arraySub4 = array2.get(1).toString();
        String arraySub03 = "{\"cultivarName\":\"Fireball\",\"likes\":1}";
        String arraySub04 = "{\"cultivarName\":\"Sun Kiss\",\"likes\":1}";

        String arraySub5 = array3.get(0).toString();
        String arraySub05 = "{\"cultivarName\":\"\",\"likes\":0}";

        assertEquals("the first index of the returned top 20 likes jsonarray should be Sun Kiss Cultivar ", arraySub01, arraySub0);
        assertEquals("the first index of the returned top 20 likes jsonarray should be FireBall Cultivar ", arraySub02, arraySub1);

        assertEquals("the first index of the returned top 20 jsonarray should be Sun Kiss Cultivar ", arraySub03, arraySub3);
        assertEquals("the first index of the returned top 20 jsonarray should be FireBall Cultivar ", arraySub04, arraySub4);

        assertEquals("there should be zero likes and no cultivar indicated", arraySub05, arraySub5);
    }

}
