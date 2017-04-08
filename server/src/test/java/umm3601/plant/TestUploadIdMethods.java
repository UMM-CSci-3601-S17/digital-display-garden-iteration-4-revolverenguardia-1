package umm3601.plant;

import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.ExcelParser;
import umm3601.digitalDisplayGarden.PlantController;

import java.io.IOException;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;


public class TestUploadIdMethods {

    private final static String databaseName = "data-for-testing-only";
    private PlantController plantController;

    @Before
    public void populateDB() throws IOException {
        PopulateMockDatabase db = new PopulateMockDatabase();
        db.clearAndPopulateDBAgain();
        plantController = new PlantController(databaseName);
    }

    @Test
    public void listingOfUploadIds() throws IOException {

        String uploadIds = "[ \"first uploadId\" , \"second uploadId\"]";

        String actualUploadId = ExcelParser.listUploadIds(databaseName);

        assertEquals("the list upload ids should match all ids in Database(mock)", uploadIds,actualUploadId);
    }


//    @Test
//    public void TestGetLiveUploadId(){
//        String plantJson = "{ \"_id\" : { \"$oid\" : \"58d1c36efb0cac4e15afd202\" }, \"uploadId\" : \"first uploadId\",\"commonName\" : \"Alternanthera\", \"cultivar\" : \"Experimental\", \"gardenLocation\" : \"10.0\", \"id\" : \"16001.0\" }";
//        String newUploadId = ExcelParser.generateNewUploadId();
//
//
//        String actualUploadId = ExcelParser.getLiveUploadId(databaseName);
//
//        System.out.println(actualUploadId);
//        //assertTrue("",actualUploadId);
//        assertEquals("",plantJson.contains("uploadId"),actualUploadId);
//    }


    @Test
    public void TestGenerateNewLiveUploadId(){

        String newUploadId = ExcelParser.generateNewUploadId().substring(11,19);

        Date date = new Date();
        String theDate = date.toString();


        assertEquals("the time that the upload id was made should correspond to the current time",theDate.substring(11,19),newUploadId);


    }
}
