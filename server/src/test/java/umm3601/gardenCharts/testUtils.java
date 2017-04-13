package umm3601.gardenCharts;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by Dogxx000 on 4/10/17.
 */
public class testUtils {

    public ArrayList<Object> JSONtoArrayList(String in){
        JsonArray jsonArray = stringToJSONArray(in);
        ArrayList<Object> list = new ArrayList<Object>();
        if(jsonArray != null){
            int jsonLength = jsonArray.size();
            for(int i = 0; i < jsonLength; i++){
                JsonArray innerJsonArray = jsonArray.get(i).getAsJsonArray();
                ArrayList innerList = JSONArrayToArrayList(innerJsonArray);
                list.add(innerList);
            }
        }
        return list;
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
