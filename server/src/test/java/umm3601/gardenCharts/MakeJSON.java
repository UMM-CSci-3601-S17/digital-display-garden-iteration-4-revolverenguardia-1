package umm3601.gardenCharts;

/**
 * Created by Dogxx000 on 4/9/17.
 */

import static junit.framework.TestCase.assertEquals;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.junit.Test;
import umm3601.digitalDisplayGarden.GardenCharts;

import java.io.IOException;
import java.util.ArrayList;

public class MakeJSON {
    public static String databaseName = "test";

    @Test
    public void emptyArray() throws IOException {
        GardenCharts gardenCharts = new GardenCharts(databaseName);

        Object[][] emptyArray = new Object[4][2];

        emptyArray[0][0] = "Team";
        emptyArray[0][1] = "Score";
        emptyArray[1][0] = "Mercdies";
        emptyArray[2][0] = "Ferarri";
        emptyArray[3][0] = "RedBull";
        emptyArray[1][1] = 1;
        emptyArray[2][1] = 2;
        emptyArray[3][1] = 3;

        System.out.println(gardenCharts.makeJSON(emptyArray));


       JsonArray trade = stringToJSONArray(gardenCharts.makeJSON(emptyArray));

        ArrayList<Object> list = new ArrayList<Object>();

        if(trade != null){
            int len = trade.size();
            for( int i = 0; i < len; i ++){
                JsonArray tradeSub = trade.get(i).getAsJsonArray();
                ArrayList tradSubList = JSONArrayToArrayList(tradeSub);
                list.add(tradSubList);
            }
        }

        System.out.println("ArrayList of ArrayList");
        System.out.println();
        printDoubleArrayList(list);

        assertEquals("Incorrect size", "[]", gardenCharts.makeJSON(emptyArray));
    }

    public JsonArray stringToJSONArray(String response){
        JsonParser parser = new JsonParser();
        JsonElement tradeElement = parser.parse(response);
        return tradeElement.getAsJsonArray();
    }

    public ArrayList<Object> JSONArrayToArrayList(JsonArray trade){
        ArrayList<Object> list = new ArrayList<Object>();
        if(trade != null){
            int len = trade.size();
            for( int i = 0; i < len; i ++){
                list.add(trade.get(i));
            }
        }
        return list;
    }

    public void printDoubleArrayList(ArrayList<Object> in){
        for(Object o : in){
            ArrayList<Object> row = (ArrayList<Object>)o;
            for(Object obj : row) {
                System.out.print(obj.toString() + ", ");
            }
            System.out.println();
        }
    }


}
