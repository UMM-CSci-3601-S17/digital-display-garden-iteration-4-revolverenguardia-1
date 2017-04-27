package umm3601.digitalDisplayGarden;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by frazi177 on 4/21/17.
 */
public class TestAccessionPatching
{
    private final static String databaseName = "data-for-testing-only";

    public MongoClient mongoClient = new MongoClient();
    public MongoDatabase testDB;
    public ExcelParser parser;
    public InputStream fromFile;
    public InputStream fromADDFile;
    public InputStream fromDELETEFile;

    @Before
    public void clearAndPopulateDatabase(){
        mongoClient.dropDatabase(databaseName);
        testDB = mongoClient.getDatabase(databaseName);
        fromFile = this.getClass().getResourceAsStream("/AccessionList2016.xlsx");
        fromADDFile = this.getClass().getResourceAsStream("/AccessionList2016_ADD_17603.xlsx");
        fromDELETEFile = this.getClass().getResourceAsStream("/AccessionList2016_DELETED_3_1600[1,9].xlsx");
        parser = new ExcelParser(fromFile, testDB);
    }

    @Test
    public void testPatchDatabase() throws FileNotFoundException {
        MongoCollection plants = testDB.getCollection("plants");

        //Clear the db-blah-test database BEFORE the test
        //So that you view only the most recent test results
        ExcelParser.clearUpload("an arbitrary ID", testDB);
        ExcelParser.clearUpload("a totally arbitrary ID", testDB);
        ExcelParser.clearUpload("an even more arbitrary ID", testDB);

        String[][] plantArray;

        plantArray = parser.parseExcel();

        plantArray = parser.collapseHorizontally(plantArray);
        plantArray = parser.collapseVertically(plantArray);
        parser.replaceNulls(plantArray);

        String oldUploadId = ExcelParser.getLiveUploadId(testDB);
        parser.populateDatabase(plantArray, "an arbitrary ID");

        try {
            assertEquals(286, plants.count(eq("uploadId", "an arbitrary ID")));
            assertEquals(13, plants.count(and(eq("commonName", "Begonia"),eq("uploadId", "an arbitrary ID"))));

            parser = new ExcelParser(fromADDFile, testDB);
            plantArray = parser.parseExcel();
            parser.patchDatabase(plantArray, "an arbitrary ID", "a totally arbitrary ID");


            //In the ADD spreadsheet a flower by TARANTULA BLUE is uploaded
            assertEquals(286+1, plants.count(eq("uploadId", "a totally arbitrary ID")));
            assertEquals("Does not have 13 Begonias",13, plants.count(and(eq("commonName", "Begonia"),eq("uploadId", "a totally arbitrary ID"))));
            assertEquals("No TARANTULA found in ADD spreadsheet", 1, plants.count(eq("commonName", "TARANTULA")));

            //Add a comment so that when the database is patched it will copy over a comment
            PlantController plantController = new PlantController(testDB);
            plantController.storePlantComment("{ plantId: \"16011.0\", comment : \"Here is our comment for this test\" }","a totally arbitrary ID");

            parser = new ExcelParser(fromDELETEFile, testDB);
            plantArray = parser.parseExcel();
            parser.patchDatabase(plantArray, "a totally arbitrary ID", "an even more arbitrary ID");


            //In the DELETED spreadsheet 2 Begonias are removed
            //3 flowers in total are removed
            assertEquals(286+1-3-1, plants.count(eq("uploadId", "an even more arbitrary ID")));
            assertEquals("Does not have 13-2 Begonias",13-2, plants.count(and(eq("commonName", "Begonia"),eq("uploadId", "an even more arbitrary ID"))));
            assertEquals("A TARANTULA was found in ADD spreadsheet (how scary)", 0, plants.count(and(eq("commonName", "TARANTULA"), eq("uploadId", "an even more arbitrary ID"))));


        }
        finally {
            ExcelParser.setLiveUploadId(oldUploadId, testDB);
        }

        //For coverage
        ExcelParser.printArray(new String[]{});
        ExcelParser.printDoubleArray(new String[][]{});

    }


}
