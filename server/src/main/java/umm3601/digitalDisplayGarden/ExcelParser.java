package umm3601.digitalDisplayGarden;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import com.mongodb.util.JSON;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.*;

import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Updates.set;
import static java.lang.Math.max;

import org.bson.BsonArray;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import javax.print.Doc;
//import sun.text.normalizer.UTF16;

public class ExcelParser {

    private String databaseName;

    private InputStream stream;

//    public static void main(String[] args) {
//        parseExcel();
//    }

    public ExcelParser(InputStream stream, String databaseName){
        this.databaseName = databaseName;
        this.stream = stream;
    }

    public String[][] parseExcel() throws FileNotFoundException{

        String[][] arrayRepresentation = extractFromXLSX(stream);

        String[][] horizontallyCollapsed = collapseHorizontally(arrayRepresentation);
        String[][] verticallyCollapsed = collapseVertically(horizontallyCollapsed);
        replaceNulls(verticallyCollapsed);
        return verticallyCollapsed;
    }

    /*
    Uses Apache POI to extract information from xlsx file into a 2D array.
    Here is where we got our starter code: http://www.mkyong.com/java/apache-poi-reading-and-writing-excel-file-in-java/
    Look at example number 4, Apache POI library – Reading an Excel file.

    This file originally just printed data, that is why there are several commented out lines in the code.
    We have repurposed this method to put all data into a 2D String array and return it.
     */
    public String[][] extractFromXLSX(InputStream excelFile) {
        try {
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);

            String[][] cellValues = new String[datatypeSheet.getLastRowNum() + 1]
                    [max(max(datatypeSheet.getRow(1).getLastCellNum(), datatypeSheet.getRow(2).getLastCellNum()),
                    datatypeSheet.getRow(3).getLastCellNum())];

            for(Row currentRow : datatypeSheet) {
                //cellValues[currentRow.getRowNum()] = new String[currentRow.getLastCellNum()];

                for (Cell currentCell : currentRow) {

                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        cellValues[currentCell.getRowIndex()][currentCell.getColumnIndex()] = currentCell.getStringCellValue();
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        cellValues[currentCell.getRowIndex()][currentCell.getColumnIndex()] =
                                Integer.toString((int)Math.round(currentCell.getNumericCellValue()));
                    }

                }

            }
            return cellValues;

        } catch (FileNotFoundException e) {
            System.out.println("EVERYTHING BLEW UP STOP STOP STOP");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println("EVERYTHING BLEW UP STOP STOP STOP");
            e.printStackTrace();
            return null;
        }

    }

    // removes all columns that are completely null.
    // Identify how much to shrink the array and calls a helper function to shrink it
    public static String[][] collapseHorizontally(String[][] cellValues){
        // scanning over columns
        for(int j = cellValues[1].length - 1; j > 0; j--){
            //scanning over the three rows of our "key row"
            for(int i = 1; i <= 3; i++) {
                if(cellValues[i][j] != null){
                    return trimArrayHorizontally(cellValues, j);
                }
            }
        }
        return null;
    }

    // removes all completely null rows
    // Identify how much to shrink the array and calls a helper function to shrink it
    public static String[][] collapseVertically(String[][] cellValues){
        for(int i = cellValues.length - 1; i > 0; i--) {
            if(!(cellValues[i][0] == null)) {
                return trimArrayVertically(cellValues, i + 1);
            }
        }
        return null;
    }

    // helper function for collapseHorizontally() decreases the number of columns in the array
    public static String[][] trimArrayHorizontally(String[][] cellValues, int horizontalBound){
        String[][] trimmedArray = new String[cellValues.length][];
        for(int j = 0; j < cellValues.length; j++) {
            trimmedArray[j] = new String[horizontalBound + 1];
            for (int i = 0; i < horizontalBound + 1; i++) {
                trimmedArray[j][i] = cellValues[j][i];
            }
        }
        return trimmedArray;
    }

    // helper function for collapseHorizontally() decreases the number of rows in the array
    public static String[][] trimArrayVertically(String[][] cellValues, int verticalBound){
        String[][] trimmedArray = new String[verticalBound][];
        for(int i = 0; i < verticalBound; i++) {
            trimmedArray[i] = new String[cellValues[i].length];
            trimmedArray[i] = cellValues[i];
        }
        return trimmedArray;
    }

    // replaces all cells with null values with an empty string (avoids future null pointer exceptions)
    public static void replaceNulls(String[][] cellValues) {
        for(int i = 0; i < cellValues.length; i++) {
            for(int j = 0; j < cellValues[i].length; j++) {
                if(cellValues[i][j] == null) {
                    cellValues[i][j] = "";
                }
            }
        }
    }

    /* Helper method for populateDatabase().
    looks at rows 2 through 4 (xlsx file is 1 based) and:
    1. Concatenates all content in rows 2 through 4 for a given column
    2. Adds all keys into a 1D string array
    3. Replaces certain key words so they match with the standard committee's requirements
     */
    public static String[] getKeys(String[][] cellValues){
        String[] keys = new String[cellValues[0].length];

        for(int i = 0; i < cellValues[0].length; i++){
            keys[i] = cellValues[1][i];
            for(int j = 2; j < 4; j++){
                keys[i] = keys[i] + cellValues[j][i];
            }
        }

        for (int i = 0; i < keys.length; i++){
            if(keys[i].equals("#")) keys[i] = "id";
            if(keys[i].equals("Common Name")) keys[i] = "commonName";
            if(keys[i].equals("Cultivar")) keys[i] = "cultivar";
            if(keys[i].equals("Source")) keys[i] = "source";
            if(keys[i].equals("Garden  Location")) keys[i] = "gardenLocation";
            if(keys[i].contains(" ")) keys[i] = keys[i].replace(" ","");
            if(keys[i].contains("=")) keys[i] = keys[i].replace("=", "");
            //if(keys[i].contains((UTF16.valueOf(0x00AE)))) keys[i].replaceAll(UTF16.valueOf(0x00AE), "");

        }

        return keys;
    }

    // Moves row by row through the 2D array and adds content for every flower paired with keys into a document
    // Uses the document to one at a time, add flower information into the database.
    public void populateDatabase(String[][] cellValues, String uploadId){
        MongoClient mongoClient = new MongoClient();
        MongoDatabase test = mongoClient.getDatabase(databaseName);
        MongoCollection plantCollection = test.getCollection("plants");
        MongoCollection bedCollection = test.getCollection("beds");

        String[] keys = getKeys(cellValues);

        Document emptyMetadataDoc = new Document();
        emptyMetadataDoc.append("pageViews", 0);
        emptyMetadataDoc.append("visits", new BsonArray());
        emptyMetadataDoc.append("ratings", new BsonArray());

        Document emptyMetadataBedDoc = new Document();
        emptyMetadataBedDoc.append("pageViews", 0);
        emptyMetadataBedDoc.append("visits", new BsonArray());
        emptyMetadataBedDoc.append("qrScans", new BsonArray());

        for (int i = 4; i < cellValues.length; i++){
            Document doc = new Document();
            Document bedDoc = new Document();
            for(int j = 0; j < cellValues[i].length; j++){
                doc.append(keys[j], cellValues[i][j]);
            }

            if(doc.get("gardenLocation").equals(""))
                continue;

            // Initialize the empty metadata


            doc.append("metadata", emptyMetadataDoc);
            doc.append("uploadId", uploadId);

            plantCollection.insertOne(doc);



        }
        //Get distinct list of beds
        Document uploadIdFilter = new Document();
        uploadIdFilter.append("uploadId", uploadId);
        DistinctIterable<String>  bedIterator = plantCollection.distinct("gardenLocation", uploadIdFilter, String.class);

        //Add beds to bed collection with empty metadata
        for(String gardenLocation : bedIterator) {
            Document bedDoc = new Document();
            bedDoc.append("gardenLocation", gardenLocation);
            bedDoc.append("metadata", emptyMetadataBedDoc);
            bedDoc.append("uploadId", uploadId);
            bedCollection.insertOne(bedDoc);
        }




        setLiveUploadId(uploadId, databaseName);
    }

    public void patchDatabase(String[][] cellValues, String oldUploadId, String newUploadId){

        populateDatabase(cellValues, newUploadId);

        MongoClient mongoClient = new MongoClient();
        MongoDatabase test = mongoClient.getDatabase(databaseName);
        MongoCollection plantCollection = test.getCollection("plants");
        MongoCollection commentCollection = test.getCollection("comments");
        MongoCollection bedCollection = test.getCollection("beds");

        //Migrate (copy) all plants/comments of the previous uploadID and add them

        Document filterByOldUploadId = new Document();
        filterByOldUploadId.put("uploadId", oldUploadId);
        FindIterable<Document> plantsOldId = plantCollection.find(filterByOldUploadId);

        Document filterByNewUploadId = new Document();
        filterByNewUploadId.put("uploadId", newUploadId);

        //Looop through old plants
        for(Document oldPlant : plantsOldId)
        {
            //With a plant, grab its metadata and update the new plant with the old metadata
            Document oldMetadata = ((Document)oldPlant.get("metadata"));

            Document newPlantFilter = new Document(filterByNewUploadId);
            newPlantFilter.put("id", oldPlant.getString("id"));

            //Looks for a plant that has the same plantId with the new uploadId and migrates old metadata over
            //If a plant with no such plantId can be found, it will ignore it.
            plantCollection.findOneAndUpdate(newPlantFilter,set("metadata", oldMetadata));
        }

        FindIterable<Document> commentsOldId = commentCollection.find(filterByOldUploadId);
        for(Document comment : commentsOldId)
        {
            //Take an old comment, change its' uploadId and objectID
            //PROBLEM: updating _id means that that object's effective date is _now_
            //So when you patch old comments, the date they will be said to be made is the time of patch
            comment.put("uploadId", newUploadId);
//            comment.put("_id", new ObjectId());

            //Only re-upload a comment if the plant it points to still exists
            Document newPlantFilter = new Document(filterByNewUploadId);
            newPlantFilter.put("id", comment.get("commentOnPlant"));
            if(plantCollection.find(newPlantFilter).first() != null) {

                commentCollection.insertOne(comment);
            }
        }

        FindIterable<Document> bedsOldId = bedCollection.find(filterByOldUploadId);
        for(Document bed : bedsOldId)
        {
            Document oldMetadata = ((Document)bed.get("metadata"));
            bed.put("uploadId", newUploadId);
            bed.put("_id", new ObjectId());

            Document newBedFilter = new Document(filterByNewUploadId);
            bedCollection.findOneAndUpdate(newBedFilter,set("metadata", oldMetadata));
        }

        setLiveUploadId(newUploadId, databaseName);
    }


    /*
    ------------------------------- UTILITIES -----------------------------------
     */

    // prints a 1D array
    public static void printArray(String[] input){
        System.out.print("[");
        for(String str : input){
            System.out.print(str + ", ");
        }
        System.out.println("]");
    }

    /*
    Prints a 2D array.
    NOTE: If you uncomment the commented out line, and comment the one beneath it,
    you will also see the indexes for every element as well as their content.
     */
    public static void printDoubleArray(String[][] input){
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

    /*
    --------------------------- SERVER UTILITIES -------------------------------
     */

    public static void setLiveUploadId(String uploadID, String db){

        MongoClient mongoClient = new MongoClient();
        MongoDatabase test = mongoClient.getDatabase(db);
        MongoCollection configCollection = test.getCollection("config");

        configCollection.deleteMany(exists("liveUploadId"));
        configCollection.insertOne(new Document().append("liveUploadId", uploadID));
    }

    public static String generateNewUploadId() {
        java.util.Date juDate = new Date();
        DateTime dt = new DateTime(juDate);

        StringBuilder sb = new StringBuilder();
        // Send all output to the Appendable object sb
        Formatter formatter = new Formatter(sb);


        int day = dt.getDayOfMonth();
        int month = dt.getMonthOfYear();
        int year = dt.getYear();
        int hour = dt.getHourOfDay();
        int minute = dt.getMinuteOfHour();
        int seconds = dt.getSecondOfMinute();

        formatter.format("%d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, seconds);
        return sb.toString();

    }

    public static void clearUpload(String uploadId, String db)
    {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase test = mongoClient.getDatabase(db);
        MongoCollection plantCollection = test.getCollection("plants");
        MongoCollection commentCollection = test.getCollection("comments");
        Document uploadIdFilter = new Document();
        uploadIdFilter.put("uploadId", uploadId);

        plantCollection.deleteMany(uploadIdFilter);
        commentCollection.deleteMany(uploadIdFilter);
    }

    /**
     *
     * @return a sorted JSON array of all the distinct uploadIds in the DB
     */
    public static String listUploadIds(String db) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase test = mongoClient.getDatabase(db);
        MongoCollection plantCollection = test.getCollection("plants");

        AggregateIterable<Document> documents
                = plantCollection.aggregate(
                Arrays.asList(
                        Aggregates.group("$uploadId"),
                        Aggregates.sort(Sorts.ascending("_id"))
                ));
        List<String> lst = new LinkedList<>();
        for(Document d: documents) {
            lst.add(d.getString("_id"));
        }
        return JSON.serialize(lst);
//        return JSON.serialize(plantCollection.distinct("uploadId","".getClass()));
    }

    public static String getLiveUploadId(String db) {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase test = mongoClient.getDatabase(db);
        MongoCollection configCollection = test.getCollection("config");
        try
        {
            FindIterable<Document> findIterable = configCollection.find(exists("liveUploadId"));
            Iterator<Document> iterator = findIterable.iterator();
            if(!iterator.hasNext())
                return null;
            Document doc = iterator.next();

            return doc.getString("liveUploadId");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println(" [hint] Database might be empty? Couldn't getLiveUploadId");
            throw e;
        }
    }


}
