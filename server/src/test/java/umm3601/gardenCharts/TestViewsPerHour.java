package umm3601.gardenCharts;

import com.google.gson.JsonArray;
import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.ExcelParser;
import umm3601.digitalDisplayGarden.GardenCharts;
import umm3601.digitalDisplayGarden.PlantController;
import umm3601.plant.PopulateMockDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by carav008 on 4/14/17.
 */

public class TestViewsPerHour{

    private final static String databaseName = "data-for-testing-only";
    private PlantController plantController;

    @Before
    public void populateDB() throws IOException {
        PopulateMockDatabase db = new PopulateMockDatabase();
        db.clearAndPopulateDBAgain();
        plantController = new PlantController(databaseName);
    }

    @Test
    public void TestGetViewsPerHour() throws IOException{
        GardenCharts gardenCharts = new GardenCharts(databaseName);
        testUtils TestUTILS = new testUtils();

            //Will use the mock database with the inputed upload id and track the hour the plant was visited

        //System.out.println(gardenCharts.getPlantViewsPerHour("googleCharts uploadId"));
        //[["Hour","Views"],["0",0],["1",0],["2",0],["3",0],["4",0],["5",0],["6",0],["7",0],["8",0],["9",0],["10",0],["11",0],["12",0],["13",0],["14",0],["15",0],["16",0],["17",0],["18",0],["19",1],["20",0],["21",0],["22",3],["23",0]]

        //System.out.println(plantController.getJSONFeedbackForPlantByPlantID("16053.0","googleCharts uploadId"));
        //System.out.println(plantController.getJSONFeedbackForPlantByPlantID("16037.0","googleCharts uploadId"));

        String json1 =plantController.getPlantFeedbackByPlantIdJSON("16053.0","googleCharts uploadId");
        String json2 = plantController.getPlantFeedbackByPlantIdJSON("16037.0","googleCharts uploadId");

        assertEquals("the plant 16053 should have like:true and like:false","{ \"likeCount\" : 1 , \"dislikeCount\" : 1 , \"commentCount\" : 0}", json1 );
        assertEquals("the plant 16037 should have like:true and like:false","{ \"likeCount\" : 2 , \"dislikeCount\" : 1 , \"commentCount\" : 0}", json2);

//        String string = gardenCharts.getPlantViewsPerHour("googleCharts uploadId");
//        JsonArray json = TestUTILS.stringToJSONArray(string);
//
//        ArrayList<Object> arrayList = TestUTILS.JSONArrayToArrayList(json);
//
//        Object object1 = arrayList.get(20).toString();
//        Object object2 = arrayList.get(23).toString();
//
//        assertEquals("at the 19th hour there should be 1 visit","[\"12\",1]",object1);
//        assertEquals("at the 22nd hour there should be 3 visits","[\"3\",3]",object2);
    }


}
