package umm3601.gardenCharts;

/**
 * Created by Dogxx000 on 4/9/17.
 */

import static junit.framework.TestCase.assertEquals;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.Test;
import umm3601.digitalDisplayGarden.GardenCharts;
import umm3601.gardenCharts.testUtils;

import java.io.IOException;
import java.util.ArrayList;

public class MakeJSON {
    public static String databaseName = "data-for-testing-only";
    public MongoClient mongoClient = new MongoClient();
    public MongoDatabase testDB = mongoClient.getDatabase(databaseName);

    @Test
    public void filledArray() throws IOException {
        GardenCharts gardenCharts = new GardenCharts(testDB);
        testUtils utils = new testUtils();

        /*
        filling array to be given to makeJSON
         */
        Object[][] filledArray = new Object[4][2];

        filledArray[0][0] = "Team";
        filledArray[0][1] = "Score";
        filledArray[1][0] = "Mercdies";
        filledArray[2][0] = "Ferarri";
        filledArray[3][0] = "RedBull";
        filledArray[1][1] = 1;
        filledArray[2][1] = 2;
        filledArray[3][1] = 3;

        /*
        filling array to be given to makeJSON
         */

        //setting array list to the result
//        System.out.println(gardenCharts.makeJSON(filledArray));
        ArrayList<Object> list = utils.JSONtoArrayList(gardenCharts.makeJSON(filledArray));
//
//        System.out.println();
//        System.out.println("ArrayList of ArrayList");
//        System.out.println();
//        utils.printDoubleArrayList(list);
//
//        System.out.println(list.get(0));

        assertEquals("Incorrect size", 4, list.size());

        ArrayList<Object> row0 = (ArrayList<Object>) list.get(0);

        assertEquals("Incorrect first row", "\"Team\"", row0.get(0).toString());
        assertEquals("Incorrect first row", "\"Score\"", row0.get(1).toString());

        ArrayList<Object> row3 = (ArrayList<Object>) list.get(3);

        assertEquals("Incorrect last row", "\"RedBull\"", row3.get(0).toString());
        assertEquals("Incorrect last row", "3", row3.get(1).toString());
    }

    @Test
    public void emptyArray() throws IOException {
        GardenCharts gardenCharts = new GardenCharts(testDB);
        testUtils utils = new testUtils();

        Object[][] emptyArray = new Object[0][0];

        System.out.println(gardenCharts.makeJSON(emptyArray));
        ArrayList<Object> list = utils.JSONtoArrayList(gardenCharts.makeJSON(emptyArray));

        System.out.println();
        System.out.println("ArrayList");
        System.out.println();
        utils.printDoubleArrayList(list);

        assertEquals("IncorrectSize", 0, list.size());

    }



}
