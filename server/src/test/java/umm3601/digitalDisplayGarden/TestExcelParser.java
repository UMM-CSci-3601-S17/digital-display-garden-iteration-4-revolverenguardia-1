package umm3601.digitalDisplayGarden;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static com.mongodb.client.model.Filters.*;

/**
 * Created by benek020 on 3/6/17.
 */
public class TestExcelParser {

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
        parser = new ExcelParser(fromFile, databaseName);
    }



    @Test
    public void testSpeadsheetToDoubleArray(){
        String[][] plantArray = parser.extractFromXLSX(fromFile);
        //printDoubleArray(plantArray);

        assertEquals(1000, plantArray.length);
        assertEquals(plantArray[40].length, plantArray[963].length);
        assertEquals("2016 Accession List: Steve's Design", plantArray[0][1]);
        assertEquals("Begonia", plantArray[6][1]);

    }

    @Test
    public void testCollapse(){
        String[][] plantArray = parser.extractFromXLSX(fromFile);
        //System.out.println(plantArray.length);
        //printDoubleArray(plantArray);

        plantArray = parser.collapseHorizontally(plantArray);
        plantArray = parser.collapseVertically(plantArray);

        //printDoubleArray(plantArray);

        assertEquals(362, plantArray.length);
        assertEquals(8, plantArray[30].length);
        assertEquals(8, plantArray[0].length);
        assertEquals(8, plantArray[3].length);
    }

    @Test
    public void testReplaceNulls(){
        String[][] plantArray = parser.extractFromXLSX(fromFile);
        plantArray = parser.collapseHorizontally(plantArray);
        plantArray = parser.collapseVertically(plantArray);
        parser.replaceNulls(plantArray);

        for (String[] row : plantArray){
            for (String cell : row){
                assertNotNull(cell);
            }
        }
    }

    @Test
    public void testPopulateDatabase(){
        String[][] plantArray = parser.extractFromXLSX(fromFile);
        plantArray = parser.collapseHorizontally(plantArray);
        plantArray = parser.collapseVertically(plantArray);
        parser.replaceNulls(plantArray);



        String oldUploadId = ExcelParser.getLiveUploadId(databaseName);
        parser.populateDatabase(plantArray, "an arbitrary ID");
        MongoCollection plants = testDB.getCollection("plants");


        try {
            assertEquals(286, plants.count());
            assertEquals(11, plants.count(eq("commonName", "Geranium")));
        }
        finally {
            ExcelParser.clearUpload("an arbitrary ID", databaseName);
            ExcelParser.setLiveUploadId(oldUploadId, databaseName);
        }

    }

    @Test
    public void testPatchDatabase(){

        //Clear the db-blah-test database BEFORE the test
        //So that you view only the most recent test results
        ExcelParser.clearUpload("an arbitrary ID", databaseName);
        ExcelParser.clearUpload("a totally arbitrary ID", databaseName);
        ExcelParser.clearUpload("an even more arbitrary ID", databaseName);

        String[][] plantArray = parser.extractFromXLSX(fromFile);
        plantArray = parser.collapseHorizontally(plantArray);
        plantArray = parser.collapseVertically(plantArray);
        parser.replaceNulls(plantArray);

        String oldUploadId = ExcelParser.getLiveUploadId(databaseName);
        parser.populateDatabase(plantArray, "an arbitrary ID");
        MongoCollection plants = testDB.getCollection("plants");


        try {
            assertEquals(286, plants.count(eq("uploadId", "an arbitrary ID")));
            assertEquals(13, plants.count(and(eq("commonName", "Begonia"),eq("uploadId", "an arbitrary ID"))));

            plantArray = parser.extractFromXLSX(fromADDFile);
            plantArray = parser.collapseHorizontally(plantArray);
            plantArray = parser.collapseVertically(plantArray);
            parser.replaceNulls(plantArray);
            parser.patchDatabase(plantArray, "an arbitrary ID", "a totally arbitrary ID");


            //In the ADD spreadsheet a flower by TARANTULA BLUE is uploaded
            assertEquals(286+1, plants.count(eq("uploadId", "a totally arbitrary ID")));
            assertEquals("Does not have 13 Begonias",13, plants.count(and(eq("commonName", "Begonia"),eq("uploadId", "a totally arbitrary ID"))));
            assertEquals("No TARANTULA found in ADD spreadsheet", 1, plants.count(eq("commonName", "TARANTULA")));

            plantArray = parser.extractFromXLSX(fromDELETEFile);
            plantArray = parser.collapseHorizontally(plantArray);
            plantArray = parser.collapseVertically(plantArray);
            parser.replaceNulls(plantArray);
            parser.patchDatabase(plantArray, "a totally arbitrary ID", "an even more arbitrary ID");


            //In the DELETED spreadsheet 2 Begonias are removed
            //3 flowers in total are removed
            assertEquals(286+1-3-1, plants.count(eq("uploadId", "an even more arbitrary ID")));
            assertEquals("Does not have 13-2 Begonias",13-2, plants.count(and(eq("commonName", "Begonia"),eq("uploadId", "an even more arbitrary ID"))));
            assertEquals("A TARANTULA was found in ADD spreadsheet (how scary)", 0, plants.count(and(eq("commonName", "TARANTULA"), eq("uploadId", "an even more arbitrary ID"))));


        }
        finally {
            ExcelParser.setLiveUploadId(oldUploadId, databaseName);
        }

    }



    private static void printDoubleArray(String[][] input){
        for(int i = 0; i < input.length; i++){
            if (!(input[i] == (null))) {
                for (int j = 0; j < input[i].length; j++) {
                    //System.out.print(" | " + "i: " + i + " j: " + j + " value: " + input[i][j] );
                    System.out.print(" | " + input[i][j]);
                }
                System.out.println();
                System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            }
        }
    }
}
