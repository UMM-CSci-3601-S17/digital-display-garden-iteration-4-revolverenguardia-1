package umm3601.plant;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
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
    public MongoClient mongoClient = new MongoClient();
    public MongoDatabase testDB = mongoClient.getDatabase(databaseName);

    @Before
    public void populateDB() throws IOException {
        PopulateMockDatabase db = new PopulateMockDatabase();
        db.clearAndPopulateDBAgain(testDB);
        plantController = new PlantController(testDB);
    }

    @Test
    public void listingOfUploadIds() throws IOException {

        String uploadIds = "[ \"first uploadId\" , \"googleCharts uploadId\" , \"second uploadId\" , \"third uploadId\"]";

        String actualUploadId = ExcelParser.listUploadIds(testDB);

        assertEquals("the list upload ids should match all ids in Database(mock)", uploadIds,actualUploadId);
    }


    @Test
    public void TestGetLiveUploadId(){
        String actualUploadId = ExcelParser.getLiveUploadId(testDB);

        System.out.println(actualUploadId);
        //assertTrue("",actualUploadId);
        assertEquals("","first uploadId", actualUploadId);
    }


    @Test
    public void TestGenerateNewLiveUploadId(){

        Date date = new Date();
        String newUploadId = ExcelParser.generateNewUploadId().substring(11,19);

        String theDate = date.toString();
        System.out.println(theDate);
        System.out.println(newUploadId);


        assertEquals("the time that the upload id was made should correspond to the current time",theDate.substring(11,19),newUploadId);


    }
}
